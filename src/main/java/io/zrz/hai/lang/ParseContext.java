package io.zrz.hai.lang;

import java.nio.channels.ReadableByteChannel;

import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class ParseContext {
  String sourceName;
  ReadableByteChannel channel;
  long size;
  CodePointCharStream input;
  CommonTokenStream tokens;
}
