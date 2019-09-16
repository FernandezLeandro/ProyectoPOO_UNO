package modelo;

import java.util.ArrayList;

import modelo.cartas.Carta;
import modelo.cartas.eTipo;

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
	
	public Carta mostrarCarta (int numero) {
		return misCartas.get(numero);
	}
	
	public void darCarta (Carta carta) {
		int pos = misCartas.indexOf(carta);
		misCartas.remove(pos);
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
	
	public int getPuntos () { // Obtiene puntos segun las cartas de su mazo
		int ptos = 0;
		if (misCartas.isEmpty()) { // Si esta vacio devuelve 0
			return ptos;
		} else {
			for (Carta c : misCartas) {
				if (c.getTipo() == eTipo.COMUN) { // Si la carta es comun incrementa con su valor
					ptos += c.getValor();
				} else {
					if (c.getTipo() == eTipo.COMODIN || c.getTipo() == eTipo.COMODIN_ROBA_4) {
						ptos += 50; // Si es algun comodin suma 50
					} else
						ptos += 20; // Si es otro tipo especial, suma 20
				}
			}
			return ptos;
		}
		
	}
	
	public String toString () {
		return "Jugador: " + getNombre() + "| Puntos: " + getPuntos();
	}

	public int cantidadCartas() {
		return misCartas.size();
	}
}
