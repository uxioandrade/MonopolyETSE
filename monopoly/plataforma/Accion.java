package monopoly.plataforma;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections.*;

import monopoly.contenido.Carta;
import monopoly.contenido.Casilla;
import monopoly.contenido.Jugador;

public class Accion {
    private Tablero tablero;

    public Accion(Tablero tablero) {
        if (tablero != null) {
            this.tablero = tablero;
        }
    }

    private Tablero getTablero() {
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
        //Caso en el que la propiedad ya está adquirida
        if (!jugador.getAvatar().getCasilla().getPropietario().equals(this.tablero.getBanca())) {
            System.out.println("Error. Propiedad ya adquirida, para comprarla debes negociar con " + jugador.getAvatar().getCasilla().getPropietario().getNombre());
            return;
        }
        //Caso en el que la propiedad no es un solar
        if (jugador.getAvatar().getCasilla().getPrecio() <= 0) {
            System.out.println("No se puede comprar");
            return;
        }
        //Caso en el que el jugador tiene menos dinero que el precio del solar
        if (jugador.getAvatar().getCasilla().getPrecio() > jugador.getDinero()) {
            System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades o a declararte en bancarrota");
            return;
        }
        //Caso en el que se compra la propiedad
        //Se disminuye el dinero del jugador
        jugador.modificarDinero(-jugador.getAvatar().getCasilla().getPrecio());
        //Añade la casilla a sus propiedades
        jugador.anhadirPropiedad(jugador.getAvatar().getCasilla());
        //Añade el propietario a la casilla
        jugador.getAvatar().getCasilla().setPropietario(jugador);
        System.out.println("Operación realizada con éxito");
        System.out.println("\t" + jugador.getAvatar().getCasilla().getNombre() + " se ha anadido a tu lista de propiedades");
        System.out.println("\tTu saldo actual es de: " + jugador.getDinero() + "€");
        System.out.println("\tEl jugador " + jugador.getNombre() + " compra la casilla " + jugador.getAvatar().getCasilla().getNombre() + " por " + jugador.getAvatar().getCasilla().getPrecio() + "€.");
    }

    public void vender(Jugador vendedor, Jugador comprador, Casilla propiedad) {

    }

    public void hipotecar(Casilla propiedad, Jugador jugActual) {
        //Comprueba que el jugador tenga la propiedad actual
        if (jugActual.getPropiedades().contains(propiedad) && !propiedad.getHipotecado()) {
            jugActual.modificarDinero(propiedad.getHipoteca());
            propiedad.setHipotecado(true);
        } else {
            System.out.println("El jugador " + jugActual.getNombre() + " no puede hipotecar la propiedad " + propiedad.getNombre());
        }
    }

    public void desHipotecar(Casilla propiedad, Jugador jugActual) {
        //Comprueba que el jugador tenga la propiedad actual
        if (jugActual.getPropiedades().contains(propiedad) && propiedad.getHipotecado()) {
            jugActual.modificarDinero(-1*propiedad.getHipoteca());
            propiedad.setHipotecado(false);
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
                    for(Casilla cas : jugador.getPropiedades()){
                        cas.setPropietario(this.tablero.getBanca());
                        cas.setHipotecado(false);
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

    public void pagarAlquiler(Jugador jugador,int tirada) {
        //Comprueba que la casilla tenga un propietario
        if (!jugador.getAvatar().getCasilla().getPropietario().equals(this.tablero.getBanca()) && !jugador.getAvatar().getCasilla().getHipotecado()) {
            //Caso en el que la casilla es un servicio
            if(jugador.getAvatar().getCasilla().getNombre().contains("Servicio")){
                //El alquiler depende de la tirada
                if (jugador.getDinero() >= jugador.getAvatar().getCasilla().getAlquiler(tirada)){
                    //Se resta el alquiler del jugador que ha caído en el servicio
                    jugador.modificarDinero(-jugador.getAvatar().getCasilla().getAlquiler(tirada));
                    System.out.println("Se han pagado " + jugador.getAvatar().getCasilla().getAlquiler(tirada) + "€ de servicio.");
                    //Se aumenta el dinero del propietario
                    jugador.getAvatar().getCasilla().getPropietario().modificarDinero(jugador.getAvatar().getCasilla().getAlquiler(tirada));
                    return;
                } else {
                    System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
                    if(this.menuHipotecar(jugador,tablero,jugador.getAvatar().getCasilla().getPrecio()))
                        pagarAlquiler(jugador,tirada);
                    return;
                }
            }
            //Caso en el que la casilla es un solar
            if (jugador.getDinero() >= jugador.getAvatar().getCasilla().getAlquiler()){
                //Se resta el alquiler del jugador que ha caído en el servicio
                jugador.modificarDinero(-jugador.getAvatar().getCasilla().getAlquiler());
                System.out.println("Se han pagado " + jugador.getAvatar().getCasilla().getAlquiler() + "€ de alquiler.");
                //Se aumenta el dinero del propietario
                jugador.getAvatar().getCasilla().getPropietario().modificarDinero(jugador.getAvatar().getCasilla().getAlquiler());
            } else {
                System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
                if(this.menuHipotecar(jugador,tablero,jugador.getAvatar().getCasilla().getPrecio())){
                    pagarAlquiler(jugador,tirada);
                }
            }
        }else{
            System.out.println("La casilla actual no tiene un alquiler a pagar");
        }
    }

    public void irCarcel(Jugador jugador){
        //Modifica la posicion del jugador
        jugador.getAvatar().setCasilla(Valor.casillas.get(10));
        //Modifica el estado del jugador
        jugador.getAvatar().setEncarcelado(1);
        System.out.println(jugador.getNombre() + " va a la cárcel.");
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

    public void pagarImpuesto(Jugador jugador){
        //Comprueba que se trate de un impuesto
        if (jugador.getAvatar().getCasilla().getPrecio() < 0) {
            System.out.println(jugador.getNombre() + ",debes pagar un impuesto de " + -1*jugador.getAvatar().getCasilla().getPrecio() + " debido a " + jugador.getAvatar().getCasilla().getNombre());
            //Comprueba que el jugador tenga dinero suficiente para pagar
            if (-1 * jugador.getAvatar().getCasilla().getPrecio() <= jugador.getDinero()) {
                jugador.modificarDinero(jugador.getAvatar().getCasilla().getPrecio());
                System.out.println("Se han pagado " + -1.0*jugador.getAvatar().getCasilla().getPrecio() + "€ de impuesto");
                Valor.dineroAcumulado += jugador.getAvatar().getCasilla().getPrecio()*-1;
                return;
            } else {
                System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
                if(this.menuHipotecar(jugador,tablero,jugador.getAvatar().getCasilla().getPrecio()*(-1.0)))
                    pagarImpuesto(jugador);
                return;
            }
        }else
            return;
    }

    private ArrayList<Carta> barajarCartas(ArrayList<Carta> cartas){
        ArrayList<Carta> aux = new ArrayList<>(cartas);
        java.util.Collections.shuffle(aux);
        return aux;
    }

    private int elegirCarta(){
        while(true) {
            System.out.println("Indique un número del 1 al 10 para escoger carta");
            Scanner scanner= new Scanner(System.in);
            int num = scanner.nextInt();
            if(num >= 1 && num <= 10)
                return num;
            else
                System.out.println("El número indicado no está dentro de los límites exigidos");
        }
    }

    public void caer(Jugador jugador, int tirada){
        if(jugador.getAvatar().getCasilla().getNombre().contains("Suerte")){
            ArrayList<Carta> casSuerte = barajarCartas(Valor.cartasSuerte);
            Carta cartaEscogida = casSuerte.get(elegirCarta());
            cartaEscogida.accionCarta(jugador,tablero);
        }
        if(jugador.getAvatar().getCasilla().getNombre().contains("Caja")){
            ArrayList<Carta> casCaja = barajarCartas(Valor.cartaCajaKomuna);
            Carta cartaEscogida = casCaja.get(elegirCarta());
            cartaEscogida.accionCarta(jugador,tablero);
        }
        //Cobrar impuesto casilla especial
        if (jugador.getAvatar().getCasilla().getPrecio() < 0) {
            this.pagarImpuesto(jugador);
            return;
        }
        //Cobrar alquileres
        if(!jugador.getAvatar().getCasilla().getPropietario().equals(this.tablero.getBanca()) && !jugador.getAvatar().getCasilla().getPropietario().equals(jugador)) {
            this.pagarAlquiler(jugador,tirada);
            return;
        }else
            if(jugador.getAvatar().getCasilla().getPosicion() == 30) {
                this.irCarcel(jugador);
            }
            if(jugador.getAvatar().getCasilla().getPosicion() == 20) {
                jugador.modificarDinero(Valor.getDineroAcumulado());
                System.out.println("El jugador " + jugador.getNombre() + "recibe " + Valor.getDineroAcumulado() + "€, el bote de la banca");
                Valor.setDineroAcumulado(0);
            }
    }
}
