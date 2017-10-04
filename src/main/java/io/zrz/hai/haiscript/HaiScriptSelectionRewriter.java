package io.zrz.hai.haiscript;

import org.antlr.v4.runtime.tree.RuleNode;

import io.zrz.hai.haiscript.model.HaiScriptExpr;
import io.zrz.hai.haiscript.model.HaiScriptNode;
import io.zrz.hai.haiscript.model.HaiScriptSelectionItem;
import io.zrz.hai.lang.antlr4.HaiParser;
import io.zrz.hai.lang.antlr4.HaiParserBaseVisitor;

public class HaiScriptSelectionRewriter extends HaiParserBaseVisitor<HaiScriptNode> {

  private final ExpressionTreeRewriter expr;

  HaiScriptSelectionRewriter(ExpressionTreeRewriter expr) {
    this.expr = expr;
  }

  @Override
  public HaiScriptExpr visitChildren(RuleNode ctx) {
    throw new IllegalArgumentException(ctx.getClass().toString());
  }

  @Override
  public HaiScriptSelectionItem visitSelectionItem(HaiParser.SelectionItemContext x) {
    if (x.alias() != null) {
      return new HaiScriptSelectionItem(x.alias().symbol().getText(), x.expression().accept(this.expr));
    } else {
      return new HaiScriptSelectionItem(x.expression().accept(this.expr));
    }
  }

  @Override
  public HaiScriptNode visitSelectionSpread(HaiParser.SelectionSpreadContext x) {
    return new HaiScriptParameterSpreadNode(x.typeref().getText());
  }

}
