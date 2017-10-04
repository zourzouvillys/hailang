package io.zrz.hai.symbolic.expr;

import javax.annotation.Nullable;

import io.zrz.hai.symbolic.type.HType;

/**
 * a handle for a variable (local or parameter) in an expression tree.
 *
 * all HVarExpr instances will reference the same instance of a {@link HVar} if
 * they refer to the same label.
 *
 */

public interface HVar {

  /**
   * the statically declared (or inferred) type for this variable.
   */

  HType getType();

  /**
   * The symbol given to this variable, if any.
   */

  @Nullable
  String getName();

}
