package io.zrz.hai.syntax.model;

import java.util.List;

import com.google.common.collect.Lists;

import io.zrz.hai.syntax.HaiScriptStatementVisitor;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptTryBlock extends AbstractHaiScriptStatement {

  @Getter
  @Setter
  private HaiScriptStatement body;

  @Getter
  @Setter
  private List<HaiScriptCatchBlock> handlers;

  @Getter
  @Setter
  private HaiScriptStatement finallyBlock;

  public HaiScriptTryBlock() {
  }

  public HaiScriptTryBlock(HaiScriptStatement body, HaiScriptStatement finalblock) {
    this.body = body;
    this.handlers = Lists.newLinkedList();
    this.finallyBlock = finalblock;
  }

  public HaiScriptTryBlock(HaiScriptStatement body, List<HaiScriptCatchBlock> handlers, HaiScriptStatement finalblock) {
    this.body = body;
    this.handlers = handlers;
    this.finallyBlock = finalblock;
  }

  public HaiScriptTryBlock(HaiScriptStatement body, List<HaiScriptCatchBlock> handlers) {
    this.body = body;
    this.handlers = handlers;
  }

  public HaiScriptTryBlock(HaiScriptStatement body) {
    this.body = body;
    this.handlers = Lists.newLinkedList();
  }

  @Override
  public HaiScriptStatementKind getStatementKind() {
    return HaiScriptStatementKind.TRY;
  }

  @Override
  public <T, R> R apply(HaiScriptStatementVisitor<T, R> visitor, T arg) {
    return visitor.visitTryStatement(this, arg);
  }

  @Override
  public String toString() {
    return "TRY";
  }

}
