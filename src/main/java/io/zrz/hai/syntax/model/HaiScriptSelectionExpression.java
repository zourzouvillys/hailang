package io.zrz.hai.syntax.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;

import io.zrz.hai.syntax.HaiScriptExpressionVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptSelectionExpression extends AbstractHaiScriptExpr {

  /**
   */

  @Getter
  @Setter
  private HaiScriptNode name;

  @Getter
  @Setter
  private List<HaiScriptNode> selections;

  public HaiScriptSelectionExpression() {
    this.name = null;
    this.selections = new LinkedList<>();
  }

  public HaiScriptSelectionExpression(HaiScriptNode name, List<HaiScriptNode> selection) {
    this.name = name;
    this.selections = selection;
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.SELECTION;
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitSelectionExpr(this, arg);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append(this.name);
    sb.append(Joiner.on(", ").join(this.selections.stream().map(x -> x.getClass()).collect(Collectors.toList())));
    return sb.toString();
  }

}
