package monopoly.contenido;

import monopoly.plataforma.Accion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Valor;

import java.util.ArrayList;

public final class Esfinge extends Avatar{

    private double historialDinero; //Revisa
    private ArrayList<Propiedades> historialCompras;
    private int numTiradas;

    public Esfinge(Jugador jug, Tablero tablero){
        super(jug,tablero);
        this.historialCompras = new ArrayList<>();
        super.numTiradas = 3;
    }

    public double getHistorialDinero() {
        return this.historialDinero;
    }

    public ArrayList<Propiedades> getHistorialCompras() {
        return this.historialCompras;
    }

    public void resetHistorialCompras(){
        this.historialDinero = 0;
        this.historialCompras=new ArrayList<>();
    }

    public void moverEnAvanzado(int valor){
        Accion accion = new Accion(super.getTablero());
        if(valor > 4){
            this.moverZigZag(valor);
            this.getCasilla().accionCaer(this.getJugador(), valor, accion);
        }else {
            super.numTiradas = 0;
            Juego.consola.imprimir("La esfinge ya ha acabado sus tiradas este turno");
        }
    }

    private void moverACasilla(int valor){
        this.getCasilla().quitarAvatar(this);
        this.setCasilla(Valor.casillas.get(valor));
        this.getCasilla().anhadirAvatar(this);
    }



    private void moverZigZag(int valor) {
        if (this.getCasilla().getPosicion() < 10){
            if(valor % 2 == 0)
                this.moverACasilla((valor + this.getCasilla().getPosicion()) % 10);
            else
                this.moverACasilla(30 - ((valor + this.getCasilla().getPosicion()) % 10));
        } else if (this.getCasilla().getPosicion() < 20) {
            this.moverACasilla(21);
            if(valor % 2 == 0)
                this.moverACasilla(20 + ((valor - 1 + this.getCasilla().getPosicion() - 20) % 10));
            else
                this.moverACasilla(10 - ((valor - 1 + this.getCasilla().getPosicion() -20 ) % 10));
        } else if (this.getCasilla().getPosicion() < 30) {
            if(valor % 2 == 0)
                this.moverACasilla(20 + ((valor + this.getCasilla().getPosicion() - 20) % 10));
            else
                this.moverACasilla(10 - ((valor + this.getCasilla().getPosicion() -20 ) % 10));
        } else {
            this.moverACasilla(1);
            if(valor % 2 == 0)
                this.moverACasilla((valor - 1 + this.getCasilla().getPosicion()) % 10);
            else
                this.moverACasilla(30 - ((valor - 1 + this.getCasilla().getPosicion()) % 10));
        }
    }
}
