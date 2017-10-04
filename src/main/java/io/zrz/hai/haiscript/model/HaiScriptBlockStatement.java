package io.zrz.hai.haiscript.model;

import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Joiner;

import io.zrz.hai.haiscript.HaiScriptStatementVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptBlockStatement extends AbstractHaiScriptStatement {

  @Getter
  @Setter
  private List<HaiScriptStatement> statements;

  public HaiScriptBlockStatement(List<HaiScriptStatement> statements) {
    this.statements = statements;
  }

  // an empty block with no statements
  public HaiScriptBlockStatement() {
    this.statements = new LinkedList<>();
  }

  @Override
  public HaiScriptStatementKind getStatementKind() {
    return HaiScriptStatementKind.BLOCK;
  }

  @Override
  public String toString() {
    return "{\n  " + Joiner.on(";\n  ").join(this.statements) + ";\n}\n";
  }

  @Override
  public <T, R> R apply(HaiScriptStatementVisitor<T, R> visitor, T arg) {
    return visitor.visitBlockStatement(this, arg);
  }

}
