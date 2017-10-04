package io.zrz.hai.symbolic.expr;

import io.zrz.hai.symbolic.HTypeToken;
import io.zrz.hai.symbolic.type.HType;

/**
 * A constant expression value.
 */

public interface HConstExpr extends HExpr {

  /**
   * the type of this constant.
   */

  @Override
  HType getType();

  /**
   *
   */

  HTypeToken getTypeToken();

  /**
   * the value.
   */

  Object getValue();

}
