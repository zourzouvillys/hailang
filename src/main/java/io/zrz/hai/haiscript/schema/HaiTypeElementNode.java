package io.zrz.hai.haiscript.schema;

import java.util.Collection;
import java.util.List;

import io.zrz.hai.haiscript.HaiScriptCommentNode;
import io.zrz.hai.haiscript.model.HaiScriptSourceInfo;

public interface HaiTypeElementNode {

  HaiScriptSourceInfo getSourceInfo();

  HaiTypeElementNode setSourceInfo(HaiScriptSourceInfo source);

  <R> R apply(HaiTypeElementNodeVisitor<R> visitor);

  List<? extends HaiTypeElementNode> getChildren();

  <T extends HaiTypeElementNode> Collection<? extends T> getElements(Class<T> klass);

  HaiScriptCommentNode getComments();

  void setComments(HaiScriptCommentNode node);

}
