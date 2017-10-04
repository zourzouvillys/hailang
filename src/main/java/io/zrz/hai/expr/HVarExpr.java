package io.zrz.hai.expr;

import io.zrz.hai.type.HType;

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
