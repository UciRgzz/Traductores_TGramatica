// =============================================================================
// Ejercicio 3d - Analizador para Listas Anidadas
// =============================================================================
// Gramatica original:
//   Lista -> '[]'
//   Lista -> '[' T ']'
//   T     -> T ',' T        (ambigua)
//   T     -> id
//   T     -> Lista
//
// Gramatica equivalente sin ambiguedad (asociatividad derecha):
//   Lista -> '[' ']' | '[' T ']'
//   T     -> base (',' T)?
//   base  -> id | Lista
//
// Compilar: javac Ejercicio3d.java
// Ejecutar: java Ejercicio3d
// =============================================================================

import java.util.ArrayList;
import java.util.List;

public class Ejercicio3d {

    // ---- TOKENS -------------------------------------------------------------

    enum TipoToken { ID, LBRAC, RBRAC, COMA, EOF }

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
                       (Character.isLetterOrDigit(texto.charAt(j)) || texto.charAt(j) == '_')) j++;
                tokens.add(new Token(TipoToken.ID, texto.substring(i, j)));
                i = j;
            } else if (c == '[') { tokens.add(new Token(TipoToken.LBRAC, "[")); i++; }
              else if (c == ']') { tokens.add(new Token(TipoToken.RBRAC, "]")); i++; }
              else if (c == ',') { tokens.add(new Token(TipoToken.COMA,  ",")); i++; }
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

    // Lista -> '[' ']' | '[' T ']'
    static void Lista() {
        consumir(TipoToken.LBRAC);
        if (actual().tipo == TipoToken.RBRAC) {
            consumir(TipoToken.RBRAC);  // lista vacia []
        } else {
            T();
            consumir(TipoToken.RBRAC);
        }
    }

    // T -> base (',' T)?   [asociatividad derecha]
    static void T() {
        base();
        if (actual().tipo == TipoToken.COMA) { consumir(TipoToken.COMA); T(); }
    }

    // base -> id | Lista
    static void base() {
        if      (actual().tipo == TipoToken.ID)    consumir(TipoToken.ID);
        else if (actual().tipo == TipoToken.LBRAC) Lista();
        else throw new RuntimeException("Se esperaba id o '[', se encontro '" + actual().valor + "'");
    }

    // ---- MAIN ---------------------------------------------------------------

    static void analizar(String expresion) {
        System.out.print("Analizando: \"" + expresion + "\"  ->  ");
        try {
            tokens = tokenizar(expresion); pos = 0;
            Lista();
            if (actual().tipo != TipoToken.EOF)
                throw new RuntimeException("Token inesperado al final: '" + actual().valor + "'");
            System.out.println("ACEPTADA");
        } catch (Exception e) { System.out.println("RECHAZADA: " + e.getMessage()); }
    }

    public static void main(String[] args) {
        analizar("[]");
        analizar("[a]");
        analizar("[a, b]");
        analizar("[a, b, c]");
        analizar("[[]]");
        analizar("[a, [b, c]]");
        analizar("[[a, b], c]");
        analizar("[[a], [b], [c]]");
        analizar("[[a, [b]], c]");
        // Invalidos
        analizar("[");
        analizar("[a,]");
        analizar("a");
    }
}
