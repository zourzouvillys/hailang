package io.zrz.hai.symbolic.type.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.collections.api.map.primitive.MutableObjectIntMap;
import org.eclipse.collections.impl.factory.primitive.ObjectIntMaps;

import io.zrz.hai.symbolic.HLoader;
import io.zrz.hai.symbolic.type.HTupleType;
import io.zrz.hai.symbolic.type.HType;
import io.zrz.hai.symbolic.type.impl.TupleTypeImpl;

public class TupleTypeBuilder {

  private final MutableObjectIntMap<String> names = ObjectIntMaps.mutable.empty();
  private final List<HType> types = new LinkedList<>();

  /**
   *
   */

  public void add(HType type) {
    this.types.add(type);
  }

  /**
   *
   */

  public void put(String name, HType type) {
    final int idx = this.types.size();
    this.types.add(type);
    this.names.put(name, idx);
  }

  /**
   *
   */

  public HTupleType build(HLoader loader) {
    return new TupleTypeImpl(this.types, this.names);
  }

}
