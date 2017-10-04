package io.zrz.hai.syntax.model;

import io.zrz.hai.lang.TypeRef;
import io.zrz.hai.syntax.HaiSymbol;

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
