package io.zrz.hai.symbolic.expr.impl;

import java.util.List;

import io.zrz.hai.symbolic.expr.HExpr;
import io.zrz.hai.symbolic.expr.HExprKindVisitor;
import io.zrz.hai.symbolic.expr.HExprVisitor;
import io.zrz.hai.symbolic.expr.HTupleInitExpr;
import io.zrz.hai.symbolic.type.HTupleType;

public class TupleInitExprImpl implements HTupleInitExpr {

  private final HTupleType type;
  private final List<? extends HExpr> initializers;

  public TupleInitExprImpl(HTupleType type, List<? extends HExpr> initializers) {
    this.type = type;
    this.initializers = initializers;
  }

  @Override
  public List<? extends HExpr> getInitializers() {
    return this.initializers;
  }

  @Override
  public HExpr expr(int i) {
    return this.initializers.get(i);
  }

  @Override
  public HTupleType getType() {
    return this.type;
  }

  @Override
  public <R> R accept(HExprVisitor<R> visitor) {
    return visitor.visitTupleInit(this);
  }

  @Override
  public <R> R accept(HExprKindVisitor<R> visitor) {
    return visitor.visitTupleInit(this);
  }

}
