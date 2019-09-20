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
	public int numOpcion;


	public void setNumOpcion(int numOpcion) {
		this.numOpcion = numOpcion;
	}

	private Mazo mazo = new Mazo ();
	private Turno turno; 
	private eTipo tipoEnJuego; // Administran la carta que esta en juego
	private eColor colorEnJuego, nuevoColor;	
	private int numeroEnJuego;
	private eColor [] colores = new eColor [4];// Arreglo para cuando la primera carta sea el comodin y genere un color al azar
	public boolean tomoCarta; // Verifica si ya tomo carta, para que no tome mas de una por turno.
	
	public boolean isTomoCarta() {
		return tomoCarta;
	}

	boolean hayGanador = false;
	public char opcion;
	
	public Juego () {
		//ejecutarJuego ();
	}
	
	public int getPuntajeMIN() {
		return puntajeMIN;
	}

	public void setPuntajeMIN(int puntajeMIN) {
		this.puntajeMIN = puntajeMIN;
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
		/*
		 * Faltaria evaluar si la carta es comun, es decir al arrancar el juego 
		 * no hace falta hacer nada, sigue normalmente
		 */
		switch (tipoEnJuego) {
		/*case COMUN:
			queCambio = CambiosEnJuego.seCambiaTurno;
			notificarObservadores ();
			break;*/
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
			
		case CAMBIO_SENTIDO: // Se cambia el sentido de la ronda
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
			menuDeJuego (turnoActualDe ());
			
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
		// Muestra el jugador actual en turno
		queCambio = CambiosEnJuego.mostrarJugEnTurno;
		notificarObservadores ();
		// Y muestra el ultimo descarte
		queCambio = CambiosEnJuego.mostrarUltimoDescarte;
		notificarObservadores ();
		// muestra las cartas del jugador en turno
		queCambio = CambiosEnJuego.mostrarCartasJug;
		notificarObservadores ();
		
		// Muestra sus opciones
		queCambio = CambiosEnJuego.opcionesJug;
		notificarObservadores ();
		
		queCambio = CambiosEnJuego.tomarOpJug;
		notificarObservadores ();
		/*
		System.out.print(" Seleccione la opcion a realizar: ");
		numOpcion = EntradaConsola.tomarInt();
		*/
		if (numOpcion == 99 || numOpcion  == 0) {
			if (numOpcion == 99) { // Si pide una carta del mazo
				jugadorPideCarta (jugador);	
				tomoCarta = true;
			} else { // Si pasa el turno
				if (tomoCarta) {
					jugadorPasaTurno ();
				} else { // Si pasa el turno pero no ha pedido carta antes
					tomoCarta= true;
					
					// Notifica que el jugador debe pedir una carta antes de pasar
					queCambio = CambiosEnJuego.debePedirCarta;
					notificarObservadores ();
					
					jugadorPideCarta(jugador);
					
					// Muestra las cartas del jugador
					queCambio = CambiosEnJuego.mostrarCartasJug;
					notificarObservadores ();
					
					// Muestra sus opciones
					queCambio = CambiosEnJuego.opcionesJug;
					notificarObservadores ();
					
					// Toma la opcion que desea el jugador 
					queCambio = CambiosEnJuego.tomarOpJug;
					notificarObservadores ();

					if (numOpcion == 0) {
						jugadorPasaTurno();
					} else { // Si no pasa turno, evalua su jugada
						// ?? Si uso el metodo para simplificar codigo, puedo tirar por numeros pero no por color
						//tomaCartaAValidar(numOpcion,jugador,jugo); 
						
						int intentos = 1;// El jugador tiene 3 intentos, caso contrario debe tomar una carta
						while (intentos <= 3 && !jugo) {
							if (numOpcion < 0 || numOpcion > jugador.cantidadCartas()) { //Valida la carta que selecciona
								queCambio = CambiosEnJuego.opcionInvalida;
								notificarObservadores ();
								
								queCambio = CambiosEnJuego.tomarOpJug;
								notificarObservadores ();
								//numOpcion = EntradaConsola.tomarInt(); // Falta en caso de que se equivoque, poder evaluar si pasa turno o toma carta
							} else {
								if (validarJugada (jugador.mostrarCarta(numOpcion-1))) {
									jugo = true; //Al ya jugar no vuelve a entrar al ciclo
									tomoCarta = false; // Coloca false para el proximo turno, sino no dejara tomar carta al siguiente
									mazo.addDescarte(jugador.mostrarCarta(numOpcion-1)); // Agrega la carta jugada al descarte
									cambiaCartaEnJuego (mazo.devolverUltimoDescarte());
									jugador.darCarta(jugador.mostrarCarta(numOpcion-1)); // Y la elimina del mazo del jugador
									if (verificarGanador (jugador)) { // Verificara si gano
										huboGanador();
									}
								} else {
									queCambio = CambiosEnJuego.opcionInvalida;
									notificarObservadores ();
									
									queCambio = CambiosEnJuego.tomarOpJug;
									notificarObservadores ();
									intentos++;
								}					
							}
						}
						if (!jugo) {
							tomoCarta = false;// Coloca false para el proximo turno, sino no dejara tomar carta al siguiente
							queCambio = CambiosEnJuego.perdioIntentos;
							notificarObservadores ();
							jugador.nuevaCarta(mazo.darCarta()); // Como perdio sus 3 intentos debe tomar una carta
							queCambioT = CambiosEnTurno.PASA_TURNO;
							notificarObT();
						}
					}
				}
			}
		} else { // Si selecciona una carta, es decir no toma ni pasa turno
			//tomaCartaAValidar(numOpcion,jugador,jugo);
			
			int intentos = 1;// El jugador tiene 3 intentos, caso contrario debe tomar una carta
			while (intentos <= 3 && !jugo) {
				if (numOpcion < 0 || numOpcion > jugador.cantidadCartas()) { //Valida la carta que selecciona
					queCambio = CambiosEnJuego.opcionInvalida;
					notificarObservadores ();
					
					queCambio = CambiosEnJuego.tomarOpJug;
					notificarObservadores ();
					//numOpcion = EntradaConsola.tomarInt(); // Falta en caso de que se equivoque, poder evaluar si pasa turno o toma carta
				} else {
					if (validarJugada (jugador.mostrarCarta(numOpcion-1))) {
						jugo = true; //Al ya jugar no vuelve a entrar al ciclo
						tomoCarta = false; // Coloca false para el proximo turno, sino no dejara tomar carta al siguiente
						mazo.addDescarte(jugador.mostrarCarta(numOpcion-1)); // Agrega la carta jugada al descarte
						cambiaCartaEnJuego (mazo.devolverUltimoDescarte());
						jugador.darCarta(jugador.mostrarCarta(numOpcion-1)); // Y la elimina del mazo del jugador
						if (verificarGanador (jugador)) { // Verificara si gano
							huboGanador();
						}
					} else {
						queCambio = CambiosEnJuego.opcionInvalida;
						notificarObservadores ();
						
						queCambio = CambiosEnJuego.tomarOpJug;
						notificarObservadores ();
						intentos++;
					}					
				}
			}
			if (!jugo) {
				tomoCarta = false;// Coloca false para el proximo turno, sino no dejara tomar carta al siguiente
				queCambio = CambiosEnJuego.perdioIntentos;
				notificarObservadores ();
				jugador.nuevaCarta(mazo.darCarta()); // Como perdio sus 3 intentos debe tomar una carta
				queCambioT = CambiosEnTurno.PASA_TURNO;
				notificarObT();
			}
		}		
	}

	



	public ArrayList <Carta> cartasJugadorActual() {
		return turnoActualDe ().getMisCartas();
	}

	/*
	private void tomaCartaAValidar(int numOpcion, Jugador jugador, boolean jugo) {
			int intentos = 1; 		
			while (intentos <= 3 && !jugo) {
				if (numOpcion < 0 || numOpcion > jugador.cantidadCartas()) { //Valida la carta que selecciona
					System.out.println("Numero erroneo, intente nuevamente: ");
					numOpcion = EntradaConsola.tomarInt();
				} else {
					if (validarJugada (jugador.mostrarCarta(numOpcion-1))) {
						jugo = true;
						tomoCarta = false;
						mazo.addDescarte(jugador.mostrarCarta(numOpcion-1)); // Agrega la carta jugada al descarte
						cambiaCartaEnJuego (mazo.devolverUltimoDescarte());
						jugador.darCarta(jugador.mostrarCarta(numOpcion-1)); // Y la elimina del mazo del jugador
						if (verificarGanador (jugador)) { // Verificara si gano
							huboGanador();
						}
					} else {
						System.out.println("Carta mal jugada, intente nuevamente: ");
						numOpcion = EntradaConsola.tomarInt();
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
	}*/

	private void jugadorPasaTurno() {
		tomoCarta = false;// Coloca false para el proximo turno, sino no dejara tomar carta al siguiente
		queCambioT = CambiosEnTurno.PASA_TURNO;
		notificarObT();
	}

	private void jugadorPideCarta(Jugador jugador) {
		jugador.nuevaCarta(mazo.darCarta());
		tomoCarta = true;
	}



	private boolean validarJugada(Carta carta) { //Validara cada jugada que se quiere realizar
		boolean validado = false;
		eTipo caso = carta.getTipo();
		switch (caso) {
		case COMUN:
				if (carta.getValor() == numeroEnJuego || carta.getColor() == colorEnJuego) {
					validado = true;
					pasaTurno ();
				} else
					validado = false;
			break;
		case ROBA_2:
			if (colorEnJuego == carta.getColor() || tipoEnJuego == carta.getTipo()) {//Debe ser del mismo color
				pasaTurno ();
				queCambio = CambiosEnJuego.seCambiaTurno;
				notificarObservadores ();
				jugadorTomaNCartas (turno.turnoDe(), 2);// Le da las dos cartas y como perdio su turno, sigue el siguiente a él
				pasaTurno ();
				validado = true;
			} else
				validado = false;
			break;
		
		case PIERDE_TURNO:
			if (colorEnJuego == carta.getColor() || tipoEnJuego == carta.getTipo()) {
				pasaTurno ();
				queCambio = CambiosEnJuego.seCambiaTurno;
				notificarObservadores ();
				pasaTurno ();
				validado = true;
			} else
				validado = false;
			break;
		
		case CAMBIO_SENTIDO:
			if (colorEnJuego == carta.getColor() || tipoEnJuego == carta.getTipo()) {
				CambiaSentidoRonda();
				queCambio = CambiosEnJuego.seCambiaTurno;
				notificarObservadores ();
				validado = true;
			} else
				validado = false;
			break;
			
		case COMODIN_ROBA_4:
			// Muestra los colores a elegir
			queCambio = CambiosEnJuego.elegirColor;
			notificarObservadores ();
			// Recibe el color elegido
			queCambio = CambiosEnJuego.tomarColor;
			notificarObservadores ();
			// Valida la entrada
			while (numOpcion < 1 || numOpcion > 4) {
				queCambio = CambiosEnJuego.opcionInvalida;
				notificarObservadores ();
				queCambio = CambiosEnJuego.tomarColor;
				notificarObservadores ();
			} 
			// Actualiza el nuevo color al juego
			cambiarColor (numOpcion);
			// Notifica el cambio
			queCambio = CambiosEnJuego.nuevoColor;
			notificarObservadores();
			pasaTurno (); 
			queCambio = CambiosEnJuego.seCambiaTurno;
			notificarObservadores();
			jugadorTomaNCartas (turnoActualDe(), 4);
			validado = true;
			break;
		
		case COMODIN:
			// Muestra los colores a elegir
			queCambio = CambiosEnJuego.elegirColor;
			notificarObservadores ();
			// Recibe el color elegido
			queCambio = CambiosEnJuego.tomarColor;
			notificarObservadores ();
			// Valida la entrada
			while (numOpcion < 1 || numOpcion > 4) {
				queCambio = CambiosEnJuego.opcionInvalida;
				notificarObservadores ();
				queCambio = CambiosEnJuego.tomarColor;
				notificarObservadores ();
			} 
			// Actualiza el nuevo color al juego
			cambiarColor (numOpcion);
			// Notifica el cambio
			queCambio = CambiosEnJuego.nuevoColor;
			notificarObservadores();
			pasaTurno (); 
			queCambio = CambiosEnJuego.seCambiaTurno;
			notificarObservadores();
			validado = true;
			break;
		}
		return validado;
		
	}

	private void cambiarColor(int num) {
		colores = eColor.values();
		colorEnJuego = colores [num-1];
		nuevoColor = colorEnJuego;
				
	}
	
}
