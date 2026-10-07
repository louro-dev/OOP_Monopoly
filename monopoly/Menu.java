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
    private float boteParking = 0;

    //GETTERS
    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;

    }
    public float getBoteParking(){return boteParking;}

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

    public void setBoteParking(float boteParking){
        this.boteParking = boteParking;
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
                String comand;

                if(divCom.length>=2){
                    comand=divCom[0]+divCom[1];
                }
                else{
                    comand=divCom[0];
                }

                //case con analizarComando que raaliza las acciones pedidas
                switch (analizarComando(comand)) {
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
                        Jugador jugadorActual = this.getJugadores().get(this.getTurno());

                        //invocamos a la funcion que mueve el avatar para
                        jugador.moverAvatar(this.getTablero().getPosiciones(), casillasMover);

                        //Evaluamos la casilla de destino (alquileres, impuestos, parking, carcel, etc.)
                        this.evaluarCasillaDestino(jugadorActual,jugador.getLugar(), casillasMover);
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

                    case 25:
                        //
                        if(this.getTurno()==this.getAvatares().size()-1){
                            this.turno=0;
                        }
                        else{
                            this.turno=this.getTurno()+1;
                        }
                        System.out.println("Jugador que tiene el turno:"+this.getJugadores().get(this.getTurno()).getNombre()+"\n");
                        ctrl=false;
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
                case "jugador": return 23;
                case "listarjugadores": return 24;
                case "acabarturno":return 25;

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
    //metodo que evalua el tipo de casilla en el que cae el jugador y lo que ocurre en cada caso
    private void evaluarCasillaDestino(Jugador actual, Casilla destino, int sumaDados) {
        String tipo = destino.getTipo().toLowerCase();

        switch (tipo) {
            case "solar":
                Jugador duenhoSolar = destino.getDuenho();
                // Comprobamos si tiene dueño, si no es el jugador actual y si no está hipotecada
                if (duenhoSolar != null && !duenhoSolar.equals(this.banca) && !duenhoSolar.equals(actual)) {
                    float alquiler = destino.getImpuesto(); //se pone el alquiler en el apartado impuesto

                    // Si el dueño posee todo el grupo, el alquiler se duplica[cite: 3]
                    if (destino.getGrupo() != null && destino.getDuenho().getAvatar() != null && destino.getGrupo().esDuenhoGrupo(destino.getDuenho())&& destino.getGrupo().esDuenhoGrupo(duenhoSolar)) {
                        alquiler *= 2;
                    }

                    // Le restamos el dinero al jugador actual (pasando el valor en negativo)
                    actual.sumarFortuna((-1) * alquiler);
                    actual.sumarGastos(alquiler);

                    // Le sumamos el dinero al dueño de la casilla
                    duenhoSolar.sumarFortuna(alquiler);

                    System.out.printf("Se han pagado %.0f€ de alquiler a %s.\n", alquiler, destino.getDuenho().getNombre());

                }
                break;

            case "servicio":
                Jugador duenhoServicio = destino.getDuenho();

                if (duenhoServicio != null && !duenhoServicio.equals(this.banca) && !duenhoServicio.equals(actual)) {
                    float alquiler = 4.0f * sumaDados * 50000.0f;

                    actual.sumarFortuna(-alquiler);
                    actual.sumarGastos(alquiler);

                    duenhoServicio.sumarFortuna(alquiler);
                    System.out.printf("Se han pagado %.0f€ de servicio a %s.\n", alquiler, duenhoServicio.getNombre());
                }
                break;

            case "transporte":
                Jugador duenhoTransporte = destino.getDuenho();

                if (duenhoTransporte != null && !duenhoTransporte.equals(this.banca) && !duenhoTransporte.equals(actual)) {
                    float alquiler = destino.getImpuesto();

                    actual.sumarFortuna(-alquiler);
                    actual.sumarGastos(alquiler);

                    duenhoTransporte.sumarFortuna(alquiler);
                    System.out.printf("Se han pagado %.0f€ de transporte a %s.\n", alquiler, duenhoTransporte.getNombre());
                }
                break;
            //para tener un sitio donde almacenar los impuestos cree el boteParking como atributo
            case "impuesto":
                float impuesto = 2000000; // 2.000.000€ depositados en Parking[cite: 3, 4]
                actual.sumarFortuna((-1)*impuesto);
                // Sumamos al bote usando el getter y setter
                this.setBoteParking(this.getBoteParking() + impuesto);
                System.out.printf("El jugador %s paga %.0f€ de impuestos que se depositan en el Parking.\n", actual.getNombre(), impuesto);
                break;

            case "parking":
                float boteActual = this.getBoteParking();

                if (boteActual > 0) {
                    actual.sumarFortuna(boteActual);
                    System.out.printf("El jugador %s recibe %.0f€ del bote del Parking.\n",
                            actual.getNombre(), boteActual);

                    // Reiniciamos el bote a 0 usando el setter
                    this.setBoteParking(0.0f); //[cite: 4]
                } else {
                    System.out.println("El Parking no tiene bote acumulado.");
                }
                break;

            case "ircarcel":
                Avatar jugador = this.getAvatares().get(this.getTurno());
                Casilla carcel = tablero.encontrar_casilla("carcel");
                int posActual = actual.getAvatar().getLugar().getPosicion(); // Posición de 1 a 40
                int posCarcel = 11; // Posición de la Cárcel

                // Calculamos las casillas a avanzar hasta llegar a la Cárcel
                int casillasMover;
                if (posCarcel >= posActual) {
                    casillasMover = posCarcel - posActual;
                } else {
                    casillasMover = (40 - posActual) + posCarcel;
                }

                // Movemos el avatar del jugador con la estructura indicada
                jugador.moverAvatar(this.getTablero().getPosiciones(), casillasMover);

                // Marcamos al jugador como encarcelado
                actual.setEnCarcel(true);

                System.out.println("El avatar se ha movido directamente a la casilla de Cárcel.");
                break;

            case "suerte":
            case "caja":
                // No realiza acción en esta primera entrega[cite: 6]
                break;
        }
    }

}
