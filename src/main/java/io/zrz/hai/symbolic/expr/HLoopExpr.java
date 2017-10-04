package io.zrz.hai.symbolic.expr;

public interface HLoopExpr extends HExpr {

  HExpr getBody();

  HLabelTarget getBreakLabel();

  HLabelTarget getContinueLabel();

}
