package io.zrz.hai.haiscript;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.zrz.hai.haiscript.model.HaiScriptNode;

public class ReducerTreeRewriter implements ParseTreeVisitor<HaiScriptNode> {

  @Override
  public HaiScriptNode visit(ParseTree tree) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public HaiScriptNode visitChildren(RuleNode node) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public HaiScriptNode visitTerminal(TerminalNode node) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public HaiScriptNode visitErrorNode(ErrorNode node) {
    // TODO Auto-generated method stub
    return null;
  }

}
