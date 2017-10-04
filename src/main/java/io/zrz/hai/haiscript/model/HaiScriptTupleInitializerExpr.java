package io.zrz.hai.haiscript.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import io.zrz.hai.haiscript.HaiScriptExpressionVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptTupleInitializerExpr extends AbstractHaiScriptExpr {

  @Getter
  @Setter
  private List<HaiScriptExpr> args;

  /**
   *
   */

  public HaiScriptTupleInitializerExpr(List<HaiScriptExpr> args) {
    this.args = args;
  }

  public HaiScriptTupleInitializerExpr() {
    this.args = new LinkedList<>();
  }

  /**
   *
   */

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.TUPLE_INITIALIZER;
  }

  /**
   *
   */

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitTupleInitializerExpr(this, arg);
  }

  /**
   *
   */

  @Override
  public String toString() {
    return "(" + this.args.stream().map(arg -> arg.getExprKind() + " " + arg.toString()).collect(Collectors.joining(", ")) + ")";
  }

}
