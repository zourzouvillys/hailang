package io.zrz.hai.type;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import io.zrz.hai.lang.StructField;

/**
 * The type is a tuple definition.
 */

public interface HTupleType extends HType {

  @Override
  default HTypeKind getTypeKind() {
    return HTypeKind.TUPLE;
  }

  /**
   * the number of entries in the tuple.
   */

  int size();

  /**
   * the type at the given index.
   */

  HType get(int num);

  /**
   * fetch the type of the given name.
   */

  HType get(String name);

  /**
   * index of the given named parameter, or -1 if there is not an entry with that
   * name.
   */

  OptionalInt index(String name);

  /**
   * the name of the parameter at the given index.
   *
   * as tuple parameters may not have a name, an empty optional is returned if one
   * is not assigned.
   *
   */

  Optional<String> name(int num);

  List<StructField> getFields();

  boolean contains(String string);

}
