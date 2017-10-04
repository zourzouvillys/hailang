package io.zrz.hai.expr;

import io.zrz.hai.type.HType;
import io.zrz.hai.type.HTypeToken;

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
