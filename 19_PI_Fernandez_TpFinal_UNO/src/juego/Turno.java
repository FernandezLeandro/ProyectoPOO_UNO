package juego;

import java.util.ArrayList;

public class Turno implements IObservador{
	private ArrayList <Jugador> turnoJugadores;
	int jugadorEnTurno = 0;
	public Turno (ArrayList listaJug) {
		turnoJugadores = (ArrayList<Jugador>) listaJug.clone();
	}
	
	public Jugador turnoDe () {
		return turnoJugadores.get(jugadorEnTurno);
	}

	@Override
	public void cambiosTurno(cambiosDeTurno cambio) {
		switch (cambio) {
		case PASA_TURNO:
			jugadorEnTurno++;
			break;
		case SALTEA_TURNO:
			
			break;
		case CAMBIO_RONDA:
			
			break;
		}
	}
}
