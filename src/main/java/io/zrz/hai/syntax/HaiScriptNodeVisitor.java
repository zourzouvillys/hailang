package io.zrz.hai.syntax;

import io.zrz.hai.syntax.model.HaiScriptCatchBlock;
import io.zrz.hai.syntax.model.HaiScriptExpr;
import io.zrz.hai.syntax.model.HaiScriptParameter;
import io.zrz.hai.syntax.model.HaiScriptParameterListDecl;
import io.zrz.hai.syntax.model.HaiScriptSelectionItem;
import io.zrz.hai.syntax.model.HaiScriptStatement;
import io.zrz.hai.syntax.model.HaiScriptWhenMatch;

public interface HaiScriptNodeVisitor<T, R> {

  R visitStatement(HaiScriptStatement stmt, T arg);

  R visitExpression(HaiScriptExpr expr, T arg);

  //

  R visitWhenMatch(HaiScriptWhenMatch match, T arg);

  R visitSelectionItem(HaiScriptSelectionItem item, T arg);

  R visitParameterList(HaiScriptParameterListDecl params, T arg);

  R visitParameter(HaiScriptParameter param, T arg);

  R visitParameterSpread(HaiScriptParameterSpreadNode spread, T arg);

  R visitCatch(HaiScriptCatchBlock block, T arg);

}
