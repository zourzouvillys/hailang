package io.zrz.hai.symbolic.expr.impl;

import io.zrz.hai.symbolic.HTypeToken;
import io.zrz.hai.symbolic.expr.HConstExpr;
import io.zrz.hai.symbolic.expr.HExprKind;
import io.zrz.hai.symbolic.expr.HExprKindVisitor;
import io.zrz.hai.symbolic.expr.HExprVisitor;
import io.zrz.hai.symbolic.type.HType;

public class ConstExprImpl implements HConstExpr {

  private final HTypeToken type;
  private final Object value;

  public ConstExprImpl(HTypeToken type, Object value) {
    this.type = type;
    this.value = value;
  }

  @Override
  public HExprKind getExprKind() {
    return HExprKind.CONST;
  }

  @Override
  public HType getType() {
    throw new IllegalArgumentException();
  }

  @Override
  public HTypeToken getTypeToken() {
    return this.type;
  }

  @Override
  public Object getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.type + "('" + this.value + "')";
  }

  @Override
  public <R> R accept(HExprKindVisitor<R> visitor) {
    return visitor.visitConst(this);
  }

  @Override
  public <R> R accept(HExprVisitor<R> visitor) {
    return visitor.visitConst(this);
  }

}
