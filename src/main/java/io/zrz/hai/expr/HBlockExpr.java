package io.zrz.hai.expr;

import java.util.List;

import io.zrz.hai.type.HType;

/**
 * a collection of expressions.
 */

public interface HBlockExpr extends HExpr {

  /**
   * the expressions that are in this block.
   */

  List<? extends HExpr> getExpressions();

  /**
   * the type of the value returned by this block. the type is taken from the last
   * expression.
   */

  @Override
  HType getType();

}
