package partida;

import monopoly.*;

import java.util.ArrayList;
import java.util.Locale;


public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.

    //Constructor vacío
    public Avatar() {
        this.id="@";
        this.tipo="";
        this.jugador=null;
        this.lugar=null;
    }

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        this.setTipo(tipo);
        this.jugador = jugador;
        this.lugar = lugar;
        generarId(avCreados);
        lugar.anhadirAvatar(this);
        avCreados.add(this); // Metemos el nuevo avatar en la lista de avatares creados.
    }

    // GETTERS
    public String getId() {
        return id;
    }
    public String getTipo() {
        return tipo;
    }
    public Jugador getJugador() {
        return jugador;
    }
    public Casilla getLugar() {
        return lugar;
    }

    // SETTERS
    public void setId(String i) {
        this.id = i;
    }
    public boolean setTipo(String t) {
        String lwcs=t.toLowerCase(Locale.ROOT);
        switch (lwcs) {
            case "coche": this.tipo = "Coche"; return true;
            case "esfinge": this.tipo = "Esfinge"; return true;
            case "sombrero": this.tipo = "Sombrero"; return true;
            case "pelota":this.tipo = "Pelota"; return true;
            default: System.out.println("Avatar no posible, elija otro"); return false;
        }
    }
    public void setJugador(Jugador j) {
        this.jugador = j;
    }
    public void setLugar(Casilla l) {
        this.lugar = l;
    }

    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Metodo que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siempre es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        if (this.lugar == null) return;

        // Nos quitamos de la casilla actual
        this.lugar.eliminarAvatar(this);

        // Calculamos la nueva posición (1 a 40)
        int nuevaPosicion = this.lugar.getPosicion() + valorTirada;
        if (nuevaPosicion > 40) {
            nuevaPosicion = nuevaPosicion - 40;
        }

        // Buscamos la casilla destino
        for (int i = 0; i < casillas.size(); i++) {
            for (int j = 0; j < casillas.get(i).size(); j++) {
                Casilla c = casillas.get(i).get(j);
                if (c.getPosicion() == nuevaPosicion) {
                    this.lugar = c;
                    this.lugar.anhadirAvatar(this);
                    return;
                }
            }
        }
    }

    /*Metodo que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letra mayúscula. Parámetros:
    * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {
        String ID;
        do {
            char letra = (char) ('A' + (int) (Math.random() * 26));
            ID = String.valueOf(letra);
        } while (existeId(ID, avCreados));

        this.id = ID;
    }

    // Metodo para comprobar si el ID existe dentro del ArrayList de los avatares creados.
    private boolean existeId(String id, ArrayList<Avatar> avCreados) {
        if (avCreados != null) {
            for (int i = 0; i < avCreados.size(); i++) {
                if (id.equals(avCreados.get(i).getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    //metodo que comprueba si el tipoId introducido es valido. Se comprobará de nuevo en el setter por seguridad.
    public static boolean esTipoValido(String tId){
        String tipoId=tId.toLowerCase(Locale.ROOT);
        return tipoId.equals("coche") || tipoId.equals("esfinge") || tipoId.equals("sombrero") || tipoId.equals("pelota");
    }

    @Override
    public String toString() {
        return this.getTipo();
    }
}
