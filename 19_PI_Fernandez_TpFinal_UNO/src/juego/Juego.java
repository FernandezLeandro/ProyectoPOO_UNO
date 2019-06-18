package juego;

import java.util.ArrayList;
import java.util.Scanner;

public class Juego {
	private ArrayList <Jugador> listaJugadores = new ArrayList <Jugador> ();
	private boolean termino = false;
	Scanner entrada = new Scanner (System.in);
	
	public Juego () {
		ejecutarJuego ();
	}

	private void ejecutarJuego() {
		while (!termino) {
			menuPrincipal ();
			entrada.nextLine();
			termino = true;
		}
	}
		

	private void menuPrincipal() {
		System.out.println("-----------------------------------------");
		System.out.println("------- UNO - Menú principal ------------");
		System.out.println("-----------------------------------------");
		System.out.println(" 1 - Agregar Jugador --------------------");
		System.out.println(" 2 - Lista Jugadores --------------------");
		System.out.println(" 3 - Comenzar a jugar! ------------------");
		System.out.println(" X - Salir ------------------------------");
		System.out.println("-----------------------------------------");
		System.out.println("Seleccione una opción: ");		
	}
}
