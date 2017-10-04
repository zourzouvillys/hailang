package io.zrz.hai.haiscript;

/**
 * a code symbol. at parse time, nothing is known about the type system so an
 * {@link HaiUnresolvedSymbol} is created. visitors in the compiler convert them
 * to a HaiResolvedSymbol.
 */

public interface HaiSymbol {

  /**
   * a textual representation of the symbol.
   */

  String getText();

}
