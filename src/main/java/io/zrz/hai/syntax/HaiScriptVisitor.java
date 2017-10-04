package io.zrz.hai.syntax;

import io.zrz.hai.syntax.model.HaiScriptExpr;
import io.zrz.hai.syntax.model.HaiScriptStatement;

public abstract class HaiScriptVisitor<T, R> implements HaiScriptNodeVisitor<T, R>, HaiScriptStatementVisitor<T, R>, HaiScriptExpressionVisitor<T, R> {

  @Override
  public R visitStatement(HaiScriptStatement stmt, T arg) {
    return stmt.apply((HaiScriptStatementVisitor<T, R>) this, arg);
  }

  @Override
  public R visitExpression(HaiScriptExpr expr, T arg) {
    return expr.apply((HaiScriptExpressionVisitor<T, R>) this, arg);
  }

}
