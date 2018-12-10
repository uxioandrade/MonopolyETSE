package monopoly.contenido;

import monopoly.plataforma.Accion;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Valor;

import java.util.ArrayList;

public final class Esfinge extends Avatar{

    private double historialDinero; //Revisa
    private ArrayList<Propiedades> historialCompras;

    public Esfinge(Jugador jug, Tablero tablero){
        super(jug,tablero);
        this.historialCompras = new ArrayList<>();
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
            System.out.println("Hoteli");
        }
    }

    private void moverZigZag(int valor) {
        if (this.getCasilla().getPosicion() < 10){
            if(valor % 2 == 0)
                this.moverEnBasico(valor + this.getCasilla().getPosicion() % 10);
            else
                this.moverEnBasico(30 - (valor + this.getCasilla().getPosicion() % 10));
        } else if (this.getCasilla().getPosicion() < 20) {
            this.getCasilla().quitarAvatar(this);
            this.setCasilla(Valor.casillas.get(21));
            this.getCasilla().anhadirAvatar(this);
            this.moverZigZag(valor - 1);
        } else if (this.getCasilla().getPosicion() < 30) {
            if(valor % 2 == 0)
                this.moverEnBasico(20 + ((valor + this.getCasilla().getPosicion() - 20) % 10));
            else
                this.moverEnBasico(9 - ((valor + this.getCasilla().getPosicion() -20 ) % 10));
        } else {
            this.getCasilla().quitarAvatar(this);
            this.setCasilla(Valor.casillas.get(1));
            this.getCasilla().anhadirAvatar(this);
            this.moverZigZag(valor - 1);
        }
    }
}
