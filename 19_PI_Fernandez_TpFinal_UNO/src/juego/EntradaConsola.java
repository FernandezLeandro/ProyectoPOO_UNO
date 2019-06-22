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

	public static int tomarInt() {
		entrada = new Scanner(System.in);
		boolean esCorrecto = false;
		int valor = 0;
		do {
			try {// Intenta ejecutar este codigo, y en caso de error..
				valor = entrada.nextInt();
				esCorrecto = true;
			} catch (Exception e) {// .. en caso de error muestra el mensaje y vuelve a pedir un valor
				System.out.print("El valor ingresado no es correcto. Ingrese nuevamente: ");
			}
		}	while (!esCorrecto);
		return valor;
	}
}
