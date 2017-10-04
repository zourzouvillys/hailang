package io.zrz.hai.haiscript;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Objects;
import java.util.stream.Collectors;

import io.zrz.hai.haiscript.model.HaiScriptExpr;
import io.zrz.hai.haiscript.model.HaiScriptNode;
import io.zrz.hai.haiscript.model.HaiScriptNodeProperties;
import io.zrz.hai.haiscript.model.HaiScriptStatement;
import io.zrz.hai.haiscript.model.HaiScriptSymbolExpr;
import io.zrz.hai.lang.HaiElement;
import io.zrz.hai.lang.TypeRef;

public class HaiScriptTreePrinter extends DefaultHaiScriptVisitor<Void, Void> {

  private final IndentPrintWriter writer;
  private HaiScriptNodeVisitor<Void, String> detailProvider;
  private HaiScriptExpressionVisitor<?, String> typeProvider;

  public HaiScriptTreePrinter(OutputStream out, HaiScriptNodeVisitor<Void, String> detailProvider, HaiScriptExpressionVisitor<?, String> typeProvider) {
    this.writer = new IndentPrintWriter(out);
    this.detailProvider = detailProvider;
    this.typeProvider = typeProvider;
  }

  public HaiScriptTreePrinter(IndentPrintWriter out, HaiScriptNodeVisitor<Void, String> detailProvider, HaiScriptExpressionVisitor<?, String> typeProvider) {
    this.writer = out;
    this.detailProvider = detailProvider;
    this.typeProvider = typeProvider;
  }

  public HaiScriptTreePrinter(IndentPrintWriter out) {
    this.writer = out;
  }

  public HaiScriptTreePrinter(Writer out) {
    this.writer = new IndentPrintWriter(out);
  }

  @Override
  public Void visitEnter(HaiScriptNode node) {
    this.writer.println();
    this.writer.inc();
    return null;
  }

  @Override
  public Void visitLeave(HaiScriptNode node, Void arg, Void value) {
    this.writer.dec();
    return null;
  }

  @Override
  public Void visitType(HaiScriptNode declaring, TypeRef type, Void arg) {
    return null;
  }

  @Override
  public Void visitDefault(HaiScriptNode node) {

    if (node instanceof HaiScriptExpr) {

      final HaiScriptExpr expr = ((HaiScriptExpr) node);

      String type = null;

      if (this.typeProvider != null) {
        type = expr.apply(this.typeProvider, null);
      }

      if (type != null) {
        this.writer.print(node.getNodeKind() + " " + expr.getExprKind() + ": " + node.getClass().getSimpleName() + ": <<<" + type + ">>>");
      } else {
        this.writer.print(node.getNodeKind() + " " + expr.getExprKind() + ": " + node.getClass().getSimpleName());
      }

    } else if (node instanceof HaiScriptStatement) {

      this.writer.print(node.getNodeKind() + " " + ((HaiScriptStatement) node).getStatementKind() + " " + node.getClass().getSimpleName());

    } else {

      this.writer.print(node.getNodeKind() + " " + node.getClass().getSimpleName());

    }

    if (this.detailProvider != null) {

      final String detail = node.apply(this.detailProvider, null);

      if (detail != null) {
        this.writer.inc();
        this.writer.print(" " + detail);
        this.writer.dec();
      }

    }

    return null;
  }

  @Override
  public Void visitMember(HaiElement member, Void arg) {
    this.writer.print(" member=" + member);
    return null;
  }

  public static void dump(PrintStream out, HaiScriptNode node, HaiScriptNodeVisitor<Void, String> detailProvider,
      HaiScriptExpressionVisitor<?, String> typeProvider) {
    Objects.requireNonNull(node);
    final HaiScriptTreePrinter printer = new HaiScriptTreePrinter(out, detailProvider, typeProvider);
    node.apply((HaiScriptVisitor<Void, Void>) printer, null);
    printer.writer.println();
    printer.writer.println();
    printer.writer.flush();
    out.flush();

  }

  public static void dump(IndentPrintWriter out, HaiScriptNode node, HaiScriptNodeVisitor<Void, String> detailProvider,
      HaiScriptExpressionVisitor<?, String> typeProvider) {
    Objects.requireNonNull(node);
    final HaiScriptTreePrinter printer = new HaiScriptTreePrinter(out, detailProvider, typeProvider);
    node.apply((HaiScriptVisitor<Void, Void>) printer, null);
    printer.writer.println();
    printer.writer.println();
    printer.writer.flush();
    out.flush();

  }

  public static void dump(PrintStream out, HaiScriptNode node) {

    dump(out, node, new DefaultHaiScriptVisitor<Void, String>() {

      @Override
      public String visitDefault(HaiScriptNode node) {
        if (node.getProperties().isEmpty()) {
          if (node.getSource() == null) {
            return "";
          }
          return node.getSource().toString();
        }
        return node.getProperties().stream().map(HaiScriptNodeProperties::toString).collect(Collectors.joining("\n", "\n", ""));
      }

    }, new DefaultHaiScriptVisitor<Void, String>() {

      @Override
      public String visitSymbolExpr(HaiScriptSymbolExpr expr, Void arg) {
        return "SYMBOL=" + expr.getSymbol().getClass().getSimpleName() + " " + expr.getSymbol().getText();
      }

    });
  }

}
