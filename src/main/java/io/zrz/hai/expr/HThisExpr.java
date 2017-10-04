package io.zrz.hai.expr;

/**
 * expression which references the current execution scope instance.
 */

public interface HThisExpr extends HExpr {

  @Override
  default HExprKind getExprKind() {
    return HExprKind.THIS;
  }

}
