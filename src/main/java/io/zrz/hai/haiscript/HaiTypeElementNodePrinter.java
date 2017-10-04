package io.zrz.hai.haiscript;

import java.io.OutputStream;

import io.zrz.hai.haiscript.schema.HaiCompilationUnit;
import io.zrz.hai.haiscript.schema.HaiImportElement;
import io.zrz.hai.haiscript.schema.HaiModuleNode;
import io.zrz.hai.haiscript.schema.HaiTypeAliasElement;
import io.zrz.hai.haiscript.schema.HaiTypeConstructorElement;
import io.zrz.hai.haiscript.schema.HaiTypeDeclElementNode;
import io.zrz.hai.haiscript.schema.HaiTypeElementMember;
import io.zrz.hai.haiscript.schema.HaiTypeElementModifier;
import io.zrz.hai.haiscript.schema.HaiTypeElementModifiers;
import io.zrz.hai.haiscript.schema.HaiTypeElementNode;
import io.zrz.hai.haiscript.schema.HaiTypeElementNodeVisitor;
import io.zrz.hai.haiscript.schema.HaiTypeElementTypeRef;
import io.zrz.hai.haiscript.schema.HaiTypeEnumElement;
import io.zrz.hai.haiscript.schema.HaiTypeFieldElement;
import io.zrz.hai.haiscript.schema.HaiTypeFieldSelector;
import io.zrz.hai.haiscript.schema.HaiTypeFieldUniqueElement;
import io.zrz.hai.haiscript.schema.HaiTypeMethodElement;
import io.zrz.hai.haiscript.schema.HaiTypePermissionElement;
import io.zrz.hai.haiscript.schema.HaiTypeSetterElement;
import io.zrz.hai.lang.TypeRef;

public class HaiTypeElementNodePrinter implements HaiTypeElementNodeVisitor<Void> {

  private final IndentPrintWriter out;

  public HaiTypeElementNodePrinter(IndentPrintWriter out) {
    this.out = out;
  }

  @Override
  public Void visitModule(HaiModuleNode module) {
    module.getUnits().forEach(this::visitCompilationUnit);
    return null;
  }

  @Override
  public Void visitCompilationUnit(HaiCompilationUnit unit) {

    if (unit.getNamespace() != null) {
      this.out.append("namespace ").append(unit.getNamespace()).println();
      this.out.println();
    }

    if (!unit.getImports().isEmpty()) {
      unit.getImports().forEach(this::visitImport);
      this.out.println();
    }

    unit.getElements().forEach(elt -> elt.apply(this));

    return null;
  }

  @Override
  public Void visitTypeElement(HaiTypeDeclElementNode type) {

    if (!type.getAnnotations().isEmpty()) {
      type.getAnnotations().forEach(ant -> {
        this.out.append("[").append(ant.toString()).append("]");
        this.out.println();
      });
    }

    this.visitModifiers(type.getModifiers());

    this.out.append(type.getKind().toString()).append(" ").append(type.getSimpleName());

    if (type.getContextName() != null) {

      this.out.append("(");
      this.out.append(type.getContextName());

      if (type.getContextType() != null) {
        this.out.append(": ");
        this.out.append(type.getContextType().toString());
      }

      this.out.append(")");

    } else if (type.getContextType() != null) {
      this.out.append("(_: ");
      this.out.append(type.getContextType().toString());
      this.out.append(")");
    }

    if (type.getSuperType() != null) {
      this.out.append(" extends ").append(type.getSuperType().toString());
    }

    if (!type.getSuperInterfaces().isEmpty()) {
      this.out.append(" implements ");
      int count = 0;
      for (final TypeRef st : type.getSuperInterfaces()) {
        if (count++ > 0) {
          this.out.append(", ");
        }
        this.out.append(st.toString());
      }
    }

    this.out.println(" {");
    this.out.inc();

    if (!type.getElements().isEmpty()) {
      this.out.println();
      type.getElements().forEach(elt -> elt.apply(this));
      this.out.println();
    }

    this.out.dec();
    this.out.println("}");
    this.out.println();

    return null;
  }

  private void visitModifiers(HaiTypeElementModifiers modifiers) {
    for (final HaiTypeElementModifier mod : modifiers.getModifiers()) {
      this.out.print(mod.toString());
      this.out.append(" ");
    }
  }

  @Override
  public Void visitFieldElement(HaiTypeFieldElement field) {

    this.visitModifiers(field.getModifiers());

    this.out.append(field.getFieldKind().toString());

    this.out.append(" ");

    this.out.append(field.getSimpleName());

    if (field.getType() != null) {
      this.out.append(" : ");
      field.getType().apply(this);
    }

    if (field.getDefaultValue() != null) {
      this.out.append(" = ");
      HaiScriptCodePrinter.print(this.out, field.getDefaultValue());
    }

    if (!field.getMembers().isEmpty()) {
      this.out.println(" {");
      this.out.println();
      this.out.inc();
      for (final HaiTypeElementMember member : field.getMembers()) {
        member.apply(this);
        this.out.println();
      }
      this.out.dec();
      this.out.println("}");

    }

    this.out.println();

    return null;
  }

  @Override
  public Void visitorConstructorElement(HaiTypeConstructorElement ctor) {
    this.visitModifiers(ctor.getModifiers());
    this.out.append("ctor ");
    if (ctor.getReturnType() != null) {
      this.out.append(" : ");
      this.out.append(ctor.getReturnType().toString());
    }
    ctor.getParameters().apply(new HaiScriptPrinter(this.out), null);
    this.out.println();
    return null;
  }

  @Override
  public Void visitPermissionElement(HaiTypePermissionElement permission) {
    this.out.append(permission.getAction().toString());

    if (permission.getSimpleName() != null) {
      this.out.append(" ");
      this.out.append(permission.getSimpleName());
    }

    HaiScriptCodePrinter.print(this.out, permission.getInitializer());

    if (permission.getBody() != null) {
      this.out.append(" => ");
      HaiScriptCodePrinter.print(this.out, permission.getBody());
    }

    this.out.println();
    return null;
  }

  @Override
  public Void visitMethodElement(HaiTypeMethodElement method) {

    this.visitModifiers(method.getModifiers());

    this.out.append(method.getSimpleName());

    if (method.getReturnType() != null) {
      this.out.append(" : ");
      this.out.append(method.getReturnType().toString());
    }

    if (method.getParameters() != null) {
      method.getParameters().apply(new HaiScriptPrinter(this.out), null);
    }

    this.out.append(" ");

    if (method.getBody() != null) {

      if (HaiScriptUtils.hasSubStatement(method.getBody())) {

        HaiScriptCodePrinter.print(this.out, method.getBody());

      } else {

        this.out.append("=> ");
        HaiScriptCodePrinter.print(this.out, method.getBody());

      }

    } else {

      this.out.append(";");

    }

    this.out.println();
    this.out.println();
    return null;
  }

  @Override
  public Void visitAliasElement(HaiTypeAliasElement alias) {
    this.visitModifiers(alias.getModifiers());
    this.out.append("TYPEALIAS ");
    this.out.append(alias.getSimpleName());
    this.out.append(" = ");
    this.out.append(alias.getTargetTypeName());
    this.out.println(";");
    this.out.println();
    return null;
  }

  @Override
  public Void visitSetterElement(HaiTypeSetterElement setter) {
    this.visitModifiers(setter.getModifiers());
    this.out.append(setter.getSimpleName());
    this.out.append(" -> ");
    HaiScriptCodePrinter.print(this.out, setter.getExpression());
    this.out.println();
    return null;
  }

  @Override
  public Void visitImport(HaiImportElement ie) {
    this.out.append("import ").append(ie.getName());
    if (ie.getAs() != null) {
      this.out.append(" ").append(ie.getAs());
    }
    this.out.println();
    return null;
  }

  public static void print(OutputStream out, HaiTypeElementNode module) {
    final IndentPrintWriter w = new IndentPrintWriter(out);
    module.apply(new HaiTypeElementNodePrinter(w));
    w.flush();
  }

  @Override
  public Void visitFieldUniqueElement(HaiTypeFieldUniqueElement elt) {

    this.visitModifiers(elt.getModifiers());

    this.out.append(elt.getFieldKind().toString());

    if (elt.getIndexName() != null) {
      this.out.append(" ");
      this.out.append(elt.getIndexName());
    }

    this.out.println(" {");
    this.out.inc();

    elt.getSelections().forEach(sel -> {
      HaiScriptCodePrinter.print(this.out, sel);
      this.out.println();
    });

    elt.getKeys().forEach(x -> {

      this.visitModifiers(x.getModifiers());
      this.out.append(x.getName());

      if (x.getParams() != null) {
        HaiScriptCodePrinter.print(this.out, x.getParams());
      }

      if (x.getBody() != null) {
        this.out.append(" => ");
        HaiScriptCodePrinter.print(this.out, x.getBody());
      }

      this.out.println();

    });

    this.out.dec();
    this.out.println("}");

    return null;
  }

  @Override
  public Void visitTypeRef(HaiTypeElementTypeRef typeref) {
    this.out.append(typeref.getType().toString());
    return null;
  }

  @Override
  public Void visitTypeFieldSelector(HaiTypeFieldSelector selector) {
    throw new IllegalArgumentException(selector.toString());
  }

  @Override
  public Void visitEnumElement(HaiTypeEnumElement elt) {
    this.out.println(elt.getName());
    return null;
  }

}
