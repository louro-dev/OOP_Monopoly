package monopoly;

import partida.*;
import java.util.ArrayList;


public class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Suerte y Impuesto).
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private float hipoteca; //Valor otorgado por hipotecar una casilla
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.

    //Atributos adicionales para solares: precios y alquileres de edificaciones (Apéndice I).
    //No obligatorios para la lógica de esta entrega, pero es obligatorio definir estas cantidades.
    private float precioCasa;
    private float precioHotel;
    private float precioPiscina;
    private float precioPista;
    private float alquilerCasa;
    private float alquilerHotel;
    private float alquilerPiscina;
    private float alquilerPista;

    //Constructores:
    public Casilla() {
    }//Parámetros vacíos

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
    * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.valor = valor;
        this.duenho = duenho;
        this.avatares = new ArrayList<>();
    }

    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
    * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    public Casilla(String nombre, int posicion, float impuesto, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = "Impuesto";
        this.posicion = posicion;
        this.impuesto = impuesto;
        this.duenho = duenho;
        this.avatares = new ArrayList<>();
    }

    /*Constructor utilizado para crear las otras casillas (Suerte, Caja de comunidad y Especiales):
    * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición en el tablero y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
        this.avatares = new ArrayList<>();
    }

    // GETTERS
    public String getNombre() {
        return nombre;
    }
    public String getTipo() {
        return tipo;
    }
    public float getValor() {
        return valor;
    }
    public int getPosicion() {
        return posicion;
    }
    public Jugador getDuenho() {
        return duenho;
    }
    public Grupo getGrupo() {
        return grupo;
    }
    public float getImpuesto() {
        return impuesto;
    }
    public float getHipoteca() {
        return hipoteca;
    }
    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }
    public float getPrecioCasa() {
        return precioCasa;
    }
    public float getPrecioHotel() {
        return precioHotel;
    }
    public float getPrecioPiscina() {
        return precioPiscina;
    }
    public float getPrecioPista() {
        return precioPista;
    }
    public float getAlquilerCasa() {
        return alquilerCasa;
    }
    public float getAlquilerHotel() {
        return alquilerHotel;
    }
    public float getAlquilerPiscina() {
        return alquilerPiscina;
    }
    public float getAlquilerPista() {
        return alquilerPista;
    }

    // SETTERS
    public void setNombre(String n) {
        this.nombre = n;
    }
    public void setTipo(String t) {
        this.tipo = t;
    }
    public void setValor(int v) {
        this.valor = v;
    }
    public void setPosicion(int p) {
        this.posicion = p;
    }
    public void setDuenho(Jugador d) {
        this.duenho = d;
    }
    public void setGrupo(Grupo g) {
        this.grupo = g;
    }
    public void setImpuesto(float i) {
        this.impuesto = i;
    }
    public void setHipoteca(float h) {
        this.hipoteca = h;
    }
    public void setAvatares(ArrayList<Avatar> a) {
        this.avatares = a;
    }
    public void setPrecioCasa(float p) {
        this.precioCasa = p;
    }
    public void setPrecioHotel(float p) {
        this.precioHotel = p;
    }
    public void setPrecioPiscina(float p) {
        this.precioPiscina = p;
    }
    public void setPrecioPista(float p) {
        this.precioPista = p;
    }
    public void setAlquilerCasa(float a) {
        this.alquilerCasa = a;
    }
    public void setAlquilerHotel(float a) {
        this.alquilerHotel = a;
    }
    public void setAlquilerPiscina(float a) {
        this.alquilerPiscina = a;
    }
    public void setAlquilerPista(float a) {
        this.alquilerPista = a;
    }

    //Metodo utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        if(this.avatares == null) {
            this.avatares = new ArrayList<>();
        }
        this.avatares.add(av);
    }

    //Metodo utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        if(this.avatares != null) {
            this.avatares.remove(av);
        }
    }

    /*Metodo para evaluar qué hacer en una casilla concreta. Parámetros:
    * - Jugador cuyo avatar está en esa casilla.
    * - La banca (para ciertas comprobaciones).
    * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
    * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
    * en caso de no cumplirlas.*/
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {

        //if (this is_in banca.getPrpopiedades()){
        // return true;
        if(actual.getFortuna()<this.getImpuesto()){
            return false;
        }
        return true;
    }

    /*Metodo usado para comprar una casilla determinada. Parámetros:
    * - Jugador que solicita la compra de la casilla.
    * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        solicitante.sumarGastos(valor);
        banca.sumarFortuna(valor);
        solicitante.sumarFortuna((-1)*valor);
        banca.getPropiedades().remove(this);
        solicitante.anhadirPropiedad(this);
    }

    /*Metodo para añadir valor a una casilla. Utilidad:
    * - Sumar valor a la casilla de parking.
    * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
    * Este metodo toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
        valor+=suma;
    }

    /*Metodo para mostrar información sobre una casilla.
    * Devuelve una cadena con información específica de cada tipo de casilla.*/
    /*Está pensado para devolver la información general e identificativa
    * de cualquier casilla (nombre, tipo, propietario, valor actual, etc.).*/
    public String infoCasilla() {
        return "hola";
    }

    /* Metodo para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    /*Está pensado para filtrar y formatear la información únicamente de las casillas
    * que están disponibles para comprar (o mostrar los datos financieros específicos de venta).*/
    public String casEnVenta() {
        return "Hola";
    }

    // Metodo para obtener los colores de cada grupo para luego poder imprimirlos
    public String obtenerCodigoColor() {
        // Si la casilla no pertenece a ningún grupo (por ejemplo, Salida, Cárcel, etc.)
        if (this.grupo == null || this.grupo.getColorGrupo() == null) {
            return Valor.RESET;
        }

        String color = this.grupo.getColorGrupo().toLowerCase();

        switch (color) {
            case "negro":     return Valor.BLACK;
            case "rojo":      return Valor.RED;
            case "verde":     return Valor.GREEN;
            case "amarillo":  return Valor.YELLOW;
            case "azul":      return Valor.BLUE;
            case "rosa":
            case "magenta":   return Valor.PURPLE;
            case "cian":
            case "celeste":   return Valor.CYAN;
            case "blanco":    return Valor.WHITE;
            case "marron":
            case "marrón":    return Valor.BROWN;
            case "naranja":   return Valor.ORANGE;
            default:          return Valor.RESET;
        }
    }

    @Override
    public String toString() { // Para imprimir los nombres e ID de las casillas
        String id = "";
        if(avatares.size() != 0) {
            id ="&";
            for(int i=0; i<avatares.size(); i++) {
                id+= avatares.get(i).getId();
            }
        }
        //quitamos los espacios entre el fin del nombre y la casilla
        String CasillaLimpia=this.nombre.trim();

        //Calculamos cuantos espacios hay que poner para rellenar una casilla de 14 de largo
        int numEspacios=14-CasillaLimpia.length()-id.length();

        //creamos el String que tenga los espacios necesarios
        String espacios="";
        for(int i=0; i<numEspacios; i++) {
            espacios+=" ";
        }

        //Unimos todo
        String celda=CasillaLimpia+espacios+id;

        // Resetea el color ANSI al final para no teñir las siguientes casillas ni los bordes.
        return obtenerCodigoColor() + celda + Valor.RESET;
    }
}
