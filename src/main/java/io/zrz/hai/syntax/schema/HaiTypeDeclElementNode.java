package io.zrz.hai.syntax.schema;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.zrz.hai.lang.TypeRef;
import lombok.Getter;
import lombok.Setter;

public class HaiTypeDeclElementNode extends AbstractHaiTypeElementNode {

  @Getter
  @Setter
  private String simpleName;

  @Getter
  @Setter
  private HaiTypeElementModifiers contextModifiers;

  @Getter
  @Setter
  private String contextName;

  @Getter
  @Setter
  private TypeRef contextType;

  @Getter
  @Setter
  private TypeRef superType;

  @Getter
  @Setter
  private List<TypeRef> superInterfaces = new LinkedList<>();

  @Getter
  @Setter
  private List<HaiTypeElementAnnotation> annotations = new LinkedList<>();

  @Getter
  @Setter
  private List<HaiTypeElementNode> elements = new LinkedList<>();

  @Setter
  private HaiTypeElementModifiers modifiers = new HaiTypeElementModifiers();

  @Getter
  @Setter
  private HaiTypeElementNodeKind kind;

  public HaiTypeDeclElementNode(HaiTypeElementNodeKind kind, String simpleName) {
    this.simpleName = simpleName;
    this.kind = kind;
  }

  /**
   * all of the child nodes in this element.
   */

  @Override
  public List<HaiTypeElementNode> getChildren() {
    return this.elements;
  }

  public void setContext(HaiTypeElementModifiers modifiers, String name, TypeRef type) {
    this.contextModifiers = modifiers;
    this.contextName = name;
    this.contextType = type;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("[type ").append(this.simpleName).append("]");
    return sb.toString();
  }

  @Override
  public <R> R apply(HaiTypeElementNodeVisitor<R> visitor) {
    return visitor.visitTypeElement(this);
  }

  public HaiTypeElementModifiers getModifiers() {
    return this.modifiers;
  }

  public HaiTypeDeclElementNode addModifiers(HaiTypeElementModifiers modifiers) {
    this.modifiers.addModifiers(modifiers);
    return this;
  }

  public void addElement(HaiTypeElementNode element) {
    this.elements.add(element);
  }

  @Override
  public <T extends HaiTypeElementNode> Collection<? extends T> getElements(Class<T> klass) {
    return Stream.concat(
        this.elements.stream().flatMap(child -> child.getElements(klass).stream()),
        this.elements.stream().filter(elt -> elt.getClass().isAssignableFrom(klass)).map(klass::cast))
        .collect(Collectors.toList());
  }

}
