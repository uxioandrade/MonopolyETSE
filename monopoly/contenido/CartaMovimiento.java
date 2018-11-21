package monopoly.contenido;

import monopoly.plataforma.Accion;
import monopoly.plataforma.Valor;
import monopoly.plataforma.Tablero;
import sun.tools.jconsole.Tab;

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
        jugador.modificarDinero(-jugador.getAvatar().getCasilla().getAlquiler()*2);
        System.out.println("Se han pagado " + jugador.getAvatar().getCasilla().getAlquiler()*2 + "€ de alquiler.");
        //Se aumenta el dinero del propietario
        jugador.getAvatar().getCasilla().getPropietario().modificarDinero(jugador.getAvatar().getCasilla().getAlquiler()*2);
    }

    public void accionCarta(Jugador jugador, Tablero tablero){
        System.out.println(super.getDescripcion());
        Accion accion = new Accion(tablero);
        if(this.accionFinanciera && this.posicion <= jugador.getAvatar().getCasilla().getPosicion())
            jugador.modificarDinero(Valor.getDineroVuelta());
        jugador.getAvatar().setCasilla(Valor.casillas.get(this.posicion));
        switch (alquiler){
            case 0:
                break;
            case 1:
                accion.pagarAlquiler(jugador,1);
                break;
            case 2:
                double cantidadDoble = jugador.getAvatar().getCasilla().getAlquiler()*2;
                if (jugador.getDinero() >= cantidadDoble){
                   cobrarAccion(jugador,tablero,cantidadDoble);
                } else {
                    System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
                    if(accion.menuHipotecar(jugador,tablero,jugador.getAvatar().getCasilla().getAlquiler()*2)) {
                        cobrarAccion(jugador,tablero,cantidadDoble);
                    }
                }
                break;
        }
    }
}

