package io.zrz.hai.tools;

import java.nio.file.Path;

import com.google.common.base.Stopwatch;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * provides source file and language infrastructure services.
 *
 * there are two phases - the first is a node/tree based source syntax
 * representation, which has no semantic meaning.
 *
 * The second is a semantically processed version, which resolves to types and
 * members, with statements represented as HExpr values.
 *
 *
 */

@Slf4j
public class LanguageService {

  /**
   *
   */

  public SourceFile parse(Path path) {
    return this.parse(path, path.toString());
  }

  /**
   * parse the given source file
   */

  @SneakyThrows
  public SourceFile parse(Path path, String sourceName) {

    final Stopwatch timer = Stopwatch.createStarted();

    final ParseContext ctx = LanguageUtils.createContext(path, sourceName);

    try {

      // final CompilationUnitContext tree =
      LanguageUtils.parse(ctx);

      return null;

    } finally {

      log.debug("Unit {} processed in {}", sourceName, timer);
      ctx.channel.close();

    }

  }

}
