package io.zrz.hai.symbolic;

import javax.annotation.Nullable;

import io.zrz.hai.symbolic.type.HType;

/**
 * each local variable has a type.
 */

public interface HLocalVariable {

  /**
   * the index of this local variable.
   */

  int getIndex();

  /**
   * the type of the variable.
   */

  HType getType();

  /**
   * the name that was defined for this variable, if any.
   */

  @Nullable
  String getName();

}
