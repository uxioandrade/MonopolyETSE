package monopoly.plataforma;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import monopoly.contenido.*;

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

        //Caso especial en el que el avatar sea un coche
        if(jugador.getAvatar() instanceof Coche){
            if(!((Coche)jugador.getAvatar()).getPoderComprar()){
                System.out.println("Un coche no puede comprar más de una vez cada turno");
                return;
            }
        }

        comprable = (Comprables) jugador.getAvatar().getCasilla();
        //Caso en el que la propiedad ya está adquirida
        if (!comprable.getPropietario().equals(this.tablero.getBanca())) {
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
        jugador.modificarDineroInvertido(comprable.getPrecio());
        //Añade la casilla a sus propiedades
        jugador.anhadirPropiedad(comprable);
        //Añade el propietario a la casilla
        comprable.setPropietario(jugador);

        //Caso en el que el avatar es un coche: no puede volver a comprar en el turno
        if(jugador.getAvatar() instanceof Coche){
            ((Coche)jugador.getAvatar()).setPoderComprar(false);
        }

        System.out.println("Operación realizada con éxito");
        System.out.println("\t" + jugador.getAvatar().getCasilla().getNombre() + " se ha anadido a tu lista de propiedades");
        System.out.println("\tTu saldo actual es de: " + jugador.getDinero() + "€");
        System.out.println("\tEl jugador " + jugador.getNombre() + " compra la casilla " + jugador.getAvatar().getCasilla().getNombre() + " por " + comprable.getPrecio() + "€.");
    }

    public void edificar(Jugador jugador, String tipo){
        Casilla comprableActual = jugador.getAvatar().getCasilla();
        if(!(comprableActual instanceof Solar)){
            System.out.println("Esta casilla no puede contener edificaciones");
            return;
        }
        Solar solarActual = (Solar) comprableActual;
        if(jugador.getPropiedades().contains((Comprables) solarActual)){
            if(solarActual.numVisitasAvatar(jugador.getAvatar()) > 2 || jugador.poseeGrupoCompleto(solarActual.getGrupo())){
                switch (tipo){
                    case "casa":
                    case "Casa":
                        if(solarActual.getConstrucciones("Casa").size() < 4) {
                            if(solarActual.getGrupo().getHotlesGrupo().size()>=solarActual.getGrupo().getCasillas().size() && solarActual.getGrupo().getCasasGrupo().size()>=solarActual.getGrupo().getCasillas().size()){
                                System.out.println("Operación Cancelada: Si construyes esta casa sobrepasarás el límite de edificaciones del grupo");
                                return;
                            }
                            if (jugador.getDinero() >= Valor.MULTIPLICADOR_INICIAL_CASA * solarActual.getPrecio()) {
                                jugador.modificarDinero(-Valor.MULTIPLICADOR_INICIAL_CASA * solarActual.getPrecio());
                                jugador.modificarDineroInvertido(Valor.MULTIPLICADOR_INICIAL_CASA * solarActual.getPrecio());
                                Casa nuevaCasa = new Casa(Valor.MULTIPLICADOR_INICIAL_CASA * solarActual.getPrecio(), solarActual);
                                this.tablero.anhadirEdificio(nuevaCasa);
                                solarActual.anhadirEdificio(nuevaCasa);
                                System.out.println("Se ha edificado una casa en Valencia. La fortuna de Pedro\n" +
                                        "se reduce en 600000€.");
                            } else
                                System.out.println("El jugador " + jugador.getNombre() + " no dispone de dinero suficiente para edificar una casa");
                        }else{
                            System.out.println("No se pueden construir más de 4 casas en cada solar");
                        }
                        break;
                    case "hotel":
                    case "Hotel":
                        if(jugador.getDinero() >= Valor.MULTIPLICADOR_INIIAL_HOTEL*(solarActual.getPrecio())){
                            if(((Solar) solarActual).getConstrucciones("Casa").size() >= 4) {
                                if(solarActual.getGrupo().getHotlesGrupo().size()>=solarActual.getGrupo().getCasillas().size()){
                                    System.out.println("Operación Cancelada: Si construyes este hotel sobrepasarás el límite de edificaciones del grupo");
                                    return;
                                }
                                for(int i = 0;i <4;i++){
                                    Casa casaBorrar = ((Casa) solarActual.getConstrucciones("Casa").get(0));
                                    solarActual.getConstrucciones().remove(casaBorrar);
                                    this.tablero.borrarEdificio(casaBorrar);
                                }
                                jugador.modificarDinero(-Valor.MULTIPLICADOR_INIIAL_HOTEL * solarActual.getPrecio());
                                jugador.modificarDineroInvertido(-Valor.MULTIPLICADOR_INIIAL_HOTEL * solarActual.getPrecio());
                                Hotel nuevoHotel = new Hotel(Valor.MULTIPLICADOR_INIIAL_HOTEL * solarActual.getPrecio(), solarActual);
                                solarActual.anhadirEdificio(nuevoHotel);
                                this.tablero.anhadirEdificio(nuevoHotel);
                                System.out.println("Se ha edificado una casa en Valencia. La fortuna de Pedro\n" +
                                        "se reduce en 600000€.");
                            }else{
                                System.out.println("Tienes que tener un mínimo de 4 casas para construir un hotel");
                            }
                        }else
                            System.out.println("El jugador " + jugador.getNombre() + " no dispone de dinero suficiente para edificar una casa");
                        break;
                    case "piscina":
                    case "Piscina":
                        if(jugador.getDinero() >= Valor.MULTIPLICADOR_INICIAL_PISCINA*solarActual.getPrecio()){
                            if(solarActual.getConstrucciones("Casa").size() >= 2 && solarActual.getConstrucciones("Hotel").size() >= 1) {
                                if(solarActual.getGrupo().getPiscinasGrupo().size()>=solarActual.getGrupo().getCasillas().size()){
                                    System.out.println("Operación Cancelada: Si construyes esta piscina sobrepasarás el límite de edificaciones del grupo");
                                    return;
                                }
                                jugador.modificarDinero(-Valor.MULTIPLICADOR_INICIAL_PISCINA * solarActual.getPrecio());
                                jugador.modificarDineroInvertido(-Valor.MULTIPLICADOR_INICIAL_PISCINA * solarActual.getPrecio());
                                Piscina nuevaPiscina = new Piscina(Valor.MULTIPLICADOR_INICIAL_PISCINA* solarActual.getPrecio(), solarActual);
                                solarActual.anhadirEdificio(nuevaPiscina);
                                this.tablero.anhadirEdificio(nuevaPiscina);
                                System.out.println("Se ha edificado una piscina en Valencia. La fortuna de Pedro\n" +
                                        "se reduce en 600000€.");
                            }else{
                                System.out.println("Tienes que tener un mínimo de 2 casas y un hotel para construir una piscina");
                            }
                        }else
                            System.out.println("El jugador " + jugador.getNombre() + " no dispone de dinero suficiente para edificar una piscina");
                        break;
                    case "pista":
                    case "Pista":
                        if(jugador.getDinero() >= Valor.MULTIPLICADOR_INICIAL_PISTA*solarActual.getPrecio()){
                            if(solarActual.getConstrucciones("Hotel").size() >= 2) {
                                if(solarActual.getGrupo().getPistaDeportesGrupo().size()>=solarActual.getGrupo().getCasillas().size()){
                                    System.out.println("Operación Cancelada: Si construyes esta pista de deporte sobrepasarás el límite de edificaciones del grupo");
                                    return;
                                }
                                jugador.modificarDinero(-Valor.MULTIPLICADOR_INICIAL_PISTA * solarActual.getPrecio());
                                jugador.modificarDineroInvertido(-Valor.MULTIPLICADOR_INICIAL_PISTA * solarActual.getPrecio());
                                PistaDeporte nuevaPista = new PistaDeporte(Valor.MULTIPLICADOR_INICIAL_PISTA*solarActual.getPrecio(), solarActual);
                                solarActual.anhadirEdificio(nuevaPista);
                                this.tablero.anhadirEdificio(nuevaPista);
                                System.out.println("Se ha edificado una pista de deporte en Valencia. La fortuna de Pedro\n" +
                                        "se reduce en 600000€.");
                            }else{
                                System.out.println("Tienes que tener un mínimo de 2 hoteles para construir una pista de deporte");
                            }
                        }else
                            System.out.println("El jugador " + jugador.getNombre() + " no dispone de dinero suficiente para edificar una pista de deporte");
                        break;
                    default:
                        System.out.println("El tipo de edificación indicado no existe.");
                }
            }else{
                System.out.println(jugador.getNombre() + " no cumple los requisitos necesarios para edificar en la casilla " + solarActual.getNombre());
            }
        }else{
            System.out.println(jugador.getNombre() + " no posee la casilla " + solarActual.getNombre());
        }
    }

    public void venderConstrucciones(Jugador vendedor, Casilla propiedad, String tipo, int num) {
        int vendidas = 0;
        double total = 0;
        int i = 0;
        if(!(propiedad instanceof Solar)){
            System.out.println("Esta casilla no puede contener edificaciones");
            return;
        }
        if((vendedor.getPropiedades().contains((Comprables)propiedad))){
            if(((Solar) propiedad).getConstrucciones(tipo)==null){
                System.out.println("Ese tipo de construcciones no existe");
                return;
            }
            int tamanho = ((Solar) propiedad).getConstrucciones(tipo).size();
            for(i = 0; i<tamanho && i<num;i++){
                total += ((Solar) propiedad).getConstrucciones(tipo).get(0).getPrecio()*0.5;
                vendedor.modificarDinero(((Solar) propiedad).getConstrucciones(tipo).get(0).getPrecio()*0.5);
                this.tablero.borrarEdificio(((Solar) propiedad).getConstrucciones(tipo).remove(0));
                ((Solar) propiedad).getConstrucciones().remove(((Solar) propiedad).getConstrucciones(tipo).remove(0));
            }
            System.out.println(i);
            if(i == 0)
                System.out.println(vendedor.getNombre() + " no ha vendido " + tipo + " en " + propiedad.getNombre() + " porque no ha construido");
            else if(i==num)
                System.out.println(vendedor.getNombre() + " ha vendido " + num + " " + tipo + " en " + propiedad.getNombre() +
                        ", recibiendo " + total + "€. En la propiedad queda" + ((Solar) propiedad).getConstrucciones(tipo).size() + " " + tipo + ".");
            else
                System.out.println(vendedor.getNombre() + " solamente ha podido vender " + i + " " + tipo + " en " + propiedad.getNombre() +
                    ", recibiendo " + total + "€. ");

        }else{
            System.out.println("EL jugador "+ vendedor.getNombre() + " no posee la casilla "+propiedad.getNombre());
        }
    }

    public void hipotecar(Casilla propiedad, Jugador jugActual) {
        Comprables comprable;
        if(!(propiedad instanceof Comprables)){
            System.out.println("Esta casilla no se puede hipotecar");
            return;
        }

        comprable = (Comprables) propiedad;
        if(!jugActual.getPropiedades().contains(comprable)){
            System.out.println("El jugador " + jugActual.getNombre() + " no puede hipotecar la propiedad " + comprable.getNombre()+" porque no le pertenece");
            return;
        }
        if(comprable.getHipotecado()){
            System.out.println("El jugador " + jugActual.getNombre() + " no puede hipotecar la propiedad " + comprable.getNombre()+" porque ya está hipotecada");
            return;
        }
        if(propiedad instanceof Solar){
            if(((Solar)propiedad).getConstrucciones().size()>0){
                System.out.println("El jugador " + jugActual.getNombre() + " no puede hipotecar la propiedad " + comprable.getNombre()+", antes debe vender los edificios de la misma");
                return;
            }
        }
        jugActual.modificarDinero(comprable.getHipoteca());
        comprable.setHipotecado(true);
        System.out.println("El jugador " + jugActual.getNombre() + " ha hipotecado la propiedad " + comprable.getNombre()+ "recibiendo así "+ comprable.getHipoteca()+"$");

    }

    public void desHipotecar(Casilla propiedad, Jugador jugActual) {
        Comprables comprable;
        if(!(propiedad instanceof Comprables)){
            System.out.println("Esta casilla no se puede deshipotecar");
            return;
        }
        comprable = (Comprables) propiedad;
        //Comprueba que el jugador tenga la propiedad actual
        if (jugActual.getPropiedades().contains(comprable) && comprable.getHipotecado()) {
            jugActual.modificarDinero(-1*comprable.getHipoteca());
            comprable.setHipotecado(false);
            System.out.println("El jugador " + jugActual.getNombre() + " ha deshipotecado la propiedad " + comprable.getNombre());
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
                case "vender":
                    if(partes.length >= 4){
                        auxCasilla = "";
                        for(int i = 2; i < partes.length - 2;i++) {
                            auxCasilla += partes[i] + " ";
                        }
                        accion.venderConstrucciones(jugador,this.tablero.getCasillas().get(auxCasilla + partes[partes.length-2]),partes[1],partes[partes.length-1].toCharArray()[0] - '0');
                    }else{
                        System.out.println("Comando incorrecto");
                    }
                    break;
                case "bancarrota":
                    Comprables comprable;
                    for(Casilla cas : jugador.getPropiedades()){
                        comprable = (Comprables) cas;
                        if(cas instanceof Solar){
                            for(Edificios ed: ((Solar) comprable).getConstrucciones()){
                                this.tablero.borrarEdificio(ed);
                                ((Solar) comprable).getConstrucciones().remove(ed);
                            }
                        }
                        comprable.setPropietario(this.tablero.getBanca());
                        comprable.setHipotecado(false);
                        jugador.getPropiedades().remove(cas);
                    }
                    Valor.casillas.get(jugador.getAvatar().getCasilla().getPosicion()).quitarAvatar(jugador.getAvatar());
                    this.tablero.getAvatares().remove(jugador.getAvatar().getId());
                    this.tablero.getJugadores().remove(jugador.getNombre());
                    Menu.turnosJugadores.remove(jugador.getNombre());
                    if(Menu.turnosJugadores.size() == 1){
                        System.out.println("Partida acabada!\n Enhorabuena " + Menu.turnosJugadores.get(0) + ", eres el ganador!!!!");
                        System.exit(0);
                    }
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
        jugador.anhadirVecesCarcel();
        System.out.println(jugador.getNombre() + " va a la cárcel.");
    }

}
