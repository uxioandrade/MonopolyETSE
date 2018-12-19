package monopoly.plataforma;

import monopoly.excepciones.*;

public interface Comando {

    public void describir(String[] partes);
    public void comprar();
    public void edificar(String[] partes);
    public void vender(String[] partes);
    public void hipotecar(String[] partes);
    public void deshipotecar(String[] partes);
    public void lanzar(String[] partes) throws ExcepcionesDinamicaTurno;
    public void acabar(String partes[]);
    public void salir(String partes[]);
    public void listar(String partes[]);
    public void jugador(String partes[]);
    public void ver(String partes[]);
    public void cambiar(String partes[]);
    public void estadisticas(String partes[]);
    public void trato(String partes[]);

}
