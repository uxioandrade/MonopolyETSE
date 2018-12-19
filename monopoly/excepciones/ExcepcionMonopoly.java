package monopoly.excepciones;

public abstract class ExcepcionMonopoly extends Exception{

    private final String mensaje;

    public ExcepcionMonopoly(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

}
