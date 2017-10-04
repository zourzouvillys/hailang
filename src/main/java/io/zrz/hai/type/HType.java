package io.zrz.hai.type;

/**
 * primary interface representing a type.
 */

public interface HType {

  /**
   * The kind of type.
   */

  HTypeKind getTypeKind();

  /**
   * the loader which provided this type.
   */

  HLoader getTypeLoader();

}
