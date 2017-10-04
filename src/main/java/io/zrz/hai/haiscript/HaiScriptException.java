package io.zrz.hai.haiscript;

import io.zrz.hai.haiscript.model.HaiScriptNode;
import lombok.Getter;

public class HaiScriptException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  @Getter
  private final HaiScriptNode node;

  public HaiScriptException(Exception ex, HaiScriptNode node) {
    super(ex);
    this.node = node;
  }

  @Override
  public String getMessage() {
    if (this.node != null && this.node.getSource() != null) {
      return String.format("at %s: %s", this.node.getSource(), super.getMessage());
    }
    return super.getMessage();
  }

}
