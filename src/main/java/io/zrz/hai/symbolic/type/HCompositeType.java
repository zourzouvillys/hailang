package io.zrz.hai.symbolic.type;

import java.util.Collection;

import io.zrz.hai.symbolic.HMember;

/**
 * A type which consists of members.
 */

public interface HCompositeType extends HDeclType {

  /**
   * provides a list of all members declared in this type.
   */

  Collection<? extends HMember> getDeclaredMembers();

}
