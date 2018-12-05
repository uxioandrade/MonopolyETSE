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
        this.cantidad = cantidadOfertada;
    }
    public Trato(double cantidadRecibida,Jugador receptor, Comprables propiedadOfertada){ //Propiedad x cantidad
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadOfertada);
        this.ofertor = propiedadOfertada.getPropietario();
        this.receptor = receptor;
        this.cantidad = cantidadRecibida;
    }

    public Trato(Comprables propiedadOfertada, double cantidad, Comprables propiedadRecibida){
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadOfertada);
        this.propiedadesOfertadas.add(propiedadRecibida);
        this.ofertor = propiedadOfertada.getPropietario();
        this.receptor = propiedadRecibida.getPropietario();
        this.cantidad = cantidad;
    }

    public Trato(Comprables propiedadOfertada, Comprables propiedadRecibida, Comprables propiedadNoAlquiler, int turnos){
        this.propiedadesOfertadas = new ArrayList<>();
        this.propiedadesOfertadas.add(propiedadOfertada);
        this.propiedadesOfertadas.add(propiedadRecibida);
        this.propiedadesOfertadas.add(propiedadNoAlquiler);
        this.ofertor = propiedadOfertada.getPropietario();
        this.receptor = propiedadRecibida.getPropietario();
        if(turnos > 0)
            this.turnos = turnos;
    }

    public void aceptar(){
        if(receptor.getDinero()>this.precio){
    
        }
    }
}
