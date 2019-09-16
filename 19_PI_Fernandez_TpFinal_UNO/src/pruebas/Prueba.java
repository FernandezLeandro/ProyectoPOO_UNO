package pruebas;

import java.util.Random;

import modelo.Juego;
import modelo.Jugador;
import modelo.cartas.Mazo;

public class Prueba {

	public static void main(String[] args) {
		Jugador j1 = new Jugador ("Leandro");
		Mazo mz = new Mazo ();
		//mz.mezclarMazo();
		//mz.mostrarMazo();
		/*
		j1.nuevaCarta(mz.darCarta());
		j1.nuevaCarta(mz.darCarta());
		j1.nuevaCarta(mz.darCarta());
		//j1.mostrarMazo();
		*/
		Juego motor = new Juego();

	}

}
