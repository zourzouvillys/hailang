package io.zrz.hai.syntax.model;

import io.zrz.hai.syntax.HaiScriptExpressionVisitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
public class HaiScriptUnaryExpr extends AbstractHaiScriptExpr {

  @Getter
  @Setter
  private String operator;

  @Getter
  @Setter
  private HaiScriptExpr expr;

  public HaiScriptUnaryExpr(String operator, HaiScriptExpr expr) {
    this.operator = operator;
    this.expr = expr;
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.UNARY;
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitUnaryExpr(this, arg);
  }

  @Override
  public String toString() {
    return this.operator + this.expr;
  }

}
