package io.zrz.hai.symbolic.type;

/**
 * An edge is the entity that models a link between two nodes, with one side
 * normally being part of a connection.
 */

public interface HEnumType extends HDeclType {

  @Override
  default HDeclKind getDeclKind() {
    return HDeclKind.ENUM;
  }

}
