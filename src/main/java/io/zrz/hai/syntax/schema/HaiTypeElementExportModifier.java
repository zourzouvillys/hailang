package io.zrz.hai.syntax.schema;

import java.util.Set;
import java.util.stream.Collectors;

import io.zrz.hai.syntax.HaiUnresolvedSymbol;
import lombok.Getter;
import lombok.Setter;

public class HaiTypeElementExportModifier implements HaiTypeElementModifier {

  @Getter
  @Setter
  private Set<HaiUnresolvedSymbol> symbols;

  public HaiTypeElementExportModifier(Set<HaiUnresolvedSymbol> symbols) {
    this.symbols = symbols;
  }

  @Override
  public HaiTypeElementModifierKind getModifierKind() {
    return HaiTypeElementModifierKind.EXPORT;
  }

  @Override
  public String toString() {
    if (this.symbols.isEmpty()) {
      return "EXPORT";
    }
    return "EXPORT(" + this.symbols.stream().map(sym -> sym.getText()).collect(Collectors.joining(",")) + ")";
  }

}
