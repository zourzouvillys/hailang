package io.zrz.hai.type;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import io.zrz.hai.expr.HExpr;

/**
 * A type which has been declared, and has members.
 */

public interface HDeclType extends HType, HElement {

  /**
   * A declared type may have an initialiser, which evaluates to field default
   * values. This must be run regardless of any constructor.
   */

  HExpr getInitializer();

  /**
   * fetches a specific member by name.
   */

  HMember getDeclaredMember(String memberName);

  /**
   * the modifiers on the declared type.
   */

  HModifiers getModifiers();

  /**
   * The module that this type was released in.
   */

  @Override
  HModule getModule();

  /**
   * The qualified name of the type.
   */

  String getQualifiedName();

  /**
   * the kind of declared type.
   */

  HDeclKind getDeclKind();

  /**
   * If the type has a supertype
   */

  HDeclType getSuperType();

  /**
   * Any interfaces implemented by this type.
   */

  List<? extends HDeclType> getSuperInterfaces();

  /**
   * returns the current version, or null if this is the current version.
   */

  @Nullable
  default HDeclType getCurrentVersion() {
    return null;
  }

  /**
   * returns the previous released version of this declared type, it is has one.
   */

  @Nullable
  default HDeclType getPreviousVersion() {
    return null;
  }

  /**
   * returns the next released version of this declared type.
   */

  @Nullable
  default HDeclType getNextVersion() {
    return null;
  }

  @Override
  default HLoader getTypeLoader() {
    return getModule().getTypeLoader();
  }

  @Override
  default HTypeKind getTypeKind() {
    return HTypeKind.DECL;
  }

  @Override
  default HElementKind getElementKind() {
    return HElementKind.DECLTYPE;
  }

  Collection<? extends HMember> getDeclaredMembers();

}
