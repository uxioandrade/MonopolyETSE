package monopoly.contenido;

import monopoly.plataforma.Valor;

public class Edificios{

    private Solar comprable;
    private double precio;
    private double modificadorAlquiler;
    private String nombre;

    public Edificios(double precio, Solar comprable){
        this.precio = precio;
        this.comprable = comprable;
        this.nombre = ""+Valor.getEdificios();
        Valor.incrementarEdificios();
    }

    public Solar getComprable(){
        return this.comprable;
    }

    //Los edificios están asignados a una comprable en concreto, no se puede cambiar un edificio de comprable

    public double getPrecio(){
        return this.precio;
    }

    public void setPrecio(double precio){
        if(precio > 0)
            this.precio = precio;
    }

    public String getNombre(){
        if (this instanceof Casa) return "Casa-"+this.nombre;
        if (this instanceof Hotel) return "Hotel-"+this.nombre;
        if (this instanceof Piscina) return "Piscina-"+this.nombre;
        return "Pista de Deporte-"+this.nombre;
    }

}
