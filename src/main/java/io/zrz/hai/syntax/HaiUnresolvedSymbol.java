package io.zrz.hai.syntax;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import io.zrz.hai.lang.TypeRef;
import io.zrz.hai.lang.antlr4.HaiParser.SymbolContext;
import lombok.Getter;

/**
 * a symbol which has not yet been resolved, so remains a mystery.
 *
 * because of namespaces references, the parser consumes all symbols seperated
 * by '.' as a single symbol at the start of an expression. the symbol manager
 * then converts them to actual symbols, and adds synthetic binary operators if
 * they are actually member accesses rather than a qualified or canonical type
 * reference.
 *
 */

public class HaiUnresolvedSymbol implements HaiSymbol {

  @Getter
  private final List<HaiScriptToken> tokens;

  @Getter
  private TypeRef context;

  public HaiUnresolvedSymbol(HaiScriptToken symbol) {
    this.tokens = ImmutableList.of(symbol);
  }

  public HaiUnresolvedSymbol(String text) {
    this.tokens = ImmutableList.of(new HaiScriptToken(text));
  }

  public HaiUnresolvedSymbol(List<HaiScriptToken> symbol) {
    this.tokens = symbol;
  }

  @Override
  public String getText() {
    return this.tokens.stream().map(sym -> sym.getText()).collect(Collectors.joining("."));
  }

  public HaiUnresolvedSymbol asFirstToken() {
    return new HaiUnresolvedSymbol(this.tokens.get(0)).setContext(this.context);
  }

  @Override
  public String toString() {
    return "HaiUnresolvedSymbol[" + this.getText() + (this.context == null ? "" : " in " + this.context) + "]";
  }

  public String asSingleSymbol() {
    Preconditions.checkState(this.tokens.size() == 1);
    return this.tokens.get(0).getText();
  }

  public HaiUnresolvedSymbol setContext(TypeRef type) {
    this.context = type;
    return this;
  }

  public static HaiUnresolvedSymbol from(SymbolContext s) {
    Preconditions.checkArgument(s.getChildCount() == 1);
    return new HaiUnresolvedSymbol(new HaiScriptToken(s.getStart()));
  }

}
