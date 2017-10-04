package io.zrz.hai.expr;

public class DelegatingHExprVoidVisitor implements HExprKindVisitor<Void> {

  private final HExprKindVoidVisitor delegate;

  private DelegatingHExprVoidVisitor(HExprKindVoidVisitor delegate) {
    this.delegate = delegate;
  }

  @Override
  public Void visitNewArray(HArrayInitExpr expr) {
    this.delegate.visitNewArray(expr);
    return null;
  }

  @Override
  public Void visitBlock(HBlockExpr expr) {
    this.delegate.visitBlock(expr);
    return null;
  }

  @Override
  public Void visitConditional(HMatchExpr expr) {
    this.delegate.visitConditional(expr);
    return null;
  }

  @Override
  public Void visitThis(HThisExpr expr) {
    this.delegate.visitThis(expr);
    return null;
  }

  @Override
  public Void visitVar(HVarExpr expr) {
    this.delegate.visitVar(expr);
    return null;
  }

  @Override
  public Void visitConst(HConstExpr expr) {
    this.delegate.visitConst(expr);
    return null;
  }

  @Override
  public Void visitGoto(HGotoExpr expr) {
    this.delegate.visitGoto(expr);
    return null;
  }

  @Override
  public Void visitLoop(HLoopExpr expr) {
    this.delegate.visitLoop(expr);
    return null;
  }

  @Override
  public Void visitLabel(HLabelExpr expr) {
    this.delegate.visitLabel(expr);
    return null;
  }

  @Override
  public Void visitMemberAccess(HMemberExpr expr) {
    this.delegate.visitMemberAccess(expr);
    return null;
  }

  @Override
  public Void visitMemberInit(HMemberInitExpr expr) {
    this.delegate.visitMemberInit(expr);
    return null;
  }

  @Override
  public Void visitTupleInit(HTupleInitExpr expr) {
    this.delegate.visitTupleInit(expr);
    return null;
  }

  //

  @Override
  public Void visitPlus(HUnaryExpr expr) {
    this.delegate.visitPlus(expr);
    return null;
  }

  @Override
  public Void visitMinus(HUnaryExpr expr) {
    this.delegate.visitMinus(expr);
    return null;
  }

  @Override
  public Void visitLogicalNegation(HBinaryExpr expr) {
    this.delegate.visitLogicalNegation(expr);
    return null;
  }

  @Override
  public Void visitBitwiseCompliment(HUnaryExpr expr) {
    this.delegate.visitBitwiseCompliment(expr);
    return null;
  }

  @Override
  public Void visitPrefixIncr(HUnaryExpr expr) {
    this.delegate.visitPrefixIncr(expr);
    return null;
  }

  @Override
  public Void visitPrefixDecr(HUnaryExpr expr) {
    this.delegate.visitPrefixDecr(expr);
    return null;
  }

  @Override
  public Void visitPostfixIncr(HUnaryExpr expr) {
    this.delegate.visitPostfixIncr(expr);
    return null;
  }

  @Override
  public Void visitPostfixDecr(HUnaryExpr expr) {
    this.delegate.visitPostfixDecr(expr);
    return null;
  }

  @Override
  public Void visitPtrDeref(HUnaryExpr expr) {
    this.delegate.visitPtrDeref(expr);
    return null;
  }

  //

  @Override
  public Void visitInvoke(HBinaryExpr expr) {
    this.delegate.visitInvoke(expr);
    return null;
  }

  //

  @Override
  public Void visitIs(HTypeUnaryExpr expr) {
    this.delegate.visitIs(expr);
    return null;
  }

  @Override
  public Void visitAs(HTypeUnaryExpr expr) {
    this.delegate.visitAs(expr);
    return null;
  }

  @Override
  public Void visitCast(HTypeUnaryExpr expr) {
    this.delegate.visitCast(expr);
    return null;
  }

  @Override
  public Void visitDelete(HTypeUnaryExpr expr) {
    this.delegate.visitDelete(expr);
    return null;
  }

  //

  @Override
  public Void visitNew(HTypeBinaryExpr expr) {
    this.delegate.visitNew(expr);
    return null;
  }

  //

  @Override
  public Void visitIndexAccess(HIndexAccessExpr expr) {
    this.delegate.visitIndexAccess(expr);
    return null;
  }

  //

  @Override
  public Void visitAdd(HBinaryExpr expr) {
    this.delegate.visitAdd(expr);
    return null;
  }

  @Override
  public Void visitSub(HBinaryExpr expr) {
    this.delegate.visitSub(expr);
    return null;
  }

  @Override
  public Void visitDivide(HBinaryExpr expr) {
    this.delegate.visitDivide(expr);
    return null;
  }

  @Override
  public Void visitMultiply(HBinaryExpr expr) {
    this.delegate.visitMultiply(expr);
    return null;
  }

  @Override
  public Void visitMod(HBinaryExpr expr) {
    this.delegate.visitMod(expr);
    return null;
  }

  @Override
  public Void visitLeftShift(HBinaryExpr expr) {
    this.delegate.visitLeftShift(expr);
    return null;
  }

  @Override
  public Void visitRightShift(HBinaryExpr expr) {
    this.delegate.visitRightShift(expr);
    return null;
  }

  //

  @Override
  public Void visitLessThan(HBinaryExpr expr) {
    this.delegate.visitLessThan(expr);
    return null;
  }

  @Override
  public Void visitGreaterThan(HBinaryExpr expr) {
    this.delegate.visitGreaterThan(expr);
    return null;
  }

  @Override
  public Void visitLessOrEqual(HBinaryExpr expr) {
    this.delegate.visitLessOrEqual(expr);
    return null;
  }

  @Override
  public Void visitGreaterOrEqual(HBinaryExpr expr) {
    this.delegate.visitGreaterOrEqual(expr);
    return null;
  }

  @Override
  public Void visitEqual(HBinaryExpr expr) {
    this.delegate.visitEqual(expr);
    return null;
  }

  @Override
  public Void visitNotEqual(HBinaryExpr expr) {
    this.delegate.visitNotEqual(expr);
    return null;
  }

  @Override
  public Void visitConditionalAnd(HBinaryExpr expr) {
    this.delegate.visitConditionalAnd(expr);
    return null;
  }

  @Override
  public Void visitConditionalOr(HBinaryExpr expr) {
    this.delegate.visitConditionalOr(expr);
    return null;
  }

  @Override
  public Void visitIn(HBinaryExpr expr) {
    this.delegate.visitIn(expr);
    return null;
  }

  // -----

  @Override
  public Void visitLogicalAnd(HBinaryExpr expr) {
    this.delegate.visitLogicalAnd(expr);
    return null;
  }

  @Override
  public Void visitLogicalXOr(HBinaryExpr expr) {
    this.delegate.visitLogicalXOr(expr);
    return null;
  }

  @Override
  public Void visitLogicalOr(HBinaryExpr expr) {
    this.delegate.visitLogicalOr(expr);
    return null;
  }

  // -----

  @Override
  public Void visitAssign(HBinaryExpr expr) {
    this.delegate.visitAssign(expr);
    return null;
  }

  @Override
  public Void visitIncrAssign(HBinaryExpr expr) {
    this.delegate.visitIncrAssign(expr);
    return null;
  }

  @Override
  public Void visitDecrAssign(HBinaryExpr expr) {
    this.delegate.visitDecrAssign(expr);
    return null;
  }

  @Override
  public Void visitMultiplyAssign(HBinaryExpr expr) {
    this.delegate.visitMultiplyAssign(expr);
    return null;
  }

  @Override
  public Void visitDivisionAssign(HBinaryExpr expr) {
    this.delegate.visitDivisionAssign(expr);
    return null;
  }

  @Override
  public Void visitModulusAssign(HBinaryExpr expr) {
    this.delegate.visitModulusAssign(expr);
    return null;
  }

  @Override
  public Void visitAndAssign(HBinaryExpr expr) {
    this.delegate.visitAndAssign(expr);
    return null;
  }

  @Override
  public Void visitOrAssign(HBinaryExpr expr) {
    this.delegate.visitOrAssign(expr);
    return null;
  }

  @Override
  public Void visitXOrAssign(HBinaryExpr expr) {
    this.delegate.visitXOrAssign(expr);
    return null;
  }

  @Override
  public Void visitLeftShiftAssign(HBinaryExpr expr) {
    this.delegate.visitLeftShiftAssign(expr);
    return null;
  }

  @Override
  public Void visitRightShiftAssign(HBinaryExpr expr) {
    this.delegate.visitRightShiftAssign(expr);
    return null;
  }

  @Override
  public Void visitLambda(HLambdaExpr expr) {
    this.delegate.visitLambda(expr);
    return null;
  }

  @Override
  public Void visitMap(HMapExpr expr) {
    this.delegate.visitMap(expr);
    return null;
  }

  @Override
  public Void visitFold(HFoldExpr expr) {
    this.delegate.visitFold(expr);
    return null;
  }

  @Override
  public Void visitFilter(HFilterExpr expr) {
    this.delegate.visitFilter(expr);
    return null;
  }

  @Override
  public Void visitTypeOf(HTypeUnaryExpr expr) {
    this.delegate.visitTypeOf(expr);
    return null;
  }

  @Override
  public Void visitExceptionFilter(HExceptionFilterExpr expr) {
    this.delegate.visitExceptionFilter(expr);
    return null;
  }

  public static HExprKindVisitor<Void> wrap(HExprKindVoidVisitor visitor) {
    return new DelegatingHExprVoidVisitor(visitor);
  }

}
