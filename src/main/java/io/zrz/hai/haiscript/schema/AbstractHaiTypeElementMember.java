package io.zrz.hai.haiscript.schema;

import java.util.Collections;
import java.util.List;

import lombok.Getter;

abstract class AbstractHaiTypeElementMember extends AbstractHaiTypeElementNode implements HaiTypeElementMember {

  @Getter
  private final HaiTypeElementModifiers modifiers = new HaiTypeElementModifiers();

  @Override
  public HaiTypeElementMember addModifiers(HaiTypeElementModifiers modifiers) {
    this.modifiers.getModifiers().addAll(modifiers.getModifiers());
    return this;
  }

  @Override
  public List<? extends HaiTypeElementNode> getChildren() {
    return Collections.emptyList();
  }

}
