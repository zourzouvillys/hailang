package io.zrz.hai.expr;

import java.util.List;

/**
 * an expression which filters inner expressions, catching specific (or all)
 * exceptions, and having a finally & fault block.
 */

public interface HExceptionFilterExpr extends HExpr {

  /**
   * the expression to execute, and catch any faults in.
   */

  HExpr getExpression();

  /**
   * handlers that will catch exceptions.
   */

  List<HMatchCase> getHandlers();

  /**
   * an expression to run after the handlers.
   */

  HExpr getFaultExpression();

  /**
   * a statement to run after filters and faults.
   */

  HExpr getFinallyExpression();

  /**
   *
   */

  @Override
  default HExprKind getExprKind() {
    return HExprKind.EXCEPTION_FILTER;
  }

  @Override
  default <R> R accept(HExprVisitor<R> visitor) {
    return visitor.visitExceptionFilter(this);
  }

}
