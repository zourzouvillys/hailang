package io.zrz.hai.haiscript;

import io.zrz.hai.haiscript.model.HaiScriptNode;

public class HaiScriptVisitException extends HaiScriptException {

  private static final long serialVersionUID = 1L;

  public HaiScriptVisitException(Exception ex, HaiScriptNode node) {
    super(ex, node);
  }

}
