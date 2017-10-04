package io.zrz.hai.haiscript.model;

import io.zrz.hai.haiscript.HaiScriptStatementVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptThrowStatement extends AbstractHaiScriptStatement {

  @Getter
  @Setter
  private HaiScriptExpr expression;

  public HaiScriptThrowStatement() {
  }

  public HaiScriptThrowStatement(HaiScriptExpr expression) {
    this.expression = expression;
  }

  @Override
  public HaiScriptStatementKind getStatementKind() {
    return HaiScriptStatementKind.THROW;
  }

  @Override
  public <T, R> R apply(HaiScriptStatementVisitor<T, R> visitor, T arg) {
    return visitor.visitThrowStatement(this, arg);
  }

  @Override
  public String toString() {
    if (this.expression == null) {
      return "throw";
    }
    return "throw " + this.expression;
  }

}
