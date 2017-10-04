package io.zrz.hai.expr;

import io.zrz.hai.type.HType;

/**
 * base interface for all expression nodes.
 *
 * {@link HExpr} instances are always part of an executable, which is a bit of
 * code that is addressable. This can be a method, lambda, parameter default
 * values, or field initialisers. It can also be an anonymous block for
 * execution using the code runtime API.
 *
 */

public interface HExpr {

  /**
   * The kind of expression node.
   */

  HExprKind getExprKind();

  /**
   * The static type of the expression
   */

  HType getType();

  /**
   * invoke a method in the visitor based on the concrete type.
   */

  <R> R accept(HExprVisitor<R> visitor);

  /**
   *
   */

  <R> R accept(HExprKindVisitor<R> visitor);

}
