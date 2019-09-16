package modelo;

/**
 * Clase : Juego
 * Esta clase contendra toda la logica del juego en si, para poder desarrolarlo.
 * @author Leandro Fernandez
 * @version 1.0 - 22/06/2019
 */
import java.util.ArrayList;
import java.util.Random;

import modelo.cartas.Carta;
import modelo.cartas.Mazo;
import modelo.cartas.eColor;
import modelo.cartas.eTipo;

public class Juego {
	private ArrayList <Jugador> listaJugadores = new ArrayList <Jugador> ();
	private ArrayList<IObservador> observadores = new ArrayList<>();
	CambiosDeTurno queCambio;
	private boolean termino = false;
	private int puntajeMAX = 500;
	private Mazo mazo = new Mazo ();
	private Turno turno; 
	private eTipo tipoEnJuego; // Administran la carta que esta en juego
	private eColor colorEnJuego, nuevoColor;	
	private int numeroEnJuego;
	private eColor [] colores = new eColor [4];// Arreglo para cuando la primera carta sea el comodin y genere un color al azar
	private boolean tomoCarta; // Verifica si ya tomo carta, para que no tome mas de una por turno.
	boolean hayGanador = false;
	public Juego () {
		ejecutarJuego ();
	}
	
	private void notificarObservadores() {
		for (IObservador o : observadores)
			o.cambiosTurno(queCambio); // Se notifica cada vez que hay un cambio en los turnos
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
		for (int i = 1; i <= 7; i++) { // A cada jugador le reparte 7 cartas
			for (Jugador j : listaJugadores) {
				j.nuevaCarta(mazo.darCarta());
			}
		}
		mazo.addDescarte(mazo.darCarta()); // Luego añade la primera carta al mazo de descarte para comenzar a jugar
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
	
	private void evaluarCartaEnJuego() { //Evalua la carta actual que esta en juego
		switch (tipoEnJuego) {
		case ROBA_2: // El jugador en turno toma dos cartas y pierde su turno
			System.out.println("El jugador " + turno.turnoDe().getNombre() + ", agarra 2 cartas y pierde el turno");
			jugadorTomaNCartas (turno.turnoDe(), 2);
			queCambio = CambiosDeTurno.PASA_TURNO;
			notificarObservadores();
			break;
		case PIERDE_TURNO: // El jugador en turno no puede jugar
			jugadorSalteaTurno ();
			
			break;
		case CAMBIO_SENTIDO:
			jugadorCambiaSentidoRonda();
			/*
			System.out.println("SE CAMBIA EL SENTIDO DE LA RONDA.");
			queCambio = CambiosDeTurno.CAMBIO_RONDA;
			notificarObservadores();
			*/
			break;
		case COMODIN_ROBA_4: // El jugador en turno toma 4 cartas y pierde su turno
			nuevoColor = generarColorAzar ();
			colorEnJuego = nuevoColor;
			System.out.println("El jugador " + turno.turnoDe().getNombre() + ", agarra 4 cartas y pierde el turno");
			System.out.println("El nuevo color en juego es: " + nuevoColor); 
			jugadorTomaNCartas (turno.turnoDe(), 4);
			queCambio = CambiosDeTurno.PASA_TURNO;
			notificarObservadores();
			break;
		case COMODIN:
			nuevoColor = generarColorAzar ();
			colorEnJuego = nuevoColor;
			System.out.println("El nuevo color en juego es: " + nuevoColor);
			break;
		}
	}

	private void jugadorCambiaSentidoRonda() {
		System.out.println("SE CAMBIA EL SENTIDO DE LA RONDA.");
		queCambio = CambiosDeTurno.CAMBIO_RONDA;
		notificarObservadores();
	}

	private void jugadorSalteaTurno() {
		System.out.println("-----------------------------------------");
		System.out.println("El jugador " + turno.turnoDe().getNombre() + ", es salteado, pierde el turno.");
		System.out.println("-----------------------------------------");
		queCambio = CambiosDeTurno.PASA_TURNO;
		notificarObservadores();
	}

	private eColor generarColorAzar() { // Genera un color al azar (sin contar el especial)
		colores = eColor.values();
		Random numero = new Random ();
		return colores [numero.nextInt(5)];
	}

	private void jugadorTomaNCartas(Jugador jugador, int cantidad) { // Al jugador X se le dan N cantidad de cartas
		for (int i = 1; i <= cantidad ; i++  ) {
			jugador.nuevaCarta(mazo.darCarta());
		}
	}
	
	private boolean descarteEsComodin4() { // Verifica si la carta en descarte es un comodin
		if  (mazo.devolverUltimoDescarte().getTipo() == eTipo.COMODIN_ROBA_4) {
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
			//tomoCarta = false;
			menuDeJuego (turno.turnoDe());
			
		}
		
	}
	
	private void menuDeJuego(Jugador jugador) {
		boolean jugo = false; //Luego se verifica si jugo o perdio sus 3 intentos (y debe agarrar una carta)
		System.out.println("-----------------------------------------");
		System.out.println(" Jugador en turno: " + jugador.getNombre());
		System.out.println("-----------------------------------------");
		System.out.println(" Carta en juego: ");
		System.out.println(mazo.devolverUltimoDescarte());
		System.out.println("-----------------------------------------");
		mostrarCartasJugador (jugador);
		System.out.println("-----------------------------------------");
		System.out.println("  0 - Pasar Turno -----------------------");
		if (!tomoCarta) { // Solo permite tomar carta si aun no ha tomado ninguna
			System.out.println(" 99 - Tomar Carta -----------------------");
		}
		System.out.println("-----------------------------------------");
		System.out.print(" Seleccione la opcion a realizar: ");
		int numero = EntradaConsola.tomarInt();
		if (numero == 99 || numero  == 0) {
			if (numero == 99) { // Si pide una carta del mazo
				jugadorPideCarta (jugador);				
			} else { // Si pasa el turno
				if (tomoCarta) {
					jugadorPasaTurno ();
				} else { // Si pasa el turno pero no ha pedido carta antes
					System.out.println("Antes de pasar debes tomar aunque sea una carta, se te ha dado una.");
					jugadorPideCarta(jugador);
					//jugador.nuevaCarta(mazo.darCarta());
					System.out.println("-----------------------------------------");// Luego de darle la carta le muestra nuevamente las cartas
					mostrarCartasJugador (jugador);
					System.out.println("-----------------------------------------");
					System.out.println("  0 - Pasar Turno -----------------------");// Como ya pidio carta, solo puede pasar su turno
					System.out.println("-----------------------------------------");
					System.out.print(" Seleccione la opcion a realizar: ");
					numero = EntradaConsola.tomarInt();
					if (numero == 0) {
						jugadorPasaTurno();
					} else { // Si no pasa turno, evalua su jugada
						// ?? Si uso el metodo para simplificar codigo, puedo tirar por numeros pero no por color
						//tomaCartaAValidar(numero,jugador,jugo); 
						int intentos = 1;// El jugador tiene 3 intentos, caso contrario debe tomar una carta
						while (intentos <= 3 && !jugo) {
							if (numero < 0 || numero > jugador.cantidadCartas()) { //Valida la carta que selecciona
								System.out.println("Numero erroneo, intente nuevamente: ");
								numero = EntradaConsola.tomarInt(); // Falta en caso de que se equivoque, poder evaluar si pasa turno o toma carta
							} else {
								if (validarJugada (jugador.mostrarCarta(numero-1))) {
									jugo = true; //Al ya jugar no vuelve a entrar al ciclo
									tomoCarta = false; // Coloca false para el proximo turno, sino no dejara tomar carta al siguiente
									mazo.addDescarte(jugador.mostrarCarta(numero-1)); // Agrega la carta jugada al descarte
									cambiaCartaEnJuego (mazo.devolverUltimoDescarte());
									jugador.darCarta(jugador.mostrarCarta(numero-1)); // Y la elimina del mazo del jugador
									if (verificarGanador (jugador)) { // Verificara si gano
										huboGanador();
									}
								} else {
									System.out.println("Carta mal jugada, intente nuevamente: ");
									numero = EntradaConsola.tomarInt();
									intentos++;
								}					
							}
						}
						if (!jugo) {
							tomoCarta = false;// Coloca false para el proximo turno, sino no dejara tomar carta al siguiente
							System.out.println("Has perdido tus 3 intentos, se te dara una carta extra.");
							jugador.nuevaCarta(mazo.darCarta()); // Como perdio sus 3 intentos debe tomar una carta
							queCambio = CambiosDeTurno.PASA_TURNO;
							notificarObservadores();
						}
					}
				}
			}
		} else { // Si selecciona una carta, es decir no toma ni pasa turno
			//tomaCartaAValidar(numero,jugador,jugo);
			
			int intentos = 1;		
			while (intentos <= 3 && !jugo) {
				if (numero < 0 || numero > jugador.cantidadCartas()) { //Valida la carta que selecciona
					System.out.println("Numero erroneo, intente nuevamente: ");
					numero = EntradaConsola.tomarInt();
				} else {
					if (validarJugada (jugador.mostrarCarta(numero-1))) {
						jugo = true;
						tomoCarta = false;
						mazo.addDescarte(jugador.mostrarCarta(numero-1)); // Agrega la carta jugada al descarte
						cambiaCartaEnJuego (mazo.devolverUltimoDescarte());
						jugador.darCarta(jugador.mostrarCarta(numero-1)); // Y la elimina del mazo del jugador
						if (verificarGanador (jugador)) { // Verificara si gano
							huboGanador();
						}
					} else {
						System.out.println("Carta mal jugada, intente nuevamente: ");
						numero = EntradaConsola.tomarInt();
						intentos++;
					}					
				}
			}
			if (!jugo) {
				tomoCarta = false;
				System.out.println("Has perdido tus 3 intentos, se te dara una carta extra.");
				jugador.nuevaCarta(mazo.darCarta()); // Como perdio sus 3 intentos debe tomar una carta
				queCambio = CambiosDeTurno.PASA_TURNO;
				notificarObservadores();
			}
		}		
	}

	

	private void tomaCartaAValidar(int numero, Jugador jugador, boolean jugo) {
			int intentos = 1; 		
			while (intentos <= 3 && !jugo) {
				if (numero < 0 || numero > jugador.cantidadCartas()) { //Valida la carta que selecciona
					System.out.println("Numero erroneo, intente nuevamente: ");
					numero = EntradaConsola.tomarInt();
				} else {
					if (validarJugada (jugador.mostrarCarta(numero-1))) {
						jugo = true;
						tomoCarta = false;
						mazo.addDescarte(jugador.mostrarCarta(numero-1)); // Agrega la carta jugada al descarte
						cambiaCartaEnJuego (mazo.devolverUltimoDescarte());
						jugador.darCarta(jugador.mostrarCarta(numero-1)); // Y la elimina del mazo del jugador
						if (verificarGanador (jugador)) { // Verificara si gano
							huboGanador();
						}
					} else {
						System.out.println("Carta mal jugada, intente nuevamente: ");
						numero = EntradaConsola.tomarInt();
						intentos++;
					}					
				}
			}
			if (!jugo) {
				tomoCarta = false;
				System.out.println("Has perdido tus 3 intentos, se te dara una carta extra.");
				jugador.nuevaCarta(mazo.darCarta()); // Como perdio sus 3 intentos debe tomar una carta
				queCambio = CambiosDeTurno.PASA_TURNO;
				notificarObservadores();
			}
	}

	private void jugadorPasaTurno() {
		tomoCarta = false;// Coloca false para el proximo turno, sino no dejara tomar carta al siguiente
		queCambio = CambiosDeTurno.PASA_TURNO;
		notificarObservadores();
	}

	private void jugadorPideCarta(Jugador jugador) {
		jugador.nuevaCarta(mazo.darCarta());
		tomoCarta = true;
	}

	private void mostrarCartasJugador(Jugador jugador) {
		System.out.println("Tus cartas son: ");
		jugador.mostrarMazo();
	}

	private boolean validarJugada(Carta carta) { //Validara cada jugada que se quiere realizar
		boolean validado = false;
		eTipo caso = carta.getTipo();
		switch (caso) {
		case COMUN:
				if (carta.getValor() == numeroEnJuego || carta.getColor() == colorEnJuego) {
					validado = true;
					queCambio = CambiosDeTurno.PASA_TURNO;
					notificarObservadores(); // Notifica al observador lo que cambio
				} else
					validado = false;
			break;
		case ROBA_2:
			if (colorEnJuego == carta.getColor() || tipoEnJuego == carta.getTipo()) {//Debe ser del mismo color
				queCambio = CambiosDeTurno.PASA_TURNO; //Informa al sistema de turnos 
				notificarObservadores();
				System.out.println("-----------------------------------------");
				System.out.println("El jugador " + turno.turnoDe().getNombre() + " debe tomar dos cartas y pierde el turno.");
				System.out.println("-----------------------------------------");
				jugadorTomaNCartas (turno.turnoDe(), 2);// Le da las dos cartas y como perdio su turno, sigue el siguiente a él
				queCambio = CambiosDeTurno.PASA_TURNO;
				notificarObservadores();
				validado = true;
			} else
				validado = false;
			break;
		
		case PIERDE_TURNO:
			if (colorEnJuego == carta.getColor() || tipoEnJuego == carta.getTipo()) {
				queCambio = CambiosDeTurno.PASA_TURNO;
				notificarObservadores();
				System.out.println("-----------------------------------------");
				System.out.println("El jugador " + turno.turnoDe().getNombre() + ", es salteado, pierde el turno.");
				System.out.println("-----------------------------------------");
				queCambio = CambiosDeTurno.PASA_TURNO;
				notificarObservadores();
				validado = true;
			} else
				validado = false;
			break;
		
		case CAMBIO_SENTIDO:
			if (colorEnJuego == carta.getColor() || tipoEnJuego == carta.getTipo()) {
				jugadorCambiaSentidoRonda();
				validado = true;
			} else
				validado = false;
			break;
			/*
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
