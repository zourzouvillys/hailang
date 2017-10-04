package io.zrz.hai.symbolic.expr;

/**
 * An expression which operates on a single expression.
 */

public interface HUnaryExpr extends HExpr {

  HExpr getExpression();

}
