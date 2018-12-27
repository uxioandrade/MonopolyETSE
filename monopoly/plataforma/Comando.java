package monopoly.plataforma;

import monopoly.excepciones.*;

public interface Comando {

    public void describir(String[] partes) throws ExcepcionNumeroPartesComando;
    public void comprar() throws ExcepcionDineroVoluntario, ExcepcionRestriccionComprar;
    public void edificar(String[] partes) throws ExcepcionNumeroPartesComando, ExcepcionDineroVoluntario, ExcepcionRestriccionEdificar;
    public void vender(String[] partes) throws ExcepcionNumeroPartesComando, ExcepcionRestriccionEdificar;
    public void hipotecar(String[] partes) throws ExcepcionNumeroPartesComando, ExcepcionRestriccionHipotecar;
    public void deshipotecar(String[] partes) throws ExcepcionNumeroPartesComando, ExcepcionRestriccionHipotecar, ExcepcionDineroVoluntario;
    public void lanzar(String[] partes) throws ExcepcionesDinamicaTurno, ExcepcionNumeroPartesComando,ExcepcionRestriccionHipotecar, ExcepcionDineroDeuda, ExcepcionesDinamicaEncarcelamiento, ExcepcionRestriccionEdificar, ExcepcionDineroVoluntario, ExcepcionRestriccionComprar ;
    public void acabar(String partes[]) throws ExcepcionesDinamicaTurno, ExcepcionNumeroPartesComando;
    public void salir(String partes[]) throws ExcepcionNumeroPartesComando, ExcepcionRestriccionHipotecar, ExcepcionRestriccionEdificar;
    public void listar(String partes[]) throws ExcepcionNumeroPartesComando;
    public void jugador(String partes[]) throws ExcepcionNumeroPartesComando;
    public void ver(String partes[]) throws ExcepcionNumeroPartesComando;
    public void cambiar(String partes[]) throws ExcepcionNumeroPartesComando, ExcepcionDinamicaModoMovimiento;
    public void estadisticas(String partes[]) throws ExcepcionNumeroPartesComando;
    public void trato(String partes[]) throws ExcepcionNumeroPartesComando , ExcepcionRestriccionPropiedades;
    public void aceptarTrato(String partes[]) throws ExcepcionNumeroPartesComando, ExcepcionRestriccionPropiedades, ExcepcionDineroVoluntario;
    public void borrarTrato(String partes[]) throws ExcepcionNumeroPartesComando, ExcepcionRestriccionPropiedades;
}
