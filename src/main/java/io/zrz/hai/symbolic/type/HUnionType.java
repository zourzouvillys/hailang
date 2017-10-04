package io.zrz.hai.symbolic.type;

import java.util.Set;

/**
 * a union of other types.
 */

public interface HUnionType extends HType {

  Set<? extends HType> getAlternatives();

  @Override
  default HTypeKind getTypeKind() {
    return HTypeKind.UNION;
  }

}
