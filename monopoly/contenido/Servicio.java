package monopoly.contenido;

import monopoly.plataforma.Valor;
import monopoly.plataforma.Accion;

public class Servicio extends Propiedades {

    public Servicio(String nombre, int posicion) {
       super(nombre,posicion);
       super.setPrecio(0.75*Valor.getDineroVuelta());
    }

    public double alquiler(int tirada){
        return Valor.getDineroVuelta()*tirada/200;
    }

    public void pagarAlquiler(Jugador jugador, int tirada, Accion accion){
        if (jugador.getDinero() >= this.alquiler(tirada)){
            //Se resta el alquiler del jugador que ha caído en el servicio
            jugador.modificarDinero(-this.alquiler(tirada));
            jugador.modificarPagoAlquileres(this.alquiler(tirada));
            System.out.println("Se han pagado " + this.alquiler(tirada) + "€ de servicio.");
            //Se aumenta el dinero del propietario
            super.getPropietario().modificarDinero(this.alquiler(tirada));
            super.getPropietario().modificarCobroAlquileres(this.alquiler(tirada));
            super.sumarRentabilidad(this.alquiler(tirada));
        } else {
            System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
            if(accion.menuHipotecar(jugador,accion.getTablero(),this.alquiler(tirada)))
                this.pagarAlquiler(jugador,tirada,accion);
        }
    }
}
