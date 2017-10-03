parser grammar HaiCommons;

options {
	tokenVocab = HaiLexer;
}

// -------- [ Annotations ] ---------

annotations
:
	annotation+
;

annotation
:
	ANNOTATION
;

// -------- [ Commons ] ---------

value
: stringValue
| booleanValue
| numberValue
;

stringValue:
  STRING
;

booleanValue
: TRUE
| FALSE
;

numberValue
: INTEGER_LITERAL
| HEX_INTEGER_LITERAL
| REAL_LITERAL
;

// -------- [ Symbol ] ---------

symbol
:
	IDENT
	| THIS
	| unreserved_keyword
;


ident
:
	IDENT
	| unreserved_keyword
;

// -------- [ Keywords ] ---------

unreserved_keyword:
  AS
| ADD
| AUTO
| ASYNC
| ALIAS
| CATCH
| CHECK
| CONNECTION
| DEF
| EDGE
| EXTENDS
| EXTEND
| EVENT
| EXPORT
| FILTER
| FINALLY
| FINAL
| GET
| GRANT
| KEYOF
| INPUT
| IMPORT
| IMMUTABLE
| IN
| INIT
| INTERNAL
| INTERFACE
| IS
| INDEX
| LET
| NATIVE
| NAMESPACE
| NODE
| OBJECT
| ON
| OVERRIDE
| PROJECTION
| REMOVE
| REDUCER
| REJECT
| SET
| STABLE
| SYNCHRONIZED
| SORT
| SUPER
| STRUCT
| TYPEOF
| TYPE
| TRIGGER
| UNIQUE
| VAR
| VALUE
| VIEW
| VOLATILE
| WAS
| WHERE
| WITH
| WEAK
;

reserved_keyword:
  IF
| ENUM
| FALSE
| FOR
| NEW
| PUBLIC
| PRIVATE
| MUTATING
| PROTECTED
| RETURN
| SCALAR
| TRY
| THIS
| THROW
| TRUE
| CONST
| BREAK
| CONTINUE
| DELETE
| EMIT
| ELSE
| WHEN
;

keyword:
  unreserved_keyword | reserved_keyword
;