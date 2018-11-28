package monopoly.contenido;

import monopoly.plataforma.Tablero;
import monopoly.plataforma.Accion;

public class Coche extends Avatar{

    private int numTiradas;

    public Coche(Jugador jug, Tablero tablero){
        super(jug,tablero);
        numTiradas = 1;
    }

    public void moverCasilla(int valor){
        Accion accion = new Accion(this.getTablero());
        if(super.getModoAvanzado()){
            if(valor > 4){

                this.numTiradas = 3;
            }else{
                this.retrocederCasillas(valor);
                this.getCasilla().accionCaer(this.getJugador(),valor,accion);
                numTiradas = -2;
            }
        }else
            super.moverNormal(valor);
    }

}
