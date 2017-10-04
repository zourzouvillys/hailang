package io.zrz.hai.syntax;

import io.zrz.hai.syntax.model.HaiScriptArrayAccessExpr;
import io.zrz.hai.syntax.model.HaiScriptArrayInitializerExpr;
import io.zrz.hai.syntax.model.HaiScriptBinaryExpr;
import io.zrz.hai.syntax.model.HaiScriptBranchExpr;
import io.zrz.hai.syntax.model.HaiScriptConstructorExpr;
import io.zrz.hai.syntax.model.HaiScriptDeclExpr;
import io.zrz.hai.syntax.model.HaiScriptElvisExpr;
import io.zrz.hai.syntax.model.HaiScriptExpr;
import io.zrz.hai.syntax.model.HaiScriptInvocationExpr;
import io.zrz.hai.syntax.model.HaiScriptLambdaExpr;
import io.zrz.hai.syntax.model.HaiScriptNamedTupleExpr;
import io.zrz.hai.syntax.model.HaiScriptRecordInitializerExpr;
import io.zrz.hai.syntax.model.HaiScriptSelectionExpression;
import io.zrz.hai.syntax.model.HaiScriptSymbolExpr;
import io.zrz.hai.syntax.model.HaiScriptTupleInitializerExpr;
import io.zrz.hai.syntax.model.HaiScriptUnaryExpr;
import io.zrz.hai.syntax.model.HaiScriptValue;
import io.zrz.hai.syntax.model.HaiScriptWhenExpr;

public interface HaiScriptExpressionVisitor<T, R> {

  // ---

  /**
   * a declaration of a variable and optional initializer.
   */

  R visitDeclExpr(HaiScriptDeclExpr expr, T arg);

  /**
   * A symbol.
   */

  R visitSymbolExpr(HaiScriptSymbolExpr expr, T arg);

  /**
   * a lambda expression
   */

  R visitLambdaExpr(HaiScriptLambdaExpr expr, T arg);

  /**
   * a constant value expression.
   */

  R visitValueExpr(HaiScriptValue expr, T arg);

  /**
   * a unary expression.
   */

  R visitUnaryExpr(HaiScriptUnaryExpr expr, T arg);

  /**
   * a binary expression.
   */

  R visitBinaryExpr(HaiScriptBinaryExpr expr, T arg);

  //
  // ---
  //

  /**
   * a selection on an expression.
   */

  R visitSelectionExpr(HaiScriptSelectionExpression expr, T arg);

  //
  // ---
  //

  // TODO: move to BINARYOP( '[]', name, index ).

  R visitArrayAccessExpr(HaiScriptArrayAccessExpr expr, T arg);

  // TODO: move to BINARYOP( '()', name, argstuple ).

  R visitInvocationExpr(HaiScriptInvocationExpr expr, T arg);

  // TODO: move to BINARYOP( 'new', name, argstuple ).

  R visitConstructorExpr(HaiScriptConstructorExpr expr, T arg);

  //
  // ---
  //

  R visitWhenExpr(HaiScriptWhenExpr expr, T arg);

  R visitBranchExpr(HaiScriptBranchExpr expr, T arg);

  R visitElvisExpr(HaiScriptElvisExpr expr, T arg);

  //
  // ---
  //

  R visitTupleInitializerExpr(HaiScriptTupleInitializerExpr expr, T arg);

  R visitRecordInitializerExpr(HaiScriptRecordInitializerExpr expr, T arg);

  R visitArrayInitializerExpr(HaiScriptArrayInitializerExpr expr, T arg);

  R visitNamedTupleExpr(HaiScriptNamedTupleExpr expr, T arg);

  //
  // ---
  //

  static <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, HaiScriptExpr expr) {
    return expr.apply(visitor, null);
  }

}
