package io.zrz.hai.syntax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import io.zrz.hai.lang.HaiElement;
import io.zrz.hai.lang.TypeRef;
import io.zrz.hai.syntax.model.HaiScriptArrayAccessExpr;
import io.zrz.hai.syntax.model.HaiScriptArrayInitializerExpr;
import io.zrz.hai.syntax.model.HaiScriptBinaryExpr;
import io.zrz.hai.syntax.model.HaiScriptBlockStatement;
import io.zrz.hai.syntax.model.HaiScriptBranchExpr;
import io.zrz.hai.syntax.model.HaiScriptCatchBlock;
import io.zrz.hai.syntax.model.HaiScriptConstructorExpr;
import io.zrz.hai.syntax.model.HaiScriptDeclExpr;
import io.zrz.hai.syntax.model.HaiScriptElvisExpr;
import io.zrz.hai.syntax.model.HaiScriptEmitStatement;
import io.zrz.hai.syntax.model.HaiScriptExpr;
import io.zrz.hai.syntax.model.HaiScriptExpressionStatement;
import io.zrz.hai.syntax.model.HaiScriptForStatement;
import io.zrz.hai.syntax.model.HaiScriptInvocationExpr;
import io.zrz.hai.syntax.model.HaiScriptLambdaExpr;
import io.zrz.hai.syntax.model.HaiScriptNamedTupleExpr;
import io.zrz.hai.syntax.model.HaiScriptNode;
import io.zrz.hai.syntax.model.HaiScriptParameter;
import io.zrz.hai.syntax.model.HaiScriptParameterListDecl;
import io.zrz.hai.syntax.model.HaiScriptRecordInitializerExpr;
import io.zrz.hai.syntax.model.HaiScriptReturnStatement;
import io.zrz.hai.syntax.model.HaiScriptSelectionExpression;
import io.zrz.hai.syntax.model.HaiScriptSelectionItem;
import io.zrz.hai.syntax.model.HaiScriptStatement;
import io.zrz.hai.syntax.model.HaiScriptSymbolExpr;
import io.zrz.hai.syntax.model.HaiScriptThrowStatement;
import io.zrz.hai.syntax.model.HaiScriptTryBlock;
import io.zrz.hai.syntax.model.HaiScriptTupleInitializerExpr;
import io.zrz.hai.syntax.model.HaiScriptUnaryExpr;
import io.zrz.hai.syntax.model.HaiScriptValue;
import io.zrz.hai.syntax.model.HaiScriptWhenExpr;
import io.zrz.hai.syntax.model.HaiScriptWhenMatch;
import io.zrz.hai.syntax.model.HaiScriptWithStatement;

public abstract class DefaultHaiScriptVisitor<T, R> extends HaiScriptVisitor<T, R> {

  private R defaultValue;

  public DefaultHaiScriptVisitor(R defaultValue) {
    this.defaultValue = defaultValue;
  }

  public DefaultHaiScriptVisitor() {
    this(null);
  }

  public R visitMember(HaiElement member, T arg) {
    return this.defaultValue;
  }

  public R visitType(HaiScriptNode declaring, TypeRef type, T arg) {
    return this.defaultValue;
  }

  public R visitDefault(HaiScriptNode node) {
    return this.defaultValue;
  }

  public R visitEnter(HaiScriptNode node) {
    return null;
  }

  public R visitLeave(HaiScriptNode node, T arg, R result) {
    return result;
  }

  public R visitStatementDefault(HaiScriptStatement stmt) {
    return this.visitDefault(stmt);
  }

  public R visitExprDefault(HaiScriptExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitStatement(HaiScriptStatement stmt, T arg) {
    return stmt.apply((HaiScriptStatementVisitor<T, R>) this, arg);
  }

  @Override
  public R visitExpression(HaiScriptExpr expr, T arg) {
    return expr.apply((HaiScriptExpressionVisitor<T, R>) this, arg);
  }

  protected R visitChild(String childName, HaiScriptNode node, T arg) {
    return node.apply(this, arg);
  }

  protected List<R> visitChildren(String string, Collection<? extends HaiScriptNode> nodes, T arg) {
    final List<R> results = new ArrayList<>(nodes.size());
    for (final HaiScriptNode node : nodes) {
      results.add(this.visitChild(string, node, arg));
    }
    return results;
  }

  protected R mergeResults(R self, List<R> children) {
    return self;
  }

  @Override
  public R visitSelectionItem(HaiScriptSelectionItem item, T arg) {
    this.visitEnter(item);
    final R self = this.visitDefault(item);
    final R children = this.visitChild(null, item.getExpression(), arg);
    return this.visitLeave(item, arg, this.mergeResults(self, Arrays.asList(children)));
  }

  /**
   *
   */

  @Override
  public R visitWhenMatch(HaiScriptWhenMatch match, T arg) {

    this.visitEnter(match);

    final R res = this.visitDefault(match);

    final List<R> exprs = new LinkedList<>();

    if (match.getExpressions() != null) {
      exprs.addAll(this.visitChildren("EXPR", match.getExpressions(), arg));
    }

    exprs.add(this.visitChild("STMT", match.getStatement(), arg));

    return this.visitLeave(match, arg, this.mergeResults(res, exprs));

  }

  ///

  @Override
  public R visitBlockStatement(HaiScriptBlockStatement block, T arg) {
    this.visitEnter(block);
    final R res = this.visitStatementDefault(block);
    final List<R> children = this.visitChildren("STMT", block.getStatements(), arg);
    return this.visitLeave(block, arg, this.mergeResults(res, children));
  }

  @Override
  public R visitWithStatement(HaiScriptWithStatement stmt, T arg) {
    this.visitEnter(stmt);
    final R res = this.visitStatementDefault(stmt);
    this.visitChild("EVENT", stmt.getEvent(), arg);
    this.visitChild("ARGS", stmt.getArgs(), arg);
    this.visitChild("BLOCK", stmt.getBlock(), arg);
    return this.visitLeave(stmt, arg, res);
  }

  @Override
  public R visitCatch(HaiScriptCatchBlock stmt, T arg) {
    this.visitEnter(stmt);
    final R res = this.visitStatementDefault(stmt.getBody());
    if (stmt.getType() != null) {
      this.visitType(stmt, stmt.getType(), arg);
    }
    return this.visitLeave(stmt, arg, res);
  }

  @Override
  public R visitTryStatement(HaiScriptTryBlock stmt, T arg) {
    this.visitEnter(stmt);
    final R res = this.visitStatementDefault(stmt);
    this.visitChild("BODY", stmt.getBody(), arg);
    this.visitChildren("CATCH", stmt.getHandlers(), arg);
    if (stmt.getFinallyBlock() != null) {
      this.visitChild("FINALLY", stmt.getFinallyBlock(), arg);
    }
    return this.visitLeave(stmt, arg, res);
  }

  @Override
  public R visitThrowStatement(HaiScriptThrowStatement stmt, T arg) {
    this.visitEnter(stmt);
    final R res = this.visitStatementDefault(stmt);
    this.visitChild(null, stmt.getExpression(), arg);
    return this.visitLeave(stmt, arg, res);
  }

  @Override
  public R visitReturnStatement(HaiScriptReturnStatement stmt, T arg) {
    this.visitEnter(stmt);
    final R res = this.visitStatementDefault(stmt);
    if (stmt.getReturnValue() != null) {
      final R child = this.visitChild("RVAL", stmt.getReturnValue(), arg);
      return this.visitLeave(stmt, arg, this.mergeResults(res, Arrays.asList(child)));
    }
    return this.visitLeave(stmt, arg, res);
  }

  @Override
  public R visitForStatement(HaiScriptForStatement stmt, T arg) {
    this.visitEnter(stmt);
    final R res = this.visitStatementDefault(stmt);
    this.visitChild("DECL", stmt.getDecl(), arg);
    this.visitChild("EXPR", stmt.getExpr(), arg);
    this.visitChild("STMT", stmt.getStatement(), arg);
    return this.visitLeave(stmt, arg, res);
  }

  @Override
  public R visitExprStatement(HaiScriptExpressionStatement stmt, T arg) {
    this.visitEnter(stmt);
    final R res = this.visitStatementDefault(stmt);
    final R child = this.visitChild(null, stmt.getExpression(), arg);
    return this.visitLeave(stmt, arg, this.mergeResults(res, Arrays.asList(child)));
  }

  @Override
  public R visitEmitStatement(HaiScriptEmitStatement stmt, T arg) {
    this.visitEnter(stmt);
    final R res = this.visitStatementDefault(stmt);
    stmt.getEntries().forEach(e -> {
      this.visitChild("SYM", e.getSymbol(), arg);
      this.visitChild("ARGS", e.getArgs(), arg);
      if (e.getReducer() != null) {
        e.getReducer().apply(this, arg);
      }
    });
    return this.visitLeave(stmt, arg, res);
  }

  @Override
  public R visitArrayAccessExpr(HaiScriptArrayAccessExpr expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    this.visitExpression(expr.getName(), arg);
    this.visitChildren("INDEX", expr.getIndex(), arg);
    return this.visitLeave(expr, arg, res);
  }

  @Override
  public R visitWhenExpr(HaiScriptWhenExpr expr, T arg) {

    this.visitEnter(expr);

    final R res = this.visitExprDefault(expr);

    this.visitExpression(expr.getCondition(), arg);

    this.visitChildren("MATCH", expr.getMatches().keySet(), arg);

    // expr.getMatches().forEach((match, stmt) -> {
    // match.apply(this, arg);
    // this.visitStatement(stmt, arg);
    // });

    return this.visitLeave(expr, arg, res);

  }

  @Override
  public R visitUnaryExpr(HaiScriptUnaryExpr expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    final R child = this.visitChild(null, expr.getExpr(), arg);
    return this.visitLeave(expr, arg, this.mergeResults(res, Arrays.asList(child)));
  }

  @Override
  public R visitTupleInitializerExpr(HaiScriptTupleInitializerExpr expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    this.visitChildren("ARG", expr.getArgs(), arg);
    return this.visitLeave(expr, arg, res);
  }

  @Override
  public R visitSymbolExpr(HaiScriptSymbolExpr expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    return this.visitLeave(expr, arg, res);
  }

  @Override
  public R visitRecordInitializerExpr(HaiScriptRecordInitializerExpr expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    expr.getFields().forEach((key, value) -> this.visitChild("FIELD", value, arg));
    return this.visitLeave(expr, arg, res);
  }

  @Override
  public R visitNamedTupleExpr(HaiScriptNamedTupleExpr expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    return this.visitLeave(expr, arg, res);
  }

  @Override
  public R visitInvocationExpr(HaiScriptInvocationExpr expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    final R name = this.visitChild("NAME", expr.getNameExpr(), arg);
    final R args = this.visitChild("ARGSEXPR", expr.getArgsExpr(), arg);
    return this.visitLeave(expr, arg, this.mergeResults(res, Arrays.asList(name, args)));
  }

  @Override
  public R visitArrayInitializerExpr(HaiScriptArrayInitializerExpr expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    expr.getElements().forEach(elt -> this.visitChild("ELT", elt, arg));
    return this.visitLeave(expr, arg, res);
  }

  @Override
  public R visitBinaryExpr(HaiScriptBinaryExpr expr, T arg) {
    this.visitEnter(expr);
    final R self = this.visitExprDefault(expr);
    final R left = this.visitChild("LEFT", expr.getLeftExpr(), arg);
    final R right = this.visitChild("RIGHT", expr.getRightExpr(), arg);
    return this.visitLeave(expr, arg, this.mergeResults(self, Arrays.asList(left, right)));
  }

  @Override
  public R visitBranchExpr(HaiScriptBranchExpr expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    final List<R> children = this.visitChildren("COND", expr.getConditions(), arg);
    if (expr.getOtherwise() != null) {
      children.add(this.visitChild("OTHERWISE", expr.getOtherwise(), arg));
    }
    return this.visitLeave(expr, arg, this.mergeResults(res, children));
  }

  @Override
  public R visitConstructorExpr(HaiScriptConstructorExpr expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    final List<R> children = new ArrayList<>();
    if (expr.getType() != null) {
      children.add(this.visitExpression(expr.getType(), arg));
      if (expr.getArguments() != null) {
        children.add(this.visitExpression(expr.getArguments(), arg));
      }
    }
    if (expr.getInitializers() != null) {
      children.addAll(this.visitChildren("INIT", expr.getInitializers(), arg));
    }
    return this.visitLeave(expr, arg, this.mergeResults(res, children));
  }

  @Override
  public R visitDeclExpr(HaiScriptDeclExpr expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    final List<R> children = new ArrayList<>();
    children.add(this.visitType(expr, expr.getDeclaredType(), arg));
    if (expr.getInitalizer() != null) {
      children.add(this.visitChild("INIT", expr.getInitalizer(), arg));
    }
    return this.visitLeave(expr, arg, this.mergeResults(res, children));
  }

  @Override
  public R visitElvisExpr(HaiScriptElvisExpr expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    this.visitChild("COND", expr.getCondition(), arg);
    if (expr.getThen() != null) {
      this.visitChild("THEN", expr.getThen(), arg);
    }
    this.visitChild("OTHERWISE", expr.getOtherwise(), arg);
    return this.visitLeave(expr, arg, res);
  }

  @Override
  public R visitValueExpr(HaiScriptValue expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    return this.visitLeave(expr, arg, res);
  }

  @Override
  public R visitSelectionExpr(HaiScriptSelectionExpression expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    List<R> children = new ArrayList<>();
    if (expr.getName() != null) {
      children.add(expr.getName().apply(this, arg));
    }
    children = this.visitChildren("SEL", expr.getSelections(), arg);
    return this.visitLeave(expr, arg, this.mergeResults(res, children));
  }

  @Override
  public R visitLambdaExpr(HaiScriptLambdaExpr expr, T arg) {
    this.visitEnter(expr);
    final R res = this.visitExprDefault(expr);
    final R params = this.visitChild("PARAMS", expr.getParameters(), arg);
    final R stmt = this.visitChild("STMT", expr.getStatement(), arg);
    return this.visitLeave(expr, arg, this.mergeResults(res, Arrays.asList(params, stmt)));
  }

  @Override
  public R visitParameterList(HaiScriptParameterListDecl params, T arg) {

    this.visitEnter(params);

    final R res = this.visitDefault(params);

    final List<R> children = this.visitChildren("PARAM", params.getParameters(), arg);

    if (params.getSpread() != null) {
      children.add(this.visitChild("SPREAD", params.getSpread(), arg));
    }

    return this.visitLeave(params, arg, this.mergeResults(res, children));

  }

  @Override
  public R visitParameter(HaiScriptParameter param, T arg) {

    this.visitEnter(param);

    final R val = this.visitDefault(param);

    final List<R> children = new LinkedList<>();

    if (param.getType() != null) {
      children.add(this.visitChild("TYPE", param.getType(), arg));
    }

    if (param.getDefaultValue() != null) {
      children.add(this.visitChild("DEFAULT", param.getDefaultValue(), arg));
    }

    return this.visitLeave(param, arg, this.mergeResults(val, children));

  }

  @Override
  public R visitParameterSpread(HaiScriptParameterSpreadNode spread, T arg) {
    this.visitEnter(spread);
    return this.visitLeave(spread, arg, this.visitDefault(spread));
  }

}
