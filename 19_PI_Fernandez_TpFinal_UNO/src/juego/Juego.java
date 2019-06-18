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
			char opcion;
			menuPrincipal ();
			opcion = EntradaConsola.tomarOpcion();
			switch (opcion) {
			case '1':
				System.out.println("-----------------------------------------");
				System.out.print(" Agregar Jugador -> Ingrese nombre del jugador: ");
				listaJugadores.add(new Jugador (EntradaConsola.tomarString()));
				System.out.println(" Jugador añadido con exito!");
				break;
			case '2':
				System.out.println(" Lista de jugadores:");
				if (listaJugadores.isEmpty()) {
					System.out.println("No hay jugadores actualmente.");
				} else {
					mostrarJugadores ();				
				}
				break;
			case '3':
				System.out.println("Nada programado para jugar aún.");
				break;
			case '9': 
				System.out.println("XXXXX Ejecución finalizada XXXXX");
				System.exit(0);
				break;
			default:
				System.out.println("ERROR: Ingrese una opción válida.");
			}
		}
	}
		

	private void mostrarJugadores() {
		for (Jugador j : listaJugadores) {
			System.out.println(j);
		}
	}

	private void menuPrincipal() {
		System.out.println("-----------------------------------------");
		System.out.println("------- UNO - Menú principal ------------");
		System.out.println("-----------------------------------------");
		System.out.println(" 1 - Agregar Jugador --------------------");
		System.out.println(" 2 - Lista Jugadores --------------------");
		System.out.println(" 3 - Comenzar a jugar! ------------------");
		System.out.println(" 9 - Salir ------------------------------");
		System.out.println("-----------------------------------------");
		System.out.print("Seleccione una opción: ");		
	}
}
