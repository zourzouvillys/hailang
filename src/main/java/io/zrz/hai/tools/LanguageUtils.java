package io.zrz.hai.tools;

import java.nio.channels.ReadableByteChannel;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import io.zrz.hai.lang.antlr4.HaiLexer;
import io.zrz.hai.lang.antlr4.HaiParser;
import io.zrz.hai.lang.antlr4.HaiParser.CompilationUnitContext;
import lombok.SneakyThrows;

class LanguageUtils {

  @SneakyThrows
  static CompilationUnitContext parse(ParseContext ctx) {

    final HaiParser parser = new HaiParser(ctx.tokens);

    // parser.removeErrorListeners();
    // parser.addErrorListener(ThrowingErrorListener.INSTANCE);

    parser.setBuildParseTree(true);
    parser.setProfile(true);

    return parser.compilationUnit();

  }

  /**
  *
  */

  private static final int DEFAULT_BUFFER_SIZE = 8192;

  /**
  *
  */

  @SneakyThrows
  static ParseContext createContext(Path path, String sourceName) {

    final long size = Files.size(path);

    final ReadableByteChannel channel = Files.newByteChannel(path);

    try {

      final CodePointCharStream input = CharStreams.fromChannel(
          channel,
          StandardCharsets.UTF_8,
          DEFAULT_BUFFER_SIZE,
          CodingErrorAction.REPLACE,
          sourceName,
          size);

      final HaiLexer lexer = new HaiLexer(input);
      final CommonTokenStream tokens = new CommonTokenStream(lexer);

      return new ParseContext(sourceName, channel, size, input, tokens);

    } catch (final Throwable t) {

      channel.close();
      throw t;

    }

  }

}
