package io.zrz.hai.syntax;

import java.util.stream.Collectors;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.RuleNode;

import io.zrz.hai.lang.antlr4.HaiParser;
import io.zrz.hai.lang.antlr4.HaiParserBaseVisitor;
import io.zrz.hai.syntax.model.HaiScriptStatement;
import io.zrz.hai.syntax.model.HaiScriptWhenMatch;

public class WhenMatchRewriter extends HaiParserBaseVisitor<HaiScriptWhenMatch> {

  private final HaiScriptStatement stmt;

  public WhenMatchRewriter(HaiScriptStatement stmt) {
    this.stmt = stmt;
  }

  @Override
  public HaiScriptWhenMatch visitChildren(RuleNode node) {
    throw new IllegalArgumentException(node.toString());
  }

  @Override
  public HaiScriptWhenMatch visitErrorNode(ErrorNode node) {
    throw new IllegalArgumentException(node.toString());
  }

  @Override
  public HaiScriptWhenMatch visitWhenInMatch(HaiParser.WhenInMatchContext ctx) {
    return new HaiScriptWhenMatch("in",
        ctx.expressionList().expression().stream().map(expr -> expr.accept(new ExpressionTreeRewriter())).collect(Collectors.toList()),
        this.stmt);
  }

  @Override
  public HaiScriptWhenMatch visitWhenIsMatch(HaiParser.WhenIsMatchContext ctx) {
    return new HaiScriptWhenMatch("is",
        ctx.expressionList().expression().stream().map(expr -> expr.accept(new ExpressionTreeRewriter())).collect(Collectors.toList()),
        this.stmt);
  }

  @Override
  public HaiScriptWhenMatch visitWhenExprListMatch(HaiParser.WhenExprListMatchContext ctx) {
    return new HaiScriptWhenMatch("==",
        ctx.expressionList().expression().stream().map(expr -> expr.accept(new ExpressionTreeRewriter())).collect(Collectors.toList()),
        this.stmt);
  }

  @Override
  public HaiScriptWhenMatch visitWhenElseMatch(HaiParser.WhenElseMatchContext ctx) {
    return new HaiScriptWhenMatch(this.stmt);
  }

}
