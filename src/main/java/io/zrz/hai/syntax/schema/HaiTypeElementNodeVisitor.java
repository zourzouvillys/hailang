package io.zrz.hai.syntax.schema;

public interface HaiTypeElementNodeVisitor<R> {

  R visitModule(HaiModuleNode module);

  R visitImport(HaiImportElement ie);

  R visitCompilationUnit(HaiCompilationUnit unit);

  R visitTypeElement(HaiTypeDeclElementNode type);

  //

  R visitorConstructorElement(HaiTypeConstructorElement ctor);

  R visitPermissionElement(HaiTypePermissionElement permission);

  R visitMethodElement(HaiTypeMethodElement method);

  R visitFieldElement(HaiTypeFieldElement field);

  R visitAliasElement(HaiTypeAliasElement alias);

  R visitSetterElement(HaiTypeSetterElement setter);

  R visitFieldUniqueElement(HaiTypeFieldUniqueElement elt);

  R visitEnumElement(HaiTypeEnumElement elt);

  //

  R visitTypeRef(HaiTypeElementTypeRef typeref);

  R visitTypeFieldSelector(HaiTypeFieldSelector selector);

}
