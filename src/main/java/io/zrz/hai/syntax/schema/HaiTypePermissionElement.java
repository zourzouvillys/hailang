package io.zrz.hai.syntax.schema;

import java.util.Collection;
import java.util.Collections;

import io.zrz.hai.syntax.model.HaiScriptParameterListDecl;
import io.zrz.hai.syntax.model.HaiScriptStatement;
import lombok.Getter;
import lombok.Setter;

public class HaiTypePermissionElement extends AbstractHaiTypeElementMember {

  public enum Action {
    GRANT, REJECT
  }

  @Getter
  @Setter
  private Action action;

  @Getter
  @Setter
  private String simpleName;

  @Getter
  @Setter
  private HaiScriptParameterListDecl initializer;

  @Getter
  @Setter
  private HaiScriptStatement body;

  @Getter
  @Setter
  private HaiTypeElementTypeRef returnType;

  public HaiTypePermissionElement(Action action, String simpleName, HaiScriptParameterListDecl initializer) {
    this.action = action;
    this.simpleName = simpleName;
    this.initializer = initializer;
  }

  @Override
  public <R> R apply(HaiTypeElementNodeVisitor<R> visitor) {
    return visitor.visitPermissionElement(this);
  }

  @Override
  public <T extends HaiTypeElementNode> Collection<? extends T> getElements(Class<T> klass) {
    return Collections.emptySet();
  }

}
