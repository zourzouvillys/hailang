package io.zrz.hai.symbolic.expr;

public interface HExprKindVisitor<R> {

  R visitNewArray(HArrayInitExpr expr);

  //

  R visitBlock(HBlockExpr expr);

  R visitConditional(HMatchExpr expr);

  R visitThis(HThisExpr expr);

  R visitVar(HVarExpr expr);

  R visitConst(HConstExpr expr);

  R visitGoto(HGotoExpr expr);

  R visitLoop(HLoopExpr expr);

  R visitLabel(HLabelExpr expr);

  R visitMemberAccess(HMemberExpr expr);

  R visitMemberInit(HMemberInitExpr expr);

  R visitTupleInit(HTupleInitExpr expr);

  //

  R visitPlus(HUnaryExpr expr);

  R visitMinus(HUnaryExpr expr);

  R visitLogicalNegation(HBinaryExpr expr);

  R visitBitwiseCompliment(HUnaryExpr expr);

  R visitPrefixIncr(HUnaryExpr expr);

  R visitPrefixDecr(HUnaryExpr expr);

  R visitPostfixIncr(HUnaryExpr expr);

  R visitPostfixDecr(HUnaryExpr expr);

  R visitPtrDeref(HUnaryExpr expr);

  //

  R visitInvoke(HBinaryExpr expr);

  //

  R visitIs(HTypeUnaryExpr expr);

  R visitAs(HTypeUnaryExpr expr);

  R visitCast(HTypeUnaryExpr expr);

  R visitDelete(HTypeUnaryExpr expr);

  //

  R visitNew(HTypeBinaryExpr expr);

  //

  R visitIndexAccess(HIndexAccessExpr expr);

  //

  R visitAdd(HBinaryExpr expr);

  R visitSub(HBinaryExpr expr);

  R visitDivide(HBinaryExpr expr);

  R visitMultiply(HBinaryExpr expr);

  R visitMod(HBinaryExpr expr);

  R visitLeftShift(HBinaryExpr expr);

  R visitRightShift(HBinaryExpr expr);

  //

  R visitLessThan(HBinaryExpr expr);

  R visitGreaterThan(HBinaryExpr expr);

  R visitLessOrEqual(HBinaryExpr expr);

  R visitGreaterOrEqual(HBinaryExpr expr);

  R visitEqual(HBinaryExpr expr);

  R visitNotEqual(HBinaryExpr expr);

  R visitConditionalAnd(HBinaryExpr expr);

  R visitConditionalOr(HBinaryExpr expr);

  // type operators

  // range

  R visitIn(HBinaryExpr expr);

  // -----

  R visitLogicalAnd(HBinaryExpr expr);

  R visitLogicalXOr(HBinaryExpr expr);

  R visitLogicalOr(HBinaryExpr expr);

  // -----

  R visitAssign(HBinaryExpr expr);

  R visitIncrAssign(HBinaryExpr expr);

  R visitDecrAssign(HBinaryExpr expr);

  R visitMultiplyAssign(HBinaryExpr expr);

  R visitDivisionAssign(HBinaryExpr expr);

  R visitModulusAssign(HBinaryExpr expr);

  R visitAndAssign(HBinaryExpr expr);

  R visitOrAssign(HBinaryExpr expr);

  R visitXOrAssign(HBinaryExpr expr);

  R visitLeftShiftAssign(HBinaryExpr expr);

  R visitRightShiftAssign(HBinaryExpr expr);

  // expression is a declared lambda

  R visitLambda(HLambdaExpr expr);

  // map an iterable from one value to another
  R visitMap(HMapExpr expr);

  // fold a set of iterables to a single value
  R visitFold(HFoldExpr expr);

  // filter items in an iterable
  R visitFilter(HFilterExpr expr);

  // get Type for given expression (or type)
  R visitTypeOf(HTypeUnaryExpr expr);

  // a try/catch block
  R visitExceptionFilter(HExceptionFilterExpr expr);

}
