package io.zrz.hai.symbolic.expr;

import java.util.List;

import io.zrz.hai.symbolic.HExecutable;
import io.zrz.hai.symbolic.HParameter;
import io.zrz.hai.symbolic.type.HType;
import io.zrz.hai.symbolic.type.HTypeKind;

/**
 * An expression which defines a lambda.
 */

public interface HLambdaExpr extends HExpr, HExecutable {

  /**
   * the parameters for this lambda. Parameters may be anonymous, e.g they have no
   * name.
   */

  @Override
  List<? extends HParameter> getParameters();

  /**
   * The return type of the lambda. This can be extracted from the result of
   * {@link HLambdaExpr#getType()}, but is here for convenience.
   */

  @Override
  HType getReturnType();

  /**
   * The type of the lambda itself. This is {@link HTypeKind#LAMBDA}, and the type
   * defines the signature.
   */

  @Override
  HType getType();

  /**
   * The body of this lambda expression.
   */

  HExpr getBody();

}
