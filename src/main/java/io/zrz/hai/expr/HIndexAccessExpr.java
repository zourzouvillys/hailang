package io.zrz.hai.expr;

import java.util.List;

import javax.annotation.Nullable;

/**
 * an expression which references a member of a type.
 */

public interface HIndexAccessExpr extends HExpr {

  /**
   * the expression which refers to the instance.
   *
   * this may be null in a static expression.
   *
   */

  @Nullable
  HExpr getExpression();

  /**
   * the indexing expression(s)
   */

  List<? extends HExpr> getIndexers();

}
