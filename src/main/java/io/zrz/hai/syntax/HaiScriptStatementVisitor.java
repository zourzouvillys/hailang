package io.zrz.hai.syntax;

import io.zrz.hai.syntax.model.HaiScriptBlockStatement;
import io.zrz.hai.syntax.model.HaiScriptEmitStatement;
import io.zrz.hai.syntax.model.HaiScriptExpressionStatement;
import io.zrz.hai.syntax.model.HaiScriptForStatement;
import io.zrz.hai.syntax.model.HaiScriptReturnStatement;
import io.zrz.hai.syntax.model.HaiScriptThrowStatement;
import io.zrz.hai.syntax.model.HaiScriptTryBlock;
import io.zrz.hai.syntax.model.HaiScriptWithStatement;

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
