package monopoly.contenido;

import monopoly.plataforma.Valor;

public abstract class Comprables extends Casilla{

    private double precio;
    private boolean hipotecado;
    private Jugador propietario;

    public Comprables(String nombre, int posicion){
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

    public double getPrecio(){
        return this.precio;
    }

    public abstract double getAlquiler(int tirada);
}
