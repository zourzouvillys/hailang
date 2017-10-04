package io.zrz.hai.type;

import java.util.Collection;

public interface HViewType extends HDeclType, HCompositeType, HReferenceType {

  /**
   * If this view has a super type.
   */

  @Override
  HDeclType getSuperType();

  /**
   * retrieve a method handle.
   */

  HMethod getDeclaredMethod(String name);

  /**
   * retrieve all methods declared in this node.
   */

  Collection<? extends HMethod> getDeclaredMethods();

  /**
   * fetch a specific field.
   */

  HState getDeclaredField(String name);

  /**
   * all of the fields declared in this type.
   */

  Collection<? extends HState> getDeclaredFields();

}
