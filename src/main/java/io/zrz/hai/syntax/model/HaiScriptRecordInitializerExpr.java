package io.zrz.hai.syntax.model;

import java.util.Map;
import java.util.Objects;

import io.zrz.hai.syntax.HaiScriptExpressionVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptRecordInitializerExpr extends AbstractHaiScriptExpr {

  @Getter
  @Setter
  private Map<String, HaiScriptExpr> fields;

  public HaiScriptRecordInitializerExpr(Map<String, HaiScriptExpr> fields) {
    fields.forEach((key, value) -> Objects.requireNonNull(value));
    this.fields = fields;
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.RECORD_INITIALIZER;
  }

  @Override
  public String toString() {
    return this.fields.toString();
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitRecordInitializerExpr(this, arg);
  }

}
