package io.zrz.hai.haiscript.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import io.zrz.hai.haiscript.HaiScriptNodeVisitor;
import io.zrz.hai.haiscript.HaiScriptParameterSpreadNode;
import lombok.Getter;
import lombok.Setter;

/**
 * a declaration of formal parameters.
 */

public class HaiScriptParameterListDecl extends AbstractHaiScriptNode {

  @Getter
  @Setter
  private List<HaiScriptParameter> parameters = new LinkedList<>();

  @Getter
  @Setter
  private HaiScriptParameterSpreadNode spread;

  public HaiScriptParameterListDecl() {
  }

  @Override
  public HaiScriptNodeKind getNodeKind() {
    return HaiScriptNodeKind.PARAM_LIST;
  }

  @Override
  public <T, R> R apply(HaiScriptNodeVisitor<T, R> visitor, T arg) {
    return visitor.visitParameterList(this, arg);
  }

  @Override
  public String toString() {
    return String.format("(%s%s)", this.parameters.stream().map(x -> x.toString()).collect(Collectors.joining(", ")),
        this.spread == null ? "" : ((this.parameters.isEmpty() ? "" : ", ") + this.spread));
  }

  public void add(HaiScriptParameterSpreadNode spread) {
    this.spread = spread;
  }

  public void add(String name, HaiScriptNode type) {
    this.parameters.add(new HaiScriptParameter(name, type));
  }

}
