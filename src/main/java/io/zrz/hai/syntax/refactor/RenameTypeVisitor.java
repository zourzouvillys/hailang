package io.zrz.hai.syntax.refactor;

import java.io.OutputStream;
import java.io.PrintStream;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

import io.zrz.hai.syntax.schema.HaiCompilationUnit;
import io.zrz.hai.syntax.schema.HaiImportElement;
import io.zrz.hai.syntax.schema.HaiModuleNode;
import io.zrz.hai.syntax.schema.HaiTypeAliasElement;
import io.zrz.hai.syntax.schema.HaiTypeConstructorElement;
import io.zrz.hai.syntax.schema.HaiTypeDeclElementNode;
import io.zrz.hai.syntax.schema.HaiTypeElementNode;
import io.zrz.hai.syntax.schema.HaiTypeElementNodeVisitor;
import io.zrz.hai.syntax.schema.HaiTypeElementTypeRef;
import io.zrz.hai.syntax.schema.HaiTypeEnumElement;
import io.zrz.hai.syntax.schema.HaiTypeFieldElement;
import io.zrz.hai.syntax.schema.HaiTypeFieldSelector;
import io.zrz.hai.syntax.schema.HaiTypeFieldUniqueElement;
import io.zrz.hai.syntax.schema.HaiTypeMethodElement;
import io.zrz.hai.syntax.schema.HaiTypePermissionElement;
import io.zrz.hai.syntax.schema.HaiTypeSetterElement;

public class RenameTypeVisitor implements HaiTypeElementNodeVisitor<String> {

  /**
   * renames a type in the given module.
   *
   * @param module
   *          The module that contains compilation units to rename types in.
   * @param previousName
   *          The name currently used to rename.
   * @param replacementName
   *          The new name to give the type.
   *
   */

  public static void rename(HaiModuleNode module, String previousName, String replacementName) {

    for (final HaiCompilationUnit unit : module.getUnits()) {

      final HaiTypeDeclElementNode type = unit.getNamedType(previousName);

      if (type == null) {
        continue;
      }

      apply(unit, type, replacementName);

    }

  }

  private static void apply(HaiCompilationUnit unit, HaiTypeElementNode replace, String replacement) {

    final CommonTokenStream src = unit.getTokens();

    dumpBefore(System.err, src, replace);

    System.err.println(replacement);

    dumpAfter(System.err, src, replace);

  }

  /**
   * dump all nodes that are after the given node.
   */

  static void dumpBefore(OutputStream out, CommonTokenStream tokens, HaiTypeElementNode replace) {

    final Range<Integer> range = replace.getSourceInfo().getTokens();

    final int start = range.lowerBoundType() == BoundType.CLOSED ? range.lowerEndpoint() - 1 : range.lowerEndpoint();

    for (final Token tok : tokens.getTokens(0, start)) {
      System.err.print(tok.getText());
    }

  }

  private static void dumpAfter(PrintStream out, CommonTokenStream tokens, HaiTypeElementNode prev) {

    final Range<Integer> range = prev.getSourceInfo().getTokens();

    final int stop = range.upperBoundType() == BoundType.CLOSED ? range.upperEndpoint() + 1 : range.upperEndpoint();

    for (final Token tok : tokens.getTokens(stop, tokens.getTokens().size() - 1)) {
      System.err.print(tok.getText());
    }

  }

  /**
   *
   */

  @Override
  public String visitCompilationUnit(HaiCompilationUnit unit) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String visitPermissionElement(HaiTypePermissionElement permission) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String visitMethodElement(HaiTypeMethodElement method) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String visitFieldElement(HaiTypeFieldElement field) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String visitTypeElement(HaiTypeDeclElementNode type) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String visitorConstructorElement(HaiTypeConstructorElement ctor) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String visitAliasElement(HaiTypeAliasElement alias) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String visitModule(HaiModuleNode module) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String visitImport(HaiImportElement ie) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String visitSetterElement(HaiTypeSetterElement setter) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String visitFieldUniqueElement(HaiTypeFieldUniqueElement elt) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String visitTypeRef(HaiTypeElementTypeRef typeref) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String visitTypeFieldSelector(HaiTypeFieldSelector selector) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String visitEnumElement(HaiTypeEnumElement elt) {
    // TODO Auto-generated method stub
    return null;
  }

}
