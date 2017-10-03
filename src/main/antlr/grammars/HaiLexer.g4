lexer grammar HaiLexer;

@lexer::header {
	import java.util.Stack;
}

channels { COMMENTS, HASH_COMMENTS, DOC_COMMENTS, ANNOTATIONS, WHITESPACE }



/// --------- [ Keywords ] ----------
//
// !!! remember to update the 'keyword' rule in HaiQLParser when adding here.
//

AS         : 'as';
ADD        : 'add';
AUTO       : 'auto';
ASYNC      : 'async';
ALIAS      : 'alias';

BREAK      : 'break';

CATCH      : 'catch';
CHECK      : 'check';
CONTINUE   : 'continue';
CONST      : 'const';
CONNECTION : 'connection';

DELETE     : 'delete';
DEF        : 'def';

EDGE       : 'edge';
EMIT       : 'emit';
ELSE       :	'else';
EXTEND     : 'extend';
EXTENDS    : 'extends';
EVENT      : 'event';
EXPORT     : 'export';
ENUM       : 'enum';

FINALLY    : 'finally';
FINAL      : 'final';
FALSE      : 'false';
FOR        : 'for';
FILTER     : 'filter';

GET        : 'get';
GRANT      : 'grant';

KEYOF      : 'keyof';

IMMUTABLE  : 'immutable';
IF         : 'if';
IMPORT     : 'import';
INTERNAL   : 'internal';
INIT       : 'init';
INPUT      : 'input';
IN         : 'in';
IMPLEMENTS : 'implements';
INTERFACE  : 'interface';
IS         : 'is';
INDEX      : 'index';

LET        : 'let';

MUTATING   : 'mutating';

NATIVE     : 'native';
NAMESPACE  : 'namespace';
NEW        : 'new';
NODE       : 'node';

OBJECT     : 'object';
ON         : 'on';
OVERRIDE   : 'override';

PROJECTION : 'projection';

PUBLIC       : 'public';
PRIVATE      : 'private';
PROTECTED    : 'protected';

REDUCER      : 'reducer';
RETURN       : 'return';
REMOVE       : 'remove';
REJECT       : 'reject';

STRUCT       : 'struct';
SCALAR       : 'scalar';
STATIC       : 'static';
SUPER        : 'super';
SORT         : 'sort';
SET          : 'set';
STABLE       : 'stable';
SYNCHRONIZED : 'synchronized';

THIS       :	'this';
TRY        : 'try';
THROW      : 'throw';
TRUE       : 'true';
TYPEOF     : 'typeof';
TYPE       : 'type';
TRIGGER    : 'trigger';

UNIQUE     : 'unique';

VALUE      : 'value';
VAR        : 'var';
VIEW       : 'view';
VOLATILE   : 'volatile';

WHERE      : 'where';
WHEN       : 'when';
WITH       : 'with';
WEAK       : 'weak';

WAS        : 'was';

//

UNDERSCORE    : '_';
DOT           : '.';
COMMA         : ',';
COLON         : ':';
SEMICOLON     : ';';
PLUS          : '+';
MINUS         : '-';
STAR          : '*';
DIV           : '/';
PERCENT       : '%';
AMP           : '&';
BITWISE_OR    : '|';
CARET         : '^';
BANG          : '!';
TILDE         : '~';
ASSIGNMENT    : '=';
LT            : '<';
GT            : '>';
INTERR        : '?';


DOUBLE_COLON  : '::';
OP_COALESCING : '??';
OP_INC        : '++';
OP_DEC        : '--';
OP_AND        : '&&';
OP_OR         : '||';
OP_PTR        : '->';
OP_EQ         : '==';
OP_NE         : '!=';
OP_LE         : '<=';
OP_GE         : '>=';

SPREAD        : '...';

// ---
OPEN_BRACKET:             '[';
CLOSE_BRACKET:            ']';
OPEN_PARENS:              '(';
CLOSE_PARENS:             ')';
OPEN_BRACE:               '{';
CLOSE_BRACE:              '}';




// ---

OP_ADD_ASSIGNMENT        :	'+=';
OP_SUB_ASSIGNMENT        :	'-=';
OP_MULT_ASSIGNMENT       :	'*=';
OP_DIV_ASSIGNMENT        :	'/=';
OP_MOD_ASSIGNMENT        :	'%=';
OP_AND_ASSIGNMENT        :	'&=';
OP_OR_ASSIGNMENT         :	'|=';
OP_XOR_ASSIGNMENT        :	'^=';
OP_LEFT_SHIFT            :	'<<';
OP_LEFT_SHIFT_ASSIGNMENT :	'<<=';

////


ANNOTATION: '@' IDENT;

VARIABLE: '$' IDENT;

//// --------  [ String ] ---------

STRING
:
	(
		'\''
		(
			~'\''
		)* '\''
	)
	|
	(
		'"'
		(
			'\\"'
			| ~'"'
		)* '"'
	)
;

//// ---------- [ Tokens ] -----------

INTEGER_LITERAL:     [0-9]+ IntegerTypeSuffix?;
HEX_INTEGER_LITERAL: '0' [xX] HexDigit+ IntegerTypeSuffix?;
REAL_LITERAL:        [0-9]* '.' [0-9]+ ExponentPart? [FfDdMm]? | [0-9]+ ([FfDdMm] | ExponentPart [FfDdMm]?);

URL
:
  ('http'|'https') '://' [-0-9a-zA-Z$_.+!*'(),~%=?/{}[\]]+
;


IDENT
:
	[A-Za-z0-9_]+
;

DOC_COMMENT
:
	'/**' .*? '*/' -> channel ( DOC_COMMENTS )
;

BLOCK_COMMENT
:
	'/*' .*? '*/' -> channel ( COMMENTS )
;

LINE_COMMENT
:
	'//' ~[\r\n]* -> channel ( COMMENTS )
;

HASH_COMMENT
:
	'#' ~[\r\n]* -> channel ( HASH_COMMENTS )
;

WS
:
	[ \r\n]+ -> channel ( WHITESPACE )
;

// -----





// -----

fragment HexDigit : [0-9] | [A-F] | [a-f];


fragment IntegerTypeSuffix:         [lL]? [uU] | [uU]? [lL];
fragment ExponentPart:              [eE] ('+' | '-')? [0-9]+;
