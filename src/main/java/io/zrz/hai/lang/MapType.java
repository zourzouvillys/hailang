package io.zrz.hai.lang;

/**
 * a specialised tuple with two entries, the the and the value.
 */

public interface MapType extends TypeRef {

  /**
   * the type of the key.
   */

  TypeRef getKeyType();

  /**
   * the type of the value
   */

  TypeRef getValueType();

}
