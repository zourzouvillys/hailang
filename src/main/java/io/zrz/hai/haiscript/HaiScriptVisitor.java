package io.zrz.hai.haiscript;

import io.zrz.hai.haiscript.model.HaiScriptExpr;
import io.zrz.hai.haiscript.model.HaiScriptStatement;

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
