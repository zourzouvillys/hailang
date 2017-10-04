package io.zrz.hai.lang;

public interface LiteralTypeRef extends TypeRef {

  /**
   * the type of the value, e.g a string, integer, tuple, or record.
   */

  TypeRef getLiteralType();

  /**
   * the literal values
   */

  Object getValue();

}
