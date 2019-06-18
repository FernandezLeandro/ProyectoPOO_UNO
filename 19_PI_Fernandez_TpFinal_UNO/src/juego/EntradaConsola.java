package juego;

import java.util.Scanner;

public class EntradaConsola {
	private static Scanner entrada;
	
	public static char tomarOpcion () {
		entrada = new Scanner(System.in);
		String opcion = entrada.nextLine();
		return opcion.charAt(0);
	}
	
	public static String tomarString () {
		entrada = new Scanner (System.in);
		return entrada.nextLine();
	}
}
