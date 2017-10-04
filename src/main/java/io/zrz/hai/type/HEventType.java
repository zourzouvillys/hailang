package io.zrz.hai.type;

/**
 * An event type.
 */

public interface HEventType extends HDeclType {

  @Override
  default HDeclKind getDeclKind() {
    return HDeclKind.EVENT;
  }

}
