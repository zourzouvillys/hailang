package io.zrz.hai.syntax.schema;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class HaiImportElement extends AbstractHaiTypeElementNode {

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private String as;

  @Override
  public <R> R apply(HaiTypeElementNodeVisitor<R> visitor) {
    return visitor.visitImport(this);
  }

  @Override
  public List<? extends HaiTypeElementNode> getChildren() {
    return Collections.emptyList();
  }

  @Override
  public <T extends HaiTypeElementNode> Collection<? extends T> getElements(Class<T> klass) {
    return Collections.emptySet();
  }

}
