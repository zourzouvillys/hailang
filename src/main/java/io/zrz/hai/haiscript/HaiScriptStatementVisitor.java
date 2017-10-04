package io.zrz.hai.haiscript;

import io.zrz.hai.haiscript.model.HaiScriptBlockStatement;
import io.zrz.hai.haiscript.model.HaiScriptEmitStatement;
import io.zrz.hai.haiscript.model.HaiScriptExpressionStatement;
import io.zrz.hai.haiscript.model.HaiScriptForStatement;
import io.zrz.hai.haiscript.model.HaiScriptReturnStatement;
import io.zrz.hai.haiscript.model.HaiScriptThrowStatement;
import io.zrz.hai.haiscript.model.HaiScriptTryBlock;
import io.zrz.hai.haiscript.model.HaiScriptWithStatement;

public interface HaiScriptStatementVisitor<T, R> {

  R visitBlockStatement(HaiScriptBlockStatement stmt, T arg);

  R visitThrowStatement(HaiScriptThrowStatement stmt, T arg);

  R visitReturnStatement(HaiScriptReturnStatement stmt, T arg);

  R visitForStatement(HaiScriptForStatement stmt, T arg);

  R visitExprStatement(HaiScriptExpressionStatement stmt, T arg);

  R visitEmitStatement(HaiScriptEmitStatement stmt, T arg);

  R visitWithStatement(HaiScriptWithStatement stmt, T arg);

  R visitTryStatement(HaiScriptTryBlock stmt, T arg);

}
