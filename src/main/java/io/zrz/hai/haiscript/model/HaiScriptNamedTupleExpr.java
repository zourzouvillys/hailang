package io.zrz.hai.haiscript.model;

import io.zrz.hai.haiscript.HaiScriptExpressionVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptNamedTupleExpr extends AbstractHaiScriptExpr {

  @Getter
  @Setter
  private HaiScriptExpr key;

  @Getter
  @Setter
  private HaiScriptExpr value;

  public HaiScriptNamedTupleExpr(HaiScriptExpr key, HaiScriptExpr value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.NAMED_TUPLE;
  }

  @Override
  public String toString() {
    return this.key + ": " + this.value;
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitNamedTupleExpr(this, arg);
  }

}
