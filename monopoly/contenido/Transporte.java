package monopoly.contenido;

import monopoly.plataforma.Accion;
import monopoly.plataforma.Valor;

public class Transporte extends Comprables{

    public Transporte(String nombre, int posicion) {
        super(nombre,posicion);
        super.setPrecio(Valor.getDineroVuelta());
    }

    public double getAlquiler(int tirada){
        if(super.getPropietario().getNombre().contains("Banca")) return Valor.getDineroVuelta()*0.25;
        int count = 0;
        for(Casilla c : super.getPropietario().getPropiedades()){
            if(c instanceof Transporte)
                count++;
        }
            return Valor.getDineroVuelta()*0.25*count;
    }

    public void pagarAlquiler(Jugador jugador, int tirada, Accion accion){
        if (jugador.getDinero() >= this.getAlquiler(tirada)){
            //Se resta el alquiler del jugador que ha caído en el transporte
            jugador.modificarDinero(-this.getAlquiler(tirada));
            jugador.modificarPagoAlquileres(this.getAlquiler(tirada));
            System.out.println("Se han pagado " + this.getAlquiler(tirada) + "€ de transporte.");
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
