package monopoly.contenido.casillas.propiedades;

import monopoly.contenido.Jugador;
import monopoly.contenido.avatares.Esfinge;
import monopoly.contenido.avatares.Sombrero;
import monopoly.contenido.casillas.Casilla;
import monopoly.excepciones.dinero.ExcepcionDineroDeuda;
import monopoly.plataforma.Operacion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Valor;

public final class Transporte extends Propiedades { //Las clases hoja de una jerarquía deberían ser finales

    private static final double MULTIPLICADOR_ALQUILER_TRANSPORTE = 0.25;

    public Transporte(String nombre, int posicion) {
        super(nombre,posicion);
        super.setPrecio(Valor.getDineroVuelta());
    }

    public double alquiler(int tirada){
        if(super.getPropietario().getNombre().contains("Banca")) return Valor.getDineroVuelta()* MULTIPLICADOR_ALQUILER_TRANSPORTE;
        int count = 0;
        for(Casilla c : super.getPropietario().getPropiedades()){
            if(c instanceof Transporte)
                count++;
        }
            return Valor.getDineroVuelta()* MULTIPLICADOR_ALQUILER_TRANSPORTE *count;
    }

    public void pagarAlquiler(Jugador jugador, int tirada, Operacion operacion) throws ExcepcionDineroDeuda {
        if (jugador.getDinero() >= this.alquiler(tirada)){
            //Se resta el alquiler del jugador que ha caído en el transporte
            jugador.modificarDinero(-this.alquiler(tirada));
            jugador.modificarPagoAlquileres(this.alquiler(tirada));
            if(jugador.getAvatar() instanceof Esfinge && jugador.getAvatar().getModoAvanzado())
                ((Esfinge)jugador.getAvatar()).setHistorialAlquileres(this.alquiler(tirada));
            if(jugador.getAvatar() instanceof Sombrero && jugador.getAvatar().getModoAvanzado())
                ((Sombrero)jugador.getAvatar()).setHistorialAlquileres(this.alquiler(tirada));
            Juego.consola.imprimir("Se han pagado " + this.alquiler(tirada) + "€ de transporte.");
            //Se aumenta el dinero del propietario
            super.getPropietario().modificarDinero(this.alquiler(tirada));
            super.getPropietario().modificarCobroAlquileres(this.alquiler(tirada));
            super.sumarRentabilidad(this.alquiler(tirada));
        } else {
           throw new ExcepcionDineroDeuda("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
        }
    }
    @Override
    public String toString(){
        String aux =super.toString().substring(0,super.toString().length()-2);
        aux += "Tipo: " + "Transporte" + "\n" +
                "Precio: " + ((Transporte) this).getPrecio() + "€\n" +
                "Uso Transporte Actual: " + ((Transporte) this).alquiler(1) + "€\n" +
                "Uso Transporte Básico: " + Valor.getDineroVuelta() * 0.25 + "€\n" +
                "Hipoteca: " + ((Transporte) this).getHipoteca() + "€\n";
        if(super.getPropietario().getNombre().equals("Banca"))
            aux += "Propietario: " + super.getPropietario().getNombre() + "\n";
        if(super.getHipotecado())
            aux += "Transporte hipotecado, paga " + 1.1*super.getHipoteca() + " para deshipotecar" + "\n";
        aux+="}\n";
        return aux;
    }
}
