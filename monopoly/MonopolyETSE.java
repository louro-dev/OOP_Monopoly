package monopoly;

import partida.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MonopolyETSE {

    static void main(String[] args) {
        //creamos booleano de condicion de salida
        boolean getaway = false;
        //creamos menu nuevo
        Menu menu = new Menu();
        //inicializamos documento de condiciones iniciales
        File iniDoc=null;

        //creamos variable de control para gestionar en que iteraciones se imprime el tablero: si se va a mover un jugador
        //si hace falta, pero si solo se pide que se imprima algo no hace falta reimprimirlo
        boolean ctrl=true;

        //Si se pasa el documento por argumento se abre y ya
        if(args.length>0)   iniDoc=new File(args[0]);
        //si no se pregunta si se quiere usar o no. Si sí se pide.
        else{

            System.out.println("Ningun documento introducido en linea de comandos\n quiere usar documento inicial?: y/n\n");
            String ans=new Scanner(System.in).next();
            switch (ans) {
                case "y":
                    System.out.println("dea el nombre del documento:\n");
                    String doc=new Scanner(System.in).next();
                    iniDoc=new File(doc);
                    break;
                case "n":
                default:
                    //temporalmente usamos la variable de control para controlar si se va a usar documento inicial o no
                    ctrl=false;
                    System.out.println("empezando sin documento inicial...\n");
                    break;
            }
        }

        //llamamos al metodo que inicializa la partida
        menu.iniciarPartida(iniDoc);

        if(ctrl) System.out.println(menu.getTablero());

        do{
            //reiniciamos variable de control de impresion
            ctrl=true;
            //imprimimos la opcion para pedir los dados y la escaneamos con sc.nextLine()
            Scanner sc = new Scanner(System.in);
            System.out.println("--------------------MENU--------------------\nlanzar dados: lanzarDados\n" +
                    "Crear jugador: crearJugador nombre tipo_avatar\nJugador al que le toca: jugadorTurno\n" +
                    "Listar jugadores: listarJugadores\nAcabar Partida: salir");
            String answ = sc.nextLine();

            //separamos String en partes para los comandos de mas de 1 palabra
            String[] divEn = answ.split(" ");

            //pasamos el comando por el método analizarComando
            int sec = menu.analizarComando(divEn[0]);
            if (sec == -1) System.out.println("Comando invalido1");
            else if (sec > 20 && sec <29) {
                //switch para saber que accion hacer
                switch (sec) {
                    case 21:
                        //obtenemos la tirada de los dados
                        int casillasMover = menu.lanzarDados();

                        //vemos que jugador tiene el turno y lo sacamos
                        Avatar jugador = menu.getAvatares().get(menu.getTurno());

                        //invocamos a la funcion que mueve el avatar para
                        jugador.moverAvatar(menu.getTablero().getPosiciones(), casillasMover);
                        break;

                    case 22:
                        //encontramos casilla de salida
                        Casilla ini = menu.getTablero().encontrar_casilla("salida");
                        //utilizamos los otros Strings de divEn[i] para los parametros
                        Jugador j = Jugador.newJugador(divEn[1],divEn[2],ini,menu.getAvatares(),menu.getJugadores());

                        break;

                    case 23:
                        //ponemos control de impresion a false para que no imprima
                        ctrl=false;
                        //imprimimos cabecera de impresion
                        System.out.println("---------------JUGADOR QUE TIENE EL TURNO---------------");
                        //this.getTurno(); indice en la losta del jugador que tiene el turno
                        Jugador turn=menu.getJugadores().get(menu.getTurno());
                        System.out.println(turn+"\n");
                        break;

                    case 24:
                        //ponemos control de impresion a false para que no imprima
                        ctrl=false;
                        //imprimimos cabecera de impresion
                        System.out.println("\n------------------------JUGADORES------------------------\n");
                        //imprimimos jugador con bucle que itera el ArrayList
                        for(int i=0;i<menu.getJugadores().size();i++) {
                            System.out.println("\nJugador"+i+1+":");
                            Jugador jug = menu.getJugadores().get(i);
                            System.out.println(jug+"\n");
                        }
                        break;

                    case 28:
                        System.out.println("\n"+menu.getTablero());
                        getaway = true;
                        ctrl=false;
                        System.out.println("Terminando partida...");
                        break;

                    default:
                        System.out.println("Comando invalido2");
                }
            }
            if (ctrl) System.out.println("\n"+menu.getTablero());
        }while(!getaway);
    }
}