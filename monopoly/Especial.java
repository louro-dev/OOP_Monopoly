package monopoly;

public class Especial extends Casilla {

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
