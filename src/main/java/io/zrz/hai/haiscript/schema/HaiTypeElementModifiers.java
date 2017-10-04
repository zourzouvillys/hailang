package io.zrz.hai.haiscript.schema;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

public class HaiTypeElementModifiers {

  @Getter
  @Setter
  private Set<HaiTypeElementModifier> modifiers;

  public HaiTypeElementModifiers() {
    this.modifiers = new HashSet<>();
  }

  public HaiTypeElementModifiers(Set<HaiTypeElementModifier> modifiers) {
    this.modifiers = modifiers;
  }

  public Set<HaiTypeElementExportModifier> getExports() {
    return this.modifiers.stream()
        .filter(m -> m.getModifierKind() == HaiTypeElementModifierKind.EXPORT)
        .map(HaiTypeElementExportModifier.class::cast)
        .collect(Collectors.toSet());
  }

  public void addModifiers(HaiTypeElementModifiers modifiers) {
    this.modifiers.addAll(modifiers.getModifiers());
  }

  @Override
  public String toString() {
    if (this.modifiers == null) {
      return "";
    }
    return this.modifiers.toString();
  }

  public boolean isExtension() {
    return this.modifiers.contains(HaiTypeElementModifierKind.EXTENSION);
  }

  public HaiTypeElementModifiers addModifier(HaiTypeElementModifierKind e) {
    this.modifiers.add(e);
    return this;
  }

  public boolean isConst() {
    return this.modifiers.contains(HaiTypeElementModifierKind.IMMUTABLE) || this.modifiers.contains(HaiTypeElementModifierKind.CONST);
  }

  public boolean isMutating() {
    return this.modifiers.contains(HaiTypeElementModifierKind.MUTATING);
  }

  public boolean isStatic() {
    return this.modifiers.contains(HaiTypeElementModifierKind.STATIC);
  }

  public boolean isAuto() {
    return this.modifiers.contains(HaiTypeElementModifierKind.AUTO);
  }

  public boolean isInternal() {
    return this.modifiers.contains(HaiTypeElementModifierKind.INTERNAL);
  }

  public boolean isPublic() {
    return this.modifiers.contains(HaiTypeElementModifierKind.PUBLIC);
  }

  public boolean isProtected() {
    return this.modifiers.contains(HaiTypeElementModifierKind.PROTECTED);
  }

  public boolean isPrivate() {
    return this.modifiers.contains(HaiTypeElementModifierKind.PRIVATE);
  }

  public boolean isNative() {
    return this.modifiers.contains(HaiTypeElementModifierKind.NATIVE);
  }

  public boolean isFinal() {
    return this.modifiers.contains(HaiTypeElementModifierKind.FINAL);
  }

}
