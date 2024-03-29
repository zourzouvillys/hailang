package io.zrz.hai.type;

/**
 * The type is an array of another type.
 */

public interface HArrayType extends HType {

  HType getComponentType();

  @Override
  default HTypeKind getTypeKind() {
    return HTypeKind.ARRAY;
  }

}
