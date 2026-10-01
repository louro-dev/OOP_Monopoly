package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MonopolyETSE {

    public static void main(String[] args) {
        Menu menu = new Menu();
        Jugador banca = new Jugador();
        Tablero tablero = new Tablero(banca);
        Scanner sc = new Scanner(System.in);
        String answ="";

        // PRUEBA
        // 4. Obtener la casilla 'Salida' (primera casilla del lado Sur)
        Casilla salida =  tablero.encontrar_casilla("Salida        ");

        // 5. Crear un jugador principal (su avatar se colocará en Salida y se guardará en la casilla)
        Jugador jugador1 = new Jugador("Rocío", "Coche", salida, menu.getAvatares());

        // 6. Creamos varios jugadores para ver que se comporta bien el toString
        Jugador jugador2 = new Jugador ("Rodrigo","Coche",salida, menu.getAvatares());
        Jugador jugador3 = new Jugador ("Piouvi","Coche",salida, menu.getAvatares());
        Jugador jugador4 = new Jugador ("Habibi","Coche",salida, menu.getAvatares());

        //imprimimos tablero
        System.out.println(tablero);

        //imprimimos la opcion para pedir los dados y la escaneamos con sc.nextLine()
        System.out.println("lanzar dados: t\n");
        answ=sc.nextLine();

        //pasamos el comando por el método analizarComando
        menu.analizarComando(answ,tablero.getPosiciones());

        //reimprimimos el tablero para ver si se ha movido alguno de los avatares
        System.out.println(tablero);

    }
    
}
