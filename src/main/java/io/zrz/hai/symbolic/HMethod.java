package io.zrz.hai.symbolic;

import io.zrz.hai.symbolic.type.HType;

/**
 * A method is an executable set of instructions within the scope of the
 * declaring type. it does nothing except provide a basic instruction set that
 * references expressions from the etree.
 *
 * an invocation of a method loads the instruction set, and then dispatches as a
 * stack. because the majority of the work is done in expressons, the method
 * body - which consists of opcodes) is very small. they are stored in the
 * instruction set pool, and referenced by ID in the RTTI.
 *
 * A method can be immutable or mutable, and is assigned a category for grouping
 * of namings based on their role in the decltype.
 *
 */

public interface HMethod extends HMember {

  /**
   * method modifiers.
   */

  @Override
  HModifiers getModifiers();

  /**
   * The name of the method.
   */

  @Override
  String getName();

  /**
   * The executable.
   */

  HExecutable getExecutable();

  default HType getReturnType() {
    return getExecutable().getReturnType();
  }

  /**
   * the type.
   */

  @Override
  default HMemberKind getMemberKind() {
    return HMemberKind.METHOD;
  }

}
