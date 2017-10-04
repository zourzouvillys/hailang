package io.zrz.hai.symbolic;

import io.zrz.hai.symbolic.type.HConnectionType;
import io.zrz.hai.symbolic.type.HDeclType;
import io.zrz.hai.symbolic.type.HEdgeType;

/**
 * A connection is a logical type that is a field in a node, and acts as a
 * collection of nodes or edges.
 *
 * Connections are used for sorting, filtering and indexing, as well as handling
 * adding and removal of instances. Each connection has a concrete decltype
 * which declares all of the behaviour, and is nested within the owning class.
 *
 * The connection has a target type, which logically may be a node, but none the
 * less an anonymous edge is generated.
 *
 * RTTI layout treats a connection as a field, with the type set to an anonymous
 * enclosed type and the $:Connection<Source, Edge, Target> interface.
 *
 * A connection does not have any (declared) methods itself, but does have
 * indexes, filters, sorts, adders and removers. these are stored in the
 * typeinfo as methods or selections with the category name set to the type.
 *
 * methods can be overloaded, and resolved at runtime based on input.
 *
 * @author theo
 *
 */

public interface HConnection extends HMember {

  /**
   * the connection type for this member.
   */

  HConnectionType getConnectionType();

  /**
   * the edge declaration. if none was specified, then it will be anonymous.
   */

  default HEdgeType getEdgeType() {
    return getConnectionType().getEdgeType();
  }

  /**
   * The logical result type from querying this member. It may be a NODE[], or
   * EDGE[] depending on how it was defined.
   *
   *
   * If it was declared as an edge, then it will be the type of the OUT side of
   * the edge.
   *
   * If the connection was declared as a node member, then it will be the node -
   * even though internally we generate an anonymous edge for it.
   *
   */

  HDeclType getQueryResultType();

  @Override
  default HMemberKind getMemberKind() {
    return HMemberKind.CONNECTION;
  }

  String getName();

}
