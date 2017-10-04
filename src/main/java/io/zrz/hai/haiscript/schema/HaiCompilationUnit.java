package io.zrz.hai.haiscript.schema;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.v4.runtime.CommonTokenStream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import lombok.Getter;
import lombok.Setter;

public class HaiCompilationUnit extends AbstractHaiTypeElementNode {

  /**
   * the defined namespace.
   */

  @Getter
  @Setter
  private String namespace;

  /**
   * the imports defined for this compilation unit.
   */

  @Getter
  @Setter
  private List<HaiImportElement> imports = new LinkedList<>();

  /**
   * the elements in the unit.
   */

  @Getter
  @Setter
  private List<HaiTypeElementNode> elements = new LinkedList<>();

  /**
   * the token source which this compilation unit is derived from.
   */

  @Getter
  private final CommonTokenStream tokens;

  /**
   * Construct a new compilation unit with a backing token source.
   */

  public HaiCompilationUnit(CommonTokenStream tokens) {
    this.tokens = Objects.requireNonNull(tokens);
  }

  /**
   * all of the child nodes in this element.
   */

  @Override
  public List<HaiTypeElementNode> getChildren() {
    final Builder<HaiTypeElementNode> b = ImmutableList.builder();
    b.addAll(this.imports);
    b.addAll(this.elements);
    return b.build();
  }

  /**
   * Add an element.
   */

  public void addElement(HaiTypeElementNode child) {
    this.elements.add(Objects.requireNonNull(child));
  }

  /**
   * Visit this compilation unit.
   */

  @Override
  public <R> R apply(HaiTypeElementNodeVisitor<R> visitor) {
    return visitor.visitCompilationUnit(this);
  }

  /**
   * Print description of this node.
   */

  @Override
  public String toString() {
    return "CompilationUnit(" + this.tokens.getSourceName() + ")";
  }

  /**
   * finds a node with the given simple name.
   */

  public HaiTypeDeclElementNode getNamedType(String simpleName) {
    for (final HaiTypeElementNode elt : this.elements) {
      if (elt instanceof HaiTypeDeclElementNode) {
        final HaiTypeDeclElementNode type = (HaiTypeDeclElementNode) elt;
        if (type.getSimpleName().equals(simpleName)) {
          return type;
        }
      }
    }
    return null;
  }

  @Override
  public <T extends HaiTypeElementNode> Collection<? extends T> getElements(Class<T> klass) {
    return Stream.concat(
        this.elements.stream().flatMap(child -> child.getElements(klass).stream()),
        this.elements.stream().filter(elt -> klass.isInstance(elt)).map(klass::cast))
        .collect(Collectors.toList());
  }

}
