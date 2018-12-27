package monopoly.contenido;

import monopoly.excepciones.ExcepcionDineroDeuda;
import monopoly.excepciones.ExcepcionNumeroPartesComando;
import monopoly.excepciones.ExcepcionRestriccionEdificar;
import monopoly.excepciones.ExcepcionRestriccionHipotecar;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Operacion;
import monopoly.plataforma.Valor;

public class Accion extends Casilla {
    public Accion(String nombre, int posicion){
        super(nombre,posicion);
    }
    public void accionCaer(Jugador jugador, int tirada,Operacion operacion) throws ExcepcionRestriccionHipotecar, ExcepcionNumeroPartesComando, ExcepcionDineroDeuda, ExcepcionRestriccionEdificar {
        jugador.modificarDinero(Valor.getDineroAcumulado());
        jugador.modificarPremiosInversionesOBote(Valor.getDineroAcumulado());
        if(jugador.getAvatar() instanceof Esfinge && jugador.getAvatar().getModoAvanzado())
            ((Esfinge)jugador.getAvatar()).modificarHistorialPremios(Valor.getDineroAcumulado());
        if(jugador.getAvatar() instanceof Sombrero && jugador.getAvatar().getModoAvanzado())
            ((Sombrero)jugador.getAvatar()).modificarHistorialPremios(Valor.getDineroAcumulado());
        Juego.consola.imprimir("El jugador " + jugador.getNombre() + "recibe " + Valor.getDineroAcumulado() + "€, el bote de la banca");
        Valor.setDineroAcumulado(0);
    }
    @Override
    public String toString(){
        if(this instanceof CasillasCarta) return super.toString();
        String aux =super.toString().substring(0,super.toString().length()-2);
        aux +="Bote: "+Valor.getDineroAcumulado()+"\n"+
                "Jugadores: ";
        for (Avatar a: avatares){
            aux+="["+a.getJugador().getNombre()+"] ";
        }
        aux+="\n";
        aux+="}\n";
        return aux;
    }

}
