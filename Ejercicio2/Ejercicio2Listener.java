// Generated from Ejercicio2.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Ejercicio2Parser}.
 */
public interface Ejercicio2Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link Ejercicio2Parser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(Ejercicio2Parser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link Ejercicio2Parser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(Ejercicio2Parser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code agrupacion}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAgrupacion(Ejercicio2Parser.AgrupacionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code agrupacion}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAgrupacion(Ejercicio2Parser.AgrupacionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sumaResta}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSumaResta(Ejercicio2Parser.SumaRestaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sumaResta}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSumaResta(Ejercicio2Parser.SumaRestaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code potencia}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPotencia(Ejercicio2Parser.PotenciaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code potencia}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPotencia(Ejercicio2Parser.PotenciaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multDiv}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMultDiv(Ejercicio2Parser.MultDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multDiv}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMultDiv(Ejercicio2Parser.MultDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identificador}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIdentificador(Ejercicio2Parser.IdentificadorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identificador}
	 * labeled alternative in {@link Ejercicio2Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIdentificador(Ejercicio2Parser.IdentificadorContext ctx);
}