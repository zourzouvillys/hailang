package io.zrz.hai.syntax.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HaiScriptNodeProperties {

  public static interface Key<T> {

    T cast(Object value);

    String getName();

    String toString(T cast);

  }

  private Map<Key<?>, Object> properties;

  public <T> Optional<T> get(Key<T> key) {
    if (this.properties == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(this.properties.get(key)).map(key::cast);
  }

  public <T> Optional<T> set(Key<T> key, T value) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    return Optional.ofNullable(this.properties.put(key, value)).map(key::cast);
  }

  public <T> Optional<T> remove(Key<T> key) {
    return Optional.ofNullable(this.properties.remove(key)).map(key::cast);
  }

  @Override
  public String toString() {
    if (this.properties == null) {
      return "{}";
    }
    return this.properties.entrySet().stream()
        .map(e -> HaiScriptNodeProperties.toString(e.getKey(), e.getValue()))
        .collect(Collectors.joining(", "));
  }

  public static String toString(Map.Entry<Key<?>, Object> e) {
    return toString(e.getKey(), e.getValue());
  }

  public static <T> String toString(Key<T> key, Object value) {
    final T val = key.cast(value);
    return key.getName() + " = " + key.toString(val);
  }

  public static <T> Key<T> createKey(String key, Class<T> klass, Function<T, String> printer) {
    return new Key<T>() {

      @Override
      public T cast(Object value) {
        return klass.cast(value);
      }

      @Override
      public String getName() {
        return key;
      }

      @Override
      public String toString(T value) {
        if (value == null) {
          return "null";
        }
        return printer.apply(value);
      }

    };
  }

  public Stream<Entry<Key<?>, Object>> stream() {
    if (this.properties == null) {
      return Stream.empty();
    }
    return this.properties.entrySet().stream();
  }

  public boolean isEmpty() {
    if (this.properties == null) {
      return true;
    }
    return this.properties.isEmpty();
  }

}
