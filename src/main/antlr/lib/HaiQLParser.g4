parser grammar HaiQLParser;


options {
	tokenVocab = HaiLexer;
}



// ---------
// struct body
// ---------

fieldName
:
	IDENT
	| UNDERSCORE
	| OPEN_BRACKET fieldName COLON annotatedTypeRef CLOSE_BRACKET
	| OPEN_BRACKET typeVariable IN haiTypeRef CLOSE_BRACKET
;

structField
:
	fieldName
	(
		optional = INTERR
	)? COLON fieldType = annotatedTypeRef
	| SPREAD haiTypeRef
;

structBody
:
	'{'
	(
		fields += structField
		(
			fieldSeperator fields += structField
		)*
	)? '}'
;

fieldSeperator
:
	COMMA
;

////// --------

selectionOrSet
:
	(
		selectionSet
		| selection
	)
;

selectionSet
:
	'{' selection+ '}'
;

selection
:
	alias? field selectionSet? ','?
;


field
:
	symbol
	| value
;

/**
 * a single variable reference
 */
variable
:
	VARIABLE
;

tupleInitializer
:
	'('
	(
		tupleInitializerArgument
		(
			',' tupleInitializerArgument
		)*
	)? ')'
;

tupleInitializerArgument
:
	positional = expression # PositionalTupleArgument
	| left = expression ':' right = expression # NamedTupleArgument
;

alias
:
	symbol ':'
;

typeref
:
	name '?'?
;

