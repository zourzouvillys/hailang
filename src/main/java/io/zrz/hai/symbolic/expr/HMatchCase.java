package io.zrz.hai.symbolic.expr;

import java.util.List;

public interface HMatchCase {

  /**
   * the values to test.
   *
   * The expressions here may be a {@link HUnaryExpr} or {@link HTypeUnaryExpr}
   * expression with a {@link HExprKind} that represents a binary operator. In
   * this case, the expression is tested against the contextual value.
   *
   */

  List<? extends HExpr> getTestValues();

  /**
   * the body that provides the expression.
   */

  HExpr getBody();

}
