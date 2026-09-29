package monopoly;

import partida.*;
import java.util.ArrayList;

public class MonopolyETSE {

    public static void main(String[] args) {
        new Menu();
        Jugador banca = new Jugador();
        Tablero tablero = new Tablero(banca);

        // PRUEBA
        /*// 3. Crear la lista para llevar el registro global de avatares
        ArrayList<Avatar> avCreados = new ArrayList<>();

        // 4. Obtener la casilla 'Salida' (primera casilla del lado Sur)
        Casilla salida = tablero.getPosiciones().get(0).get(0);

        // 5. Crear un jugador principal (su avatar se colocará en Salida y se guardará en la casilla)
        Jugador jugador1 = new Jugador("Rocío", "Coche", salida, avCreados);*/

        System.out.println(tablero);
    }
    
}
