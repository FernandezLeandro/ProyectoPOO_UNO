package vista;

import java.util.ArrayList;

import controlador.Controlador;
import modelo.Jugador;
import modelo.cartas.Carta;
import modelo.cartas.eColor;
import modelo.cartas.eTipo;

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
	void mostrarComienzaRonda ();
	void mostrarUltimoDescarte (Carta carta);
	void cambioEnTurno (Jugador jugador, eTipo tipo);
	void nuevoColor (eColor color);
	void faltanJugadores ();
	void mostrarAyuda (String st);
	void finalizar ();
	void opcionInvalida();
	void mostrarJugadorEnTurno (Jugador jugador);
	void mostrarCartasJug (ArrayList <Carta> cartas);
	void opcionesJug (boolean tomoCarta);
	void tomarOpJug ();
	void debePedirCarta();
	void perdioIntentos();
}
