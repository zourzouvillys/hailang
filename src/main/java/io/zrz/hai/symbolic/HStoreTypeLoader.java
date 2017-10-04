package io.zrz.hai.symbolic;

import io.zrz.hai.symbolic.type.HDeclType;

public interface HStoreTypeLoader extends HLoader {

  /**
   * load a type by ID.
   */

  HDeclType loadType(int typeId);

}
