package monopoly.contenido;
import monopoly.plataforma.Valor;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Accion;
import monopoly.Carcel;
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

    public int numVisitasAvatar(Avatar av){
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


    @Override
    public String toString(){
        //En los criterios pone que no es necesario describir las casillas especiales
        String aux = "{\n" +
                "Nombre: " + this.nombre + "\n" +
                "Posición: " + this.posicion + "\n";
        if(this instanceof Solar) {
            aux +=  "Tipo: Solar\n" +
                    "Grupo: " + ((Solar) this).getGrupo().getNombre() + "\n" +
                    "Precio: " + ((Solar) this).getPrecio() + "\n" +
                    "Alquiler: " + ((Solar) this).getAlquiler(1) + "\n" +
                    "Casas: " + ((Solar) this).getConstrucciones("casa").size() + "\n" +
                    "Hoteles: " + ((Solar) this).getConstrucciones("hotel").size() + "\n" +
                    "Piscinas: " + ((Solar) this).getConstrucciones("piscina").size() + "\n" +
                    "Pistas de Deporte: " + ((Solar) this).getConstrucciones("pista").size() + "\n" +
                    "Hipoteca: " + ((Solar) this).getHipoteca() + "\n";
        }
        else{
            if(this instanceof Transporte){
                aux += "Tipo: " + "Transporte" + "\n" +
                        "Precio: " + ((Transporte) this).getPrecio() + "\n" +
                        "Uso transporte: " + ((Transporte) this).getAlquiler(1) + "\n" +
                        "Hipoteca: " + ((Transporte) this).getHipoteca() + "\n";
            }
            else if(this instanceof Servicio){
                aux += "Tipo: " + "Servicios" + "\n" +
                        "Precio: " + ((Servicio) this).getPrecio() + "\n" +
                        "Uso Servicio: " + ((Servicio) this).getAlquiler(1)+" x suma de los dados" + "\n" +
                        "Hipoteca: " + ((Servicio) this).getHipoteca() + "\n";
            }
            else if(this instanceof Impuesto){
                aux += "Tipo: " + "Impuestos" + "\n" +
                        "A pagar: " + ((Impuesto)this).getApagar() + "\n";
            }
            else if(this instanceof Carcel){
                aux+="salir: "+Valor.getDineroSalirCarcel()+"\n"+
                      "Jugadores:";                 
                for (Avatar a: avatares){
                    if(a.getEncarcelado() > 0)
                        aux+="["+a.getJugador().getNombre()+", "+a.getEncarcelado()+"] ";
                }
                aux+="\n";
            }
            else if(this instanceof Parking){
                aux +="Bote: "+Valor.getDineroAcumulado()+"\n"+
                       "Jugadores: ";
                for (Avatar a: avatares){
                    aux+="["+a.getJugador().getNombre()+"] ";
                }
                aux+="\n";
            }
        }
        if(this instanceof Comprables){
            if(!((Comprables) this).getPropietario().getNombre().equals("Banca"))
                aux += "Propietario: " + ((Comprables) this).getPropietario().getNombre() + "\n";
            if(((Comprables) this).getHipotecado())
                aux += "Solar hipotecado, paga " + 1.1*((Comprables) this).getHipoteca() + " para deshipotecar" + "\n";
        }

        aux += "}\n";
        return aux;
    }
}
