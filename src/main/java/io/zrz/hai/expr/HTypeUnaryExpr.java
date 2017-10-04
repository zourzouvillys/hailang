package io.zrz.hai.expr;

import io.zrz.hai.type.HType;

/**
 * A {@link HExpr} that takes just type. Used for TYPEOF.
 */

public interface HTypeUnaryExpr extends HExpr {

  /**
   * The type operand.
   */

  HType getTypeOperand();

}
