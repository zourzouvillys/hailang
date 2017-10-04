package io.zrz.hai.type.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.collections.api.map.primitive.MutableObjectIntMap;

import com.google.common.collect.ImmutableMap;

import io.zrz.hai.lang.StructField;
import io.zrz.hai.lang.TypeRef;
import io.zrz.hai.type.HLoader;
import io.zrz.hai.type.HTupleType;
import io.zrz.hai.type.HType;
import lombok.Getter;

public class TupleTypeImpl implements HTupleType {

  @Getter
  private final List<StructField> fields;
  private final ImmutableMap<String, Integer> names;

  public TupleTypeImpl(List<StructField> fields) {
    this.fields = fields;
    this.names = IntStream.range(0, this.fields.size())
        .filter(idx -> fields.get(idx).getParameterName() != null)
        .mapToObj(id -> id)
        .collect(ImmutableMap.toImmutableMap(idx -> fields.get(idx).getParameterName(), idx -> idx));
  }

  public TupleTypeImpl(List<HType> types, MutableObjectIntMap<String> names) {
    throw new IllegalArgumentException("TODO");
  }

  @Override
  public HLoader getTypeLoader() {
    throw new IllegalArgumentException();
  }

  @Override
  public String toString() {
    return "TUPLE (" + this.fields.stream().map(x -> x.toString()).collect(Collectors.joining(", ")) + ")";
  }

  @Override
  public int size() {
    return this.fields.size();
  }

  @Override
  public HType get(int num) {
    return (HType) this.fields.get(num).getDataType();
  }

  @Override
  public Optional<String> name(int num) {
    return Optional.ofNullable(this.fields.get(num).getArgumentName());
  }

  @Override
  public HType get(String name) {
    return this.get(this.index(name).getAsInt());
  }

  @Override
  public OptionalInt index(String name) {
    final Integer val = this.names.get(name);
    if (val == null) {
      return OptionalInt.empty();
    }
    return OptionalInt.of(val);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof HTupleType)) {
      return false;
    }
    final TupleTypeImpl t = (TupleTypeImpl) o;
    return this.fields.equals(t.fields) && this.names.equals(t.names);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.fields, this.names);
  }

  public static TupleTypeImpl from(Map<String, HType> res) {
    return new TupleTypeImpl(res.entrySet().stream().map(e -> StructField.of(e.getKey(), (TypeRef) e.getValue(), false)).collect(Collectors.toList()));
  }

  @Override
  public boolean contains(String string) {
    return this.names.containsKey(string);
  }

}
