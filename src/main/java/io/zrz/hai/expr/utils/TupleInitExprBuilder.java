package io.zrz.hai.expr.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.collections.api.map.primitive.MutableObjectIntMap;
import org.eclipse.collections.impl.factory.primitive.ObjectIntMaps;

import io.zrz.hai.expr.HExpr;
import io.zrz.hai.expr.HTupleInitExpr;
import io.zrz.hai.expr.impl.TupleInitExprImpl;
import io.zrz.hai.type.HLoader;
import io.zrz.hai.type.impl.TupleTypeImpl;

public class TupleInitExprBuilder {

  private final MutableObjectIntMap<String> names = ObjectIntMaps.mutable.empty();
  private final List<HExpr> expressions = new ArrayList<>();

  public TupleInitExprBuilder() {
  }

  public void add(String name, HExpr value) {
    final int index = this.expressions.size();
    this.expressions.add(value);
    this.names.put(name, index);
  }

  public void add(HExpr value) {
    this.expressions.add(value);
  }

  /**
   * set a positional item.
   */

  public void set(int pos, String name, HExpr expr) {
    if (name != null) {
      this.names.put(name, pos);
    }
    for (int i = this.expressions.size(); i <= pos; ++i) {
      this.expressions.add(null);
    }
    this.expressions.set(pos, expr);
  }

  public void set(int i, HExpr expr) {
    this.set(i, null, expr);
  }

  public HTupleInitExpr build(HLoader loader) {
    final TupleTypeImpl type = new TupleTypeImpl(this.expressions.stream().map(x -> x.getType()).collect(Collectors.toList()), this.names);
    return new TupleInitExprImpl(type, this.expressions);
  }

}
