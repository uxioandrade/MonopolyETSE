package monopoly.contenido;

import monopoly.plataforma.Operacion;
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

    public void pagarImpuesto(Jugador jugador, Operacion operacion) {
        Juego.consola.imprimir(jugador.getNombre() + ",debes pagar un impuesto de " + apagar + " debido a " + jugador.getAvatar().getCasilla().getNombre());
        //Comprueba que el jugador tenga dinero suficiente para pagar
        if (apagar <= jugador.getDinero()) {
            jugador.modificarDinero(apagar);
            Juego.consola.imprimir("Se han pagado " + apagar + "€ de impuesto");
            Valor.actualizarDineroAcumulado(apagar);
            jugador.modificarPagoImpuestos(apagar);
        } else {
            Juego.consola.imprimir("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
            if (operacion.menuHipotecar(jugador, operacion.getTablero(), apagar))
                pagarImpuesto(jugador, operacion);
        }
    }

    public void accionCaer(Jugador jugador, int tirada, Operacion operacion){
        this.pagarImpuesto(jugador, operacion);
    }

    @Override
    public String toString(){
        String aux =super.toString().substring(0,super.toString().length()-2);
        aux += "Tipo: " + "Impuestos" + "\n" +
                "A pagar: " + ((Impuesto)this).getApagar() + "\n";
        aux+="}\n";
        return aux;
    }
}
