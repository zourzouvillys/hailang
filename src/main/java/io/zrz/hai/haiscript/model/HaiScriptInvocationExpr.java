package io.zrz.hai.haiscript.model;

import io.zrz.hai.haiscript.HaiScriptExpressionVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptInvocationExpr extends AbstractHaiScriptExpr {

  /**
   * the base expression, that resolves to a HaiScriptMemberAccess that provides
   * the member and base.
   */

  @Getter
  @Setter
  private HaiScriptExpr nameExpr;

  /**
   * the argument values.
   */

  @Getter
  @Setter
  private HaiScriptExpr argsExpr;

  public HaiScriptInvocationExpr(HaiScriptExpr name, HaiScriptExpr args) {
    this.nameExpr = name;
    this.argsExpr = args;
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.INVOCATION;
  }

  @Override
  public String toString() {
    return this.nameExpr.toString() + this.argsExpr;
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitInvocationExpr(this, arg);
  }

}
