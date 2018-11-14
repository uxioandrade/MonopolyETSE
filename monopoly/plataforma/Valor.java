package monopoly.plataforma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import monopoly.contenido.*;

public class Valor {

    private double valor;
    private static HashMap<String,Grupo> grupos;
    public static double dineroAcumulado;

    //Colores
    public static final String ANSI_NEGRO = "\u001b[30m";
    public static final String ANSI_DARK_GREY = "\u001b[37m";
    public static final String ANSI_ROJO = "\u001b[31m";
    public static final String ANSI_VERDE = "\u001b[32m";
    public static final String ANSI_NARANJA = "\u001b[33m";
    public static final String ANSI_AMARILLO = "\033[0;33m";
    public static final String ANSI_AZUL = "\u001b[34m";
    public static final String ANSI_ROSA = "\u001b[35m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_GRIS = "\u001b[37m";

    public static void crearGrupos() {
        grupos = new HashMap<>();
        //Inicializar hashmap de grupos
        grupos.put("Marron", new Grupo("Marron", ANSI_DARK_GREY, new ArrayList<Casilla>(Arrays.asList(casillas.get(1), casillas.get(3))), PRECIO_G1));
        grupos.put("Cyan", new Grupo("Cyan", ANSI_CYAN, new ArrayList<Casilla>(Arrays.asList(casillas.get(6), casillas.get(8), casillas.get(9))), PRECIO_G1 * 1.3));
        grupos.put("Rosa", new Grupo("Rosa", ANSI_ROSA, new ArrayList<Casilla>(Arrays.asList(casillas.get(11), casillas.get(13), casillas.get(14))), grupos.get("Cyan").getPrecio() * 1.3));
        grupos.put("Amarillo", new Grupo("Amarillo", ANSI_NARANJA, new ArrayList<Casilla>(Arrays.asList(casillas.get(16), casillas.get(18), casillas.get(19))), grupos.get("Rosa").getPrecio() * 1.3));
        grupos.put("Rojo", new Grupo("Rojo", ANSI_ROJO, new ArrayList<Casilla>(Arrays.asList(casillas.get(21), casillas.get(23), casillas.get(24))), grupos.get("Amarillo").getPrecio() * 1.3));
        grupos.put("Naranja", new Grupo("Naranja", ANSI_AMARILLO, new ArrayList<Casilla>(Arrays.asList(casillas.get(26), casillas.get(27), casillas.get(29))), grupos.get("Rojo").getPrecio() * 1.3));
        grupos.put("Verde", new Grupo("Verde", ANSI_VERDE, new ArrayList<Casilla>(Arrays.asList(casillas.get(31), casillas.get(32), casillas.get(34))), grupos.get("Naranja").getPrecio() * 1.3));
        grupos.put("Azul", new Grupo("Azul", ANSI_AZUL, new ArrayList<Casilla>(Arrays.asList(casillas.get(37), casillas.get(39))), grupos.get("Verde").getPrecio() * 1.3));
        /*grupos.put("Estaciones", new Grupo("Estaciones",Negro,new ArrayList<Casilla>(Arrays.asList(casillas.get(5),casillas.get(15),casillas.get(25),casillas.get(35))), this.grupos.get("Cyan").getPrecio()*2.0 ));
        grupos.put("Impuestos", new Grupo("Impuestos",Negro,new ArrayList<Casilla>(Arrays.asList(casillas.get(4),casillas.get(38))), -this.grupos.get("Cyan").getPrecio()*0.66));
        grupos.put("Suerte", new Grupo("Suerte",Negro,new ArrayList<Casilla>(Arrays.asList(casillas.get(7),casillas.get(22),casillas.get(36))),0.0));
        grupos.put("Caja Comunidad",new Grupo("Caja Comunidad",Negro,new ArrayList<Casilla>(Arrays.asList(casillas.get(2),casillas.get(17),casillas.get(33))),0.0));
        grupos.put("Servicios",new Grupo("Servicios",Negro,new ArrayList<Casilla>(Arrays.asList(casillas.get(12),casillas.get(28))),100.0));
        grupos.put("Carcel",new Grupo("Carcel", Negro, new ArrayList<Casilla>(Arrays.asList(casillas.get(10))),0.0));*/
        grupos.put("Especiales", new Grupo("Especiales", ANSI_NEGRO, new ArrayList<Casilla>(Arrays.asList(casillas.get(0), casillas.get(20), casillas.get(30), casillas.get(5), casillas.get(15), casillas.get(25), casillas.get(35), casillas.get(4), casillas.get(38), casillas.get(7), casillas.get(22), casillas.get(36), casillas.get(2), casillas.get(17), casillas.get(33), casillas.get(12), casillas.get(28), casillas.get(10))), 0.0));
        setValoresEspeciales();
    }

    public final static ArrayList<Casilla> casillas = new ArrayList<>(Arrays.asList(
            new Casilla("Salida",0),
            new Casilla("Bag End",1),
            new Casilla("Caja1",2),
            new Casilla("Farmer Maggot's",3),
            new Casilla("Impuesto1",4),
            new Casilla("Estacion1",5),
            new Casilla("Chetwood Forest",6),
            new Casilla("Suerte1",7),
            new Casilla("Weathertop",8),
            new Casilla("Ford of Bruinen",9),
            new Casilla("Carcel",10),
            new Casilla("Rivendell",11),
            new Casilla("Servicio1",12),
            new Casilla("Council of Elrond",13),
            new Casilla("Caras Galadon",14),
            new Casilla("Estacion2",15),
            new Casilla("Fall of Rauros",16),
            new Casilla("Caja2",17),
            new Casilla("Nen Hithoel",18),
            new Casilla("Argonath",19),
            new Casilla("Parking",20),
            new Casilla("Golden Hall",21),
            new Casilla("Suerte2",22),
            new Casilla("Edoras",23),
            new Casilla("Helm's Deep",24),
            new Casilla("Estacion3",25),
            new Casilla("Isengard Caverns",26),
            new Casilla("Fords of Isen",27),
            new Casilla("Servicio2",28),
            new Casilla("Tower of Orthanc",29),
            new Casilla("VeCarcel",30),
            new Casilla("Pelennor Fields",31),
            new Casilla("Osgiliath",32),
            new Casilla("Caja3",33),
            new Casilla("Minas Tirith",34),
            new Casilla("Estacion4",35),
            new Casilla("Suerte3",36),
            new Casilla("Barad-dûr",37),
            new Casilla("Impuesto2",38),
            new Casilla("Mt. Doom",39)
    ));

    public final static ArrayList<Carta> cartasSuerte = new ArrayList<>(Arrays.asList(
            new CartaMovimiento(28,true,0,"Ve al " + " y coge un avión. Si pasas por la casilla de Salida, cobra "),
            new CartaMovimiento(31,false,1,"Decides hacer un viaje de placer. Avanza hasta "),
            new CartaPagar(5000,true,"Vendes tu billete de avión para Argonath en una subasta por Internet. Cobra 50000€."),
            new CartaMovimiento(26,true,1,"Ve a Isengard Caverns. Si pasas por la casilla de Salida, cobra "),
            new CartaMovimiento(30,false,0,"Los acreedores te persiguen por impago. Ve a la cárcel. Ve directamente sin pasar por la casilla de salida y sin cobrar"),
            new CartaPagar(10000,true,"¡Has ganado el bote de la lotería! Recibe 1000000€.")
    ));

    public final static ArrayList<Carta> cartaCajaKomuna = new ArrayList<>(Arrays.asList(

    ));

    public static double PRECIO_G1 = 120;
    private static double precioTotalSolares = PRECIO_G1*23.85769;
    public final static double FORTUNA_INICIAL = precioTotalSolares /3.0;
    private static double dineroVuelta = precioTotalSolares /22;
    private static double dineroSalirCarcel = 0.25* dineroVuelta;
    private static double precioServicio = 0.75*dineroVuelta;

    //Multiplicadores
    public static final double MULTIPLICADOR_INICIAL_CASA = 0.60;
    public static final double MULTIPLICADOR_INIIAL_HOTEL = 0.60;
    public static final double MULTIPLICADOR_INICIAL_PISTA = 1.25;
    public static final double MULTIPLICADOR_INICIAL_PISCINA = 1.25;
    public static final double MULTIPLICADOR_HIPOTECA_CASILLA = 0.5;
    public static final double MULTIPLICADOR_HIPOTECA_EDIFICIO= 0.5;
    public static final double MULTIPLICADOR_CARGO_DESHIPOTECAR= 1.1;

    private static void setValoresEspeciales(){
        for(int i=5;i<40;i+=10){
            casillas.get(i).setPrecio(dineroVuelta);
        }
        casillas.get(12).setPrecio(precioServicio);
        casillas.get(28).setPrecio(precioServicio);
        casillas.get(4).setPrecio(-1.0*dineroVuelta/2);
        casillas.get(38).setPrecio(-1.0*dineroVuelta);
    }
    public static void setDineroAcumulado(double valor){
        if(valor >= 0)
            dineroAcumulado = valor;
    }

    public static double getDineroAcumulado(){
        return dineroAcumulado;
    }

    public static void actualizarDineroAcumulado(double valor){
        if(valor > 0)
            dineroAcumulado += valor;
    }

    public void actualizarValor(){
        this.valor = this.valor + this.valor*0.04;
    }

    public static HashMap<String,Grupo> getGrupos(){
        return grupos;
    }

    public static double getPrecioTotalSolares(){
        return precioTotalSolares;
    }

    public static double getDineroVuelta(){
        return dineroVuelta;
    }

    public static double getDineroSalirCarcel(){
        return dineroSalirCarcel;
    }

    public static void actualizarVuelta(){
        Iterator<Grupo> grup_i = Valor.getGrupos().values().iterator();
        while(grup_i.hasNext()) {
            Grupo grup = grup_i.next();
            grup.actualizarPrecio(0.05);
        }
        PRECIO_G1 += PRECIO_G1*0.05;
        precioTotalSolares = PRECIO_G1*23.85769;
        dineroVuelta = precioTotalSolares /22;
        dineroSalirCarcel = 0.25* dineroVuelta;
        precioServicio = dineroVuelta*0.75;
        setValoresEspeciales();
    }
}
