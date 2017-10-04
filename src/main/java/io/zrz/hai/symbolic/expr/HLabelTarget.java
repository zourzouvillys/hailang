package io.zrz.hai.symbolic.expr;

import io.zrz.hai.symbolic.type.HType;

/**
 * A label target that is shared between expressions.
 */

public interface HLabelTarget {

  /**
   * the type of this jump target.
   */

  HType getType();

}
