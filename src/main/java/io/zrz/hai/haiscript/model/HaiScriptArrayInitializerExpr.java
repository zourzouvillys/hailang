package io.zrz.hai.haiscript.model;

import java.util.List;

import com.google.common.collect.Lists;

import io.zrz.hai.haiscript.HaiScriptExpressionVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptArrayInitializerExpr extends AbstractHaiScriptExpr {

  @Getter
  @Setter
  private List<HaiScriptExpr> elements;

  public HaiScriptArrayInitializerExpr(List<HaiScriptExpr> list) {
    this.elements = list;
  }

  public HaiScriptArrayInitializerExpr() {
    this.elements = Lists.newArrayList();
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.ARRAY_INITIALIZER;
  }

  @Override
  public String toString() {
    return this.elements.toString();
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitArrayInitializerExpr(this, arg);
  }

}
