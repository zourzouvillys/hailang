package io.zrz.hai.symbolic.expr;

import io.zrz.hai.symbolic.type.HType;

/**
 * A {@link HExpr} that takes just type. Used for TYPEOF.
 */

public interface HTypeUnaryExpr extends HExpr {

  /**
   * The type operand.
   */

  HType getTypeOperand();

}
