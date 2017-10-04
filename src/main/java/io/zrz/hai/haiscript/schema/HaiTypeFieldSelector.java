package io.zrz.hai.haiscript.schema;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.zrz.hai.haiscript.model.HaiScriptNode;
import io.zrz.hai.haiscript.model.HaiScriptParameterListDecl;
import lombok.Getter;
import lombok.Setter;

public class HaiTypeFieldSelector extends AbstractHaiTypeElementNode {

  @Getter
  @Setter
  private HaiTypeElementModifiers modifiers;

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private HaiScriptParameterListDecl params;

  @Getter
  @Setter
  private HaiScriptNode body;

  public HaiTypeFieldSelector(HaiTypeElementModifiers modifiers, String name, HaiScriptParameterListDecl params, HaiScriptNode body) {
    this.modifiers = modifiers;
    this.name = name;
    this.params = params;
    this.body = body;
  }

  @Override
  public <R> R apply(HaiTypeElementNodeVisitor<R> visitor) {
    return visitor.visitTypeFieldSelector(this);
  }

  @Override
  public List<? extends HaiTypeElementNode> getChildren() {
    return Collections.emptyList();
  }

  @Override
  public <T extends HaiTypeElementNode> Collection<? extends T> getElements(Class<T> klass) {
    return Collections.emptyList();
  }

}
