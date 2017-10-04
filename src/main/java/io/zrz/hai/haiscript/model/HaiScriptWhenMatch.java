package io.zrz.hai.haiscript.model;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;

import io.zrz.hai.haiscript.HaiScriptNodeVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptWhenMatch extends AbstractHaiScriptNode {

  @Getter
  @Setter
  private List<HaiScriptExpr> expressions;

  @Getter
  @Setter
  private String operator;

  @Getter
  @Setter
  private HaiScriptStatement statement;

  public HaiScriptWhenMatch(HaiScriptExpr expr, HaiScriptStatement statement) {
    this.expressions = Lists.newArrayList(expr);
    this.statement = Objects.requireNonNull(statement);
  }

  public HaiScriptWhenMatch(List<HaiScriptExpr> collect, HaiScriptStatement statement) {
    this.expressions = collect;
    this.statement = Objects.requireNonNull(statement);
  }

  public HaiScriptWhenMatch(String operator, List<HaiScriptExpr> collect, HaiScriptStatement statement) {
    this.operator = operator;
    this.expressions = collect;
    this.statement = Objects.requireNonNull(statement);
  }

  public HaiScriptWhenMatch(HaiScriptStatement stmt) {
    this.statement = Objects.requireNonNull(stmt);
  }

  @Override
  public String toString() {
    if (this.expressions == null) {
      return "else";
    }
    return this.expressions.toString();
  }

  @Override
  public HaiScriptNodeKind getNodeKind() {
    return HaiScriptNodeKind.WHEN_MATCH;
  }

  @Override
  public <T, R> R apply(HaiScriptNodeVisitor<T, R> visitor, T arg) {
    return visitor.visitWhenMatch(this, arg);
  }

}
