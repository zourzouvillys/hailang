package io.zrz.hai.symbolic.expr;

public class AbstractHExprVisitor<R> implements HExprVisitor<R> {

  public R visitDefault(HExpr expr) {
    return null;
  }

  @Override
  public R visitBinary(HBinaryExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitConst(HConstExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitBlock(HBlockExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitTypeBinary(HTypeBinaryExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitGoto(HGotoExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitMatch(HMatchExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitMemberAccess(HMemberExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitThis(HThisExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitTupleInit(HTupleInitExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitUnary(HUnaryExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitVar(HVarExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitLoop(HLoopExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitIndexAccess(HIndexAccessExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitMemberInit(HMemberInitExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitLambda(HLambdaExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitFilter(HFilterExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitMap(HMapExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitFold(HFoldExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitArrayInit(HArrayInitExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitTypeUnary(HTypeUnaryExpr expr) {
    return this.visitDefault(expr);
  }

  @Override
  public R visitExceptionFilter(HExceptionFilterExpr expr) {
    return this.visitDefault(expr);
  }

}
