package io.zrz.hai.haiscript.schema;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.RuleNode;

import io.zrz.hai.haiscript.HaiScriptCommentNode;
import io.zrz.hai.haiscript.TypeRefRewriter;
import io.zrz.hai.haiscript.model.HaiScriptSourceInfo;
import io.zrz.hai.lang.TypeRef;
import io.zrz.hai.lang.antlr4.HaiParser;
import io.zrz.hai.lang.antlr4.HaiParser.ConcreteDeclaredTypeContext;
import io.zrz.hai.lang.antlr4.HaiParser.EnumMemberContext;
import io.zrz.hai.lang.antlr4.HaiParser.SymbolContext;
import io.zrz.hai.lang.antlr4.HaiParserBaseVisitor;

public class TypeElementRewriter extends HaiParserBaseVisitor<HaiTypeElementNode> {

  private final CommonTokenStream tokens;

  public TypeElementRewriter(CommonTokenStream tokens) {
    this.tokens = tokens;
  }

  @Override
  public HaiTypeElementNode visitChildren(RuleNode ctx) {
    throw new IllegalArgumentException(ctx.getClass().toString());
  }

  /**
   *
   */

  @Override
  public HaiTypeElementNode visitTypeAliasDecl(HaiParser.TypeAliasDeclContext ctx) {
    final HaiTypeElementModifiers modifiers = TypeMemberRewriter.readModifiers(ctx.modifiers());
    final HaiTypeAliasElement elt = new HaiTypeAliasElement(ctx.IDENT(0).toString(), ctx.IDENT(1).getSymbol().getText());
    elt.getModifiers().addModifiers(modifiers);
    HaiScriptCommentNode.apply(this.tokens, ctx, elt);
    return elt.setSourceInfo(HaiScriptSourceInfo.from(ctx));
  }

  @Override
  public HaiTypeDeclElementNode visitEnumDecl(HaiParser.EnumDeclContext ctx) {

    final HaiTypeDeclElementNode type = new HaiTypeDeclElementNode(HaiTypeElementNodeKind.ENUM, ctx.IDENT().toString());

    final HaiTypeElementModifiers modifiers = TypeMemberRewriter.readModifiers(ctx.modifiers());

    if (ctx.EXTEND() != null) {
      modifiers.getModifiers().add(HaiTypeElementModifierKind.EXTENSION);
    }

    type.addModifiers(modifiers);

    for (final EnumMemberContext member : ctx.enumMember()) {

      final SymbolContext sym = member.symbol();

      type.addElement(new HaiTypeEnumElement(sym.getText()));

    }

    type.setSourceInfo(HaiScriptSourceInfo.from(ctx));

    HaiScriptCommentNode.apply(this.tokens, ctx, type);

    return type;

  }

  /**
   *
   */

  @Override
  public HaiTypeDeclElementNode visitTypeDecl(HaiParser.TypeDeclContext ctx) {

    final HaiTypeElementModifiers modifiers = TypeMemberRewriter.readModifiers(ctx.modifiers());

    if (ctx.EXTEND() != null) {
      modifiers.getModifiers().add(HaiTypeElementModifierKind.EXTENSION);
    }

    final String typeType = ctx.type.getText();

    final String simpleName = ctx.IDENT().toString();

    final HaiTypeDeclElementNode type = new HaiTypeDeclElementNode(HaiTypeElementNodeKind.valueOf(typeType.toUpperCase()), simpleName);

    if (ctx.elementAnnotations() != null) {
      ctx.elementAnnotations().elementAnnotation().forEach(elt -> type.getAnnotations().add(elt.accept(new ElementAnnotationParser())));
    }

    type.addModifiers(modifiers);

    if (ctx.typeContextDecl() != null) {
      final String name = ctx.typeContextDecl().IDENT().getText();
      final TypeRef tr = ctx.typeContextDecl().concreteDeclaredType().accept(new TypeRefRewriter());
      final HaiTypeElementModifiers ctxmod = TypeMemberRewriter.readModifiers(ctx.typeContextDecl().modifiers());
      type.setContext(ctxmod, name, tr);
    }

    type.setSimpleName(simpleName);

    if (ctx.superType != null) {
      type.setSuperType(ctx.superType.accept(new TypeRefRewriter()));
    }

    for (final ConcreteDeclaredTypeContext impl : ctx.impls) {
      type.getSuperInterfaces().add(impl.accept(new TypeRefRewriter()));
    }

    final TypeMemberRewriter visitor = new TypeMemberRewriter(type, this.tokens);
    ctx.typeDeclMember().forEach(decl -> {

      final HaiTypeElementNode elt = decl.accept(visitor).setSourceInfo(HaiScriptSourceInfo.from(decl));

      HaiScriptCommentNode.apply(this.tokens, decl, elt);

      type.addElement(elt);

    });

    type.setSourceInfo(HaiScriptSourceInfo.from(ctx));

    HaiScriptCommentNode.apply(this.tokens, ctx, type);

    return type;

  }

  @Override
  public HaiTypeDeclElementNode visitConnectionTypeDecl(HaiParser.ConnectionTypeDeclContext ctx) {

    final HaiTypeElementModifiers modifiers = TypeMemberRewriter.readModifiers(ctx.modifiers());

    if (ctx.EXTEND() != null) {
      modifiers.getModifiers().add(HaiTypeElementModifierKind.EXTENSION);
    }

    final String simpleName = ctx.IDENT().toString();

    final HaiTypeDeclElementNode type = new HaiTypeDeclElementNode(HaiTypeElementNodeKind.CONNECTION, simpleName);

    if (ctx.elementAnnotations() != null) {
      ctx.elementAnnotations().elementAnnotation().forEach(elt -> type.getAnnotations().add(elt.accept(new ElementAnnotationParser())));
    }

    type.addModifiers(modifiers);

    if (ctx.typeContextDecl() != null) {
      final String name = ctx.typeContextDecl().IDENT().getText();
      final TypeRef tr = ctx.typeContextDecl().concreteDeclaredType().accept(new TypeRefRewriter());
      final HaiTypeElementModifiers ctxmod = TypeMemberRewriter.readModifiers(ctx.typeContextDecl().modifiers());
      type.setContext(ctxmod, name, tr);
    }

    type.setSimpleName(ctx.IDENT().toString());

    if (ctx.superType != null) {
      type.setSuperType(ctx.superType.accept(new TypeRefRewriter()));
    }

    for (final ConcreteDeclaredTypeContext impl : ctx.impls) {
      type.getSuperInterfaces().add(impl.accept(new TypeRefRewriter()));
    }

    final FieldMemberRewriter visitor = new FieldMemberRewriter();

    ctx.fieldDeclBodyMember().forEach(decl -> type.addElement(decl.accept(visitor)));

    type.setSourceInfo(HaiScriptSourceInfo.from(ctx));

    HaiScriptCommentNode.apply(this.tokens, ctx, type);

    return type;
  }

}
