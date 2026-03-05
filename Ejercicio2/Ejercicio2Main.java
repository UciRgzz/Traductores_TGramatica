// =============================================================================
// Ejercicio 2 - Runner para el parser generado por ANTLR4
// =============================================================================
// Compilar: javac -cp ".;antlr4.jar" -d bin (Get-ChildItem *.java | % Name)
// Ejecutar: java  -cp "bin;antlr4.jar" Ejercicio2Main
// =============================================================================

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Ejercicio2Main {

    static void analizar(String expresion) {
        System.out.print("Analizando: \"" + expresion + "\"  ->  ");

        CharStream input = CharStreams.fromString(expresion);
        Ejercicio2Lexer lexer = new Ejercicio2Lexer(input);

        // Listener de errores personalizado (sin mensajes por defecto de ANTLR)
        BaseErrorListener errores = new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?,?> r, Object sym,
                                    int line, int col,
                                    String msg, RecognitionException e) {
                throw new RuntimeException("col " + col + ": " + msg);
            }
        };

        lexer.removeErrorListeners();
        lexer.addErrorListener(errores);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Ejercicio2Parser  parser = new Ejercicio2Parser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errores);

        try {
            ParseTree tree = parser.programa();
            System.out.println("ACEPTADA  |  Arbol: " + tree.toStringTree(parser));
        } catch (Exception e) {
            System.out.println("RECHAZADA: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        analizar("a + b * c");
        analizar("(a + b) * c");
        analizar("a ^ b ^ c");
        analizar("a + b * c ^ 2 - d / e");
        analizar("(a + b) ^ 2");
        analizar("a - b + c");
        // Invalidos
        analizar("a +");
        analizar("* a");
    }
}
