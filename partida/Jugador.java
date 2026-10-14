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
    private ArrayList<Casilla> propiedades;//Propiedades que posee el jugador.

    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        this.nombre = "Banca";
        this.fortuna = Valor.FORTUNA_BANCA; // Se puede hacer porque FORTUNA_BANCA es público.
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<>();
        this.avatar = new Avatar();
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    private Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
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

    //Creamos un metodo que llame al constructor para poder tener control sobre los jugadores, ya que el constructor
    //esta obligado a no fallar
    public static Jugador newJugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados,ArrayList<Jugador> jugadores){
        if(avCreados.size()>=Valor.MAX_JUGADORES){
            System.out.println("\nmaximo de jugadores alcanzado\n");
            return null;
        }
        if(!Avatar.esTipoValido(tipoAvatar)){
            System.out.println("\ntipo de avatar invalido\n");
            return null;
        }
        Jugador j=new Jugador(nombre,tipoAvatar,inicio,avCreados);
        jugadores.add(j);
        return j;
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
    public void setPropiedades(ArrayList<Casilla> p) {this.propiedades = p;}

    //Otros métodos:
    //Metodo para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        propiedades.add(casilla);
    }

    //Metodo para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        propiedades.remove(casilla);
    }

    //Metodo que comprueba si un jugador es solvente a la hora de realizar cambios en su fortuna
    private boolean esSolvente(float precio){
        if(this.getFortuna()<((-1)*precio)){
            System.out.println("Fondos insuficientes");
            return false;
        }
        return true;
    }

    //Metodo para añadir o quitar fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public boolean sumarFortuna(float valor) {
        if (valor<0){
            if(!this.esSolvente(valor)){
                return false;
            }
        }
        fortuna+=valor;
        return true;
    }

    //Metodo para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        gastos+=Math.abs(valor);
    }

    /*Metodo para establecer al jugador en la cárcel.
    * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(Tablero tab) {
        this.enCarcel = true;
        this.tiradasCarcel=0;

        //obtenemos la posicion de la carcel
        Casilla prison = tab.encontrar_casilla("Carcel");

        //calcular cual deberia ser el valor de la tirada para caer en la carcel
        int actual = this.avatar.getLugar().getPosicion();

        int distancia;
        // Calculamos la nueva posición (1 a 40)
        if(actual>prison.getPosicion()){
            distancia = 40+(prison.getPosicion()-actual);
        }
        else{
            distancia = prison.getPosicion()-actual;
        }

        this.avatar.moverAvatar(tab.getPosiciones(), distancia);
    }

    public static Jugador encontrarJugador(String nombre,ArrayList<Jugador> jugadores){
        for(int j=0;j<jugadores.size();j++){
            if(jugadores.get(j).getNombre().equals(nombre)){
                return jugadores.get(j);
            }
        }
        System.out.println("Jugador no encontrado");
        return null;
    }

    // Es el toString de listar jugadores y describir jugador turno
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Nombre del jugador: ").append(this.getNombre())
                .append("\nAvatar del jugador: ").append(this.getAvatar())
                .append("\nFortuna del jugador: ").append(String.format(java.util.Locale.of("es", "ES"), "%,.0f", this.getFortuna()))
                .append("\nGastos totales: ").append(String.format(java.util.Locale.of("es", "ES"), "%,.0f", this.getGastos()))
                .append("\nEsta encarcelado?: ").append(this.getEnCarcel())
                .append("\nVueltas al tablero: ").append(this.getVueltas())
                .append("\nLista de propiedades: [");

        // Bucle para imprimir propiedades con StringBuilder
        for (int i = 0; i < getPropiedades().size(); i++) {
            sb.append(this.getPropiedades().get(i).getNombre().trim());
            if (i < getPropiedades().size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");


        sb.append("\nLista de edificios: [");
        boolean primerEdificio = true;

        // Recorremos la lista de casillas (propiedades)
        for (int i = 0; i < getPropiedades().size(); i++) {
            Casilla c = getPropiedades().get(i);

            // Comprobamos si la casilla actual tiene edificios
            if (c.getEdificios() != null && !c.getEdificios().isEmpty()) {
                // Recorremos la lista de edificios de esta casilla
                for (int j = 0; j < c.getEdificios().size(); j++) {
                    if (!primerEdificio) {
                        sb.append(", ");
                    }
                    sb.append(c.getEdificios().get(j)); // Añade el edificio
                    primerEdificio = false;
                }
            }
        }
        sb.append("]");


        return sb.toString();
    }

    // Es el toString de describir jugador
    public String toString(String nombre) {
        StringBuilder sb = new StringBuilder();

        sb.append("Nombre: ").append(nombre)
                .append("\nAvatar: ").append(this.getAvatar().getId())
                .append("\nFortuna: ").append(String.format(java.util.Locale.of("es", "ES"), "%,.0f", this.getFortuna()))
                .append("\nPropiedades: [");

        // Bucle para imprimir propiedades
        for (int i = 0; i < getPropiedades().size(); i++) {
            sb.append(this.getPropiedades().get(i).getNombre().trim());
            if (i < getPropiedades().size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]\nhipotecadas: [");

        // Propiedades hipotecadas
        boolean primerHipotecada = true;
        for (int i = 0; i < getPropiedades().size(); i++) {
            if (this.getPropiedades().get(i).getHipotecada()) {
                if (!primerHipotecada) {
                    sb.append(", ");
                }
                sb.append(this.getPropiedades().get(i).getNombre().trim());
                primerHipotecada = false;
            }
        }
        sb.append("]\n");

        // Listar edificios del jugador
        sb.append("edificios: [");
        boolean primerEdificio = true;

        for (int i = 0; i < this.getPropiedades().size(); i++) {
            Casilla c = this.getPropiedades().get(i);
            if (c.getEdificios() != null && !c.getEdificios().isEmpty()) {
                for (int j = 0; j < c.getEdificios().size(); j++) {
                    if (!primerEdificio) {
                        sb.append(", ");
                    }
                    sb.append(c.getEdificios().get(j));
                    primerEdificio = false;
                }
            }
        }
        sb.append("]\n\n");

        return sb.toString();
    }

}
