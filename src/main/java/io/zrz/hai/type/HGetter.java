package io.zrz.hai.type;

/**
 * a property in a type, which is exposed as a field but actually a getter /
 * setter.
 */

public interface HGetter extends HMember, HExecutable {

  /**
   * The name of this field.
   */

  String getName();

  /**
   * field modifiers.
   */

  HModifiers getModifiers();

}
