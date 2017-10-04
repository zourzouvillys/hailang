package io.zrz.hai.haiscript.model;

import io.zrz.hai.haiscript.HaiScriptExpressionVisitor;
import io.zrz.hai.lang.TypeRef;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptDeclExpr extends AbstractHaiScriptExpr {

  @Getter
  @Setter
  private String symbol;

  @Getter
  @Setter
  private TypeRef declaredType;

  @Getter
  @Setter
  private HaiScriptExpr initalizer;

  @Getter
  @Setter
  private boolean variable;

  public HaiScriptDeclExpr(boolean variable, String symbol, TypeRef type, HaiScriptExpr initializer) {
    this.variable = variable;
    this.symbol = symbol;
    this.declaredType = type;
    this.initalizer = initializer;
  }

  public HaiScriptDeclExpr(boolean variable, String symbol, HaiScriptExpr initializer) {
    this.variable = variable;
    this.symbol = symbol;
    this.declaredType = null;
    this.initalizer = initializer;
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.DECLARATION;
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitDeclExpr(this, arg);
  }

  @Override
  public String toString() {
    return (this.variable ? "var" : "const") + " " + this.symbol + (this.declaredType != null ? (" : " + this.declaredType) : "") + " = " + this.initalizer;
  }

}
