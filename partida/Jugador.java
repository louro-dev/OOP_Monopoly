package partida;

import java.util.ArrayList;
import monopoly.*;


public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private ArrayList<Casilla> propiedades; //Propiedades que posee el jugador.

    //Constructor vacío. Se usará para crear la banca.
    //Rodrigo-23/09
    public Jugador() {
        //setteamos valores con os parametros pasados
        this.nombre="banca";
        this.fortuna = Valor.FORTUNA_BANCA;
        //inicializamos variables que ni se modificaran por ser la banca
        this.gastos = 0;
        this.enCarcel=false;
        this.tiradasCarcel=0;
        this.vueltas=0;
        this.propiedades = null;
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    //Rodrigo-23/09
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        //setteamos valores con os parametros pasados
        this.nombre=nombre;
        this.fortuna = Valor.FORTUNA_INICIAL;
        this.avatar = new Avatar(tipoAvatar,this,inicio,avCreados);
        //inicializamos variables que van a guardar valores
        this.gastos = 0;
        this.enCarcel=false;
        this.tiradasCarcel=0;
        this.vueltas=0;
        this.propiedades = new ArrayList<>();
    }

    //GETTERS
    public String getNombre(){
        return this.nombre;
    }
    //Metodo para sacar el avatar de los jugadores, para poder acceder al Id
    public Avatar getAvatar() {
        return this.avatar;
    }
    public float getFortuna(){
        return this.fortuna;
    }
    public float getGastos(){
        return this.gastos;
    }
    public boolean isEnCarcel(){
        return this.enCarcel;
    }
    public int getTiradasCarcel(){
        return this.tiradasCarcel;
    }
    public int getVueltas(){
        return this.vueltas;
    }


    //Otros métodos:
    //Metodo para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    //Rodrigo-23/09
    public void anhadirPropiedad(Casilla casilla) {
        propiedades.add(casilla);
    }

    //Metodo para eliminar una propiedad del arraylist de propiedades de jugador.
    //Rodrigo-23/09
    public void eliminarPropiedad(Casilla casilla) {
        propiedades.remove(casilla);
    }

    //Metodo para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    //Rodrigo-23/09
    public void sumarFortuna(float valor) {
        fortuna+=valor;
    }

    //Metodo para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        gastos+=valor;
    }

    /*Metodo para establecer al jugador en la cárcel.
    * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    //Rodrigo-23/09 SIN ACABAR
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
        this.enCarcel=true;
        //mover posicion a la casilla de carcel etc etc
    }

}
