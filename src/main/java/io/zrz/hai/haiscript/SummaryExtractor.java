package io.zrz.hai.haiscript;

import io.zrz.hai.haiscript.model.HaiScriptBinaryExpr;
import io.zrz.hai.haiscript.model.HaiScriptExpr;
import io.zrz.hai.haiscript.model.HaiScriptNode;
import io.zrz.hai.haiscript.model.HaiScriptStatement;

public class SummaryExtractor extends DefaultHaiScriptVisitor<Void, String> {

  @Override
  public String visitDefault(HaiScriptNode node) {
    return node.getNodeKind().toString();
  }

  @Override
  public String visitStatementDefault(HaiScriptStatement stmt) {
    switch (stmt.getStatementKind()) {
      case EXPR:
        return "EXPRSTMT";
      default:
        return stmt.getStatementKind().toString();
    }
  }

  @Override
  public String visitExprDefault(HaiScriptExpr expr) {
    return expr.getExprKind().toString();
  }

  @Override
  public String visitBinaryExpr(HaiScriptBinaryExpr expr, Void arg) {
    return "BINARY[" + expr.getOperator() + "]";
  }

}
