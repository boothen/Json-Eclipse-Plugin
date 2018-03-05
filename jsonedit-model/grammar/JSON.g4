
/** Taken from "The Definitive ANTLR 4 Reference" by Terence Parr */

// Derived from http://json.org
grammar JSON;

json
   : value
   ;

object
   : '{' pair (',' pair)* '}'
   | '{' '}'
   ;

pair
   : STRING ':' value
   ;

array
   : '[' value (',' value)* ']'
   | '[' ']'
   ;

value
   : STRING
   | NUMBER
   | object
   | array
   | 'true'
   | 'false'
   | 'null'
   ;


STRING
   : '"' (ESC | SAFECODEPOINT)* '"'
   ;


fragment ESC
   : '\\' (["\\/bfnrt] | UNICODE | SAFECODEPOINT )
   ;


fragment UNICODE
   : 'u' HEX HEX HEX HEX
   ;


fragment HEX
   : [0-9a-fA-F]
   ;


fragment SAFECODEPOINT
   : ~ ["\\\u0000-\u001F]
   ;


NUMBER
   : '-'? INT ('.' [0-9] +)? EXP?
   ;

// keywords

TRUE  : 'true';
FALSE : 'false';
NULL  : 'null';

// more constants

BEGIN_ARRAY  : '[';
END_ARRAY    : ']';
BEGIN_OBJECT : '{';
END_OBJECT   : '}';
COMMA        : ',';
COLON        : ':';

fragment INT
   : '0' | [1-9] [0-9]*
   ;

// no leading zeros

fragment EXP
   : [Ee] [+\-]? INT
   ;

// \- since - means "range" inside [...]

WS
   : [ \t\n\r] + -> channel(HIDDEN)
   ;


UNKNOWN
   : ~ [{}[\],:"0-9\- \t\r\n]+
   ;

// collect everything else and put it into a single token to aggregate consecutive error chars.
