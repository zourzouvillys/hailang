package io.zrz.hai.symbolic.expr;

import io.zrz.hai.symbolic.type.HType;

/**
 * A {@link HExpr} that takes a type and an expression.
 *
 * the cast, new, and 'is' expressions use this interface.
 *
 */

public interface HTypeBinaryExpr extends HExpr {

  /**
   * The type operand.
   */

  HType getTypeOperand();

  /**
   * The expression operand.
   */

  HExpr getExpression();

}
