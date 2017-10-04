package io.zrz.hai.type;

import java.util.Collection;

/**
 * A type which consists of members.
 */

public interface HCompositeType extends HDeclType {

  /**
   * provides a list of all members declared in this type.
   */

  Collection<? extends HMember> getDeclaredMembers();

}
