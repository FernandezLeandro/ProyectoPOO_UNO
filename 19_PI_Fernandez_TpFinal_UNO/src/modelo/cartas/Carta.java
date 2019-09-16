package modelo.cartas;

/**
 * Clase : Carta
 * Esta clase define la estructura de las cartas.
 * @author Leandro Fernández
 * @version 1.0.1 - 17/06/2019 
 */

/* CONSIDERACIONES GENERALES
	- El mazo de descartes, podria utilizar una estructura PILA 
	- En la creacion del mazo, se podria optimizar (observar la variable p tambien)
	- mostrarUltimoDescarte () podria directamente mostrar una carta X, y pasarle por parametro la carta a mostrar
	
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
		if (this.getTipo()== eTipo.COMUN) {
			return mostrarComun();
		} else
			return mostrarEspecial();
		
	}
	
	public String mostrarComun () {
		return "Valor: " + this.getValor () + ", Color: " + this.getColor();
		
	}
	public String mostrarEspecial () {
		String retorno = "";		
		switch (tipo) {
			case ROBA_2:
				retorno = "Carta: |+2| " + ", Color: "+ this.getColor();
				break;
			case PIERDE_TURNO: 
				retorno = "Carta: SALTEA" + ", Color: "+ this.getColor();
				break;
			case CAMBIO_SENTIDO:
				retorno = "Carta: CAMBIA RONDA" + ", Color: "+ this.getColor();
				break;
			case COMODIN:
				retorno = "Carta: COMODIN DE COLORES";
				break;
			case COMODIN_ROBA_4:
				retorno = "Carta: COMODIN |+4| ";
				break;
		}		
		return retorno;
	}

	
}
