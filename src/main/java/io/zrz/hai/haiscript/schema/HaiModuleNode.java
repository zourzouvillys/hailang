package io.zrz.hai.haiscript.schema;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.zrz.hai.haiscript.HaiScriptCommentNode;
import io.zrz.hai.haiscript.model.HaiScriptSourceInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * a module consists of a bunch of compilation units, which together make a
 * functionally complete store.
 */

public class HaiModuleNode implements HaiTypeElementNode {

  @Getter
  @Setter
  private LinkedList<HaiCompilationUnit> units = new LinkedList<>();

  public void addUnit(HaiCompilationUnit unit) {
    this.units.add(unit);
  }

  @Override
  public <R> R apply(HaiTypeElementNodeVisitor<R> visitor) {
    return visitor.visitModule(this);
  }

  @Override
  public HaiScriptSourceInfo getSourceInfo() {
    return null;
  }

  @Override
  public HaiTypeElementNode setSourceInfo(HaiScriptSourceInfo source) {
    throw new IllegalStateException();
  }

  @Override
  public List<? extends HaiTypeElementNode> getChildren() {
    return this.units;
  }

  @Override
  public <T extends HaiTypeElementNode> Collection<? extends T> getElements(Class<T> klass) {
    return Stream.concat(
        this.units.stream().flatMap(child -> child.getElements(klass).stream()),
        this.units.stream().filter(elt -> klass.isInstance(elt)).map(klass::cast))
        .collect(Collectors.toList());
  }

  @Override
  public HaiScriptCommentNode getComments() {
    return null;
  }

  @Override
  public void setComments(HaiScriptCommentNode node) {
    // TODO Auto-generated method stub

  }

}
