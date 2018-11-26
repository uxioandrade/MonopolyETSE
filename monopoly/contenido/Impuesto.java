package monopoly.contenido;

public class Impuesto extends Casilla{

    private double apagar;

    public Impuesto(String nombre, int posicion){
        super(nombre,posicion);
    }
    public void setApagar(double apagar){
        if(apagar>0)
            this.apagar=apagar;
    }

    public double getApagar() {
        return apagar;
    }
}
