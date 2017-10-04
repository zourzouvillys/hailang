package io.zrz.hai.haiscript.model;

public enum HaiScriptStatementKind {

  // check statement
  CHECK,

  // return from scope
  RETURN,

  // emit an event
  EMIT,

  // throw an error
  THROW,

  // a query statement, values populate the local scope
  QUERY,

  // an expression
  EXPR,

  // a for loop
  FOR,

  //
  BLOCK,

  // with a specific event
  WITH,

  // try/catch/finally
  TRY,

}
