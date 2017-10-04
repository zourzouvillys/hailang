package io.zrz.hai.lang;

/**
 * referencing an "element" is different that the type of that element. For
 * example, an expression of user.username references the field "username",
 * which is an element. The type of that field is (in this example) string.
 *
 * To avoid mixing up types vs refernces to something, HaiElement is provided to
 * be the type representing such a reference.
 *
 */

public interface HaiElement {

  TypeRef getType();

}
