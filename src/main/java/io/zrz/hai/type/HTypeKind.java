package io.zrz.hai.type;

public enum HTypeKind {

  //

  NEVER,

  VOID,

  LAMBDA,

  // primitive

  INT,

  DOUBLE,

  BOOLEAN,

  STRING,

  // shapes

  ARRAY,

  UNION,

  TUPLE,

  INTERSECTION,

  WILDCARD,

  // declared types. node, edge, connection, view, struct, interface.

  DECL,

}
