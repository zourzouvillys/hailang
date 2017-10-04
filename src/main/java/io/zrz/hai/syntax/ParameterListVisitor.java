package io.zrz.hai.syntax;

import org.antlr.v4.runtime.ParserRuleContext;

import io.zrz.hai.lang.antlr4.HaiParser;
import io.zrz.hai.lang.antlr4.HaiParser.NonNullableTypeContext;
import io.zrz.hai.lang.antlr4.HaiParser.ParameterSpreadContext;
import io.zrz.hai.syntax.model.HaiScriptExpr;
import io.zrz.hai.syntax.model.HaiScriptNode;
import io.zrz.hai.syntax.model.HaiScriptParameter;
import io.zrz.hai.syntax.model.HaiScriptParameterListDecl;
import io.zrz.hai.syntax.model.HaiScriptSourceInfo;
import io.zrz.hai.syntax.model.HaiScriptSymbolExpr;
import io.zrz.hai.lang.antlr4.HaiParserBaseVisitor;

public class ParameterListVisitor extends HaiParserBaseVisitor<HaiScriptParameterListDecl> {

  private <T extends ParserRuleContext, R extends HaiScriptNode> R position(T ctx, R node) {
    node.setSource(HaiScriptSourceInfo.from(ctx));
    return node;
  }

  @Override
  public HaiScriptParameterListDecl visitParameterList(HaiParser.ParameterListContext ctx) {

    final HaiScriptParameterListDecl params = new HaiScriptParameterListDecl();

    if (ctx.parameterSpread() != null) {

      for (final ParameterSpreadContext spread : ctx.parameterSpread()) {
        params.add(this.position(spread, new HaiScriptParameterSpreadNode(spread.IDENT())));
      }

    }

    ctx.parameterItem().stream().forEach(param -> {

      final HaiScriptExpr defaultValue = param.expression() == null ? null : param.expression().accept(new ExpressionTreeRewriter());

      final HaiScriptParameter pa = this.position(param, new HaiScriptParameter());

      pa.setDefaultValue(defaultValue);

      pa.setName(param.symbol().getText());

      //
      if (param.parameterFilter() != null) {
        pa.setFilterExpression(param.parameterFilter().expression().accept(new ExpressionTreeRewriter()));
      }

      if (param.haiTypeRef() != null) {

        // final TypeRef type = param.haiTypeRef().accept(new TypeRefRewriter());

        if (param.haiTypeRef() instanceof NonNullableTypeContext) {
          pa.setOptional(false);
        } else {
          pa.setOptional(true);
        }

        pa.setType(HaiScriptSymbolExpr.fromToken(param.haiTypeRef().getStart()));

      } else if (param.parameterSpread() != null) {

        pa.setType(this.position(param.parameterSpread(), new HaiScriptParameterSpreadNode(param.parameterSpread().IDENT())));

      }

      params.getParameters().add(pa);

    });

    params.setSource(HaiScriptSourceInfo.from(ctx));

    return params;

  }

}
