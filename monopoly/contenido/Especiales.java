package monopoly.contenido;

import monopoly.plataforma.Accion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Valor;

public final class Especiales extends Casilla{

    public Especiales(String nombre, int posicion) {
        super(nombre, posicion);
    }

    public void accionCaer(Jugador jugador, int tirada, Accion accion){
        if(super.getPosicion() == 20) {
            jugador.modificarDinero(Valor.getDineroAcumulado());
            jugador.modificarPremiosInversionesOBote(Valor.getDineroAcumulado());
            Juego.consola.imprimir("El jugador " + jugador.getNombre() + "recibe " + Valor.getDineroAcumulado() + "€, el bote de la banca");
            Valor.setDineroAcumulado(0);
        }else if(super.getPosicion() == 30) {
            accion.irCarcel(jugador);
        }
    }
}