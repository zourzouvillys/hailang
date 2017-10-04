package io.zrz.hai.haiscript.model;

import java.util.Iterator;
import java.util.LinkedList;

import io.zrz.hai.haiscript.HaiScriptExpressionVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptBranchExpr extends AbstractHaiScriptExpr {

  @Getter
  @Setter
  private LinkedList<HaiScriptWhenMatch> conditions = new LinkedList<>();

  @Getter
  @Setter
  private HaiScriptStatement otherwise;

  public HaiScriptBranchExpr() {
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.CONDITIONAL;
  }

  public void add(HaiScriptWhenMatch entry) {
    this.conditions.add(entry);
  }

  public void otherwise(HaiScriptStatement otherwise) {
    this.otherwise = otherwise;
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitBranchExpr(this, arg);
  }

  @Override
  public String toString() {

    final StringBuilder sb = new StringBuilder();

    final Iterator<HaiScriptWhenMatch> it = this.conditions.iterator();

    int count = 0;

    while (it.hasNext()) {

      final HaiScriptWhenMatch e = it.next();

      if (count++ == 0) {
        sb.append("if ");
      }

      sb.append(e.getExpressions());

      sb.append("\n  ");

      sb.append(e.getStatement());

    }

    if (this.otherwise != null) {
      sb.append("\nelse\n");
      sb.append("  ");
      sb.append(this.otherwise);
    }

    return sb.toString();

  }

}
