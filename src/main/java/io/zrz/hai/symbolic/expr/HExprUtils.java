package io.zrz.hai.symbolic.expr;

import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.stream.Collectors;

import io.zrz.hai.haiscript.IndentPrintWriter;
import io.zrz.hai.symbolic.HTypeUtils;

public class HExprUtils {

  public static String toString(HExpr expr) {
    final StringWriter w = new StringWriter();
    dump(w, expr);
    return w.toString().trim();
  }

  /**
   *
   */

  public static void dump(Writer strm, HExpr expr) {
    final IndentPrintWriter w = new IndentPrintWriter(strm);
    expr.accept(new DumpExpressionVisitor(w));
    w.forceLine();
    w.flush();
  }

  public static void dump(PrintStream strm, HExpr expr) {
    final IndentPrintWriter w = new IndentPrintWriter(strm);
    expr.accept(new DumpExpressionVisitor(w));
    w.forceLine();
    w.flush();
    strm.flush();
  }

  private static final class DumpExpressionVisitor implements HExprVisitor<Void> {

    private final IndentPrintWriter out;

    public DumpExpressionVisitor(IndentPrintWriter strm) {
      this.out = strm;
    }

    @Override
    public Void visitBlock(HBlockExpr expr) {
      this.out.append("BLOCK");
      this.out.println();
      this.out.inc();
      expr.getExpressions().forEach(sub -> {
        sub.accept(this);
        this.out.forceLine();
      });
      this.out.dec();
      return null;
    }

    @Override
    public Void visitUnary(HUnaryExpr expr) {
      this.out.append(expr.getExprKind().toString());
      this.out.append(" ");
      this.out.inc();
      expr.getExpression().accept(this);
      this.out.dec();
      this.out.println();
      return null;
    }

    @Override
    public Void visitBinary(HBinaryExpr expr) {

      switch (expr.getExprKind()) {

        case LOGICAL_AND:
        case LOGICAL_OR:
        case NE:
        case EQ:
        case LT:
        case GT:
        case LE:
        case GE: {
          expr.getLeft().accept(this);
          this.out.append(" ");
          this.out.append(expr.getExprKind().toString());
          this.out.append(" ");
          expr.getRight().accept(this);
          return null;
        }

        case INVOKE:
          if (expr.getLeft().getExprKind() == HExprKind.LAMBDA) {
            final HLambdaExpr lambda = (HLambdaExpr) expr.getLeft();

            this.out.append("INLINE LAMBDA (");
            expr.getRight().accept(this);
            this.out.append(" --> (");
            this.out.append(lambda.getParameters().stream().map(x -> x.toString()).collect(Collectors.joining(", ")));
            this.out.append(")): ");
            if (lambda.getReturnType() != null) {
              this.out.append(lambda.getReturnType().getTypeKind().toString());
            } else {
              this.out.append("{null}");
            }

            this.out.append(" => ");
            if (lambda.getBody() != null) {
              lambda.getBody().accept(this);
            } else {
              this.out.append("{null}");
            }

          } else {
            this.out.append(expr.getExprKind().toString());
            this.out.append(" ");
            expr.getLeft().accept(this);
            // this.out.println();
            this.out.append(" ");
            this.out.inc();
            expr.getRight().accept(this);
            this.out.dec();
          }
          return null;

        case ASSIGN:
          this.out.append(expr.getExprKind().toString()).append(" ");
          expr.getLeft().accept(this);
          this.out.append(" = ");
          expr.getRight().accept(this);
          return null;

      }

      this.out.append("BINOP ");
      this.out.append(expr.getExprKind().toString());
      this.out.println();
      this.out.inc();

      this.out.append("OP1 ");
      expr.getLeft().accept(this);
      this.out.println();

      this.out.append("OP2 ");
      expr.getRight().accept(this);
      this.out.println();

      this.out.dec();

      return null;
    }

    @Override
    public Void visitTypeBinary(HTypeBinaryExpr expr) {

      switch (expr.getExprKind()) {
        case NEW:
          this.out.append("NEW ");
          this.out.append(HTypeUtils.summary(expr.getTypeOperand())).append(" ");
          expr.getExpression().accept(this);
          return null;
        case IS:
          expr.getExpression().accept(this);
          this.out.append(" IS ");
          this.out.append(expr.getTypeOperand().toString());
          return null;
        case AS:
          this.out.append("( ");
          expr.getExpression().accept(this);
          this.out.append(" AS ");
          this.out.append(expr.getTypeOperand().toString());
          this.out.append(" )");
          return null;
      }

      this.out.append(expr.getExprKind().toString());
      this.out.append(" ");

      this.out.println();
      this.out.inc();

      this.out.append("TYPEOP ").append(expr.getTypeOperand().toString()).append(" ");

      this.out.forceLine();
      this.out.append("OPERAND ");
      expr.getExpression().accept(this);

      this.out.dec();

      return null;

    }

    @Override
    public Void visitGoto(HGotoExpr expr) {

      switch (expr.getGotoKind()) {

        case BREAK:
          this.out.append("BREAK");
          break;

        case CONTINUE:
          this.out.append("CONTINUE");
          break;

        case RETURN:
          this.out.append("RETURN");
          break;

        default:
          throw new RuntimeException(expr.getGotoKind().toString());

      }

      if (expr.getValue() != null) {

        this.out.append(" ");
        this.out.inc();
        expr.getValue().accept(this);
        this.out.dec();

      }

      return null;
    }

    @Override
    public Void visitMatch(HMatchExpr expr) {

      if (expr.getMatchValue() == null && expr.getCases().size() == 1 && expr.getCases().get(0).getTestValues().size() == 1) {

        final HMatchCase single = expr.getCases().get(0);
        final HExpr test = single.getTestValues().get(0);
        final HExpr otherwise = expr.getDefaultExpression();

        if (test != null && otherwise != null) {
          test.accept(this);
          this.out.append(" ? ");
          single.getBody().accept(this);
          this.out.append(" : ");
          otherwise.accept(this);
          return null;
        }

        this.out.append("IF ");
        test.accept(this);
        this.out.forceLine();
        this.out.inc();
        if (single.getBody() != null) {
          single.getBody().accept(this);
        } else {
          this.out.append("<<<NULL>>>");
        }
        this.out.dec();

        this.out.forceLine();

        if (expr.getDefaultExpression() != null) {
          this.out.println("ELSE");
          this.out.inc();
          expr.getDefaultExpression().accept(this);
          this.out.dec();
        }

        return null;
      }

      this.out.append("CONDITIONAL ");

      if (expr.getMatchValue() != null) {
        expr.getMatchValue().accept(this);
      }

      this.out.println();
      this.out.inc();

      expr.getCases().forEach(c -> {
        this.out.print("CASE ");
        this.out.inc();
        this.out.forceLine();
        c.getTestValues().forEach(x -> {
          this.out.print("MATCH ");
          x.accept(this);
          this.out.forceLine();
        });
        this.out.forceLine();
        this.out.append("THEN ");
        if (c.getBody() == null) {
          this.out.append("<<<NULL>>>");
        } else {
          c.getBody().accept(this);
        }
        this.out.dec();
        this.out.forceLine();
      });

      if (expr.getDefaultExpression() != null) {
        this.out.append("ELSE ");
        this.out.inc();
        expr.getDefaultExpression().accept(this);
        this.out.dec();
        this.out.forceLine();
      }

      this.out.dec();
      return null;
    }

    @Override
    public Void visitMemberAccess(HMemberExpr expr) {

      if (expr.getExpression() != null) {
        this.out.inc();
        expr.getExpression().accept(this);
        this.out.append(" . ");
        this.out.dec();
      }

      this.out.print("MEMBER ");
      this.out.print(expr.getMember().getMemberKind());
      this.out.append(" [");
      if (expr.getMember().getDeclaringType() != null) {
        // for HAI builtins, for now.
        this.out.append(expr.getMember().getDeclaringType().getQualifiedName());
        this.out.append(".");
      }
      this.out.append(expr.getMember().getName());
      this.out.append("]");

      return null;
    }

    @Override
    public Void visitThis(HThisExpr expr) {
      this.out.append("THIS");
      return null;
    }

    @Override
    public Void visitTupleInit(HTupleInitExpr expr) {

      if (expr.getInitializers().isEmpty()) {
        this.out.append("EMPTY_TUPLE");
        return null;
      }

      this.out.append("TUPLE_INIT ");

      if (expr.getInitializers().size() == 1) {
        this.out.append("( ");
        expr.getInitializers().get(0).accept(this);
        this.out.append(" )");
        return null;
      }

      int count = 0;
      this.out.inc();
      for (final HExpr arg : expr.getInitializers()) {
        this.out.forceLine();
        this.out.print(count++);
        this.out.append(": ");
        arg.accept(this);
      }
      this.out.dec();
      return null;
    }

    @Override
    public Void visitConst(HConstExpr expr) {
      this.out.append("VAL(");
      this.out.append(expr.getValue().toString());
      this.out.append(")");
      return null;
    }

    @Override
    public Void visitVar(HVarExpr expr) {
      this.out.append("VAR(");
      if (expr.getVariable() == null) {
        this.out.append("{NULL}");
      } else {
        this.out.append(expr.getVariable().getName());
        if (expr.getVariable().getType() != null) {
          this.out.append(":");
          this.out.append(expr.getVariable().getType().toString());
        }
      }
      this.out.append(")");
      return null;
    }

    @Override
    public Void visitLoop(HLoopExpr expr) {
      this.out.append("LOOP ");
      this.out.forceLine();
      this.out.inc();
      expr.getBody().accept(this);
      this.out.dec();
      return null;
    }

    @Override
    public Void visitIndexAccess(HIndexAccessExpr expr) {
      expr.getExpression().accept(this);
      this.out.append("[");
      for (int i = 0; i < expr.getIndexers().size(); ++i) {
        if (i > 0) {
          this.out.append(", ");
        }
        expr.getIndexers().get(i).accept(this);
      }
      this.out.append("]");
      return null;
    }

    @Override
    public Void visitMemberInit(HMemberInitExpr expr) {

      if (expr.getNewExpr() != null) {
        this.out.append("(");
        expr.getNewExpr().accept(this);
        this.out.append(")");
        this.out.append(" ");
      } else {
        this.out.append("<NO-INIT>");
      }

      this.out.append("{ ");
      this.out.forceLine();
      this.out.inc();

      expr.getBindings().forEach(v -> {

        if (v.getMember() == null) {
          this.out.append("[unnamed]");
        } else {
          this.out.append(v.getMember().getName());
        }

        this.out.append(" : ");
        if (v.getExpression().getType() == null) {
          this.out.append("<<NO-TYPE>>");
        } else {
          this.out.append(HTypeUtils.toString(v.getExpression().getType()));
        }

        this.out.append(" = ");
        if (v.getExpression() == null) {
          this.out.append("[uninitialized]");
        } else {
          v.getExpression().accept(this);
        }

        this.out.forceLine();

      });
      this.out.dec();
      this.out.append("}");
      return null;
    }

    @Override
    public Void visitLambda(HLambdaExpr expr) {
      this.out.append("(");
      this.out.append(expr.getParameters().stream().map(x -> x.toString()).collect(Collectors.joining(", ")));
      this.out.append(") : ");
      if (expr.getReturnType() != null) {
        this.out.append(expr.getReturnType().toString());
      } else {
        this.out.append("{null}");
      }
      this.out.append(" => ");
      if (expr.getBody() != null) {
        expr.getBody().accept(this);
      } else {
        this.out.append("{null}");
      }
      return null;
    }

    @Override
    public Void visitFilter(HFilterExpr expr) {
      throw new IllegalArgumentException();
    }

    @Override
    public Void visitMap(HMapExpr expr) {
      this.out.append("MAP [");
      expr.getSource().accept(this);
      this.out.append("] ---> ");
      expr.getMapper().accept(this);
      return null;
    }

    @Override
    public Void visitFold(HFoldExpr expr) {
      this.out.append("FOLD ");
      expr.getSource().accept(this);
      return null;
    }

    @Override
    public Void visitArrayInit(HArrayInitExpr expr) {
      this.out.append("NEW[] ");
      this.out.append(HTypeUtils.toString(expr.getComponentType()));
      this.out.append(" { ");
      int count = 0;
      for (final HExpr x : expr.getInitializers()) {
        if (count++ > 0) {
          this.out.append(", ");
        }
        x.accept(this);
      }
      this.out.append(" }");
      return null;
    }

    @Override
    public Void visitTypeUnary(HTypeUnaryExpr expr) {
      switch (expr.getExprKind()) {
        case TYPEOF:
          this.out.append("typeof(");
          this.out.append(expr.getTypeOperand().toString());
          this.out.append(")");
          return null;
        default:
          throw new IllegalArgumentException(expr.getExprKind().toString());
      }
    }

    @Override
    public Void visitExceptionFilter(HExceptionFilterExpr expr) {

      this.out.append("TRY ");

      this.out.inc();
      expr.getExpression().accept(this);
      this.out.dec();

      expr.getHandlers().forEach(handler -> {
        this.out.append("CATCH ");
        handler.getTestValues().forEach(c -> c.accept(this));
        this.out.inc();
        handler.getBody().accept(this);
        this.out.dec();
      });

      if (expr.getFaultExpression() != null) {
        this.out.append("FAULT ");
        this.out.inc();
        expr.getFaultExpression().accept(this);
        this.out.dec();
      }

      if (expr.getFinallyExpression() != null) {
        this.out.append("FINALLY ");
        this.out.inc();
        expr.getFinallyExpression().accept(this);
        this.out.dec();
      }

      return null;
    }

  }

}
