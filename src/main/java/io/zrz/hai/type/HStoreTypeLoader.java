package io.zrz.hai.type;

public interface HStoreTypeLoader extends HLoader {

  /**
   * load a type by ID.
   */

  HDeclType loadType(int typeId);

}
