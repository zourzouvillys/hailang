package io.zrz.hai.lang;

public class ArrayTypeRef implements TypeRef {

  private final TypeRef componentType;

  public ArrayTypeRef(TypeRef componentType) {
    this.componentType = componentType;
  }

  public TypeRef getComponentType() {
    return this.componentType;
  }

  @Override
  public String toString() {
    return this.componentType + "[]";
  }

}
