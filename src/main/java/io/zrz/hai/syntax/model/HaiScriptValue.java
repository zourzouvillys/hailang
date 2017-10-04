package io.zrz.hai.syntax.model;

import java.util.Objects;

import io.zrz.hai.lang.PrimitiveTypes;
import io.zrz.hai.lang.TypeRef;
import io.zrz.hai.syntax.HaiScriptExpressionVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class HaiScriptValue extends AbstractHaiScriptExpr {

  /**
   * the singleton for null.
   */

  private static final HaiScriptValue NULL_INSTANCE = new HaiScriptValue(PrimitiveTypes.Void, null);

  /**
   * the singleton for undefined.
   */

  private static final HaiScriptValue UNDEFINED_INSTANCE = new HaiScriptValue(PrimitiveTypes.Void, null);

  /**
   * The type of the value.
   */

  @Getter
  @Setter
  private TypeRef type;

  /**
   * The value itself.
   */

  @Getter
  @Setter
  private Object value;

  public static HaiScriptValue of(String value) {
    return new HaiScriptValue(PrimitiveTypes.String, value);
  }

  public static HaiScriptValue url(String value) {
    return new HaiScriptValue(PrimitiveTypes.URL, value);
  }

  public static HaiScriptValue of(long value) {
    return new HaiScriptValue(PrimitiveTypes.Integer, value);
  }

  public static HaiScriptValue of(boolean value) {
    return new HaiScriptValue(PrimitiveTypes.Boolean, value);
  }

  public static HaiScriptValue of(double value) {
    return new HaiScriptValue(PrimitiveTypes.Float, value);
  }

  public static HaiScriptValue nullValue() {
    return NULL_INSTANCE;
  }

  public static HaiScriptValue undefinedValue() {
    return UNDEFINED_INSTANCE;
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.VALUE;
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitValueExpr(this, arg);
  }

  @Override
  public String toString() {
    if (this.type == PrimitiveTypes.String) {
      return "'" + this.value + "'";
    }
    return Objects.toString(this.value);
  }

  public static HaiScriptValue of(TypeRef type, Object value) {
    return new HaiScriptValue(type, value);
  }

}
