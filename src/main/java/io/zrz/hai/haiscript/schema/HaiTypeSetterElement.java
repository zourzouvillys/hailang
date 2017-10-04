package io.zrz.hai.haiscript.schema;

import java.util.Collection;
import java.util.Collections;

import io.zrz.hai.haiscript.model.HaiScriptExpr;
import lombok.Getter;
import lombok.Setter;

public class HaiTypeSetterElement extends AbstractHaiTypeElementMember {

  @Getter
  @Setter
  private String simpleName;

  @Getter
  @Setter
  private HaiScriptExpr expression;

  public HaiTypeSetterElement(String simpleName, HaiScriptExpr expr) {
    this.simpleName = simpleName;
    this.expression = expr;
  }

  @Override
  public HaiTypeSetterElement addModifiers(HaiTypeElementModifiers modifiers) {
    this.getModifiers().addModifiers(modifiers);
    return this;
  }

  @Override
  public <R> R apply(HaiTypeElementNodeVisitor<R> visitor) {
    return visitor.visitSetterElement(this);
  }

  @Override
  public <T extends HaiTypeElementNode> Collection<? extends T> getElements(Class<T> klass) {
    return Collections.emptySet();
  }

}
