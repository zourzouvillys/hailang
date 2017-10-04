package io.zrz.hai.lang;

import lombok.Getter;

public class SymbolTypeRef implements TypeRef {

  @Getter
  private final String name;

  private SymbolTypeRef(String name) {
    this.name = name;
  }

  public static SymbolTypeRef fromString(String name) {
    return new SymbolTypeRef(name);
  }

  @Override
  public String toString() {
    return this.name;
  }

}
