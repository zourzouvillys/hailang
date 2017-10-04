package io.zrz.hai.syntax.model;

import io.zrz.hai.syntax.HaiScriptExpressionVisitor;
import io.zrz.hai.syntax.HaiScriptNodeVisitor;
import io.zrz.hai.syntax.HaiScriptVisitException;

public interface HaiScriptExpr extends HaiScriptNode {

  HaiScriptExprKind getExprKind();

  @Override
  default HaiScriptNodeKind getNodeKind() {
    return HaiScriptNodeKind.EXPR;
  }

  @Override
  default <T, R> R apply(HaiScriptNodeVisitor<T, R> visitor, T arg) {
    return visitor.visitExpression(this, arg);
  }

  <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg);

  default <R> R apply(HaiScriptExpressionVisitor<Void, R> visitor) {
    try {
      return apply(visitor, null);
    } catch (final Exception ex) {
      throw new HaiScriptVisitException(ex, this);
    }
  }

}
