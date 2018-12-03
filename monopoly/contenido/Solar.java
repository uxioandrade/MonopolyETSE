package monopoly.contenido;

import java.util.ArrayList;
import monopoly.plataforma.Accion;
import monopoly.plataforma.Valor;


public class Solar extends Propiedades {

    private Grupo grupo;
    private ArrayList<Edificios> construcciones;

    public Solar(String nombre, int posicion){
        super(nombre,posicion);
        this.construcciones = new ArrayList<>();
    }

    public Grupo getGrupo(){
        return this.grupo;
    }

    public void setGrupo(Grupo grupo){
        this.grupo = grupo;
    }

    public double alquiler(int valor){
        if(super.getPropietario().getNombre().contains("Banca")) return 0.1*super.getPrecio();
        double alquiler=0;
        if(this.construcciones.size()==0) alquiler=0.1*super.getPrecio();
        else{
            switch (this.getConstrucciones("casa").size()){
                case 1:
                    alquiler+=5*super.getPrecio()*0.1;
                    break;
                case 2:
                    alquiler+=15*super.getPrecio()*0.1;
                    break;
                case 3:
                    alquiler+=35*super.getPrecio()*0.1;
                    break;
                case 4:
                    alquiler+=50*super.getPrecio()*0.1;
                    break;
                default:

            }
            alquiler+=70*this.getConstrucciones("hotel").size()*super.getPrecio()*0.1;
            alquiler+=25*this.getConstrucciones("piscina").size()*super.getPrecio()*0.1;
            alquiler+=25*this.getConstrucciones("pista").size()*super.getPrecio()*0.1;
        }

        if(this.getPropietario().poseeGrupoCompleto(this.grupo)){
            return 2*alquiler;
        }
        return alquiler;
    }

    public void anhadirEdificio(Edificios edificio){
        if(edificio != null)
            this.construcciones.add(edificio);
    }

    public void borrarEdificio(Edificios edificio){
        if(this.construcciones.contains(edificio))
            this.construcciones.remove(edificio);
        else
            System.out.println("Este solar no tiene el edificio indicado");
    }

    public ArrayList<Edificios> getConstrucciones(){
        return this.construcciones;
    }

    public ArrayList<Edificios> getConstrucciones(String tipo){
        ArrayList<Edificios> construccionesTipo = new ArrayList<>();

        switch (tipo){
            case "casa":
            case "Casa":
                for(Edificios c : this.construcciones){
                    if(c instanceof Casa)
                        construccionesTipo.add(c);
                }
                break;
            case "hotel":
            case "Hotel":
                for(Edificios c : this.construcciones){
                    if(c instanceof Hotel)
                        construccionesTipo.add(c);
                }
                break;
            case "piscina":
            case "Piscina":
                for(Edificios c : this.construcciones){
                    if(c instanceof Piscina)
                        construccionesTipo.add(c);
                }
                break;
            case "pista":
            case "Pista":
                for(Edificios c : this.construcciones){
                    if(c instanceof PistaDeporte)
                        construccionesTipo.add(c);
                }
                break;
            default:
                return construccionesTipo;
        }
        return  construccionesTipo;
    }

    public void pagarAlquiler(Jugador jugador, int tirada, Accion accion){
        if (jugador.getDinero() >= this.alquiler(tirada)){
            //Se resta el alquiler del jugador que ha caído en el servicio
            jugador.modificarDinero(-this.alquiler(tirada));
            jugador.modificarPagoAlquileres(this.alquiler(tirada));
            System.out.println("Se han pagado " + this.alquiler(tirada) + "€ de alquiler.");
            //Se aumenta el dinero del propietario
            super.getPropietario().modificarDinero(this.alquiler(tirada));
            super.getPropietario().modificarCobroAlquileres(this.alquiler(tirada));
            super.sumarRentabilidad(this.alquiler(tirada));
            this.grupo.sumarRentabilidad(this.alquiler(tirada));
        } else {
            System.out.println("No dispones de capital suficiente para efectuar esta operación. Prueba a hipotecar tus propiedades, a negociar o declararte en bancarrota");
            if(accion.menuHipotecar(jugador,accion.getTablero(),this.alquiler(tirada))){
                pagarAlquiler(jugador,tirada,accion);
            }
        }
    }

    public void edificar(String tipo){
        if( this.getPropietario().getPropiedades().contains(this)){
            if(this.frecuenciaVisita( this.getPropietario().getAvatar()) > 2 ||  this.getPropietario().poseeGrupoCompleto(this.getGrupo())){
                switch (tipo){
                    case "casa":
                    case "Casa":
                        if(this.getConstrucciones("Casa").size() < 4) {
                            if(this.getGrupo().getHotlesGrupo().size()>=this.getGrupo().getCasillas().size() && this.getGrupo().getCasasGrupo().size()>=this.getGrupo().getCasillas().size()){
                                System.out.println("Operación Cancelada: Si construyes esta casa sobrepasarás el límite de edificaciones del grupo");
                                return;
                            }
                            if ( this.getPropietario().getDinero() >= Valor.MULTIPLICADOR_INICIAL_CASA * this.getPrecio()) {
                                this.getPropietario().modificarDinero(-Valor.MULTIPLICADOR_INICIAL_CASA * this.getPrecio());
                                this.getPropietario().modificarDineroInvertido(Valor.MULTIPLICADOR_INICIAL_CASA * this.getPrecio());
                                Casa nuevaCasa = new Casa(Valor.MULTIPLICADOR_INICIAL_CASA * this.getPrecio(), this);
                                this.tablero.anhadirEdificio(nuevaCasa);
                                this.anhadirEdificio(nuevaCasa);
                                System.out.println("Se ha edificado una casa en " + this.getNombre() + ". La fortuna de " +  this.getPropietario().getNombre() +"\n" +
                                        "se reduce en " + Valor.MULTIPLICADOR_INICIAL_CASA * this.getPrecio() + "€.");
                            } else
                                System.out.println("El jugador " +  this.getPropietario().getNombre() + " no dispone de dinero suficiente para edificar una casa");
                        }else{
                            System.out.println("No se pueden construir más de 4 casas en cada solar");
                        }
                        break;
                    case "hotel":
                    case "Hotel":
                        if( this.getPropietario().getDinero() >= Valor.MULTIPLICADOR_INICIAL_HOTEL *(this.getPrecio())){
                            if(((Solar) this).getConstrucciones("Casa").size() >= 4) {
                                if(this.getGrupo().getHotlesGrupo().size()>=this.getGrupo().getCasillas().size()){
                                    System.out.println("Operación Cancelada: Si construyes este hotel sobrepasarás el límite de edificaciones del grupo");
                                    return;
                                }
                                for(int i = 0;i <4;i++){
                                    Casa casaBorrar = ((Casa) this.getConstrucciones("Casa").get(0));
                                    this.getConstrucciones().remove(casaBorrar);
                                    this.tablero.borrarEdificio(casaBorrar);
                                }
                                this.getPropietario().modificarDinero(-Valor.MULTIPLICADOR_INICIAL_HOTEL * this.getPrecio());
                                this.getPropietario().modificarDineroInvertido(-Valor.MULTIPLICADOR_INICIAL_HOTEL * this.getPrecio());
                                Hotel nuevoHotel = new Hotel(Valor.MULTIPLICADOR_INICIAL_HOTEL * this.getPrecio(), this);
                                this.anhadirEdificio(nuevoHotel);
                                this.tablero.anhadirEdificio(nuevoHotel);
                                System.out.println("Se ha edificado un hotel en " + this.getNombre() + ". La fortuna de " +  this.getPropietario().getNombre() +"\n" +
                                        "se reduce en " + Valor.MULTIPLICADOR_INICIAL_HOTEL * this.getPrecio() + "€.");
                            }else{
                                System.out.println("Tienes que tener un mínimo de 4 casas para construir un hotel");
                            }
                        }else
                            System.out.println("El jugador " +  this.getPropietario().getNombre() + " no dispone de dinero suficiente para edificar una casa");
                        break;
                    case "piscina":
                    case "Piscina":
                        if( this.getPropietario().getDinero() >= Valor.MULTIPLICADOR_INICIAL_PISCINA*this.getPrecio()){
                            if(this.getConstrucciones("Casa").size() >= 2 && this.getConstrucciones("Hotel").size() >= 1) {
                                if(this.getGrupo().getPiscinasGrupo().size()>=this.getGrupo().getCasillas().size()){
                                    System.out.println("Operación Cancelada: Si construyes esta piscina sobrepasarás el límite de edificaciones del grupo");
                                    return;
                                }
                                this.getPropietario().modificarDinero(-Valor.MULTIPLICADOR_INICIAL_PISCINA * this.getPrecio());
                                this.getPropietario().modificarDineroInvertido(-Valor.MULTIPLICADOR_INICIAL_PISCINA * this.getPrecio());
                                Piscina nuevaPiscina = new Piscina(Valor.MULTIPLICADOR_INICIAL_PISCINA* this.getPrecio(), this);
                                this.anhadirEdificio(nuevaPiscina);
                                this.tablero.anhadirEdificio(nuevaPiscina);
                                System.out.println("Se ha edificado una piscina en " + this.getNombre() + ". La fortuna de " +  this.getPropietario().getNombre() +"\n" +
                                        "se reduce en " + Valor.MULTIPLICADOR_INICIAL_PISCINA * this.getPrecio() + "€.");
                            }else{
                                System.out.println("Tienes que tener un mínimo de 2 casas y un hotel para construir una piscina");
                            }
                        }else
                            System.out.println("El jugador " +  this.getPropietario().getNombre() + " no dispone de dinero suficiente para edificar una piscina");
                        break;
                    case "pista":
                    case "Pista":
                        if( this.getPropietario().getDinero() >= Valor.MULTIPLICADOR_INICIAL_PISTA*this.getPrecio()){
                            if(this.getConstrucciones("Hotel").size() >= 2) {
                                if(this.getGrupo().getPistaDeportesGrupo().size()>=this.getGrupo().getCasillas().size()){
                                    System.out.println("Operación Cancelada: Si construyes esta pista de deporte sobrepasarás el límite de edificaciones del grupo");
                                    return;
                                }
                                this.getPropietario().modificarDinero(-Valor.MULTIPLICADOR_INICIAL_PISTA * this.getPrecio());
                                this.getPropietario().modificarDineroInvertido(-Valor.MULTIPLICADOR_INICIAL_PISTA * this.getPrecio());
                                PistaDeporte nuevaPista = new PistaDeporte(Valor.MULTIPLICADOR_INICIAL_PISTA*this.getPrecio(), this);
                                this.anhadirEdificio(nuevaPista);
                                this.tablero.anhadirEdificio(nuevaPista);
                                System.out.println("Se ha edificado una piscina en " + this.getNombre() + ". La fortuna de " +  this.getPropietario().getNombre() +"\n" +
                                        "se reduce en " + Valor.MULTIPLICADOR_INICIAL_PISTA * this.getPrecio() + "€.");
                            }else{
                                System.out.println("Tienes que tener un mínimo de 2 hoteles para construir una pista de deporte");
                            }
                        }else
                            System.out.println("El jugador " +  this.getPropietario().getNombre() + " no dispone de dinero suficiente para edificar una pista de deporte");
                        break;
                    default:
                        System.out.println("El tipo de edificación indicado no existe.");
                }
            }else{
                System.out.println( this.getPropietario().getNombre() + " no cumple los requisitos necesarios para edificar en la casilla " + this.getNombre());
            }
        }else{
            System.out.println( this.getPropietario().getNombre() + " no posee la casilla " + this.getNombre());
        }
    }
    }

}
