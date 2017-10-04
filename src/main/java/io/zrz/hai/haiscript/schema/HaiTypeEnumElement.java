package io.zrz.hai.haiscript.schema;

import java.util.Collection;
import java.util.Collections;

import lombok.Getter;
import lombok.Setter;

public class HaiTypeEnumElement extends AbstractHaiTypeElementMember {

  @Getter
  @Setter
  private String name;

  public HaiTypeEnumElement(String name) {
    this.name = name;
  }

  @Override
  public <R> R apply(HaiTypeElementNodeVisitor<R> visitor) {
    return visitor.visitEnumElement(this);
  }

  @Override
  public <T extends HaiTypeElementNode> Collection<? extends T> getElements(Class<T> klass) {
    return Collections.emptySet();
  }

}
