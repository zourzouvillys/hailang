package io.zrz.hai.haiscript.schema;

import org.antlr.v4.runtime.CommonTokenStream;

import io.zrz.hai.haiscript.model.HaiScriptSourceInfo;
import io.zrz.hai.lang.antlr4.HaiParser;
import io.zrz.hai.lang.antlr4.HaiParser.CompilationUnitImportContext;
import io.zrz.hai.lang.antlr4.HaiParserBaseVisitor;

public class CompilationUnitRewriter extends HaiParserBaseVisitor<HaiTypeElementNode> {

  private final CommonTokenStream tokens;

  public CompilationUnitRewriter(CommonTokenStream tokens) {
    this.tokens = tokens;
  }

  @Override
  public HaiCompilationUnit visitCompilationUnit(HaiParser.CompilationUnitContext ctx) {

    final HaiCompilationUnit unit = new HaiCompilationUnit(this.tokens);

    unit.setSourceInfo(HaiScriptSourceInfo.from(ctx));

    if (ctx.NAMESPACE() != null) {
      unit.setNamespace(ctx.ns.getText());
    }

    for (final CompilationUnitImportContext im : ctx.compilationUnitImport()) {
      final HaiImportElement node = new HaiImportElement();
      node.setName(im.name().getText());
      if (im.symbol() != null) {
        node.setAs(im.symbol().getText());
      }
      node.setSourceInfo(HaiScriptSourceInfo.from(im));
      unit.getImports().add(node);
    }

    ctx.typeDecl().forEach(decl -> unit.addElement(decl.accept(new TypeElementRewriter(this.tokens))));
    ctx.connectionTypeDecl().forEach(decl -> unit.addElement(decl.accept(new TypeElementRewriter(this.tokens))));
    ctx.typeAliasDecl().forEach(decl -> unit.addElement(decl.accept(new TypeElementRewriter(this.tokens))));
    ctx.enumDecl().forEach(decl -> unit.addElement(decl.accept(new TypeElementRewriter(this.tokens))));

    return unit;
  }

}
