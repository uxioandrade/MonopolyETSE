package monopoly.contenido;

import monopoly.plataforma.Juego;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Accion;
import monopoly.plataforma.Valor;

public final class Coche extends Avatar{

    private boolean poderComprar;

    public Coche(Jugador jug, Tablero tablero){
        super(jug,tablero);
        super.numTiradas = 3;
        this.poderComprar = true;
    }

    public void moverEnAvanzado(int valor){
        Accion accion = new Accion(this.getTablero());
        if(super.numTiradas > 0) {
            if (valor > 4) {
                if (super.numTiradas == 1) {
                    super.numTiradas = 4;
                    Juego.consola.imprimir("El coche aún puede realizar " + (super.numTiradas - 1) + " tiradas este turno");
                }else if (super.numTiradas == 2){
                    super.numTiradas = 0;
                    Juego.consola.imprimir("El coche ha acabado sus tiradas en este turno");
                }else{
                    super.numTiradas--;
                    Juego.consola.imprimir("El coche aún puede realizar " + (super.numTiradas - 1) + " tiradas este turno");
                }
                Juego.consola.imprimir("El avatar " + this.getId() + " avanza " + valor + " posiciones, desde " + this.getCasilla().getNombre() + " hasta " + Valor.casillas.get((this.getCasilla().getPosicion() + valor) % 40).getNombre());
                this.moverEnBasico(valor);
                this.getCasilla().accionCaer(this.getJugador(), valor, accion);
            } else {
                if (super.numTiradas == 1) {
                    this.retrocederCasillas(valor);
                    this.getCasilla().accionCaer(this.getJugador(), valor, accion);
                    super.numTiradas = -2;
                    Juego.consola.imprimir("El coche ha obtenido un valor menor o igual de 4, retrocede " + valor + " casillas y estará 2 turnos sin poder tirar");
                } else {
                    super.numTiradas = 0;
                    Juego.consola.imprimir("El valor obtenido ha sido menor de 4, el coche no puede tirar más veces");
                }
            }
        }else
            Juego.consola.imprimir(this.getJugador().getNombre() + " sigue sin poder tirar");
    }

    public boolean getPoderComprar(){
        return this.poderComprar;
    }

    public void setPoderComprar(boolean valor){
        this.poderComprar = valor;
    }

}
