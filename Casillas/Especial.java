package Casillas;

import monopoly.Valor;
import partida.Jugador;

public class Especial extends Casilla {

    private int tipo;          //se mete un int de identificador, sino es imposible distinguir entre carcel, irCarcel e impuestos.
    //error=0 - Salida=1 - Carcel=2 - irCercel=3 - Impuestos=4

    public int getTipo() {return tipo;}

    public void setTipo(int val){
        if(val<5 && val>0){
            tipo=val;
        }
        else{
            tipo=0;
        }
    }
    //constructor de impuesto
    public Especial(int tipo,String nombre, int posicion, float impuesto, Jugador duenho){
        super(nombre, posicion, impuesto, duenho);
        this.setTipo(tipo);
    }

    //constructor de carcel+irCarcel
    public Especial(int tipo,String nombre, int posicion, Jugador duenho){
        super(nombre, posicion, 0, duenho);
        this.setTipo(tipo);
    }


    @Override
    public String infoCasilla() {
        //carcel
        if(getTipo()==2) {
            StringBuilder rt = new StringBuilder(("salir: " + Valor.COSTE_SALIR_CARCEL + "\n"));
            for (int i = 0; i < getAvatares().size(); i++) {
                rt.append("[").append(getAvatares().get(i).getJugador().getNombre()).append(",").append(getAvatares().get(i).getJugador().getTiradasCarcel()).append("]");
            }
            rt.append("\n\n");
            return rt.toString();
        }
        //impuesto
        else if(getTipo()==4){return ("tipo: impuesto\na pagar: " + getImpuesto() + "\n");}

        return("No hay informacion sobre esa casilla");

    }


}
