package io.zrz.hai.expr;

/**
 * an expression which converts an iterable source into an array of values.
 *
 * it takes:
 *
 * <ul>
 * <li>source - list of items to fold</li>
 * <li>combiner - converts a single item in the list to a return value</li>
 * </ul>
 *
 */

public interface HFoldExpr extends HExpr {

  /**
   * the source expression which provides an iterable.
   */

  HExpr getSource();

}
