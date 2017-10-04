package io.zrz.hai.symbolic.expr;

public class AbstractHExprKindVoidVisitor implements HExprKindVoidVisitor {

  public void visitDefault(HExpr expr) {
  }

  @Override
  public void visitNewArray(HArrayInitExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitBlock(HBlockExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitConditional(HMatchExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitThis(HThisExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitVar(HVarExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitConst(HConstExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitGoto(HGotoExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitLoop(HLoopExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitLabel(HLabelExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitMemberAccess(HMemberExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitMemberInit(HMemberInitExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitTupleInit(HTupleInitExpr expr) {
    this.visitDefault(expr);
  }

  //

  @Override
  public void visitPlus(HUnaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitMinus(HUnaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitLogicalNegation(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitBitwiseCompliment(HUnaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitPrefixIncr(HUnaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitPrefixDecr(HUnaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitPostfixIncr(HUnaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitPostfixDecr(HUnaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitPtrDeref(HUnaryExpr expr) {
    this.visitDefault(expr);
  }

  //

  @Override
  public void visitInvoke(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  //

  @Override
  public void visitIs(HTypeUnaryExpr expr)

  {
    this.visitDefault(expr);
  }

  @Override
  public void visitAs(HTypeUnaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitCast(HTypeUnaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitDelete(HTypeUnaryExpr expr) {
    this.visitDefault(expr);
  }

  //

  @Override
  public void visitNew(HTypeBinaryExpr expr) {
    this.visitDefault(expr);
  }

  //

  @Override
  public void visitIndexAccess(HIndexAccessExpr expr) {
    this.visitDefault(expr);
  }

  //

  @Override
  public void visitAdd(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitSub(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitDivide(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitMultiply(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitMod(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitLeftShift(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitRightShift(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  //

  @Override
  public void visitLessThan(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitGreaterThan(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitLessOrEqual(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitGreaterOrEqual(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitEqual(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitNotEqual(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitConditionalAnd(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitConditionalOr(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitIn(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  // -----

  @Override
  public void visitLogicalAnd(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitLogicalXOr(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitLogicalOr(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  // -----

  @Override
  public void visitAssign(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitIncrAssign(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitDecrAssign(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitMultiplyAssign(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitDivisionAssign(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitModulusAssign(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitAndAssign(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitOrAssign(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitXOrAssign(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitLeftShiftAssign(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitRightShiftAssign(HBinaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitLambda(HLambdaExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitMap(HMapExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitFold(HFoldExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitFilter(HFilterExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitTypeOf(HTypeUnaryExpr expr) {
    this.visitDefault(expr);
  }

  @Override
  public void visitExceptionFilter(HExceptionFilterExpr expr) {
    this.visitDefault(expr);
  }

}
