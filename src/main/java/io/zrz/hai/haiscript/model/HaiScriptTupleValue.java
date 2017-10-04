package io.zrz.hai.haiscript.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.zrz.hai.lang.TypeRef;
import lombok.Getter;

/**
 * a tuple of values. may be positional or named - or both.
 */

public class HaiScriptTupleValue {

  private static final HaiScriptTupleValue EMPTY_INSTANCE = new HaiScriptTupleValue(true);

  @Getter
  private final boolean readOnly;

  private List<HaiScriptValue> _positional;
  private Map<String, HaiScriptValue> _named;

  public HaiScriptTupleValue(boolean readOnly) {
    this.readOnly = readOnly;
  }

  private Map<String, HaiScriptValue> named() {
    if (this._named == null) {
      this._named = new HashMap<>();
    }
    return this._named;
  }

  private List<HaiScriptValue> positional() {
    if (this._positional == null) {
      this._positional = new LinkedList<>();
    }
    return this._positional;
  }

  /**
   * set a positional value
   */

  public void set(int position, HaiScriptValue value) {
    this.positional().set(position, value);
  }

  /**
   * remove a named parameter if it exists, no-op if it doesn't
   */

  public HaiScriptValue remove(String name) {
    return this.named().remove(name);
  }

  /**
   *
   */

  public HaiScriptValue remove(int position) {
    if (this._positional == null) {
      return null;
    }
    return this._positional.remove(position);
  }

  /**
   *
   */

  public TypeRef type(int position) {
    if (this._positional == null) {
      return null;
    }
    return this._positional.get(position).getType();
  }

  /**
   *
   */

  public TypeRef type(String name) {
    return this._named.get(name).getType();
  }

  /**
   * set a named value
   *
   * @return
   */

  public HaiScriptTupleValue set(String name, HaiScriptValue value) {
    this.named().put(name, value);
    return this;
  }

  /**
   * set a named value
   *
   * @return
   */

  public HaiScriptTupleValue set(String name, String value) {
    return this.set(name, HaiScriptValue.of(value));
  }

  /**
   * set a named value
   *
   * @return
   */

  public HaiScriptTupleValue set(String name, int value) {
    return this.set(name, HaiScriptValue.of(value));
  }

  /**
   * set a named value
   *
   * @return
   */

  public HaiScriptTupleValue set(String name, boolean value) {
    return this.set(name, HaiScriptValue.of(value));
  }

  /**
   * push a positional value.
   */

  public void push(HaiScriptValue value) {
    this.positional().add(value);
  }

  /**
   * pop a positional argument.
   */

  public HaiScriptValue pop() {
    if (this._positional == null || this._positional.isEmpty()) {
      return null;
    }
    return this._positional.remove(this._positional.size() - 1);
  }

  /**
   *
   * @param position
   * @return
   */

  public Optional<HaiScriptValue> get(int position) {
    if (this._positional == null || position > this._positional.size()) {
      return Optional.empty();
    }
    return Optional.of(this.positional().get(position));
  }

  /**
   *
   * @param position
   * @param defaultValue
   * @return
   */

  public HaiScriptValue get(int position, HaiScriptValue defaultValue) {
    if (this._positional == null || position > this._positional.size()) {
      return defaultValue;
    }
    return this._positional.get(position);
  }

  /**
   *
   * @param name
   * @return
   */

  public Optional<HaiScriptValue> get(String name) {
    if (this._named == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(this._named.get(name));
  }

  /**
   *
   * @param name
   * @param defaultValue
   * @return
   */

  public HaiScriptValue get(String name, HaiScriptValue defaultValue) {
    if (this._named == null) {
      return defaultValue;
    }
    final HaiScriptValue value = this._named.get(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

  public static HaiScriptTupleValue emptyTuple() {
    return EMPTY_INSTANCE;
  }

  public static HaiScriptTupleValue of(String name, String value) {
    return new HaiScriptTupleValue(false).set(name, value);
  }

  @Override
  public String toString() {

    final StringBuilder sb = new StringBuilder();

    int count = 0;

    sb.append("(");

    if (this._positional != null) {
      for (int i = 0; i < this._positional.size(); ++i) {
        if (count++ > 0) {
          sb.append(", ");
        }
        final HaiScriptValue value = this._positional.get(i);
        sb.append(this._positional.get(i)).append("[").append(value.getType()).append("]").append(": ").append(value);
      }
    }

    if (this._named != null) {
      for (final Map.Entry<String, HaiScriptValue> e : this._named.entrySet()) {
        if (count++ > 0) {
          sb.append(", ");
        }
        final HaiScriptValue value = e.getValue();
        sb.append(e.getKey()).append("[").append(value.getType()).append("]").append(": ").append(value);
      }
    }

    sb.append(")");

    return sb.toString();

  }

}
