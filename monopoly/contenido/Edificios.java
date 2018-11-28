package monopoly.contenido;

public class Edificios{

    private Solar comprable;
    private double precio;
    private double modificadorAlquiler;

    public Edificios(double precio, Solar comprable){
        this.precio = precio;
        this.comprable = comprable;
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



}
