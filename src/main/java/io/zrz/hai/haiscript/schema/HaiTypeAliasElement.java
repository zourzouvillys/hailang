package io.zrz.hai.haiscript.schema;

import java.util.Collection;
import java.util.Collections;

import lombok.Getter;
import lombok.Setter;

public class HaiTypeAliasElement extends AbstractHaiTypeElementMember {

  @Getter
  @Setter
  private String simpleName;

  @Getter
  @Setter
  private String targetTypeName;

  public HaiTypeAliasElement(String simpleName, String targetTypeName) {
    this.simpleName = simpleName;
    this.targetTypeName = targetTypeName;
  }

  @Override
  public <R> R apply(HaiTypeElementNodeVisitor<R> visitor) {
    return visitor.visitAliasElement(this);
  }

  @Override
  public HaiTypeAliasElement addModifiers(HaiTypeElementModifiers modifiers) {
    this.getModifiers().addModifiers(modifiers);
    return this;
  }

  @Override
  public <T extends HaiTypeElementNode> Collection<? extends T> getElements(Class<T> klass) {
    return Collections.emptySet();
  }

}
