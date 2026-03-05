// =============================================================================
// Ejercicio 2 - Gramatica ANTLR4
// =============================================================================
// Herramienta generadora: ANTLR4
//
// Instrucciones (ejecutar en PowerShell desde la carpeta del proyecto):
//   1. Descargar ANTLR4:
//        Invoke-WebRequest -Uri "https://www.antlr.org/download/antlr-4.13.2-complete.jar" -OutFile "antlr4.jar"
//   2. Generar el parser:
//        java -jar antlr4.jar -visitor Ejercicio2.g4
//   3. Compilar:
//        javac -cp ".;antlr4.jar" *.java
//   4. Ejecutar:
//        java -cp ".;antlr4.jar" Ejercicio2Main
// =============================================================================

grammar Ejercicio2;

// ---- REGLA DE INICIO --------------------------------------------------------

programa
    : expr EOF
    ;

// ---- EXPRESIONES (orden: primera alternativa = mayor precedencia) -----------
// ANTLR4 resuelve la ambiguedad por el orden de las alternativas.
// <assoc=right> indica asociatividad derecha para la potencia.

expr
    : <assoc=right> expr POT expr          # potencia        // mayor precedencia
    | expr op=(MUL | DIV) expr             # multDiv         // misma precedencia
    | expr op=(MAS | MENOS) expr           # sumaResta       // misma precedencia, menor
    | LPAREN expr RPAREN                   # agrupacion
    | ID                                   # identificador
    ;

// ---- TOKENS -----------------------------------------------------------------

MAS    : '+' ;
MENOS  : '-' ;
MUL    : '*' ;
DIV    : '/' ;
POT    : '^' ;
LPAREN : '(' ;
RPAREN : ')' ;

ID  : [A-Za-z_][A-Za-z0-9_]*
    | [0-9]+ ('.' [0-9]*)?
    ;

WS  : [ \t\r\n]+ -> skip ;
