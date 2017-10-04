package io.zrz.hai.type;

/**
 * loader of modules and types.
 */

public interface HLoader {

  /**
   * fetch a module by its qualified name.
   */

  HModule loadModule(String module);

  /**
   * provides the type for an array of the given type.
   */

  HArrayType getArrayFor(HType type);

  /**
   * exchange a well known type token for a concrete instance of a type.
   */

  HType fromToken(HTypeToken token);

}
