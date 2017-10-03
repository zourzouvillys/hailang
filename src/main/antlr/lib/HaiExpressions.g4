parser grammar HaiExpressions;

import HaiCommons, HaiTypes, HaiQLParser, HaiStatements;

options {
	tokenVocab = HaiLexer;
}



/// -------------- [ Expressions ] --------------



unaryOperator
:
	'!'
	| '~'
	| '&'
	| '*'
	| '-'
	| '+'
	| '--'
	| '++'
	| '...'
;



typeOfExpression
:
	TYPEOF '('

	  ( expression | haiTypeRef )

	')'

;

recordExpression
:
	'{' fieldValues? '}'
;

arrayExpression
:
	'[' expressionList? ']'
;

constructorExpression
:
  NEW typeref tupleInitializer? memberInitializers?
| NEW memberInitializers
;

memberInitializers
:
  '{'
	  ( memberInitializer ( ',' memberInitializer)* )? ','?
	'}'
;

memberInitializer
:
  symbol '=' expression
;

bracketedExpression
:
	'(' expression ')'
;

///

selections
:
	'{' ( selectionSpread | selectionItem )+ '}'
;

parameterList
:
	'('
	(
		(parameterSpread | parameterItem)
		(
			',' (parameterSpread | parameterItem)
		)*
	)? ')'
;

parameterItem
:
	symbol parameterFilter? '?'?
	(
		':' (parameterSpread | haiTypeRef)
	)?
	(
		 '=' expression
	)?
;

parameterFilter
:
  IN expression
;

parameterSpread:
  SPREAD (AS IDENT)?
;

lambdaExpression
:
	(
		symbol
		| parameterList
	)
	(
		right_arrow statement
	)
;

expression
:
  nonSelectableExpression selections?
;

nonSelectableExpression:
  assignment | nonAssignmentExpression
;

nonAssignmentExpression
:
	lambdaExpression | conditionalExpression
;

assignment
:
	unaryExpression assignment_operator expression
;

assignment_operator
:
	ASSIGNMENT
	| OP_ADD_ASSIGNMENT
	| OP_SUB_ASSIGNMENT
	| OP_MULT_ASSIGNMENT
	| OP_DIV_ASSIGNMENT
	| OP_MOD_ASSIGNMENT
	| OP_AND_ASSIGNMENT
	| OP_OR_ASSIGNMENT
	| OP_XOR_ASSIGNMENT
	| OP_LEFT_SHIFT_ASSIGNMENT
	| OP_LEFT_SHIFT
;

conditionalExpression
:
	null_coalescing_expression
	(
		'?' t = expression? ':' e = expression
	)?
;

null_coalescing_expression
:
	conditional_or_expression
	(
		'??' null_coalescing_expression
	)?
;

conditional_or_expression
:
	conditional_and_expression
	(
		'||' conditional_and_expression
	)*
;

conditional_and_expression
:
	inclusive_or_expression
	(
		'&&' inclusive_or_expression
	)*
;

inclusive_or_expression
:
	exclusive_or_expression
	(
		'|' exclusive_or_expression
	)*
;

exclusive_or_expression
:
	and_expression
	(
		'^' and_expression
	)*
;

and_expression
:
	equality_expression
	(
		'&' equality_expression
	)*
;

equality_expression
:
	relationalExpression
	(
		(
			'=='
			| '!='
		) relationalExpression
	)*
;

relationalExpression
:
	left = shift_expression
	(
		op +=
		(
			'<'
			| '>'
			| '<='
			| '>='
		) right += shift_expression
		| op += IN shift_expression
		| op += IS typeref
		| op += AS typeref
	)*
;

shift_expression
:
	left = additive_expression
	(
		op = shift_operator right += additive_expression
	)*
;

shift_operator
:
	OP_LEFT_SHIFT
	| right_shift
;

right_shift
:
	first = '>' second = '>'
	{$first.index + 1 == $second.index}? // Nothing between the tokens?

;

additive_expression
:
	left = multiplicativeExpression
	(
		op +=
		(
			PLUS
			| MINUS
		) right += multiplicativeExpression
	)*
;

multiplicativeExpression
:
	left = unaryExpression
	(
		(
			op +=
			(
				'*'
				| '/'
				| '%'
			)
		) right += unaryExpression
	)*
;

unaryExpression
:
	primaryExpression
	| unaryOperator expression
;

primaryExpression
:
	primaryExpressionStart bracket_expression? primaryExpressionSuffix*
;

primaryExpressionSuffix
:
	(
		ma = memberAccess
		| ie = tupleInitializer
		| op =
		(
			'++'
			| '--'
		)
		| '->' ptr = symbol
	) bracket_expression*
;

bracket_expression
:
	'?'? '[' indexer_argument
	(
		',' indexer_argument
	)* ']'
;

indexer_argument
:
	(
		symbol ':'
	)? expression
;

memberAccess
:
	'?'? '.' symbol
;

primaryExpressionStart
:
	THIS
	| SUPER
	| typeOfExpression
	| value
	| name
	| variable
	| bracketedExpression
	| constructorExpression
	| tupleInitializer
	| recordExpression
	| arrayExpression
;

// ----------------

selectionSpread:
  '...' ON typeref
;

selectionItem
:
	alias? expression ','?
;

fieldValues
:
	fieldValue
	(
		(
			','
		) fieldValue
	)*
;

fieldValue
:
	(
		(
			keyExpr = expression
		) ':'
		(
			valueExpr = expression
		)
	) # KeyValueExpression
	| expression # FieldValueExpression
;

declarationExpression
:
	variability =
	(
		CONST
		| VAR
	) declName = symbol '?'?
	(
		':' declType = haiTypeRef
	)?
	(
		'=' expression
	)?
;
