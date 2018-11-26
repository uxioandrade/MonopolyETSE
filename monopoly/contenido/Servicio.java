package monopoly.contenido;

import monopoly.plataforma.Valor;

public class Servicio extends Comprables{

    public Servicio(String nombre, int posicion) {
       super(nombre,posicion);
       super.setPrecio(0.75*Valor.getDineroVuelta());
    }

    public double getAlquiler(int tirada){
        return Valor.getDineroVuelta()*tirada/200;
    }

}
