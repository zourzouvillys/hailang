package io.zrz.hai.symbolic.expr;

import java.util.Collection;

/**
 * Initialises an instance of a type and the members in a single expression.
 *
 * This is used to instantiate anonymous structs which are generated from an
 * incoming operation, as well as return values which are selections.
 *
 */

public interface HMemberInitExpr extends HExpr {

  /**
   * The expression to provide the new instance to set the members of. must be a
   * new expression.
   */

  HExpr getNewExpr();

  /**
   * The members to intiialize.
   */

  Collection<? extends HMemberBinding> getBindings();

}
