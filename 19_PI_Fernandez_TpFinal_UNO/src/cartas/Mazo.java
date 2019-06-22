package cartas;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase : Mazo
 * Se encargará de crear y manipular el mazo de juego, utilizando las cartas para ello.
 * @author Leandro Fernandez
 * @version 1.0.2 - 22/06/2019
 */

public class Mazo {
	private Carta [] cartas = new Carta [108];
	private ArrayList <Carta> mazoDescartes = new ArrayList <>();
	private int cartaActual = 0;
	
	public Mazo () {	
		int numCarta = 0; // Indice de las cartas, para realizar las 108, comienza en 
		eColor [] colores = new eColor [4]; // Arreglo para hacer los colores y los especiales
		colores = eColor.values(); // Al array le devuelve los valores del enumerado, ejemplo -> colores [0] = AMARILLO
		eTipo [] tipos = new eTipo [6]; // Lo mismo que con los colores
		tipos = eTipo.values();
		int vuelta = 1; // Debe hacer dos vueltas, porque hay pares de numeros en el mazo, es decir hay dos 0, dos 1, etc.
		for (int color = 0; color < colores.length; color++) {
			if (colores [color] != eColor.ESPECIAL) {// Primero genera las cartas con valor numerico
				while (vuelta < 3) { // Controla que haga 2 vueltas, para hacer 19 cartas, donde solo habra un 0
					for (int valor = 0; valor <= 9; valor++) {
						if (vuelta != 1 || valor != 0) { // Para que solo haya un 0
							cartas [numCarta++] = new Carta (valor, eTipo.COMUN, colores [color]); // Crea objetos cartas
						}			
					}
					vuelta++;
				}
				vuelta = 1; // Reinicia la vuelta para el proximo color
			} else { // Entra cuando es especial 
				for (int tipo = 1; tipo < tipos.length; tipo++) {
					if (tipos[tipo] == eTipo.COMODIN || tipos[tipo]==eTipo.COMODIN_ROBA_4) { // Verifica tales casos especiales
						// debido a que estos casos solo poseen 4 cartas, y no 8 como tendra el "else" (se puede observar que hace 2 vueltas de 4
						for (int i= 1; i <= 4; i++) {
							cartas [numCarta++] = new Carta (tipos[tipo]);
						}
					} else {
						while (vuelta < 3) { // Evalua las 2 vueltas
							for (int i= 1; i <= 4; i++) {
								cartas [numCarta++] = new Carta (tipos[tipo]);
							}
							vuelta++;
						}
						vuelta = 1; // Reinicia para el proximo
					}				
				}
			}
			
		}
	}
	
	// El mazo descarte es el cual tiene las cartas que se van jugando, mientras que el mazo comun tiene las no jugadas aun.
	public void addDescarte (Carta carta) {
		mazoDescartes.add(carta);
	}
	
	public void mostrarUltimoDescarte () { //Muestra la ultima carta del mazo
		System.out.println(mazoDescartes.get(mazoDescartes.size()-1));
	}
	
	public void mostrarDescartes () {//Muestra todos los descartes
		for (Carta c : mazoDescartes) {
			System.out.println(c);
		}
	}
	
	public Carta devolverUltimoDescarte () {
		if (mazoDescartes.isEmpty()) {
			return null;
		} else
			return mazoDescartes.get(mazoDescartes.size()-1);
	}
	public void mostrarMazo () {
		int i = 1; // Valor con que se lo podra manipular en consola
		for (Carta c : cartas) {// Muestra la lista de cartas
			if (c.getTipo() == eTipo.COMUN) {
				System.out.println(i++ + " - " + c.mostrarComun()); 
			} else
				System.out.println(i++ + " - " + c.mostrarEspecial()); 
		}	
	}
	
	public void mezclarMazo() {
		Random r = new Random();
		for(int i = 0; i < cartas.length; i ++) {
			int pos = r.nextInt(108);
			Carta aux = cartas[i];
			cartas[i] = cartas[pos];
			cartas[pos] = aux;
		}
	}
	
	public Carta darCarta() { //Metodo que da la primer carta del mazo
		if (cartaActual >= cartas.length) 
			return null; //Devuelve null si ya no hay mazo
		else
			return cartas[cartaActual++]; //Devuelve la primera carta actuald el mazo (como cuando tomas una carta en un mazo al jugar cualquier juego)
		
	}
	
}
