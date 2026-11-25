package Casillas;

import monopoly.*;
import partida.*;

import java.util.ArrayList;

public class CajaComunidad extends Accion {

    public CajaComunidad(String nombre, int posicion, Jugador dueno){
        super(nombre,posicion,dueno);
    }

    public void cajaComunidad(Juego j){

        ArrayList<Jugador> jugadores = j.getJugadores();
        Jugador actual=jugadores.get(j.getTurno());

        actualizarValor();

        switch (getValor()){
            case 1:
                System.out.println("Paga 500.000 por un balneario de 5 estrellas");
                actual.sumarFortuna((-1)*500000);
                actual.sumarTasasImpuestos(500000);
                break;
            case 2:
                System.out.println("""
                        Te investigan por fraude de identidad
                        Ve a la carcel.
                        Ve directamente sin pasar por la casilla de salida y sin cobrar los 2.000.000
                        """);
                actual.encarcelar(j.getTablero());
                break;
            case 3:
                System.out.println("Colocate en la salida.\nCobra 2.000.000");
                int po = getPo(j, actual);
                actual.getAvatar().moverAvatar(j.getTablero().getPosiciones(),-(po-1));
                break;

            case 4:
                System.out.println("Devolución de hacienda\nCobra 500.000");
                actual.sumarFortuna(500000);
                actual.sumarTasasImpuestos(500000);
                break;
            case 5:
                System.out.println("Retrocede a Solar1 para comprar antigüedades exoticas");
                int p1 = getPo(j, actual);
                actual.getAvatar().moverAvatar(j.getTablero().getPosiciones(), -(p1-2));
                j.evaluarCasillaDestino(actual,actual.getAvatar().getLugar(),0);
                break;

            case 6:
                System.out.println("Ve a Solar20 para disfrutar del San Fermín. Si pasas por la casilla de salida, cobra 2.000.000");
                System.out.println("Retrocede a Solar1 para comprar antigüedades exoticas");
                int p2= getPo(j,actual);
                actual.getAvatar().moverAvatar(j.getTablero().getPosiciones(), 35-p2);
                j.evaluarCasillaDestino(actual,actual.getAvatar().getLugar(),0);
                break;
        }
    }

    private static int getPo(Juego j, Jugador actual) {
        int po=0;
        for(int i = 0; i< j.getTablero().getPosiciones().size(); i++){
            for(int l = 0; l < j.getTablero().getPosiciones().get(i).size(); l++){
                for(int k = 0; k< j.getTablero().getPosiciones().get(i).get(l).getAvatares().size(); k++){
                    if (j.getTablero().getPosiciones().get(i).get(l).getAvatares().get(k).getJugador().equals(actual)) {
                        po = j.getTablero().getPosiciones().get(i).get(l).getPosicion();
                        break;
                    }
                }
            }
        }
        return po;
    }

    @Override
    public String infoCasilla() {
        return "Tipo: Caja de Comunidad\nRealzia una accion de entre las definidas";
    }
}
