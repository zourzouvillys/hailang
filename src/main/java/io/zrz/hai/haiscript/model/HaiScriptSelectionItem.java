package io.zrz.hai.haiscript.model;

import io.zrz.hai.haiscript.HaiScriptNodeVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptSelectionItem extends AbstractHaiScriptNode {

  @Getter
  @Setter
  private String alias;

  @Getter
  @Setter
  private HaiScriptExpr expression;

  public HaiScriptSelectionItem(String alias, HaiScriptExpr expr) {
    this.alias = alias;
    this.expression = expr;
  }

  public HaiScriptSelectionItem(HaiScriptExpr expr) {
    this.expression = expr;
  }

  @Override
  public HaiScriptNodeKind getNodeKind() {
    return HaiScriptNodeKind.SELECTION_ITEM;
  }

  @Override
  public <T, R> R apply(HaiScriptNodeVisitor<T, R> visitor, T arg) {
    return visitor.visitSelectionItem(this, arg);
  }

  @Override
  public String toString() {
    return (this.alias != null ? this.alias + ": " : "") + this.expression;
  }

}
