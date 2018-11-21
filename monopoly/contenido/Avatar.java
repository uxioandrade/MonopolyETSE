package monopoly.contenido;

import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Valor;
public class Avatar {

    private String id;
    private String tipo;
    private Casilla casilla;
    private Jugador jugador;
    private Tablero tablero;
    private int numVueltas;
    private int encarcelado;
    private boolean modoAvanzado;

    private final ArrayList<String> listaTipos = new ArrayList<String>() {{
        add("sombrero");
        add("coche");
        add("esfinge");
        add("pelota");
    }};

    //Función privada que asigna un id aleatorio no repetido a un avatar
    private void asignarId(){
        Random rnd = new Random();
        String aux = "";
        boolean repetida = true;
        while(repetida){
            aux =  "" + (char)(rnd.nextInt('Z' - 'A' + 1) + 'A');
            if(tablero.getAvatares() != null) {
                if (!tablero.getAvatares().containsKey(aux)) {
                    repetida = false;
                    this.id = aux;
                }
            }else{
                repetida = false;
                this.id = aux;
            }
        }
    }

    private String asignarTipoAleatorio(){
        Random rnd = new Random();
        return this.getListaTipos().get(rnd.nextInt(4));
    }

    public Avatar(String tipo,Jugador jug, Tablero tabla){
        //Inicializamos el tipo a un valor arbitrario
        this.jugador = jug;
        this.tablero = tabla;
        if(this.getListaTipos().contains(tipo))
            this.tipo = tipo;
        else
            this.tipo = asignarTipoAleatorio();
        this.casilla = Valor.casillas.get(0);
        asignarId();
        this.tablero.getAvatares().put(this.id,this);
        this.encarcelado = 0;
        this.numVueltas = 0;
        this.modoAvanzado = false;
    }

    public Avatar(Jugador jug, Tablero tabla){
        //Inicializamos el tipo a un valor arbitrario
        this.jugador = jug;
        this.tablero = tabla;
        this.tipo = this.asignarTipoAleatorio();
        this.casilla = Valor.casillas.get(0);
        asignarId();
        this.tablero.getAvatares().put(this.id,this);
        this.encarcelado = 0;
        this.numVueltas = 0;
        this.modoAvanzado = false;
    }

    public String getId(){
        return this.id;
    }

    //No tiene sentido el setter de Id, pues es único e invariable a lo largo de la partida

    private ArrayList<String> getListaTipos(){
        return (ArrayList) this.listaTipos.clone();
    }

    //El atributo listatipos es constante, no tiene sentido crear un getter

    public String getTipo(){
        return this.tipo;
    }

    //El tipo no puede ser modificado una vez empezada la partida

    public Casilla getCasilla(){
        return this.casilla;
    }

    public void setCasilla(Casilla cas){
        if(cas!=null){
            this.casilla.quitarAvatar(this);
            this.casilla = cas;
            this.casilla.anhadirAvatar(this);
        }
    }

    public Jugador getJugador(){
        return this.jugador;
    }

    //No tiene sentido crear el setter de jugador, ya que es el avatar se crea para un jugador en concreto, no tiene sentido modificarlo

    public Tablero getTablero() {
        return this.tablero;
    }
    //El setter para tablero no es necesario, pues el tablero solo se pasa a la instancia de acción al principio. Si se crease un setter y se cambiase el tablero
    //esto implicaría que la partida es una partida nueva

    public int getEncarcelado(){
        return this.encarcelado;
    }

    public void setEncarcelado(int valor){
        this.encarcelado = valor;
    }

    public void modificarEncarcelado(int valor){
        this.encarcelado += valor;
    }

    public int getNumVueltas(){
        return this.numVueltas;
    }

    //No es necesario el setter de numvueltas, ya que este solo se puede modificar de 1 en 1 cuando se completa una vuelta

    public boolean getModoAvanzado(){
        return modoAvanzado;
    }

    public void setModoAvanzado(boolean valor){
        this.modoAvanzado = valor;
    }

    public void switchMode(){
        this.modoAvanzado = !this.modoAvanzado;
    }

    public void moverCasilla(int valor){
        this.casilla.quitarAvatar(this);
        //Caso en el que el movimiento suponga completar una vuelta
        if(this.casilla.getPosicion() + valor > 39){
            this.casilla= Valor.casillas.get(this.casilla.getPosicion() + valor - 40);
            //Se le ingresa al jugador el dinero correspondiente a completar la vuelta
            this.jugador.modificarDinero(Valor.getDineroVuelta());
            this.numVueltas++;
            System.out.println("El jugador " + this.jugador.getNombre() + " recibe " + Valor.getDineroVuelta() + "€ por haber cruzado la salida.");
            //Se recorren los avatares para comprobar si es necesario actualizar el dinero de pasar por la casilla de salida
            Iterator<Avatar> avatar_i = this.tablero.getAvatares().values().iterator();
            while(avatar_i.hasNext()) {
                Avatar avatar = avatar_i.next();
                if(avatar.numVueltas <= this.tablero.getVueltas() + 3) {
                    return;
                }
            }
            this.tablero.modificarVueltas(4);
            Valor.actualizarVuelta();
        }else{
            this.casilla= Valor.casillas.get(this.casilla.getPosicion() + valor);
        }
        this.casilla.anhadirAvatar(this);
    }

    @Override
    public String toString(){
        String aux = "{\n" +
                "Id: " + this.id + "\n" +
                "Tipo: " + this.tipo + "\n" +
                "Jugador: " + this.jugador.getNombre() + "\n" +
                "Casilla: " + this.casilla.getNombre() + "\n" +
                "}\n";
        return aux;
    }
}
