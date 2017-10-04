package io.zrz.hai.syntax.model;

import io.zrz.hai.syntax.HaiScriptStatementVisitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
public class HaiScriptForStatement extends AbstractHaiScriptStatement {

  @Getter
  @Setter
  private HaiScriptExpr decl;

  @Getter
  @Setter
  private HaiScriptExpr expr;

  @Getter
  @Setter
  private HaiScriptStatement statement;

  public HaiScriptForStatement(HaiScriptExpr decl, HaiScriptExpr expr, HaiScriptStatement statement) {
    this.decl = decl;
    this.expr = expr;
    this.statement = statement;
  }

  @Override
  public <T, R> R apply(HaiScriptStatementVisitor<T, R> visitor, T arg) {
    return visitor.visitForStatement(this, arg);
  }

  @Override
  public HaiScriptStatementKind getStatementKind() {
    return HaiScriptStatementKind.FOR;
  }

  @Override
  public String toString() {

    return "for " + this.decl + " in " + this.expr + "\n  " + this.statement;

  }

}
