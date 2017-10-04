package io.zrz.hai.expr;

/**
 * an expression in the expression tree which is the receiver for gotos.
 *
 * the most common use case for this is the RETURN label, which is the last
 * expression at the end of an executable block.
 *
 */

public interface HLabelExpr extends HExpr {

  /**
   * the value to be provided if the expression is reached using normal flow
   * control rather than a ump.
   */

  HExpr getDefaultValue();

  /**
   * The label target which this expression is the target for.
   */

  HLabelTarget getLabelTarget();

}
