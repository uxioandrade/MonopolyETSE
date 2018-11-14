package monopoly.contenido;

public class CartaPagar extends Carta{

    private double cantidad; //Negativa => Pagar , Positivo => Cobrar
    private boolean banca; //True => Banca, False => Todos los jugadores

    public CartaPagar(double cantidad, boolean banca, String descripcion){
        this.cantidad = cantidad;
        this.banca = banca;
        super.setDescripcion(descripcion);
    }

    public double getCantidad(Jugador jugador){
        if(cantidad == 0)
            return jugador.getPropiedades().size()*2;//Hay que cambiarlo
        else
            return cantidad;
    }
}
