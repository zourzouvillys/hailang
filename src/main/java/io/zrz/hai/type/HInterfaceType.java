package io.zrz.hai.type;

import java.util.Collection;

import javax.annotation.Nullable;

/**
 * An edge is the entity that models a link between two nodes, with one side
 * normally being part of a connection.
 */

public interface HInterfaceType extends HDeclType, HCompositeType, HReferenceType {

  @Override
  default HDeclKind getDeclKind() {
    return HDeclKind.INTERFACE;
  }

  /**
   * If this view has a super type.
   */

  @Override
  @Nullable
  HInterfaceType getSuperType();

  /**
   * retrieve a method handle.
   */

  @Nullable
  HMethod getDeclaredMethod(String name);

  /**
   * retrieve all methods declared in this node.
   */

  Collection<? extends HMethod> getDeclaredMethods();

  /**
   * fetch a specific field.
   */

  @Nullable
  HState getDeclaredField(String name);

  /**
   * all of the fields declared in this type.
   *
   * the returns collection is immutable and may not be modified by the caller.
   *
   */

  Collection<? extends HState> getDeclaredFields();

}
