package monopoly.plataforma;

import monopoly.excepciones.*;

public interface Comando {

    public void describir(String[] partes) throws ExcepcionNumeroPartesComando;
    public void comprar();
    public void edificar(String[] partes) throws ExcepcionNumeroPartesComando;
    public void vender(String[] partes) throws ExcepcionNumeroPartesComando;
    public void hipotecar(String[] partes) throws ExcepcionNumeroPartesComando;
    public void deshipotecar(String[] partes) throws ExcepcionNumeroPartesComando;
    public void lanzar(String[] partes) throws ExcepcionesDinamicaTurno, ExcepcionNumeroPartesComando;
    public void acabar(String partes[]) throws ExcepcionesDinamicaTurno, ExcepcionNumeroPartesComando;
    public void salir(String partes[]) throws ExcepcionNumeroPartesComando;
    public void listar(String partes[]) throws ExcepcionNumeroPartesComando;
    public void jugador(String partes[]) throws ExcepcionNumeroPartesComando;
    public void ver(String partes[]) throws ExcepcionNumeroPartesComando;
    public void cambiar(String partes[]) throws ExcepcionesDinamicaTurno, ExcepcionNumeroPartesComando;
    public void estadisticas(String partes[]) throws ExcepcionNumeroPartesComando;
    public void trato(String partes[]) throws ExcepcionNumeroPartesComando;
    public void aceptarTrato(String partes[]) throws ExcepcionNumeroPartesComando;

}
