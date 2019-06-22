package juego;

/**
 * Clase : Juego
 * Esta clase contendra toda la logica del juego en si, para poder desarrolarlo.
 * @author Leandro Fernandez
 * @version 1.0 - 22/06/2019
 */
import java.util.ArrayList;

import cartas.Mazo;

public class Juego {
	private ArrayList <Jugador> listaJugadores = new ArrayList <Jugador> ();
	private boolean termino = false;
	private int puntajeMAX = 500;
	private Mazo mazo = new Mazo ();
	public Juego () {
		ejecutarJuego ();
	}
	
	private void agregarJugador(String jugador) {
		listaJugadores.add(new Jugador (jugador));
	}

	private void mostrarJugadores() { // Muestra los jugadores, si hay
		System.out.println("Lista de jugadores:");
		if (listaJugadores.isEmpty()) {
			System.out.println("No hay jugadores actualmente.");
		} else {
			int i = 0;
			for (Jugador j : listaJugadores) {
				System.out.println(++i + " - " + j);
			}
		}
	}
	
	private void actualizarPtosMax(int puntaje) {
		puntajeMAX = puntaje;
	}
	
	private void repartirMazo() {
		mazo.mezclarMazo();
		for (int i = 1; i <= 7; i++) {
			for (Jugador j : listaJugadores) {
				j.nuevaCarta(mazo.darCarta());
			}
		}
		mazo.addDescarte(mazo.darCarta());
		mazo.addDescarte(mazo.darCarta());
	}
	
	public void mostrarAyuda () { // Ofrece una ayuda de como jugar al usuario
		System.out.println("El puntaje maximo por default es de 500 pts si no es modificado.");
	}
	
	private void mostrarMenuPrincipal() {
		System.out.println("-----------------------------------------");
		System.out.println("------- UNO - Menú principal ------------");
		System.out.println("- Puntaje para ganar: " + puntajeMAX + " ptos" + "-----------");
		System.out.println("-----------------------------------------");
		System.out.println(" 1 - Agregar Jugador --------------------");
		System.out.println(" 2 - Eliminar Jugador -------------------");
		System.out.println(" 3 - Lista Jugadores --------------------");
		System.out.println(" 4 - Modificar puntaje MÁXIMO -----------");
		System.out.println(" 5 - Comenzar a jugar! ------------------");
		System.out.println(" 6 - Ayuda ------------------------------");
		System.out.println(" 9 - Salir ------------------------------");
		System.out.println("-----------------------------------------");
		System.out.print("Seleccione una opción: ");		
	}
	
	
	private void ejecutarJuego() {
		while (!termino) {
			char opcion;
			mostrarMenuPrincipal();
			opcion = EntradaConsola.tomarOpcion(); // A la espera de la eleccion de una opcion
			switch (opcion) {
			case '1':// Agrega un jugador
				System.out.println("-----------------------------------------");
				if (listaJugadores.size() < 10) { // Maximo pueden haber 10 jugadores
					System.out.print(" Agregar Jugador -> Ingrese nombre del jugador: ");
					String jugador = EntradaConsola.tomarString();
					while (jugador.length() == 0) {
						System.out.print("Se necesita nombre para jugador, ingrese uno: ");	
						jugador = EntradaConsola.tomarString();					
					} 
					agregarJugador (jugador);				
					System.out.println(" Jugador: " + jugador +", añadido con exito!");	
				} else
					System.out.println("ERROR: Maximo puede haber 10 jugadores.");
				
				break;
			case '2': // Elimina un jugador
				mostrarJugadores ();
				System.out.print("Ingrese el numero de jugador a eliminar: ");
				int numero = EntradaConsola.tomarInt();
				if (numero <= listaJugadores.size()) {
					listaJugadores.remove(numero-1);
				} else
					System.out.println("El numero ingresado es invalido");
				break;
			case '3':// Muestra la lista actual de jugadores				
				mostrarJugadores();			
				break;
			case '4': // Modificar el puntaje maximo de juego
				System.out.print("Ingrese el puntaje nuevo: ");
				int puntaje = EntradaConsola.tomarInt();
				actualizarPtosMax(puntaje);
				break;
			case '5': // Comienzo del juego
				if (listaJugadores.size() == 2) {
					comenzarRonda();
				} else
					System.out.println("Se necesitan minimo dos jugadores para comenzar.");
				break;
			case '6':
				mostrarAyuda();
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


	private void comenzarRonda() {
		System.out.println("Comienza la ronda...");
		repartirMazo();
	}
	
	/* 
	for (Jugador j : listaJugadores) {
			System.out.println(j.getNombre());
			j.mostrarMazo();
		}
	*/
	

	
		

	
}
