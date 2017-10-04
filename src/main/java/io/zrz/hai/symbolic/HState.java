package io.zrz.hai.symbolic;

import io.zrz.hai.symbolic.expr.HExpr;
import io.zrz.hai.symbolic.type.HType;

/**
 * scalar values that represent the state of this node. aka fields/values.
 */

public interface HState extends HMember {

  /**
   * The name of this state property.
   */

  @Override
  String getName();

  /**
   * field modifiers.
   */

  @Override
  HModifiers getModifiers();

  /**
   * the type of the field. it may be a a primitive, ANY, STRUCT, or TUPLE - or an
   * array of.
   */

  HType getType();

  /**
   * the kind of member.
   */

  @Override
  default HMemberKind getMemberKind() {
    return HMemberKind.STATE;
  }

  /**
   * the default value.
   */

  HExpr getDefaultValue();

}
