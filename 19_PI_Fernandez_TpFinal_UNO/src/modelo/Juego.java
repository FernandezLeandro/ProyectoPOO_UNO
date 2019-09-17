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
	public ArrayList <Jugador> listaJugadores = new ArrayList <Jugador> ();
	private ArrayList<IObservador> observadores = new ArrayList<>();
	private IObservadorTurno observadorT;
	CambiosEnJuego queCambio;
	CambiosEnTurno queCambioT;
	private boolean termino = false;
	public int puntajeMAX = 500;
	private int puntajeMIN = 250;
	public int getPuntajeMIN() {
		return puntajeMIN;
	}

	public void setPuntajeMIN(int puntajeMIN) {
		this.puntajeMIN = puntajeMIN;
	}

	private Mazo mazo = new Mazo ();
	private Turno turno; 
	private eTipo tipoEnJuego; // Administran la carta que esta en juego
	private eColor colorEnJuego, nuevoColor;	
	private int numeroEnJuego;
	private eColor [] colores = new eColor [4];// Arreglo para cuando la primera carta sea el comodin y genere un color al azar
	private boolean tomoCarta; // Verifica si ya tomo carta, para que no tome mas de una por turno.
	boolean hayGanador = false;
	public char opcion;
	
	public Juego () {
		//ejecutarJuego ();
	}
	
	public void setOpcion(char opcion) {
		this.opcion = opcion;
	}

	private void notificarObservadores() {
		for (IObservador o : observadores)
			o.cambiosJuego(queCambio); // Se notifica cada vez que hay un cambio en los turnos
	}
	
	private void notificarObT () {
		observadorT.cambiosEnTurno(queCambioT);
	}
	
	public void agregarJugador(String jugador) {
		listaJugadores.add(new Jugador (jugador));
	}

	public void eliminarJugador (int posicion) {
		listaJugadores.remove(posicion);
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
	
	
	
	public void actualizarPtosMax(int puntaje) {
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

	public String mostrarAyuda () { // Ofrece una ayuda de como jugar al usuario
	String texto;
	texto = "";
	texto = texto + "El puntaje maximo por default es de 500 pts si no es modificado.";
		return texto;
	}
	
	public Jugador turnoActualDe () {
		return turno.turnoDe();
	}
	private void evaluarCartaEnJuego() { //Evalua la carta actual que esta en juego
		switch (tipoEnJuego) {
		case ROBA_2: // El jugador en turno toma dos cartas y pierde su turno
			queCambio = CambiosEnJuego.seCambiaTurno;
			notificarObservadores ();
			jugadorTomaNCartas (turno.turnoDe(), 2);
			pasaTurno ();
			break;
			
		case PIERDE_TURNO: // El jugador en turno no puede jugar
			queCambio = CambiosEnJuego.seCambiaTurno;
			notificarObservadores ();
			pasaTurno ();
			break;
			
		case CAMBIO_SENTIDO:
			queCambio = CambiosEnJuego.seCambiaTurno;
			notificarObservadores ();
			CambiaSentidoRonda();
			break;
			
		case COMODIN_ROBA_4: // El jugador en turno toma 4 cartas y pierde su turno			
			nuevoColor = generarColorAzar ();
			colorEnJuego = nuevoColor;
			queCambio = CambiosEnJuego.seCambiaTurno;
			notificarObservadores ();
			jugadorTomaNCartas (turnoActualDe(), 4);
			pasaTurno ();
			break;
			
		case COMODIN:
			nuevoColor = generarColorAzar ();
			colorEnJuego = nuevoColor;
			queCambio = CambiosEnJuego.nuevoColor;
			notificarObservadores ();
			break;
		}
	}

	public eColor getNuevoColor() {
		return nuevoColor;
	}

	public eTipo getTipoEnJuego() {
		return tipoEnJuego;
	}

	private void CambiaSentidoRonda() {
		queCambioT = CambiosEnTurno.CAMBIO_RONDA;
		notificarObT();
	}

	private void pasaTurno () {
		queCambioT = CambiosEnTurno.PASA_TURNO;
		notificarObT ();
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
	
	
	public void ejecutarJuego() {
		while (!termino) {
			
			// Muestra el menu principal
			queCambio = CambiosEnJuego.menuPrinc;
			notificarObservadores ();
			queCambio = CambiosEnJuego.tomarOpcion;
			notificarObservadores ();
			switch (opcion) {
			case '1':// Agrega un jugador
				if (listaJugadores.size() < 4) { // Maximo pueden haber 4 jugadores
					// Solicitar nombre del jugador y agregarlo
					queCambio = CambiosEnJuego.agregarJug;
					notificarObservadores ();
				} else {
					// Si esta llena la lista de jugadores, notificarlo
					queCambio = CambiosEnJuego.jugCompletos;
					notificarObservadores ();
				}
				
				break;
				
			case '2': // Elimina un jugador
				if (listaJugadores.isEmpty()) {
					queCambio = CambiosEnJuego.sinJug;
					notificarObservadores ();
				} else {
					// Muestra la lista de jugadores
					queCambio = CambiosEnJuego.mostrarJugadores;
					notificarObservadores ();
					// Elimina un jugador de X posicion solicitada por el usuario
					queCambio = CambiosEnJuego.eliminarJug;
					notificarObservadores ();				
				}			
				break;
				
			case '3':// Muestra la lista actual de jugadores				
				queCambio = CambiosEnJuego.mostrarJugadores;
				notificarObservadores ();			
				break;
				
			case '4': // Modificar el puntaje maximo de juego
				queCambio = CambiosEnJuego.modificarPtosMax;
				notificarObservadores ();
				

				break;
			case '5': // Comienzo del juego
				if (listaJugadores.size() >= 2) {
					comenzarRonda();
				} else {
					queCambio = CambiosEnJuego.minDosJug;
					notificarObservadores ();
				}
				break;
			case '6':
				queCambio = CambiosEnJuego.Help;
				notificarObservadores ();
				break;
			case '9': 
				queCambio = CambiosEnJuego.finalizar;
				notificarObservadores ();
				System.exit(0);
				break;
			default: {
				queCambio = CambiosEnJuego.opcionInvalida;
				notificarObservadores ();
			}
			}
		}
	}

	public void agregarObservador (IObservador observador) {
		observadores.add(observador);
	}
	private void comenzarRonda() {
		// Primero reparte el mazo a cada uno de los jugadores
		repartirMazo();
		// Crea la instancia de Turno con los jugadores que hay
		turno = new Turno (listaJugadores);
		agregarObservadorT (turno);
		// Muestra el comienzo de la ronda
		queCambio = CambiosEnJuego.comienzaRonda;
		notificarObservadores ();
		// Y muestra la ultima carta en descarte, para comenzar a jugar
		queCambio = CambiosEnJuego.mostrarUltimoDescarte;
		notificarObservadores ();
		
		evaluarCartaEnJuego ();
		while (!hayGanador) {	
			//tomoCarta = false;
			menuDeJuego (turno.turnoDe());
			
		}
		
	}
	
	public Carta ultimoDescarte () {
		return mazo.devolverUltimoDescarte();
	}
	
	private void agregarObservadorT(Turno turno2) {
		observadorT = turno;
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
							queCambioT = CambiosEnTurno.PASA_TURNO;
							notificarObT();
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
				queCambioT = CambiosEnTurno.PASA_TURNO;
				notificarObT();
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
				queCambioT = CambiosEnTurno.PASA_TURNO;
				notificarObT();
			}
	}

	private void jugadorPasaTurno() {
		tomoCarta = false;// Coloca false para el proximo turno, sino no dejara tomar carta al siguiente
		queCambioT = CambiosEnTurno.PASA_TURNO;
		notificarObT();
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
					queCambioT = CambiosEnTurno.PASA_TURNO;
					notificarObT(); // Notifica al observador lo que cambio
				} else
					validado = false;
			break;
		case ROBA_2:
			if (colorEnJuego == carta.getColor() || tipoEnJuego == carta.getTipo()) {//Debe ser del mismo color
				queCambioT = CambiosEnTurno.PASA_TURNO; //Informa al sistema de turnos 
				notificarObT();
				System.out.println("-----------------------------------------");
				System.out.println("El jugador " + turno.turnoDe().getNombre() + " debe tomar dos cartas y pierde el turno.");
				System.out.println("-----------------------------------------");
				jugadorTomaNCartas (turno.turnoDe(), 2);// Le da las dos cartas y como perdio su turno, sigue el siguiente a él
				queCambioT = CambiosEnTurno.PASA_TURNO;
				notificarObT();
				validado = true;
			} else
				validado = false;
			break;
		
		case PIERDE_TURNO:
			if (colorEnJuego == carta.getColor() || tipoEnJuego == carta.getTipo()) {
				queCambioT = CambiosEnTurno.PASA_TURNO;
				notificarObT();
				System.out.println("-----------------------------------------");
				System.out.println("El jugador " + turno.turnoDe().getNombre() + ", es salteado, pierde el turno.");
				System.out.println("-----------------------------------------");
				queCambioT = CambiosEnTurno.PASA_TURNO;
				notificarObT();
				validado = true;
			} else
				validado = false;
			break;
		
		case CAMBIO_SENTIDO:
			if (colorEnJuego == carta.getColor() || tipoEnJuego == carta.getTipo()) {
				CambiaSentidoRonda();
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
