package io.zrz.hai.lang;

import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;

/**
 * A tuple contains an ordered list of typed fields, optionally with parameter
 * and argument names.
 *
 * the primary use for tuples is arguments. a function declared a tuple as its
 * formal parameters, and a tuple is passed in by the caller.
 *
 */

public class TupleTypeDecl implements TypeRef {

  private final ImmutableList<StructField> elements;

  public TupleTypeDecl() {
    this.elements = ImmutableList.of();
  }

  public TupleTypeDecl(Collection<StructField> types) {
    this.elements = ImmutableList.copyOf(types);
  }

  public List<? extends StructField> getElements() {
    return this.elements;
  }

  public TupleTypeDecl withReplacedTypes(UnaryOperator<TypeRef> replacer) {

    final ImmutableList<StructField> fields = this.elements.stream()
        .map(field -> field.withType(replacer))
        .collect(ImmutableList.toImmutableList());

    return new TupleTypeDecl(fields);

  }

  public TupleTypeDecl withReplacedFields(UnaryOperator<StructField> replacer) {

    final ImmutableList<StructField> fields = this.elements.stream()
        .map(field -> replacer.apply(field))
        .collect(ImmutableList.toImmutableList());

    return new TupleTypeDecl(fields);

  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("(");
    sb.append(this.elements.stream().map(StructField::toString).collect(Collectors.joining(", ")));
    sb.append(")");
    return sb.toString();
  }

}
