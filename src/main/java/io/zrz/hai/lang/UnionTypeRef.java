package io.zrz.hai.lang;

import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import lombok.Getter;

public class UnionTypeRef implements TypeRef {

  @Getter
  private final Set<? extends TypeRef> alternatives;

  private UnionTypeRef(Set<TypeRef> collect) {
    this.alternatives = ImmutableSet.copyOf(collect);
  }

  @Override
  public String toString() {
    return Joiner.on(" | ").join(this.alternatives);
  }

  public static TypeRef create(Set<TypeRef> collect) {
    Preconditions.checkArgument(!collect.isEmpty(), "can't create union from empty set of types");
    if (collect.size() == 1) {
      return collect.iterator().next();
    }
    return new UnionTypeRef(collect);
  }

  public static TypeRef create(TypeRef... elements) {
    return create(Sets.newHashSet(elements));
  }

}
