package monopoly;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.FileReader;


import partida.*;

public class Menu {

    //Atributos
    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.


    //GETTERS
    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;

    }

    public int getTurno(){
        return turno;
    }

    public Jugador getBanca(){
        return  banca;
    }

    public Tablero getTablero() {
        return tablero;
    }

    //SETTERS
    public void setAvataresMenu(ArrayList<Avatar> avatares){
        this.avatares = avatares;
    }

    public void setJugadoresMenu(ArrayList<Jugador> jugador){
        this.jugadores = jugador;
    }

    public void setTurno(int turno){
        this.turno = turno;
    }

    public void setBancaMenu(Jugador banca){
        this.banca = banca;
    }

    public void setTableroMenu(Tablero tablero){
        this.tablero = tablero;
    }

    //Constructor
    public Menu(){
        jugadores = new ArrayList<>();
        avatares = new ArrayList<>();
    }

    // Metodo para inciar una partida: crea los jugadores y avatares.
    //por ahora solo pedimos casilla de Salida, si hiciesen falta mas se pediria el tablero
    public void iniciarPartida(File iniDoc) {
        //creamos la banca
        Jugador banca = new Jugador();
        this.setBancaMenu(banca);

        //creamos variable de control para gestionar en que iteraciones se imprime el tablero: si se va a mover un jugador
        //si hace falta, pero si solo se pide que se imprima algo no hace falta reimprimirlo
        boolean ctrl=true;

        //creamos el tablero
        Tablero tab= new Tablero(banca);
        this.setTableroMenu(tab);

        //sacamos casilla de inicio del tablero
        Casilla ini=tablero.encontrar_casilla("salida");

        //setteamos las propiedades de la banca(todas)
        for (int i = 0; i < tablero.getPosiciones().size(); i++) {
            for (int j=0;j<tablero.getPosiciones().get(i).size();j++){
                banca.anhadirPropiedad(tablero.getPosiciones().get(i).get(j));
            }
        }

        //Si hay cualquier error con el documento inicial se sale una vez inicializado todo lo necesario
        if(iniDoc == null || !iniDoc.exists() || !iniDoc.isFile())  return;

        //Leemos documento inicial
        try (Stream<String> lineas = Files.lines(iniDoc.toPath())) {

            //creamos las variables auxiliares
            String lineaActual;
            BufferedReader br = new BufferedReader(new FileReader(iniDoc));

            while(((lineaActual = br.readLine())!=null)) {
                //reiniciamos variable de control de impresion
                ctrl=true;

                //si la linea etsa vacia la ignoramos
                if (lineaActual.trim().isEmpty()) continue;

                //separamos el comando en porciones [0]=comandi [1]=arg1 [2]=arg2
                String[] divCom = lineaActual.split(" ");

                //case con analizarComando que raaliza las acciones pedidas
                switch (analizarComando(divCom[0])) {
                    //0 y -1 son errores
                    case 0:
                    case -1:
                        System.out.println("Error al leer el archivo1");
                        return;
                    //1 se ignora, no puede mandarte a outro nuevo archivo
                    case 1:
                        break;
                    //21 se lanzan los dados y mueve al jugador que tiene el turno
                    case 21:
                        //obtenemos la tirada de los dados
                        int casillasMover = this.lanzarDados();

                        //vemos que jugador tiene el turno y lo sacamos
                        Avatar jugador = this.getAvatares().get(this.getTurno());

                        //invocamos a la funcion que mueve el avatar para
                        jugador.moverAvatar(this.getTablero().getPosiciones(), casillasMover);
                        break;
                    //22 creamos nuevo jugador
                    case 22:
                        //public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados)
                        Jugador j = Jugador.newJugador(divCom[1],divCom[2],ini,this.getAvatares(),this.getJugadores());

                        break;
                    //23 imprimir jugador que tiene el turno
                    case 23:
                        //ponemos control de impresion a false para que no imprima
                        ctrl=false;
                        //imprimimos cabecera de impresion
                        System.out.println("\n---------------JUGADOR QUE TIENE EL TURNO---------------");
                        //this.getTurno(); indice en la losta del jugador que tiene el turno
                        Jugador turn=this.getJugadores().get(this.getTurno());
                        System.out.println(turn+"\n\n");
                        break;
                    //24 listamos los jugadores
                    case 24:
                        //ponemos control de impresion a false para que no imprima
                        ctrl=false;
                        //imprimimos cabecera de impresion
                        System.out.println("\n------------------------JUGADORES------------------------\n");
                        //imprimimos jugador con bucle que itera el ArrayList
                        for(int i=0;i<this.getJugadores().size();i++) {
                            System.out.println("\nJugador"+i+1+":");
                            Jugador jug = this.getJugadores().get(i);
                            System.out.println(jug+"\n");
                        }

                        break;
                    default:
                        break;
                }
                if (ctrl) System.out.println("\n"+this.getTablero());
            }
        } catch (IOException e) {System.out.println("Error al leer el archivo2");}

    }
    
    /*Metodo que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetros: cadena de caracteres (el comando).
    */
    //devuelve:
    // -1 si ha habido un error
    // 1 si la entrada es un documento
    // 2x si la entrada es un comando:
    //     21 si es lanzar dados
    //     22 si es crear jugador
    //     23 si es decir que jugador tiene el turno
    //     24 si es listar los jugadores
    //
    //     29 si se pide salir y acabar la partida
    public int analizarComando(String comando) {
        //capa1: analizamos si es documento o si es comando
        if(comando.toLowerCase().endsWith(".txt")){
            //capa 2: revisamos que el documento exista
            File doc = new File(comando);
            if(doc.exists() && doc.isFile()) return 1;
            else return -1;
        }
        else{
            //capa 2: hacemos un switch con todos los comandos posibles para ver que coincida con uno de ellos
            // el toLowerCase se usa por si hya mayusculas
            switch (comando.toLowerCase()){
                case "lanzardados": return 21;
                case "crearjugador": return 22;
                case "jugadorturno": return 23;
                case "listarjugadores": return 24;

                case "salir": return 28;
            }
        }
        return -1;
    }


    /*Metodo que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {
    }

    /*Metodo que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    */
    private void descAvatar(String ID) {
    }

    /* Metodo que realiza las acciones asociadas al comando 'describir nombre_casilla'.
    * Parámetros: nombre de la casilla a describir.
    */
    private void descCasilla(String nombre) {
    }

    //Metodo que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    //cambiado de private a public. Enterarse si se puede hacer
    public int lanzarDados() {
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        dado1.hacerTirada();
        dado2.hacerTirada();
        tirado = true;
        return dado1.getValor() + dado2.getValor();
    }

    /*Metodo que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
    }

    //Metodo que ejecuta todas las acciones relacionadas con el comando 'salir carcel'.
    private void salirCarcel() {
    }

    // Metodo que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
    }

    // Metodo que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
    }

    // Metodo que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
    }

    // Metodo que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
    }

}
