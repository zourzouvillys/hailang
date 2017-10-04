package io.zrz.hai.syntax;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import io.zrz.hai.lang.antlr4.HaiLexer;
import io.zrz.hai.syntax.schema.HaiTypeElementNode;
import lombok.Getter;

public class HaiScriptCommentNode {

  public static class DocComment {

    @Getter
    private final Token token;

    @Getter
    private final String comment;

    public DocComment(Token tok, String comment) {
      this.token = tok;
      this.comment = comment;
    }

  }

  @Getter
  private final List<DocComment> comments = new LinkedList<>();

  public static void apply(HaiTypeElementNode type, List<Token> tokens) {

    HaiScriptCommentNode comments = null;

    for (final Token tok : tokens) {

      switch (tok.getChannel()) {
        case HaiLexer.COMMENTS:
          break;
        case HaiLexer.DOC_COMMENTS:
          if (comments == null) {
            comments = new HaiScriptCommentNode();
          }
          comments.comments.add(new DocComment(tok, parseDocComment(tok)));
          break;
        case HaiLexer.HASH_COMMENTS:
          // comments.comments.add((new HashComment(tok, parseHashComment(tok))));
          // addHash(parseHashComment(tok));
          break;
      }

    }

    if (comments != null) {
      type.setComments(comments);
    }

  }

  private static String parseDocComment(Token tok) {
    final String text = normalizeDocComment(tok.getText().trim());
    return Splitter.onPattern("\r?\n").trimResults(CharMatcher.anyOf(" \t*")).splitToList(text).stream().collect(Collectors.joining("\n")).trim();
  }

  private static String normalizeDocComment(String text) {
    return StringUtils.trimToEmpty(StringUtils.stripEnd(StringUtils.stripStart(text, "/*"), "*/"));
  }

  public static void apply(CommonTokenStream tokens, ParserRuleContext ctx, HaiTypeElementNode type) {

    List<Token> hidden = tokens.getHiddenTokensToLeft(ctx.start.getTokenIndex(), -1);

    if (hidden != null) {

      hidden = hidden.stream()
          .filter(t -> t.getChannel() != HaiLexer.WHITESPACE)
          .collect(Collectors.toList());

      HaiScriptCommentNode.apply(type, hidden);

    }

  }

}
