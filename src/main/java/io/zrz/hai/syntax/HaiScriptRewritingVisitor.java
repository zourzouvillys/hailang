package io.zrz.hai.syntax;

import java.util.Objects;
import java.util.stream.Collectors;

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

public class HaiScriptRewritingVisitor<T>
    implements HaiScriptNodeVisitor<T, HaiScriptNode>, HaiScriptStatementVisitor<T, HaiScriptStatement>, HaiScriptExpressionVisitor<T, HaiScriptExpr> {

  public TypeRef visitType(TypeRef type, T arg) {
    return type;
  }

  @Override
  public HaiScriptStatement visitStatement(HaiScriptStatement stmt, T arg) {
    return stmt.apply((HaiScriptStatementVisitor<T, HaiScriptStatement>) this, arg);
  }

  @Override
  public HaiScriptExpr visitExpression(HaiScriptExpr expr, T arg) {
    return Objects.requireNonNull(expr.apply((HaiScriptExpressionVisitor<T, HaiScriptExpr>) this, arg));
  }

  /**
   *
   */

  @Override
  public HaiScriptWhenMatch visitWhenMatch(HaiScriptWhenMatch match, T arg) {
    if (match.getExpressions() != null) {
      match.getExpressions().forEach(expr -> this.visitExpression(expr, arg));
    }
    this.visitStatement(match.getStatement(), arg);
    return match;
  }

  ///

  @Override
  public HaiScriptBlockStatement visitBlockStatement(HaiScriptBlockStatement block, T arg) {
    block.getStatements().forEach(stmt -> this.visitStatement(stmt, arg));
    return block;
  }

  @Override
  public HaiScriptThrowStatement visitThrowStatement(HaiScriptThrowStatement stmt, T arg) {
    stmt.setExpression(this.visitExpression(stmt.getExpression(), arg));
    return stmt;
  }

  @Override
  public HaiScriptReturnStatement visitReturnStatement(HaiScriptReturnStatement stmt, T arg) {
    if (stmt.getReturnValue() != null) {
      stmt.setReturnValue(this.visitExpression(stmt.getReturnValue(), arg));
    }
    return stmt;
  }

  @Override
  public HaiScriptForStatement visitForStatement(HaiScriptForStatement stmt, T arg) {
    stmt.setDecl(this.visitExpression(stmt.getDecl(), arg));
    stmt.setExpr(this.visitExpression(stmt.getExpr(), arg));
    stmt.setStatement(this.visitStatement(stmt.getStatement(), arg));
    return stmt;
  }

  @Override
  public HaiScriptExpressionStatement visitExprStatement(HaiScriptExpressionStatement stmt, T arg) {
    stmt.setExpression(this.visitExpression(stmt.getExpression(), arg));
    return stmt;
  }

  @Override
  public HaiScriptEmitStatement visitEmitStatement(HaiScriptEmitStatement stmt, T arg) {
    stmt.getEntries().forEach(e -> {

      e.setSymbol(this.visitExpression(e.getSymbol(), arg));

      e.setArgs(this.visitTupleInitializerExpr(e.getArgs(), arg));

      if (e.getReducer() != null) {
        e.getReducer().apply(this, arg);
      }

    });
    return stmt;
  }

  @Override
  public HaiScriptArrayAccessExpr visitArrayAccessExpr(HaiScriptArrayAccessExpr expr, T arg) {
    expr.setName(this.visitExpression(expr.getName(), arg));
    expr.setIndex(
        expr.getIndex().stream()
            .map(x -> this.visitExpression(x, arg))
            .collect(Collectors.toList()));
    return expr;
  }

  @Override
  public HaiScriptWhenExpr visitWhenExpr(HaiScriptWhenExpr expr, T arg) {
    expr.setCondition(this.visitExpression(expr.getCondition(), arg));
    expr.getMatches().forEach((match, stmt) -> {
      match.apply(this, arg);
      this.visitStatement(stmt, arg);
    });
    return expr;
  }

  @Override
  public HaiScriptUnaryExpr visitUnaryExpr(HaiScriptUnaryExpr expr, T arg) {
    this.visitExpression(expr.getExpr(), arg);
    return expr;
  }

  @Override
  public HaiScriptTupleInitializerExpr visitTupleInitializerExpr(HaiScriptTupleInitializerExpr expr, T arg) {
    expr.setArgs(expr.getArgs().stream().map(e -> this.visitExpression(e, arg)).collect(Collectors.toList()));
    return expr;
  }

  @Override
  public HaiScriptExpr visitSymbolExpr(HaiScriptSymbolExpr expr, T arg) {
    return expr;
  }

  @Override
  public HaiScriptRecordInitializerExpr visitRecordInitializerExpr(HaiScriptRecordInitializerExpr expr, T arg) {
    expr.getFields().forEach((key, value) -> this.visitExpression(value, arg));
    return expr;
  }

  @Override
  public HaiScriptNamedTupleExpr visitNamedTupleExpr(HaiScriptNamedTupleExpr expr, T arg) {
    expr.setKey(this.visitExpression(expr.getKey(), arg));
    expr.setValue(this.visitExpression(expr.getValue(), arg));
    return expr;
  }

  @Override
  public HaiScriptInvocationExpr visitInvocationExpr(HaiScriptInvocationExpr expr, T arg) {
    expr.setNameExpr(this.visitExpression(expr.getNameExpr(), arg));
    expr.setArgsExpr(this.visitExpression(expr.getArgsExpr(), arg));
    return expr;
  }

  @Override
  public HaiScriptArrayInitializerExpr visitArrayInitializerExpr(HaiScriptArrayInitializerExpr expr, T arg) {
    expr.setElements(expr.getElements().stream().map(elt -> this.visitExpression(elt, arg)).collect(Collectors.toList()));
    return expr;
  }

  @Override
  public HaiScriptExpr visitBinaryExpr(HaiScriptBinaryExpr expr, T arg) {
    expr.setLeftExpr(this.visitExpression(expr.getLeftExpr(), arg));
    expr.setRightExpr(this.visitExpression(expr.getRightExpr(), arg));
    return expr;
  }

  @Override
  public HaiScriptBranchExpr visitBranchExpr(HaiScriptBranchExpr expr, T arg) {
    expr.getConditions().forEach(m -> m.apply(this, arg));
    if (expr.getOtherwise() != null) {
      this.visitStatement(expr.getOtherwise(), arg);
    }
    return expr;
  }

  @Override
  public HaiScriptConstructorExpr visitConstructorExpr(HaiScriptConstructorExpr expr, T arg) {
    if (expr.getType() != null) {
      expr.setType(this.visitExpression(expr.getType(), arg));
      if (expr.getArguments() != null) {
        expr.setArguments(this.visitExpression(expr.getArguments(), arg));
      }
    }
    if (expr.getInitializers() != null) {
      expr.setInitializers(
          expr.getInitializers().stream().map(x -> this.visitNamedTupleExpr(x, arg)).collect(Collectors.toList()));

    }
    return expr;
  }

  @Override
  public HaiScriptDeclExpr visitDeclExpr(HaiScriptDeclExpr expr, T arg) {
    if (expr.getDeclaredType() != null) {
      expr.setDeclaredType(this.visitType(expr.getDeclaredType(), arg));
    }
    if (expr.getInitalizer() != null) {
      expr.setInitalizer(this.visitExpression(expr.getInitalizer(), arg));
    }
    return expr;
  }

  @Override
  public HaiScriptElvisExpr visitElvisExpr(HaiScriptElvisExpr expr, T arg) {
    expr.setCondition(this.visitExpression(expr.getCondition(), arg));
    if (expr.getThen() != null) {
      expr.setThen(this.visitExpression(expr.getThen(), arg));
    }
    expr.setOtherwise(this.visitExpression(expr.getOtherwise(), arg));
    return expr;
  }

  @Override
  public HaiScriptValue visitValueExpr(HaiScriptValue expr, T arg) {
    return expr;
  }

  @Override
  public HaiScriptExpr visitSelectionExpr(HaiScriptSelectionExpression expr, T arg) {
    expr.setName(expr.getName().apply(this, arg));
    expr.setSelections(expr.getSelections().stream().map(sel -> sel.apply(this, arg)).collect(Collectors.toList()));
    return expr;
  }

  @Override
  public HaiScriptSelectionItem visitSelectionItem(HaiScriptSelectionItem sel, T arg) {
    sel.setExpression(this.visitExpression(sel.getExpression(), arg));
    return sel;
  }

  @Override
  public HaiScriptExpr visitLambdaExpr(HaiScriptLambdaExpr expr, T arg) {
    expr.setStatement(this.visitStatement(expr.getStatement(), arg));
    return expr;
  }

  @Override
  public HaiScriptStatement visitWithStatement(HaiScriptWithStatement stmt, T arg) {
    stmt.setEvent(this.visitExpression(stmt.getEvent(), arg));
    stmt.setArgs(this.visitExpression(stmt.getArgs(), arg));
    stmt.setBlock(this.visitStatement(stmt.getBlock(), arg));
    return stmt;
  }

  @Override
  public HaiScriptNode visitParameterList(HaiScriptParameterListDecl params, T arg) {
    params.setParameters(params.getParameters().stream().map(x -> (HaiScriptParameter) x.apply(this, arg)).collect(Collectors.toList()));
    return params;
  }

  @Override
  public HaiScriptNode visitParameter(HaiScriptParameter param, T arg) {
    // if (param.getName() != null) {
    // param.setName(param.getName().apply(this, arg));
    // }
    if (param.getType() != null) {
      param.setType(param.getType().apply(this, arg));
    }
    if (param.getDefaultValue() != null) {
      param.setDefaultValue(this.visitExpression(param.getDefaultValue(), arg));
    }
    return param;
  }

  @Override
  public HaiScriptNode visitParameterSpread(HaiScriptParameterSpreadNode spread, T arg) {
    return spread;
  }

  @Override
  public HaiScriptTryBlock visitTryStatement(HaiScriptTryBlock stmt, T arg) {

    stmt.setBody(this.visitStatement(stmt.getBody(), arg));

    stmt.setHandlers(stmt.getHandlers().stream().map(x -> this.visitCatch(x, arg)).collect(Collectors.toList()));

    stmt.setBody(this.visitStatement(stmt.getBody(), arg));
    if (stmt.getFinallyBlock() != null) {
      stmt.setFinallyBlock(this.visitStatement(stmt.getFinallyBlock(), arg));
    }
    return stmt;
  }

  @Override
  public HaiScriptCatchBlock visitCatch(HaiScriptCatchBlock block, T arg) {
    block.setBody(this.visitStatement(block.getBody(), arg));
    return block;
  }

}
