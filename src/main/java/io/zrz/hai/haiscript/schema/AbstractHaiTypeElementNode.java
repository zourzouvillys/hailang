package io.zrz.hai.haiscript.schema;

import io.zrz.hai.haiscript.HaiScriptCommentNode;
import io.zrz.hai.haiscript.model.HaiScriptSourceInfo;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractHaiTypeElementNode implements HaiTypeElementNode {

  @Getter
  private HaiScriptSourceInfo sourceInfo;

  @Override
  public HaiTypeElementNode setSourceInfo(HaiScriptSourceInfo source) {
    this.sourceInfo = source;
    return this;
  }

  @Getter
  @Setter
  private HaiScriptCommentNode comments;

}
