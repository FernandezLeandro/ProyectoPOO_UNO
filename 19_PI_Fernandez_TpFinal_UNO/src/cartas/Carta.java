package cartas;

/**
 * Clase : Carta
 * Esta clase define la estructura de las cartas.
 * @author Leandro Fernández
 * @version 1.0 - 17/06/2019 
 */
public class Carta {
	private eColor color;
	private int valor;
	private eTipo tipo;
	
	public Carta (int valor, eTipo tipo, eColor color) {
		this.color = color;
		this.valor = valor;
		this.tipo = tipo;
	}
	
	public Carta (eTipo tipo) {
		this.color = eColor.ESPECIAL;
		this.valor = -1;
		this.tipo = tipo;
	}

	public eColor getColor() {
		return color;
	}

	public int getValor() {
		return valor;
	}

	public eTipo getTipo() {
		return tipo;
	}
	
	public String toString () {
		return "Carta: " + getTipo() + ", Valor: " + getValor () + ", Color: " + getColor();
		
	}
}
