package io.zrz.hai.lang;

import java.util.List;
import java.util.Objects;

import lombok.Getter;

public class ParameterizedTypeRef implements TypeRef {

  @Getter
  private final TypeRef rawType;

  @Getter
  private final List<TypeRef> typeArguments;

  public ParameterizedTypeRef(TypeRef rawType, List<TypeRef> typeArguments) {
    this.rawType = Objects.requireNonNull(rawType);
    this.typeArguments = typeArguments;
  }

  @Override
  public String toString() {
    return this.rawType + "<" + this.typeArguments + ">";
  }

}
