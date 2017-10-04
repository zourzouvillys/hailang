package io.zrz.hai.haiscript.schema;

import java.util.stream.Collectors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.RuleNode;

import io.zrz.hai.haiscript.ExpressionTreeRewriter;
import io.zrz.hai.haiscript.HaiScriptStatementParser;
import io.zrz.hai.haiscript.ParameterListVisitor;
import io.zrz.hai.haiscript.model.HaiScriptExpressionStatement;
import io.zrz.hai.haiscript.model.HaiScriptParameterListDecl;
import io.zrz.hai.haiscript.model.HaiScriptSelectionItem;
import io.zrz.hai.haiscript.model.HaiScriptSourceInfo;
import io.zrz.hai.haiscript.model.HaiScriptStatement;
import io.zrz.hai.lang.antlr4.HaiParser;
import io.zrz.hai.lang.antlr4.HaiParserBaseVisitor;

public class FieldMemberRewriter extends HaiParserBaseVisitor<HaiTypeElementMember> {

  @Override
  public HaiTypeElementMember visitChildren(RuleNode node) {
    throw new IllegalArgumentException(node.getClass().toString());
  }

  private <T extends HaiTypeElementNode> T source(T elt, ParserRuleContext ctx) {
    elt.setSourceInfo(HaiScriptSourceInfo.from(ctx));
    return elt;
  }

  public HaiTypeFieldSelector getKeyedLambda(HaiParser.KeyedLambdaContext ctx) {

    final HaiTypeElementModifiers modifiers = TypeMemberRewriter.readModifiers(ctx.modifiers());
    final String name = ctx.symbol().getText();
    final HaiScriptParameterListDecl params = (ctx.parameterList() == null) ? null : ctx.parameterList().accept(new ParameterListVisitor());

    HaiScriptStatement body;

    if (ctx.expression() != null) {

      // a simple expression maps to a selection
      body = new HaiScriptExpressionStatement(ctx.expression().accept(new ExpressionTreeRewriter()));
      body.setSource(HaiScriptSourceInfo.from(ctx.expression()));

    } else if (ctx.blockStatement() != null) {

      // calculates and then returns a lambda. stored as a statement.
      body = ctx.blockStatement().accept(new HaiScriptStatementParser());
      body.setSource(HaiScriptSourceInfo.from(ctx.blockStatement()));

    } else if (ctx.statement() != null) {

      // calculates and returns a lambda.
      body = ctx.statement().accept(new HaiScriptStatementParser());
      body.setSource(HaiScriptSourceInfo.from(ctx.statement()));

    } else {

      // no expression, just field.
      body = null;

    }

    // type.setSourceInfo(HaiScriptSourceInfo.from(ctx));

    return new HaiTypeFieldSelector(modifiers, name, params, body);
  }

  @Override
  public HaiTypeElementMember visitFieldDeclUniqueMember(HaiParser.FieldDeclUniqueMemberContext ctx) {

    final HaiTypeFieldUniqueElement unique = new HaiTypeFieldUniqueElement(HaiTypeFieldKind.UNIQUE);

    if (ctx.uniqueBlock().symbol() != null) {
      unique.setIndexName(ctx.uniqueBlock().symbol().getText());
    }

    unique.addModifiers(TypeMemberRewriter.readModifiers(ctx.modifiers()));

    // return new HaiScriptSelectionExpression(
    // expr,

    unique.setSelections(ctx.uniqueBlock().selections().selectionItem()
        .stream()
        .map(x -> {

          if (x.alias() != null) {
            return new HaiScriptSelectionItem(x.alias().symbol().getText(), x.expression().accept(new ExpressionTreeRewriter()));
          } else {
            return new HaiScriptSelectionItem(x.expression().accept(new ExpressionTreeRewriter()));
          }

        })
        .collect(Collectors.toList()));

    return this.source(unique, ctx);

  }

  @Override
  public HaiTypeElementMember visitFieldDeclIndexMember(HaiParser.FieldDeclIndexMemberContext ctx) {

    final HaiTypeFieldUniqueElement unique = new HaiTypeFieldUniqueElement(HaiTypeFieldKind.INDEX);

    unique.addModifiers(TypeMemberRewriter.readModifiers(ctx.modifiers()));

    // return new HaiScriptSelectionExpression(
    // expr,

    unique.setSelections(ctx.indexBlock().selections().selectionItem()
        .stream()
        .map(x -> {

          if (x.alias() != null) {
            return new HaiScriptSelectionItem(x.alias().symbol().getText(), x.expression().accept(new ExpressionTreeRewriter()));
          } else {
            return new HaiScriptSelectionItem(x.expression().accept(new ExpressionTreeRewriter()));
          }

        })
        .collect(Collectors.toList()));

    return this.source(unique, ctx);

  }

  @Override
  public HaiTypeElementMember visitFieldDeclFilterMember(HaiParser.FieldDeclFilterMemberContext ctx) {

    final HaiTypeFieldUniqueElement sort = new HaiTypeFieldUniqueElement(HaiTypeFieldKind.FILTER);

    sort.addModifiers(TypeMemberRewriter.readModifiers(ctx.modifiers()));

    if (ctx.filterBlock().keyedLambdaBlock() != null) {
      ctx.filterBlock().keyedLambdaBlock().keyedLambda().forEach(l -> sort.addKey(this.getKeyedLambda(l)));
    }

    return this.source(sort, ctx);
  }

  @Override
  public HaiTypeElementMember visitFieldDeclSortMember(HaiParser.FieldDeclSortMemberContext ctx) {
    final HaiTypeFieldUniqueElement sort = new HaiTypeFieldUniqueElement(HaiTypeFieldKind.SORT);
    sort.addModifiers(TypeMemberRewriter.readModifiers(ctx.modifiers()));

    if (ctx.sortBlock().keyedLambdaBlock() != null) {
      ctx.sortBlock().keyedLambdaBlock().keyedLambda().forEach(l -> sort.addKey(this.getKeyedLambda(l)));
    }
    return this.source(sort, ctx);
  }

  ///// --------

  @Override
  public HaiTypeElementMember visitFieldDeclGetMember(HaiParser.FieldDeclGetMemberContext ctx) {
    return this.source(new HaiTypeFieldUniqueElement(HaiTypeFieldKind.GET).addModifiers(TypeMemberRewriter.readModifiers(ctx.modifiers())), ctx);
  }

  @Override
  public HaiTypeElementMember visitFieldDeclSetMember(HaiParser.FieldDeclSetMemberContext ctx) {
    return this.source(new HaiTypeFieldUniqueElement(HaiTypeFieldKind.SET).addModifiers(TypeMemberRewriter.readModifiers(ctx.modifiers())), ctx);
  }

  @Override
  public HaiTypeElementMember visitFieldDeclAddMember(HaiParser.FieldDeclAddMemberContext ctx) {
    return this.source(new HaiTypeFieldUniqueElement(HaiTypeFieldKind.ADD).addModifiers(TypeMemberRewriter.readModifiers(ctx.modifiers())), ctx);
  }

  @Override
  public HaiTypeElementMember visitFieldDeclRemoveMember(HaiParser.FieldDeclRemoveMemberContext ctx) {
    return this.source(new HaiTypeFieldUniqueElement(HaiTypeFieldKind.REMOVE).addModifiers(TypeMemberRewriter.readModifiers(ctx.modifiers())), ctx);
  }

}
