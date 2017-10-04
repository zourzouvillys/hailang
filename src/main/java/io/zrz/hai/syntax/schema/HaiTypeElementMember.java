package io.zrz.hai.syntax.schema;

public interface HaiTypeElementMember extends HaiTypeElementNode {

  HaiTypeElementModifiers getModifiers();

  HaiTypeElementMember addModifiers(HaiTypeElementModifiers modifiers);

}
