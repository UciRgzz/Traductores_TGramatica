// Generated from Ejercicio2.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Ejercicio2Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Ejercicio2Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Ejercicio2Parser#programa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrograma(Ejercicio2Parser.ProgramaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code agrupacion}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAgrupacion(Ejercicio2Parser.AgrupacionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sumaResta}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSumaResta(Ejercicio2Parser.SumaRestaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code potencia}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPotencia(Ejercicio2Parser.PotenciaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multDiv}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultDiv(Ejercicio2Parser.MultDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identificador}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentificador(Ejercicio2Parser.IdentificadorContext ctx);
}