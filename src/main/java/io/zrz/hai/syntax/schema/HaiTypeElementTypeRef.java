package io.zrz.hai.syntax.schema;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.zrz.hai.lang.TypeRef;
import lombok.Getter;
import lombok.Setter;

/**
 * An element which is known to be a type.
 */

public class HaiTypeElementTypeRef extends AbstractHaiTypeElementNode {

  /**
   *
   */

  @Getter
  @Setter
  private TypeRef type;

  /**
   * an optional aliased name for the constructed type. context and type
   * dependent.
   */

  @Getter
  @Setter
  private String alias;

  /**
   *
   */

  public HaiTypeElementTypeRef(TypeRef type) {
    this.type = Objects.requireNonNull(type);
  }

  public HaiTypeElementTypeRef(TypeRef type, String alias) {
    this.type = Objects.requireNonNull(type);
    this.alias = alias;
  }

  /**
   *
   */

  @Override
  public <R> R apply(HaiTypeElementNodeVisitor<R> visitor) {
    return visitor.visitTypeRef(this);
  }

  @Override
  public List<? extends HaiTypeElementNode> getChildren() {
    return Collections.emptyList();
  }

  @Override
  public <T extends HaiTypeElementNode> Collection<? extends T> getElements(Class<T> klass) {
    return Collections.emptySet();
  }

  @Override
  public String toString() {
    return this.type.toString();
  }

}
