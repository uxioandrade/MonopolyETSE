package monopoly.contenido;

import monopoly.plataforma.Accion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Tablero;

import java.util.Scanner;

public final class Pelota extends Avatar{

    public Pelota(Jugador jug, Tablero tablero){
        super(jug,tablero);
        super.numTiradas = 1;
    }

    private void accionRebote(int valor){
        Accion accion = new Accion(this.getTablero());
        Juego.consola.imprimir("El jugador " + this.getJugador().getNombre() + " ha rebotado a " + this.getCasilla().getNombre());
        super.getTablero().imprimirTablero();
        this.getCasilla().accionCaer(this.getJugador(),valor,accion);
        if(this.getCasilla() instanceof Propiedades){
            Propiedades comprable = (Propiedades) this.getCasilla();
            if(comprable.getPropietario().getNombre().equals("Banca")){
                Juego.consola.imprimir("Desea comprar la propiedad " + this.getCasilla().getNombre() + " ? (Si/No)");
                //System.out.print("$> ");
                //Scanner scanner = new Scanner(System.in);
                //String orden = scanner.nextLine();
                String orden =Juego.consola.leer("$> ");
                if(orden.equals("Si") || orden.equals("si") || orden.equals("SI")) {
                    if (this.getJugador().getDinero() >= comprable.getPrecio()) {
                        accion.comprar(this.getJugador());
                    }else{
                       Juego.consola.imprimir("No tienes dinero suficiente para adquirir esta propiedad");
                    }
                }
            }
        }
    }

    public void moverEnAvanzado(int valor) {
        if (valor > 4) {
            super.moverEnBasico(5);
            this.accionRebote(valor);
            for (int i = 7; i <= valor; i = i + 2) {
                if (this.getEncarcelado() > 0)
                    return;
                super.moverEnBasico(2);
                this.accionRebote(valor);
            }
            if (this.getEncarcelado() > 0)
                return;
            if (valor % 2 == 0) {
                moverEnBasico(1);
                this.accionRebote(valor);
            }
        } else {
            retrocederCasillas(1);
            this.accionRebote(valor);
            if (valor > 2) {
                if (this.getEncarcelado() > 0)
                    return;
                this.retrocederCasillas(2);
                this.accionRebote(valor);
            }
            if (this.getEncarcelado() > 0)
                return;
            if (valor % 2 == 0) {
                this.retrocederCasillas(1);
                this.accionRebote(valor);
            }
        }
        Juego.consola.imprimir("La pelota ha dejado de rebotar. Volviendo al menú principal");
    }
}
