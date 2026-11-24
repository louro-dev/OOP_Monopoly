package Casillas;

public class Parking extends Accion {

    private float valorAcumulado; //guarda el valor acumulado por los impuestos

    public float getValorAcumulado() {return valorAcumulado;}

    public void setValorAcumulado(float valorAcumulado) {this.valorAcumulado = valorAcumulado;}

    public Parking(){
        setValorAcumulado(0);
    }

    public String toString() {
        StringBuilder ret = new StringBuilder(("bote: " + getValorAcumulado() + "\n["));
        for (int i = 0; i < getAvatares().size(); i++) {
            ret.append(this.getAvatares().get(i).getJugador().getNombre());}
        ret.append("]\n\n");
        return ret.toString();

    }
}
