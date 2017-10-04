package io.zrz.hai.type;

import java.util.Collection;

public interface HStructType extends HCompositeType, HDeclType {

  Collection<? extends HState> getFields();

  @Override
  default HDeclKind getDeclKind() {
    return HDeclKind.STRUCT;
  }

}
