package io.zrz.hai.type;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;

import io.zrz.hai.lang.StructField;
import io.zrz.hai.type.impl.TupleTypeImpl;

/**
 * some utility helpers.
 */

public class HTypeUtils {

  public static Optional<? extends HMember> getMember(HDeclType userType, String symbol) {
    return getMembers(Objects.requireNonNull(userType)).filter(f -> f.getName().equals(symbol)).findFirst();
  }

  public static Stream<? extends HMember> getMembers(HDeclType type) {
    Objects.requireNonNull(type);
    if (type.getSuperType() != null) {
      return Streams.concat(type.getDeclaredMembers().stream(), getMembers(type.getSuperType()));
    }
    return type.getDeclaredMembers().stream();
  }

  /**
   * summary of a type name
   */

  public static String summary(HType type) {
    if (type.getTypeKind() == HTypeKind.DECL) {
      final HDeclType decl = (HDeclType) type;
      switch (decl.getDeclKind()) {
        case CONNECTION:
        case ENUM:
        case INTERFACE:
        case NODE:
        case EDGE:
        case VIEW:
        case EVENT:
        case TYPE:
          return decl.getQualifiedName();
        case STRUCT:
          if (decl.getQualifiedName() == null) {
            return "ANON STRUCT";
          }
          return decl.getQualifiedName();
      }
    }
    return type.getTypeKind().toString();
  }

  /**
   * returns a set of types which are the supertype for other types.
   */

  public static Set<String> getExtendedDeclTypes(HModule module) {
    return module.getDeclaredTypes().stream()
        .filter(t -> t.getSuperType() == null)
        .map(t -> t.getQualifiedName())
        .collect(Collectors.toSet());
  }

  public static Collection<HDeclType> superTypes(HDeclType type) {

    final Set<HDeclType> types = new HashSet<>();

    while (type != null) {

      if (type.getSuperType() == null || types.contains(type.getSuperType())) {
        return types;
      }

      types.add(type.getSuperType());

      type = type.getSuperType();

    }

    return types;

  }

  public static HType componentType(HType arg) {
    return ((HArrayType) arg).getComponentType();
  }

  public static String toString(HType type) {

    switch (type.getTypeKind()) {
      case ARRAY:
        return "[" + toString(((HArrayType) type).getComponentType()) + "]";
      case BOOLEAN:
        return "BOOLEAN";
      case DECL:
        switch (((HDeclType) type).getDeclKind()) {
          case STRUCT:
            return "STRUCT";
          case CONNECTION:
            return "CONNECTION";
          case EDGE:
            return "EDGE";
          case ENUM:
            return "ENUM";
          case INTERFACE:
            return "INTERFACE";
          case NODE:
            return "NODE";
          case VIEW:
            return "VIEW";
          case EVENT:
            return "EVENT";
          case TYPE:
            return "TYPE";
        }
        break;
      case DOUBLE:
        return "DOUBLE";
      case INT:
        return "INT";
      case STRING:
        return "STRING";
      case NEVER:
        return "NEVER";
      case VOID:
        return "VOID";

      case TUPLE:
        return "TUPLE";
      case INTERSECTION:
        return "INTERSECTION";
      case UNION:
        return "UNION";
      case WILDCARD:
        return "WILDCARD";
      case LAMBDA:
        return "LAMBDA";
    }

    throw new IllegalArgumentException(type.toString());

  }

  public static HTupleType emptyTuple() {
    return EMPTY_TUPLE;
  }

  private static HTupleType EMPTY_TUPLE = new HTupleType() {

    @Override
    public HLoader getTypeLoader() {
      return null;
    }

    @Override
    public int size() {
      return 0;
    }

    @Override
    public Optional<String> name(int num) {
      return Optional.empty();
    }

    @Override
    public OptionalInt index(String name) {
      return OptionalInt.empty();
    }

    @Override
    public HType get(String name) {
      return null;
    }

    @Override
    public HType get(int num) {
      return null;
    }

    @Override
    public List<StructField> getFields() {
      return ImmutableList.of();
    }

    @Override
    public boolean contains(String string) {
      return false;
    }

  };

  /**
   * given a type, returns a type which represents an array of it.
   */

  public static HArrayType makeArrayType(HType type) {
    return type.getTypeLoader().getArrayFor(type);
  }

  public static boolean isKind(HType type, HDeclKind kind) {
    if (type.getTypeKind() == HTypeKind.DECL) {
      return ((HDeclType) type).getDeclKind() == kind;
    }
    return false;
  }

  public static HDeclKind declKind(HType type) {
    return asDeclType(type).getDeclKind();
  }

  private static HDeclType asDeclType(HType type) {
    if (type.getTypeKind() != HTypeKind.DECL) {
      throw new IllegalArgumentException(String.format("%s not decltype", type.getTypeKind()));
    }
    return (HDeclType) type;
  }

  public static HType type(HMember member) {

    switch (member.getMemberKind()) {
      case AMBIENT:
      case LINK:
        return ((HLink) member).getType();
      case CONNECTION:
        return ((HConnection) member).getConnectionType();
      case METHOD:
        return ((HMethod) member).getReturnType();
      case STATE:
        return ((HState) member).getType();
      default:
      case PERMISSION:
      case SELECTION:
        break;
    }

    throw new IllegalArgumentException(member.getMemberKind().toString());
  }

  public static HTupleType createTuple(Map<String, HType> res) {
    return TupleTypeImpl.from(res);
  }

}
