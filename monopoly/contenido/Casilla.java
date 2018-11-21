package monopoly.contenido;
import monopoly.plataforma.Valor;
import java.util.ArrayList;

public class Casilla {

    private int posicion;
    private Grupo grupo;
    private String nombre;
    private double precio;
    private boolean hipotecado;
    private Jugador propietario;
    private ArrayList<Avatar> avatares;

    public Casilla(String name,int pos){
        this.posicion = pos;
        this.nombre = name;
        this.hipotecado = false;
        this.avatares = new ArrayList<>();
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
    public void anhadirAvatar(Avatar a){
        if(a!=null){
            avatares.add(a);
        }
    }
    public void quitarAvatar(Avatar a){
        if(this.avatares.contains(a)) this.avatares.remove(a);
    }
    public void setPrecio(double precio){
        this.precio=precio;
    }
    public double getPrecio(){
        return this.precio;
    }
    public Jugador getPropietario(){
        return this.propietario;
    }
    public void setPropietario(Jugador propietario){
        if(propietario != null) this.propietario=propietario;
    }
    public Grupo getGrupo(){
        return this.grupo;
    }

    public void setGrupo(Grupo grupo){
        this.grupo = grupo;
    }

    public double getAlquiler(int tirada){
        return Valor.getDineroVuelta()*tirada/200.0;
    }

    public double getAlquiler() {
        if(this.getGrupo().getNombre().equals("Especiales"))
            return this.precio;
        else
            return this.precio*0.1;
    }

    public double getHipoteca(){
            return this.precio*0.5;
    }

    public boolean getHipotecado(){
        return this.hipotecado;
    }

    public void setHipotecado(boolean hipotecado){
        this.hipotecado = hipotecado;
    }

    @Override
    public String toString(){
        //En los criterios pone que no es necesario describir las casillas especiales
        String aux = "{\n" +
                "Nombre: " + this.nombre + "\n" +
                "Posición: " + this.posicion + "\n";
        if(!this.getGrupo().getNombre().equals("Especiales")) {
            aux +=  "Tipo: Solar\n" +
                    "Grupo: " + this.grupo.getNombre() + "\n" +
                    "Precio: " + this.precio + "\n" +
                    "Alquiler: " + this.getAlquiler() + "\n" +
                    "Hipoteca: " + this.getHipoteca() + "\n";
        }
        else{
            if(this.getNombre().contains("Estacion")){
                aux += "Tipo: " + "Transportes" + "\n" +
                        "Precio: " + this.precio + "\n" +
                        "Uso transporte: " + this.getAlquiler() + "\n" +
                        "Hipoteca: " + this.getHipoteca() + "\n";
            }
            else if(this.getNombre().contains("Servicio")){
                aux += "Tipo: " + "Servicios" + "\n" +
                        "Precio: " + this.precio + "\n" +
                        "Uso Servicio: " + this.getAlquiler(1)+" x suma de los dados" + "\n" +
                        "Hipoteca: " + this.getHipoteca() + "\n";
            }
            else if(this.getNombre().contains("Impuesto")){
                aux += "Tipo: " + "Impuestos" + "\n" +
                        "A pagar: " + this.getAlquiler() + "\n";
            }
            else if(this.getNombre().equals("Carcel")){
                aux+="salir: "+Valor.getDineroSalirCarcel()+"\n"+
                      "Jugadores:";                 
                for (Avatar a: avatares){
                    if(a.getEncarcelado() > 0)
                        aux+="["+a.getJugador().getNombre()+", "+a.getEncarcelado()+"] ";
                }
                aux+="\n";
            }
            else if(this.getNombre().equals("Parking")){
                aux +="Bote: "+Valor.getDineroAcumulado()+"\n"+
                       "Jugadores: ";
                for (Avatar a: avatares){
                    aux+="["+a.getJugador().getNombre()+"] ";
                }
                aux+="\n";
            }
        }
        if(!this.propietario.getNombre().equals("Banca"))
            aux += "Propietario: " + this.propietario.getNombre() + "\n";
        if(this.hipotecado)
            aux += "Solar hipotecado, paga " + 1.1*this.getHipoteca() + " para deshipotecar" + "\n";
        aux += "}\n";
        return aux;
    }
}
