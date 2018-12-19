package monopoly.contenido;

public final class Hotel extends Edificios{

    public Hotel(double precio, Solar comprable){
        super(precio, comprable);
    }

    public void setPrecio(double precio){
        super.setPrecio(precio*0.6);
    }

    public String getNombre(){return "Hotel-"+super.nombre;}
}
