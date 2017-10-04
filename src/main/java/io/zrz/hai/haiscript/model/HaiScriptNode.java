package io.zrz.hai.haiscript.model;

import io.zrz.hai.haiscript.HaiScriptNodeVisitor;

/**
 * common interface for all HaiScript nodes represented by text - statements,
 * expressions, types.
 *
 * @author theo
 *
 */
public interface HaiScriptNode {

  HaiScriptNodeKind getNodeKind();

  <T, R> R apply(HaiScriptNodeVisitor<T, R> visitor, T arg);

  default <T, R> R apply(HaiScriptNodeVisitor<T, R> visitor) {
    return apply(visitor, null);
  }

  void setSource(HaiScriptSourceInfo info);

  HaiScriptSourceInfo getSource();

  HaiScriptNodeProperties getProperties();

}
