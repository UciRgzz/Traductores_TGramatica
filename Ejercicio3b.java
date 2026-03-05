// =============================================================================
// Ejercicio 3b - Analizador para Ecuaciones
// =============================================================================
// Gramatica original (con recursion izquierda):
//   S -> E '=' E
//   E -> E '+' T | T
//   T -> T '.' F | F        (punto '.' = multiplicacion)
//   F -> cte | '(' E ')' | x
//
// Gramatica equivalente sin recursion izquierda:
//   S -> E '=' E
//   E -> T ('+' T)*
//   T -> F ('.' F)*
//   F -> 'cte' | '(' E ')' | 'x'
//
// Compilar: javac Ejercicio3b.java
// Ejecutar: java Ejercicio3b
// =============================================================================

import java.util.ArrayList;
import java.util.List;

public class Ejercicio3b {

    // ---- TOKENS -------------------------------------------------------------

    enum TipoToken { CTE, X, MAS, PUNTO, IGUAL, LPAREN, RPAREN, EOF }

    static class Token {
        TipoToken tipo; String valor;
        Token(TipoToken tipo, String valor) { this.tipo = tipo; this.valor = valor; }
    }

    // ---- LEXER --------------------------------------------------------------

    static List<Token> tokenizar(String texto) {
        List<Token> tokens = new ArrayList<>();
        int i = 0;
        while (i < texto.length()) {
            char c = texto.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
            } else if (Character.isDigit(c)) {
                int j = i;
                while (j < texto.length() && Character.isDigit(texto.charAt(j))) j++;
                if (j < texto.length() && texto.charAt(j) == '.') {
                    j++;
                    while (j < texto.length() && Character.isDigit(texto.charAt(j))) j++;
                }
                tokens.add(new Token(TipoToken.CTE, texto.substring(i, j)));
                i = j;
            } else if (Character.isLetter(c)) {
                int j = i;
                while (j < texto.length() && Character.isLetter(texto.charAt(j))) j++;
                String palabra = texto.substring(i, j);
                switch (palabra) {
                    case "x":   tokens.add(new Token(TipoToken.X,   palabra)); break;
                    case "cte": tokens.add(new Token(TipoToken.CTE, palabra)); break;
                    default: throw new RuntimeException("Identificador no reconocido: '" + palabra + "'");
                }
                i = j;
            } else if (c == '+') { tokens.add(new Token(TipoToken.MAS,    "+")); i++; }
              else if (c == '.') { tokens.add(new Token(TipoToken.PUNTO,  ".")); i++; }
              else if (c == '=') { tokens.add(new Token(TipoToken.IGUAL,  "=")); i++; }
              else if (c == '(') { tokens.add(new Token(TipoToken.LPAREN, "(")); i++; }
              else if (c == ')') { tokens.add(new Token(TipoToken.RPAREN, ")")); i++; }
              else { throw new RuntimeException("Caracter inesperado: '" + c + "'"); }
        }
        tokens.add(new Token(TipoToken.EOF, "EOF"));
        return tokens;
    }

    // ---- PARSER -------------------------------------------------------------

    static List<Token> tokens;
    static int pos;

    static Token actual() { return tokens.get(pos); }

    static void consumir(TipoToken esperado) {
        if (actual().tipo != esperado)
            throw new RuntimeException(
                "Se esperaba '" + esperado + "', se encontro '" + actual().valor + "'");
        pos++;
    }

    // S -> E '=' E
    static void S() { E(); consumir(TipoToken.IGUAL); E(); }

    // E -> T ('+' T)*
    static void E() {
        T();
        while (actual().tipo == TipoToken.MAS) { consumir(TipoToken.MAS); T(); }
    }

    // T -> F ('.' F)*
    static void T() {
        F();
        while (actual().tipo == TipoToken.PUNTO) { consumir(TipoToken.PUNTO); F(); }
    }

    // F -> 'cte' | '(' E ')' | 'x'
    static void F() {
        if (actual().tipo == TipoToken.CTE)         consumir(TipoToken.CTE);
        else if (actual().tipo == TipoToken.LPAREN) { consumir(TipoToken.LPAREN); E(); consumir(TipoToken.RPAREN); }
        else if (actual().tipo == TipoToken.X)       consumir(TipoToken.X);
        else throw new RuntimeException("Se esperaba cte, x o '(', se encontro '" + actual().valor + "'");
    }

    // ---- MAIN ---------------------------------------------------------------

    static void analizar(String expresion) {
        System.out.print("Analizando: \"" + expresion + "\"  ->  ");
        try {
            tokens = tokenizar(expresion); pos = 0;
            S();
            if (actual().tipo != TipoToken.EOF)
                throw new RuntimeException("Token inesperado al final: '" + actual().valor + "'");
            System.out.println("ACEPTADA");
        } catch (Exception e) { System.out.println("RECHAZADA: " + e.getMessage()); }
    }

    public static void main(String[] args) {
        analizar("x = cte");
        analizar("x + cte = 3");
        analizar("x . x = cte");
        analizar("x + 2 . x = 3 . cte");
        analizar("(x + cte) . x = x . (x + cte)");
        analizar("2 = 2");
        // Invalidos
        analizar("x + = cte");
        analizar("x = cte =");
        analizar("= x");
    }
}
