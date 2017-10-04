package io.zrz.hai.haiscript.model;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Objects;

import io.zrz.hai.haiscript.HaiScriptExpressionVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptWhenExpr extends AbstractHaiScriptExpr {

  @Getter
  @Setter
  private HaiScriptExpr condition;

  @Getter
  private final LinkedHashMap<HaiScriptWhenMatch, HaiScriptStatement> matches = new LinkedHashMap<>();

  public HaiScriptWhenExpr(HaiScriptExpr expr) {
    this.condition = expr;
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.WHEN;
  }

  public void add(HaiScriptWhenMatch match, HaiScriptStatement stmt) {
    Objects.requireNonNull(stmt);
    this.matches.put(match, stmt);
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitWhenExpr(this, arg);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();

    sb.append("when ");
    sb.append(this.condition);
    sb.append(" {\n");

    for (final Entry<HaiScriptWhenMatch, HaiScriptStatement> e : this.matches.entrySet()) {

      sb.append("  ");
      sb.append(e.getKey());
      sb.append(" => ");
      sb.append(e.getValue());
      sb.append("\n");

    }

    sb.append("}\n");

    return sb.toString();
  }

}
