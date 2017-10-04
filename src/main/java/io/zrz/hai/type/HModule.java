package io.zrz.hai.type;

import java.util.Collection;

import javax.annotation.Nullable;

public interface HModule extends HElement {

  /**
   * the loader.
   */

  HLoader getTypeLoader();

  /**
   * the types declared in this module.
   */

  Collection<? extends HDeclType> getDeclaredTypes();

  /**
   * gets all types, not just those which were declared.
   *
   * this includes elements which were synthetically generated and most likely are
   * anonymous.
   *
   */

  Collection<? extends HType> getTypes();

  /**
   * returns an element by ID that is scoped to the current version of the module,
   * even if the ID was historical.
   */

  HElement getCurrentElement(int elementId);

  /**
   * returns a declared type in this module, by fully qualified name.
   */

  HDeclType getType(String qualifiedName) throws HTypeNotFoundException;

  /**
   * Lookup a type by qualified name.
   */

  HType findType(String qualifiedName) throws HTypeNotFoundException;

  /**
   * the current released version. null if this is the current version.
   */

  @Nullable
  default HModule getCurrentVersion() {
    return null;
  }

  /**
   * the next version of this module.
   */

  @Nullable
  default HModule getNextVersion() {
    return null;
  }

  /**
   * the previous version of this module.
   */

  @Nullable
  default HModule getPreviousVersion() {
    return null;
  }

  /**
   *
   */

  @Override
  default HModule getModule() {
    return this;
  }

  default <R extends HType> R getType(String string, Class<R> klass) {
    final HDeclType type = this.getType(string);
    if (!klass.isInstance(type)) {
      throw new IllegalArgumentException(String.format("%s is not a %s", type, klass.getSimpleName()));
    }
    return klass.cast(type);
  }

}
