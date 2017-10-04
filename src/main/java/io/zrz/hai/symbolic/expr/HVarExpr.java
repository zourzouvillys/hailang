package io.zrz.hai.symbolic.expr;

import io.zrz.hai.symbolic.type.HType;

/**
 * a reference to a local variable or parameter.
 */

public interface HVarExpr extends HExpr {

  /**
   * the variable which this expresson references.
   */

  HVar getVariable();

  /**
   * The type of the variable.
   */

  @Override
  HType getType();

}
