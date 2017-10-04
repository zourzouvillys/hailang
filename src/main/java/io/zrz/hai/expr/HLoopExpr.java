package io.zrz.hai.expr;

public interface HLoopExpr extends HExpr {

  HExpr getBody();

  HLabelTarget getBreakLabel();

  HLabelTarget getContinueLabel();

}
