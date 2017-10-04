package io.zrz.hai.haiscript.model;

import io.zrz.hai.haiscript.HaiSymbol;
import io.zrz.hai.lang.TypeRef;

public class HaiTypeSymbol implements HaiSymbol {

  private final TypeRef type;

  public HaiTypeSymbol(TypeRef type) {
    this.type = type;
  }

  @Override
  public String getText() {
    return this.type.toString();
  }

  @Override
  public String toString() {
    return this.type.toString();
  }

}
