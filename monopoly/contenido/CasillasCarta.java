package monopoly.contenido;

import monopoly.plataforma.Accion;

import java.util.ArrayList;
import java.util.Scanner;

public class CasillasCarta extends Casilla {

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
            System.out.println("Indique un número del 1 al 10 para escoger carta");
            Scanner scanner= new Scanner(System.in);
            int num = scanner.nextInt();
            if(num >= 1 && num <= 10)
                return num;
            else
                System.out.println("El número indicado no está dentro de los límites exigidos");
        }
    }

    public void accionCaer(Jugador jugador,int tirada, Accion accion){
        ArrayList<Carta> casCaja = barajarCartas(this.cartas);
        Carta cartaEscogida = casCaja.get(elegirCarta()-1);
        cartaEscogida.accionCarta(jugador,accion.getTablero());
    }

}
