package io.zrz.hai.haiscript;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Map.Entry;

import com.google.common.base.Preconditions;

import io.zrz.hai.haiscript.HaiScriptCodePrinter.CodeContext;
import io.zrz.hai.haiscript.model.HaiScriptArrayAccessExpr;
import io.zrz.hai.haiscript.model.HaiScriptArrayInitializerExpr;
import io.zrz.hai.haiscript.model.HaiScriptBinaryExpr;
import io.zrz.hai.haiscript.model.HaiScriptBlockStatement;
import io.zrz.hai.haiscript.model.HaiScriptBranchExpr;
import io.zrz.hai.haiscript.model.HaiScriptCatchBlock;
import io.zrz.hai.haiscript.model.HaiScriptConstructorExpr;
import io.zrz.hai.haiscript.model.HaiScriptDeclExpr;
import io.zrz.hai.haiscript.model.HaiScriptElvisExpr;
import io.zrz.hai.haiscript.model.HaiScriptEmitStatement;
import io.zrz.hai.haiscript.model.HaiScriptExpr;
import io.zrz.hai.haiscript.model.HaiScriptExpressionStatement;
import io.zrz.hai.haiscript.model.HaiScriptForStatement;
import io.zrz.hai.haiscript.model.HaiScriptInvocationExpr;
import io.zrz.hai.haiscript.model.HaiScriptLambdaExpr;
import io.zrz.hai.haiscript.model.HaiScriptNamedTupleExpr;
import io.zrz.hai.haiscript.model.HaiScriptNode;
import io.zrz.hai.haiscript.model.HaiScriptParameter;
import io.zrz.hai.haiscript.model.HaiScriptParameterListDecl;
import io.zrz.hai.haiscript.model.HaiScriptRecordInitializerExpr;
import io.zrz.hai.haiscript.model.HaiScriptReturnStatement;
import io.zrz.hai.haiscript.model.HaiScriptSelectionExpression;
import io.zrz.hai.haiscript.model.HaiScriptSelectionItem;
import io.zrz.hai.haiscript.model.HaiScriptSymbolExpr;
import io.zrz.hai.haiscript.model.HaiScriptThrowStatement;
import io.zrz.hai.haiscript.model.HaiScriptTryBlock;
import io.zrz.hai.haiscript.model.HaiScriptTupleInitializerExpr;
import io.zrz.hai.haiscript.model.HaiScriptUnaryExpr;
import io.zrz.hai.haiscript.model.HaiScriptValue;
import io.zrz.hai.haiscript.model.HaiScriptWhenExpr;
import io.zrz.hai.haiscript.model.HaiScriptWhenMatch;
import io.zrz.hai.haiscript.model.HaiScriptWithStatement;

public class HaiScriptCodePrinter extends HaiScriptVisitor<CodeContext, Void> {

  public static enum CodeContext {
    OUTER,
  }

  private final IndentPrintWriter out;
  private final CodeContext context;

  public HaiScriptCodePrinter(IndentPrintWriter out) {
    this.context = CodeContext.OUTER;
    this.out = out;
  }

  public HaiScriptCodePrinter(IndentPrintWriter out, CodeContext context) {
    this.context = context;
    this.out = out;
  }

  @Override
  public Void visitWhenMatch(HaiScriptWhenMatch match, CodeContext ctx) {
    throw new IllegalArgumentException();
  }

  @Override
  public Void visitSelectionItem(HaiScriptSelectionItem item, CodeContext ctx) {
    if (item.getAlias() != null) {
      this.out.append(item.getAlias()).append(": ");
    }
    this.visitExpression(item.getExpression(), ctx);
    return null;
  }

  @Override
  public Void visitParameterList(HaiScriptParameterListDecl params, CodeContext ctx) {
    this.out.append("(");
    int count = 0;
    for (final HaiScriptNode param : params.getParameters()) {
      if (count++ > 0) {
        this.out.append(", ");
      }
      param.apply(this, ctx);
    }
    this.out.append(")");
    return null;
  }

  @Override
  public Void visitBlockStatement(HaiScriptBlockStatement block, CodeContext ctx) {
    this.out.append("{").println();
    this.out.inc();
    block.getStatements().forEach(stmt -> {
      this.visitStatement(stmt, ctx);
      this.out.println();
    });
    this.out.dec();
    this.out.append("}").println();
    return null;
  }

  @Override
  public Void visitThrowStatement(HaiScriptThrowStatement stmt, CodeContext ctx) {
    throw new IllegalArgumentException();
  }

  @Override
  public Void visitReturnStatement(HaiScriptReturnStatement stmt, CodeContext ctx) {
    this.out.append("return");
    if (stmt.getReturnValue() != null) {
      this.out.append(" ");
      this.visitExpression(stmt.getReturnValue(), ctx);
    }
    return null;
  }

  @Override
  public Void visitForStatement(HaiScriptForStatement stmt, CodeContext ctx) {
    this.out.append("<<<FOR>>>");
    return null;
  }

  @Override
  public Void visitExprStatement(HaiScriptExpressionStatement stmt, CodeContext ctx) {
    this.visitExpression(stmt.getExpression(), null);
    return null;
  }

  @Override
  public Void visitEmitStatement(HaiScriptEmitStatement stmt, CodeContext ctx) {
    this.out.append("emit ");
    stmt.getEntries().forEach(e -> this.visitExpression(e.getSymbol(), ctx));
    return null;
  }

  @Override
  public Void visitWithStatement(HaiScriptWithStatement stmt, CodeContext ctx) {
    throw new IllegalArgumentException();
  }

  @Override
  public Void visitArrayAccessExpr(HaiScriptArrayAccessExpr expr, CodeContext ctx) {
    this.visitExpression(expr.getName(), ctx);
    this.out.append("[");
    expr.getIndex().forEach(i -> this.visitExpression(i, ctx));
    this.out.append("]");
    return null;
  }

  @Override
  public Void visitWhenExpr(HaiScriptWhenExpr expr, CodeContext ctx) {
    throw new IllegalArgumentException();
  }

  @Override
  public Void visitUnaryExpr(HaiScriptUnaryExpr expr, CodeContext ctx) {

    switch (expr.getOperator()) {
      case "--":
      case "++":
      case "-":
      case "+":
        this.out.append(expr.getOperator());
        this.visitExpression(expr.getExpr(), ctx);
        break;
      case "typeof":
        this.out.append("typeof(");
        this.visitExpression(expr.getExpr(), ctx);
        this.out.append(")");
        break;
      default:
        this.out.append(expr.getOperator());
        this.out.append(" ");
        this.visitExpression(expr.getExpr(), ctx);
        break;
    }

    return null;
  }

  @Override
  public Void visitTupleInitializerExpr(HaiScriptTupleInitializerExpr expr, CodeContext ctx) {
    this.out.append("(");
    int count = 0;
    for (final HaiScriptExpr e : expr.getArgs()) {

      if (count++ > 0) {
        this.out.append(", ");
      }

      this.visitExpression(e, ctx);

    }
    this.out.append(")");
    return null;
  }

  @Override
  public Void visitSymbolExpr(HaiScriptSymbolExpr expr, CodeContext ctx) {
    this.out.append(expr.getSymbol().getText());
    return null;
  }

  @Override
  public Void visitRecordInitializerExpr(HaiScriptRecordInitializerExpr expr, CodeContext ctx) {
    this.out.append("{ ");
    int count = 0;
    for (final Entry<String, HaiScriptExpr> e : expr.getFields().entrySet()) {

      if (count++ > 0) {
        this.out.append(", ");
      }
      this.out.append(e.getKey());
      this.out.append(": ");
      this.visitExpression(e.getValue(), ctx);

    }
    this.out.append(" }");
    return null;
  }

  @Override
  public Void visitNamedTupleExpr(HaiScriptNamedTupleExpr expr, CodeContext ctx) {
    if (expr.getKey() != null) {
      this.visitExpression(expr.getKey(), ctx);
      this.out.append(": ");
    }
    this.visitExpression(expr.getValue(), ctx);
    return null;
  }

  @Override
  public Void visitInvocationExpr(HaiScriptInvocationExpr expr, CodeContext ctx) {

    this.visitExpression(expr.getNameExpr(), ctx);

    switch (expr.getArgsExpr().getExprKind()) {
      case TUPLE_INITIALIZER:
        break;
      default:
        this.out.append(" ");
        break;

    }

    this.visitExpression(expr.getArgsExpr(), ctx);
    return null;
  }

  @Override
  public Void visitArrayInitializerExpr(HaiScriptArrayInitializerExpr expr, CodeContext ctx) {
    throw new IllegalArgumentException();
  }

  @Override
  public Void visitBinaryExpr(HaiScriptBinaryExpr expr, CodeContext ctx) {
    this.visitExpression(expr.getLeftExpr(), ctx);

    switch (expr.getOperator()) {
      case ".":
      case "->":
        this.out.append(expr.getOperator());
        break;
      default:
        this.out.append(" ");
        this.out.append(expr.getOperator());
        this.out.append(" ");
        break;
    }

    this.visitExpression(expr.getRightExpr(), ctx);
    return null;
  }

  @Override
  public Void visitBranchExpr(HaiScriptBranchExpr branch, CodeContext ctx) {

    int count = 0;

    for (final HaiScriptWhenMatch cond : branch.getConditions()) {

      if (count++ > 0) {
        this.out.append("else ");
      }

      this.out.append("if ");

      Preconditions.checkArgument(cond.getExpressions().size() == 1);

      this.out.append("(");

      cond.getExpressions().forEach(expr -> this.visitExpression(expr, ctx));
      this.out.append(") ");
      this.out.println("{");
      this.out.inc();
      this.visitStatement(cond.getStatement(), ctx);
      this.out.println();
      this.out.dec();
      this.out.println("}");

    }

    if (branch.getOtherwise() != null) {
      this.out.append("else {");
      this.out.println("}");
    }

    return null;

  }

  @Override
  public Void visitConstructorExpr(HaiScriptConstructorExpr expr, CodeContext ctx) {
    this.out.append("new ");
    if (expr.getType() != null) {
      this.visitExpression(expr.getType(), ctx);
      if (expr.getArguments() != null) {
        this.visitExpression(expr.getArguments(), ctx);
      }
    }
    if (expr.getInitializers() != null) {
      this.out.append(" { ");
      int count = 0;
      for (final HaiScriptNamedTupleExpr e : expr.getInitializers()) {
        if (count++ > 0) {
          this.out.append(", ");
        }
        this.visitExpression(e.getKey(), ctx);
        this.out.append(" = ");
        this.visitExpression(e.getValue(), ctx);
      }
      this.out.append(" }");
    }
    return null;
  }

  @Override
  public Void visitDeclExpr(HaiScriptDeclExpr expr, CodeContext ctx) {
    if (expr.isVariable()) {
      this.out.append("var ");
    } else {
      this.out.append("const ");
    }
    this.out.append(expr.getSymbol());

    if (expr.getDeclaredType() != null) {
      this.out.append(" : ");
      this.out.append(expr.getDeclaredType().toString());
    }

    if (expr.getInitalizer() != null) {
      this.out.append(" = ");
      this.visitExpression(expr.getInitalizer(), ctx);
    }
    return null;
  }

  @Override
  public Void visitElvisExpr(HaiScriptElvisExpr expr, CodeContext ctx) {
    throw new IllegalArgumentException();
  }

  @Override
  public Void visitValueExpr(HaiScriptValue expr, CodeContext ctx) {

    this.out.append(expr.toString());
    return null;
  }

  @Override
  public Void visitSelectionExpr(HaiScriptSelectionExpression expr, CodeContext ctx) {

    if (expr.getName() != null) {
      expr.getName().apply(this, ctx);
    }

    if (!expr.getSelections().isEmpty()) {

      this.out.println(" {");
      this.out.inc();
      expr.getSelections().forEach(sel -> {
        sel.apply(this, ctx);
        this.out.forceLine();
      });
      this.out.dec();
      this.out.forceLine();
      this.out.println("}");

    }

    return null;
  }

  @Override
  public Void visitLambdaExpr(HaiScriptLambdaExpr expr, CodeContext ctx) {
    this.visitParameterList(expr.getParameters(), ctx);
    this.out.append(" => ");
    this.visitStatement(expr.getStatement(), ctx);
    return null;
  }

  //
  public static void print(Writer writer, HaiScriptNode node, CodeContext context) {
    if (node == null) {
      return;
    }
    final IndentPrintWriter out = new IndentPrintWriter(writer);
    final HaiScriptCodePrinter printer = new HaiScriptCodePrinter(out);
    node.apply(printer, context);
    out.flush();
  }

  public static void print(OutputStream writer, HaiScriptNode node, CodeContext context) {
    final IndentPrintWriter out = new IndentPrintWriter(writer);
    print(out, node, context);
  }

  public static void print(OutputStream writer, HaiScriptNode node) {
    final IndentPrintWriter out = new IndentPrintWriter(writer);
    print(out, node, HaiScriptCodePrinter.CodeContext.OUTER);
  }

  public static void print(Writer writer, HaiScriptNode node) {
    final IndentPrintWriter out = new IndentPrintWriter(writer);
    print(out, node, CodeContext.OUTER);
  }

  @Override
  public Void visitParameter(HaiScriptParameter param, CodeContext arg) {
    if (param.getName() != null) {
      this.out.append(param.getName());
      // param.getName().apply(this, arg);
    } else {
      this.out.append("_");
    }
    if (param.getType() != null) {
      if (param.isOptional()) {
        this.out.append("?");
      }
      this.out.append(": ");
      param.getType().apply(this, arg);
    }
    if (param.getDefaultValue() != null) {
      this.out.append(" = ");
      this.visitExpression(param.getDefaultValue(), arg);
    }
    return null;
  }

  @Override
  public Void visitParameterSpread(HaiScriptParameterSpreadNode spread, CodeContext arg) {
    this.out.append("...");
    if (spread.getAlias() != null) {
      this.out.append(" as ").append(spread.getAlias());
    }
    return null;
  }

  @Override
  public Void visitCatch(HaiScriptCatchBlock block, CodeContext arg) {
    this.out.append("catch ");
    if (block.getType() != null) {
      this.out.append("(").append(block.getType().toString());
      this.out.append(") ");
    }
    this.out.println("{");
    this.out.inc();
    this.visitStatement(block.getBody(), arg);
    this.out.dec();
    this.out.println("}");
    return null;
  }

  @Override
  public Void visitTryStatement(HaiScriptTryBlock stmt, CodeContext arg) {
    this.out.println("try {");
    this.out.inc();
    this.visitStatement(stmt.getBody(), arg);
    this.out.dec();
    this.out.println("}");
    stmt.getHandlers().forEach(handler -> this.visitCatch(handler, arg));
    if (stmt.getFinallyBlock() != null) {
      this.out.append("finally {");
      this.out.inc();
      this.visitStatement(stmt.getFinallyBlock(), arg);
      this.out.dec();
      this.out.append("}");
    }
    return null;
  }

}
