package io.zrz.hai.haiscript.schema;

public interface HaiTypeElementMember extends HaiTypeElementNode {

  HaiTypeElementModifiers getModifiers();

  HaiTypeElementMember addModifiers(HaiTypeElementModifiers modifiers);

}
