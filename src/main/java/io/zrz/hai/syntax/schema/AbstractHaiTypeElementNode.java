package io.zrz.hai.syntax.schema;

import io.zrz.hai.syntax.HaiScriptCommentNode;
import io.zrz.hai.syntax.model.HaiScriptSourceInfo;
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
