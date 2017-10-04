package io.zrz.hai.syntax.model;

import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Joiner;

import io.zrz.hai.syntax.HaiScriptStatementVisitor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class HaiScriptEmitStatement extends AbstractHaiScriptStatement {

  @Data
  @AllArgsConstructor
  public class Entry {

    @Getter
    @Setter
    private HaiScriptExpr symbol;

    /**
     * each arg along with the name of the field to set.
     */

    @Getter
    @Setter
    private HaiScriptTupleInitializerExpr args;

    @Getter
    @Setter
    private HaiScriptNode reducer;

    @Override
    public String toString() {

      final StringBuilder sb = new StringBuilder();

      sb.append(this.symbol);

      if (this.args != null) {
        sb.append(this.args);
      }

      if (this.reducer != null) {

        if (this.reducer instanceof HaiScriptExpr) {
          sb.append(" -> ").append(this.reducer);
        } else {
          sb.append(" {\n").append(this.reducer).append("\n}\n");
        }

      }

      return sb.toString();

    }

  }

  @Getter
  @Setter
  private List<Entry> entries = new LinkedList<>();

  public HaiScriptEmitStatement() {
  }

  @Override
  public HaiScriptStatementKind getStatementKind() {
    return HaiScriptStatementKind.EMIT;
  }

  public void add(HaiScriptExpr event, HaiScriptTupleInitializerExpr args) {
    this.entries.add(new Entry(event, args, null));
  }

  public void add(HaiScriptExpr event, HaiScriptTupleInitializerExpr args, HaiScriptNode reducer) {
    this.entries.add(new Entry(event, args, reducer));
  }

  @Override
  public <T, R> R apply(HaiScriptStatementVisitor<T, R> visitor, T arg) {
    return visitor.visitEmitStatement(this, arg);
  }

  @Override
  public String toString() {
    return "emit " + Joiner.on(", ").join(this.entries);
  }

}
