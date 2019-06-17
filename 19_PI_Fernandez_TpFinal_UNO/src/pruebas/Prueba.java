package pruebas;

import java.util.Random;

import cartas.Mazo;
import juego.Jugador;

public class Prueba {

	public static void main(String[] args) {
		Jugador j1 = new Jugador ("Leandro");
		Mazo mz = new Mazo ();
		mz.mezclarMazo();
		//mz.mostrarMazo();
		j1.nuevaCarta(mz.darCarta());
		j1.nuevaCarta(mz.darCarta());
		j1.mostrarMazo();
	}

}
