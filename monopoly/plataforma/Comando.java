package monopoly.plataforma;

import monopoly.contenido.Dados;
import monopoly.contenido.Jugador;
import monopoly.plataforma.Accion;
import monopoly.plataforma.Tablero;

import java.util.ArrayList;
import java.util.SplittableRandom;

public interface Comando {

    public void describir(String[] partes, Tablero tablero);
    public void comprar(Jugador jugadorActual, Accion accion);
    public void edificar(String[] partes,Jugador jugadorActual, Accion accion);
    public void vender(String[] partes,Jugador jugadorActual,Tablero tablero, Accion accion);
    public void hipotecar(String[] partes,Jugador jugadorActual,Tablero tablero, Accion accion);
    public void deshipotecar(String[] partes,Jugador jugadorActual,Tablero tablero, Accion accion);
    public void lanzar(String[] partes,Jugador jugadorActual,Tablero tablero, Accion accion, int countTiradas, int tirada, int vecesDobles, Dados dados);
    public void acabar(String partes[], Jugador jugadorActual,Tablero tablero, int countTiradas, int vecesDobles, int turno, ArrayList<String> turnosJugadores);
    public void salir(String partes[],Jugador jugadorActual, int countTiradas, Accion accion, Boolean salir);
    public void listar(String partes[], Tablero tablero);
    public void jugador(String partes[],Jugador jugadorActual);
    public void ver(String partes[],Tablero tablero);
    public void cambiar(String partes[], Tablero tablero, Jugador jugadorActual, int countTiradas);
    public void estadisticas(String partes[], Tablero tablero, ArrayList<String> turnosJugadores);

}
