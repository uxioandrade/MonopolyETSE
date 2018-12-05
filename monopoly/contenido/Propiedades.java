package monopoly.contenido;

import monopoly.plataforma.Accion;

public abstract class Propiedades extends Casilla {

    private double precio;
    private boolean hipotecado;
    private Jugador propietario;
    private double rentabilidad;

    public Propiedades(String nombre, int posicion){
        super(nombre,posicion);
    }

    public Jugador getPropietario(){
        return this.propietario;
}
    public void setPropietario(Jugador propietario){
        if(propietario != null) this.propietario=propietario;
    }

    public double getHipoteca(){
        return this.precio*0.5;
    }

    public boolean getHipotecado(){ return this.hipotecado; }

    public void setHipotecado(boolean hipotecado){
        this.hipotecado = hipotecado;
    }

    public void setPrecio(double precio){
        if(precio > 0.00000)
            this.precio = precio;
    }

    public double getRentabilidad(){
        return this.rentabilidad;
    }

    public void sumarRentabilidad(double valor){
        if(valor > 0)
            this.rentabilidad += valor;
    }

    public double getPrecio(){
        return this.precio;
    }

    public abstract double alquiler(int tirada);

    public abstract void pagarAlquiler(Jugador jugador, int tirada, Accion accion);

    public void accionCaer(Jugador jugador, int tirada, Accion accion) {
        if (!propietario.equals(accion.getTablero().getBanca()) && !propietario.equals(jugador) && !this.hipotecado)
            this.pagarAlquiler(jugador, tirada, accion);
    }

    public boolean perteneceAJugador(Jugador jugador){
        return this.propietario.equals(jugador);
    }
}
