package Casillas;

import monopoly.Valor;
import partida.*;
import java.util.ArrayList;


public abstract class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.

    //Atributo para las estadisticas de la partida
    private float RentabilidadAcumulada;
    private int vecesVisitada = 0;

    //Constructores:
    public Casilla() {
    }//Parámetros vacíos

    //xuntamos ambos pq son o mismo
    /*Constructor para casillas tipo Solar, Servicios o Transporte:
     * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
     * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    //usado para impusto en especial
    public Casilla(String nombre, int posicion, float valor, Jugador duenho) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.duenho = duenho;
        this.impuesto=valor;
        this.avatares = new ArrayList<>();
    }

    /*Constructor utilizado para crear las otras casillas (Suerte, Caja de comunidad y Especiales):
     * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición en el tablero y dueño.
     */
    //usado para carcel+irCarcel en especial
    public Casilla(String nombre, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.duenho = duenho;
        this.avatares = new ArrayList<>();
    }

    // GETTERS
    public String getNombre() {return nombre;}
    public int getPosicion() {return posicion;}
    public Jugador getDuenho() {return duenho;}
    public float getImpuesto() {return impuesto;}
    public ArrayList<Avatar> getAvatares() {return avatares;}
    public float getRentabilidadAcumulada(){return RentabilidadAcumulada;}
    public int getVecesVisitada() {return vecesVisitada;}


    // SETTERS
    public void setNombre(String n) {this.nombre = n;}
    public void setPosicion(int p) {this.posicion = p;}
    public void setDuenho(Jugador d) {this.duenho = d;}
    public void setImpuesto(float i) {this.impuesto = i;}
    public void setAvatares(ArrayList<Avatar> a) {this.avatares = a;}
    public void setRentabilidadAcumulada(float r){this.RentabilidadAcumulada = r;}
    public void setVecesVisitada(int v) {this.vecesVisitada = v;}

    // Metodo para sumar la rentabilidad acumulada
    public void sumarRentabilidadAcumulada(float cantidad){this.RentabilidadAcumulada += cantidad;}

    // Metodo para sumar las veces que se ha visitado una casilla
    public void sumarVecesVisitada() {setVecesVisitada(getVecesVisitada()+1);}

    //Metodo utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        if (this.avatares == null) {this.avatares = new ArrayList<>();}
        this.avatares.add(av);
    }
    //Metodo utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        if (this.avatares != null) {this.avatares.remove(av);}
    }

    // Método por defecto para cualquier casilla sin grupo
    public String obtenerCodigoColor() {
        return Valor.RESET;
    }

    /*Metodo para evaluar qué hacer en una casilla concreta. Parámetros:
    * - Jugador cuyo avatar está en esa casilla.
    * - La banca (para ciertas comprobaciones).
    * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
    * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
    * en caso de no cumplirlas.
    public boolean evaluarCasilla(Jugador actual, Casilla destino, int sumaDados) {
    }
     */

    /*Metodo para mostrar información sobre una casilla.
     * Devuelve una cadena con información específica de cada tipo de casilla.*/
    /*Está pensado para devolver la información general e identificativa
     * de cualquier casilla (nombre, tipo, propietario, valor actual, etc.).*/
    // abstract (el compilador te obliga a ponerlo en TODAS las clases finales)
    public abstract String infoCasilla();

    @Override
    public String toString() { // Para imprimir los nombres e ID de las casillas
        StringBuilder id = new StringBuilder();
        if (!avatares.isEmpty()) {
            id = new StringBuilder("&");
            for (Avatar avatare : avatares) {
                id.append(avatare.getId());}
        }
        //quitamos los espacios entre el fin del nombre y la casilla
        String CasillaLimpia = this.nombre.trim();

        //Calculamos cuantos espacios hay que poner para rellenar una casilla de 14 de largo
        int numEspacios = 14 - CasillaLimpia.length() - id.length();

        //creamos el String que tenga los espacios necesarios
        StringBuilder espacios = new StringBuilder();
        espacios.repeat(" ", Math.max(0, numEspacios));

        //Unimos todo
        String celda = CasillaLimpia + espacios + id;

        // Resetea el color ANSI al final para no teñir las siguientes casillas ni los bordes.
        return obtenerCodigoColor() + celda + Valor.RESET;
    }
}