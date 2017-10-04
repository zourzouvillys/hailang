package io.zrz.hai.type;

import java.util.Collection;

import javax.annotation.Nullable;

public interface HNodeType extends HDeclType, HCompositeType, HReferenceType {

  /**
   * If this view has a super type, the type. Otherwise, null.
   */

  @Nullable
  HNodeType getSuperType();

  /**
   * retrieve a method handle by name form the general invocation scope.
   */

  @Nullable
  HMethod getDeclaredMethod(String name);

  /**
   * fetch a specific state decl.
   */

  @Nullable
  HState getDeclaredState(String name);

  /**
   * all of the fields declared in this type.
   */

  Collection<? extends HState> getDeclaredState();

  /**
   * retrieve all methods declared in this node in the general scope.
   */

  Collection<? extends HMethod> getDeclaredMethods();

  /**
   * The connections declared in this node type.
   */

  Collection<? extends HConnection> getDeclaredConnections();

  /**
   * links to other nodes or edges.
   */

  Collection<? extends HLink> getDeclaredLinks();

  /**
   * provides permission decisions. HPermission extends HMethod, so all of the
   * method properties are included. The name of the method combined with the
   * parameters defines the permissions it can grant or reject, and
   * {@link HPermission#getAction()} provides the policy type.
   */

  Collection<? extends HPermission> getDeclaredPermissions();

}
