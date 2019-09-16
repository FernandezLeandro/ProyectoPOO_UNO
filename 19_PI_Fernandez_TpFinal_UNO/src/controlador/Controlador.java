package controlador;

import modelo.CambiosEnJuego;
import modelo.IObservador;
import modelo.Juego;
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





	@Override
	public void cambiosJuego(CambiosEnJuego cambio) {
		switch (cambio) {
		case menuPrinc : 
			vista.mostrarMenuPrincipal(juego.puntajeMAX);
		break;
		}
	}
}
