// =============================================================================
// Ejercicio 3a - Analizador para Expresiones Logicas (Booleanas)
// =============================================================================
// Gramatica original (con recursion izquierda):
//   T -> T v F | F          (v = OR)
//   F -> F ^ S | S          (^ = AND)
//   S -> -P | P             (- = NOT)
//   P -> [T] | true | false
//
// Gramatica equivalente sin recursion izquierda (para descenso recursivo):
//   T -> F ('v' F)*
//   F -> S ('^' S)*
//   S -> '-' P | P
//   P -> '[' T ']' | 'true' | 'false'
//
// Precedencia: NOT > AND > OR
//
// Compilar: javac Ejercicio3a.java
// Ejecutar: java Ejercicio3a
// =============================================================================

import java.util.ArrayList;
import java.util.List;

public class Ejercicio3a {

    // ---- TOKENS -------------------------------------------------------------

    enum TipoToken { TRUE, FALSE, OR, AND, NOT, LBRAC, RBRAC, EOF }

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
            } else if (Character.isLetter(c)) {
                int j = i;
                while (j < texto.length() && Character.isLetter(texto.charAt(j))) j++;
                String palabra = texto.substring(i, j);
                switch (palabra) {
                    case "true":  tokens.add(new Token(TipoToken.TRUE,  palabra)); break;
                    case "false": tokens.add(new Token(TipoToken.FALSE, palabra)); break;
                    case "v":     tokens.add(new Token(TipoToken.OR,    palabra)); break;
                    default: throw new RuntimeException("Palabra desconocida: '" + palabra + "'");
                }
                i = j;
            } else if (c == '^') { tokens.add(new Token(TipoToken.AND,   "^")); i++; }
              else if (c == '-') { tokens.add(new Token(TipoToken.NOT,   "-")); i++; }
              else if (c == '[') { tokens.add(new Token(TipoToken.LBRAC, "[")); i++; }
              else if (c == ']') { tokens.add(new Token(TipoToken.RBRAC, "]")); i++; }
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

    // T -> F ('v' F)*    [OR - menor precedencia]
    static void T() {
        F();
        while (actual().tipo == TipoToken.OR) { consumir(TipoToken.OR); F(); }
    }

    // F -> S ('^' S)*    [AND]
    static void F() {
        S();
        while (actual().tipo == TipoToken.AND) { consumir(TipoToken.AND); S(); }
    }

    // S -> '-' P | P     [NOT]
    static void S() {
        if (actual().tipo == TipoToken.NOT) consumir(TipoToken.NOT);
        P();
    }

    // P -> '[' T ']' | 'true' | 'false'
    static void P() {
        if (actual().tipo == TipoToken.LBRAC) {
            consumir(TipoToken.LBRAC); T(); consumir(TipoToken.RBRAC);
        } else if (actual().tipo == TipoToken.TRUE) {
            consumir(TipoToken.TRUE);
        } else if (actual().tipo == TipoToken.FALSE) {
            consumir(TipoToken.FALSE);
        } else {
            throw new RuntimeException(
                "Se esperaba '[', 'true' o 'false', se encontro '" + actual().valor + "'");
        }
    }

    // ---- MAIN ---------------------------------------------------------------

    static void analizar(String expresion) {
        System.out.print("Analizando: \"" + expresion + "\"  ->  ");
        try {
            tokens = tokenizar(expresion); pos = 0;
            T();
            if (actual().tipo != TipoToken.EOF)
                throw new RuntimeException("Token inesperado al final: '" + actual().valor + "'");
            System.out.println("ACEPTADA");
        } catch (Exception e) { System.out.println("RECHAZADA: " + e.getMessage()); }
    }

    public static void main(String[] args) {
        analizar("true");
        analizar("false");
        analizar("-true");
        analizar("true v false");
        analizar("true ^ false");
        analizar("-true ^ false v true");
        analizar("[true v false]");
        analizar("[true ^ -false] v true");
        analizar("-[true v false]");
        // Invalidos
        analizar("true v");
        analizar("^ false");
        analizar("[true");
    }
}
