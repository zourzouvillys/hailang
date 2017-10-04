package io.zrz.hai.expr;

/**
 * An expression which performs a jump to another part of the expression tree.
 */

public interface HGotoExpr extends HExpr {

  /**
   * the kind of goto expression.
   */

  HGotoExprKind getGotoKind();

  /**
   * The target to jump to on evaluation of this expression.
   */

  HLabelTarget getLabelTarget();

  /**
   * The value to provide to the target of the label.
   */

  HExpr getValue();

}
