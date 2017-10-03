parser grammar HaiStatements;

options {
	tokenVocab = HaiLexer;
}

// -------- [ Statements ] ---------


statements
:
	statement
	(
		(
			SEMICOLON*
		) statement
	)*
	(
		SEMICOLON+
		| EOF
	)*
;

standaloneStatement
:
	statement SEMICOLON* EOF
;


statement
:
  blockStatement
| tryCatchFinallyStatement
| checkStatement
| emitStatement
| returnStatement
| abortStatement
| expressionStatement
| forStatement
| withStatement
| deleteStatement
;


tryCatchFinallyStatement
:
  TRY statement
	( (catchBlock+ finallyBlock?) | finallyBlock )
;

catchBlock
:
	CATCH '(' haiTypeRef symbol ')' statement
| CATCH statement
;

finallyBlock
:
  FINALLY statement
;

deleteStatement
:
	DELETE expression
;

withStatement
:
	WITH typeref tupleInitializer blockStatement
;

blockStatement
:
	'{' statements? '}'
;

conditionalStatement
:
	nonSelectableExpression statement
;

branchExpression
:
	IF conditionalStatement
	(
		ELSE IF conditionalStatement
	)*
	(
		ELSE statement
	)?
;

breakExpression
:
	BREAK
;

continueExpression
:
	CONTINUE
;

forStatement
:
	FOR '(' decl = expression IN expr = expression ')' statement
;

whenExpression
:
	WHEN expression? '{' whenClause+ '}'
;

whenMatch
:
	'!'? IN expressionList # WhenInMatch
	| '!'? IS expressionList # WhenIsMatch
	| expressionList # WhenExprListMatch
	| ELSE # WhenElseMatch
;

right_arrow
:
	first = '=' second = '>'
	{$first.index + 1 == $second.index}? // Nothing between the tokens?

;

whenClause
:
	whenMatch right_arrow statement
;

checkStatement
:
	CHECK expression
;

emitStatement
:
	EMIT emitExpression
;

emitExpression
:
	name tupleInitializer?
;

name
:
	symbol
	(
		'.' symbol
	)*
;

expressionList
:
	expression
	(
		',' expression
	)*
;

returnStatement
:
	RETURN expression?
;

abortStatement
:
	THROW expression?
;

expressionStatement
:
  branchExpression
| whenExpression
| breakExpression
| continueExpression
| declarationExpression
| expression
;
