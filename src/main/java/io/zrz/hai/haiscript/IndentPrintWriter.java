package io.zrz.hai.haiscript;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

public class IndentPrintWriter extends PrintWriter {

  public static int INDENT = 2;
  private boolean addIndent;
  private int indent;
  private int col;

  public IndentPrintWriter(Writer out) {
    super(out);
    this.addIndent = false;
    this.indent = this.col = 0;
  }

  public IndentPrintWriter(OutputStream out) {
    super(out);
    this.addIndent = false;
    this.indent = this.col = 0;
  }

  @Override
  public void write(String str) {
    final int len = str.length();
    for (int i = 0; i < len; ++i) {
      this.write(str.charAt(i));
    }
  }

  @Override
  public void println() {
    this.write('\n');
  }

  @Override
  public void write(String str, int off, int len) {
    len = Math.min(str.length(), off + len);
    for (int i = off; i < len; ++i) {
      this.write(str.charAt(i));
    }
  }

  public boolean isAtNewline() {
    return this.addIndent;
  }

  @Override
  public void write(int b) {
    if (b == '\n') {
      this.addIndent = true;
      this.col = 0;
    } else if (this.addIndent) {
      this.addIndent = false;
      this.toIndent();
    } else {
      ++this.col;
    }
    super.write(b);
  }

  @Override
  public void write(char[] buf, int off, int len) {
    for (int i = 0; i < len; ++i) {
      this.write(buf[i] + off);
    }
  }

  public void setIndent(int size) {
    this.indent = size;
  }

  public void inc() {
    ++this.indent;
  }

  public void dec() {
    --this.indent;
  }

  public void toCol(int idx) {
    while (idx > this.col++) {
      super.write(' ');
    }
  }

  public int getIndent() {
    return this.indent;
  }

  public void toIndent() {
    final int end = this.indent * INDENT;
    for (int i = 0; i < end; ++i) {
      super.write(' ');
    }
    this.col = end;
  }

  public void forceLine() {

    if (!this.addIndent) {
      this.println();
    }

  }

}
