package io.zrz.hai.syntax.schema;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import io.zrz.hai.syntax.model.HaiScriptExpr;
import lombok.Getter;
import lombok.Setter;

public class HaiTypeFieldElement extends AbstractHaiTypeElementMember {

  public enum FieldKind {
    VAR, CONST, UNSPECIFIED
  }

  @Getter
  @Setter
  private String simpleName;

  @Getter
  @Setter
  private FieldKind fieldKind;

  @Getter
  @Setter
  private HaiTypeElementNode type;

  @Getter
  @Setter
  private HaiScriptExpr defaultValue;

  @Getter
  @Setter
  private List<HaiTypeElementMember> members = new LinkedList<>();

  public HaiTypeFieldElement(String simpleName) {
    this.simpleName = simpleName;
  }

  public HaiTypeFieldElement(String simpleName, HaiTypeDeclElementNode fieldType) {
    this.simpleName = simpleName;
    this.type = fieldType;
  }

  @Override
  public String toString() {
    return String.format("  def %s: %s", this.simpleName, this.type);
  }

  @Override
  public <R> R apply(HaiTypeElementNodeVisitor<R> visitor) {
    return visitor.visitFieldElement(this);
  }

  @Override
  public HaiTypeFieldElement addModifiers(HaiTypeElementModifiers modifiers) {
    this.getModifiers().addModifiers(modifiers);
    return this;
  }

  public void addElement(HaiTypeElementMember m) {
    Objects.requireNonNull(m);
    this.members.add(m);
  }

  @Override
  public <T extends HaiTypeElementNode> Collection<T> getElements(Class<T> klass) {
    final List<T> elements = new LinkedList<>();
    if (this.type.getClass().isAssignableFrom(klass)) {
      elements.add(klass.cast(this.type));
    }
    Stream.concat(
        this.members.stream().flatMap(child -> child.getElements(klass).stream()),
        this.members.stream().filter(elt -> elt.getClass().isAssignableFrom(klass)).map(klass::cast))
        .forEach(elt -> elements.add(elt));
    return elements;
  }

}
