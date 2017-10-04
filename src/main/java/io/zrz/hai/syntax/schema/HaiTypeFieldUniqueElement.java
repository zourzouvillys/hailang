package io.zrz.hai.syntax.schema;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import io.zrz.hai.syntax.model.HaiScriptSelectionItem;
import lombok.Getter;
import lombok.Setter;

public class HaiTypeFieldUniqueElement extends AbstractHaiTypeElementMember {

  @Getter
  @Setter
  private HaiTypeFieldKind fieldKind;

  @Getter
  @Setter
  private String indexName;

  @Getter
  @Setter
  private List<HaiScriptSelectionItem> selections = new LinkedList<>();

  @Getter
  @Setter
  private List<HaiTypeFieldSelector> keys = new LinkedList<>();

  public HaiTypeFieldUniqueElement(HaiTypeFieldKind kind) {
    this.fieldKind = kind;
  }

  @Override
  public <R> R apply(HaiTypeElementNodeVisitor<R> visitor) {
    return visitor.visitFieldUniqueElement(this);
  }

  public void addKey(HaiTypeFieldSelector key) {
    this.keys.add(key);
  }

  @Override
  public <T extends HaiTypeElementNode> Collection<? extends T> getElements(Class<T> klass) {
    return Collections.emptySet();
  }

}
