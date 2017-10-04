package io.zrz.hai.lang;

import java.util.function.UnaryOperator;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

/**
 * A single field in a tuple or struct.
 */

@Value
@Builder
public class StructField {

  /**
   * the name used when passing an argument to this field.
   */

  @Getter
  private final String argumentName;

  /**
   * the name used when referencing this field.
   */

  @Getter
  private final String parameterName;

  /**
   * the type of this field.
   */

  @Getter
  private final TypeRef dataType;

  /**
   * if this field is optional.
   */

  @Getter
  private final boolean optional;

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    if (this.argumentName != null && !this.argumentName.equals(this.parameterName)) {
      sb.append(this.argumentName).append(" ");
    }
    sb.append(this.parameterName);
    if (this.optional) {
      sb.append("?");
    }
    sb.append(": ");
    sb.append(this.dataType);
    return sb.toString();
  }

  public static StructField of(String parameterName, TypeRef dataType, boolean optional) {
    return new StructField(parameterName, parameterName, dataType, optional);
  }

  public static StructField mandatory(String parameterName, TypeRef dataType) {
    return new StructField(parameterName, parameterName, dataType, false);
  }

  public static StructField optional(String parameterName, TypeRef dataType) {
    return new StructField(parameterName, parameterName, dataType, true);
  }

  public StructField withType(TypeRef typeref) {
    return new StructField(this.argumentName, this.parameterName, typeref, this.optional);
  }

  public StructField withType(UnaryOperator<TypeRef> typeref) {
    return this.withType(typeref.apply(this.getDataType()));
  }

  public static StructField mandatory(TypeRef dataType) {
    return new StructField(null, null, dataType, false);
  }

}
