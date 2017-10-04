package io.zrz.hai.lang;

/**
 * a specialised tuple with exactly one element.
 */

public interface SetType extends TypeRef {

  /**
   * the type of the value, e.g a string, integer, tuple, or record.
   */

  TypeRef getComponentType();

}
