package io.zrz.hai.lang;

import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

public class IntersectionTypeRef extends BinaryTypeRef {

  public IntersectionTypeRef(Set<TypeRef> types) {
    super(TypeOperator.INTERSECT, types);
  }

  public static TypeRef create(Set<TypeRef> collect) {
    Preconditions.checkArgument(!collect.isEmpty(), "can't create union from empty set of types");
    if (collect.size() == 1) {
      return collect.iterator().next();
    }
    return new IntersectionTypeRef(collect);
  }

  public static TypeRef create(TypeRef... elements) {
    return create(Sets.newHashSet(elements));
  }

}
