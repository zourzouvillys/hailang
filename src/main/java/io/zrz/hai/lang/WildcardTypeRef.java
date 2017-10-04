package io.zrz.hai.lang;

public interface WildcardTypeRef extends TypeRef {

  TypeRef[] getUpperBounds();

  TypeRef[] getLowerBounds();

}
