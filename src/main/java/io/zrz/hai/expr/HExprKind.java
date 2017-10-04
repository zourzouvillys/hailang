package io.zrz.hai.expr;

public enum HExprKind {

  /**
   * a block of expressions. the last value is used as the result. if empty, has a
   * VOID value.
   */

  BLOCK,

  /**
   * branch based on conditions.
   */

  CONDITIONAL,

  /**
   * reference to the current scope. Generally not needed, but in some cases such
   * as passing self as a parameter, or "delete this", is is needed to reference
   * the current instance.
   */

  THIS,

  /**
   * a local variable or parameter
   */

  VAR,

  /**
   * Expression is a constant value provided by the expression itself.
   */

  CONST,

  /**
   * expression is a GOTO.
   */

  GOTO,

  /**
   * A loop expression (for, while)
   */

  LOOP,

  /**
   * expression is a label.
   */

  LABEL,

  /**
   * a reference to a type member, along with an optional expression for accessing
   * an instance.
   */

  MEMBER_ACCESS,

  // -----------------------------------------------------------------------------
  // -----------------------------------------------------------------------------
  // Initialisers
  // -----------------------------------------------------------------------------
  // -----------------------------------------------------------------------------

  MEMBER_INIT,

  TUPLE_INIT,

  // -----------------------------------------------------------------------------
  // -----------------------------------------------------------------------------
  // Unary Operators
  // -----------------------------------------------------------------------------
  // -----------------------------------------------------------------------------

  PLUS,

  MINUS,

  LOGICAL_NEGATION,

  BITWISE_COMPLIMENT,

  PREFIX_INCR,

  PREFIX_DECR,

  POSTFIX_INCR,

  POSTFIX_DECR,

  // -----------------------------------------------------------------------------
  // -----------------------------------------------------------------------------
  // Binary Operators
  // -----------------------------------------------------------------------------
  // -----------------------------------------------------------------------------

  //

  PTR_DEREF,

  //

  INVOKE,

  /**
   * selection on an expression, which must be a node type.
   */

  // SELECTION,

  //

  CAST,

  NEW,

  NEW_ARRAY,

  DELETE,

  //

  INDEX_ACCESS,

  //

  ADD,

  SUB,

  DIVIDE,

  MULTIPLY,

  MOD,

  LEFT_SHIFT,

  RIGHT_SHIFT,

  //

  LT,

  GT,

  LE,

  GE,

  EQ,

  NE,

  /**
   * short circuit
   */

  COND_AND,

  COND_OR,

  // type operators

  IS,

  AS,

  // range

  IN,

  //

  /**
   *
   */

  LOGICAL_AND,

  /**
   *
   */

  LOGICAL_XOR,

  /**
   *
   */

  LOGICAL_OR,

  //

  // NULL_COALESCE,

  //

  ASSIGN,

  INCR_ASSIGN,

  DECR_ASSIGN,

  MULTIPLY_ASSIGN,

  DIVISION_ASSIGN,

  MODULUS_ASSIGN,

  AND_ASSIGN,

  OR_ASSIGN,

  XOR_ASSIGN,

  LSHIFT_ASSIGN,

  RSHIFT_ASSIGN,

  // expression is a declared lambda
  LAMBDA,

  // map an iterable from one value to another
  MAP,

  // fold a set of iterables to a single value
  FOLD,

  // filter items in an iterable
  FILTER,

  // get Type for given expression (or type)
  TYPEOF,

  // a try/catch block
  EXCEPTION_FILTER,

  ;

  /**
   * returns true if the provided expression can potentially result in a branching
   * of flow control in the local scope.
   *
   * binary logical/boolean operators, goto, and conditionals are candidates.
   *
   * a branching candidate doesn't mean it actually will, just that it could.
   * optimimization passes may eliminate the branches.
   *
   * note that this only tests a specific expr kind. it may contain children which
   * can branch, so it must be recursively checked.
   *
   */

  public boolean isBranchCandidate() {

    switch (this) {
      case LOGICAL_AND:
      case LOGICAL_OR:
      case CONDITIONAL:
      case GOTO:
        return true;
      default:
        return false;
    }

  }

  /**
   * a unary of binary operator which has local side effects of variables.
   */

  public boolean hasSideEffect() {
    switch (this) {
      case PREFIX_INCR:
      case PREFIX_DECR:
      case POSTFIX_INCR:
      case POSTFIX_DECR:
      case ASSIGN:
      case INCR_ASSIGN:
      case DECR_ASSIGN:
      case MULTIPLY_ASSIGN:
      case DIVISION_ASSIGN:
      case MODULUS_ASSIGN:
      case AND_ASSIGN:
      case OR_ASSIGN:
      case XOR_ASSIGN:
      case LSHIFT_ASSIGN:
      case RSHIFT_ASSIGN:
        return true;
      default:
        return false;
    }
  }

}
