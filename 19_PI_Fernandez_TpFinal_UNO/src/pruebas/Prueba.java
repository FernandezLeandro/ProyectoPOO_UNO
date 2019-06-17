package pruebas;

import java.util.Random;

import cartas.Mazo;

public class Prueba {

	public static void main(String[] args) {
		
		Mazo mz = new Mazo ();
		mz.mezclarMazo();
		//mz.mostrarMazo();
		System.out.println("------------------");
		System.out.println(mz.darCarta());
		System.out.println(mz.darCarta());
	}

}
