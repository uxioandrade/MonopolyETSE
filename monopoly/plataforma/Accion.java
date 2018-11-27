package monopoly.plataforma;

import java.util.ArrayList;
import java.util.Scanner;

import monopoly.contenido.Casilla;
import monopoly.contenido.Comprables;
import monopoly.contenido.Jugador;

public class Accion {
    private Tablero tablero;

    public Accion(Tablero tablero) {
        if (tablero != null) {
            this.tablero = tablero;
        }
    }

    public Tablero getTablero() {
        return this.tablero;
    }

    //El setter para tablero no es necesario, pues el tablero solo se pasa a la instancia de acción al principio. Si se crease un setter y se cambiase el tablero
    //esto implicaría que la partida es una partida nueva

    public void crearJugadores() {
        int empezar = 1; //Almacena el número de jugadores
        Menu.turnosJugadores = new ArrayList<>();
        while (empezar <= 6) {
            System.out.print("$> ");
            Scanner scanner = new Scanner(System.in);
            String orden = scanner.nextLine();
            String[] partes = orden.split(" ");
            String comando = partes[0];
            switch (comando) {
                case "introducir":
                    if (partes.length == 4 && partes[1].equals("jugador")) {
                        if (this.tablero.getJugadores().containsKey(partes[2])) {
                            System.out.println("Error: No puede haber dos jugadores con el mismo nombre");
                        } else {
                            Jugador jugadorIntroducir = new Jugador(partes[2], partes[3], this.tablero);
                            //Se añade los jugadores al HashMap de tableros
                            this.tablero.addJugadores(jugadorIntroducir);
                            //Se añade el jugador al ArrayList que almacena el orden de los jugadores
                            Menu.turnosJugadores.add(jugadorIntroducir.getNombre());
                            //Se aumenta el número de jugadores
                            empezar++;
                            System.out.println(partes[2] + " se ha unido a la partida");
                            System.out.println("Informacion sobre el Jugador");
                            System.out.println(jugadorIntroducir.getDescripcionInicial());
                            this.tablero.imprimirTablero();
                        }
                        break;
                    }
                case "empezar":
                    if (partes.length == 2 && partes[1].equals("partida")) {
                        if (empezar <= 2)
                            System.out.println("El número mínimo de jugadores es 2");
                        else
                            empezar = 7; //Modifica el valor de empezar para romper el bucle
                        break;
                    }
                default:
                    System.out.println("Comando incorrecto, prueba con:");
                    System.out.println(">introducir jugador <nombre> <tipo> para crear un jugador");
                    System.out.println(">empezar partida    para comenzar la partida");
            }
        }

    }

    public void comprar(Jugador jugador) {
        Comprables comprable;
        if(!(jugador.getAvatar().getCasilla() instanceof Comprables)){
            System.out.println("Esta casilla no se puede comprar");
            return;
        }

        comprable = (Comprables) jugador.getAvatar().getCasilla();
        //Caso en el que la propiedad ya está adquirida
        if (comprable.equals(this.tablero.getBanca())) {
            System.out.println("Error. Propiedad ya adquirida, para comprarla debes negociar con " + comprable.getPropietario().getNombre());
            return;
        }
        //Caso en el que el jugador tiene menos dinero que el precio del solar
        if (comprable.getPrecio() > jugador.getDinero()) {
            System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades o a declararte en bancarrota");
            return;
        }
        //Caso en el que se compra la propiedad
        //Se disminuye el dinero del jugador
        jugador.modificarDinero(-comprable.getPrecio());
        //Añade la casilla a sus propiedades
        jugador.anhadirPropiedad(comprable);
        //Añade el propietario a la casilla
        comprable.setPropietario(jugador);
        System.out.println("Operación realizada con éxito");
        System.out.println("\t" + jugador.getAvatar().getCasilla().getNombre() + " se ha anadido a tu lista de propiedades");
        System.out.println("\tTu saldo actual es de: " + jugador.getDinero() + "€");
        System.out.println("\tEl jugador " + jugador.getNombre() + " compra la casilla " + jugador.getAvatar().getCasilla().getNombre() + " por " + comprable.getPrecio() + "€.");
    }

    public void vender(Jugador vendedor, Jugador comprador, Casilla propiedad) {

    }

    public void hipotecar(Casilla propiedad, Jugador jugActual) {
        Comprables comprable;
        if(!(propiedad instanceof Comprables)){
            System.out.println("Esta casilla no se puede comprar");
            return;
        }

        comprable = (Comprables) propiedad;
        //Comprueba que el jugador tenga la propiedad actual
        if (jugActual.getPropiedades().contains(comprable) && !comprable.getHipotecado()) {
            jugActual.modificarDinero(comprable.getHipoteca());
            comprable.setHipotecado(true);
        } else {
            System.out.println("El jugador " + jugActual.getNombre() + " no puede hipotecar la propiedad " + comprable.getNombre());
        }
    }

    public void desHipotecar(Casilla propiedad, Jugador jugActual) {
        Comprables comprable;
        if(!(propiedad instanceof Comprables)){
            System.out.println("Esta casilla no se puede comprar");
            return;
        }
        comprable = (Comprables) propiedad;
        //Comprueba que el jugador tenga la propiedad actual
        if (jugActual.getPropiedades().contains(comprable) && comprable.getHipotecado()) {
            jugActual.modificarDinero(-1*comprable.getHipoteca());
            comprable.setHipotecado(false);
        } else {
            System.out.println("El jugador " + jugActual.getNombre() + " no puede deshipotecar la propiedad " + propiedad.getNombre());
        }
    }

    public boolean menuHipotecar(Jugador jugador,Tablero tablero, double deuda){ //Devuelve true si el jugador ya ha afrontado su deuda
        System.out.println(jugador.getNombre() + " entró en el menú hipotecar");
        Accion accion = new Accion(tablero);
        while(true) {
            System.out.print("$> ");
            Scanner scanner = new Scanner(System.in);
            String orden = scanner.nextLine();
            String[] partes = orden.split(" ");
            String comando = partes[0];

            switch(comando){
                case "hipotecar":
                    String auxCasilla = "";
                    for(int i = 1; i < partes.length - 1;i++) {
                        auxCasilla += partes[i] + " ";
                    }
                    if(partes.length<2 || partes.length >4) System.out.println("\n Comando incorrecto");
                    else if(this.tablero.getCasillas().get(auxCasilla + partes[partes.length-1])!=null) {//si existe la casilla
                        accion.hipotecar(this.tablero.getCasillas().get(partes[1]), jugador);
                        if(jugador.getDinero() >= deuda){
                            System.out.println("El jugador " + jugador.getNombre() + " ya tiene dinero suficiente para afrontar su deuda");
                            return true;
                        }else{
                            System.out.println("El jugador " + jugador.getNombre() + " aún no tiene dinero suficiente");
                        }
                    }else System.out.println("La casilla que quieres hipotecar no existe");
                    break;
                case "bancarrota":
                    Comprables comprable;
                    for(Casilla cas : jugador.getPropiedades()){
                        comprable = (Comprables) cas;
                        comprable.setPropietario(this.tablero.getBanca());
                        comprable.setHipotecado(false);
                        //Borrar todas las edificaciones del hashmap
                        jugador.getPropiedades().remove(cas);
                    }
                    Valor.casillas.get(jugador.getAvatar().getCasilla().getPosicion()).quitarAvatar(jugador.getAvatar());
                    this.tablero.getAvatares().remove(jugador.getAvatar().getId());
                    this.tablero.getJugadores().remove(jugador.getNombre());
                    Menu.turnosJugadores.remove(jugador.getNombre());
                    return false;
            }
        }
    }

    public void salirCarcel(Jugador jugador){
        //Comprueba que el jugador esté en la cárcel
        if(jugador.getAvatar().getEncarcelado()>0 && jugador.getAvatar().getEncarcelado()<3){
            //Comprueba que el jugador tenga dinero para pagar la fianza
            if(jugador.getDinero()>Valor.getDineroSalirCarcel()){
                jugador.getAvatar().setEncarcelado(0);
                jugador.modificarDinero(-Valor.getDineroSalirCarcel());
                System.out.println(jugador.getNombre() + " paga " + Valor.getDineroSalirCarcel() +"€ y sale de la cárcel. Puede lanzar los dados");
            }else {
                System.out.println(jugador.getNombre() + " no tiene dinero suficiente para salir de la cárcel");
                return;
            }
        //Caso en el que el jugador ya superó los 3 turnos en la cárcel
        }else if(jugador.getAvatar().getEncarcelado() >= 4){
            if(jugador.getDinero() >= Valor.getDineroSalirCarcel()) {
                jugador.getAvatar().setEncarcelado(0);
                jugador.modificarDinero(-Valor.getDineroSalirCarcel());
                System.out.println("El jugador " + jugador.getNombre() + " paga " + Valor.getDineroSalirCarcel() + "€ y sale de la cárcel.");
            }else{
                if(this.menuHipotecar(jugador,this.tablero,Valor.getDineroSalirCarcel()))
                    salirCarcel(jugador);
            }
        }else{
            System.out.println("El jugador " + jugador.getNombre() + " no está en la cárcel");
        }
    }

    public void irCarcel(Jugador jugador) {
        //Modifica la posicion del jugador
        jugador.getAvatar().setCasilla(Valor.casillas.get(10));
        //Modifica el estado del jugador
        jugador.getAvatar().setEncarcelado(1);
        System.out.println(jugador.getNombre() + " va a la cárcel.");
    }
}
