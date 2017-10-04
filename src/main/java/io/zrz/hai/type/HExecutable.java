package io.zrz.hai.type;

import java.util.List;

import io.zrz.hai.expr.HExpr;

/**
 * A single executable block, used by both methods and lambdas. No modifiers are
 * declared for the executable, as these are handled by HMethod (or other
 * container) when it is a method, and lambdas do not have modifiers.
 *
 * Unlike a "traditional" method, rather than store opcodes or instructions, the
 * high level declaration is stored, albeit reduced down and inlined in some
 * cases. However, it is declarative in nature so we keep the full
 * representation.
 *
 * An executable provides fairly extensive dependency information, to enable
 * fast runtime analysis of a query or mutation. Each type, field, or member
 * which could be queried or modified by this executable is provided.
 *
 * Every {@link HExpr} in an executable has an identifier within the scope it is
 * in, which remains stable between loads of the executable - but may not be
 * relied on between builds. The identifier can be used to retrieve source code
 * information, suspend/resume, and debug/tracepoints.
 *
 *
 */

public interface HExecutable {

  /**
   * If this executable can potentially perform a modification of state directly
   * or indirectly, then it is mutable.
   */

  boolean isMutable();

  /**
   * The receiver type for this executable.
   */

  HType getReceiverType();

  /**
   * the return type of the field. it may be a primitive, ANY, VOID, another type
   * (of NODE, CONNECTION, EDGE, etc) as well as ARRAY, TUPLE, and STRUCT.
   */

  HType getReturnType();

  /**
   * The parameters for this excutable.
   */

  List<? extends HParameter> getParameters();

  /**
   * a table of local variables this executable uses.
   */

  List<HLocalVariable> getLocalVariables();

  /**
   * the expression tree that this executable consists of.
   */

  HExpr getExpression();

  /**
   * the instruction set for this executable.
   */

  // HILBody getInstructions();

  /**
   * returns the type (and order) which is expected as the input parameters. this
   * will always be a tuple.
   */

  HTupleType getInputType();

}
