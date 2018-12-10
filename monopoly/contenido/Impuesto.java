package monopoly.contenido;

import monopoly.plataforma.Accion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Valor;

public final class Impuesto extends Casilla {

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

    public void pagarImpuesto(Jugador jugador, Accion accion) {
        Juego.consola.imprimir(jugador.getNombre() + ",debes pagar un impuesto de " + apagar + " debido a " + jugador.getAvatar().getCasilla().getNombre());
        //Comprueba que el jugador tenga dinero suficiente para pagar
        if (apagar <= jugador.getDinero()) {
            jugador.modificarDinero(apagar);
            Juego.consola.imprimir("Se han pagado " + apagar + "€ de impuesto");
            Valor.actualizarDineroAcumulado(apagar);
            jugador.modificarPagoImpuestos(apagar);
        } else {
            Juego.consola.imprimir("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
            if (accion.menuHipotecar(jugador, accion.getTablero(), apagar))
                pagarImpuesto(jugador,accion);
        }
    }

    public void accionCaer(Jugador jugador, int tirada, Accion accion){
        this.pagarImpuesto(jugador,accion);
    }
}
