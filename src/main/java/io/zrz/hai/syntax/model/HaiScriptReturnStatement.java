package io.zrz.hai.syntax.model;

import io.zrz.hai.syntax.HaiScriptStatementVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptReturnStatement extends AbstractHaiScriptStatement {

  @Getter
  @Setter
  private HaiScriptExpr returnValue;

  public HaiScriptReturnStatement() {
  }

  public HaiScriptReturnStatement(HaiScriptExpr returnValue) {
    this.returnValue = returnValue;
  }

  @Override
  public HaiScriptStatementKind getStatementKind() {
    return HaiScriptStatementKind.RETURN;
  }

  @Override
  public <T, R> R apply(HaiScriptStatementVisitor<T, R> visitor, T arg) {
    return visitor.visitReturnStatement(this, arg);
  }

  @Override
  public String toString() {
    if (this.returnValue == null) {
      return "return";
    }
    return "return " + this.returnValue;
  }

}
