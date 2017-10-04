package io.zrz.hai.syntax.model;

import io.zrz.hai.syntax.HaiScriptStatementVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptWithStatement extends AbstractHaiScriptStatement {

  @Getter
  @Setter
  private HaiScriptExpr event;

  @Getter
  @Setter
  private HaiScriptExpr args;

  @Getter
  @Setter
  private HaiScriptStatement block;

  public HaiScriptWithStatement(HaiScriptExpr event, HaiScriptExpr args, HaiScriptStatement block) {
    this.event = event;
    this.args = args;
    this.block = block;
  }

  @Override
  public HaiScriptStatementKind getStatementKind() {
    return HaiScriptStatementKind.WITH;
  }

  @Override
  public <T, R> R apply(HaiScriptStatementVisitor<T, R> visitor, T arg) {
    return visitor.visitWithStatement(this, arg);
  }

}
