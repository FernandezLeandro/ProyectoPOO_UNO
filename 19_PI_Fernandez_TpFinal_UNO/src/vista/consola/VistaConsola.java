package vista.consola;

import java.util.ArrayList;

import controlador.Controlador;
import modelo.EntradaConsola;
import modelo.Juego;
import modelo.Jugador;
import modelo.cartas.Carta;
import modelo.cartas.eColor;
import modelo.cartas.eTipo;
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

	@Override
	public void agregarJugador() {
		System.out.println("-----------------------------------------");
		System.out.print(" Agregar Jugador -> Ingrese nombre del jugador: ");
		String jugador = EntradaConsola.tomarString();
		while (jugador.length() == 0) {
			System.out.print("Se necesita nombre para jugador, ingrese uno: ");	
			jugador = EntradaConsola.tomarString();					
		} 
			controlador.agregarJugador(jugador);				
			System.out.println(" Jugador: " + jugador +", añadido con exito!");	
		
	}

	@Override
	public void jugCompletos() {
		System.out.println("ERROR - No se pudo agregar jugador, la lista de jugadores está llena.");
	}

	@Override
	public void sinJugadores() {
		System.out.println("ERROR - No hay jugadores que borrar.");
	}

	@Override
	public void mostrarJugadores(ArrayList <Jugador> listaJugadores) {
		System.out.println("Lista de jugadores:");
		if (listaJugadores.isEmpty()) {
			System.out.println("No hay jugadores actualmente.");
		} else {
			int i = 0;
			for (Jugador j : listaJugadores) {
				System.out.println(++i + " - " + j);
			}
		}
	}

	@Override
	public void tomarOpcion() {
		char opcion;
		opcion = EntradaConsola.tomarOpcion(); // A la espera de la eleccion de una opcion
		controlador.setearOpcion(opcion);
	}
	
	
	
	@Override
	public void eliminarJugador(ArrayList <Jugador> listaJugadores) {
		System.out.print("Ingrese el numero de jugador a eliminar: ");
		int numero = tomarInt ();
		//int numero = EntradaConsola.tomarInt();
		if (numero <= listaJugadores.size()) {
			controlador.eliminarJug(numero-1);
		} else
			System.out.println("El numero ingresado es invalido");
	}

	@Override
	public int tomarInt() {
		int numero = EntradaConsola.tomarInt();
		return numero;
	}

	@Override
	public void modificarPtosMax(int minimo) {
		System.out.print("Ingrese el puntaje nuevo: ");
		int puntaje = tomarInt ();
		while (puntaje < minimo) {
				System.out.print("El minimo puntaje es de " + Integer.toString(minimo) +" ptos, ingrese nuevamente: ");
				puntaje = EntradaConsola.tomarInt();
		}
		controlador.actualizarPtosMaximos(puntaje);
	}

	@Override
	public void mostrarComienzaRonda() {
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("*---------- JUEGO EN CURSO -------------*");
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		//System.out.println(" Carta en juego: ");

	}

	@Override
	public void mostrarUltimoDescarte(Carta carta) {
		System.out.println(" Carta en juego: ");
		System.out.println(carta.toString());
	}

	@Override
	public void cambioEnTurno(Jugador jugador, eTipo tipo) {
		switch (tipo) {
		case COMUN:
			System.out.println("COMUUUUUUUUUUUUUUUUUUUUUUUN");
			break;
		case ROBA_2:
			System.out.println("-----------------------------------------");
			System.out.println("El jugador " + jugador.getNombre() + ", agarra 2 cartas y pierde el turno");
			System.out.println("-----------------------------------------");
			break;
		case PIERDE_TURNO:
			System.out.println("-----------------------------------------");
			System.out.println("El jugador " + jugador.getNombre() + ", es salteado, pierde el turno.");
			System.out.println("-----------------------------------------");
			break;
			
		case CAMBIO_SENTIDO:
			System.out.println("-----------------------------------------");
			System.out.println("SE CAMBIA EL SENTIDO DE LA RONDA.");
			System.out.println("-----------------------------------------");
			break;
			
		case COMODIN_ROBA_4:
			System.out.println("El jugador " + jugador.getNombre() + ", agarra 4 cartas y pierde el turno");
			nuevoColor (controlador.obtenerNewColor());
			break;
		case COMODIN:
			System.out.println("-----------------------------------------");
			System.out.println("El nuevo color en juego es: " + controlador.mostrarNuevoColor());
			System.out.println("-----------------------------------------");
			break;
		default:
			System.out.println("ERROR");
			break;
		}
		
	}

	@Override
	public void nuevoColor(eColor nuevoColor) {
		System.out.println("El nuevo color en juego es: " + nuevoColor);
	}

	@Override
	public void faltanJugadores() {
		System.out.println("-----------------------------------------");
		System.out.println("Se necesitan minimo dos jugadores para comenzar.");
		System.out.println("-----------------------------------------");
	}

	@Override
	public void mostrarAyuda(String st) {
		System.out.println(st);
	}

	@Override
	public void finalizar() {
		System.out.println("XXXXX Ejecución finalizada XXXXX");
	}

	@Override
	public void opcionInvalida() {
		System.out.println("ERROR: Ingrese una opción válida.");
	}

	@Override
	public void mostrarJugadorEnTurno(Jugador jugador) {
		System.out.println("-----------------------------------------");
		System.out.println(" Jugador en turno: " + jugador.getNombre());
		System.out.println("-----------------------------------------");
	}

	@Override
	public void mostrarCartasJug(ArrayList <Carta> cartas) {
		System.out.println("-----------------------------------------");
		System.out.println("Tus cartas son: ");
		System.out.println("-----------------------------------------");
		int i = 1; // Valor con que se lo podra manipular en consola
		for (Carta c : cartas) {// Muestra la lista de cartas
			if (c.getTipo() == eTipo.COMUN) {
				System.out.println(i++ + " - " + c.mostrarComun()); 
			} else
				System.out.println(i++ + " - " + c.mostrarEspecial()); 
		}	
		System.out.println("-----------------------------------------");
	}

	@Override
	public void opcionesJug(boolean tomoCarta) {
		System.out.println("-----------------------------------------");
		System.out.println("  0 - Pasar Turno -----------------------");
		if (!tomoCarta) { // Solo permite tomar carta si aun no ha tomado ninguna
			System.out.println(" 99 - Tomar Carta -----------------------");
		}
		System.out.println("-----------------------------------------");
	}

	@Override
	public void tomarOpJug() {
		System.out.print(" Seleccione la opcion a realizar: ");
		int num = tomarInt ();
		controlador.setearNumOpcion(num);
	}

	@Override
	public void debePedirCarta() {
		System.out.println("Antes de pasar debes tomar aunque sea una carta, se te ha dado una.");
	}

	@Override
	public void perdioIntentos() {
		System.out.println("Has perdido tus 3 intentos, se te dara una carta extra.");
	}

	@Override
	public void elegirColor() {
		System.out.println("-----------------------------------------");
		System.out.println("¿A qué color desea cambiar?");
		System.out.println("-----------------------------------------");
		System.out.println(" 1 - AMARILLO");
		System.out.println(" 2 - AZUL");
		System.out.println(" 3 - ROJO");
		System.out.println(" 4 - VERDE");
		System.out.println("-----------------------------------------");
	}

	@Override
	public void tomarColor() {
		tomarOpJug();
	}
	
	
}
