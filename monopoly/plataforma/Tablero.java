package monopoly.plataforma;
import monopoly.contenido.Avatar;
import monopoly.contenido.Casilla;
import monopoly.contenido.Comprables;
import monopoly.contenido.Jugador;

import java.util.HashMap;

import java.util.Iterator;

public class Tablero {

    public final static int LONGITUDCASILLA = 20;
    private HashMap<String,Casilla> casillas;
    private HashMap<String,Jugador> jugadores;
    private HashMap<String,Avatar> avatares;
    private Jugador banca;
    private int vueltas;

    public Tablero(){
        this.jugadores = new HashMap<>();
        this.avatares = new HashMap<>();
        this.casillas = new HashMap<>();
        for(Casilla cas: Valor.casillas){
            this.casillas.put(cas.getNombre(),cas);
        }
        this.banca = new Jugador();
        this.vueltas = 0;
    }

    public Tablero(HashMap<String,Jugador> jugadores){
        this.jugadores = jugadores;
        this.avatares = new HashMap<>();
        this.casillas = new HashMap<>();
        for(Casilla cas: Valor.casillas){
            this.casillas.put(cas.getNombre(),cas);
        }
        this.banca = new Jugador();
        this.vueltas = 0;
    }

    public HashMap<String,Casilla> getCasillas(){
        return this.casillas;
    }

    //No tiene sentido un setter de casillas, pues estas no se modifican a lo largo de un programa

    public int getVueltas(){
        return this.vueltas;
    }

    public void setVueltas(int vueltas){
        this.vueltas = vueltas;
    }

    public void modificarVueltas(int vueltas){
        this.vueltas += vueltas;
    }

    public HashMap<String,Jugador> getJugadores(){ return this.jugadores; }

    public void setJugadores(HashMap<String,Jugador> jugs){
        if(jugs != null)
            this.jugadores = jugs;
    }

    public void addJugadores(Jugador jug){
        if(jug != null)
            this.jugadores.put(jug.getNombre(),jug);
    }

    public HashMap<String,Avatar> getAvatares(){
        return this.avatares;
    }

    public Jugador getBanca(){
        return this.banca;
    }

    //No tiene sentido el setter de Banca, ya que este jugador no se ve modificado a lo largo del programa

    public void cambiarModo(){
        Iterator<Avatar> av_it = this.avatares.values().iterator();
        while(av_it.hasNext()){
            Avatar av = av_it.next();
            av.switchMode();
        }
    }

    private void printBarraBaja(){
        System.out.printf("_");
        for(int i = 0; i < 11; i++){
            for(int j = 0; j < LONGITUDCASILLA; j++){
                System.out.printf("_");
            }
            System.out.printf(" ");
        }
        System.out.printf("\n");
    }

    private String rellenarCasilla(Casilla casilla){
        StringBuilder aux = new StringBuilder();
//        Iterator<Avatar> it = (Iterator) this.avatares.entrySet().iterator();

        Iterator<Avatar> avatar_i = this.avatares.values().iterator();
        while(avatar_i.hasNext()) {
            Avatar avatar = avatar_i.next();
            if(avatar.getCasilla().equals(casilla)){
                aux.append("&" + avatar.getId());
            }
        }

        return aux.toString();
    }

    private void printCasilla(int i){
        StringBuilder aux = new StringBuilder();
        int lenAux;
        System.out.printf("\033[0;1m%s",Valor.casillas.get(i).getGrupo().getColor() + Valor.casillas.get(i).getNombre());

        aux.append(" " + rellenarCasilla(Valor.casillas.get(i)));
        lenAux = LONGITUDCASILLA - aux.length() - Valor.casillas.get(i).getNombre().length();
        for(int j = 0; j < lenAux ; j++) {
            aux.append(" ");
        }
        System.out.printf("\033[0;1m%s|", aux.toString());
    }
    public void imprimirTablero(){

        //Imprimir primera línea barras bajas
        printBarraBaja();

        System.out.printf("|");
        //Imprimir primeras líneas
        for(int i = 20; i < 31;i++){
            printCasilla(i);
        }
        System.out.printf("\n");
        printBarraBaja();

        int k=31;
        for(int i=19;i> 10;i--){
            System.out.printf("|");
            printCasilla(i);
            for(int j = 0 ; j<LONGITUDCASILLA*9+8;j++){
                System.out.printf(" ");
            }
            System.out.printf("|");
            printCasilla(k);
            k++;
            System.out.printf("\n");
            if(i != 11) {
                for (int j = 0; j < LONGITUDCASILLA + 1; j++) {
                    System.out.printf("_");
                }
                for (int t = 0; t < LONGITUDCASILLA * 9 + 9; t++) {
                    System.out.printf(" ");
                }
                for (int j = 0; j < LONGITUDCASILLA + 1; j++) {
                    System.out.printf("_");
                }
                System.out.printf("\n");
            }
        }

        printBarraBaja();
        System.out.printf("|");
        for(int i = 10; i >= 0 ;i--){
            printCasilla(i);
        }
        System.out.printf("\n");
        printBarraBaja();
    }

    public void listarJugadores(){
        Iterator<Jugador> jug_i = this.jugadores.values().iterator();
        while(jug_i.hasNext()) {
            Jugador jug = jug_i.next();
            System.out.println(jug.toString());
        }
    }
    public void listarAvatares(){
        Iterator<Avatar> ava_i = this.avatares.values().iterator();
        while(ava_i.hasNext()) {
            Avatar ava = ava_i.next();
            System.out.println(ava.toString());
        }
    }
    /*
    public void listarPropiedades(){
        for(Casilla cas: Valor.casillas){
                if(cas.getPropietario().getNombre().equals("Banca") && cas.getPrecio()>0){
                    System.out.println(cas.toString());
                }
        }
    }*/
}
