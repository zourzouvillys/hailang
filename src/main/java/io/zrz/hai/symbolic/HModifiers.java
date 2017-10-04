package io.zrz.hai.symbolic;

import java.util.Set;

public interface HModifiers {

  boolean isConst();

  boolean isMutating();

  Set<HModifierFlag> getFlags();

  boolean isStatic();

  boolean isNative();

  boolean isExport();

  boolean isAuto();

  boolean isInternal();

  boolean isPrivate();

  boolean isProtected();

  boolean isPublic();

}
