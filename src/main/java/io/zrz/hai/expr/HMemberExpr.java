package io.zrz.hai.expr;

import javax.annotation.Nullable;

import io.zrz.hai.type.HMember;

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
