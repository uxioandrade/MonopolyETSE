package monopoly.contenido;

import monopoly.plataforma.Juego;
import monopoly.plataforma.Valor;
import monopoly.plataforma.Operacion;

public final class Servicio extends Propiedades {

    public Servicio(String nombre, int posicion) {
       super(nombre,posicion);
       super.setPrecio(0.75*Valor.getDineroVuelta());
    }

    public double alquiler(int tirada){
        return Valor.getDineroVuelta()*tirada/200;
    }

    public void pagarAlquiler(Jugador jugador, int tirada, Operacion operacion){
        if (jugador.getDinero() >= this.alquiler(tirada)){
            //Se resta el alquiler del jugador que ha caído en el servicio
            jugador.modificarDinero(-this.alquiler(tirada));
            jugador.modificarPagoAlquileres(this.alquiler(tirada));
            if(jugador.getAvatar() instanceof Esfinge && jugador.getAvatar().getModoAvanzado())
                ((Esfinge)jugador.getAvatar()).setHistorialAlquileres(this.alquiler(tirada));
            Juego.consola.imprimir("Se han pagado " + this.alquiler(tirada) + "€ de servicio.");
            //Se aumenta el dinero del propietario
            super.getPropietario().modificarDinero(this.alquiler(tirada));
            super.getPropietario().modificarCobroAlquileres(this.alquiler(tirada));
            super.sumarRentabilidad(this.alquiler(tirada));
        } else {
            Juego.consola.imprimir("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
            if(operacion.menuHipotecar(jugador, operacion.getTablero(),this.alquiler(tirada)))
                this.pagarAlquiler(jugador,tirada, operacion);
        }
    }
    @Override
    public String toString(){
        String aux =super.toString().substring(0,super.toString().length()-2);
        aux += "Tipo: " + "Servicios" + "\n" +
                "Precio: " + ((Servicio) this).getPrecio() + "\n" +
                "Uso Servicio: " + ((Servicio) this).alquiler(1)+" x suma de los dados" + "\n" +
                "Hipoteca: " + ((Servicio) this).getHipoteca() + "\n";
        if(super.getPropietario().getNombre().equals("Banca"))
            aux += "Propietario: " + super.getPropietario().getNombre() + "\n";
        if(super.getHipotecado())
            aux += "Servicio hipotecado, paga " + 1.1*super.getHipoteca() + " para deshipotecar" + "\n";
        aux+="}\n";
        return aux;
    }
}
