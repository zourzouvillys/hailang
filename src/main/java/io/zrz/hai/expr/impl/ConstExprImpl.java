package io.zrz.hai.expr.impl;

import io.zrz.hai.expr.HConstExpr;
import io.zrz.hai.expr.HExprKind;
import io.zrz.hai.expr.HExprKindVisitor;
import io.zrz.hai.expr.HExprVisitor;
import io.zrz.hai.type.HType;
import io.zrz.hai.type.HTypeToken;

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
