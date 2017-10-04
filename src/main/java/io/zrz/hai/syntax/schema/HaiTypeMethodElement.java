package io.zrz.hai.syntax.schema;

import java.util.Collection;
import java.util.Collections;

import io.zrz.hai.syntax.model.HaiScriptParameterListDecl;
import io.zrz.hai.syntax.model.HaiScriptStatement;
import lombok.Getter;
import lombok.Setter;

public class HaiTypeMethodElement extends AbstractHaiTypeElementMember {

  @Getter
  @Setter
  private String simpleName;

  @Getter
  @Setter
  private HaiTypeElementTypeRef returnType;

  @Getter
  @Setter
  private HaiScriptStatement body;

  @Getter
  @Setter
  private HaiScriptParameterListDecl parameters;

  public HaiTypeMethodElement(String simpleName, HaiScriptParameterListDecl params, HaiTypeElementTypeRef returnType, HaiScriptStatement body) {
    this.simpleName = simpleName;
    this.parameters = params;
    this.returnType = returnType;
    this.body = body;
  }

  @Override
  public <R> R apply(HaiTypeElementNodeVisitor<R> visitor) {
    return visitor.visitMethodElement(this);
  }

  @Override
  public HaiTypeMethodElement addModifiers(HaiTypeElementModifiers modifiers) {
    this.getModifiers().addModifiers(modifiers);
    return this;
  }

  @Override
  public <T extends HaiTypeElementNode> Collection<? extends T> getElements(Class<T> klass) {
    return Collections.emptySet();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("[FIELD ");
    sb.append(this.simpleName);
    sb.append(this.parameters);
    if (this.returnType != null) {
      sb.append(": ").append(this.returnType);
    }
    sb.append("]");
    return sb.toString();
  }

}
