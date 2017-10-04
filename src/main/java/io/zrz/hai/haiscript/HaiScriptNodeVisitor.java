package io.zrz.hai.haiscript;

import io.zrz.hai.haiscript.model.HaiScriptCatchBlock;
import io.zrz.hai.haiscript.model.HaiScriptExpr;
import io.zrz.hai.haiscript.model.HaiScriptParameter;
import io.zrz.hai.haiscript.model.HaiScriptParameterListDecl;
import io.zrz.hai.haiscript.model.HaiScriptSelectionItem;
import io.zrz.hai.haiscript.model.HaiScriptStatement;
import io.zrz.hai.haiscript.model.HaiScriptWhenMatch;

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
