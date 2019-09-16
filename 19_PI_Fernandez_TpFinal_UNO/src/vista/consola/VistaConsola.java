package vista.consola;

import controlador.Controlador;
import modelo.Juego;
import vista.IVista;

public class VistaConsola implements IVista{
	private Controlador controlador;
	
	public VistaConsola () {
		
	}
	
	public void setConstrolador (Controlador c) {
		
	}
	public void mostrarMenuPrincipal(int ptosMax) {
		System.out.println("-----------------------------------------");
		System.out.println("------- UNO - Menú principal ------------");
		System.out.println("- Puntaje para ganar: " + ptosMax + " ptos" + "-----------");
		System.out.println("-----------------------------------------");
		System.out.println(" 1 - Agregar Jugador --------------------");
		System.out.println(" 2 - Eliminar Jugador -------------------");
		System.out.println(" 3 - Lista Jugadores --------------------");
		System.out.println(" 4 - Modificar puntaje MÁXIMO -----------");
		System.out.println(" 5 - Comenzar a jugar! ------------------");
		System.out.println(" 6 - Ayuda ------------------------------");
		System.out.println(" 9 - Salir ------------------------------");
		System.out.println("-----------------------------------------");
		System.out.print("Seleccione una opción: ");
	}

	@Override
	public void setControlador(Controlador c) {
		controlador = c;
	}
}
