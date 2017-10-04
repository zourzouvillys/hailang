package io.zrz.hai.symbolic;

import io.zrz.hai.symbolic.expr.HExpr;
import io.zrz.hai.symbolic.type.HDeclType;

/**
 * A named member slot that is filled with a reference to another entity. it is
 * just a field with the type set to a decltype rather than primitive. however,
 * in the compiler we treat it separately for ease of API use.
 */

public interface HLink extends HMember {

  /**
   * the name of this link.
   */

  @Override
  String getName();

  /**
   * The type that this link contains. It may be a union or interface.
   */

  HDeclType getType();

  /**
   *
   */

  HExpr getDefaultExpr();

  /**
   * type is a link
   */

  @Override
  default HMemberKind getMemberKind() {
    return HMemberKind.LINK;
  }

}
