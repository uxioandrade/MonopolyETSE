package monopoly.contenido;

import monopoly.plataforma.Juego;
import monopoly.plataforma.Operacion;
import monopoly.plataforma.Valor;

public class Accion extends Casilla {
    public Accion(String nombre, int posicion){
        super(nombre,posicion);
    }
    public void accionCaer(Jugador jugador, int tirada,Operacion operacion){
        jugador.modificarDinero(Valor.getDineroAcumulado());
        jugador.modificarPremiosInversionesOBote(Valor.getDineroAcumulado());
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
