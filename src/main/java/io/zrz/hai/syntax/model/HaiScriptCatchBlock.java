package io.zrz.hai.syntax.model;

import io.zrz.hai.lang.TypeRef;
import io.zrz.hai.syntax.HaiScriptNodeVisitor;
import io.zrz.hai.syntax.HaiSymbol;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptCatchBlock extends AbstractHaiScriptNode implements HaiScriptNode {

  @Getter
  @Setter
  private TypeRef type;

  @Getter
  @Setter
  private HaiSymbol symbol;

  @Getter
  @Setter
  private HaiScriptStatement body;

  @Override
  public HaiScriptNodeKind getNodeKind() {
    return HaiScriptNodeKind.CATCH;
  }

  @Override
  public <T, R> R apply(HaiScriptNodeVisitor<T, R> visitor, T arg) {
    return visitor.visitCatch(this, arg);
  }

}
