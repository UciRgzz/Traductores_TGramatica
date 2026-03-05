// =============================================================================
// Ejercicio 3c - Analizador para Bloques begin...end
// =============================================================================
// Gramatica original (con recursion izquierda):
//   B -> 'begin' D ';' S 'end'
//   D -> d | D ';' d
//   S -> s | S ';' s
//
// Gramatica equivalente sin recursion izquierda:
//   B -> 'begin' D ';' S 'end'
//   D -> d (';' d)*
//   S -> s (';' s)*
//
// Tokens:
//   begin, end  -> palabras reservadas
//   d, d1, d2   -> declaraciones
//   s, s1, s2   -> sentencias
//   ;            -> separador
//
// Compilar: javac Ejercicio3c.java
// Ejecutar: java Ejercicio3c
// =============================================================================

import java.util.ArrayList;
import java.util.List;

public class Ejercicio3c {

    // ---- TOKENS -------------------------------------------------------------

    enum TipoToken { BEGIN, END, DECL, SENT, SEMI, EOF }

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
            } else if (Character.isLetterOrDigit(c)) {
                int j = i;
                while (j < texto.length() && Character.isLetterOrDigit(texto.charAt(j))) j++;
                String palabra = texto.substring(i, j);
                if      (palabra.equals("begin"))              tokens.add(new Token(TipoToken.BEGIN, palabra));
                else if (palabra.equals("end"))                tokens.add(new Token(TipoToken.END,   palabra));
                else if (palabra.charAt(0) == 'd')             tokens.add(new Token(TipoToken.DECL,  palabra));
                else if (palabra.charAt(0) == 's')             tokens.add(new Token(TipoToken.SENT,  palabra));
                else throw new RuntimeException("Token desconocido: '" + palabra + "'");
                i = j;
            } else if (c == ';') { tokens.add(new Token(TipoToken.SEMI, ";")); i++; }
              else { throw new RuntimeException("Caracter inesperado: '" + c + "'"); }
        }
        tokens.add(new Token(TipoToken.EOF, "EOF"));
        return tokens;
    }

    // ---- PARSER -------------------------------------------------------------

    static List<Token> tokens;
    static int pos;

    static Token actual() { return tokens.get(pos); }
    static Token siguiente() { return tokens.get(pos + 1); }

    static void consumir(TipoToken esperado) {
        if (actual().tipo != esperado)
            throw new RuntimeException(
                "Se esperaba '" + esperado + "', se encontro '" + actual().valor + "'");
        pos++;
    }

    // B -> 'begin' D ';' S 'end'
    static void B() {
        consumir(TipoToken.BEGIN);
        D();
        consumir(TipoToken.SEMI);
        S();
        consumir(TipoToken.END);
    }

    // D -> d (';' d)*   -- el ';' se consume solo si el siguiente token es DECL
    static void D() {
        consumir(TipoToken.DECL);
        while (actual().tipo == TipoToken.SEMI && siguiente().tipo == TipoToken.DECL) {
            consumir(TipoToken.SEMI);
            consumir(TipoToken.DECL);
        }
    }

    // S -> s (';' s)*
    static void S() {
        consumir(TipoToken.SENT);
        while (actual().tipo == TipoToken.SEMI && siguiente().tipo == TipoToken.SENT) {
            consumir(TipoToken.SEMI);
            consumir(TipoToken.SENT);
        }
    }

    // ---- MAIN ---------------------------------------------------------------

    static void analizar(String expresion) {
        System.out.print("Analizando: \"" + expresion + "\"  ->  ");
        try {
            tokens = tokenizar(expresion); pos = 0;
            B();
            if (actual().tipo != TipoToken.EOF)
                throw new RuntimeException("Token inesperado al final: '" + actual().valor + "'");
            System.out.println("ACEPTADA");
        } catch (Exception e) { System.out.println("RECHAZADA: " + e.getMessage()); }
    }

    public static void main(String[] args) {
        analizar("begin d ; s end");
        analizar("begin d1 ; s1 end");
        analizar("begin d1 ; d2 ; s end");
        analizar("begin d ; s1 ; s2 end");
        analizar("begin d1 ; d2 ; d3 ; s1 ; s2 ; s3 end");
        // Invalidos
        analizar("begin end");
        analizar("begin d end");
        analizar("begin d ; end");
    }
}
