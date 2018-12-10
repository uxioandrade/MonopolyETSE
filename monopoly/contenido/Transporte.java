package monopoly.contenido;

import monopoly.plataforma.Accion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Valor;

public final class Transporte extends Propiedades {

    public Transporte(String nombre, int posicion) {
        super(nombre,posicion);
        super.setPrecio(Valor.getDineroVuelta());
    }

    public double alquiler(int tirada){
        if(super.getPropietario().getNombre().contains("Banca")) return Valor.getDineroVuelta()*0.25;
        int count = 0;
        for(Casilla c : super.getPropietario().getPropiedades()){
            if(c instanceof Transporte)
                count++;
        }
            return Valor.getDineroVuelta()*0.25*count;
    }

    public void pagarAlquiler(Jugador jugador, int tirada, Accion accion){
        if (jugador.getDinero() >= this.alquiler(tirada)){
            //Se resta el alquiler del jugador que ha caído en el transporte
            jugador.modificarDinero(-this.alquiler(tirada));
            jugador.modificarPagoAlquileres(this.alquiler(tirada));
            Juego.consola.imprimir("Se han pagado " + this.alquiler(tirada) + "€ de transporte.");
            //Se aumenta el dinero del propietario
            super.getPropietario().modificarDinero(this.alquiler(tirada));
            super.getPropietario().modificarCobroAlquileres(this.alquiler(tirada));
            super.sumarRentabilidad(this.alquiler(tirada));
        } else {
            Juego.consola.imprimir("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
            if(accion.menuHipotecar(jugador,accion.getTablero(),this.alquiler(tirada)))
                this.pagarAlquiler(jugador,tirada,accion);
        }
    }
}
