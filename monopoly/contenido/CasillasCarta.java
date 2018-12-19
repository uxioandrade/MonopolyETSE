package monopoly.contenido;

import monopoly.plataforma.Operacion;
import monopoly.plataforma.Juego;

import java.util.ArrayList;

public final class CasillasCarta extends Accion {

    private ArrayList<Carta> cartas;

    public CasillasCarta(String nombre, int posicion, ArrayList<Carta> cartas){
        super(nombre,posicion);
        this.cartas = cartas;
    }

    private ArrayList<Carta> barajarCartas(ArrayList<Carta> cartas){
        ArrayList<Carta> aux = new ArrayList<>(cartas);
        java.util.Collections.shuffle(aux);
        return aux;
    }

    private int elegirCarta(){
        while(true) {
            //System.out.println("Indique un número del 1 al " + this.cartas.size() + " para escoger carta");
            //Scanner scanner= new Scanner(System.in);
            int num = Integer.parseInt(Juego.consola.leer("Indique un número del 1 al " + this.cartas.size() + " para escoger carta\n"));
            if(num >= 1 && num <= this.cartas.size())
                return num;
            else
                System.out.println("El número indicado no está dentro de los límites exigidos");
        }
    }

    public void accionCaer(Jugador jugador,int tirada, Operacion operacion){
        ArrayList<Carta> casCaja = barajarCartas(this.cartas);
        Carta cartaEscogida = casCaja.get(elegirCarta()-1);
        cartaEscogida.accionCarta(jugador, operacion.getTablero());
    }
}
