package monopoly.contenido;

public final class PistaDeporte extends Edificios{

    public PistaDeporte(double precio, Solar comprable){
        super(precio, comprable);
    }

    public void setPrecio(double precio){
        super.setPrecio(precio*0.6);
    }
}
