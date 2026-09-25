package partida;

import monopoly.*;

import java.util.ArrayList;


public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.


    //GETTERS
    public String getId(){
        return this.id;
    }

    public String getTipo(){
        return this.tipo;
    }

    public Jugador getJugador(){
        return this.jugador;
    }
    public Casilla getCasilla(){
        return this.lugar;
    }

    //Constructor vacío
    public Avatar() {
    }

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    //Rodrigo-23-09
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        //setteamos todos los atributos con los parametros dados
        this.tipo= tipo;
        this.jugador=jugador;
        this.lugar = lugar;
        this.id = (String) generarId(avCreados);    // creamos nuevo id para el avatar, ej: plyr1

        avCreados.add(this); //metemos el nuevo avatar en la lista de avatares creados
    }

    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Metodo que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
    }

    /*Metodo que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letra mayúscula. Parámetros:
    * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    //Rodrigo-25/09
    private String generarId(ArrayList<Avatar> avCreados) {
        String ID;
        do {
            ID =  "A" + (int) (Math.random() * 26);
        } while(avCreados.contains(ID));
        return ID;
    }
}
