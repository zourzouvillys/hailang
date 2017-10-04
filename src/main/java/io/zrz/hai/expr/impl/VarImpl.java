package io.zrz.hai.expr.impl;

import io.zrz.hai.expr.HVar;
import io.zrz.hai.type.HType;
import lombok.Getter;

public class VarImpl implements HVar {

  @Getter
  private final String name;

  @Getter
  private final HType type;

  public VarImpl(String name, HType type) {
    this.name = name;
    this.type = type;
  }

}
