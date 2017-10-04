package io.zrz.hai.symbolic.expr;

public interface HExprVisitor<R> {

  R visitBinary(HBinaryExpr expr);

  R visitConst(HConstExpr expr);

  R visitBlock(HBlockExpr expr);

  R visitTypeBinary(HTypeBinaryExpr expr);

  R visitGoto(HGotoExpr expr);

  R visitMatch(HMatchExpr expr);

  R visitMemberAccess(HMemberExpr expr);

  R visitThis(HThisExpr expr);

  R visitTupleInit(HTupleInitExpr expr);

  R visitUnary(HUnaryExpr expr);

  R visitVar(HVarExpr expr);

  R visitLoop(HLoopExpr expr);

  R visitIndexAccess(HIndexAccessExpr expr);

  R visitMemberInit(HMemberInitExpr expr);

  R visitLambda(HLambdaExpr expr);

  R visitFilter(HFilterExpr expr);

  R visitMap(HMapExpr expr);

  R visitFold(HFoldExpr expr);

  R visitArrayInit(HArrayInitExpr expr);

  R visitTypeUnary(HTypeUnaryExpr expr);

  R visitExceptionFilter(HExceptionFilterExpr expr);

}
