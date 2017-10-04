package io.zrz.hai.expr;

public interface HExprKindVoidVisitor {

  void visitNewArray(HArrayInitExpr expr);

  //

  void visitBlock(HBlockExpr expr);

  void visitConditional(HMatchExpr expr);

  void visitThis(HThisExpr expr);

  void visitVar(HVarExpr expr);

  void visitConst(HConstExpr expr);

  void visitGoto(HGotoExpr expr);

  void visitLoop(HLoopExpr expr);

  void visitLabel(HLabelExpr expr);

  void visitMemberAccess(HMemberExpr expr);

  void visitMemberInit(HMemberInitExpr expr);

  void visitTupleInit(HTupleInitExpr expr);

  //

  void visitPlus(HUnaryExpr expr);

  void visitMinus(HUnaryExpr expr);

  void visitLogicalNegation(HBinaryExpr expr);

  void visitBitwiseCompliment(HUnaryExpr expr);

  void visitPrefixIncr(HUnaryExpr expr);

  void visitPrefixDecr(HUnaryExpr expr);

  void visitPostfixIncr(HUnaryExpr expr);

  void visitPostfixDecr(HUnaryExpr expr);

  void visitPtrDeref(HUnaryExpr expr);

  //

  void visitInvoke(HBinaryExpr expr);

  //

  void visitIs(HTypeUnaryExpr expr);

  void visitAs(HTypeUnaryExpr expr);

  void visitCast(HTypeUnaryExpr expr);

  void visitDelete(HTypeUnaryExpr expr);

  //

  void visitNew(HTypeBinaryExpr expr);

  //

  void visitIndexAccess(HIndexAccessExpr expr);

  //

  void visitAdd(HBinaryExpr expr);

  void visitSub(HBinaryExpr expr);

  void visitDivide(HBinaryExpr expr);

  void visitMultiply(HBinaryExpr expr);

  void visitMod(HBinaryExpr expr);

  void visitLeftShift(HBinaryExpr expr);

  void visitRightShift(HBinaryExpr expr);

  //

  void visitLessThan(HBinaryExpr expr);

  void visitGreaterThan(HBinaryExpr expr);

  void visitLessOrEqual(HBinaryExpr expr);

  void visitGreaterOrEqual(HBinaryExpr expr);

  void visitEqual(HBinaryExpr expr);

  void visitNotEqual(HBinaryExpr expr);

  void visitConditionalAnd(HBinaryExpr expr);

  void visitConditionalOr(HBinaryExpr expr);

  // type operators

  // range

  void visitIn(HBinaryExpr expr);

  // -----

  void visitLogicalAnd(HBinaryExpr expr);

  void visitLogicalXOr(HBinaryExpr expr);

  void visitLogicalOr(HBinaryExpr expr);

  // -----

  void visitAssign(HBinaryExpr expr);

  void visitIncrAssign(HBinaryExpr expr);

  void visitDecrAssign(HBinaryExpr expr);

  void visitMultiplyAssign(HBinaryExpr expr);

  void visitDivisionAssign(HBinaryExpr expr);

  void visitModulusAssign(HBinaryExpr expr);

  void visitAndAssign(HBinaryExpr expr);

  void visitOrAssign(HBinaryExpr expr);

  void visitXOrAssign(HBinaryExpr expr);

  void visitLeftShiftAssign(HBinaryExpr expr);

  void visitRightShiftAssign(HBinaryExpr expr);

  // expression is a declared lambda

  void visitLambda(HLambdaExpr expr);

  // map an iterable from one value to another
  void visitMap(HMapExpr expr);

  // fold a set of iterables to a single value
  void visitFold(HFoldExpr expr);

  // filter items in an iterable
  void visitFilter(HFilterExpr expr);

  // get Type for given expression (or type)
  void visitTypeOf(HTypeUnaryExpr expr);

  // a try/catch block
  void visitExceptionFilter(HExceptionFilterExpr expr);

}
