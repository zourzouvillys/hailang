package io.zrz.hai.syntax;

import io.zrz.hai.syntax.model.HaiScriptNode;

public class HaiScriptVisitException extends HaiScriptException {

  private static final long serialVersionUID = 1L;

  public HaiScriptVisitException(Exception ex, HaiScriptNode node) {
    super(ex, node);
  }

}
