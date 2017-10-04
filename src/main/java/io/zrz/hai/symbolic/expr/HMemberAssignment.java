package io.zrz.hai.symbolic.expr;

import io.zrz.hai.symbolic.HMember;

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
