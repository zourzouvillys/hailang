package io.zrz.hai.syntax.model;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;

import com.google.common.collect.Range;

import io.zrz.hai.syntax.HaiScriptToken;
import lombok.Getter;

/**
 * stores info about positions in the source - the raw text, offset, and the
 * token ID in the source for refactoring.
 *
 * if nodes are copied or replaced, they must copy the source info, otherwise
 * refactoring and error reporting will not work.
 *
 */

public class HaiScriptSourceInfo {

  @Getter
  public int startPositionInLine;

  @Getter
  public int stopPositionInLine;

  @Getter
  private final Range<Integer> charIndex;

  @Getter
  private final Range<Integer> lines;

  @Getter
  private final String text;

  @Getter
  private final Range<Integer> tokens;

  private final TokenSource source;

  public HaiScriptSourceInfo(ParserRuleContext ctx) {

    this.source = ctx.getStart().getTokenSource();

    final Token start = ctx.getStart();

    if (start.getStartIndex() >= ctx.getStop().getStopIndex()) {

      this.charIndex = Range.singleton(start.getStartIndex());
      this.lines = Range.singleton(start.getLine());

    } else {

      this.charIndex = Range.closed(ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex());
      this.lines = Range.closed(ctx.getStart().getLine(), ctx.getStop().getLine());

    }

    this.startPositionInLine = ctx.getStart().getCharPositionInLine();
    this.stopPositionInLine = ctx.getStop().getCharPositionInLine() + ctx.getStop().getText().length();

    this.text = ctx.getText();

    this.tokens = Range.closed(ctx.getStart().getTokenIndex(), ctx.getStop().getTokenIndex());

  }

  public HaiScriptSourceInfo(HaiScriptSourceInfo from, HaiScriptSourceInfo to) {

    this.source = from.source;

    this.charIndex = Range.range(
        from.charIndex.lowerEndpoint(),
        from.charIndex.lowerBoundType(),
        to.charIndex.upperEndpoint(),
        to.charIndex.upperBoundType());

    this.lines = Range.range(
        from.lines.lowerEndpoint(),
        from.lines.lowerBoundType(),
        to.lines.upperEndpoint(),
        to.lines.upperBoundType());

    this.startPositionInLine = from.startPositionInLine;
    this.stopPositionInLine = to.stopPositionInLine;

    this.text = from.getText() + to.getText();

    this.tokens = Range.range(
        from.tokens.lowerEndpoint(),
        from.tokens.lowerBoundType(),
        to.tokens.upperEndpoint(),
        to.tokens.upperBoundType());

  }

  public HaiScriptSourceInfo(Token token) {

    this.charIndex = Range.closed(token.getStartIndex(), token.getStopIndex());
    this.lines = Range.closed(token.getLine(), token.getLine());

    this.startPositionInLine = token.getCharPositionInLine();
    this.stopPositionInLine = token.getCharPositionInLine() + token.getText().length();

    this.text = token.getText();

    this.source = token.getTokenSource();

    this.tokens = Range.singleton(token.getTokenIndex());

  }

  public HaiScriptSourceInfo(HaiScriptToken token) {

    this.charIndex = Range.closed(token.getStartIndex(), token.getStopIndex());
    this.lines = Range.singleton(token.getLine());

    this.startPositionInLine = token.getPosition();
    this.stopPositionInLine = token.getPosition() + token.getText().length();

    this.text = token.getText();

    this.source = token.getSource();

    this.tokens = null;

  }

  public static HaiScriptSourceInfo from(ParserRuleContext ctx) {
    return new HaiScriptSourceInfo(ctx);
  }

  @Override
  public String toString() {

    final StringBuilder sb = new StringBuilder();

    if (this.getSourceName() != null) {
      sb.append(this.getSourceName());
    }

    sb.append("(");
    sb.append(this.lines.lowerEndpoint()).append(":").append(this.startPositionInLine + 1);
    if (this.lines.upperEndpoint() != this.lines.lowerEndpoint()) {
      sb.append(" -> ");
      sb.append(this.lines.upperEndpoint());
      sb.append(":");
      sb.append(this.stopPositionInLine);
    } else if (this.stopPositionInLine != this.startPositionInLine) {
      sb.append(",");
      sb.append(this.stopPositionInLine);
    }
    sb.append(")");

    return sb.toString();

  }

  public String getSourceName() {
    if (this.source != null) {
      return this.source.getSourceName();
    }
    return null;
  }

}
