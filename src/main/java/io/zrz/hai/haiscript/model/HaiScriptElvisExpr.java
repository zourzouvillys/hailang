package io.zrz.hai.haiscript.model;

import io.zrz.hai.haiscript.HaiScriptExpressionVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptElvisExpr extends AbstractHaiScriptExpr {

  @Getter
  @Setter
  private HaiScriptExpr condition;

  @Getter
  @Setter
  private HaiScriptExpr then;

  @Getter
  @Setter
  private HaiScriptExpr otherwise;

  public HaiScriptElvisExpr(HaiScriptExpr condition, HaiScriptExpr then, HaiScriptExpr otherwise) {
    this.condition = condition;
    this.then = then;
    this.otherwise = otherwise;
  }

  public HaiScriptElvisExpr(HaiScriptExpr condition, HaiScriptExpr otherwise) {
    this.condition = condition;
    this.otherwise = otherwise;
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.CONDITIONAL;
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitElvisExpr(this, arg);
  }

}
