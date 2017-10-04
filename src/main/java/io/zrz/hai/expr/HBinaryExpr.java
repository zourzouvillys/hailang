package io.zrz.hai.expr;

/**
 * Most {@link HExprKind} types that takes two operands.
 */

public interface HBinaryExpr extends HExpr {

  HExpr getLeft();

  HExpr getRight();

}
