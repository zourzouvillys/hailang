package io.zrz.hai.symbolic.expr.impl;

import io.zrz.hai.symbolic.expr.HExprKind;
import io.zrz.hai.symbolic.expr.HExprKindVisitor;
import io.zrz.hai.symbolic.expr.HExprVisitor;
import io.zrz.hai.symbolic.expr.HVar;
import io.zrz.hai.symbolic.expr.HVarExpr;
import io.zrz.hai.symbolic.type.HType;
import lombok.Getter;

public class VarExprImpl implements HVarExpr {

  @Getter
  private final String name;

  @Getter
  private final HType type;

  public VarExprImpl(String name, HType type) {
    this.name = name;
    this.type = type;
  }

  @Override
  public HExprKind getExprKind() {
    return HExprKind.VAR;
  }

  @Override
  public <R> R accept(HExprVisitor<R> visitor) {
    return visitor.visitVar(this);
  }

  @Override
  public <R> R accept(HExprKindVisitor<R> visitor) {
    return visitor.visitVar(this);
  }

  @Override
  public HVar getVariable() {
    return new VarImpl(this.name, this.type);
  }

}
