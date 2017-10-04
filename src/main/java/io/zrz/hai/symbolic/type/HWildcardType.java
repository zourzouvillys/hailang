package io.zrz.hai.symbolic.type;

import java.util.Collection;

/**
 * A type reference which specifies upper and lower bounds.
 */

public interface HWildcardType extends HType {

  /**
   * the upper bound for this wildcard. If not specified, defaults to ANY.
   */

  Collection<HType> getUpperBounds();

  /**
   * The lower bounds. if not specified, defaults to VOID.
   */

  Collection<HType> getLowerBounds();

  /**
   * The kind of type.
   */

  @Override
  default HTypeKind getTypeKind() {
    return HTypeKind.WILDCARD;
  }

}
