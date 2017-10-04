package io.zrz.hai.syntax.model;

public enum HaiScriptExprKind {

  // unresolved symbol
  SYMBOL,

  // assignment expression
  ASSIGNMENT,

  // if expression, and elvis operator
  CONDITIONAL,

  // when (xx) { a => x }
  WHEN,

  // construction call
  CONSTRUCTION,

  // invocation
  INVOCATION,

  // a query expression.
  QUERY,

  // a type reference
  TYPEREF,

  // a field
  MEMBER_ACCESS,

  // array access
  ARRAY_ACCESS,

  // local variable access
  VARIABLE_ACCESS,

  // parameter access
  PARAMETER_ACCESS,

  // reference this
  THIS,

  // super reference
  SUPER,

  // new operator
  NEW,

  // unary expression
  UNARY,

  // binary expression
  BINARY,

  // a constant value
  VALUE,

  // null reference
  NULL,

  // an array
  ARRAY_INITIALIZER,

  // a record initializer
  RECORD_INITIALIZER,

  // tuple initializer
  TUPLE_INITIALIZER,

  //
  DECLARATION,

  //
  NAMED_TUPLE,

  // reference to the arguments as a whole
  ARGS_REF,

  // selection on an expression
  SELECTION,

  // the type of the expression or type
  TYPEOF,

  //
  LAMBDA,

}
