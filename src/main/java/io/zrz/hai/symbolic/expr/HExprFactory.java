package io.zrz.hai.symbolic.expr;

import javax.json.JsonValue;

import io.zrz.hai.symbolic.HTypeToken;
import io.zrz.hai.symbolic.expr.impl.ConstExprImpl;
import io.zrz.hai.symbolic.expr.impl.VarExprImpl;
import io.zrz.hai.symbolic.type.HType;

public class HExprFactory {

  public static HConstExpr value(boolean value) {
    return new ConstExprImpl(HTypeToken.BOOLEAN, value);
  }

  public static HConstExpr value(String value) {
    return new ConstExprImpl(HTypeToken.STRING, value);
  }

  public static HConstExpr value(long value) {
    return new ConstExprImpl(HTypeToken.INT, value);
  }

  public static HConstExpr value(double value) {
    return new ConstExprImpl(HTypeToken.DOUBLE, value);
  }

  public static HExpr var(String name, HType type) {
    return new VarExprImpl(name, type);
  }

  public static HExpr value(JsonValue val) {
    // TODO Auto-generated method stub
    return null;
  }

}
