package io.zrz.hai.syntax.model;

import io.zrz.hai.syntax.HaiScriptExpressionVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptLambdaExpr extends AbstractHaiScriptExpr {

  @Getter
  @Setter
  private HaiScriptStatement statement;

  @Getter
  @Setter
  private HaiScriptParameterListDecl parameters;

  public HaiScriptLambdaExpr(HaiScriptParameterListDecl params, HaiScriptStatement statement) {
    this.parameters = params;
    this.statement = statement;
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.LAMBDA;
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitLambdaExpr(this, arg);
  }

  @Override
  public String toString() {
    return this.parameters + " => " + this.statement;
  }

}
