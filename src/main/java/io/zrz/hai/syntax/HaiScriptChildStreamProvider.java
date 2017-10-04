package io.zrz.hai.syntax;

import java.util.Objects;
import java.util.stream.Stream;

import io.zrz.hai.syntax.model.HaiScriptArrayAccessExpr;
import io.zrz.hai.syntax.model.HaiScriptArrayInitializerExpr;
import io.zrz.hai.syntax.model.HaiScriptBinaryExpr;
import io.zrz.hai.syntax.model.HaiScriptBlockStatement;
import io.zrz.hai.syntax.model.HaiScriptBranchExpr;
import io.zrz.hai.syntax.model.HaiScriptCatchBlock;
import io.zrz.hai.syntax.model.HaiScriptConstructorExpr;
import io.zrz.hai.syntax.model.HaiScriptDeclExpr;
import io.zrz.hai.syntax.model.HaiScriptElvisExpr;
import io.zrz.hai.syntax.model.HaiScriptEmitStatement;
import io.zrz.hai.syntax.model.HaiScriptExpressionStatement;
import io.zrz.hai.syntax.model.HaiScriptForStatement;
import io.zrz.hai.syntax.model.HaiScriptInvocationExpr;
import io.zrz.hai.syntax.model.HaiScriptLambdaExpr;
import io.zrz.hai.syntax.model.HaiScriptNamedTupleExpr;
import io.zrz.hai.syntax.model.HaiScriptNode;
import io.zrz.hai.syntax.model.HaiScriptParameter;
import io.zrz.hai.syntax.model.HaiScriptParameterListDecl;
import io.zrz.hai.syntax.model.HaiScriptRecordInitializerExpr;
import io.zrz.hai.syntax.model.HaiScriptReturnStatement;
import io.zrz.hai.syntax.model.HaiScriptSelectionExpression;
import io.zrz.hai.syntax.model.HaiScriptSelectionItem;
import io.zrz.hai.syntax.model.HaiScriptSymbolExpr;
import io.zrz.hai.syntax.model.HaiScriptThrowStatement;
import io.zrz.hai.syntax.model.HaiScriptTryBlock;
import io.zrz.hai.syntax.model.HaiScriptTupleInitializerExpr;
import io.zrz.hai.syntax.model.HaiScriptUnaryExpr;
import io.zrz.hai.syntax.model.HaiScriptValue;
import io.zrz.hai.syntax.model.HaiScriptWhenExpr;
import io.zrz.hai.syntax.model.HaiScriptWhenMatch;
import io.zrz.hai.syntax.model.HaiScriptWithStatement;

public class HaiScriptChildStreamProvider extends HaiScriptVisitor<Void, Stream<? extends HaiScriptNode>> {

  private static final HaiScriptNodeVisitor<Void, Stream<? extends HaiScriptNode>> VOID_INSTANCE = new HaiScriptChildStreamProvider();

  public static Stream<? extends HaiScriptNode> directChildren(HaiScriptNode node) {
    return node.apply(VOID_INSTANCE, null).filter(Objects::nonNull);
  }

  public static Stream<HaiScriptNode> recurse(HaiScriptNode node) {
    return Stream.concat(
        Stream.of(node),
        node.apply(VOID_INSTANCE, null)
            .filter(Objects::nonNull)
            .flatMap(HaiScriptChildStreamProvider::recurse));
  }

  @Override
  public Stream<HaiScriptNode> visitWhenMatch(HaiScriptWhenMatch match, Void arg) {
    return Stream.concat(Stream.of(match.getStatement()), match.getExpressions().stream());
  }

  @Override
  public Stream<HaiScriptNode> visitSelectionItem(HaiScriptSelectionItem item, Void arg) {
    return Stream.of(item.getExpression());
  }

  @Override
  public Stream<? extends HaiScriptNode> visitParameterList(HaiScriptParameterListDecl params, Void arg) {
    return Stream.concat(params.getParameters().stream(), Stream.of(params.getSpread()));
  }

  @Override
  public Stream<HaiScriptNode> visitParameter(HaiScriptParameter param, Void arg) {
    return Stream.empty();
  }

  @Override
  public Stream<HaiScriptNode> visitParameterSpread(HaiScriptParameterSpreadNode spread, Void arg) {
    return Stream.empty();
  }

  @Override
  public Stream<? extends HaiScriptNode> visitBlockStatement(HaiScriptBlockStatement stmt, Void arg) {
    return stmt.getStatements().stream();
  }

  @Override
  public Stream<HaiScriptNode> visitThrowStatement(HaiScriptThrowStatement stmt, Void arg) {
    return Stream.of(stmt.getExpression());
  }

  @Override
  public Stream<HaiScriptNode> visitReturnStatement(HaiScriptReturnStatement stmt, Void arg) {
    return Stream.of(stmt.getReturnValue());
  }

  @Override
  public Stream<HaiScriptNode> visitForStatement(HaiScriptForStatement stmt, Void arg) {
    return Stream.of(stmt.getDecl(), stmt.getExpr(), stmt.getStatement());
  }

  @Override
  public Stream<HaiScriptNode> visitExprStatement(HaiScriptExpressionStatement stmt, Void arg) {
    return Stream.of(stmt.getExpression());
  }

  @Override
  public Stream<HaiScriptNode> visitEmitStatement(HaiScriptEmitStatement stmt, Void arg) {
    return stmt.getEntries().stream().flatMap(x -> Stream.of(x.getArgs(), x.getReducer(), x.getSymbol()));
  }

  @Override
  public Stream<HaiScriptNode> visitWithStatement(HaiScriptWithStatement stmt, Void arg) {
    return Stream.of(stmt.getArgs(), stmt.getBlock(), stmt.getEvent());
  }

  @Override
  public Stream<HaiScriptNode> visitDeclExpr(HaiScriptDeclExpr expr, Void arg) {
    return Stream.of(expr.getInitalizer());
  }

  @Override
  public Stream<HaiScriptNode> visitSymbolExpr(HaiScriptSymbolExpr expr, Void arg) {
    return Stream.empty();
  }

  @Override
  public Stream<HaiScriptNode> visitLambdaExpr(HaiScriptLambdaExpr expr, Void arg) {
    return Stream.of(expr.getParameters(), expr.getStatement());
  }

  @Override
  public Stream<HaiScriptNode> visitValueExpr(HaiScriptValue expr, Void arg) {
    return Stream.empty();
  }

  @Override
  public Stream<HaiScriptNode> visitUnaryExpr(HaiScriptUnaryExpr expr, Void arg) {
    return Stream.of(expr.getExpr());
  }

  @Override
  public Stream<HaiScriptNode> visitBinaryExpr(HaiScriptBinaryExpr expr, Void arg) {
    return Stream.of(expr.getLeftExpr(), expr.getRightExpr());
  }

  @Override
  public Stream<HaiScriptNode> visitSelectionExpr(HaiScriptSelectionExpression expr, Void arg) {
    return Stream.concat(Stream.of(expr.getName()), expr.getSelections().stream());
  }

  @Override
  public Stream<HaiScriptNode> visitArrayAccessExpr(HaiScriptArrayAccessExpr expr, Void arg) {
    return Stream.concat(Stream.of(expr.getName()), expr.getIndex().stream());
  }

  @Override
  public Stream<HaiScriptNode> visitInvocationExpr(HaiScriptInvocationExpr expr, Void arg) {
    return Stream.of(expr.getNameExpr(), expr.getArgsExpr());
  }

  @Override
  public Stream<HaiScriptNode> visitConstructorExpr(HaiScriptConstructorExpr expr, Void arg) {
    return Stream.of(expr.getType(), expr.getArguments());
  }

  @Override
  public Stream<HaiScriptNode> visitWhenExpr(HaiScriptWhenExpr expr, Void arg) {
    return Stream.concat(Stream.of(expr.getCondition()), expr.getMatches().entrySet().stream().flatMap(e -> Stream.of(e.getKey(), e.getValue())));
  }

  @Override
  public Stream<HaiScriptNode> visitBranchExpr(HaiScriptBranchExpr expr, Void arg) {
    return Stream.concat(expr.getConditions().stream(), Stream.of(expr.getOtherwise()));
  }

  @Override
  public Stream<HaiScriptNode> visitElvisExpr(HaiScriptElvisExpr expr, Void arg) {
    return Stream.of(expr.getCondition(), expr.getThen(), expr.getOtherwise());
  }

  @Override
  public Stream<? extends HaiScriptNode> visitTupleInitializerExpr(HaiScriptTupleInitializerExpr expr, Void arg) {
    return expr.getArgs().stream();
  }

  @Override
  public Stream<? extends HaiScriptNode> visitRecordInitializerExpr(HaiScriptRecordInitializerExpr expr, Void arg) {
    return expr.getFields().values().stream();
  }

  @Override
  public Stream<? extends HaiScriptNode> visitArrayInitializerExpr(HaiScriptArrayInitializerExpr expr, Void arg) {
    return expr.getElements().stream();
  }

  @Override
  public Stream<HaiScriptNode> visitNamedTupleExpr(HaiScriptNamedTupleExpr expr, Void arg) {
    return Stream.of(expr.getKey(), expr.getValue());
  }

  @Override
  public Stream<? extends HaiScriptNode> visitCatch(HaiScriptCatchBlock block, Void arg) {
    return Stream.of(block.getBody());
  }

  @Override
  public Stream<? extends HaiScriptNode> visitTryStatement(HaiScriptTryBlock stmt, Void arg) {
    return Stream.concat(Stream.concat(Stream.of(stmt.getBody()), stmt.getHandlers().stream()), Stream.of(stmt.getFinallyBlock()));
  }

}
