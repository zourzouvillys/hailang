package io.zrz.hai.lang;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * a struct contains a set of named fields
 */

public class StructTypeDecl implements TypeRef {

  private final List<StructField> fields;

  public StructTypeDecl(Collection<StructField> fields) {
    this.fields = ImmutableList.copyOf(fields);
  }

  public List<StructField> getFields() {
    return this.fields;
  }

}
