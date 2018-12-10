package monopoly.contenido;

public final class Piscina extends Edificios{

    public Piscina(double precio, Solar comprable){
        super(precio, comprable);
    }

    public void setPrecio(double precio){
        super.setPrecio(precio*0.4);
    }
}
