package io.zrz.hai.expr;

import io.zrz.hai.type.HMember;

public interface HMemberAssignment extends HMemberBinding {

  /**
   * the member to assign
   */

  HMember getMember();

  /**
   * the expression to assign.
   */

  HExpr getExpression();

}
