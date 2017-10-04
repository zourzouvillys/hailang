package io.zrz.hai.syntax;

import org.antlr.v4.runtime.tree.TerminalNode;

import io.zrz.hai.syntax.model.AbstractHaiScriptNode;
import io.zrz.hai.syntax.model.HaiScriptNodeKind;

public class HaiScriptParameterSpreadNode extends AbstractHaiScriptNode {

  private final String ident;

  public HaiScriptParameterSpreadNode(TerminalNode ident) {
    if (ident != null) {
      this.ident = ident.getText();
    } else {
      this.ident = null;
    }
  }

  public HaiScriptParameterSpreadNode(String text) {
    this.ident = text;
  }

  @Override
  public HaiScriptNodeKind getNodeKind() {
    return HaiScriptNodeKind.PARAM_SPREAD;
  }

  @Override
  public <T, R> R apply(HaiScriptNodeVisitor<T, R> visitor, T arg) {
    return visitor.visitParameterSpread(this, arg);
  }

  public String getAlias() {
    return this.ident;
  }

  @Override
  public String toString() {
    return "..." + (this.ident != null ? (" as " + this.ident) : "");
  }

}
