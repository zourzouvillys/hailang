package io.zrz.hai.haiscript.schema;

public class HaiTypeNodeBaseVisitor<R> implements HaiTypeElementNodeVisitor<R> {

  public R visitDefault(HaiTypeElementNode node) {
    return null;
  }

  @Override
  public R visitModule(HaiModuleNode module) {
    return this.visitDefault(module);
  }

  @Override
  public R visitImport(HaiImportElement ie) {
    return this.visitDefault(ie);
  }

  @Override
  public R visitCompilationUnit(HaiCompilationUnit unit) {
    return this.visitDefault(unit);
  }

  @Override
  public R visitTypeElement(HaiTypeDeclElementNode type) {
    return this.visitDefault(type);
  }

  @Override
  public R visitorConstructorElement(HaiTypeConstructorElement ctor) {
    return this.visitDefault(ctor);
  }

  @Override
  public R visitPermissionElement(HaiTypePermissionElement permission) {
    return this.visitDefault(permission);
  }

  @Override
  public R visitMethodElement(HaiTypeMethodElement method) {
    return this.visitDefault(method);
  }

  @Override
  public R visitFieldElement(HaiTypeFieldElement field) {
    return this.visitDefault(field);
  }

  @Override
  public R visitAliasElement(HaiTypeAliasElement alias) {
    return this.visitDefault(alias);
  }

  @Override
  public R visitSetterElement(HaiTypeSetterElement setter) {
    return this.visitDefault(setter);
  }

  @Override
  public R visitFieldUniqueElement(HaiTypeFieldUniqueElement elt) {
    return this.visitDefault(elt);
  }

  @Override
  public R visitTypeRef(HaiTypeElementTypeRef typeref) {
    return this.visitDefault(typeref);
  }

  @Override
  public R visitTypeFieldSelector(HaiTypeFieldSelector selector) {
    return this.visitDefault(selector);
  }

  @Override
  public R visitEnumElement(HaiTypeEnumElement elt) {
    return this.visitDefault(elt);
  }

}
