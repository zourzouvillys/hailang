package io.zrz.hai.symbolic.expr;

/**
 * filters an iterable to only include specific items.
 *
 * <ul>
 * <li>source - list of items to apply the mapper to</li>
 * <li>predicate - an invocable which returns true to include the given item in
 * the resulting expression</li>
 * </ul>
 *
 */

public interface HFilterExpr extends HExpr {

  /**
   * the source expression which provides an iterable.
   */

  HExpr getSource();

  /**
   * an invocable which evaluates to true if the item should be included.
   */

  HExpr getPredicate();

}
