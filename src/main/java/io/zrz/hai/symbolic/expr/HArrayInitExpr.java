package io.zrz.hai.symbolic.expr;

import java.util.List;

import io.zrz.hai.symbolic.type.HType;

public interface HArrayInitExpr extends HExpr {

  /**
   * The component type of the array
   */

  HType getComponentType();

  /**
   * The initializers
   */

  List<? extends HExpr> getInitializers();

  /**
   *
   */

  @Override
  default HExprKind getExprKind() {
    return HExprKind.NEW_ARRAY;
  }

  @Override
  default <R> R accept(HExprKindVisitor<R> visitor) {
    return visitor.visitNewArray(this);
  }

}
