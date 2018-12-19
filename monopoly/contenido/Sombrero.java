package monopoly.contenido;

import monopoly.plataforma.Operacion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Valor;

import java.util.ArrayList;

public final class Sombrero extends Avatar{

    private double historialDinero;
    private ArrayList<Propiedades> historialCompras;

    public String getTipo(){ return "Sombrero";}

    public Sombrero(Jugador jug, Tablero tablero){
        super(jug,tablero);
        this.historialCompras = new ArrayList<>();
        super.numTiradas = 3;
    }

    public void setNumTiradas(int tiradas){
        if(tiradas >= 0)
            super.numTiradas = tiradas;
    }

    public double getHistorialDinero() {
        return this.historialDinero;
    }

    public ArrayList<Propiedades> getHistorialCompras() {
        return this.historialCompras;
    }

    public void resetHistorialCompras(){}

    public void moverEnAvanzado(int valor){
        Operacion operacion = new Operacion(super.getTablero());
        if(valor > 4){
            this.moverZigZag(valor);
            this.getCasilla().accionCaer(this.getJugador(), valor, operacion);
        }else {
            super.numTiradas = 0;
            Juego.consola.imprimir("El sombrero ya ha acabado sus tiradas este turno");
        }
    }

    private void moverACasilla(int valor){
        this.getCasilla().quitarAvatar(this);
        this.setCasilla(Valor.casillas.get(valor));
        this.getCasilla().anhadirAvatar(this);
    }

    private void moverZigZag(int valor) {
        if (this.getCasilla().getPosicion() < 10){
            this.moverACasilla(11);
            if(valor % 2 == 0)
                this.moverACasilla(20 + ((valor - 1 + this.getCasilla().getPosicion() - 10) % 10));
            else
                this.moverACasilla(39 - ((valor - 1 + this.getCasilla().getPosicion() - 10) % 10));
        } else if (this.getCasilla().getPosicion() < 20) {
            if(valor % 2 == 0)
                this.moverACasilla(20 + ((valor + this.getCasilla().getPosicion() - 10) % 10));
            else
                this.moverACasilla(39 - ((valor + this.getCasilla().getPosicion() - 10) % 10));
        } else if (this.getCasilla().getPosicion() < 30) {
            this.moverACasilla(31);
            if(valor % 2 == 0)
                this.moverACasilla(30 + ((valor - 1 + this.getCasilla().getPosicion() - 30) % 10));
            else
                this.moverACasilla(20 - ((valor - 1+ this.getCasilla().getPosicion() - 30 ) % 10));
        } else {
            if(valor % 2 == 0)
                this.moverACasilla(30 + ((valor + this.getCasilla().getPosicion() - 30) % 10));
            else
                this.moverACasilla(20 - ((valor + this.getCasilla().getPosicion() - 30 ) % 10));
        }
    }

}
