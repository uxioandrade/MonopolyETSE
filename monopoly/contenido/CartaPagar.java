package monopoly.contenido;


import monopoly.plataforma.Accion;
import monopoly.plataforma.Tablero;
import sun.tools.jconsole.Tab;

import java.util.Iterator;

public class CartaPagar extends Carta{

    private double cantidad; //Positivo => Pagar , Negativo => Cobrar
    private boolean banca; //True => Banca, False => Todos los jugadores

    public CartaPagar(double cantidad, boolean banca, String descripcion){
        this.cantidad = cantidad;
        this.banca = banca;
        super.setDescripcion(descripcion);
    }

    public double getCantidad(Jugador jugador, Tablero tablero){
        if(!banca) return this.cantidad*(tablero.getAvatares().size() - 1);
        if(this.cantidad == 0)
            return jugador.getPropiedades().size()*2;//Hay que cambiarlo
        else
            return this.cantidad;
    }

    private void cobrarAccion(Jugador jugador, Tablero tablero, double cantidad) {
        jugador.modificarDinero(cantidad * -1);
        if (!banca) {
            Iterator<Jugador> itJug = tablero.getJugadores().values().iterator();
            while (itJug.hasNext()) {
                Jugador jug = itJug.next();
                if (!jug.equals(jugador)) {
                    jug.modificarDinero(-1 * this.cantidad);
                }
            }
        }
    }

    public void accionCarta(Jugador jugador, Tablero tablero){
        System.out.println(super.getDescripcion());
        double cantidadTotal = this.getCantidad(jugador,tablero);
        Accion accion = new Accion(tablero);
        if(jugador.getDinero() >= cantidadTotal){
            cobrarAccion(jugador,tablero,cantidadTotal);
        }else{
            System.out.println(jugador.getNombre() + " no tiene dinero suficiente para pagar.");
            if(accion.menuHipotecar(jugador,tablero,cantidadTotal))
                cobrarAccion(jugador,tablero,cantidadTotal);
        }
    }
}
