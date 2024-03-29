package io.zrz.hai.syntax.model;

import io.zrz.hai.syntax.HaiScriptNodeVisitor;
import io.zrz.hai.syntax.HaiScriptStatementVisitor;

/**
 * a single statement
 */

public interface HaiScriptStatement extends HaiScriptNode {

  HaiScriptStatementKind getStatementKind();

  @Override
  default HaiScriptNodeKind getNodeKind() {
    return HaiScriptNodeKind.STMT;
  }

  @Override
  default <T, R> R apply(HaiScriptNodeVisitor<T, R> visitor, T arg) {
    return visitor.visitStatement(this, arg);
  }

  <T, R> R apply(HaiScriptStatementVisitor<T, R> visitor, T arg);

  default <R> R apply(HaiScriptStatementVisitor<Void, R> visitor) {
    return apply(visitor, null);
  }

}
