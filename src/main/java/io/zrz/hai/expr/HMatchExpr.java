package io.zrz.hai.expr;

import java.util.List;

import io.zrz.hai.type.HType;

/**
 * a conditional match expression. this is used for if/else, case, when, and the
 * elvis operator - anywhere that there is a branch based on an expression.
 *
 * it is similar in conception to a binary expression, except the right hand
 * side has many values, and whichever matches results in the associated branch
 * being chosen.
 *
 * to use the result of the match value in a resulting expression, assign to a
 * temporary var in the match, and then reference the var in the cases.
 *
 */

public interface HMatchExpr extends HExpr {

  /**
   * the expression which provides the value to be tested against. it is evaluated
   * just once and then used in the case instances to test. if a body of a match
   * case is null, then the value of this will be the result of the expression.
   *
   * if there is not a match test, then constant true can be used, and each
   * expression will be evaluated independently to test for boolean true.
   *
   */

  HExpr getMatchValue();

  /**
   * the operator which is used to compare the match value with each match case
   * expression. It must be a valid binary operator, and may be EQ if the match
   * value is true.
   */

  HExprKind getOperator();

  /**
   * the cases we can match against. each case has a list of expressions to test,
   * and a expression body.
   *
   * order is important: each case is evalulated in the order they are in the
   * list.
   *
   */

  List<? extends HMatchCase> getCases();

  /**
   * the default expression to be used if there is no match in any of the cases.
   */

  HExpr getDefaultExpression();

  /**
   * the type of this expression is the union of the types of any of the possible
   * cases.
   */

  @Override
  HType getType();

}
