parser grammar HaiParser;

import HaiCommons, HaiTypes, HaiExpressions, HaiStatements, HaiElements, HaiQLParser;

options {
	tokenVocab = HaiLexer;
}

// -------- [ Master ] ---------

compilationUnit
:
  (NAMESPACE ns=IDENT SEMICOLON)?
  (compilationUnitImport)*
	(connectionTypeDecl | typeDecl | enumDecl | typeAliasDecl)* EOF
;