package monopoly.contenido;

import monopoly.plataforma.Tablero;

public class Esfinge extends Avatar{

    public Esfinge(Jugador jug, Tablero tablero){
        super(jug,tablero);
    }

    public void moverCasilla(int valor){
        if(super.getModoAvanzado()){
            super.moverEnBasico(valor); //El movimiento avanzado de coche se implementará en la próxima entrega
        }else
            super.moverEnBasico(valor);
    }

}
