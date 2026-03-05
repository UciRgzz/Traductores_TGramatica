// =============================================================================
// Ejercicio 1b - Analizador Sintactico Descendente Recursivo (Extendido)
// =============================================================================
// Gramatica extendida con resta, division y potencia:
//
//   E  -> T (('+' | '-') T)*
//   T  -> P (('*' | '/') P)*
//   P  -> F ('^' P)?                  potencia: asociativa por la derecha
//   F  -> '(' E ')' | id
//
// Nivel de precedencia (de mayor a menor):
//   1. ()    parentesis
//   2. ^     potencia  (derecha a izquierda)
//   3. * /   multiplicacion y division
//   4. + -   suma y resta
//
// Compilar: javac Ejercicio1b.java
// Ejecutar: java Ejercicio1b
// =============================================================================

import java.util.ArrayList;
import java.util.List;

public class Ejercicio1b {

    // ---- TOKENS -------------------------------------------------------------

    enum TipoToken { ID, MAS, MENOS, MUL, DIV, POT, LPAREN, RPAREN, EOF }

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
            } else if (Character.isLetterOrDigit(c) || c == '_') {
                int j = i;
                while (j < texto.length() &&
                       (Character.isLetterOrDigit(texto.charAt(j)) || texto.charAt(j) == '_'))
                    j++;
                tokens.add(new Token(TipoToken.ID, texto.substring(i, j)));
                i = j;
            } else if (c == '+')  { tokens.add(new Token(TipoToken.MAS,    "+")); i++; }
              else if (c == '-')  { tokens.add(new Token(TipoToken.MENOS,  "-")); i++; }
              else if (c == '*')  { tokens.add(new Token(TipoToken.MUL,    "*")); i++; }
              else if (c == '/')  { tokens.add(new Token(TipoToken.DIV,    "/")); i++; }
              else if (c == '^')  { tokens.add(new Token(TipoToken.POT,    "^")); i++; }
              else if (c == '(')  { tokens.add(new Token(TipoToken.LPAREN, "(")); i++; }
              else if (c == ')')  { tokens.add(new Token(TipoToken.RPAREN, ")")); i++; }
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

    // E -> T (('+' | '-') T)*
    static void E() {
        T();
        while (actual().tipo == TipoToken.MAS || actual().tipo == TipoToken.MENOS) {
            consumir(actual().tipo); T();
        }
    }

    // T -> P (('*' | '/') P)*
    static void T() {
        P();
        while (actual().tipo == TipoToken.MUL || actual().tipo == TipoToken.DIV) {
            consumir(actual().tipo); P();
        }
    }

    // P -> F ('^' P)?   [asociatividad derecha]
    static void P() {
        F();
        if (actual().tipo == TipoToken.POT) { consumir(TipoToken.POT); P(); }
    }

    // F -> '(' E ')' | id
    static void F() {
        if (actual().tipo == TipoToken.LPAREN) {
            consumir(TipoToken.LPAREN); E(); consumir(TipoToken.RPAREN);
        } else if (actual().tipo == TipoToken.ID) {
            consumir(TipoToken.ID);
        } else {
            throw new RuntimeException("Se esperaba id o '(', se encontro '" + actual().valor + "'");
        }
    }

    // ---- MAIN ---------------------------------------------------------------

    static void analizar(String expresion) {
        System.out.print("Analizando: \"" + expresion + "\"  ->  ");
        try {
            tokens = tokenizar(expresion); pos = 0;
            E();
            if (actual().tipo != TipoToken.EOF)
                throw new RuntimeException("Token inesperado al final: '" + actual().valor + "'");
            System.out.println("ACEPTADA");
        } catch (Exception e) { System.out.println("RECHAZADA: " + e.getMessage()); }
    }

    public static void main(String[] args) {
        analizar("a + b - c");
        analizar("a * b / c");
        analizar("a ^ b ^ c");              // a^(b^c) por asoc. derecha
        analizar("a + b * c ^ 2 - d / e");
        analizar("(a + b) * (c - d)");
        analizar("(a + b) ^ 2");
        // Invalidos
        analizar("a +");
        analizar("a ^ ^ b");
        analizar("/ a");
    }
}
