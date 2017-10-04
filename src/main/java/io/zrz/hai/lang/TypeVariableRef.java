package io.zrz.hai.lang;

public interface TypeVariableRef extends TypeRef {

  // D getGenericDeclaration();

  String getName();

  TypeRef[] getBounds();

}
