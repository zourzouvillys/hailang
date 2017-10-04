package io.zrz.hai.haiscript;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import io.zrz.hai.haiscript.model.HaiScriptNode;
import io.zrz.hai.haiscript.model.HaiScriptSourceInfo;
import lombok.Getter;
import lombok.experimental.Value;

public class HaiContextRecordingScriptVisitor<T, R> extends DefaultHaiScriptVisitor<T, R> {

  @Getter
  private final HaiScriptNodePath context = new HaiScriptNodePath();

  int childOffset = -1;

  @Override
  public R visitEnter(HaiScriptNode node) {
    this.context.stack.push(new Entry(this.context.connection, this.childOffset, node));
    this.childOffset = -1;
    this.context.currentOffset = -1;
    this.context.connection = null;
    return super.visitEnter(node);
  }

  @Override
  public R visitLeave(HaiScriptNode node, T arg, R result) {
    Preconditions.checkState(this.context.stack.peek().getNode() == node,
        "current: %s, leaving: %s",
        this.context.stack.peek().getNode().getClass(),
        node.getClass());
    final Entry prev = this.context.stack.pop();
    this.childOffset = prev.index;
    this.context.currentOffset = this.childOffset;
    this.context.connection = prev.connection;
    return super.visitLeave(node, arg, result);
  }

  @Override
  protected List<R> visitChildren(String string, Collection<? extends HaiScriptNode> nodes, T arg) {
    final int oldOffset = this.childOffset;
    this.childOffset = 0;
    this.context.currentOffset = 0;
    try {
      return super.visitChildren(string, nodes, arg);
    } finally {
      this.childOffset = oldOffset;
      this.context.currentOffset = oldOffset;
    }
  }

  @Override
  protected R visitChild(String childName, HaiScriptNode node, T arg) {
    this.context.connection = childName;
    int oldOffset = this.childOffset;
    try {
      final R res = super.visitChild(childName, node, arg);
      this.context.result = res;
      return res;
    } finally {
      if (oldOffset >= 0) {
        oldOffset++;
      }
      this.childOffset = oldOffset;
      this.context.currentOffset = this.childOffset;
      this.context.connection = null;
      this.context.current = null;
    }
  }

  @Value
  public static class Entry {
    String connection;
    int index;
    HaiScriptNode node;
  }

  @lombok.Value
  public static class HaiScriptSnapshot {

    private List<Entry> entries;
    private HaiScriptSourceInfo source;
    private String text;

    // private HaiScriptNode current;

    public HaiScriptNode getCurrent() {
      if (this.entries.isEmpty()) {
        return null;
      }
      return this.entries.get(this.entries.size() - 1).getNode();
    }

    @Override
    public String toString() {
      return this.text;
    }

  }

  public static class HaiScriptNodePath {

    public Object result;

    @Getter
    public HaiScriptNode current;
    int currentOffset = 0;

    public String connection;

    private final HaiScriptVisitor<?, String> summarizer = new SummaryExtractor();

    @Getter
    private final Stack<Entry> stack = new Stack<>();

    public HaiScriptSnapshot snapshot() {
      return new HaiScriptSnapshot(ImmutableList.copyOf(this.stack), this.getSourceInfo(), this.toString());
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder();
      int count = 0;
      for (final Entry node : this.stack) {
        if (node.getConnection() != null) {
          sb.append("-(").append(node.getConnection());

          if (node.getIndex() != -1) {
            sb.append(":").append(node.getIndex());
          }

          sb.append(")->");
        } else if (count > 0) {
          sb.append("->");
        }
        count++;
        sb.append(node.getNode().apply(this.summarizer));
      }

      if (this.connection != null) {
        sb.append("-(").append(this.connection);
        if (this.currentOffset != -1) {
          sb.append(":").append(this.currentOffset);
        }
        sb.append(")");
      } else {
        // sb.append("->");
      }

      if (this.result != null) {
        sb.append(" = ").append(this.result);
      }

      // if (this.current != null) {
      // sb.append(this.current.apply(this.summarizer));
      // }

      return sb.toString();
    }

    public HaiScriptSourceInfo getSourceInfo() {
      if (this.current != null && this.current.getSource() != null) {
        return this.current.getSource();
      }
      for (final Entry node : Lists.reverse(this.stack)) {
        if (node.getNode().getSource() != null) {
          return node.getNode().getSource();
        }
      }
      return null;
    }

  }

}
