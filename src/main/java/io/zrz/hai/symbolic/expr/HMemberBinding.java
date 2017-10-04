package io.zrz.hai.symbolic.expr;

import io.zrz.hai.symbolic.HMember;

public interface HMemberBinding {

  HExpr getExpression();

  HMember getMember();

}
