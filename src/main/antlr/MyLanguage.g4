grammar MyLanguage;

myLanguageFile
    : statement+
    ;

statement
    : function
    ;

function : returnType functionName openBracket args? closeBracket CONST? functionBody;

functionBody
    : BLOCK
    ;

args
    : arg
    | arg ',' args
    ;

arg
    : argAsVal
    | argAsConstVal
    | argAsRef
    | argAsConstRef
    | argAsPtr
    | argAsConstPtr
    ;


argAsVal
    : type variableName
    ;

argAsConstVal
    : CONST type variableName
    | type CONST  variableName
    ;

argAsRef
    : type REF variableName
    ;

argAsConstRef
    : CONST type REF variableName
    ;


argAsPtr
    : type PTR variableName
    ;

argAsConstPtr
    : CONST type PTR variableName
    | type CONST PTR variableName
    ;


constArg : CONST type REF? variableName;


type
    : IDENTIFIER
    ;


functionName
    : IDENTIFIER
    ;

returnType
    : IDENTIFIER PTR?
    ;


variableName : IDENTIFIER;

openBracket : OPEN_BRACKET;

closeBracket : CLOSE_BRACKET;


STRANGE_UNNEEDED_DIRECTIVE : '<%'  .*? '%>' '\r'? '\n' -> skip;

PRIVATE: 'private:' -> skip;

COMMENT : '//' ~[\r\n]* -> skip;

BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;

OPEN_BRACKET : '(';
CLOSE_BRACKET : ')';

CONST: 'const';

REF : '&';

PTR : '*';


IDENTIFIER :
    [A-Za-z0-9_:]+;


BLOCK: '{' BLOCK_BODY '}';

BLOCK_BODY
    : STRANGE_UNNEEDED_DIRECTIVE
    | ~[}]*?
    ;

WS
    :   [ \t\r\n]+ -> skip
    ;


