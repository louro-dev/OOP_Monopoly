package Casillas;

import partida.Jugador;

public class Parking extends Accion {

    private float valorAcumulado; //guarda el valor acumulado por los impuestos

    public float getValorAcumulado() {return valorAcumulado;}

    public void setValorAcumulado(float valorAcumulado) {this.valorAcumulado = valorAcumulado;}

    public void sumarValorParking(float val){
        if(val>0){
            setValorAcumulado(getValorAcumulado()+val);
        }
    }

    public Parking(String nombre, int posicion, Jugador duen){
        super(nombre, posicion, duen);
        setValorAcumulado(0);
    }

    @Override
    public String infoCasilla() {
        StringBuilder ret = new StringBuilder(("bote: " + getValorAcumulado() + "\n["));
        for (int i = 0; i < getAvatares().size(); i++) {
            ret.append(this.getAvatares().get(i).getJugador().getNombre());}
        ret.append("]\n\n");
        return ret.toString();

    }
}
