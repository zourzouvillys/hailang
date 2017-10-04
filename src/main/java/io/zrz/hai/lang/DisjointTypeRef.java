package io.zrz.hai.lang;

public interface DisjointTypeRef extends TypeRef {

  TypeRef getType();

  TypeRef getExclusion();

}
