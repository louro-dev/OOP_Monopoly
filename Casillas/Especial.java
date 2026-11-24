package Casillas;

import monopoly.Valor;

public class Especial extends Casilla {

    private int Tipo;          //se mete un int de identificador, sino es imposible distinguir entre carcel, irCarcel e impuestos.


    @Override
    public String toString() {
        StringBuilder rt = new StringBuilder(("salir: " + Valor.COSTE_SALIR_CARCEL + "\n"));
        for (int i = 0; i < getAvatares().size(); i++) {
            rt.append("[").append(getAvatares().get(i).getJugador().getNombre()).append(",").append(getAvatares().get(i).getJugador().getTiradasCarcel()).append("]");}
        rt.append("\n\n");
        return rt.toString();
    }

    public String toString() {return ("tipo: impuesto\na pagar: " + getImpuesto() + "\n");}

}
