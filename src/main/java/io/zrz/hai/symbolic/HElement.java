package io.zrz.hai.symbolic;

public interface HElement {

  /**
   * the module this element is in.
   */

  HModule getModule();

  /**
   * the kind of element.
   */

  HElementKind getElementKind();

  /**
   * the registered ID of this version of this element. -1 it not registered.
   */

  default int getElementId() {
    return -1;
  }

}
