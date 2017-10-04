package io.zrz.hai.expr;

/**
 * applies a mapping function to each item in a source of iterables.
 *
 * <ul>
 * <li>source - list of items to apply the mapper to</li>
 * <li>mapper - an invocable which converts the element to the resulting
 * type</li>
 * </ul>
 *
 */

public interface HMapExpr extends HExpr {

  /**
   * the source expression which provides an iterable.
   */

  HExpr getSource();

  /**
   *
   */

  HExpr getMapper();

}
