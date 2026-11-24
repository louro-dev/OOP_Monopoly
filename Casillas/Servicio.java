package Casillas;

import monopoly.Valor;
import partida.Jugador;

public class Servicio extends Propiedad {

    /*Constructor para crear una casilla de Servicio (mismo precio de compra:
     * 500.000€). El alquiler de de servicio se calculará dinámicamente
     * más adelante (en evaluarCasilla), según el valor de los dados, así que aquí no se fija.
     */
    public Servicio(String nombre, int posicion, Jugador banca) {
        setValor((int) Valor.PRECIO_TRANSPORTE_SERVICIO);
        setNombre(nombre);
        setPosicion(posicion);
        banca.anhadirPropiedad(this);
    }

    @Override
    public void hipotecar(Jugador solicitante){
        String nombreCasilla = this.getNombre().trim();
        if(!this.getDuenho().equals(solicitante)){
            System.out.println(solicitante.getNombre() + " no puede hipotecar " + nombreCasilla + ". No es su propiedad.\n");
            return;
        }
        if(this.getHipotecada()){
            System.out.println(solicitante.getNombre() + " no puede hipotecar " + nombreCasilla + ". Ya está hipotecada.\n");
            return;
        }
        setHipotecada(true);
        solicitante.sumarFortuna(getHipoteca());
        solicitante.sumarInversionPropiedades((-1)*getHipoteca());

        String mensaje= solicitante.getNombre() + " recibe " + (long) this.getHipoteca()+  "€ por la hipoteca de " + nombreCasilla + ".\n";
        mensaje += "No puede recibir alquileres por esta propiedad.";

        System.out.println(mensaje);
    }

    public String toString() {return "tipo: servicio\nvalor: " + getValor() + "\n";}
}
