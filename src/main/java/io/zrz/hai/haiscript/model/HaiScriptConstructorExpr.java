package io.zrz.hai.haiscript.model;

import java.util.List;

import io.zrz.hai.haiscript.HaiScriptExpressionVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptConstructorExpr extends AbstractHaiScriptExpr {

  /**
   * the context to construct in, if there are any. Otherwise the current context
   * applies.
   */

  @Getter
  @Setter
  private HaiScriptExpr context;

  /**
   *
   */

  @Getter
  @Setter
  private HaiScriptExpr type;

  /**
   * constructor arguments.
   */

  @Getter
  @Setter
  private HaiScriptExpr arguments;

  @Getter
  @Setter
  private List<? extends HaiScriptNamedTupleExpr> initializers;

  public HaiScriptConstructorExpr(HaiScriptExpr type, HaiScriptExpr arguments) {
    this.type = type;
    this.arguments = arguments;
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.CONSTRUCTION;
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitConstructorExpr(this, arg);
  }

  @Override
  public String toString() {

    final StringBuilder sb = new StringBuilder();

    sb.append("new ");
    sb.append(this.type);

    if (this.arguments != null) {
      sb.append(this.arguments);
    }

    return sb.toString();

  }

}
