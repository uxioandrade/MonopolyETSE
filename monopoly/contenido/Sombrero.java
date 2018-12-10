package monopoly.contenido;

import monopoly.plataforma.Accion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Valor;

import java.util.ArrayList;

public final class Sombrero extends Avatar{

    private double historialDinero;
    private ArrayList<Propiedades> historialCompras;

    public Sombrero(Jugador jug, Tablero tablero){
        super(jug,tablero);
        this.historialCompras = new ArrayList<>();
    }

    public double getHistorialDinero() {
        return this.historialDinero;
    }

    public ArrayList<Propiedades> getHistorialCompras() {
        return this.historialCompras;
    }

    public void resetHistorialCompras(){}

    public void moverEnAvanzado(int valor){
        Accion accion = new Accion(super.getTablero());
        if(valor > 4){
            this.moverZigZag(valor);
            this.getCasilla().accionCaer(this.getJugador(), valor, accion);
        }else {
            Juego.consola.imprimir("hola");
        }
    }

    private void moverZigZag(int valor) {
        if (this.getCasilla().getPosicion() < 10){
            this.getCasilla().quitarAvatar(this);
            this.setCasilla(Valor.casillas.get(11));
            this.getCasilla().anhadirAvatar(this);
            this.moverZigZag(valor - 1);
        } else if (this.getCasilla().getPosicion() < 20) {
            if(valor % 2 == 0)
                this.moverEnBasico(20 + ((valor + this.getCasilla().getPosicion() - 10) % 10));
            else
                this.moverEnBasico(39 - ((valor + this.getCasilla().getPosicion() - 10) % 10));
        } else if (this.getCasilla().getPosicion() < 30) {
            this.getCasilla().quitarAvatar(this);
            this.setCasilla(Valor.casillas.get(31));
            this.getCasilla().anhadirAvatar(this);
            this.moverZigZag(valor - 1);
        } else {
            if(valor % 2 == 0)
                this.moverEnBasico(30 + ((valor + this.getCasilla().getPosicion() - 30) % 10));
            else
                this.moverEnBasico(19 - ((valor + this.getCasilla().getPosicion() - 30 ) % 10));
        }
    }

}
