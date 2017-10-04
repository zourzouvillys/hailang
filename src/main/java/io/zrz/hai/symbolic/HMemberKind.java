package io.zrz.hai.symbolic;

/**
 * Each decltype has a set of members. The allowed types depend on the concrete
 * decltype.
 */

public enum HMemberKind {

  /**
   * the member is a "state" field which has an intrinsic scalar/struct value (or
   * array of). (NODE, EDGE, VIEW, STRUCT).
   *
   * State values can be constant, in which case they can not be changed after the
   * decltype is instantiated. They can also be static, in which case the value is
   * the same for all instances of the node.
   *
   */

  STATE,

  /**
   * the member is a connection to a set of nodes or edges. (NODE only).
   */

  CONNECTION,

  /**
   * the member is a reference to another node or edge. (NODE, EDGE, and VIEW).
   */

  LINK,

  /**
   * the member is an executable within the scope of the declaring type. it can
   * have parameters. it can perform fairly complex expressions, and may result in
   * local state, a path expression or set of nodes/edges/connections.
   *
   * methods are split into two categories: mutable (var) and immutable (const).
   *
   * a query may not modify state or call any mutations, and is therefore
   * immutable. it can generate dynamic instances of types, as long as those types
   * are not persisted by adding to a connection. A query method can emit async
   * events, which are enqueued and dispatched in the background after the query
   * has completed.
   *
   * a mutation method changes state or calls another method which changes state.
   * This includes adding methods to connections, modifying links, or adjusting
   * state.
   *
   * when an operation is dispatched that is mutable, it will keep track of all
   * nodes, links, and connections that are read. Once the operation has
   * completed, it will atomically validate these data dependencies have not
   * changes, and then save the changes to the store. If they have changed, then
   * they will be re-executed.
   *
   * HMethod provides the information related to the mutability of the member.
   *
   */

  METHOD,

  /**
   * The member is a permission check. (NODE, EDGE, VIEW) that either grants,
   * rejects, or passes.
   */

  PERMISSION,

  //
  SELECTION,

  // ambient context link
  AMBIENT,

}
