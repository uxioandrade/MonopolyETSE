package monopoly.contenido;

import monopoly.plataforma.Tablero;

public class Pelota extends Avatar{

    public Pelota(Jugador jug, Tablero tablero){
        super(jug,tablero);
    }

    public void moverCasilla(int valor){
        if(super.getModoAvanzado()){
            super.moverNormal(valor); //El movimiento avanzado de coche se implementará en la próxima entrega
        }else
            super.moverNormal(valor);
    }
}
