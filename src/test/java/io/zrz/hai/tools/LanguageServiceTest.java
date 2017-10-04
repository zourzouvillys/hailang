package io.zrz.hai.tools;

import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.junit.Test;

public class LanguageServiceTest {

  @Test
  public void test() {

    final LanguageService lang = new LanguageService();

    IntStream.range(0, 1000).forEach(seq -> lang.parse(Paths.get("/Users/theo/prides/hai/models/second/Todo.hai")));

  }

}
