package vista;

import java.util.ArrayList;

import controlador.Controlador;
import modelo.Jugador;

public interface IVista {
	void setControlador (Controlador c);
	void mostrarMenuPrincipal (int PtosMax);
	void agregarJugador ();
	void jugCompletos ();
	void sinJugadores ();
	void mostrarJugadores (ArrayList <Jugador> listaJugadores);
	void tomarOpcion ();
	int tomarInt ();
	void eliminarJugador (ArrayList <Jugador> listaJugadores);
	void modificarPtosMax (int minimo);
}
