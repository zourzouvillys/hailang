package io.zrz.hai.symbolic.type;

import java.util.Collection;

import io.zrz.hai.symbolic.HState;

public interface HStructType extends HCompositeType, HDeclType {

  Collection<? extends HState> getFields();

  @Override
  default HDeclKind getDeclKind() {
    return HDeclKind.STRUCT;
  }

}
