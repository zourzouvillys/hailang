package io.zrz.hai.syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.RuleNode;

import com.google.common.base.Preconditions;

import io.zrz.hai.lang.antlr4.HaiParser;
import io.zrz.hai.lang.antlr4.HaiParser.AbortStatementContext;
import io.zrz.hai.lang.antlr4.HaiParser.BlockStatementContext;
import io.zrz.hai.lang.antlr4.HaiParser.CatchBlockContext;
import io.zrz.hai.lang.antlr4.HaiParser.EmitExpressionContext;
import io.zrz.hai.lang.antlr4.HaiParser.EmitStatementContext;
import io.zrz.hai.lang.antlr4.HaiParser.ExpressionStatementContext;
import io.zrz.hai.lang.antlr4.HaiParser.NameContext;
import io.zrz.hai.lang.antlr4.HaiParser.ReturnStatementContext;
import io.zrz.hai.lang.antlr4.HaiParser.StatementContext;
import io.zrz.hai.lang.antlr4.HaiParser.StatementsContext;
import io.zrz.hai.lang.antlr4.HaiParser.SymbolContext;
import io.zrz.hai.syntax.model.HaiScriptBlockStatement;
import io.zrz.hai.syntax.model.HaiScriptCatchBlock;
import io.zrz.hai.syntax.model.HaiScriptEmitStatement;
import io.zrz.hai.syntax.model.HaiScriptExpr;
import io.zrz.hai.syntax.model.HaiScriptExpressionStatement;
import io.zrz.hai.syntax.model.HaiScriptForStatement;
import io.zrz.hai.syntax.model.HaiScriptInvocationExpr;
import io.zrz.hai.syntax.model.HaiScriptReturnStatement;
import io.zrz.hai.syntax.model.HaiScriptSourceInfo;
import io.zrz.hai.syntax.model.HaiScriptStatement;
import io.zrz.hai.syntax.model.HaiScriptSymbolExpr;
import io.zrz.hai.syntax.model.HaiScriptThrowStatement;
import io.zrz.hai.syntax.model.HaiScriptTryBlock;
import io.zrz.hai.syntax.model.HaiScriptTupleInitializerExpr;
import io.zrz.hai.syntax.model.HaiScriptUnaryExpr;
import io.zrz.hai.syntax.model.HaiScriptWithStatement;
import io.zrz.hai.lang.antlr4.HaiParserBaseVisitor;

public class HaiScriptStatementParser extends HaiParserBaseVisitor<HaiScriptStatement> {

  @Override
  public HaiScriptStatement visitChildren(RuleNode node) {
    throw new IllegalArgumentException(node.getClass().toString());
  }

  @Override
  public HaiScriptStatement visitErrorNode(ErrorNode node) {
    throw new IllegalArgumentException(node.getClass().toString());
  }

  @Override
  public HaiScriptStatement visitStatements(StatementsContext ctx) {
    final List<HaiScriptStatement> stmts = ctx.statement().stream().map(stmt -> stmt.accept(this)).collect(Collectors.toList());
    final HaiScriptBlockStatement s = new HaiScriptBlockStatement(stmts);
    s.setSource(HaiScriptSourceInfo.from(ctx));
    return s;
  }

  @Override
  public HaiScriptStatement visitStatement(StatementContext ctx) {
    Preconditions.checkState(ctx.getChildCount() == 1);
    return ctx.getChild(0).accept(this);
  }

  @Override
  public HaiScriptTryBlock visitTryCatchFinallyStatement(HaiParser.TryCatchFinallyStatementContext ctx) {

    final HaiScriptTryBlock block = new HaiScriptTryBlock(ctx.statement().accept(this));

    for (final CatchBlockContext catchctx : ctx.catchBlock()) {

      final HaiScriptCatchBlock handler = new HaiScriptCatchBlock();

      handler.setBody(catchctx.statement().accept(this));

      if (catchctx.haiTypeRef() != null) {
        handler.setType(catchctx.haiTypeRef().accept(new TypeRefRewriter()));
      }

      if (catchctx.symbol() != null) {
        handler.setSymbol(HaiUnresolvedSymbol.from(catchctx.symbol()));
      }

      handler.setSource(HaiScriptSourceInfo.from(catchctx));

    }

    if (ctx.finallyBlock() != null) {

      block.setFinallyBlock(ctx.finallyBlock().statement().accept(this));

    }

    block.setSource(HaiScriptSourceInfo.from(ctx));

    return block;

  }

  @Override
  public HaiScriptStatement visitWithStatement(HaiParser.WithStatementContext ctx) {

    final ExpressionTreeRewriter expr = new ExpressionTreeRewriter();

    // WITH typeref tupleInitializer blockStatement

    return new HaiScriptWithStatement(

        ctx.typeref().accept(expr),
        ctx.tupleInitializer().accept(expr),
        ctx.blockStatement().accept(this)

    );

  }

  /**
   *
   */

  @Override
  public HaiScriptStatement visitBlockStatement(BlockStatementContext ctx) {
    if (ctx.statements() == null) {
      return new HaiScriptBlockStatement();
    }
    return ctx.statements().accept(this);
  }

  @Override
  public HaiScriptStatement visitEmitStatement(EmitStatementContext ctx) {

    final HaiScriptEmitStatement emit = new HaiScriptEmitStatement();

    final EmitExpressionContext expr = ctx.emitExpression();

    emit.add(
        this.typeName(expr.name()),
        expr.tupleInitializer() == null ? null : (HaiScriptTupleInitializerExpr) expr.tupleInitializer().accept(new ExpressionTreeRewriter()));

    return emit;

  }

  @Override
  public HaiScriptStatement visitDeleteStatement(HaiParser.DeleteStatementContext ctx) {
    return new HaiScriptExpressionStatement(new HaiScriptUnaryExpr("delete", ctx.expression().accept(new ExpressionTreeRewriter())));
  }

  @Override
  public HaiScriptStatement visitCheckStatement(HaiParser.CheckStatementContext ctx) {
    final HaiScriptExpr args = ctx.expression().accept(new ExpressionTreeRewriter());
    return new HaiScriptExpressionStatement(new HaiScriptInvocationExpr(HaiScriptSymbolExpr.fromToken(ctx.CHECK().getSymbol()), args));
  }

  private HaiScriptSymbolExpr typeName(NameContext ctx) {

    if (ctx.symbol().size() == 1) {
      return HaiScriptSymbolExpr.fromToken(ctx.symbol(0).getStart());
    }

    final List<HaiScriptToken> tokens = new ArrayList<>(ctx.symbol().size());

    for (final SymbolContext symbol : ctx.symbol()) {
      tokens.add(new HaiScriptToken(symbol.getStart()));
    }

    return new HaiScriptSymbolExpr(new HaiUnresolvedSymbol(tokens), HaiScriptSourceInfo.from(ctx));

  }

  @Override
  public HaiScriptStatement visitReturnStatement(ReturnStatementContext ctx) {
    if (ctx.expression() != null) {
      return new HaiScriptReturnStatement(ctx.expression().accept(new ExpressionTreeRewriter()));
    }
    return new HaiScriptReturnStatement();
  }

  @Override
  public HaiScriptStatement visitExpressionStatement(ExpressionStatementContext ctx) {
    Preconditions.checkArgument(ctx.getChildCount() == 1);
    final HaiScriptExpressionStatement x = new HaiScriptExpressionStatement(ctx.getChild(0).accept(new ExpressionTreeRewriter()));
    x.setSource(HaiScriptSourceInfo.from(ctx));
    return x;
  }

  @Override
  public HaiScriptStatement visitAbortStatement(AbortStatementContext ctx) {
    if (ctx.expression() != null) {
      return new HaiScriptThrowStatement(ctx.expression().accept(new ExpressionTreeRewriter()));
    }
    return new HaiScriptThrowStatement();
  }

  @Override
  public HaiScriptStatement visitForStatement(HaiParser.ForStatementContext ctx) {
    return new HaiScriptForStatement(
        ctx.decl.accept(new ExpressionTreeRewriter()),
        ctx.expr.accept(new ExpressionTreeRewriter()),
        ctx.statement().accept(this));
  }

}
