package io.zrz.hai.haiscript.schema;

import java.util.Collection;
import java.util.Collections;

import io.zrz.hai.haiscript.model.HaiScriptParameterListDecl;
import io.zrz.hai.haiscript.model.HaiScriptStatement;
import io.zrz.hai.lang.TypeRef;
import lombok.Getter;
import lombok.Setter;

public class HaiTypeConstructorElement extends AbstractHaiTypeElementMember {

  @Getter
  @Setter
  private HaiScriptParameterListDecl parameters;
  @Getter
  @Setter
  private HaiTypeElementTypeRef returnType;
  @Getter
  @Setter
  private HaiScriptStatement body;

  public HaiTypeConstructorElement(String simpleName, TypeRef fieldType) {
    // TODO Auto-generated constructor stub
  }

  public HaiTypeConstructorElement(HaiScriptParameterListDecl params, HaiTypeElementTypeRef returnType, HaiScriptStatement body) {
    this.parameters = params;
    this.returnType = returnType;
    this.body = body;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("  ");
    sb.append("ctor ");
    sb.append(this.parameters);
    if (this.returnType != null) {
      sb.append(": ").append(this.returnType);
    }
    if (this.body != null) {
      sb.append(" {").append("\n");
      sb.append("    ").append(this.body);
      sb.append("\n").append("  }\n");
    }
    return sb.toString();
  }

  @Override
  public <R> R apply(HaiTypeElementNodeVisitor<R> visitor) {
    return visitor.visitorConstructorElement(this);
  }

  @Override
  public HaiTypeElementMember addModifiers(HaiTypeElementModifiers modifiers) {
    this.getModifiers().addModifiers(modifiers);
    return this;
  }

  @Override
  public <T extends HaiTypeElementNode> Collection<? extends T> getElements(Class<T> klass) {
    return Collections.emptySet();
  }

}
