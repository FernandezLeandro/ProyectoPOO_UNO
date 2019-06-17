package juego;

import java.util.ArrayList;

import cartas.Carta;
import cartas.eTipo;

/**
 * Clase: Jugador
 * Aqui estará la estructura del jugador que participara en este juego.
 * @author Leandro Fernandez
 * @version 1.0 - 17/06/2019
 */
public class Jugador {
	private String nombre;
	private int puntos;
	private ArrayList <Carta> misCartas = new ArrayList <Carta>();
	
	public Jugador (String nombre) {
		this.nombre = nombre;
		this.puntos = 0;
	}
	
	public void nuevaCarta (Carta c) {// Añade a su mazo la nueva carta por parametro
		misCartas.add(c);
	}
	
	public void setPuntos (int puntos) {
		this.puntos =+ puntos;
	}
	
	public String getNombre() {
		return nombre;
	}

	public int getPuntos() {
		return puntos;
	}

	public void mostrarMazo () {
		int i = 1; // Valor con que se lo podra manipular en consola
		for (Carta c : misCartas) {// Muestra la lista de cartas
			if (c.getTipo() == eTipo.COMUN) {
				System.out.println(i++ + " - " + c.mostrarComun()); 
			} else
				System.out.println(i++ + " - " + c.mostrarEspecial()); 
		}		
	}
	
}
