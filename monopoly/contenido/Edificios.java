package monopoly.contenido;

public class Edificios {

    private Casilla casilla;
    private double precio;
    private double modificadorAlquiler;
    private boolean hipotecado;

    public Casilla getCasilla(){
        return this.casilla;
    }

    //Los edificios están asignados a una casilla en concreto, no se puede cambiar un edificio de casilla

    public double getPrecio(){
        return this.precio;
    }

    public void setPrecio(double precio){
        if(precio > 0)
            this.precio = precio;
    }

    public double getModificadorAlquiler(){
        return this.modificadorAlquiler;
    }

    public void setModificadorAlquiler(double valor){
        this.modificadorAlquiler = valor;
    }

    public boolean getHipotecado(){
        return this.hipotecado;
    }

    public void setHipotecado(boolean hipotecado){
        this.hipotecado = hipotecado;
    }


}
