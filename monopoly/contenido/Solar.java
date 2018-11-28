package monopoly.contenido;

import java.util.HashMap;
import java.util.ArrayList;
import monopoly.plataforma.Accion;


public class Solar extends Comprables{

    private Grupo grupo;
    private ArrayList<Edificios> construcciones;

    public Solar(String nombre, int posicion){
        super(nombre,posicion);
        this.construcciones = new ArrayList<>();
    }

    public Grupo getGrupo(){
        return this.grupo;
    }

    public void setGrupo(Grupo grupo){
        this.grupo = grupo;
    }

    public double getAlquiler(int valor){
        return super.getPrecio()*0.1;
    }

    public void anhadirEdificio(Edificios edificio){
        if(edificio != null)
            this.construcciones.add(edificio);
    }

    public void borrarEdificio(Edificios edificio){
        if(this.construcciones.contains(edificio))
            this.construcciones.remove(edificio);
        else
            System.out.println("Este solar no tiene el edificio indicado");
    }

    public ArrayList<Edificios> getConstrucciones(){
        return this.construcciones;
    }

    public ArrayList<Edificios> getConstrucciones(String tipo){
        ArrayList<Edificios> construccionesTipo = new ArrayList<>();

        switch (tipo){
            case "casa":
            case "Casa":
                for(Edificios c : this.construcciones){
                    if(c instanceof Casa)
                        construccionesTipo.add(c);
                }
                break;
            case "hotel":
            case "Hotel":
                for(Edificios c : this.construcciones){
                    if(c instanceof Hotel)
                        construccionesTipo.add(c);
                }
                break;
            case "piscina":
            case "Piscina":
                for(Edificios c : this.construcciones){
                    if(c instanceof Piscina)
                        construccionesTipo.add(c);
                }
                break;
            case "pista":
            case "Pista":
                for(Edificios c : this.construcciones){
                    if(c instanceof PistaDeporte)
                        construccionesTipo.add(c);
                }
                break;
            default:
                return null;
        }
        return  construccionesTipo;
    }

    public void pagarAlquiler(Jugador jugador, int tirada, Accion accion){
        if (jugador.getDinero() >= this.getAlquiler(tirada)){
            //Se resta el alquiler del jugador que ha caído en el servicio
            jugador.modificarDinero(-this.getAlquiler(tirada));
            jugador.modificarPagoAlquileres(this.getAlquiler(tirada));
            System.out.println("Se han pagado " + this.getAlquiler(tirada) + "€ de alquiler.");
            //Se aumenta el dinero del propietario
            super.getPropietario().modificarDinero(this.getAlquiler(tirada));
            super.getPropietario().modificarCobroAlquileres(this.getAlquiler(tirada));
            super.sumarRentabilidad(this.getAlquiler(tirada));
            this.grupo.sumarRentabilidad(this.getAlquiler(tirada));
        } else {
            System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
            if(accion.menuHipotecar(jugador,accion.getTablero(),super.getPrecio())){
                pagarAlquiler(jugador,tirada,accion);
            }
        }
    }

}
