package io.zrz.hai.expr;

import io.zrz.hai.type.HMember;

public interface HMemberBinding {

  HExpr getExpression();

  HMember getMember();

}
