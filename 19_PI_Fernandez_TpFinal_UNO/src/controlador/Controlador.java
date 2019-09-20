package controlador;

import java.util.ArrayList;

import modelo.CambiosEnJuego;
import modelo.IObservador;
import modelo.Juego;
import modelo.Jugador;
import modelo.cartas.eColor;
import vista.IVista;
import vista.consola.VistaConsola;


public class Controlador  implements IObservador{
	
	private Juego juego = new Juego ();
	private IVista vista;
	public Controlador (Juego juego, IVista vista) {
		this.vista = vista;	
		this.juego = juego;
		juego.agregarObservador(this);
		vista.setControlador(this);
		
	}
	

	public static void main(String[] args) {
		Juego juego = new Juego();
        IVista vista = new VistaConsola();
        Controlador c = new Controlador(juego, vista);
        juego.ejecutarJuego();
    }
	
	public void agregarJugador (String jugador) {
		juego.agregarJugador(jugador);
	}

	public void setearOpcion (char opcion) {
		juego.setOpcion(opcion);
	}

	public void eliminarJug (int posicion) {
		juego.eliminarJugador(posicion);
	}
	
	public void actualizarPtosMaximos (int ptos) {
		juego.actualizarPtosMax(ptos);
	}
	
	public eColor obtenerNewColor () {
		return juego.getNuevoColor();
	}
	
	public void setearNumOpcion (int opcion) {
		juego.setNumOpcion(opcion);
	}
	@Override
	public void cambiosJuego(CambiosEnJuego cambio) {
		switch (cambio) {
		case menuPrinc : 
			vista.mostrarMenuPrincipal(juego.puntajeMAX);
			break;
		
		case agregarJug:
			vista.agregarJugador();
			break;
		case jugCompletos:
			vista.jugCompletos ();
			break;
		case sinJug:
			vista.sinJugadores();
			break;
		case mostrarJugadores:
			vista.mostrarJugadores(juego.listaJugadores);
			break;
		case tomarOpcion:
			vista.tomarOpcion();
			break;
		case tomarInt:
			
			break;
		case eliminarJug:
			vista.eliminarJugador(juego.listaJugadores);
			break;
		case modificarPtosMax:
			vista.modificarPtosMax(juego.getPuntajeMIN());
			break;
		case comienzaRonda:
			vista.mostrarComienzaRonda();
			break;
		case mostrarUltimoDescarte: 
			vista.mostrarUltimoDescarte(juego.ultimoDescarte());
			break;
		case seCambiaTurno:
			vista.cambioEnTurno(juego.turnoActualDe(), juego.getTipoEnJuego());
			break;
		case nuevoColor:
			vista.nuevoColor(juego.getNuevoColor());
			break;
		case minDosJug:
			vista.faltanJugadores();
			break;
		case Help:
			vista.mostrarAyuda(juego.mostrarAyuda());
			break;
		case finalizar:
			vista.finalizar();
			break;
		case opcionInvalida:
			vista.opcionInvalida();
		case mostrarJugEnTurno:
			vista.mostrarJugadorEnTurno(juego.turnoActualDe());
			break;
		case mostrarCartasJug:
			vista.mostrarCartasJug(juego.cartasJugadorActual());
			break;
		case opcionesJug:
			vista.opcionesJug(juego.isTomoCarta());
			break;
		case tomarOpJug:
			vista.tomarOpJug();
			break;
		case debePedirCarta:
			vista.debePedirCarta();
			break;
		case perdioIntentos:
			vista.perdioIntentos();
			break;
		}
	}
}
