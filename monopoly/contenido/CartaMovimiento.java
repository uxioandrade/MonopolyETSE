package monopoly.contenido;

import monopoly.plataforma.Accion;
import monopoly.plataforma.Valor;
import monopoly.plataforma.Tablero;

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

    private void cobrarAccion(Jugador jugador, Tablero tablero, double cantidad){
        //Se resta el alquiler del jugador que ha caído en el servicio
        jugador.modificarDinero(-cantidad);
        jugador.modificarPagoAlquileres(cantidad);
        System.out.println("Se han pagado " + cantidad + "€ de alquiler.");
        //Se aumenta el dinero del propietario
        Comprables casillaComprable;
        casillaComprable = (Comprables) jugador.getAvatar().getCasilla();
        casillaComprable.getPropietario().modificarDinero(cantidad);
    }

    public void accionCarta(Jugador jugador, Tablero tablero){
        System.out.println(super.getDescripcion());
        Comprables casillaComprable;
        Accion accion = new Accion(tablero);
        //Siempre se cae en una casilla que tiene un alquiler asociado
        if(this.accionFinanciera && this.posicion <= jugador.getAvatar().getCasilla().getPosicion()) {
            jugador.modificarDinero(Valor.getDineroVuelta());
            jugador.modificarPasarPorCasilla(Valor.getDineroVuelta());
        }
        jugador.getAvatar().setCasilla(Valor.casillas.get(this.posicion));
        casillaComprable = (Comprables) jugador.getAvatar().getCasilla();
        switch (alquiler){
            case 0:
                break;
            case 1:
                casillaComprable.accionCaer(jugador,1,accion);
                break;
            case 2:
                double cantidadDoble = casillaComprable.getAlquiler(1)*2;
                if (jugador.getDinero() >= cantidadDoble){
                   cobrarAccion(jugador,tablero,cantidadDoble);
                } else {
                    System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
                    if(accion.menuHipotecar(jugador,tablero,casillaComprable.getAlquiler(1)*2)) {
                        cobrarAccion(jugador,tablero,cantidadDoble);
                    }
                }
                break;
        }
    }
}

