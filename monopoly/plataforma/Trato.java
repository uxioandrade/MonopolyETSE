package monopoly.plataforma;

import monopoly.contenido.Propiedades;
import monopoly.contenido.Jugador;
import monopoly.plataforma.*;

import java.util.ArrayList;

public class Trato {
    private ArrayList<Propiedades> propiedadesOfertadas; //Ofertada->0 Recibida->1 No Pagar->2
    private Jugador ofertor;
    private Jugador receptor;
    private double cantidad;
    private int turnos;
    private int id;

    public Trato(Propiedades ofertada, Propiedades recibida){ //Propiedad x propiedad
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(ofertada);
        this.propiedadesOfertadas.add(recibida);
        this.ofertor = ofertada.getPropietario();
        this.receptor = recibida.getPropietario();
        this.receptor.anhadirTratoPendiente(this);
        this.ofertor.anhadirTratoPropuesto(this);
        this.id = Valor.getTratos();
        Valor.incrementarTratos();
    }

    public Trato(Jugador ofertor, double cantidadOfertada,Propiedades propiedadRecibida){ //Cantidad x propiedad
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadRecibida);
        this.ofertor = ofertor;
        this.receptor = propiedadRecibida.getPropietario();
        this.receptor.anhadirTratoPendiente(this);
        this.ofertor.anhadirTratoPropuesto(this);
        this.cantidad = cantidadOfertada;
        this.id = Valor.getTratos();
        Valor.incrementarTratos();
    }
    public Trato(double cantidadRecibida,Jugador receptor, Propiedades propiedadOfertada){ //Propiedad x cantidad
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadOfertada);
        this.ofertor = propiedadOfertada.getPropietario();
        this.receptor = receptor;
        this.receptor.anhadirTratoPendiente(this);
        this.ofertor.anhadirTratoPropuesto(this);
        this.cantidad = cantidadRecibida;
        this.id = Valor.getTratos();
        Valor.incrementarTratos();
    }

    public Trato(Propiedades propiedadOfertada, double cantidad, Propiedades propiedadRecibida){
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadOfertada);
        this.propiedadesOfertadas.add(propiedadRecibida);
        this.ofertor = propiedadOfertada.getPropietario();
        this.receptor = propiedadRecibida.getPropietario();
        this.receptor.anhadirTratoPendiente(this);
        this.ofertor.anhadirTratoPropuesto(this);
        this.cantidad = cantidad;
        this.id = Valor.getTratos();
        Valor.incrementarTratos();
    }

    public Trato(Propiedades propiedadOfertada, Propiedades propiedadRecibida, Propiedades propiedadNoAlquiler, int turnos){
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadOfertada);
        this.propiedadesOfertadas.add(propiedadRecibida);
        this.propiedadesOfertadas.add(propiedadNoAlquiler);
        this.ofertor = propiedadOfertada.getPropietario();
        this.receptor = propiedadRecibida.getPropietario();
        this.receptor.anhadirTratoPendiente(this);
        this.ofertor.anhadirTratoPropuesto(this);
        this.id = Valor.getTratos();
        Valor.incrementarTratos();
        if(turnos > 0)
            this.turnos = turnos;
        propiedadNoAlquiler.anhadirJugadorExcluido(this.receptor,this.turnos);
    }

    public Jugador getOfertor() {
        return this.ofertor;
    }

    public Jugador getReceptor(){
        return this.receptor;
    }

    private void swapPropiedades(){
        if(this.propiedadesOfertadas.size()>=2){
            this.propiedadesOfertadas.get(0).setPropietario(this.receptor);
            this.propiedadesOfertadas.get(1).setPropietario(this.ofertor);
        }else {
            if (this.ofertor.getPropiedades().contains(this.propiedadesOfertadas.get(0))){
                this.propiedadesOfertadas.get(0).setPropietario(this.receptor);
            }else{
                this.propiedadesOfertadas.get(0).setPropietario(this.ofertor);
            }
        }
    }

    public int getId(){
        return this.id;
    }

    public void aceptar(){
       if(this.receptor.getDinero()<cantidad){
           System.out.println("El trato no puede ser aceptado: " + this.receptor.getNombre() + " no dispone de " + -1*cantidad + "€.");
           return;
       }
       this.swapPropiedades();
       this.receptor.modificarDinero(cantidad);
       this.ofertor.modificarDinero(-cantidad);
       this.receptor.borrarTratoPendiente(this);
        this.ofertor.anhadirTratoPropuesto(this);
    }

    public void eliminar(){
        this.receptor.borrarTratoPendiente(this); //Dentro de este arraylist ya se borra el ofertor
        this.ofertor.anhadirTratoPropuesto(this);
        System.out.println("Se ha eliminado el trato" + this.id + ".");
    }
}
