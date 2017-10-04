package io.zrz.hai.symbolic;

import io.zrz.hai.symbolic.expr.HExpr;
import io.zrz.hai.symbolic.type.HType;

/**
 * A placeholder for a parameter.
 */

public interface HParameter {

  /**
   * The index of this parameter.
   */

  int getIndex();

  /**
   * If the parameter has a name.
   */

  String getName();

  /**
   * the parameter type.
   */

  HType getType();

  /**
   * the default value for the parameter.
   */

  HExpr getDefaultValue();

  /**
   * A runtime filter expression that limits the content of the parameter value.
   */

  HExpr getFilter();

  /**
   * if this parameter is optional.
   */

  boolean isOptional();

}
