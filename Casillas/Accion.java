package Casillas;

import partida.Jugador;

public abstract class Accion extends Casilla {

    int valor;

    //SETTERS
    void setValor(int val){
        if(val>0 && val<7){
            valor=val;
        }
    }

    //GETTERS
    int getValor(){
        return valor;
    }

    //CONSTRUCTORES
    public Accion(String nombre, int posicion, Jugador duen){
        super(nombre,posicion, duen);
    }

    void actualizarValor(){
        setValor((int)(Math.random()*7)+1);
    }
}
