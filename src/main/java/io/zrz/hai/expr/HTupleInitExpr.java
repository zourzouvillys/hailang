package io.zrz.hai.expr;

import java.util.List;
import java.util.OptionalInt;

import io.zrz.hai.type.HTupleType;

public interface HTupleInitExpr extends HExpr {

  /**
   *
   */

  @Override
  HTupleType getType();

  /**
   * Initialise each of the tuple members with the given expressions
   */

  List<? extends HExpr> getInitializers();

  //

  @Override
  default HExprKind getExprKind() {
    return HExprKind.TUPLE_INIT;
  }

  /**
   *
   */

  HExpr expr(int i);

  /**
   * fetch an expression value by tuple name
   */

  default HExpr expr(String name) {
    final OptionalInt idx = getType().index(name);
    if (idx.isPresent()) {
      return expr(idx.getAsInt());
    }
    throw new IllegalArgumentException(String.format("no such param: %s", name));
  }

}
