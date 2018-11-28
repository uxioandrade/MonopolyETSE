package monopoly.contenido;

import monopoly.plataforma.Accion;

public class VeCarcel extends Casilla {

    public VeCarcel(String nombre, int posicion) {
        super(nombre, posicion);
    }

    public void accionCaer(Jugador jugador, int tirada, Accion accion){
        accion.irCarcel(jugador);
    }
}
