package io.zrz.hai.haiscript.schema;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CommonTokenStream;

import io.zrz.hai.haiscript.ExpressionTreeRewriter;
import io.zrz.hai.haiscript.HaiScriptStatementParser;
import io.zrz.hai.haiscript.HaiUnresolvedSymbol;
import io.zrz.hai.haiscript.ParameterListVisitor;
import io.zrz.hai.haiscript.TypeRefRewriter;
import io.zrz.hai.haiscript.model.HaiScriptExpr;
import io.zrz.hai.haiscript.model.HaiScriptExpressionStatement;
import io.zrz.hai.haiscript.model.HaiScriptParameterListDecl;
import io.zrz.hai.haiscript.model.HaiScriptSourceInfo;
import io.zrz.hai.haiscript.model.HaiScriptStatement;
import io.zrz.hai.haiscript.schema.HaiTypeFieldElement.FieldKind;
import io.zrz.hai.lang.TypeRef;
import io.zrz.hai.lang.antlr4.HaiParser;
import io.zrz.hai.lang.antlr4.HaiParser.ExportModifierContext;
import io.zrz.hai.lang.antlr4.HaiParserBaseVisitor;

public class TypeMemberRewriter extends HaiParserBaseVisitor<HaiTypeElementNode> {

  private final HaiTypeDeclElementNode type;
  private final CommonTokenStream tokens;

  public TypeMemberRewriter(HaiTypeDeclElementNode type, CommonTokenStream tokens) {
    this.type = type;
    this.tokens = tokens;
  }

  static HaiTypeElementModifiers readModifiers(HaiParser.ModifiersContext ctx) {

    if (ctx == null || ctx.getChildCount() == 0) {
      return new HaiTypeElementModifiers();
    }

    final Set<HaiTypeElementModifier> modifiers = new HashSet<>();

    if (ctx.exportModifier() != null) {
      for (final ExportModifierContext mod : ctx.exportModifier()) {
        modifiers.add(new HaiTypeElementExportModifier(mod.symbol().stream().map(s -> HaiUnresolvedSymbol.from(s)).collect(Collectors.toSet())));
      }
    }

    modifiers.addAll(
        ctx.mtv.stream()
            .map(x -> HaiTypeElementModifierKind.from(x))
            .collect(Collectors.toSet()));

    return new HaiTypeElementModifiers(modifiers);

  }

  @Override
  public HaiTypeElementNode visitTypeDeclMember(HaiParser.TypeDeclMemberContext ctx) {

    ctx.elementAnnotations();

    final HaiTypeElementModifiers modifiers = TypeMemberRewriter.readModifiers(ctx.modifiers());

    //

    if (ctx.typeDeclField() != null) {

      return this.visitTypeDeclField(ctx.typeDeclField()).addModifiers(modifiers).setSourceInfo(HaiScriptSourceInfo.from(ctx));

    } else if (ctx.typeDeclMethod() != null) {

      return this.visitTypeDeclMethod(ctx.typeDeclMethod()).addModifiers(modifiers).setSourceInfo(HaiScriptSourceInfo.from(ctx));

      // } else if (ctx.typeDeclAlias() != null) {
      //
      // return
      // this.visitTypeDeclAlias(ctx.typeDeclAlias()).addModifiers(modifiers).setSourceInfo(HaiScriptSourceInfo.from(ctx));

    } else if (ctx.typeDeclSetter() != null) {

      return this.visitTypeDeclSetter(ctx.typeDeclSetter()).addModifiers(modifiers).setSourceInfo(HaiScriptSourceInfo.from(ctx));

    } else if (ctx.permissionMember() != null) {

      return ctx.permissionMember().accept(this).setSourceInfo(HaiScriptSourceInfo.from(ctx));

    }

    throw new IllegalArgumentException(ctx.getText());

  }

  /**
   * a grant/reject
   */

  @Override
  public HaiTypeElementNode visitPermissionMember(HaiParser.PermissionMemberContext ctx) {

    HaiTypePermissionElement.Action action;

    if (ctx.GRANT() != null) {
      action = HaiTypePermissionElement.Action.GRANT;
    } else if (ctx.REJECT() != null) {
      action = HaiTypePermissionElement.Action.REJECT;
    } else {
      throw new IllegalArgumentException();
    }

    final String simpleName = ctx.symbol().getText();

    final HaiScriptParameterListDecl initializer = (ctx.parameterList() == null) ? null
        : ctx.parameterList().accept(new ParameterListVisitor());

    final HaiTypePermissionElement permission = new HaiTypePermissionElement(action, simpleName, initializer);

    if (ctx.expression() != null) {

      permission.setBody(new HaiScriptExpressionStatement(ctx.expression().accept(new ExpressionTreeRewriter())));

    } else if (ctx.statement() != null) {

      permission.setBody(ctx.statement().accept(new HaiScriptStatementParser()));

    } else if (ctx.blockStatement() != null) {

      permission.setBody(ctx.blockStatement().accept(new HaiScriptStatementParser()));

    }

    return permission;

  }

  /**
   *
   */

  @Override
  public HaiTypeSetterElement visitTypeDeclSetter(HaiParser.TypeDeclSetterContext ctx) {
    final String simpleName = ctx.symbol().getText();
    final HaiScriptExpr expr = ctx.expression().accept(new ExpressionTreeRewriter());
    return new HaiTypeSetterElement(simpleName, expr);
  }

  /**
   *
   */

  @Override
  public HaiTypeFieldElement visitTypeDeclField(HaiParser.TypeDeclFieldContext ctx) {

    final String simpleName = ctx.ident().getText();

    final HaiTypeFieldElement field = new HaiTypeFieldElement(simpleName);

    if (ctx.CONST() != null) {
      field.setFieldKind(FieldKind.CONST);
    } else if (ctx.VAR() != null) {
      field.setFieldKind(FieldKind.VAR);
    } else {
      field.setFieldKind(FieldKind.UNSPECIFIED);
    }

    if (ctx.haiTypeRef() != null) {

      final TypeRef reftype = ctx.haiTypeRef().accept(new TypeRefRewriter());

      if (ctx.expression() != null) {
        field.setDefaultValue(ctx.expression().accept(new ExpressionTreeRewriter()));
      }

      String alias = null;

      if (ctx.outputTypeAlias != null) {
        alias = ctx.outputTypeAlias.getText();
      }

      field.setType(new HaiTypeElementTypeRef(reftype, alias));

      if (ctx.fieldDeclBody() != null) {

        final FieldMemberRewriter rewriter = new FieldMemberRewriter();
        ctx.fieldDeclBody().fieldDeclBodyMember().stream().map(d -> d.accept(rewriter)).forEach(m -> field.addElement(m));

      }
      return field;

    } else {

      final HaiTypeElementNode typedecl = ctx.connectionTypeDecl().accept(new TypeElementRewriter(this.tokens));

      field.setType(typedecl);

    }

    return field;

  }

  /**
   *
   */

  @Override
  public HaiTypeElementMember visitTypeDeclMethod(HaiParser.TypeDeclMethodContext ctx) {

    String simpleName = null;

    if (ctx.INIT() == null) {
      // method
      simpleName = ctx.ident().getText();
    }

    HaiTypeElementTypeRef xreturnType = null;

    if (ctx.haiTypeRef() != null) {
      final TypeRef returnType = ctx.haiTypeRef().accept(new TypeRefRewriter());
      xreturnType = new HaiTypeElementTypeRef(returnType);
      xreturnType.setSourceInfo(HaiScriptSourceInfo.from(ctx.haiTypeRef()));
    }

    HaiScriptStatement body = null;

    final HaiScriptParameterListDecl params = ctx.parameterList() == null ? null : ctx.parameterList().accept(new ParameterListVisitor());

    if (ctx.right_arrow() != null) {
      body = ctx.statement().accept(new HaiScriptStatementParser());
    } else if (ctx.blockStatement() != null) {

      body = ctx.blockStatement().accept(new HaiScriptStatementParser());

    } else if (ctx.expression() != null) {
      body = new HaiScriptExpressionStatement(ctx.expression().accept(new ExpressionTreeRewriter()));
    }

    if (simpleName == null) {
      return new HaiTypeConstructorElement(params, xreturnType, body);
    }

    return new HaiTypeMethodElement(simpleName, params, xreturnType, body);

  }

}
