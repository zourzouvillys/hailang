package io.zrz.hai.haiscript;

import io.zrz.hai.lang.HaiElement;
import io.zrz.hai.lang.TypeRef;
import lombok.Getter;

public class UnresolvedElement implements HaiElement {

  @Getter
  private final String name;

  public UnresolvedElement(String name) {

    this.name = name;

  }

  public static HaiElement fromString(String name) {
    return new UnresolvedElement(name);
  }

  @Override
  public String toString() {
    return "UNRESOLVED " + this.name;
  }

  @Override
  public TypeRef getType() {
    return null;
  }

}
