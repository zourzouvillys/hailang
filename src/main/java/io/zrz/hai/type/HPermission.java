package io.zrz.hai.type;

/**
 * A permission member. each permission either grants or rejects. to gain
 * access, there must be no matching rejections and at least one grant.
 */

public interface HPermission extends HMethod {

  /**
   * if this permision grants or rejects if it matches.
   */

  HPermissionAction getAction();

}
