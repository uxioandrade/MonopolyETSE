package monopoly.contenido;

import monopoly.plataforma.Valor;
import monopoly.plataforma.Tablero;

import java.util.ArrayList;

public class Jugador {

    private Avatar avatar;
    private String nombre;
    private double dinero;
    private ArrayList<Casilla> propiedades;

    public Jugador(){
        this.avatar = null;
        this.nombre = "Banca";
        this.dinero = Double.POSITIVE_INFINITY;
        this.propiedades = Valor.casillas;
        for(Casilla c : this.propiedades){
            c.setPropietario(this);
        }
    }

    public Jugador(String nombre, String tipo, Tablero tablero){
        this.avatar = new Avatar(tipo,this, tablero);
        this.dinero = Valor.FORTUNA_INICIAL;
        this.propiedades = new ArrayList<>();
        this.nombre = nombre;
    }

    public Avatar getAvatar(){
        return this.avatar;
    }

    //El setter de avatar es omitible, pues un avatar asociado a un jugador no debería modificarse

    public String getNombre(){
        return this.nombre;
    }

    //El setter de nombre no es necesario, pues un jugador no puede cambiar de nombre una vez creado

    public double getDinero(){
        return this.dinero;
    }
    public void setDinero(double dinero){
        this.dinero=dinero;
    }
    public void modificarDinero(double cantidad){
        this.dinero+=cantidad;
    }

    public Tablero getTablero() { return this.getTablero(); }

    //El setter para tablero no es necesario, pues el tablero solo se pasa a la instancia de acción al principio. Si se crease un setter y se cambiase el tablero
    //esto implicaría que la partida es una partida nueva


    public ArrayList<Casilla> getPropiedades(){
        return this.propiedades;
    }

    public void setPropiedades(ArrayList<Casilla> casillas){//Podría ser interesante en las próximas entregas
        this.propiedades = casillas;
    }

    public void anhadirPropiedad(Casilla casilla){
        if(casilla != null)
            this.propiedades.add(casilla);
    }

    public String getDescripcionInicial(){
        String aux = "\t{\n" +
                "\t\tNombre: " + this.nombre + "\n" +
                "\t\tAvatar: " + this.avatar.getId() + "\n\t}";
        return aux;
    }
    public int numSolaresGrupo(Grupo grupo){
        int i=0;
        for (Casilla cas: this.getPropiedades()){//recorremos las propiedades del jugador
            if(cas.getGrupo().equals(grupo)){//si el grupo de la casilla coincide con el que buscamos sumamos 1
                i++;
            }
        }
        return i;
    }
    public boolean poseeGrupoCompleto(Grupo grupo){
        return (this.numSolaresGrupo(grupo)==grupo.getCasillas().size());//comparamos que el numero de casillas que posee del grupo coincidan con el numero de casillas del grupo
    }

    @Override
    public String toString(){
        String aux = "{\n" +
                "Nombre: " + this.nombre + "\n" +
                "Avatar: " + this.avatar.getId() + "\n" +
                "Tipo: " + this.avatar.getTipo() + "\n" +
                "Dinero Actual: " + this.dinero + "\n" +
                "Propiedades: {";
        if(this.propiedades.size()!=0) {//si el jugador tiene propiedades las añadimos al string
            aux +="\n";
            for (Casilla prop : propiedades) {
                aux += "\t" + prop.getNombre() + "\n";
            }
        }
        aux += "}\n}\n";
        return aux;
    }
}
