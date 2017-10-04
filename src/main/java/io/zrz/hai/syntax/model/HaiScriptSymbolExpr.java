package io.zrz.hai.syntax.model;

import java.util.Objects;

import org.antlr.v4.runtime.Token;

import io.zrz.hai.lang.TypeRef;
import io.zrz.hai.syntax.HaiScriptExpressionVisitor;
import io.zrz.hai.syntax.HaiScriptToken;
import io.zrz.hai.syntax.HaiSymbol;
import io.zrz.hai.syntax.HaiUnresolvedSymbol;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptSymbolExpr extends AbstractHaiScriptExpr {

  @Getter
  @Setter
  private HaiSymbol symbol;

  public HaiScriptSymbolExpr(HaiSymbol symbol, HaiScriptSourceInfo source) {
    super(source);
    this.symbol = Objects.requireNonNull(symbol);
  }

  public HaiScriptSymbolExpr(HaiSymbol symbol) {
    this.symbol = Objects.requireNonNull(symbol);
  }

  public HaiScriptSymbolExpr(TypeRef symbol) {
    this.symbol = new HaiTypeSymbol(symbol);
  }

  public HaiScriptSymbolExpr(String text) {
    this.symbol = new HaiUnresolvedSymbol(text);
  }

  @Override
  public HaiScriptExprKind getExprKind() {
    return HaiScriptExprKind.SYMBOL;
  }

  @Override
  public String toString() {
    return "{" + this.symbol.getClass().getSimpleName() + ":" + this.symbol.toString() + "}";
  }

  @Override
  public <T, R> R apply(HaiScriptExpressionVisitor<T, R> visitor, T arg) {
    return visitor.visitSymbolExpr(this, arg);
  }

  public static HaiScriptSymbolExpr fromToken(Token token) {
    return new HaiScriptSymbolExpr(new HaiUnresolvedSymbol(new HaiScriptToken(token)), new HaiScriptSourceInfo(token));
  }

  public static HaiScriptExpr fromToken(HaiScriptToken token) {
    return new HaiScriptSymbolExpr(new HaiUnresolvedSymbol(token), new HaiScriptSourceInfo(token));
  }

}
