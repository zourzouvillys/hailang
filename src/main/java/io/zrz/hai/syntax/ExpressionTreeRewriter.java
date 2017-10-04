package io.zrz.hai.syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;

import com.google.common.base.Preconditions;

import io.zrz.hai.lang.TypeRef;
import io.zrz.hai.lang.antlr4.HaiParser;
import io.zrz.hai.lang.antlr4.HaiParser.Bracket_expressionContext;
import io.zrz.hai.lang.antlr4.HaiParser.ConditionalStatementContext;
import io.zrz.hai.lang.antlr4.HaiParser.FieldValueExpressionContext;
import io.zrz.hai.lang.antlr4.HaiParser.KeyValueExpressionContext;
import io.zrz.hai.lang.antlr4.HaiParser.PrimaryExpressionSuffixContext;
import io.zrz.hai.lang.antlr4.HaiParser.SymbolContext;
import io.zrz.hai.lang.antlr4.HaiParser.WhenClauseContext;
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
import io.zrz.hai.syntax.model.HaiScriptNode;
import io.zrz.hai.syntax.model.HaiScriptParameter;
import io.zrz.hai.syntax.model.HaiScriptParameterListDecl;
import io.zrz.hai.syntax.model.HaiScriptRecordInitializerExpr;
import io.zrz.hai.syntax.model.HaiScriptSelectionExpression;
import io.zrz.hai.syntax.model.HaiScriptSourceInfo;
import io.zrz.hai.syntax.model.HaiScriptStatement;
import io.zrz.hai.syntax.model.HaiScriptSymbolExpr;
import io.zrz.hai.syntax.model.HaiScriptTupleInitializerExpr;
import io.zrz.hai.syntax.model.HaiScriptUnaryExpr;
import io.zrz.hai.syntax.model.HaiScriptValue;
import io.zrz.hai.syntax.model.HaiScriptWhenExpr;
import io.zrz.hai.syntax.model.HaiScriptWhenMatch;
import io.zrz.hai.lang.antlr4.HaiParserBaseVisitor;

/**
 * performs conversion from the raw ANTLR4 tree into HaiScript nodes.
 */

public class ExpressionTreeRewriter extends HaiParserBaseVisitor<HaiScriptExpr> {

  private <T extends ParserRuleContext, R extends HaiScriptNode> R position(T ctx, R node) {
    node.setSource(HaiScriptSourceInfo.from(ctx));
    return node;
  }

  /**
   *
   */

  @Override
  public HaiScriptSelectionExpression visitSelections(HaiParser.SelectionsContext ctx) {
    final List<HaiScriptNode> selections = new LinkedList<>();
    for (final ParseTree c : ctx.children) {
      if (c.getChildCount() == 0) {
        continue;
      }
      selections.add(Objects.requireNonNull(c.accept(new HaiScriptSelectionRewriter(this)), c.toString()));
    }
    return new HaiScriptSelectionExpression(null, selections);
  }

  @Override
  public HaiScriptExpr visitChildren(RuleNode ctx) {
    // Preconditions.checkArgument(ctx.getChildCount() == 1,
    // ctx.getClass().toString());
    // return Objects.requireNonNull(ctx.getChild(0).accept(this),
    // ctx.getClass().toString());

    final StringBuilder sb = new StringBuilder();

    sb.append(HaiScriptSourceInfo.from((ParserRuleContext) ctx));
    sb.append(": ");
    sb.append(ctx.getClass().toGenericString());

    throw new IllegalArgumentException(sb.toString());
  }

  @Override
  public HaiScriptExpr visitErrorNode(ErrorNode node) {
    throw new IllegalArgumentException(node.getClass().toString());
  }

  @Override
  public HaiScriptExpr visitVariable(HaiParser.VariableContext decl) {
    return HaiScriptSymbolExpr.fromToken(decl.VARIABLE().getSymbol());
  }

  @Override
  public HaiScriptExpr visitAssignment(HaiParser.AssignmentContext decl) {
    return this.position(decl, new HaiScriptBinaryExpr(
        decl.assignment_operator().getText(),
        Objects.requireNonNull(decl.unaryExpression().accept(this)),
        Objects.requireNonNull(decl.expression().accept(this))));
  }

  @Override
  public HaiScriptExpr visitNull_coalescing_expression(HaiParser.Null_coalescing_expressionContext ctx) {
    HaiScriptExpr name = ctx.children.get(0).accept(this);
    for (int i = 1; i < ctx.children.size(); i += 2) {
      final String op = ctx.children.get(i).getText();
      final HaiScriptExpr right = ctx.children.get(i + 1).accept(this);
      final HaiScriptSourceInfo source = new HaiScriptSourceInfo(name.getSource(), right.getSource());
      name = new HaiScriptBinaryExpr(op, name, right);
      name.setSource(source);

    }
    return name;
  }

  @Override
  public HaiScriptExpr visitConditional_or_expression(HaiParser.Conditional_or_expressionContext ctx) {
    HaiScriptExpr name = ctx.children.get(0).accept(this);
    for (int i = 1; i < ctx.children.size(); i += 2) {
      final String op = ctx.children.get(i).getText();
      final HaiScriptExpr right = ctx.children.get(i + 1).accept(this);
      final HaiScriptSourceInfo source = new HaiScriptSourceInfo(name.getSource(), right.getSource());
      name = new HaiScriptBinaryExpr(op, name, right);
      name.setSource(source);
    }
    return name;
  }

  @Override
  public HaiScriptExpr visitConditional_and_expression(HaiParser.Conditional_and_expressionContext ctx) {
    HaiScriptExpr name = ctx.children.get(0).accept(this);
    for (int i = 1; i < ctx.children.size(); i += 2) {
      final String op = ctx.children.get(i).getText();
      final HaiScriptExpr right = ctx.children.get(i + 1).accept(this);
      final HaiScriptSourceInfo source = new HaiScriptSourceInfo(name.getSource(), right.getSource());
      name = new HaiScriptBinaryExpr(op, name, right);
      name.setSource(source);
    }
    return name;
  }

  @Override
  public HaiScriptExpr visitInclusive_or_expression(HaiParser.Inclusive_or_expressionContext ctx) {
    HaiScriptExpr name = ctx.children.get(0).accept(this);
    for (int i = 1; i < ctx.children.size(); i += 2) {
      final String op = ctx.children.get(i).getText();
      final HaiScriptExpr right = ctx.children.get(i + 1).accept(this);
      final HaiScriptSourceInfo source = new HaiScriptSourceInfo(name.getSource(), right.getSource());
      name = new HaiScriptBinaryExpr(op, name, right);
      name.setSource(source);
    }
    return name;
  }

  @Override
  public HaiScriptExpr visitExclusive_or_expression(HaiParser.Exclusive_or_expressionContext ctx) {
    HaiScriptExpr name = ctx.children.get(0).accept(this);
    for (int i = 1; i < ctx.children.size(); i += 2) {
      final String op = ctx.children.get(i).getText();
      final HaiScriptExpr right = ctx.children.get(i + 1).accept(this);
      final HaiScriptSourceInfo source = new HaiScriptSourceInfo(name.getSource(), right.getSource());
      name = new HaiScriptBinaryExpr(op, name, right);
      name.setSource(source);
    }
    return name;
  }

  @Override
  public HaiScriptExpr visitAnd_expression(HaiParser.And_expressionContext ctx) {
    HaiScriptExpr name = ctx.children.get(0).accept(this);
    for (int i = 1; i < ctx.children.size(); i += 2) {
      final String op = ctx.children.get(i).getText();
      final HaiScriptExpr right = ctx.children.get(i + 1).accept(this);
      final HaiScriptSourceInfo source = new HaiScriptSourceInfo(name.getSource(), right.getSource());
      name = new HaiScriptBinaryExpr(op, name, right);
      name.setSource(source);
    }
    return name;
  }

  @Override
  public HaiScriptExpr visitEquality_expression(HaiParser.Equality_expressionContext ctx) {
    HaiScriptExpr name = ctx.children.get(0).accept(this);
    for (int i = 1; i < ctx.children.size(); i += 2) {
      final String op = ctx.children.get(i).getText();
      final HaiScriptExpr right = ctx.children.get(i + 1).accept(this);
      final HaiScriptSourceInfo source = new HaiScriptSourceInfo(name.getSource(), right.getSource());
      name = new HaiScriptBinaryExpr(op, name, right);
      name.setSource(source);
    }
    return name;
  }

  @Override
  public HaiScriptExpr visitRelationalExpression(HaiParser.RelationalExpressionContext ctx) {
    HaiScriptExpr name = ctx.children.get(0).accept(this);
    for (int i = 1; i < ctx.children.size(); i += 2) {
      final String op = ctx.children.get(i).getText();
      final HaiScriptExpr right = ctx.children.get(i + 1).accept(this);
      final HaiScriptSourceInfo source = new HaiScriptSourceInfo(name.getSource(), right.getSource());
      name = new HaiScriptBinaryExpr(op, name, right);
      name.setSource(source);
    }
    return name;
  }

  @Override
  public HaiScriptExpr visitShift_expression(HaiParser.Shift_expressionContext ctx) {
    HaiScriptExpr name = ctx.children.get(0).accept(this);
    for (int i = 1; i < ctx.children.size(); i += 2) {
      final String op = ctx.children.get(i).getText();
      final HaiScriptExpr right = ctx.children.get(i + 1).accept(this);
      final HaiScriptSourceInfo source = new HaiScriptSourceInfo(name.getSource(), right.getSource());
      name = new HaiScriptBinaryExpr(op, name, right);
      name.setSource(source);
    }
    return name;
  }

  @Override
  public HaiScriptExpr visitMultiplicativeExpression(HaiParser.MultiplicativeExpressionContext ctx) {
    HaiScriptExpr name = ctx.children.get(0).accept(this);
    for (int i = 1; i < ctx.children.size(); i += 2) {
      final String op = ctx.children.get(i).getText();
      final HaiScriptExpr right = ctx.children.get(i + 1).accept(this);
      final HaiScriptSourceInfo source = new HaiScriptSourceInfo(name.getSource(), right.getSource());
      name = new HaiScriptBinaryExpr(op, name, right);
      name.setSource(source);
    }
    return name;
  }

  @Override
  public HaiScriptExpr visitAdditive_expression(HaiParser.Additive_expressionContext ctx) {
    HaiScriptExpr name = ctx.left.accept(this);
    for (int i = 0; i < ctx.op.size(); ++i) {
      final HaiScriptExpr right = ctx.right.get(i).accept(this);
      final HaiScriptSourceInfo source = new HaiScriptSourceInfo(name.getSource(), right.getSource());
      name = new HaiScriptBinaryExpr(
          ctx.op.get(i).getText(),
          name,
          right);
      name.setSource(source);
    }
    return name;
  }

  @Override
  public HaiScriptExpr visitDeclarationExpression(HaiParser.DeclarationExpressionContext decl) {

    boolean variable;

    switch (decl.variability.getText()) {
      case "const":
        variable = false;
        break;
      case "var":
        variable = true;
        break;
      default:
        throw new IllegalArgumentException();
    }

    final HaiScriptExpr initializer = decl.expression() == null ? null : this.position(decl.expression(), decl.expression().accept(this));

    if (decl.declType != null) {

      final TypeRef type = Objects.requireNonNull(decl.declType.accept(new TypeRefRewriter()), decl.declType.getText());

      return this.position(decl, new HaiScriptDeclExpr(variable, decl.declName.getText(), type, initializer));

    } else {
      return this.position(decl, new HaiScriptDeclExpr(variable, decl.declName.getText(), initializer));
    }

  }

  @Override
  public HaiScriptExpr visitBracketedExpression(HaiParser.BracketedExpressionContext ctx) {
    return ctx.expression().accept(this);
  }

  @Override
  public HaiScriptExpr visitStringValue(HaiParser.StringValueContext ctx) {
    final String str = ctx.STRING().getText();
    return this.position(ctx, HaiScriptValue.of(str.substring(1, str.length() - 1)));
  }

  @Override
  public HaiScriptExpr visitUrlValue(HaiParser.UrlValueContext ctx) {
    final String str = ctx.URL().getText();
    return this.position(ctx, HaiScriptValue.url(str.substring(1, str.length() - 1)));
  }

  @Override
  public HaiScriptExpr visitBooleanValue(HaiParser.BooleanValueContext ctx) {
    return this.position(ctx, HaiScriptValue.of(Boolean.parseBoolean(ctx.getText())));
  }

  @Override
  public HaiScriptExpr visitNumberValue(HaiParser.NumberValueContext ctx) {

    if (ctx.HEX_INTEGER_LITERAL() != null) {
      return this.position(ctx, HaiScriptValue.of(Long.parseLong(ctx.getText().substring(2), 16)));
    } else if (ctx.INTEGER_LITERAL() != null) {
      return this.position(ctx, HaiScriptValue.of(Long.parseLong(ctx.getText())));
    } else if (ctx.REAL_LITERAL() != null) {
      return this.position(ctx, HaiScriptValue.of(Double.parseDouble(ctx.getText())));
    } else {
      throw new IllegalArgumentException(ctx.getText());
    }

  }

  @Override
  public HaiScriptExpr visitArrayExpression(HaiParser.ArrayExpressionContext ctx) {
    if (ctx.expressionList() != null) {
      final List<HaiScriptExpr> list = ctx.expressionList().expression().stream().map(expr -> expr.accept(this)).collect(Collectors.toList());
      return this.position(ctx, new HaiScriptArrayInitializerExpr(list));
    }
    return this.position(ctx, new HaiScriptArrayInitializerExpr());
  }

  @Override
  public HaiScriptExpr visitNonAssignmentExpression(HaiParser.NonAssignmentExpressionContext ctx) {
    Preconditions.checkArgument(ctx.children.size() == 1);
    return ctx.children.get(0).accept(this);
  }

  @Override
  public HaiScriptExpr visitRecordExpression(HaiParser.RecordExpressionContext ctx) {

    final Map<String, HaiScriptExpr> fields = new HashMap<>();

    if (ctx.fieldValues() != null) {

      ctx.fieldValues().fieldValue().stream().forEach(initializer -> {

        initializer.accept(new HaiParserBaseVisitor<Void>() {

          @Override
          public Void visitChildren(RuleNode node) {
            throw new IllegalArgumentException(node.getClass().toString());
          }

          @Override
          public Void visitErrorNode(ErrorNode node) {
            throw new IllegalArgumentException(node.getClass().toString());
          }

          @Override
          public Void visitKeyValueExpression(KeyValueExpressionContext ctx) {
            fields.put(ctx.keyExpr.getText(), ctx.valueExpr.accept(ExpressionTreeRewriter.this));
            return null;
          }

          @Override
          public Void visitFieldValueExpression(FieldValueExpressionContext ctx) {
            fields.put(ctx.getText(), ctx.expression().accept(ExpressionTreeRewriter.this));
            return null;
          }

        });

      });

    }

    return this.position(ctx, new HaiScriptRecordInitializerExpr(fields));
  }

  @Override
  public HaiScriptTupleInitializerExpr visitTupleInitializer(HaiParser.TupleInitializerContext ctx) {
    return this.position(ctx,
        new HaiScriptTupleInitializerExpr(ctx.tupleInitializerArgument().stream().map(arg -> arg.accept(this)).collect(Collectors.toList())));
  }

  @Override
  public HaiScriptExpr visitExpression(HaiParser.ExpressionContext ctx) {

    final HaiScriptExpr expr = Objects.requireNonNull(ctx.children.get(0).accept(this));

    if (ctx.selections() != null) {
      final HaiScriptSelectionExpression sel = this.visitSelections(ctx.selections());
      sel.setName(expr);
      return sel;
    }

    return expr;
  }

  @Override
  public HaiScriptExpr visitPositionalTupleArgument(HaiParser.PositionalTupleArgumentContext ctx) {
    return ctx.positional.accept(this);
  }

  @Override
  public HaiScriptExpr visitLambdaExpression(HaiParser.LambdaExpressionContext ctx) {

    final HaiScriptParameterListDecl params;

    if (ctx.symbol() != null) {

      // simple symbol with no parenthesis.
      params = this.position(ctx.symbol(), new HaiScriptParameterListDecl());
      params.getParameters().add(this.position(ctx.symbol(), new HaiScriptParameter(ctx.symbol().getText())));

    } else if (ctx.parameterList().parameterItem() != null) {

      params = this.position(ctx.parameterList(), ctx.parameterList().accept(new ParameterListVisitor()));

    } else {
      throw new IllegalArgumentException();
    }

    return this.position(
        ctx,
        new HaiScriptLambdaExpr(
            params,
            ctx.statement().accept(new HaiScriptStatementParser())));
  }

  @Override
  public HaiScriptExpr visitNamedTupleArgument(HaiParser.NamedTupleArgumentContext ctx) {
    return this.position(ctx, new HaiScriptNamedTupleExpr(ctx.left.accept(this), ctx.right.accept(this)));
  }

  @Override
  public HaiScriptExpr visitTypeOfExpression(HaiParser.TypeOfExpressionContext ctx) {
    if (ctx.expression() != null) {
      return this.position(ctx, new HaiScriptUnaryExpr("typeof", ctx.expression().accept(this)));
    } else if (ctx.haiTypeRef() != null) {
      return this.position(ctx, new HaiScriptUnaryExpr("typeof", ctx.haiTypeRef().accept(this)));
    }
    throw new IllegalArgumentException();
  }

  /**
   * generate an unknown symbol for this name.
   *
   * expressions which start with a set of symbols separated by '.' could be
   * qualified or canonical names, or member access of local symbols. the parser
   * doesn't know, so generates a single symbol expression with multiple tokens.
   *
   */

  @Override
  public HaiScriptExpr visitName(HaiParser.NameContext ctx) {

    if (ctx.symbol().size() == 1) {
      return this.position(ctx, HaiScriptSymbolExpr.fromToken(ctx.symbol(0).getStart()));
    }

    final List<HaiScriptToken> tokens = new ArrayList<>(ctx.symbol().size());

    for (final SymbolContext symbol : ctx.symbol()) {
      tokens.add(new HaiScriptToken(symbol.getStart()));
    }

    return this.position(ctx, new HaiScriptSymbolExpr(new HaiUnresolvedSymbol(tokens), HaiScriptSourceInfo.from(ctx)));

  }

  @Override
  public HaiScriptSymbolExpr visitSymbol(HaiParser.SymbolContext ctx) {
    return new HaiScriptSymbolExpr(new HaiUnresolvedSymbol(new HaiScriptToken(ctx.getStart())), HaiScriptSourceInfo.from(ctx));
  }

  @Override
  public HaiScriptExpr visitBranchExpression(HaiParser.BranchExpressionContext ctx) {

    final HaiScriptBranchExpr branch = new HaiScriptBranchExpr();

    for (final ConditionalStatementContext cond : ctx.conditionalStatement()) {
      branch.add(new HaiScriptWhenMatch(cond.nonSelectableExpression().accept(this), cond.statement().accept(new HaiScriptStatementParser())));
    }

    if (ctx.statement() != null) {
      branch.otherwise(ctx.statement().accept(new HaiScriptStatementParser()));
    }

    return this.position(ctx, branch);

  }

  @Override
  public HaiScriptExpr visitUnaryExpression(HaiParser.UnaryExpressionContext ctx) {

    if (ctx.primaryExpression() != null) {
      return Objects.requireNonNull(ctx.primaryExpression().accept(this));
    }

    return this.position(ctx, new HaiScriptUnaryExpr(
        ctx.unaryOperator().getText(),
        ctx.expression().accept(this)));
  }

  @Override
  public HaiScriptExpr visitWhenExpression(HaiParser.WhenExpressionContext ctx) {

    final HaiScriptExpr expr = ctx.expression() == null ? null : ctx.expression().accept(this);

    final HaiScriptWhenExpr when = new HaiScriptWhenExpr(expr);

    for (final WhenClauseContext clause : ctx.whenClause()) {
      final HaiScriptStatement stmt = clause.statement().accept(new HaiScriptStatementParser());
      final HaiScriptWhenMatch match = clause.whenMatch().accept(new WhenMatchRewriter(stmt));
      when.add(match, stmt);
    }

    return this.position(ctx, when);
  }

  @Override
  public HaiScriptExpr visitConstructorExpression(HaiParser.ConstructorExpressionContext ctx) {
    final HaiScriptExpr args = ctx.tupleInitializer() != null ? ctx.tupleInitializer().accept(this) : null;

    final HaiScriptExpr typeref = (ctx.typeref() == null) ? null : this.visitName(ctx.typeref().name());

    final HaiScriptConstructorExpr ctor = new HaiScriptConstructorExpr(typeref, args);

    if (ctx.memberInitializers() != null) {

      final List<HaiScriptNamedTupleExpr> init = ctx.memberInitializers().memberInitializer().stream()
          .map(x -> {

            final HaiScriptExpr key = x.symbol().accept(this);

            final HaiScriptExpr value = x.expression().accept(this);

            return new HaiScriptNamedTupleExpr(key, value);

          })
          .collect(Collectors.toList());

      ctor.setInitializers(init);

    }

    return this.position(ctx, ctor);
  }

  @Override
  public HaiScriptExpr visitConditionalExpression(HaiParser.ConditionalExpressionContext ctx) {

    final HaiScriptExpr condition = ctx.null_coalescing_expression().accept(this);

    if (ctx.t != null) {

      return new HaiScriptElvisExpr(condition, ctx.t.accept(this), ctx.e.accept(this));

    } else if (ctx.e != null) {

      return new HaiScriptElvisExpr(condition, ctx.e.accept(this));

    }

    return condition;
  }

  @Override
  public HaiScriptExpr visitIndexer_argument(HaiParser.Indexer_argumentContext ctx) {

    if (ctx.symbol() != null) {
      return new HaiScriptNamedTupleExpr(
          ctx.symbol().accept(this),
          ctx.expression().accept(this));
    }

    return ctx.expression().accept(this);

  }

  @Override
  public HaiScriptExpr visitTyperef(HaiParser.TyperefContext ctx) {
    return ctx.name().accept(this);
  }

  /**
   *
   * @param ctx
   * @return
   */

  @Override
  public HaiScriptExpr visitPrimaryExpression(HaiParser.PrimaryExpressionContext ctx) {

    HaiScriptExpr name = ctx.primaryExpressionStart().accept(this);

    if (ctx.bracket_expression() != null) {
      name = new HaiScriptArrayAccessExpr(
          name,
          ctx.bracket_expression().indexer_argument()
              .stream()
              .map(x -> x.accept(this))
              .collect(Collectors.toList()));
    }

    for (final PrimaryExpressionSuffixContext sfx : ctx.primaryExpressionSuffix()) {
      if (sfx.ma != null) {
        name = new HaiScriptBinaryExpr(
            ".",
            name,
            sfx.ma.symbol().accept(this));
      } else if (sfx.ie != null) {
        name = new HaiScriptInvocationExpr(name, sfx.ie.accept(this));
      } else if (sfx.op != null) {
        name = new HaiScriptUnaryExpr(sfx.op.getText(), name);
      } else if (sfx.ptr != null) {
        name = new HaiScriptBinaryExpr("->", name, sfx.ptr.accept(this));
      }

      for (final Bracket_expressionContext index : sfx.bracket_expression()) {
        name = new HaiScriptArrayAccessExpr(
            name,
            index.indexer_argument()
                .stream()
                .map(x -> x.accept(this))
                .collect(Collectors.toList()));
      }

      name = this.position(sfx, name);

    }

    return this.position(ctx, name);
  }

  @Override
  public HaiScriptExpr visitPrimaryExpressionStart(HaiParser.PrimaryExpressionStartContext ctx) {
    if (ctx.THIS() != null) {
      return HaiScriptSymbolExpr.fromToken(ctx.THIS().getSymbol());
    } else if (ctx.SUPER() != null) {
      return HaiScriptSymbolExpr.fromToken(ctx.SUPER().getSymbol());
    }
    return ctx.getChild(0).accept(this);
  }

  @Override
  public HaiScriptExpr visitValue(HaiParser.ValueContext ctx) {
    if (ctx.booleanValue() != null) {
      return this.visitBooleanValue(ctx.booleanValue());
    } else if (ctx.stringValue() != null) {
      return this.visitStringValue(ctx.stringValue());
    } else if (ctx.numberValue() != null) {
      return this.visitNumberValue(ctx.numberValue());
    }
    return this.visitChildren(ctx);
  }

  @Override
  public HaiScriptExpr visitHaiTypeUse(HaiParser.HaiTypeUseContext ctx) {
    return this.position(ctx, new HaiScriptSymbolExpr(ctx.haiTypeRef().accept(new TypeRefRewriter())));
  }

  @Override
  public HaiScriptExpr visitNonSelectableExpression(HaiParser.NonSelectableExpressionContext ctx) {
    return ctx.getChild(0).accept(this);
  }

}
