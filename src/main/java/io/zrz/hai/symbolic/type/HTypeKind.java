package io.zrz.hai.symbolic.type;

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
