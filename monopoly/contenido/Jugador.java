package monopoly.contenido;

import monopoly.plataforma.Trato;
import monopoly.plataforma.Valor;
import monopoly.plataforma.Tablero;

import java.util.ArrayList;
import java.util.Random;

public class Jugador {

    private Avatar avatar;
    private String nombre;
    private double dinero;
    private ArrayList<Comprables> propiedades;
    private double dineroInvertido;
    private double pagoAlquileres;
    private double cobroAlquileres;
    private double pasarPorCasilla;
    private double premiosInversionesOBote;
    private int vecesCarcel;
    private int vecesDados;
    private ArrayList<Trato> tratosPendientes;

    public Jugador(){
        this.avatar = null;
        this.nombre = "Banca";
        this.dinero = Double.POSITIVE_INFINITY;
        propiedades = new ArrayList<>();
        for(Casilla c : Valor.casillas){
            if(c instanceof Comprables)
                this.propiedades.add((Comprables)c);
        }
        for(Comprables c : this.propiedades){
            c.setPropietario(this);
        }
        this.tratosPendientes = new ArrayList<>();
    }

    public Jugador(String nombre, String tipo, Tablero tablero){
        this.avatar = generarAvatar(tipo,tablero);
        this.dinero = Valor.FORTUNA_INICIAL;
        this.propiedades = new ArrayList<>();
        this.nombre = nombre;
        this.tratosPendientes = new ArrayList<>();
    }

    private Avatar generarAvatar(String tipo, Tablero tablero){
        Avatar avatar;
        switch (tipo){
            case "Coche":
            case "coche":
                avatar = new Coche(this,tablero);
                break;
            case "esfinge":
            case "Esfinge":
                avatar = new Esfinge(this,tablero);
                break;
            case "pelota":
            case "Pelota":
                avatar = new Pelota(this,tablero);
                break;
            case "sombrero":
            case "Sombrero":
                avatar = new Sombrero(this,tablero);
                break;
            default:
                avatar = generarAvatar(asignarTipoAleatorio(),tablero);
        }
        return avatar;
    }

    private final ArrayList<String> listaTipos = new ArrayList<String>() {{
        add("Sombrero");
        add("Coche");
        add("Esfinge");
        add("Pelota");
    }};

    private String asignarTipoAleatorio(){
        Random rnd = new Random();
        return this.listaTipos.get(rnd.nextInt(4));
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


    public ArrayList<Comprables> getPropiedades(){
        return this.propiedades;
    }

    public void setPropiedades(ArrayList<Comprables> casillas){//Podría ser interesante en las próximas entregas
        this.propiedades = casillas;
    }

    public void anhadirPropiedad(Comprables casilla){
        if(casilla != null){
            this.propiedades.add(casilla);
            if(!casilla.getPropietario().equals(this))
                casilla.setPropietario(this);
        }
    }

    public ArrayList<Trato> getTratosPendientes(){
        return this.tratosPendientes;
    }

    public void anhadirTratoPendiente(Trato trato){
        this.tratosPendientes.add(trato);
    }

    public double getDineroInvertido(){
        return this.dineroInvertido;
    }

    public void modificarDineroInvertido(double valor){
        this.dineroInvertido += valor;
    }

    public double getPagoAlquileres(){
        return this.pagoAlquileres;
    }

    public void modificarPagoAlquileres(double valor){
        this.pagoAlquileres += valor;
    }

    public double getCobroAlquileres(){
        return this.cobroAlquileres;
    }

    public void modificarCobroAlquileres(double valor){
        this.pagoAlquileres += valor;
    }

    public double getPasarPorCasilla(){
        return this.pasarPorCasilla;
    }

    public void modificarPasarPorCasilla(double valor){
        this.pasarPorCasilla += valor;
    }

    public double getPremiosInversionesOBote(){
        return this.premiosInversionesOBote;
    }

    public void modificarPremiosInversionesOBote(double valor){
        this.premiosInversionesOBote += valor;
    }

    public int getVecesCarcel(){
        return this.vecesCarcel;
    }

    public int getVecesDados(){return this.vecesDados;}

    public void anhadirVecesDados(){
        this.vecesDados++;
    }

    public void anhadirVecesCarcel(){
        this.vecesCarcel++;
    }

    public String getDescripcionInicial(){
        String aux = "\t{\n" +
                "\t\tNombre: " + this.nombre + "\n" +
                "\t\tAvatar: " + this.avatar.getId() + "\n\t}";
        return aux;
    }
    public int numSolaresGrupo(Grupo grupo){
        int i=0;
        for (Comprables cas: this.getPropiedades()){//recorremos las propiedades del jugador
            if(cas instanceof Solar) {
                Solar sol = (Solar) cas;
                if (sol.getGrupo().equals(grupo)) {//si el grupo de la casilla coincide con el que buscamos sumamos 1
                    i++;
                }
            }
        }
        return i;
    }
    public boolean poseeGrupoCompleto(Grupo grupo){
        return (this.numSolaresGrupo(grupo)==grupo.getCasillas().size());//comparamos que el numero de casillas que posee del grupo coincidan con el numero de casillas del grupo
    }

    public double calcularFortuna(){
        double total=dinero;
        for(Comprables c: this.propiedades){
            if(!c.getHipotecado()) {
                total += c.getPrecio();
                if(c instanceof Solar && ((Solar)c).getConstrucciones() != null){
                    for(Edificios ed : ((Solar) c).getConstrucciones()){
                        total += ed.getPrecio();
                    }
                }
            }
        }
        return total;
    }

    public void imprimirEstadisticas(){

        String aux = "{" +
                "\ndineroInvertido: " + this.getDineroInvertido() +
                "\npagoDeAlquileres: " + this.getPagoAlquileres() +
                "\ncobroDeAlquileres: " + this.getCobroAlquileres() +
                "\npasarPorCasillaDeSalida:" + this.getPasarPorCasilla() +
                "\npremiosInversionesOBote:" + this.getPremiosInversionesOBote() +
                "\nvecesEnLaCarcel: " + this.getVecesCarcel() + "\n}";
        System.out.println(aux);
    }

    @Override
    public String toString(){
        String aux = "{\n" +
                "Nombre: " + this.nombre + "\n" +
                "Avatar: " + this.avatar.getId() + "\n" +
           //     "Tipo: " + this.avatar.getTipo() + "\n" +
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
