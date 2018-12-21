package monopoly.contenido;

import monopoly.plataforma.Accion;
import monopoly.plataforma.Juego;

import java.util.HashMap;

public abstract class Propiedades extends Casilla {

    private double precio;
    private boolean hipotecado;
    private Jugador propietario;
    private double rentabilidad;
    private HashMap<Jugador,Integer> jugadoresExcluidos;

    public Propiedades(String nombre, int posicion){
        super(nombre,posicion);
        this.jugadoresExcluidos = new HashMap<>();
    }

    public Jugador getPropietario(){
        return this.propietario;
}
    public void setPropietario(Jugador propietario){
        if(propietario != null){
            if(this.propietario != null) {
                if (!this.propietario.getNombre().equals("Banca"))
                    this.propietario.borrarPropiedad(this);
            }
            this.propietario=propietario;
            if(!propietario.getPropiedades().contains(this))
            this.propietario.anhadirPropiedad(this);
        }
    }

    public double getHipoteca(){
        return this.precio*0.5;
    }

    public boolean getHipotecado(){ return this.hipotecado; }

    public void setHipotecado(boolean hipotecado){
        this.hipotecado = hipotecado;
    }

    public void setPrecio(double precio){
        if(precio > 0.00000)
            this.precio = precio;
    }

    public double getRentabilidad(){
        return this.rentabilidad;
    }

    public void sumarRentabilidad(double valor){
        if(valor > 0)
            this.rentabilidad += valor;
    }

    public double getPrecio(){
        return this.precio;
    }

    public abstract double alquiler(int tirada);

    public abstract void pagarAlquiler(Jugador jugador, int tirada, Accion accion);

    public void accionCaer(Jugador jugador, int tirada, Accion accion) {
        if (!propietario.equals(accion.getTablero().getBanca()) && !propietario.equals(jugador) && !this.hipotecado){
            if(this.jugadoresExcluidos.containsKey(jugador)){
                Juego.consola.imprimir("El jugador " + jugador.getNombre() + " no tiene que pagar este alquiler durante " + this.jugadoresExcluidos.get(jugador) + " turnos.");
            }else
                this.pagarAlquiler(jugador, tirada, accion);
        }
    }

    public HashMap<Jugador,Integer> getJugadoresExcluidos(){
        return this.jugadoresExcluidos;
    }

    public void anhadirJugadorExcluido(Jugador jugador, int turnos){
        if(!this.jugadoresExcluidos.containsKey(jugador))
            this.jugadoresExcluidos.put(jugador,turnos);
        else
            this.jugadoresExcluidos.replace(jugador,turnos + this.jugadoresExcluidos.get(jugador) + turnos);
    }

    public void reducirTurnosTratos(Jugador jugador){
        if(this.jugadoresExcluidos.containsKey(jugador)){
            this.jugadoresExcluidos.replace(jugador,this.jugadoresExcluidos.get(jugador)-1);
            if(this.jugadoresExcluidos.get(jugador) == 0)
                this.jugadoresExcluidos.remove(jugador);
        }
    }

    public boolean perteneceAJugador(Jugador jugador){
        return this.propietario.equals(jugador);
    }
}
