package monopoly.contenido;

import monopoly.excepciones.ExcepcionDineroDeuda;
import monopoly.excepciones.ExcepcionNumeroPartesComando;
import monopoly.excepciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.ExcepcionRestriccionHipotecar;
import monopoly.plataforma.Tablero;

public abstract class Carta{
    public void Carta(String descripcion){
        this.descripcion=descripcion;
    }
    protected String descripcion;

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public abstract void  accionCarta(Jugador jugador, Tablero tablero) throws ExcepcionRestriccionHipotecar, ExcepcionNumeroPartesComando, ExcepcionDineroDeuda,ExcepcionRestriccionEdificar;
}
