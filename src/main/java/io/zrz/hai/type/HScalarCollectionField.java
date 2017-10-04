package io.zrz.hai.type;

import io.zrz.hai.expr.HExpr;

/**
 * A field which is a collection of non reference values.
 */

public interface HScalarCollectionField extends HState {

  /**
   * The component type of this field.
   */

  HType getComponentType();

  /**
   * The initializer for this field. It is an expression, and must be executed in
   * the context of the declaring type.
   */

  HExpr getInitializer();

  /**
   * If the field has a getter, then this is it.
   */

  HExecutable getGetter();

  /**
   * If the field has a setter, this is it.
   */

  HExecutable getSetter();

  /**
   * If there is an adder block for this collection.
   */

  HExecutable getAdder();

  /**
   * If there is a remover block for this collection.
   */

  HExecutable getRemover();

}
