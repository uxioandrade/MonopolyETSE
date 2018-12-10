package monopoly.contenido;

import monopoly.plataforma.Tablero;
import monopoly.plataforma.Accion;
import monopoly.plataforma.Valor;

public final     class Coche extends Avatar{

    private int numTiradas;
    private boolean poderComprar;

    public Coche(Jugador jug, Tablero tablero){
        super(jug,tablero);
        numTiradas = 1;
        poderComprar = true;
    }

    public void moverEnAvanzado(int valor){
        Accion accion = new Accion(this.getTablero());
        if(this.numTiradas > 0) {
            if (valor > 4) {
                if (this.numTiradas == 1) {
                    this.numTiradas = 4;
                    System.out.println("El coche aún puede realizar " + (this.numTiradas - 1) + " tiradas este turno");
                }else if (this.numTiradas == 2){
                    this.numTiradas = 0;
                    System.out.println("El coche ha acabado sus tiradas en este turno");
                }else{
                    this.numTiradas--;
                    System.out.println("El coche aún puede realizar " + (this.numTiradas - 1) + " tiradas este turno");
                }
                System.out.println("El avatar " + this.getId() + " avanza " + valor + " posiciones, desde " + this.getCasilla().getNombre() + " hasta " + Valor.casillas.get((this.getCasilla().getPosicion() + valor) % 40).getNombre());
                this.moverEnBasico(valor);
                this.getCasilla().accionCaer(this.getJugador(), valor, accion);
            } else {
                if (this.numTiradas == 1) {
                    this.retrocederCasillas(valor);
                    this.getCasilla().accionCaer(this.getJugador(), valor, accion);
                    numTiradas = -2;
                    System.out.println("El coche ha obtenido un valor menor o igual de 4, retrocede " + valor + " casillas y estará 2 turnos sin poder tirar");
                } else {
                    this.numTiradas = 0;
                    System.out.println("El valor obtenido ha sido menor de 4, el coche no puede tirar más veces");
                }
            }
        }else
            System.out.println(this.getJugador().getNombre() + " sigue sin poder tirar");
    }

    public boolean getPoderComprar(){
        return this.poderComprar;
    }

    public void setPoderComprar(boolean valor){
        this.poderComprar = valor;
    }

    public int getNumTiradas(){
        return this.numTiradas;
    }

    public void anhadirTirada(){
        this.numTiradas++;
    }

    public void setNumTiradas(int valor){
        if(valor >= -2 && valor < 5) //Rango de valores que puede tomar este atributo
            this.numTiradas = valor;
    }

}
