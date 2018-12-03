package monopoly.contenido;

import monopoly.plataforma.Valor;
import monopoly.plataforma.Accion;

public class Servicio extends Comprables{

    public Servicio(String nombre, int posicion) {
       super(nombre,posicion);
       super.setPrecio(0.75*Valor.getDineroVuelta());
    }

    public double getAlquiler(int tirada){
        return Valor.getDineroVuelta()*tirada/200;
    }

    public void pagarAlquiler(Jugador jugador, int tirada, Accion accion){
        if (jugador.getDinero() >= this.getAlquiler(tirada)){
            //Se resta el alquiler del jugador que ha caído en el servicio
            jugador.modificarDinero(-this.getAlquiler(tirada));
            jugador.modificarPagoAlquileres(this.getAlquiler(tirada));
            System.out.println("Se han pagado " + this.getAlquiler(tirada) + "€ de servicio.");
            //Se aumenta el dinero del propietario
            super.getPropietario().modificarDinero(this.getAlquiler(tirada));
            super.getPropietario().modificarCobroAlquileres(this.getAlquiler(tirada));
            super.sumarRentabilidad(this.getAlquiler(tirada));
        } else {
            System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
            if(accion.menuHipotecar(jugador,accion.getTablero(),this.getAlquiler(tirada)))
                this.pagarAlquiler(jugador,tirada,accion);
        }
    }
}
