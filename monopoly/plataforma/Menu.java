package monopoly.plataforma;/*import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;*/

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import monopoly.contenido.Avatar;
import monopoly.contenido.Casilla;
import monopoly.contenido.Dados;
import monopoly.contenido.Jugador;

public class Menu {

    private Tablero tablero;
    private Dados dados;
    private Jugador jugadorActual;
    private Jugador jugadorTurno;
    private int turno;
    static ArrayList<String> turnosJugadores;//ArrayList que guarda el orden de los jugadores (0-primero)...
    //Tiene acceso a paquete

    public Menu() {
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
        this.jugadorActual = this.tablero.getJugadores().get(turnosJugadores.get(0)); //set el jugador del primer turno al primer jugador
        int countTiradas = 0;
        int vecesDobles = 0;
        boolean salir= false;
        int tirada = 0;
        String auxCasilla = "";
        System.out.println(" El jugador actual es " + jugadorActual.getNombre());
        while(!salir) {
            System.out.print("$> ");
            Scanner scanner= new Scanner(System.in);
            String orden= scanner.nextLine();
            String[] partes=orden.split(" ");
            String comando= partes[0];
            // Acciones en función del comando introducido
            switch(comando){

                case "describir":
                    if(partes.length<3) System.out.println("\n Comando incorrecto");
                    else if(partes[1].equals("jugador")) {
                        Jugador jugadorDescribir = this.tablero.getJugadores().get(partes[2]);//buscamos el jugador en el hashmap de jugadores de tablero
                        if (jugadorDescribir != null) System.out.println(jugadorDescribir);//si existe lo imprimimos
                        else System.out.println("\nEl jugador no existe");
                    }else if(partes[1].equals("casilla")){
                        auxCasilla = "";
                        for(int i = 2; i < partes.length - 1;i++) {
                            auxCasilla += partes[i] + " ";
                        }
                        Casilla casilla = this.tablero.getCasillas().get(auxCasilla + partes[partes.length-1]);//buscamos la casilla en el hashmap de casillas del tablero
                        if(casilla!=null) System.out.println(casilla);//si existe la imprimimos
                        else System.out.println("\nLa casilla no existe");
                    }else if(partes[1].equals("avatar")){
                        Avatar avatar = this.tablero.getAvatares().get(partes[2]);//buscamos el avatar en el hashmap de avatares del tablero
                        if(avatar!=null) System.out.println(avatar);//si existe lo imprimimos
                        else System.out.println("\nEl avatar no existe.");
                        System.out.flush();
                    }else System.out.println("\nNo existe el tipo de elemento " + partes[1]);
                    break;
                case "comprar":
                    accion.comprar(jugadorActual);
                    break;
                case "hipotecar":
                    auxCasilla = "";
                    for(int i = 2; i < partes.length - 1;i++) {
                        auxCasilla += partes[i] + " ";
                    }
                    if(partes.length<2 || partes.length >4) System.out.println("\n Comando incorrecto");
                    else if(this.tablero.getCasillas().get(auxCasilla + partes[partes.length-1])!=null)//si existe la casilla
                        accion.hipotecar(this.tablero.getCasillas().get(partes[1]),this.jugadorActual);
                    else System.out.println("La casilla que quieres hipotecar no existe :(");
                    break;
                case "deshipotecar":
                    auxCasilla = "";
                    for(int i = 2; i < partes.length - 1;i++) {
                        auxCasilla += partes[i] + " ";
                    }
                    if(partes.length<2 || partes.length >4) System.out.println("\n Comando incorrecto");
                    else if(this.tablero.getCasillas().get(auxCasilla + partes[partes.length-1])!=null)//si existe la casilla
                        accion.hipotecar(this.tablero.getCasillas().get(partes[1]),this.jugadorActual);
                    else System.out.println("La casilla que quieres hipotecar no existe :(");
                    break;
                case "lanzar":
                    if(partes.length>=2 && partes[1].equals("dados")) {
                        if (countTiradas == 0) {//si tienes tiradas pendientes te muestra la tirada
                            this.dados.lanzarDados();
                            tirada = this.dados.getSuma();
                            this.dados.getDescripcion();
                            if (jugadorActual.getAvatar().getEncarcelado() == 0) {//si no esta encarcelado
                                if (!this.dados.sonDobles()) countTiradas++;//si no son dobles aumentamos una tirada si fuesen dobles no se aumenta porque tendria derecho a volver a tirar
                                else vecesDobles++;//si son dobles incrementamos veces dobles
                                if (vecesDobles >= 3) {//si saco dobles 3 veces va a la carcel y se cancelan sus acciones pendientes
                                    accion.irCarcel(jugadorActual);
                                    countTiradas++;
                                    System.out.println("El jugador " + jugadorActual.getNombre() + " ha sacado dobles 3 veces.");
                                } else {
                                    System.out.println("El avatar " + jugadorActual.getAvatar().getId() + " avanza " + tirada + " posiciones, desde " + this.jugadorActual.getAvatar().getCasilla().getNombre() + " hasta " + Valor.casillas.get((jugadorActual.getAvatar().getCasilla().getPosicion() + tirada) % 40).getNombre());
                                    jugadorActual.getAvatar().moverCasilla(tirada);
                                    accion.caer(jugadorActual, tirada);
                                    if (jugadorActual.getAvatar().getEncarcelado() != 0) countTiradas++;
                                }
                            } else {
                                if (this.dados.sonDobles()) {
                                    jugadorActual.getAvatar().setEncarcelado(0);
                                    jugadorActual.getAvatar().moverCasilla(tirada);
                                    accion.caer(jugadorActual, tirada);
                                    countTiradas++;
                                } else {
                                    jugadorActual.getAvatar().modificarEncarcelado(1);
                                    countTiradas++;
                                    if (jugadorActual.getAvatar().getEncarcelado() >= 3)
                                        accion.salirCarcel(jugadorActual);
                                    else
                                        System.out.println("El jugador " + jugadorActual.getNombre() + " sigue en la cárcel");
                                }
                            }
                            this.tablero.imprimirTablero();
                        } else
                            System.out.println("No puedes tirar más veces en este turno");
                    }
                    else System.out.println("Comando incorrecto");
                    break;

                case "acabar":
                    if(partes.length != 2){
                        System.out.println("Comando incorrecto");
                    }else{
                        if(partes[1].equals("turno")) {//si no hay alquileres por pagar o tiradas pendientes deja finalizar el turno
                            if (countTiradas != 0) {
                                this.turno++;
                                jugadorActual = this.tablero.getJugadores().get(turnosJugadores.get(this.turno % turnosJugadores.size()));
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
                    break;
                case "jugador"://describe al jugador actual
                    if(partes.length == 1)
                        System.out.println(jugadorActual.getDescripcionInicial());
                    else
                        System.out.println("Comando incorrecto");
                    break;
                case "salir":
                    if(partes.length==2 && partes[1].equals("carcel")){
                        if(countTiradas ==0){
                            accion.salirCarcel(jugadorActual);
                            countTiradas++;
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
                    break;
                case "listar":
                    if(partes.length==2){
                        if(partes[1].equals("jugadores")){
                            this.tablero.listarJugadores();
                            this.tablero.imprimirTablero();
                        }
                        if(partes[1].equals("avatares")) {
                            this.tablero.listarAvatares();
                            this.tablero.imprimirTablero();
                        }
                        if(partes[1].equals("enventa")) this.tablero.listarPropiedades();

                    }else
                        System.out.println("Comando incorrecto");
                    break;
                case "chetar":
                    jugadorActual.getAvatar().setCasilla(tablero.getCasillas().get(partes[1]));
                    accion.caer(jugadorActual,1);
                    break;
                case "ver":
                    if(partes.length==2){
                        if(partes[1].equals("tablero")) this.tablero.imprimirTablero();
                    }else
                        System.out.println("Comando incorrecto");
                    break;
                case "cambiar":
                    if(partes.length==2 && partes[1].equals("modo")){
                        this.tablero.cambiarModo();
                    }else{
                        System.out.println("Comando incorrecto");
                    }
                    break;
                default:
                    System.out.println("\nComando incorrecto.");
                    break;
            }
        }
    }

}
