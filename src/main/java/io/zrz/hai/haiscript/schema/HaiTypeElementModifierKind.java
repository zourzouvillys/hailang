package io.zrz.hai.haiscript.schema;

import java.util.Objects;

import org.antlr.v4.runtime.Token;

public enum HaiTypeElementModifierKind implements HaiTypeElementModifier {

  PUBLIC,

  PRIVATE,

  PROTECTED,

  INTERNAL,

  EXPORT,

  FINAL,

  CONST,

  NATIVE,

  STATIC,

  AUTO,

  WEAK,

  ASYNC,

  VOLATILE,

  STABLE,

  SYNCHRONIZED,

  IMMUTABLE,

  MUTATING,

  EXTENSION,

  TRIGGER,

  // internal only: indicates the element was generated synthetically.
  SYNTHETIC;

  @Override
  public HaiTypeElementModifierKind getModifierKind() {
    return this;
  }

  public static HaiTypeElementModifier from(Token x) {
    return Objects.requireNonNull(HaiTypeElementModifierKind.valueOf(x.getText().toUpperCase()));
  }

}
