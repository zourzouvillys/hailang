package io.zrz.hai.haiscript.model;

import io.zrz.hai.haiscript.HaiScriptExpressionVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptBinaryExpr extends AbstractHaiScriptExpr {

  @Getter
  @Setter
  private String operator;

  @Getter
  @Setter
  private HaiScriptExpr leftExpr;

  @Getter
  @Setter
  private HaiScriptExpr rightExpr;

  public HaiScriptBinaryExpr(String operator, HaiScriptExpr left, HaiScriptExpr right) {
    this.operator = operator;
    this.leftExpr = left;
    this.rightExpr = right;
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.BINARY;
  }

  @Override
  public String toString() {
    return "BINARY_EXPR ( " + this.leftExpr + " " + this.operator + " " + this.rightExpr + " )";
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitBinaryExpr(this, arg);
  }

}
