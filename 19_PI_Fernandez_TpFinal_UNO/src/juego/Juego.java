package juego;

/**
 * Clase : Juego
 * Esta clase contendra toda la logica del juego en si, para poder desarrolarlo.
 * @author Leandro Fernandez
 * @version 1.0 - 22/06/2019
 */
import java.util.ArrayList;

import cartas.Carta;
import cartas.Mazo;
import cartas.eColor;
import cartas.eTipo;

public class Juego {
	private ArrayList <Jugador> listaJugadores = new ArrayList <Jugador> ();
	private ArrayList<IObservador> observadores = new ArrayList<>();
	cambiosDeTurno queCambio;
	private boolean termino = false;
	private int puntajeMAX = 500;
	private Mazo mazo = new Mazo ();
	private Turno turno;
	private eTipo tipoEnJuego;
	private eColor colorEnJuego;
	private int numeroEnJuego;
	boolean hayGanador = false;
	public Juego () {
		ejecutarJuego ();
	}
	
	private void notificarObservadores() {
		for (IObservador o : observadores)
			o.cambiosTurno(queCambio);
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
		cambiaCartaEnJuego(mazo.devolverUltimoDescarte()); 
	}
	
	private void cambiaCartaEnJuego(Carta carta) {// Define la carta que esta en juego actualmente
		colorEnJuego = carta.getColor();
		numeroEnJuego = carta.getValor();
		tipoEnJuego = carta.getTipo();
	}

	public void mostrarAyuda () { // Ofrece una ayuda de como jugar al usuario
		System.out.println("El puntaje maximo por default es de 500 pts si no es modificado.");
	}
	
	private void evaluarCartaEnJuego() {
		switch (tipoEnJuego) {
		case ROBA_2:
			System.out.println("El jugador " + turno.turnoDe().getNombre() + ", agarra 2 cartas y pierde el turno");
			jugadorTomaNCartas (turno.turnoDe(), 2);
			queCambio = cambiosDeTurno.PASA_TURNO;
			notificarObservadores();
			break;
		case PIERDE_TURNO:
			System.out.println("El jugador " + turno.turnoDe().getNombre() + ", pierde el turno");
			queCambio = cambiosDeTurno.PASA_TURNO;
			notificarObservadores();
			break;
		case CAMBIO_SENTIDO:
			System.out.println("SE CAMBIA EL SENTIDO DE LA RONDA.");
			queCambio = cambiosDeTurno.CAMBIO_RONDA;
			notificarObservadores();
			break;
		case COMODIN_ROBA_4:
			System.out.println("El jugador " + turno.turnoDe().getNombre() + ", agarra 4 cartas y pierde el turno");
			jugadorTomaNCartas (turno.turnoDe(), 4);
			queCambio = cambiosDeTurno.PASA_TURNO;
			notificarObservadores();
			break;
		}
	}

	private void jugadorTomaNCartas(Jugador jugador, int cantidad) {
		for (int i = 1; i <= cantidad ; i++  ) {
			jugador.nuevaCarta(mazo.darCarta());
		}
	}
	
	private boolean descarteEsComodin() { // Verifica si la carta en descarte es un comodin
		if (mazo.devolverUltimoDescarte().getTipo() == eTipo.COMODIN || mazo.devolverUltimoDescarte().getTipo() == eTipo.COMODIN_ROBA_4) {
			return true;
		} else
			return false;
	} 
	
	private void huboGanador() {
		hayGanador = true;
	}

	private boolean verificarGanador (Jugador jugador) {
		if (jugador.cantidadCartas() == 0) {
			return true;
		} else
			return false;
		
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
				if (listaJugadores.isEmpty()) {
					System.out.println ("No hay jugadores agregados, no se puede eliminar.");
				} else {
					mostrarJugadores ();
					System.out.print("Ingrese el numero de jugador a eliminar: ");
					int numero = EntradaConsola.tomarInt();
					if (numero <= listaJugadores.size()) {
						listaJugadores.remove(numero-1);
					} else
						System.out.println("El numero ingresado es invalido");
				}
					
				
				break;
			case '3':// Muestra la lista actual de jugadores				
				mostrarJugadores();			
				break;
			case '4': // Modificar el puntaje maximo de juego
				System.out.print("Ingrese el puntaje nuevo: ");
				int puntaje = EntradaConsola.tomarInt();
				if (puntaje < 250) {
					System.out.print("El minimo puntaje es de 250 ptos, ingrese nuevamente: ");
					puntaje = EntradaConsola.tomarInt();
				}
				actualizarPtosMax(puntaje);
				break;
			case '5': // Comienzo del juego
				if (listaJugadores.size() >= 2) {
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
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("*---------- JUEGO EN CURSO -------------*");
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		repartirMazo();
		turno = new Turno (listaJugadores);
		observadores.add(turno);
		System.out.println(" Carta en juego: ");
		System.out.println(mazo.devolverUltimoDescarte());
		evaluarCartaEnJuego ();
		while (!hayGanador) {
			menuDeJuego (turno.turnoDe());
			
		}
		
	}

	private void menuDeJuego(Jugador jugador) {
		System.out.println("-----------------------------------------");
		System.out.println("Jugador en turno: " + jugador.getNombre());
		System.out.println("-----------------------------------------");
		System.out.println("Carta en juego: ");
		System.out.println(mazo.devolverUltimoDescarte());
		System.out.println("-----------------------------------------");
		System.out.println("Tus cartas son: ");
		jugador.mostrarMazo();
		System.out.println("-----------------------------------------");
		System.out.println("Seleccione la carta a jugar: ");
		
		int intentos = 1;
		boolean jugo = false; //Luego se verifica si jugo o perdio sus 3 intentos (y debe agarrar una carta)
		while (intentos <= 3 && !jugo) {
			int numero = EntradaConsola.tomarInt();
			if (numero < 1 || numero > jugador.cantidadCartas()) { //Valida la carta que selecciona
				System.out.println("Numero erroneo, intente nuevamente: ");
				numero = EntradaConsola.tomarInt();
			} else {
				if (validarJugada (jugador.mostrarCarta(numero-1))) {
					jugo = true;
					mazo.addDescarte(jugador.mostrarCarta(numero-1)); // Agrega la carta jugada al descarte
					jugador.darCarta(jugador.mostrarCarta(numero-1)); // Y la elimina del mazo del jugador
					if (verificarGanador (jugador)) { // Verificara si gano
						huboGanador();
					}
					/*
					queCambio = cambiosDeTurno.PASA_TURNO;
					notificarObservadores(); // Notifica al observador lo que cambio
					*/
				} else {
					System.out.println("Carta mal jugada, intente nuevamente: ");
					intentos++;
				}					
			}
		}
		if (!jugo) {
			System.out.println("Has perdido tus 3 intentos, se te dara una carta extra.");
			jugador.nuevaCarta(mazo.darCarta()); // Como perdio sus 3 intentos debe tomar una carta
			queCambio = cambiosDeTurno.PASA_TURNO;
			notificarObservadores();
		}
			
	}

	

	private boolean validarJugada(Carta carta) { //Validara cada jugada que se quiere realizar
		boolean validado = false;
		eTipo caso = carta.getTipo();
		switch (caso) {
		case COMUN:
			if (descarteEsComodin ()) {
				validado = true;
				queCambio = cambiosDeTurno.PASA_TURNO;
				notificarObservadores(); // Notifica al observador lo que cambio
			} else {
				if (carta.getValor() == numeroEnJuego || carta.getColor() == colorEnJuego) {
					validado = true;
					queCambio = cambiosDeTurno.PASA_TURNO;
					notificarObservadores(); // Notifica al observador lo que cambio
				} else
					validado = false;
			}
			//break;
		/*
		case ROBA_2:

			break;
		/*
		case PIERDE_TURNO:
			
			break;
		case CAMBIO_SENTIDO:
			
			break;
		case COMODIN_ROBA_4:
			
			break;
		case COMODIN:
			
			break;
			*/
		}
		return validado;
		
	}


	/* 
	for (Jugador j : listaJugadores) {
			System.out.println(j.getNombre());
			j.mostrarMazo();
		}
	*/
	

	
		

	
}
