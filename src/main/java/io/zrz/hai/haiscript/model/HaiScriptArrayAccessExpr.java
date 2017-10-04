package io.zrz.hai.haiscript.model;

import java.util.List;

import com.google.common.collect.Lists;

import io.zrz.hai.haiscript.HaiScriptExpressionVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptArrayAccessExpr extends AbstractHaiScriptExpr {

  @Getter
  @Setter
  private HaiScriptExpr name;

  @Getter
  @Setter
  private List<HaiScriptExpr> index;

  public HaiScriptArrayAccessExpr(HaiScriptExpr name, HaiScriptExpr index) {
    this(name, Lists.newArrayList(index));
  }

  public HaiScriptArrayAccessExpr(HaiScriptExpr name, List<HaiScriptExpr> index) {
    this.name = name;
    this.index = index;
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.ARRAY_ACCESS;
  }

  @Override
  public String toString() {
    return this.name + this.index.toString();
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitArrayAccessExpr(this, arg);
  }

}
