package monopoly.contenido;

import monopoly.plataforma.Valor;

public abstract class Edificios{

    protected Solar comprable;
    protected double precio;
    protected double modificadorAlquiler;
    protected String nombre;

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

    public abstract String getNombre();

}
