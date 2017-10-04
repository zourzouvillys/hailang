package io.zrz.hai.syntax;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

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

public class HaiScriptPrinter extends HaiScriptVisitor<Void, Void> {

  private final int depth;
  private final PrintWriter out;
  private final String indent;

  public HaiScriptPrinter(IndentPrintWriter out) {
    this(out, out.getIndent());
  }

  public HaiScriptPrinter(PrintWriter out, int depth) {
    this.depth = depth;
    this.out = out;
    this.indent = StringUtils.repeat("  ", depth);
  }

  @Override
  public Void visitExpression(HaiScriptExpr expr, Void arg) {
    this.out.append("(");
    super.visitExpression(expr, arg);
    this.out.append(")");
    return null;
  }

  @Override
  public Void visitWhenMatch(HaiScriptWhenMatch match, Void arg) {
    throw new IllegalArgumentException();
  }

  @Override
  public Void visitBlockStatement(HaiScriptBlockStatement block, Void arg) {
    this.out.append("{").println();
    block.getStatements().forEach(stmt -> {
      this.out.append(StringUtils.repeat("  ", this.depth + 1));
      this.visitStatement(stmt, arg);
      this.out.println(";");
    });
    this.out.append(this.indent);
    this.out.append("}");
    return null;
  }

  @Override
  public Void visitThrowStatement(HaiScriptThrowStatement stmt, Void arg) {
    this.out.append(this.indent).append("throw ");
    return null;
  }

  @Override
  public Void visitReturnStatement(HaiScriptReturnStatement stmt, Void arg) {
    this.out.append(this.indent).append("return ");
    if (stmt.getReturnValue() != null) {
      this.visitExpression(stmt.getReturnValue(), arg);
    }
    return null;
  }

  @Override
  public Void visitForStatement(HaiScriptForStatement stmt, Void arg) {
    this.out.append(this.indent).append("for ");
    this.visitExpression(stmt.getDecl(), arg);
    this.out.append(" in ");
    this.visitExpression(stmt.getExpr(), arg);
    this.out.println();
    this.out.append(this.indent);
    this.visitStatement(stmt.getStatement(), arg);
    return null;
  }

  @Override
  public Void visitExprStatement(HaiScriptExpressionStatement stmt, Void arg) {
    return this.visitExpression(stmt.getExpression(), arg);
  }

  @Override
  public Void visitEmitStatement(HaiScriptEmitStatement stmt, Void arg) {

    this.out.append("emit ");

    stmt.getEntries().forEach(emit -> {

      this.out.print(emit.getSymbol());

      if (emit.getArgs() != null) {
        this.visitExpression(emit.getArgs(), arg);
      }

      if (emit.getReducer() != null) {
        emit.getReducer().apply(this, arg);
      }

    });

    return null;

  }

  @Override
  public Void visitTupleInitializerExpr(HaiScriptTupleInitializerExpr expr, Void arg) {

    this.out.append("(");

    final AtomicInteger count = new AtomicInteger(0);

    expr.getArgs().forEach(param -> {

      if (count.getAndIncrement() > 0) {
        this.out.append(", ");
      }

      this.out.append(param.getExprKind().toString()).append(" ");
      this.visitExpression(param, arg);

    });
    this.out.append(")");
    return null;
  }

  @Override
  public Void visitSymbolExpr(HaiScriptSymbolExpr expr, Void arg) {
    this.out.print(expr.getSymbol().getText());
    return null;
  }

  @Override
  public Void visitBranchExpr(HaiScriptBranchExpr expr, Void arg) {

    int count = 0;

    for (final HaiScriptWhenMatch e : expr.getConditions()) {

      if (count++ > 0) {
        this.out.append("else ");
      }

      this.out.append("if ");

      e.getExpressions().forEach(a -> this.visitExpression(a, arg));

      this.out.println(" {");

      this.out.append(StringUtils.repeat("  ", this.depth + 1));

      new HaiScriptPrinter(this.out, this.depth + 1).visitStatement(e.getStatement(), arg);

      this.out.println();
      this.out.append(this.indent).println("}");

    }

    if (expr.getOtherwise() != null) {
      this.out.append("else ");
      new HaiScriptPrinter(this.out, this.depth + 1).visitStatement(expr.getOtherwise(), arg);
    }

    return null;
  }

  @Override
  public Void visitUnaryExpr(HaiScriptUnaryExpr expr, Void arg) {
    this.out.append(expr.getOperator());
    this.visitExpression(expr.getExpr(), arg);
    return null;
  }

  @Override
  public Void visitRecordInitializerExpr(HaiScriptRecordInitializerExpr expr, Void arg) {
    this.out.append("{ ");
    final AtomicInteger count = new AtomicInteger(0);
    expr.getFields().forEach((key, value) -> {

      if (count.getAndIncrement() > 0) {
        this.out.append(", ");
      }

      if (key != null) {
        this.out.append(key).append(": ");
      }

      this.visitExpression(value, arg);

    });
    this.out.append(" }");
    return null;
  }

  @Override
  public Void visitNamedTupleExpr(HaiScriptNamedTupleExpr expr, Void arg) {
    this.visitExpression(expr.getKey(), arg);
    this.out.append(": ");
    this.visitExpression(expr.getValue(), arg);
    return null;
  }

  @Override
  public Void visitInvocationExpr(HaiScriptInvocationExpr expr, Void arg) {
    this.visitExpression(expr.getNameExpr(), arg);
    this.visitExpression(expr.getArgsExpr(), arg);
    return null;
  }

  @Override
  public Void visitArrayInitializerExpr(HaiScriptArrayInitializerExpr expr, Void arg) {
    this.out.append("[ ");
    final AtomicInteger count = new AtomicInteger(0);
    expr.getElements().forEach(elt -> {
      if (count.getAndIncrement() > 0) {
        this.out.append(", ");
      }
      this.visitExpression(elt, arg);
    });
    this.out.append(" ]");
    return null;
  }

  @Override
  public Void visitBinaryExpr(HaiScriptBinaryExpr expr, Void arg) {

    this.out.append("(");
    this.visitExpression(expr.getLeftExpr(), arg);
    this.out.append(")");

    this.out.append(" ").append(expr.getOperator()).append(" ");

    this.out.append("(");
    this.visitExpression(expr.getRightExpr(), arg);
    this.out.append(")");

    return null;
  }

  @Override
  public Void visitConstructorExpr(HaiScriptConstructorExpr expr, Void arg) {
    this.out.append("new ");
    this.visitExpression(expr.getType(), arg);
    if (expr.getArguments() != null) {
      this.visitExpression(expr.getArguments(), arg);
    }
    return null;
  }

  private void visitType(TypeRef type) {
    this.out.append(type.getClass().getSimpleName()).append(":").append(type.toString());
  }

  @Override
  public Void visitDeclExpr(HaiScriptDeclExpr expr, Void arg) {

    this.out.print(expr.isVariable() ? "var" : "const");

    this.out.append(" ").append(expr.getSymbol());

    if (expr.getInitalizer() != null) {
      this.out.append(" = [");
      if (expr.getDeclaredType() != null) {
        this.visitType(expr.getDeclaredType());
      }
      this.out.append("]");
      this.visitExpression(expr.getInitalizer(), arg);
    }

    return null;

  }

  @Override
  public Void visitElvisExpr(HaiScriptElvisExpr expr, Void arg) {
    this.visitExpression(expr.getCondition(), arg);
    if (expr.getThen() != null) {
      this.out.append(" ? ");
      this.visitExpression(expr.getThen(), arg);
      this.out.append(" : ");
    } else {
      this.out.append(" ?: ");
    }
    this.visitExpression(expr.getOtherwise(), arg);
    return null;
  }

  @Override
  public Void visitValueExpr(HaiScriptValue expr, Void arg) {
    this.out.append(expr.toString());
    return null;
  }

  @Override
  public Void visitArrayAccessExpr(HaiScriptArrayAccessExpr expr, Void arg) {
    this.visitExpression(expr.getName(), arg);
    this.out.append("[");
    final AtomicInteger count = new AtomicInteger(0);
    expr.getIndex().forEach(idx -> {
      if (count.getAndIncrement() > 0) {
        this.out.append(", ");
      }
      this.visitExpression(idx, arg);
    });
    this.out.append("]");
    return null;
  }

  @Override
  public Void visitWhenExpr(HaiScriptWhenExpr expr, Void arg) {
    this.out.append("when ");
    this.visitExpression(expr.getCondition(), arg);
    this.out.append(" {");
    expr.getMatches().forEach((key, val) -> {
      this.out.append("(");
      key.getExpressions().forEach(c -> this.visitExpression(c, arg));
      this.out.append(") => ");
      this.visitStatement(val, arg);
      this.out.append(";");
    });
    this.out.append("}");
    return null;
  }

  /**
   * a query on an expression.
   */

  @Override
  public Void visitSelectionExpr(HaiScriptSelectionExpression expr, Void arg) {
    expr.getName().apply(this, arg);

    this.out.append("{");
    // new HaiQueryNodePrinter(this.out, this.depth + 1)
    final AtomicInteger count = new AtomicInteger(0);
    expr.getSelections().forEach(item -> {

      if (count.getAndIncrement() == 0) {
        this.out.append(", ");
      }

      item.apply(this, arg);

    });
    this.out.append("}");
    return null;
  }

  @Override
  public Void visitParameter(HaiScriptParameter param, Void arg) {

    if (param.getName() != null) {
      this.out.append(param.getName());
      // param.getName().apply(this, arg);
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
  public Void visitSelectionItem(HaiScriptSelectionItem item, Void arg) {

    if (item.getAlias() != null) {
      this.out.append(item.getAlias());
      this.out.append(": ");
    }

    this.visitExpression(item.getExpression(), arg);

    return null;
  }

  @Override
  public Void visitLambdaExpr(HaiScriptLambdaExpr expr, Void arg) {
    this.visitParameterList(expr.getParameters(), arg);
    this.out.append(" => ");
    this.visitStatement(expr.getStatement(), arg);
    return null;
  }

  @Override
  public Void visitWithStatement(HaiScriptWithStatement stmt, Void arg) {
    this.out.append("with ");
    this.visitExpression(stmt.getEvent(), arg);
    this.visitExpression(stmt.getArgs(), arg);
    this.visitStatement(stmt.getBlock(), arg);
    return null;
  }

  @Override
  public Void visitParameterList(HaiScriptParameterListDecl params, Void arg) {
    this.out.append("(");

    int count = 0;
    for (final HaiScriptNode param : params.getParameters()) {

      if (count++ > 0) {
        this.out.append(", ");
      }

      param.apply(this, arg);

    }

    this.out.append(")");
    return null;
  }

  public static void print(OutputStream out, HaiScriptNode body) {
    final IndentPrintWriter i = new IndentPrintWriter(out);
    body.apply(new HaiScriptPrinter(i), null);
    i.flush();
  }

  public static void print(PrintWriter out, HaiScriptStatement body) {
    final IndentPrintWriter i = new IndentPrintWriter(out);
    new HaiScriptPrinter(i).visitStatement(body, null);
    i.flush();
  }

  public static void print(IndentPrintWriter out, HaiScriptStatement body) {
    new HaiScriptPrinter(out).visitStatement(body, null);
    out.flush();
  }

  @Override
  public Void visitParameterSpread(HaiScriptParameterSpreadNode spread, Void arg) {
    this.out.append("...");
    if (spread.getAlias() != null) {
      this.out.append(" as ").append(spread.getAlias());
    }
    return null;
  }

  @Override
  public Void visitCatch(HaiScriptCatchBlock block, Void arg) {
    this.out.append("catch ");
    if (block.getType() != null) {
      this.out.append("(").append(block.getType().toString());
      this.out.append(") ");
    }
    this.out.println("{");
    this.visitStatement(block.getBody(), arg);
    this.out.println("}");
    return null;
  }

  @Override
  public Void visitTryStatement(HaiScriptTryBlock stmt, Void arg) {
    this.out.println("try {");
    this.visitStatement(stmt.getBody(), arg);
    this.out.println("}");
    stmt.getHandlers().forEach(handler -> this.visitCatch(handler, arg));
    if (stmt.getFinallyBlock() != null) {
      this.out.append("finally {");
      this.visitStatement(stmt.getFinallyBlock(), arg);
      this.out.append("}");
    }
    return null;
  }

}
