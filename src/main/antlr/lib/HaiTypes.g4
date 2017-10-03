parser grammar HaiTypes;

import HaiCommons;

options {
	tokenVocab = HaiLexer;
}

// -------- [ Types ] ---------

// a concrete type that is either a raw type, or a parameterizerd one

concreteDeclaredType
: rawTypeName # DirectType
| rawTypeName LT typeList GT # DirectParameterizedType
;

haiTypeUse
:
  haiTypeRef
;

haiTypeRef:
	rawTypeName # RawType
	| rawTypeName LT typeList GT # ParameterizedType
	| '(' typeMemberList ')' # TupleType
	| haiTypeRef '?' # NullableType
	| haiTypeRef '!' # NonNullableType
	| literalType # LiteralValueType
	| KEYOF rawTypeName # TypeOperator
	| haiTypeRef '[' memberExpression ']' # IndexedAccess
	| '{' typeMemberList '}' # SetType
	// | structBody # StructType
	| '[' haiTypeRef  ']' # ArrayType
	| haiTypeRef typeBinaryOperator annotatedTypeRef # BinaryType
	| haiTypeRef typeBounds annotatedTypeRef+ # BoundsType
;

standaloneHaiTypeRef
:
	haiTypeRef EOF
;

typeMemberList
:
	typeMember
	(
		','? typeMember
	)*
;

typeMember
:
	(
	  (IDENT ('[' ']')? | '[' IDENT ']')
		opt = '?'? ':'
	)? haiTypeRef
;

annotatedTypeRef:
	annotations? type = haiTypeRef
;

typeBinaryOperator
:
  '|'
| '&'
| '~'
;

typeBounds
:
	EXTENDS | SUPER
;

typeList:
	annotatedTypeRef ( ',' annotatedTypeRef )*
;

memberExpression:
	literalType | IDENT
;

literalType
: numberValue
| booleanValue
| stringValue
| urlValue
;

urlValue:
  URL
;

rawTypeName:
	IDENT
;

typeVariable:
	IDENT
;


