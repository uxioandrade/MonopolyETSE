package monopoly.plataforma;

import monopoly.contenido.Comprables;
import monopoly.contenido.Jugador;
import monopoly.plataforma.*;

import java.util.ArrayList;

public class Trato {
    private ArrayList<Comprables> propiedadesOfertadas; //Ofertada->0 Recibida->1 No Pagar->2
    private Jugador ofertor;
    private Jugador receptor;
    private double cantidad;
    private int turnos;
    private int id;

    public Trato(Comprables ofertada, Comprables recibida){ //Propiedad x propiedad
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(ofertada);
        this.propiedadesOfertadas.add(recibida);
        this.ofertor = ofertada.getPropietario();
        this.receptor = recibida.getPropietario();
    }

    public Trato(Jugador ofertor, double cantidadOfertada,Comprables propiedadRecibida){ //Cantidad x propiedad
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadRecibida);
        this.ofertor = ofertor;
        this.receptor = propiedadRecibida.getPropietario();
        this.receptor.anhadirTratoPendiente(this);
        this.cantidad = cantidadOfertada;
    }
    public Trato(double cantidadRecibida,Jugador receptor, Comprables propiedadOfertada){ //Propiedad x cantidad
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadOfertada);
        this.ofertor = propiedadOfertada.getPropietario();
        this.receptor = receptor;
        this.receptor.anhadirTratoPendiente(this);
        this.cantidad = cantidadRecibida;
    }

    public Trato(Comprables propiedadOfertada, double cantidad, Comprables propiedadRecibida){
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadOfertada);
        this.propiedadesOfertadas.add(propiedadRecibida);
        this.ofertor = propiedadOfertada.getPropietario();
        this.receptor = propiedadRecibida.getPropietario();
        this.receptor.anhadirTratoPendiente(this);
        this.cantidad = cantidad;
    }

    public Trato(Comprables propiedadOfertada, Comprables propiedadRecibida, Comprables propiedadNoAlquiler, int turnos){
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadOfertada);
        this.propiedadesOfertadas.add(propiedadRecibida);
        this.propiedadesOfertadas.add(propiedadNoAlquiler);
        this.ofertor = propiedadOfertada.getPropietario();
        this.receptor = propiedadRecibida.getPropietario();
        this.receptor.anhadirTratoPendiente(this);
        if(turnos > 0)
            this.turnos = turnos;
    }

    private void swapPropiedades(){
        if(this.propiedadesOfertadas.size()>=2){
            this.propiedadesOfertadas.get(0).setPropietario(this.receptor);
            this.propiedadesOfertadas.get(1).setPropietario(this.ofertor);
        }else {
            if(this.ofertor.getPropiedades().contains(this.propiedadesOfertadas.get(0)))
                this.propiedadesOfertadas.get(0).setPropietario(this.receptor);
            else
                this.propiedadesOfertadas.get(0).setPropietario(this.ofertor);
        }
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
    }
}
