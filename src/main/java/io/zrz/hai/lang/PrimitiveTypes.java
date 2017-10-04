package io.zrz.hai.lang;

public enum PrimitiveTypes implements TypeRef {

  String {

    @Override
    public String toString() {
      return "STRING";
    }

  },

  Integer {

    @Override
    public String toString() {
      return "INTEGER";
    }

  },

  Float {

    @Override
    public String toString() {
      return "FLOAT";
    }

  },

  Boolean {

    @Override
    public String toString() {
      return "BOOLEAN";
    }

  },

  URL {

    @Override
    public String toString() {
      return "URL";
    }

  },

  // the special types

  /**
   * the value which doesn't exist. e.g, undefined.
   */

  Void {

    @Override
    public String toString() {
      return "VOID";
    }

  },

  /**
   * a type which should an instance of should never be encountered, e.g the
   * return from exit().
   */

  Never {

    @Override
    public String toString() {
      return "NEVER";
    }

  },

}
