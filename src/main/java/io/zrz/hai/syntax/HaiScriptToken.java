package io.zrz.hai.syntax;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;

import lombok.Getter;

/**
 * Each node generated from source code has a token which represents where in
 * the source it was parsed from.
 */
public class HaiScriptToken {

  @Getter
  private final int line;
  @Getter
  private final int position;
  @Getter
  private final int startIndex;
  @Getter
  private final int stopIndex;
  @Getter
  private final String text;
  @Getter
  private final TokenSource source;

  public HaiScriptToken(Token token) {
    this.source = token.getTokenSource();
    this.line = token.getLine();
    this.position = token.getCharPositionInLine();
    this.startIndex = token.getStartIndex();
    this.stopIndex = token.getStopIndex();
    this.text = token.getText();
  }

  public HaiScriptToken(String text) {
    this.text = text;
    this.line = 0;
    this.position = 0;
    this.startIndex = 0;
    this.stopIndex = 0;
    this.source = null;
  }

}
