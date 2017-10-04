package io.zrz.hai.type;

import java.util.Collection;

/**
 * a connection is a field in a node which references other nodes or edges and
 * defines the constraints and access methods for them. it is not really a type
 * in itself, but instead a field. however, it can be declared separately and be
 * given a type name. the same connection "type" can be used in multiple node
 * fields.
 */

public interface HConnectionType extends HDeclType, HCompositeType {

  /**
   * if this connection type is anonymous, meaning it has no assigned name.
   */

  boolean isAnonymous();

  /**
   *
   */

  HNodeType getNodeType();

  /**
   * The type of edges stored in this connection. This may be a synthesised edge.
   */

  HEdgeType getEdgeType();

  /**
   * declared indexes for this connection.
   */

  Collection<? extends HMember> getIndexes();

  /**
   * the unique rules for this connection.
   */

  Collection<? extends HMember> getUniqueConstraints();

  /**
   * the available filters for this connection.
   */

  Collection<? extends HMethod> getFilters();

  /**
   * the available sorters for this field.
   */

  Collection<? extends HMethod> getSorters();

  /**
   * handler for a connection entry to be added.
   */

  HMethod getAdder();

  /**
   * handler for removal of an entity from this connection.
   */

  HMethod getRemover();

  /**
   *
   */

  @Override
  default HDeclKind getDeclKind() {
    return HDeclKind.CONNECTION;
  }

}
