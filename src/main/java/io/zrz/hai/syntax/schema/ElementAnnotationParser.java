package io.zrz.hai.syntax.schema;

import org.antlr.v4.runtime.tree.RuleNode;

import io.zrz.hai.lang.antlr4.HaiParser.KeywordAnnotationContext;
import io.zrz.hai.lang.antlr4.HaiParser.RenameAnnotationContext;
import io.zrz.hai.lang.antlr4.HaiParserBaseVisitor;

public class ElementAnnotationParser extends HaiParserBaseVisitor<HaiTypeElementAnnotation> {

  @Override
  public HaiTypeElementAnnotation visitChildren(RuleNode node) {
    throw new IllegalArgumentException();
  }

  @Override
  public HaiTypeElementAnnotation visitKeywordAnnotation(KeywordAnnotationContext ctx) {
    return new HaiTypeElementAnnotation();
  }

  @Override
  public HaiTypeElementAnnotation visitRenameAnnotation(RenameAnnotationContext ctx) {
    return new HaiTypeElementAnnotation();
  }

}
