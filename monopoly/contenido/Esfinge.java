package monopoly.contenido;

import monopoly.plataforma.Operacion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Valor;

import java.util.ArrayList;

public final class Esfinge extends Avatar{

    private double historialAlquileres; //Revisa
    private double historialCompras;
    private ArrayList<Propiedades> historialCompradas;
    private double historialSalida;
    private int numTiradas;

    public Esfinge(Jugador jug, Tablero tablero){
        super(jug,tablero);
        super.numTiradas = 3;
        this.historialCompradas= new ArrayList<>();
    }

    public double getHistorialCompras() {
        return this.historialCompras;
    }
    public void setHistorialAlquileres(double alquiler){
        if(alquiler>0) this.historialAlquileres=alquiler;
    }

    public void resetHistorial(){
        this.historialAlquileres=0; //Revisa
        this.historialCompras=0;
        this.historialSalida=0;
        this.historialCompradas.clear();
    }

    public void moverEnAvanzado(int valor){
        Operacion operacion = new Operacion(super.getTablero());
        if(valor > 4){
            this.resetHistorial();
            this.moverZigZag(valor);
            this.getCasilla().accionCaer(this.getJugador(), valor, operacion);
        }else {
            if(this.numTiradas==3){
                this.deshacerHistorial();
            }
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

    private void deshacerHistorial(){
        Juego.consola.imprimir("Se deshrán las acciones realizadas en el turno anterior:");
        if(this.historialAlquileres>0) {
            super.getJugador().modificarDinero(this.historialAlquileres);
            super.getJugador().modificarPagoAlquileres(-this.historialAlquileres);
            Juego.consola.imprimir("Se ha deshecho la accion pagar alquiler.");
            Juego.consola.imprimir("Recuperas "+this.historialAlquileres+ ", tu fortuna aumenta a "+super.getJugador().getDinero());
        }
        if(this.historialSalida>0) {
            super.getJugador().modificarDinero(-this.historialSalida);
            super.getJugador().modificarPasarPorCasilla(-this.historialSalida);
            Juego.consola.imprimir("Se ha deshecho la accion pasar por la casilla de salida.");
            Juego.consola.imprimir("Pierdes "+this.historialSalida+ ", tu fortuna se reduce a "+super.getJugador().getDinero());
        }
        /*
        DESHACER COMPRAS O EDIFICACIONES SI LO PONE PENIN EN EL FAQ
         */
        resetHistorial();
    }
}
