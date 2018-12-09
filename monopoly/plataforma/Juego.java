package monopoly.plataforma;

import monopoly.contenido.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Juego implements Comando{

    private Tablero tablero;
    private Dados dados;
    static ArrayList<String> turnosJugadores;//ArrayList que guarda el orden de los jugadores (0-primero)...
    //Tiene acceso a paquete

    public Juego() {
        Valor.crearGrupos();//creamos los grupos almacenados en valor
        this.tablero = new Tablero();
        Accion accion = new Accion(this.tablero);
        // Inicializar valores
        System.out.println("Bienvenido a MonopolyETSE");
        System.out.println("Antes de comezar a jugar debeis introducir los jugadores de la siguiente forma:");
        System.out.println("\nintroducir jugador <nombre> <tipo>");

        accion.crearJugadores();//antes de empezar el menu de partida creamos los jugadores

        this.tablero.imprimirTablero();
        this.dados = new Dados();

        int turno = 0;
        Juego juego = new Juego();
        Jugador jugadorActual = this.tablero.getJugadores().get(juego.turnosJugadores.get(0)); //set el jugador del primer turno al primer jugador
        boolean salir = false;
        String auxCasilla = "";
        int countTiradas = 0;
        int vecesDobles = 0;
        int tirada = 0;
        System.out.println(" El jugador actual es " + jugadorActual.getNombre());
        while (!salir) {
            System.out.print("$> ");
            Scanner scanner = new Scanner(System.in);
            String orden = scanner.nextLine();
            String[] partes = orden.split(" ");
            String comando = partes[0];
            // Acciones en función del comando introducido
            switch (comando) {

                case "describir":
                    juego.describir(partes, this.tablero);
                    break;
                case "comprar":
                    juego.comprar(jugadorActual, accion);
                    break;
                case "edificar":
                    juego.edificar(partes, jugadorActual, accion);
                    break;
                case "vender":
                    juego.vender(partes, jugadorActual, this.tablero, accion);
                    break;
                case "hipotecar":
                    juego.hipotecar(partes, jugadorActual, this.tablero, accion);
                    break;
                case "deshipotecar":
                    juego.deshipotecar(partes, jugadorActual, this.tablero, accion);
                    break;
                case "lanzar":
                    juego.lanzar(partes, jugadorActual, this.tablero, accion, countTiradas, tirada, vecesDobles, juego.getDados());
                    break;
                case "acabar":
                    juego.acabar(partes, jugadorActual, this.tablero, countTiradas, vecesDobles, turno, juego.turnosJugadores);
                    break;
                case "jugador"://describe al jugador actual
                    juego.jugador(partes, jugadorActual);
                    break;
                case "salir":
                    juego.salir(partes, jugadorActual, countTiradas, accion, salir);
                    break;
                case "listar":
                    juego.listar(partes, this.tablero);
                    break;
                case "ver":
                    juego.ver(partes, this.tablero);
                    break;
                case "cambiar":
                    juego.cambiar(partes, this.tablero, jugadorActual, countTiradas);
                    break;
                case "estadisticas":
                    juego.estadisticas(partes, this.tablero, juego.turnosJugadores);
                    break;
                default:
                    System.out.println("\nComando incorrecto.");
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

    public void describir(String[] partes,Tablero tablero){
        String auxCasilla;
        if(partes.length<3) System.out.println("\n Comando incorrecto");
        else if(partes[1].equals("jugador")) {
            Jugador jugadorDescribir = tablero.getJugadores().get(partes[2]);//buscamos el jugador en el hashmap de jugadores de tablero
            if (jugadorDescribir != null) System.out.println(jugadorDescribir);//si existe lo imprimimos
            else System.out.println("\nEl jugador no existe");
        }else if(partes[1].equals("casilla")){
            auxCasilla = "";
            for(int i = 2; i < partes.length - 1;i++) {
                auxCasilla += partes[i] + " ";
            }
            Casilla casilla = tablero.getCasillas().get(auxCasilla + partes[partes.length-1]);//buscamos la casilla en el hashmap de casillas del tablero
            if(casilla!=null) System.out.println(casilla);//si existe la imprimimos
            else System.out.println("\nLa casilla no existe");
        }else if(partes[1].equals("avatar")){
            Avatar avatar = tablero.getAvatares().get(partes[2]);//buscamos el avatar en el hashmap de avatares del tablero
            if(avatar!=null) System.out.println(avatar);//si existe lo imprimimos
            else System.out.println("\nEl avatar no existe.");
            System.out.flush();
        }else System.out.println("\nNo existe el tipo de elemento " + partes[1]);
    }

    public void comprar(Jugador jugadorActual, Accion accion){
        accion.comprar(jugadorActual);
    }

    public void edificar(String[] partes,Jugador jugadorActual, Accion accion){
        if(partes.length ==2){
            accion.edificar(jugadorActual,partes[1]);
        }else{
            System.out.println("Comando incorrecto");
        }
    }

    public void vender(String[] partes,Jugador jugadorActual,Tablero tablero, Accion accion){
        String auxCasilla = "";
        if(partes.length >= 4){
            auxCasilla = "";
            for(int i = 2; i < partes.length - 2;i++) {
                auxCasilla += partes[i] + " ";
            }
            accion.venderConstrucciones(jugadorActual,tablero.getCasillas().get(auxCasilla + partes[partes.length-2]),partes[1],partes[partes.length-1].toCharArray()[0] - '0');
        }else{
            System.out.println("Comando incorrecto");
        }
    }

    public void hipotecar(String[] partes,Jugador jugadorActual,Tablero tablero, Accion accion){
        String auxCasilla = "";
        auxCasilla = "";
        for(int i = 1; i < partes.length - 1;i++) {
            auxCasilla += partes[i] + " ";
        }
        if(partes.length<2 || partes.length >4) System.out.println("\n Comando incorrecto");
        else if(tablero.getCasillas().get(auxCasilla + partes[partes.length-1])!=null)//si existe la casilla
            accion.hipotecar(tablero.getCasillas().get(auxCasilla + partes[partes.length-1]),jugadorActual);
        else System.out.println("La casilla que quieres hipotecar no existe :(");
    }

    public void deshipotecar(String[] partes,Jugador jugadorActual,Tablero tablero, Accion accion) {
        String auxCasilla = "";
        for (int i = 1; i < partes.length - 1; i++) {
            auxCasilla += partes[i] + " ";
        }
        if (partes.length < 2 || partes.length > 4) System.out.println("\n Comando incorrecto");
        else if (tablero.getCasillas().get(auxCasilla + partes[partes.length - 1]) != null)//si existe la casilla
            accion.desHipotecar(tablero.getCasillas().get(auxCasilla + partes[partes.length - 1]), jugadorActual);
        else System.out.println("La casilla que quieres hipotecar no existe :(");
    }

    public void lanzar(String[] partes,Jugador jugadorActual,Tablero tablero, Accion accion, int countTiradas, int tirada, int vecesDobles, Dados dados){
        if(partes.length>=2 && partes[1].equals("dados")) {
            if (countTiradas == 0) {//si tienes tiradas pendientes te muestra la tirada
                dados.lanzarDados();
                jugadorActual.anhadirVecesDados();
                tirada = dados.getSuma();
                dados.getDescripcion();
                if (jugadorActual.getAvatar().getEncarcelado() == 0) {//si no esta encarcelado
                    if(jugadorActual.getAvatar() instanceof Coche && jugadorActual.getAvatar().getModoAvanzado()) {
                        if(((Coche) jugadorActual.getAvatar()).getNumTiradas() > 0){
                            ((Coche) jugadorActual.getAvatar()).moverCasilla(tirada);
                        }else if(((Coche) jugadorActual.getAvatar()).getNumTiradas() == 0) {
                            System.out.println("El coche ya ha realizado todas las tiradas que podía en su turno");
                        }else{
                            ((Coche) jugadorActual.getAvatar()).moverCasilla(tirada);
                            System.out.println(jugadorActual.getNombre() + " aún le quedan " + (((Coche) jugadorActual.getAvatar()).getNumTiradas()*-1 + 1) + " turnos para poder volver a tirar");
                        }
                        if(((Coche) jugadorActual.getAvatar()).getNumTiradas() <= 0) countTiradas++;
                        if(jugadorActual.getAvatar().getEncarcelado() != 0){
                            countTiradas++;
                            ((Coche) jugadorActual.getAvatar()).setNumTiradas(0);
                        }
                    }else {
                        if (!dados.sonDobles())
                            countTiradas++;//si no son dobles aumentamos una tirada si fuesen dobles no se aumenta porque tendria derecho a volver a tirar
                        else vecesDobles++;//si son dobles incrementamos veces dobles
                        if (vecesDobles >= 3) {//si saco dobles 3 veces va a la carcel y se cancelan sus acciones pendientes
                            accion.irCarcel(jugadorActual);
                            countTiradas++;
                            System.out.println("El jugador " + jugadorActual.getNombre() + " ha sacado dobles 3 veces.");
                        } else {
                            if(jugadorActual.getAvatar() instanceof Pelota && jugadorActual.getAvatar().getModoAvanzado() && tirada <= 4){
                                System.out.println("El avatar " + jugadorActual.getAvatar().getId() + " retrocede " + tirada + " posiciones");
                            }else
                                System.out.println("El avatar " + jugadorActual.getAvatar().getId() + " avanza " + tirada + " posiciones, desde " + jugadorActual.getAvatar().getCasilla().getNombre() + " hasta " + Valor.casillas.get((jugadorActual.getAvatar().getCasilla().getPosicion() + tirada) % 40).getNombre());
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
                                System.out.println("El coche ya ha realizado todas las tiradas que podía en su turno");
                            } else {
                                ((Coche) jugadorActual.getAvatar()).moverCasilla(tirada);
                                System.out.println(jugadorActual.getNombre() + " aún le quedan " + (((Coche) jugadorActual.getAvatar()).getNumTiradas() * -1 + 1) + " turnos para poder volver a tirar");
                            }
                            if (((Coche) jugadorActual.getAvatar()).getNumTiradas() <= 0)
                                countTiradas++;
                            if (jugadorActual.getAvatar().getEncarcelado() != 0) {
                                countTiradas++;
                                ((Coche) jugadorActual.getAvatar()).setNumTiradas(0);
                            }
                        }
                        jugadorActual.getAvatar().moverCasilla(tirada);
                        jugadorActual.getAvatar().getCasilla().accionCaer(jugadorActual, tirada,accion);
                        countTiradas++;
                    } else {
                        jugadorActual.getAvatar().modificarEncarcelado(1);
                        countTiradas++;
                        if (jugadorActual.getAvatar().getEncarcelado() > 3) {
                            countTiradas = 0;
                            accion.salirCarcel(jugadorActual);
                            //System.out.println("El avatar " + jugadorActual.getAvatar().getId() + " avanza " + tirada + " posiciones, desde " + this.jugadorActual.getAvatar().getCasilla().getNombre() + " hasta " + Valor.casillas.get((jugadorActual.getAvatar().getCasilla().getPosicion() + tirada) % 40).getNombre());
                            jugadorActual.getAvatar().moverCasilla(tirada);
                            if(jugadorActual.getAvatar() instanceof Coche && jugadorActual.getAvatar().getModoAvanzado()){
                                if(tirada > 4)
                                    ((Coche) jugadorActual.getAvatar()).setNumTiradas(3);
                            }
                            countTiradas++;
                            jugadorActual.getAvatar().getCasilla().accionCaer(jugadorActual, tirada, accion);
                        }
                        else
                            System.out.println("El jugador " + jugadorActual.getNombre() + " sigue en la cárcel");
                    }
                }
                tablero.imprimirTablero();
            } else
                System.out.println("No puedes tirar más veces en este turno");
        }
        else System.out.println("Comando incorrecto");
    }

    public void acabar(String partes[], Jugador jugadorActual,Tablero tablero, int countTiradas, int vecesDobles, int turno, ArrayList<String> turnosJugadores){
        if(partes.length != 2){
            System.out.println("Comando incorrecto");
        }else{
            if(partes[1].equals("turno")) {//si no hay alquileres por pagar o tiradas pendientes deja finalizar el turno
                if (countTiradas != 0) {
                    turno++;
                    if(jugadorActual.getAvatar() instanceof Coche && jugadorActual.getAvatar().getModoAvanzado()) {
                        ((Coche) jugadorActual.getAvatar()).setPoderComprar(true);
                        ((Coche) jugadorActual.getAvatar()).anhadirTirada();
                        if(jugadorActual.getAvatar().getEncarcelado() != 0)
                            ((Coche) jugadorActual.getAvatar()).setNumTiradas(1);
                    }
                    jugadorActual = tablero.getJugadores().get(turnosJugadores.get(turno % turnosJugadores.size()));
                    countTiradas = 0;
                    vecesDobles = 0;
                    System.out.println("El jugador actual es " + jugadorActual.getNombre());
                } else {
                    System.out.println("No se puede finalizar el turno sin haber tirado los dados");
                }
            }else{
                System.out.println("Comando incorrecto");
            }
        }
    }

    public void salir(String partes[],Jugador jugadorActual, int countTiradas, Accion accion, Boolean salir){
        if(partes.length==2 && partes[1].equals("carcel")){
            if(countTiradas ==0){
                accion.salirCarcel(jugadorActual);
                countTiradas = 0;
            }else{
                if(jugadorActual.getAvatar().getEncarcelado()>0)
                    System.out.println("No puedes pagar para salir de la cárcel una vez que has lanzado los dados");
                else
                    System.out.println("No estás en la cárcel");
            }
        }
        else if(partes.length==1){
            System.out.println("\nGracias por jugar.");
            salir = true;
        }
    }

    public void listar(String partes[], Tablero tablero){
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
                    System.out.println("El grupo introducido no existe");
            }
            if(partes[1].equals("enventa")){
                tablero.listarPropiedades();
                tablero.imprimirTablero();
            }
        }else
            System.out.println("Comando incorrecto");
    }

    public void jugador(String partes[],Jugador jugadorActual){
        if(partes.length == 1)
            System.out.println(jugadorActual.getDescripcionInicial());
        else
            System.out.println("Comando incorrecto");
    }

    public void ver(String partes[],Tablero tablero){
        if(partes.length==2){
            if(partes[1].equals("tablero")) tablero.imprimirTablero();
        }else
            System.out.println("Comando incorrecto");
    }

    public void cambiar(String partes[], Tablero tablero, Jugador jugadorActual, int countTiradas){
        if(partes.length==2 && partes[1].equals("modo")){
            if(countTiradas==0) {
                tablero.cambiarModo(jugadorActual);
                if (jugadorActual.getAvatar().getModoAvanzado())
                    System.out.println("El jugador " + jugadorActual.getNombre() + " está ahora en modo avanzado de tipo " + jugadorActual.getAvatar().getTipo());
                else
                    System.out.println("El jugador " + jugadorActual.getNombre() + " ya no está en modo avanzado");
            }else{
                System.out.println("El modo de movimiento debe ser cambiado antes de tirar los dados");
            }
        }else{
            System.out.println("Comando incorrecto");
        }
    }

    public void estadisticas(String partes[], Tablero tablero, ArrayList<String> turnosJugadores){
        if(partes.length==1)
            tablero.obtenerEstadisticas();
        else if(partes.length==2){
            if(turnosJugadores.contains(partes[1])){
                tablero.getJugadores().get(partes[1]).imprimirEstadisticas();
            }else
                System.out.println("El jugador introducido no existe");
        }
        else
            System.out.println("Comando incorrecto");
    }
}
