package io.zrz.hai.haiscript;

import io.zrz.hai.haiscript.model.HaiScriptExpressionStatement;
import io.zrz.hai.haiscript.model.HaiScriptStatement;

public class HaiScriptUtils {

  public static boolean hasSubStatement(HaiScriptStatement stmt) {

    switch (stmt.getStatementKind()) {
      case BLOCK:
      case FOR:
        return true;
      case CHECK:
      case EMIT:
      case QUERY:
      case RETURN:
      case THROW:
        return false;
      case EXPR:
        switch (((HaiScriptExpressionStatement) stmt).getExpression().getExprKind()) {
          case CONDITIONAL:
          case QUERY:
          case WHEN:
            return true;
          default:
            return false;
        }
      case WITH:
        return true;
      default:
        break;
    }

    return false;
  }

}
