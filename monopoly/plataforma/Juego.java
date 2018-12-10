package monopoly.plataforma;

import monopoly.contenido.*;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Juego implements Comando{

    public static ConsolaNormal consola;
    private Tablero tablero;
    private Dados dados;
    private int countTiradas;
    private int vecesDobles;
    private int tirada;
    private int turno;
    private Accion accion;
    private Boolean salir;
    private Jugador jugadorActual;
    static ArrayList<String> turnosJugadores;//ArrayList que guarda el orden de los jugadores (0-primero)...
    //Tiene acceso a paquete

    public Juego() {
        consola=new ConsolaNormal();
        Valor.crearGrupos();//creamos los grupos almacenados en valor
        this.tablero = new Tablero();
        this.accion = new Accion(this.tablero);
        // Inicializar valores
        System.out.println("Bienvenido a MonopolyETSE");
        System.out.println("Antes de comezar a jugar debeis introducir los jugadores de la siguiente forma:");
        System.out.println("\nintroducir jugador <nombre> <tipo>");

        this.accion.crearJugadores();//antes de empezar el menu de partida creamos los jugadores

        this.tablero.imprimirTablero();
        this.dados = new Dados();

        this.turno = 0;
        this.jugadorActual = this.tablero.getJugadores().get(turnosJugadores.get(0)); //set el jugador del primer turno al primer jugador
        this.salir = false;
        String auxCasilla = "";
        this.countTiradas = 0;
        this.vecesDobles = 0;
        this.tirada = 0;
        System.out.println(" El jugador actual es " + jugadorActual.getNombre());
        while (!salir) {
            //System.out.print("$> ");
            //Scanner scanner = new Scanner(System.in);
            //String orden = scanner.nextLine();
            String orden= consola.leer("$> ");
            String[] partes = orden.split(" ");
            String comando = partes[0];
            // Acciones en función del comando introducido
            switch (comando) {
                case "describir":
                    this.describir(partes);
                    break;
                case "comprar":
                    this.comprar();
                    break;
                case "edificar":
                    this.edificar(partes);
                    break;
                case "vender":
                    this.vender(partes);
                    break;
                case "hipotecar":
                    this.hipotecar(partes);
                    break;
                case "deshipotecar":
                    this.deshipotecar(partes);
                    break;
                case "lanzar":
                    this.lanzar(partes);
                    break;
                case "acabar":
                    this.acabar(partes);
                    break;
                case "jugador"://describe al jugador actual
                    this.jugador(partes);
                    break;
                case "salir":
                    this.salir(partes);
                    break;
                case "listar":
                    this.listar(partes);
                    break;
                case "ver":
                    this.ver(partes);
                    break;
                case "cambiar":
                    this.cambiar(partes);
                    break;
                case "estadisticas":
                    this.estadisticas(partes);
                    break;
                default:
                    consola.imprimir("\nComando incorrecto.");
                    break;
            }
        }
    }

    public Tablero getTablero() {
        return this.tablero;
    }

    public Dados getDados() {
        return this.dados;
    }

    public static ArrayList<String> getTurnosJugadores() {
        return turnosJugadores;
    }

    public void describir(String[] partes){
        String auxCasilla;
        if(partes.length<3) consola.imprimir("\n Comando incorrecto");
        else if(partes[1].equals("jugador")) {
            Jugador jugadorDescribir = tablero.getJugadores().get(partes[2]);//buscamos el jugador en el hashmap de jugadores de tablero
            if (jugadorDescribir != null) consola.imprimir(jugadorDescribir.toString());//si existe lo imprimimos
            else consola.imprimir("\nEl jugador no existe");
        }else if(partes[1].equals("casilla")){
            auxCasilla = "";
            for(int i = 2; i < partes.length - 1;i++) {
                auxCasilla += partes[i] + " ";
            }
            Casilla casilla = tablero.getCasillas().get(auxCasilla + partes[partes.length-1]);//buscamos la casilla en el hashmap de casillas del tablero
            if(casilla!=null) consola.imprimir(casilla.toString());//si existe la imprimimos
            else consola.imprimir("\nLa casilla no existe");
        }else if(partes[1].equals("avatar")){
            Avatar avatar = tablero.getAvatares().get(partes[2]);//buscamos el avatar en el hashmap de avatares del tablero
            if(avatar!=null) consola.imprimir(avatar.toString());//si existe lo imprimimos
            else consola.imprimir("\nEl avatar no existe.");
            System.out.flush();
        }else consola.imprimir("\nNo existe el tipo de elemento " + partes[1]);
    }

    public void comprar(){
        this.accion.comprar(jugadorActual);
    }

    public void edificar(String[] partes){
        if(partes.length ==2){
            accion.edificar(jugadorActual,partes[1]);
        }else{
            consola.imprimir("Comando incorrecto");
        }
    }

    public void vender(String[] partes){
        String auxCasilla = "";
        if(partes.length >= 4){
            auxCasilla = "";
            for(int i = 2; i < partes.length - 2;i++) {
                auxCasilla += partes[i] + " ";
            }
            accion.venderConstrucciones(jugadorActual,tablero.getCasillas().get(auxCasilla + partes[partes.length-2]),partes[1],partes[partes.length-1].toCharArray()[0] - '0');
        }else{
            consola.imprimir("Comando incorrecto");
        }
    }

    public void hipotecar(String[] partes){
        String auxCasilla = "";
        auxCasilla = "";
        for(int i = 1; i < partes.length - 1;i++) {
            auxCasilla += partes[i] + " ";
        }
        if(partes.length<2 || partes.length >4) consola.imprimir("\n Comando incorrecto");
        else if(tablero.getCasillas().get(auxCasilla + partes[partes.length-1])!=null)//si existe la casilla
            accion.hipotecar(tablero.getCasillas().get(auxCasilla + partes[partes.length-1]),jugadorActual);
        else consola.imprimir("La casilla que quieres hipotecar no existe :(");
    }

    public void deshipotecar(String[] partes) {
        String auxCasilla = "";
        for (int i = 1; i < partes.length - 1; i++) {
            auxCasilla += partes[i] + " ";
        }
        if (partes.length < 2 || partes.length > 4) consola.imprimir("\n Comando incorrecto");
        else if (tablero.getCasillas().get(auxCasilla + partes[partes.length - 1]) != null)//si existe la casilla
            accion.desHipotecar(tablero.getCasillas().get(auxCasilla + partes[partes.length - 1]), jugadorActual);
        else consola.imprimir("La casilla que quieres hipotecar no existe :(");
    }

    public void lanzar(String[] partes){
        if(partes.length>=2 && partes[1].equals("dados")) {
            if (this.countTiradas == 0) {//si tienes tiradas pendientes te muestra la tirada
                this.dados.lanzarDados();
                this.jugadorActual.anhadirVecesDados();
                this.tirada = dados.getSuma();
                this.dados.getDescripcion();
                if (jugadorActual.getAvatar().getEncarcelado() == 0) {//si no esta encarcelado
                    if(jugadorActual.getAvatar() instanceof Coche && jugadorActual.getAvatar().getModoAvanzado()) {
                        if(((Coche) jugadorActual.getAvatar()).getNumTiradas() > 0){
                            ((Coche) jugadorActual.getAvatar()).moverCasilla(tirada);
                        }else if(((Coche) jugadorActual.getAvatar()).getNumTiradas() == 0) {
                            consola.imprimir("El coche ya ha realizado todas las tiradas que podía en su turno");
                        }else{
                            ((Coche) jugadorActual.getAvatar()).moverCasilla(tirada);
                            consola.imprimir(jugadorActual.getNombre() + " aún le quedan " + (((Coche) jugadorActual.getAvatar()).getNumTiradas()*-1 + 1) + " turnos para poder volver a tirar");
                        }
                        if(((Coche) jugadorActual.getAvatar()).getNumTiradas() <= 0) countTiradas++;
                        if(jugadorActual.getAvatar().getEncarcelado() != 0){
                            this.countTiradas++;
                            ((Coche) jugadorActual.getAvatar()).setNumTiradas(0);
                        }
                    }else {
                        if (!dados.sonDobles())
                            this.countTiradas++;//si no son dobles aumentamos una tirada si fuesen dobles no se aumenta porque tendria derecho a volver a tirar
                        else vecesDobles++;//si son dobles incrementamos veces dobles
                        if (vecesDobles >= 3) {//si saco dobles 3 veces va a la carcel y se cancelan sus acciones pendientes
                            accion.irCarcel(jugadorActual);
                            this.countTiradas++;
                            consola.imprimir("El jugador " + jugadorActual.getNombre() + " ha sacado dobles 3 veces.");
                        } else {
                            if(jugadorActual.getAvatar() instanceof Pelota && jugadorActual.getAvatar().getModoAvanzado() && tirada <= 4){
                                consola.imprimir("El avatar " + jugadorActual.getAvatar().getId() + " retrocede " + tirada + " posiciones");
                            }else
                                consola.imprimir("El avatar " + jugadorActual.getAvatar().getId() + " avanza " + tirada + " posiciones, desde " + jugadorActual.getAvatar().getCasilla().getNombre() + " hasta " + Valor.casillas.get((jugadorActual.getAvatar().getCasilla().getPosicion() + tirada) % 40).getNombre());
                            jugadorActual.getAvatar().moverCasilla(tirada);
                            if(!(jugadorActual.getAvatar() instanceof Pelota && jugadorActual.getAvatar().getModoAvanzado()))
                                jugadorActual.getAvatar().getCasilla().accionCaer(jugadorActual, tirada, accion);
                            if (jugadorActual.getAvatar().getEncarcelado() != 0) countTiradas++;
                        }
                    }
                }else {
                    if (dados.sonDobles()) {
                        jugadorActual.getAvatar().setEncarcelado(0);
                        if(jugadorActual.getAvatar() instanceof Coche && jugadorActual.getAvatar().getModoAvanzado()) {
                            if (((Coche) jugadorActual.getAvatar()).getNumTiradas() > 0) {
                                ((Coche) jugadorActual.getAvatar()).moverCasilla(tirada);
                            } else if (((Coche) jugadorActual.getAvatar()).getNumTiradas() == 0) {
                                consola.imprimir("El coche ya ha realizado todas las tiradas que podía en su turno");
                            } else {
                                ((Coche) jugadorActual.getAvatar()).moverCasilla(tirada);
                                consola.imprimir(jugadorActual.getNombre() + " aún le quedan " + (((Coche) jugadorActual.getAvatar()).getNumTiradas() * -1 + 1) + " turnos para poder volver a tirar");
                            }
                            if (((Coche) jugadorActual.getAvatar()).getNumTiradas() <= 0)
                                this.countTiradas++;
                            if (jugadorActual.getAvatar().getEncarcelado() != 0) {
                                this.countTiradas++;
                                ((Coche) jugadorActual.getAvatar()).setNumTiradas(0);
                            }
                        }
                        jugadorActual.getAvatar().moverCasilla(tirada);
                        jugadorActual.getAvatar().getCasilla().accionCaer(jugadorActual, tirada,accion);
                        this.countTiradas++;
                    } else {
                        jugadorActual.getAvatar().modificarEncarcelado(1);
                        this.countTiradas++;
                        if (jugadorActual.getAvatar().getEncarcelado() > 3) {
                            this.countTiradas = 0;
                            accion.salirCarcel(jugadorActual);
                            //System.out.println("El avatar " + jugadorActual.getAvatar().getId() + " avanza " + tirada + " posiciones, desde " + this.jugadorActual.getAvatar().getCasilla().getNombre() + " hasta " + Valor.casillas.get((jugadorActual.getAvatar().getCasilla().getPosicion() + tirada) % 40).getNombre());
                            jugadorActual.getAvatar().moverCasilla(tirada);
                            if(jugadorActual.getAvatar() instanceof Coche && jugadorActual.getAvatar().getModoAvanzado()){
                                if(this.tirada > 4)
                                    ((Coche) jugadorActual.getAvatar()).setNumTiradas(3);
                            }
                            countTiradas++;
                            jugadorActual.getAvatar().getCasilla().accionCaer(jugadorActual, tirada, accion);
                        }
                        else
                            consola.imprimir("El jugador " + jugadorActual.getNombre() + " sigue en la cárcel");
                    }
                }
                tablero.imprimirTablero();
            } else
                consola.imprimir("No puedes tirar más veces en este turno");
        }
        else consola.imprimir("Comando incorrecto");
    }

    public void acabar(String partes[]){
        if(partes.length != 2){
            consola.imprimir("Comando incorrecto");
        }else{
            if(partes[1].equals("turno")) {//si no hay alquileres por pagar o tiradas pendientes deja finalizar el turno
                if (this.countTiradas != 0) {
                    this.turno++;
                    if(jugadorActual.getAvatar() instanceof Coche && jugadorActual.getAvatar().getModoAvanzado()) {
                        ((Coche) jugadorActual.getAvatar()).setPoderComprar(true);
                        ((Coche) jugadorActual.getAvatar()).anhadirTirada();
                        if(jugadorActual.getAvatar().getEncarcelado() != 0)
                            ((Coche) jugadorActual.getAvatar()).setNumTiradas(1);
                    }
                    for(Propiedades p: Valor.getComprables()){
                       p.reducirTurnosTratos(jugadorActual);
                    }
                    jugadorActual = tablero.getJugadores().get(turnosJugadores.get(this.turno % turnosJugadores.size()));
                    this.countTiradas = 0;
                    this.vecesDobles = 0;
                    consola.imprimir("El jugador actual es " + jugadorActual.getNombre());
                } else {
                    consola.imprimir("No se puede finalizar el turno sin haber tirado los dados");
                }
            }else{
                consola.imprimir("Comando incorrecto");
            }
        }
    }

    public void salir(String partes[]){
        if(partes.length==2 && partes[1].equals("carcel")){
            if(countTiradas == 0){
                accion.salirCarcel(jugadorActual);
                this.countTiradas = 0;
            }else{
                if(jugadorActual.getAvatar().getEncarcelado()>0)
                    consola.imprimir("No puedes pagar para salir de la cárcel una vez que has lanzado los dados");
                else
                    consola.imprimir("No estás en la cárcel");
            }
        }
        else if(partes.length==1){
            consola.imprimir("\nGracias por jugar.");
            salir = true;
        }
    }

    public void listar(String partes[]){
        if(partes.length>=2){
            if(partes[1].equals("jugadores")){
                tablero.listarJugadores();
                tablero.imprimirTablero();
            }else if(partes[1].equals("avatares")) {
                tablero.listarAvatares();
                tablero.imprimirTablero();
            }else if(partes[1].equals("edificios")){
                if(partes.length == 2)
                    tablero.listarCasillasEdificadas();
                else
                if(Valor.getGrupos().containsKey(partes[2]))
                    Valor.getGrupos().get(partes[2]).listarEdificiosGrupo();
                else
                    consola.imprimir("El grupo introducido no existe");
            }
            if(partes[1].equals("enventa")){
                tablero.listarPropiedades();
                tablero.imprimirTablero();
            }
        }else
            consola.imprimir("Comando incorrecto");
    }

    public void jugador(String partes[]){
        if(partes.length == 1)
            consola.imprimir(jugadorActual.getDescripcionInicial());
        else
            consola.imprimir("Comando incorrecto");
    }

    public void ver(String partes[]){
        if(partes.length==2){
            if(partes[1].equals("tablero")) tablero.imprimirTablero();
        }else
            consola.imprimir("Comando incorrecto");
    }

    public void cambiar(String partes[]){
        if(partes.length==2 && partes[1].equals("modo")){
            if(this.countTiradas==0) {
                tablero.cambiarModo(jugadorActual);
                if (jugadorActual.getAvatar().getModoAvanzado())
                    consola.imprimir("El jugador " + jugadorActual.getNombre() + " está ahora en modo avanzado de tipo " + jugadorActual.getAvatar().getTipo());
                else
                    consola.imprimir("El jugador " + jugadorActual.getNombre() + " ya no está en modo avanzado");
            }else{
                consola.imprimir("El modo de movimiento debe ser cambiado antes de tirar los dados");
            }
        }else{
            consola.imprimir("Comando incorrecto");
        }
    }

    public void estadisticas(String partes[]){
        if(partes.length==1)
            tablero.obtenerEstadisticas();
        else if(partes.length==2){
            if(turnosJugadores.contains(partes[1])){
                tablero.getJugadores().get(partes[1]).imprimirEstadisticas();
            }else
                consola.imprimir("El jugador introducido no existe");
        }
        else
            consola.imprimir("Comando incorrecto");
    }
}
