package io.zrz.hai.haiscript.model;

import io.zrz.hai.haiscript.HaiScriptNodeVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptParameter extends AbstractHaiScriptNode {

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private HaiScriptNode type;

  @Getter
  @Setter
  private HaiScriptExpr defaultValue;

  /**
   * an expression the parameter should be filtered against, using the IN
   * operator.
   */

  @Getter
  @Setter
  private HaiScriptExpr filterExpression;

  /**
   * if this parameter is optional or not.
   */

  @Getter
  @Setter
  private boolean optional;

  public HaiScriptParameter(String symbol) {
    this.name = symbol;
  }

  public HaiScriptParameter(String symbol, HaiScriptNode type) {
    this.name = symbol;
    this.type = type;
  }

  public HaiScriptParameter(String symbol, HaiScriptNode type, HaiScriptExpr defaultValue) {
    this.name = symbol;
    this.type = type;
    this.defaultValue = defaultValue;
  }

  public HaiScriptParameter() {
  }

  @Override
  public HaiScriptNodeKind getNodeKind() {
    return HaiScriptNodeKind.PARAM;
  }

  @Override
  public <T, R> R apply(HaiScriptNodeVisitor<T, R> visitor, T arg) {
    return visitor.visitParameter(this, arg);
  }

  @Override
  public String toString() {
    return "PARAM " + this.name + ": "
        + (this.type == null ? "" : this.type)
        + (this.defaultValue == null ? "" : (" = " + this.defaultValue));
  }

}
