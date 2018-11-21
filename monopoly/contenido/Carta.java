package monopoly.contenido;

import monopoly.plataforma.Tablero;

public class Carta{

    private String descripcion;

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public void accionCarta(Jugador jugador, Tablero tablero){}
}
