package io.zrz.hai.type;

import lombok.Getter;

public class HTypeNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  @Getter
  private final HModule module;
  private final String qname;

  public HTypeNotFoundException(HModule module, String qname) {
    this.module = module;
    this.qname = qname;
  }

  public String getQualifiedName() {
    return this.qname;
  }

  @Override
  public String getMessage() {
    return String.format("can't find type '%s'", this.qname);
  }

}
