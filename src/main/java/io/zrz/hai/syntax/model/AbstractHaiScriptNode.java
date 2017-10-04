package io.zrz.hai.syntax.model;

import java.util.Objects;

import lombok.Getter;

public abstract class AbstractHaiScriptNode implements HaiScriptNode {

  @Getter
  private HaiScriptSourceInfo source;

  @Getter
  private final HaiScriptNodeProperties properties = new HaiScriptNodeProperties();

  public AbstractHaiScriptNode(HaiScriptSourceInfo source) {
    this.source = Objects.requireNonNull(source);
  }

  public AbstractHaiScriptNode() {
  }

  @Override
  public void setSource(HaiScriptSourceInfo source) {
    this.source = Objects.requireNonNull(source);
  }

}
