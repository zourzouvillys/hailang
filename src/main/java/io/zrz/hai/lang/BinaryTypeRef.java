package io.zrz.hai.lang;

import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;

import lombok.Getter;

abstract class BinaryTypeRef implements TypeRef {

  public static enum TypeOperator {

    INTERSECT("&"), UNION("|"), DISJOINT("~");

    TypeOperator(String op) {
    }

  }

  @Getter
  private final Set<? extends TypeRef> types;
  private final TypeOperator op;

  protected BinaryTypeRef(TypeOperator op, Set<TypeRef> collect) {
    this.op = op;
    this.types = ImmutableSet.copyOf(collect);
  }

  @Override
  public String toString() {
    return Joiner.on(" " + this.op + " ").join(this.types);
  }

}
