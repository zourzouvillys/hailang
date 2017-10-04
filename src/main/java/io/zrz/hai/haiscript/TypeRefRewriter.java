package io.zrz.hai.haiscript;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.tree.RuleNode;

import io.zrz.hai.lang.ArrayTypeRef;
import io.zrz.hai.lang.IntersectionTypeRef;
import io.zrz.hai.lang.ParameterizedTypeRef;
import io.zrz.hai.lang.StructField;
import io.zrz.hai.lang.StructTypeDecl;
import io.zrz.hai.lang.SymbolTypeRef;
import io.zrz.hai.lang.TupleTypeDecl;
import io.zrz.hai.lang.TypeRef;
import io.zrz.hai.lang.UnionTypeRef;
import io.zrz.hai.lang.antlr4.HaiParser;
import io.zrz.hai.lang.antlr4.HaiParserBaseVisitor;

public class TypeRefRewriter extends HaiParserBaseVisitor<TypeRef> {

  @Override
  public TypeRef visitChildren(RuleNode ctx) {
    throw new IllegalArgumentException(ctx.getClass().getCanonicalName());
  }

  @Override
  public TypeRef visitStandaloneHaiTypeRef(HaiParser.StandaloneHaiTypeRefContext ctx) {
    return ctx.haiTypeRef().accept(this);
  }

  @Override
  public TypeRef visitNonNullableType(HaiParser.NonNullableTypeContext ctx) {
    return ctx.haiTypeRef().accept(this);
  }

  @Override
  public TypeRef visitSetType(HaiParser.SetTypeContext ctx) {
    return new StructTypeDecl(Collections.emptyList());
  }

  @Override
  public TypeRef visitArrayType(HaiParser.ArrayTypeContext ctx) {
    return new ArrayTypeRef(ctx.haiTypeRef().accept(this));
  }

  @Override
  public TypeRef visitNullableType(HaiParser.NullableTypeContext ctx) {
    return ctx.haiTypeRef().accept(this);
  }

  @Override
  public TypeRef visitDirectType(HaiParser.DirectTypeContext ctx) {
    return SymbolTypeRef.fromString(ctx.rawTypeName().getText());
  }

  @Override
  public TypeRef visitBinaryType(HaiParser.BinaryTypeContext ctx) {

    final TypeRef left = ctx.haiTypeRef().accept(this);
    final String op = ctx.typeBinaryOperator().getText();
    final TypeRef right = ctx.annotatedTypeRef().accept(this);

    switch (op) {
      case "|":
        return UnionTypeRef.create(left, right);
      case "&":
        return IntersectionTypeRef.create(left, right);
      // case "~":
      // return DisjointTypeRef.create(left, right);
      default:
        throw new IllegalArgumentException(op);
    }

  }

  // ( IDENT opt='?'? ':')? haiTypeRef

  @Override
  public TypeRef visitTupleType(HaiParser.TupleTypeContext ctx) {

    if (ctx.typeMemberList() == null || ctx.typeMemberList().typeMember() == null) {
      return new TupleTypeDecl();
    }

    final List<StructField> tupleMember = ctx.typeMemberList().typeMember().stream().map(x -> {

      return StructField.of(x.IDENT() == null ? null : x.IDENT().getText(), x.haiTypeRef().accept(this), x.opt != null);

    }).collect(Collectors.toList());

    return new TupleTypeDecl(tupleMember);

  }

  @Override
  public TypeRef visitAnnotatedTypeRef(HaiParser.AnnotatedTypeRefContext ctx) {
    return ctx.type.accept(this);
  }

  @Override
  public TypeRef visitRawType(HaiParser.RawTypeContext ctx) {
    return SymbolTypeRef.fromString(ctx.getText());
  }

  @Override
  public TypeRef visitParameterizedType(HaiParser.ParameterizedTypeContext ctx) {
    return new ParameterizedTypeRef(
        SymbolTypeRef.fromString(ctx.rawTypeName().IDENT().getText()),
        ctx.typeList()
            .annotatedTypeRef()
            .stream()
            .map(x -> x.accept(this))
            .collect(Collectors.toList()));
  }

}
