package monopoly.contenido;

import monopoly.plataforma.Accion;
import monopoly.plataforma.Valor;
import monopoly.plataforma.Tablero;

public class CartaMovimiento extends Carta{

    private int posicion;
    private boolean accionFinanciera;
    private int alquiler; //0 si no hay alquiler, 1 si hay alquiler y 2 si hay que pagar el doble del alquiler

    public CartaMovimiento(int posicion, boolean accionFinanciera, int alquiler, String descripcion){
        if(posicion >= -2 && posicion <= 39)
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
        Propiedades casillaComprable;
        casillaComprable = (Propiedades) jugador.getAvatar().getCasilla();
        casillaComprable.getPropietario().modificarDinero(cantidad);
    }

    public void accionCarta(Jugador jugador, Tablero tablero){
        if(this.posicion == -1) {
            if(jugador.getAvatar().getCasilla().getPosicion() - 3 >= 0){
                this.posicion = jugador.getAvatar().getCasilla().getPosicion() - 3;
            }else{
                this.posicion = jugador.getAvatar().getCasilla().getPosicion() - 3 + 40;
            }
        }else if(this.posicion == -2){
            if(jugador.getAvatar().getCasilla().getPosicion() >= 35 || jugador.getAvatar().getCasilla().getPosicion() <= 4){
                this.posicion = 5;
            }else if(jugador.getAvatar().getCasilla().getPosicion() >= 5 && jugador.getAvatar().getCasilla().getPosicion() <= 14){
                this.posicion = 15;
            }else if(jugador.getAvatar().getCasilla().getPosicion() >= 15 && jugador.getAvatar().getCasilla().getPosicion() <= 24){
                this.posicion = 25;
            }else if(jugador.getAvatar().getCasilla().getPosicion() >= 25 && jugador.getAvatar().getCasilla().getPosicion() <= 34){
                this.posicion = 35;
            }
        }

        if(accionFinanciera)
            System.out.println(super.getDescripcion() + " " + Valor.getDineroVuelta() + "€.");
        else
            System.out.println(super.getDescripcion());

        Propiedades casillaComprable;
        Accion accion = new Accion(tablero);
        //Siempre se cae en una casilla que tiene un alquiler asociado
        if(this.accionFinanciera && this.posicion <= jugador.getAvatar().getCasilla().getPosicion()) {
            jugador.modificarDinero(Valor.getDineroVuelta());
            jugador.modificarPasarPorCasilla(Valor.getDineroVuelta());
        }
        jugador.getAvatar().setCasilla(Valor.casillas.get(this.posicion));
        if(jugador.getAvatar().getCasilla() instanceof Propiedades) {
            casillaComprable = (Propiedades) jugador.getAvatar().getCasilla();
            switch (alquiler) {
                case 0:
                    break;
                case 1:
                    casillaComprable.accionCaer(jugador, 1, accion);
                    break;
                case 2:
                    double cantidadDoble = casillaComprable.alquiler(1) * 2;
                    if (jugador.getDinero() >= cantidadDoble) {
                        cobrarAccion(jugador, tablero, cantidadDoble);
                    } else {
                        System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
                        if (accion.menuHipotecar(jugador, tablero, casillaComprable.alquiler(1) * 2)) {
                            cobrarAccion(jugador, tablero, cantidadDoble);
                        }
                    }
                    break;
            }
        }else{
            jugador.getAvatar().getCasilla().accionCaer(jugador,1,accion);
        }
    }
}

