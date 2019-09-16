package modelo;

import java.util.ArrayList;

public class Turno implements IObservador{
	private ArrayList <Jugador> turnoJugadores;
	int jugadorEnTurno = 0;
	EstadoRonda direccionRonda = EstadoRonda.DERECHA; // Por defecto la ronda va hacia la derecha
	public Turno (ArrayList listaJug) {
		turnoJugadores = (ArrayList<Jugador>) listaJug.clone();
	}
	
	public Jugador turnoDe () {
		return turnoJugadores.get(jugadorEnTurno);
	}

	@Override
	public void cambiosTurno(CambiosDeTurno cambio) {
		switch (cambio) {
		case PASA_TURNO:
			pasaTurno();	
			break;
		case SALTEA_TURNO:
			if (direccionRonda == EstadoRonda.DERECHA) { // Si la ronda va hacia la derecha
				if (jugadorEnTurno == ultimoJugador()) {// Si salta el ultimo sigue el jugador siguiente del primero
					jugadorEnTurno = 1;
				} else {
					if (jugadorEnTurno == ultimoJugador()-1) {
						jugadorEnTurno = 0;// Si salta el anteultimo sigue el primero
					} else {
						jugadorEnTurno += 2; //Sino saltea a un jugador
					}
				}
			} else {// Ronda hacia la izquierda
				if (jugadorEnTurno == 0) { // Si salta el primero sigue el anteultimo
					jugadorEnTurno = ultimoJugador()-1;
				} else {
					if (jugadorEnTurno == 1) {// Si salta el siguiente al primero, sigue el ultimo
						jugadorEnTurno = ultimoJugador();
					} else {
						jugadorEnTurno -= 2; // Sino salta un jugador
					}
				}
			}
			
				
			break;
		case CAMBIO_RONDA:
			if (direccionRonda == EstadoRonda.DERECHA) { // Si va hacia la derecha
				if (turnoJugadores.size() == 2) {
					direccionRonda = EstadoRonda.IZQUIERDA; // Cambia la ronda y le asigna el turno al siguiente
				} else {
					direccionRonda = EstadoRonda.IZQUIERDA; // Cambia la ronda y le asigna el turno al siguiente
					pasaTurno();
				}
				
			} else { // Caso de que la ronda va hacia la izquierda
				if (turnoJugadores.size() == 2) {
					direccionRonda = EstadoRonda.DERECHA; // Cambia la ronda y le asigna el turno al siguiente
				} else {
					direccionRonda = EstadoRonda.DERECHA; // Cambia la ronda y le asigna el turno al siguiente
					pasaTurno();
				}
			}
			break;
		}
	}

	private void pasaTurno() {
		if (direccionRonda == EstadoRonda.DERECHA) {// Ronda hacia la derecha
			if (jugadorEnTurno == ultimoJugador()) {
				jugadorEnTurno = 0; //Si es el ultimo jugador de la ronda, sigue el primero
			} else
				jugadorEnTurno++; // Sino el siguiente
		} else {
			if (jugadorEnTurno == 0) { // Ronda hacia la izquierda
				jugadorEnTurno = ultimoJugador(); //Si es el primer jugador de la ronda, sigue el ultimo
			} else
				jugadorEnTurno--; // Sino el siguiente
		}
	}
	private int ultimoJugador () {
		return turnoJugadores.size()-1;
	}
}
