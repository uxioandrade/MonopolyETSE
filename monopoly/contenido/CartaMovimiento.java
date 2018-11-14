package monopoly.contenido;

public class CartaMovimiento extends Carta{

    private int posicion;
    private boolean accionFinanciera;
    private int alquiler; //0 si no hay alquiler, 1 si hay alquiler y 2 si hay que pagar el doble del alquiler

    public CartaMovimiento(int posicion, boolean accionFinanciera, int alquiler, String descripcion){
        if(posicion >= 0 && posicion <= 39)
            this.posicion = posicion;
        this.accionFinanciera = accionFinanciera;
        if(alquiler >= 0 && alquiler <= 2)
            this.alquiler = alquiler;
        super.setDescripcion(descripcion);
    }

    public int getPosicion(){
        return this.posicion;
    }

    public boolean getAccionFinanciera(){
        return this.accionFinanciera;
    }

    public int getAlquiler(){
        return this.alquiler;
    }

    //Los setters no son necesarios, pues cada carta es inmutable una vez creada
}

