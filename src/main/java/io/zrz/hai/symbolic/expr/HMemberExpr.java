package io.zrz.hai.symbolic.expr;

import javax.annotation.Nullable;

import io.zrz.hai.symbolic.HMember;

/**
 * an expression which references a member of a type.
 */

public interface HMemberExpr extends HExpr {

  /**
   * the expression which refers to the instance.
   *
   * this may be null in a static expression.
   *
   */

  @Nullable
  HExpr getExpression();

  /**
   * the member which this expression is referring to.
   */

  HMember getMember();

}
