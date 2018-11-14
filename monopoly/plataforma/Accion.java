package monopoly.plataforma;

import java.util.ArrayList;
import java.util.Scanner;

import monopoly.contenido.Casilla;
import monopoly.contenido.Jugador;

public class Accion {
    private Tablero tablero;

    public Accion(Tablero tablero){
        if(tablero!=null){
            this.tablero=tablero;
        }
    }

    private Tablero getTablero(){
        return this.tablero;
    }

    //El setter para tablero no es necesario, pues el tablero solo se pasa a la instancia de acción al principio. Si se crease un setter y se cambiase el tablero
    //esto implicaría que la partida es una partida nueva

    public void crearJugadores(ArrayList<String> turnosJugadores){
        int empezar = 1; //Almacena el número de jugadores
        while(empezar <= 6){
            System.out.print("$> ");
            Scanner scanner= new Scanner(System.in);
            String orden= scanner.nextLine();
            String[] partes=orden.split(" ");
            String comando= partes[0];
            switch (comando){
                case "introducir":
                    if (partes.length == 4 && partes[1].equals("jugador")) {
                        if (this.tablero.getJugadores().containsKey(partes[2])) {
                            System.out.println("Error: No puede haber dos jugadores con el mismo nombre");
                        } else {
                            Jugador jugadorIntroducir = new Jugador(partes[2], partes[3], this.tablero);
                            //Se añade los jugadores al HashMap de tableros
                            this.tablero.addJugadores(jugadorIntroducir);
                            //Se añade el jugador al ArrayList que almacena el orden de los jugadores
                            turnosJugadores.add(jugadorIntroducir.getNombre());
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
                    if(partes.length ==2 && partes[1].equals("partida") ) {
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

    public void comprar(Jugador jugador){
        //Caso en el que la propiedad ya está adquirida
        if(!jugador.getAvatar().getCasilla().getPropietario().equals(this.tablero.getBanca())) {
            System.out.println("Error. Propiedad ya adquirida, para comprarla debes negociar con " + jugador.getAvatar().getCasilla().getPropietario().getNombre());
            return;
        }
        //Caso en el que la propiedad no es un solar
        if(jugador.getAvatar().getCasilla().getPrecio() <= 0){
            System.out.println("No se puede comprar");
            return;
        }
        //Caso en el que el jugador tiene menos dinero que el precio del solar
        if(jugador.getAvatar().getCasilla().getPrecio() > jugador.getDinero()){
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
        System.out.println("\t"+jugador.getAvatar().getCasilla().getNombre()+" se ha anadido a tu lista de propiedades");
        System.out.println("\tTu saldo actual es de: "+jugador.getDinero()+"€");
        System.out.println("\tEl jugador " + jugador.getNombre() + " compra la casilla " + jugador.getAvatar().getCasilla().getNombre() + " por " + jugador.getAvatar().getCasilla().getPrecio() + "€.");
    }
    public void vender(Jugador vendedor,Jugador comprador, Casilla propiedad){

    }

    public void hipotecar(Casilla propiedad, Jugador jugActual){
        //Comprueba que el jugador tenga la propiedad actual
        if(jugActual.getPropiedades().contains(propiedad)){
            jugActual.modificarDinero(propiedad.getHipoteca());
            propiedad.setHipotecado(true);
        }else{
            System.out.println("El jugador " + jugActual.getNombre() + " no puede hipotecar la propiedad " + propiedad.getNombre());
        }
    }

    public boolean pagarAlquiler(Jugador jugador,int tirada) {
        //Comprueba que la casilla tenga un propietario
        if (!jugador.getAvatar().getCasilla().getPropietario().equals(this.tablero.getBanca())) {
            //Caso en el que la casilla es un servicio
            if(jugador.getAvatar().getCasilla().getNombre().contains("Servicio")){
                //El alquiler depende de la tirada
                if (jugador.getDinero() >= jugador.getAvatar().getCasilla().getAlquiler(tirada)){
                    //Se resta el alquiler del jugador que ha caído en el servicio
                    jugador.modificarDinero(-jugador.getAvatar().getCasilla().getAlquiler(tirada));
                    System.out.println("Se han pagado " + jugador.getAvatar().getCasilla().getAlquiler(tirada) + "€ de servicio.");
                    //Se aumenta el dinero del propietario
                    jugador.getAvatar().getCasilla().getPropietario().modificarDinero(jugador.getAvatar().getCasilla().getAlquiler(tirada));
                    return true;
                } else {
                    System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
                    return false;
                }
            }
            //Caso en el que la casilla es un solar
            if (jugador.getDinero() >= jugador.getAvatar().getCasilla().getAlquiler()){
                //Se resta el alquiler del jugador que ha caído en el servicio
                jugador.modificarDinero(-jugador.getAvatar().getCasilla().getAlquiler());
                System.out.println("Se han pagado " + jugador.getAvatar().getCasilla().getAlquiler() + "€ de alquiler.");
                //Se aumenta el dinero del propietario
                jugador.getAvatar().getCasilla().getPropietario().modificarDinero(jugador.getAvatar().getCasilla().getAlquiler());
                return true;
            } else {
                System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
                return false;
            }
        }else{
            System.out.println("La casilla actual no tiene un alquiler a pagar");
            return false;
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
            }
        //Caso en el que el jugador ya superó los 3 turnos en la cárcel
        }else if(jugador.getAvatar().getEncarcelado() >= 4 && jugador.getDinero() >= Valor.getDineroSalirCarcel()){
            jugador.getAvatar().setEncarcelado(0);
            jugador.modificarDinero(-Valor.getDineroSalirCarcel());
            System.out.println("El jugador " + jugador.getNombre() + " paga " + Valor.getDineroSalirCarcel() +"€ y sale de la cárcel.");
        }else{
            System.out.println("El jugador " + jugador.getNombre() + " no está en la cárcel");
        }
    }

    public boolean pagarImpuesto(Jugador jugador){
        //Comprueba que se trate de un impuesto
        if (jugador.getAvatar().getCasilla().getPrecio() < 0) {
            System.out.println(jugador.getNombre() + ",debes pagar un impuesto de " + -1*jugador.getAvatar().getCasilla().getPrecio() + " debido a " + jugador.getAvatar().getCasilla().getNombre());
            //Comprueba que el jugador tenga dinero suficiente para pagar
            if (-1 * jugador.getAvatar().getCasilla().getPrecio() <= jugador.getDinero()) {
                jugador.modificarDinero(jugador.getAvatar().getCasilla().getPrecio());
                System.out.println("Se han pagado " + -1.0*jugador.getAvatar().getCasilla().getPrecio() + "€ de impuesto");
                Valor.dineroAcumulado += jugador.getAvatar().getCasilla().getPrecio()*-1;
                return true;
            } else {
                System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
                return false;
            }
        }else
            return false;
    }

    public boolean caer(Jugador jugador, int tirada){
        if(jugador.getAvatar().getCasilla().getNombre().contains("Suerte")){
            
        }
        if(jugador.getAvatar().getCasilla().getNombre().contains("Caja")){

        }
        //Cobrar impuesto casilla especial
        if (jugador.getAvatar().getCasilla().getPrecio() < 0)
            return this.pagarImpuesto(jugador);
        //Cobrar alquileres
        if(!jugador.getAvatar().getCasilla().getPropietario().equals(this.tablero.getBanca()) && !jugador.getAvatar().getCasilla().getPropietario().equals(jugador)) {
            return this.pagarAlquiler(jugador,tirada);
        }else
            if(jugador.getAvatar().getCasilla().getPosicion() == 30) {
                this.irCarcel(jugador);
            }
            if(jugador.getAvatar().getCasilla().getPosicion() == 20) {
                jugador.modificarDinero(Valor.getDineroAcumulado());
                System.out.println("El jugador " + jugador.getNombre() + "recibe " + Valor.getDineroAcumulado() + "€, el bote de la banca");
                Valor.setDineroAcumulado(0);
            }
            return true;
    }
}
