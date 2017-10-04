package io.zrz.hai.type;

import java.util.List;

public interface HMember extends HElement {

  /**
   *
   */

  default HType getReflectedType() {
    return null;
  }

  /**
   * the name of the member, if there is one.
   */

  String getName();

  /**
   * the type this member is declared in.
   */

  HDeclType getDeclaringType();

  /**
   * the kind of member.
   */

  HMemberKind getMemberKind();

  /**
   *
   */

  HModifiers getModifiers();

  /**
   *
   */

  List<String> getDocumentation();

  /**
   *
   */

  @Override
  default HElementKind getElementKind() {
    return HElementKind.MEMBER;
  }

  /**
   * returns the previous version of this member, if there is one.
   */

  default HMember getPreviousVersion() {
    return null;
  }

  /**
   * returns the next version of this member, if there is one.
   */

  default HMember getNextVersion() {
    return null;
  }

  @Override
  default HModule getModule() {
    return this.getDeclaringType().getModule();
  }

}
