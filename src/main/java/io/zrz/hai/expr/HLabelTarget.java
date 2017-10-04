package io.zrz.hai.expr;

import io.zrz.hai.type.HType;

/**
 * A label target that is shared between expressions.
 */

public interface HLabelTarget {

  /**
   * the type of this jump target.
   */

  HType getType();

}
