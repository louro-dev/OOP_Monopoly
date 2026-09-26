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
    public Jugador() {
        this.nombre = "Banca";
        this.fortuna = Valor.FORTUNA_BANCA; // Se puede hacer porque FORTUNA_BANCA es público.
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = null; // MAL, LA BANCA TIENE TODAS LAS PROPIEDADES
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        this.nombre = nombre;
        this.fortuna = Valor.FORTUNA_INICIAL; // Se puede hacer porque FORTUNA_INICIAL es público.
        this.avatar = new Avatar(tipoAvatar,this,inicio,avCreados);
        // Inicializamos variables que van a guardar valores
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<>();
    }

    // GETTERS
    public String getNombre() {
        return this.nombre;
    }
    public Avatar getAvatar() {
        return this.avatar;
    }
    public float getFortuna() {
        return this.fortuna;
    }
    public float getGastos() {
        return this.gastos;
    }
    public boolean getEnCarcel() {
        return this.enCarcel;
    }
    public int getTiradasCarcel() {
        return this.tiradasCarcel;
    }
    public int getVueltas() {
        return this.vueltas;
    }
    public ArrayList<Casilla> getPropiedades() {
        return this.propiedades;
    }

    // SETTERS
    public void setNombre(String n) {
        this.nombre = n;
    }
    public void setAvatar(Avatar a) {
        this.avatar = a;
    }
    public void setFortuna(float f) {
        this.fortuna = f;
    }
    public void setGastos(float g) {
        this.gastos = g;
    }
    public void setEnCarcel(boolean c) {
        this.enCarcel = c;
    }
    public void setTiradasCarcel(int t) {
        this.tiradasCarcel = t;
    }
    public void setVueltas(int v) {
        this.vueltas = v;
    }
    public void setPropiedades(ArrayList<Casilla> p) {
        this.propiedades = p;
    }

    //Otros métodos:
    //Metodo para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        propiedades.add(casilla);
    }

    //Metodo para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        propiedades.remove(casilla);
    }

    //Metodo para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
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
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
        enCarcel = true;
        // FALTA MOVER POSICION A LA CASILLA DE CARCEL
    }

}
