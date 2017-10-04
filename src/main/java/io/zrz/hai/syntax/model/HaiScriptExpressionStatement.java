package io.zrz.hai.syntax.model;

import java.util.Objects;

import io.zrz.hai.syntax.HaiScriptStatementVisitor;
import lombok.Getter;

public class HaiScriptExpressionStatement extends AbstractHaiScriptStatement {

  @Getter
  private HaiScriptExpr expression;

  public HaiScriptExpressionStatement(HaiScriptExpr expr) {
    this.expression = expr;
  }

  public void setExpression(HaiScriptExpr expr) {
    this.expression = Objects.requireNonNull(expr);
  }

  @Override
  public <T, R> R apply(HaiScriptStatementVisitor<T, R> visitor, T arg) {
    return visitor.visitExprStatement(this, arg);
  }

  @Override
  public HaiScriptStatementKind getStatementKind() {
    return HaiScriptStatementKind.EXPR;
  }

  @Override
  public String toString() {
    return this.expression.toString();
  }

}
