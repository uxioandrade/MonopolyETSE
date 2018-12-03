package monopoly.contenido;
import monopoly.plataforma.Valor;
import monopoly.plataforma.Accion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public abstract class Casilla {

    private int posicion;
    private String nombre;
    private ArrayList<Avatar> avatares;
    private HashMap<Avatar,Integer> visitasAvatares;

    public Casilla(String nombre, int posicion){
        if(posicion >=0 && posicion<40)
            this.posicion = posicion;
        this.nombre = nombre;
        this.avatares = new ArrayList<>();
        this.visitasAvatares = new HashMap<>();
    }

    public String getNombre(){
        return this.nombre;
    }
    //No son necesarios los setters de nombre y posicion, ya que no se modifican en el programa
    public int getPosicion(){
        return this.posicion;
    }
    //el setter de avatares no tiene sentido pues solo se pueden anhadir o quitar avatares pero no cambiar el arraylist completo
    public ArrayList<Avatar> getAvatares(){
        return this.avatares;
    }

    private void anhadirVisita(Avatar avatar){
        if(this.visitasAvatares.containsKey(avatar))
            this.visitasAvatares.replace(avatar,this.visitasAvatares.get(avatar) + 1);
        else
            this.visitasAvatares.put(avatar,1);
    }

    public void anhadirAvatar(Avatar a){
        if(a!=null){
            avatares.add(a);
        }
        this.anhadirVisita(a);
    }

    public void quitarAvatar(Avatar a){
        if(this.avatares.contains(a)) this.avatares.remove(a);
    }

    public HashMap getVisitasAvatares(){
        return this.visitasAvatares;
    }

    public int frecuenciaVisita(Avatar av){
        if(this.visitasAvatares.containsKey(av))
            return this.visitasAvatares.get(av);
        else
            return 0;
    }

    public int numVisitas(){
        int total = 0;
        Iterator<Integer> vis_i = this.visitasAvatares.values().iterator();
        while(vis_i.hasNext()){
            total += vis_i.next();
        }
        return total;
    }

    public abstract void accionCaer(Jugador jugador,int tirada, Accion accion);

    public boolean estaAvatar(Avatar av){
        return this.avatares.contains(av);
    }

    @Override
    public String toString(){
        //En los criterios pone que no es necesario describir las casillas especiales
        String aux = "{\n" +
                "Nombre: " + this.nombre + "\n" +
                "Posición: " + this.posicion + "\n";
        if(this instanceof Solar) {
            aux +=  "Tipo: Solar\n" +
                    "Grupo: " + ((Solar) this).getGrupo().getNombre() + "\n" +
                    "Precio: " + ((Solar) this).getPrecio() + "€\n" +
                    "Alquiler Actual: " + ((Solar) this).alquiler(1) + "€\n" +
                    "Alquiler Básico: " + ((Solar) this).getPrecio() * 0.1 + "€\n" +
                    "Casas: " + ((Solar) this).getConstrucciones("casa").size() +  " | valor casa " + Valor.MULTIPLICADOR_INICIAL_CASA * ((Solar) this).getPrecio() + "€\n" +
                    "Hoteles: " + ((Solar) this).getConstrucciones("hotel").size() + " | valor hotel " + Valor.MULTIPLICADOR_INICIAL_HOTEL * ((Solar) this).getPrecio() + "€\n" +
                    "Piscinas: " + ((Solar) this).getConstrucciones("piscina").size() +  " | valor piscina " + Valor.MULTIPLICADOR_INICIAL_PISCINA * ((Solar) this).getPrecio() + "€\n" +
                    "Pistas de Deporte: " + ((Solar) this).getConstrucciones("pista").size() + " | valor pista de deporte " + Valor.MULTIPLICADOR_INICIAL_PISTA* ((Solar) this).getPrecio() + "€\n" +
                    "Alquiler una casa: " + 5*((Solar) this).getPrecio()*0.1 + "€\n" +
                    "Alquiler dos casas: " + 15*((Solar) this).getPrecio()*0.1 + "€\n" +
                    "Alquiler tres casas: " + 35*((Solar) this).getPrecio()*0.1 + "€\n" +
                    "Alquiler cuatro casas: " + 50*((Solar) this).getPrecio()*0.1 + "€\n" +
                    "Alquiler hotel: " + 70*((Solar) this).getPrecio()*0.1 + "€\n" +
                    "Alquiler piscina: " + 25*((Solar) this).getPrecio()*0.1 + "€\n" +
                    "Alquiler pista de deporte: " + 25*((Solar) this).getPrecio()*0.1 + "€\n" +
                    "Hipoteca: " + ((Solar) this).getHipoteca() + "€\n";
        }
        else{
            if(this instanceof Transporte){
                aux += "Tipo: " + "Transporte" + "\n" +
                        "Precio: " + ((Transporte) this).getPrecio() + "€\n" +
                        "Uso Transporte Actual: " + ((Transporte) this).alquiler(1) + "€\n" +
                        "Uso Transporte Básico: " + Valor.getDineroVuelta() * 0.25 + "€\n" +
                        "Hipoteca: " + ((Transporte) this).getHipoteca() + "€\n";
            }
            else if(this instanceof Servicio){
                aux += "Tipo: " + "Servicios" + "\n" +
                        "Precio: " + ((Servicio) this).getPrecio() + "\n" +
                        "Uso Servicio: " + ((Servicio) this).alquiler(1)+" x suma de los dados" + "\n" +
                        "Hipoteca: " + ((Servicio) this).getHipoteca() + "\n";
            }
            else if(this instanceof Impuesto){
                aux += "Tipo: " + "Impuestos" + "\n" +
                        "A pagar: " + ((Impuesto)this).getApagar() + "\n";
            }
            else if(this.posicion == 10){
                aux+="salir: "+Valor.getDineroSalirCarcel()+"\n"+
                      "Jugadores:";                 
                for (Avatar a: avatares){
                    if(a.getEncarcelado() > 0)
                        aux+="["+a.getJugador().getNombre()+", "+a.getEncarcelado()+"] ";
                }
                aux+="\n";
            }
            else if(this.posicion == 20){
                aux +="Bote: "+Valor.getDineroAcumulado()+"\n"+
                       "Jugadores: ";
                for (Avatar a: avatares){
                    aux+="["+a.getJugador().getNombre()+"] ";
                }
                aux+="\n";
            }
        }
        if(this instanceof Propiedades){
            if(!((Propiedades) this).getPropietario().getNombre().equals("Banca"))
                aux += "Propietario: " + ((Propiedades) this).getPropietario().getNombre() + "\n";
            if(((Propiedades) this).getHipotecado())
                aux += "Solar hipotecado, paga " + 1.1*((Propiedades) this).getHipoteca() + " para deshipotecar" + "\n";
        }

        aux += "}\n";
        return aux;
    }
}
