package monopoly.contenido;

import java.util.ArrayList;


public class Grupo {

    private ArrayList<Casilla> casillas;
    private double precio;
    private String nombre;//Nombre asociado, que es el color
    private String color;//String asociado al color

    public Grupo(String nombre,String color, ArrayList<Casilla> casillas, double precio){
        this.casillas = casillas;
        this.nombre = nombre;
        this.color = color;
            this.precio = precio;
            //Recorre el ArrayList de casillas, asigna el grupo a las casillas y inicializa el precio de cada casilla
            for(Casilla cas : casillas){
                cas.setPrecio(precio/casillas.size());
                cas.setGrupo(this);
            }
    }

    public void setPrecio(double precio){
        if(precio>0)
            this.precio = precio;
    }

    public double getPrecio(){
        return this.precio;
    }

    public void actualizarPrecio(Double precio){
        this.precio += this.precio*precio;
        for(Casilla cas : casillas)
            cas.setPrecio(this.precio/casillas.size());
    }

    //Para los siguientes getters no es necesario crear su setter correspondiente, pues son atributos que no se modifican a lo largo del programa
    public ArrayList<Casilla> getCasillas(){
        return this.casillas;
    }

    public String getColor(){
        return this.color;
    }

    public String getNombre(){
        return this.nombre;
    }

    @Override
    public String toString(){
        String aux = "{\nGrupo: " + this.nombre + "\nPrecio por propiedad:" + this.precio + "\nPrecio por hipoteca" + this.precio/2.0 + "\nPrecio por alquiler" + this.precio*0.1 + "\n";
        for(Casilla c : casillas){
            aux += "\n";
            aux += c.getNombre();
            if(c.getPropietario() != null)
                aux += " Vendida";
        }
        aux += "\n}";
        return aux;
    }

}
