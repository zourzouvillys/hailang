parser grammar HaiElements;

options {
	tokenVocab = HaiLexer;
}

// -------- [ Elements ] ---------


/**
 * an import declaration
 */

compilationUnitImport:
  IMPORT name (AS symbol)? SEMICOLON
;

/**
 * modifiers allowed on elements.
 */

modifiers:
  (mtv+=(
	  PUBLIC
	| PRIVATE
	| PROTECTED
	| INTERNAL
	| STATIC
	| CONST
	| NATIVE
	| AUTO
	| WEAK
	| ASYNC
	| SYNCHRONIZED
	| OVERRIDE
	| FINAL
	| TRIGGER
	| VOLATILE
	| STABLE
	| IMMUTABLE
	| MUTATING
	) | exportModifier)+
;

/**
 * export modifier allows symbols, for declaring the views that it should be exported in.
 */

exportModifier:
  EXPORT
	('(' symbol (',' symbol)* ')')?
;

/**
 * define a connection type.
 */

connectionTypeDecl:

  elementAnnotations?
	(modifiers | EXTEND)? CONNECTION IDENT typeContextDecl?
	(
		EXTENDS superType=concreteDeclaredType
	)?
	(
		IMPLEMENTS impls+=concreteDeclaredType
		(
			COMMA impls+=concreteDeclaredType
		)*
	)? (
	  (OPEN_BRACE  fieldDeclBodyMember* CLOSE_BRACE)
	| ';'
	)

;

typeAliasDecl:
  elementAnnotations?
  (modifiers | EXTEND)? ALIAS IDENT '=' IDENT ';'
;

enumDecl
:
	elementAnnotations?
	(modifiers | EXTEND)? ENUM IDENT
	(
		(OPEN_BRACE enumMember* CLOSE_BRACE)
	| ';'
	)

;

enumMember
:
  symbol ','?
;

typeDecl
:

	elementAnnotations?
	(modifiers | EXTEND)? type=(VIEW|NODE|TYPE|EDGE|EVENT|INPUT|REDUCER|PROJECTION|INTERFACE|STRUCT|SCALAR) IDENT typeContextDecl?
	(
		EXTENDS superType=concreteDeclaredType
	)?
	(
		IMPLEMENTS impls+=concreteDeclaredType
		(
			COMMA impls+=concreteDeclaredType
		)*
	)?
	(
	  (OPEN_BRACE typeDeclMember* CLOSE_BRACE)
  | ';'
	)
;

typeContextDecl
:
	OPEN_PARENS modifiers? IDENT '?'? COLON concreteDeclaredType CLOSE_PARENS
;

typeDeclMember
:
	elementAnnotations?
	(
		permissionMember
		|
		(
			modifiers?
			(
				typeDeclField
				| typeDeclMethod
				| typeDeclSetter
			)
			SEMICOLON*
		)
	)
;

permissionMember:
	((GRANT | REJECT) symbol parameterList? ( ('=' expression) | blockStatement | right_arrow statement ))
;

typeDeclSetter
:
	symbol tupleInitializer? (':' concreteDeclaredType)? (WITH concreteDeclaredType tupleInitializer?)? '->' expression
;

typeDeclField
:
	(VAR|CONST)? ident '?'?
	COLON (
	  (haiTypeRef (AS outputTypeAlias=symbol) ? ('=' expression)? fieldDeclBody?)
	| (connectionTypeDecl)
	)

;

fieldDeclBody:
  '{' fieldDeclBodyMember* '}'
;

fieldDeclBodyMember:
  modifiers? UNIQUE uniqueBlock  # FieldDeclUniqueMember
| modifiers? INDEX indexBlock    # FieldDeclIndexMember
| modifiers? SORT sortBlock      # FieldDeclSortMember
| modifiers? FILTER filterBlock  # FieldDeclFilterMember
| modifiers? GET getBlock        # FieldDeclGetMember
| modifiers? SET setBlock        # FieldDeclSetMember
| modifiers? ADD addedBlock      # FieldDeclAddMember
| modifiers? REMOVE removedBlock # FieldDeclRemoveMember
;

uniqueBlock:
  symbol? selections
;

indexBlock:
  selections
;

filterBlock:
  SEMICOLON | keyedLambdaBlock
;

sortBlock:
  SEMICOLON | keyedLambdaBlock
;

getBlock:
  SEMICOLON | blockStatement
;

setBlock:
  SEMICOLON | blockStatement
;

addedBlock:
  SEMICOLON | blockStatement
;

removedBlock:
  SEMICOLON | blockStatement
;

keyedLambdaBlock:
  '{' keyedLambda* '}'
;

keyedLambda:
  modifiers? symbol parameterList?
  (
	  ( '=' expression )
	| blockStatement
	| right_arrow statement
	)?
	','?
;

typeDeclMethod
:
	(
		DEF? ident
		| INIT
	) parameterList?
	(
		WITH concreteDeclaredType tupleInitializer?
	)?
	(
		WHERE whereClause=nonSelectableExpression
	)?
	(
		COLON haiTypeRef
	)?
	(
		(
			blockStatement
			|
			(
				right_arrow statement
			)
			|
			(
			  '=' expression
	    )
			SEMICOLON?
		)
		|
		SEMICOLON
	)
;

elementAnnotations
:
	elementAnnotation+
;

elementAnnotation
:
  OPEN_BRACKET ident CLOSE_BRACKET # KeywordAnnotation
| OPEN_BRACKET WAS ident CLOSE_BRACKET # RenameAnnotation
;