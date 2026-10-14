package monopoly;

import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
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
    private boolean ctrl = true; // Booleano para imprimir el tablero.
    private boolean end = false; // Booleano para acabar el juego.

    //CONSTRUCTOR
    public Menu(String[] args){
        jugadores = new ArrayList<>();
        avatares = new ArrayList<>();
        lanzamientos = 0;
        dado1 = new Dado();
        dado2 = new Dado();
        banca = new Jugador();
        tirado = false;
        solvente = true;
        this.iniciarPartida(args);
    }

    //GETTERS
    public ArrayList<Avatar> getAvatares() {return avatares;}
    public ArrayList<Jugador> getJugadores() {return jugadores;}
    public int getTurno(){return turno;}
    public Jugador getBanca(){return  banca;}
    public Tablero getTablero() {return tablero;}
    public Dado getDado1() {return dado1;}
    public Dado getDado2() {return dado2;}
    public int getLanzamientos() {return lanzamientos;}
    public boolean getSolvente() {return solvente;}
    public boolean getTirado() {return tirado;}
    public boolean getEnd() {return end;}
    public boolean getCtrl() {return ctrl;}

    //SETTERS
    public void setAvataresMenu(ArrayList<Avatar> avatares){this.avatares = avatares;}
    public void setJugadoresMenu(ArrayList<Jugador> jugador){this.jugadores = jugador;}
    public void setTurno(int turno){this.turno = turno;}
    public void setBancaMenu(Jugador banca){this.banca = banca;}
    public void setTableroMenu(Tablero tablero){this.tablero = tablero;}
    public void setDado1(Dado dado1){this.dado1 = dado1;}
    public void setDado2(Dado dado2){this.dado2 = dado2;}
    public void setLanzamientosMenu(int lanzamientos){this.lanzamientos = lanzamientos;}
    public void setSolvente(boolean solvente){this.solvente = solvente;}
    public void setTirado(boolean tirado) {this.tirado = tirado;}
    public void setEnd(boolean end){this.end=end;}
    public void setCtrl(boolean ctrl) {this.ctrl = ctrl;}


    // Metodo para inciar una partida: crea los jugadores y avatares.
    //por ahora solo pedimos casilla de Salida, si hiciesen falta mas se pediria el tablero
    private void iniciarPartida(String[] args) {
        File iniDoc = null;
        //Si se pasa el documento por argumento se abre y ya
        if(args.length>0)   iniDoc=new File(args[0]);
        //si no se pregunta si se quiere usar o no. Si sí se pide.
        else{
            System.out.println("Ningun documento introducido en linea de comandos\n quiere usar documento inicial?: y/n\n");
            String ans=new Scanner(System.in).next();
            switch (ans) {
                case "y":
                    System.out.println("Escriba el nombre del documento:\n");
                    String doc=new Scanner(System.in).next();
                    iniDoc=new File(doc);
                    break;
                case "n":
                default:
                    //temporalmente usamos la variable de control para controlar si se va a usar documento inicial o no
                    System.out.println("empezando sin documento inicial...\n");
                    break;
            }
        }

        //creamos el tablero
        Tablero tab= new Tablero(banca);
        this.setTableroMenu(tab);

        //sacamos casilla de inicio del tablero
        Casilla ini = tablero.encontrar_casilla("salida");

        //setteamos las propiedades de la banca(todas)
        for (int i = 0; i < tablero.getPosiciones().size(); i++) {
            for (int j=0;j<tablero.getPosiciones().get(i).size();j++){
                banca.anhadirPropiedad(tablero.getPosiciones().get(i).get(j));
            }
        }

        //Si hay cualquier error con el documento inicial se sale una vez inicializado todo lo necesario
        if(iniDoc != null && iniDoc.exists() && iniDoc.isFile()) {
            //Leemos documento inicial
            try (BufferedReader br = new BufferedReader(new FileReader(iniDoc))) {
                //creamos las variables auxiliares
                String lineaActual;
                while (((lineaActual = br.readLine()) != null)) {
                    //reiniciamos variable de control de impresion
                    ctrl = true;
                    //si la linea esta vacia la ignoramos
                    if (lineaActual.trim().isEmpty()) continue;

                    //separamos el comando en porciones [0]=comandi [1]=arg1 [2]=arg2
                    String[] divCom = lineaActual.split(" ");
                    String comand,aux=null;
                    if (divCom.length == 4) {comand = divCom[0] + divCom[1];aux=divCom[2]+","+divCom[3];}
                    else if (divCom.length == 3) {comand = divCom[0] + divCom[1];aux=divCom[2];}
                    else if (divCom.length == 2) {comand = divCom[0] + divCom[1];}
                    else {comand = divCom[0];}

                    //case con analizarComando que realiza las acciones pedidas
                    analizarComando(comand,aux);
                    if(end){
                        System.out.println("No se puede acabar la partida desde el documento inicial");
                        end=false;
                    }
                }
            }
            catch (IOException e) {
                System.out.println("Error al leer el archivo2");
            }
        }

        do{
            //reiniciamos variable de control de impresion
            ctrl=true;
            //imprimimos la opcion para pedir los dados y la escaneamos con sc.nextLine()
            Scanner sc = new Scanner(System.in);
            System.out.println("""
                    --------------------MENU--------------------
                    lanzar los dados: lanzar dados
                    Lanzar los dados determinados: lanzar dados <x+y>
                    Crear un jugador: crear jugador <nombre> <tipo_avatar>
                    Jugador al que le toca: jugador
                    Listar los jugadores: listar Jugadores
                    Describir una casilla: describir <casilla>
                    Describir a un jugador: describir jugador <nombre>
                    Acabar el turno: acabar turno
                    Ver el tablero: ver tablero
                    Comprar una casilla: comprar casilla
                    Listar las casillas en venta: listar enventa
                    Acabar la partida: salir
                    """);
            if(!this.getJugadores().isEmpty() && this.getJugadores().get(this.getTurno()).getEnCarcel()){
                System.out.println("Salir de la carcel: Salir Carcel\n");
            }
            String answ = sc.nextLine();

            //separamos String en partes para los comandos de mas de 1 palabra
            String[] divEn = answ.split(" ");
            String comand,aux=null;

            if (divEn.length >= 4) {comand = divEn[0] + divEn[1];aux=divEn[2]+","+divEn[3];}
            else if (divEn.length == 3) {comand = divEn[0] + divEn[1];aux=divEn[2];}
            else if (divEn.length == 2) {comand = divEn[0] + divEn[1];}
            else {comand = divEn[0];}

            //pasamos el comando por el método analizarComand
            this.analizarComando(comand,aux);
        }while(!end);
    }

    
    /*Metodo que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetros: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando, String auxiliar) {

        Casilla ini = tablero.encontrar_casilla("salida");
        //capa1: analizamos si es documento o si es comando
        if(comando.toLowerCase().endsWith(".txt")){
            //capa 2: revisamos que el documento exista
            File doc = new File(comando);
            if(doc.exists() && doc.isFile()) System.out.println("No se puede leer otro archivo");
            else System.out.println("Error al abrir el archivo, y no se puede leer otro archivo");
            return;
        }
        String cmd=comando.toLowerCase(Locale.ROOT);
        if(cmd.equals("describirjugador")){
            if(auxiliar!=null) {
                descJugador(auxiliar);
            }
            else {
                System.out.println("Debes indicar el nombre del jugador.");
                ctrl = false;
            }
        }
        else if(cmd.startsWith("describir")){
            descCasilla(cmd.substring(9));}
        else if(cmd.startsWith("edificar")){
            construirEdificio(cmd.substring(8),this.getJugadores().get(this.getTurno()));
        }
        else{
            //capa 2: hacemos un switch con todos los comandos posibles para ver que coincida con uno de ellos
            // el toLowerCase se usa por si hya mayusculas
            switch (cmd){
                case "lanzardados":
                    lanzarDados(auxiliar);
                    break;
                case "crearjugador":
                    if(auxiliar!=null) {
                        String[] partes= auxiliar.split(",");
                        if(partes.length == 2) {
                            Jugador j = Jugador.newJugador(partes[0],partes[1],ini,this.getAvatares(),this.getJugadores());
                            if(j==null){
                                ctrl=false;
                            }
                        }
                        else {
                            System.out.println("Error. Uso: crear jugador <nombre> <avatar>");
                        }
                    }
                    else {
                        System.out.println("Error. Uso: crear jugador <nombre> <avatar>");
                        ctrl = false;
                    }
                    break;
                case "jugador":
                    descJugadorTurno();
                    break;
                case "listarjugadores":
                    listarJugadores();
                    break;
                case "acabarturno":
                    acabarTurno();
                    break;
                case "salircarcel":
                    salirCarcel();
                    break;
                //hueco de describir casilla que sale antes por el return
                //hueco de describir jugador, que no puede ir aqui porque se confundiria con el 2
                case "comprarcasilla":
                    comprar();
                    break;
                case "vertablero":
                    break;
                case "listarenventa":
                    listarVenta();
                    break;
                case "salir":
                    this.setEnd(true);
                    this.setCtrl(true);
                    System.out.println("\n"+this.getTablero());
                    System.out.println("Terminando partida...");
                    break;
                default:
                    this.setCtrl(false);
                    System.out.println("comando invalido");
                    break;
            }
        }
        if(ctrl)System.out.println("\n"+this.getTablero());
    }


    /*Metodo que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: nombre del jugador
     */
    private void descJugador(String nombre) {
        ctrl=false;
        if(this.getJugadores().isEmpty()){
            System.out.println("\nNo hay jugadores creados\n");
        }
        Jugador ply = Jugador.encontrarJugador(nombre,this.getJugadores());
        if(ply != null) {
            System.out.println(ply.toString(ply.getNombre()));
        }
        else {
            System.out.println("Error. Uso: describir jugador <nombre>");
        }
    }

    private void descJugadorTurno(){
        //ponemos control de impresion a false para que no imprima
        ctrl=false;
        //imprimimos cabecera de impresion
        if(this.getJugadores().isEmpty()){
            System.out.println("---------------JUGADOR QUE TIENE EL TURNO---------------");
            //this.getTurno(); indice en la losta del jugador que tiene el turno
            Jugador turn=this.getJugadores().get(this.getTurno());
            System.out.println(turn+"\n");
        }
        else System.out.println("\nNo hay jugadores creados\n");

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
        if(nombre!=null) {
            ctrl = false;
            Casilla atc = this.getTablero().encontrar_casilla(nombre);
            if (atc != null) System.out.println(atc.infoCasilla());
            else System.out.println("No existe esa casilla");
        }
        else{
            System.out.println("no se ha inrtoducido nombre de casilla o es invalido");
        }
    }

    //Metodo que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados(String vals) {
        if(!this.getJugadores().isEmpty()){
            if (!tirado) {
                if (vals!=null) {
                    //si el valor de la tirada viene determinado, casillas mover es con la suma de los valores introducidos
                    String[] dados = vals.split("\\+");
                    //parseamos para tener los valores en entero
                    int val1 = Integer.parseInt(dados[0]), val2 = Integer.parseInt(dados[1]);
                    //setteamos los valores de los dados
                    this.getDado1().setValor(val1);
                    this.getDado2().setValor(val2);
                }
                else{
                    this.getDado1().setValor(this.getDado1().hacerTirada());
                    this.getDado2().setValor(this.getDado2().hacerTirada());
                }

                //sacamos el entero con el valor
                int casillasMover = this.getDado1().getValor() + this.getDado2().getValor();
                System.out.println("Dado1:"+this.getDado1()+"\nDado2:"+this.getDado2());

                //vemos que jugador tiene el turno y lo sacamos
                Avatar jugador = this.getAvatares().get(this.getTurno());
                Jugador jugadorActual = jugador.getJugador();

                //logica de tirada si esta en la carcel
                if(jugadorActual.getEnCarcel()){
                    System.out.println("tirada numero: "+(jugadorActual.getTiradasCarcel()+1));
                    System.out.println("tiradas para salir:3");
                    ctrl=false;
                    jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel()+1);
                    if(jugadorActual.getTiradasCarcel() ==3 || this.getDado1().getValor()==this.getDado2().getValor()){
                        System.out.println(jugadorActual.getNombre() +"ha salido de la carcel!!");
                        jugadorActual.setEnCarcel(false);

                        jugadorActual.setTiradasCarcel(0);
                        ctrl=true;
                    }
                }

                if(!jugadorActual.getEnCarcel()){
                    //invocamos a la funcion que mueve el avatar para
                    jugador.moverAvatar(this.getTablero().getPosiciones(), casillasMover);

                    //Evaluamos la casilla de destino (alquileres, impuestos, parking, carcel, etc.)
                    this.evaluarCasillaDestino(jugadorActual, jugador.getLugar(), casillasMover);
                }
            }
            else{
                System.out.println("ya se ha tirado en este turno");
                ctrl = false;
            }
        }
        else{
            System.out.println("\nNo hay jugadores creados\n");
        }
        tirado=true;
    }

    /*Metodo que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar() {
        if(this.getJugadores().isEmpty()){System.out.println("\nNo hay jugadores creados\n");}
        ctrl=false;
        Casilla c=this.getAvatares().get(this.getTurno()).getLugar();
        c.comprarCasilla(this.getJugadores().get(this.getTurno()),this.getBanca());
    }

    //Metodo que ejecuta todas las acciones relacionadas con el comando 'salir carcel'.
    private void salirCarcel() {
        if(this.getJugadores().isEmpty()){
            System.out.println("\nNo hay jugadores creados\n");
        }
        Jugador jug=this.getJugadores().get(this.getTurno());
        ctrl=false;
        if(!jug.getEnCarcel()){
            System.out.println("Comando invalido");
        }
        if(!jug.sumarFortuna((-1)*Valor.COSTE_SALIR_CARCEL)){
            setSolvente(false);
        }
        this.getBanca().sumarFortuna(Valor.COSTE_SALIR_CARCEL);
        jug.setTiradasCarcel(0);
        jug.setEnCarcel(false);

        System.out.println(jug.getNombre()+" ha pagado 500000 para salir de la carcel.\nPuede lanzar los dados\n");
    }

    // Metodo que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
        System.out.println("\n----------CASILLAS EN VENTA----------\n");
        for(int i = 0; i < getBanca().getPropiedades().size(); i++){
            Casilla cas = getBanca().getPropiedades().get(i);
            String tipo = cas.getTipo().trim().toLowerCase(Locale.ROOT);

            // Solo imprimimos si es Solar, Transporte o Servicio
            if(tipo.equals("solar") || tipo.equals("transporte") || tipo.equals("servicio")){
                // Imprimimos la información de la casilla y un salto de línea para separarlas
                System.out.println(cas.casEnVenta(tipo) + "\n");
            }
        }
        ctrl = false;
    }

    // Metodo que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        //ponemos control de impresion a false para que no imprima
        ctrl=false;
        if(!this.getJugadores().isEmpty()){
            //imprimimos cabecera de impresion
            System.out.println("\n------------------------JUGADORES------------------------\n");
            //imprimimos jugador con bucle que itera el ArrayList
            for(int i=0;i<this.getJugadores().size();i++) {
                System.out.println("\nJugador"+(i+1)+":");
                Jugador jug = this.getJugadores().get(i);
                System.out.println(jug+"\n");
            }
        }
        else{
            System.out.println("\nNo hay jugadores creados\n");
        }
    }

    // Metodo que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
    }

    // Metodo que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        if(this.getJugadores().isEmpty()){
            if(!this.getTirado()) {
                if (this.getTurno() == this.getAvatares().size() - 1) {
                    this.turno = 0;
                } else {
                    this.turno = this.getTurno() + 1;
                }
                System.out.println("Jugador que tiene el turno:" + this.getJugadores().get(this.getTurno()).getNombre() + "\n");
                ctrl = false;
                tirado=false;
            }
            else{
                System.out.println("no se ha tirado aun, no se puede acabar el turno");
            }
        }
        else{
            System.out.println("\nNo hay jugadores creados\n");
        }
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
                    if(!actual.sumarFortuna((-1) * alquiler)){
                        setSolvente(false);
                        break;
                    }
                    actual.sumarGastos(alquiler);

                    // Le sumamos el dinero al dueño de la casilla
                    duenhoSolar.sumarFortuna(alquiler);

                    System.out.printf("Se han pagado %.0f€ de alquiler a %s.\n", alquiler, destino.getDuenho().getNombre());
                }
                break;

            case "servicio":
                Jugador duenhoServicio = destino.getDuenho();

                if (duenhoServicio != null && !duenhoServicio.equals(this.banca) && !duenhoServicio.equals(actual)) {
                    float alquiler = 4.0f * sumaDados * Valor.FACTOR_SERVICIO;

                    if(!actual.sumarFortuna(-alquiler)){
                        setSolvente(false);
                        break;
                    }
                    actual.sumarGastos(alquiler);

                    duenhoServicio.sumarFortuna(alquiler);
                    System.out.printf("Se han pagado %.0f€ de servicio a %s.\n", alquiler, duenhoServicio.getNombre());
                }
                break;

            case "transporte":
                Jugador duenhoTransporte = destino.getDuenho();

                if (duenhoTransporte != null && !duenhoTransporte.equals(this.banca) && !duenhoTransporte.equals(actual)) {
                    float alquiler = destino.getImpuesto();

                    if(!actual.sumarFortuna((-1)*alquiler)){
                        setSolvente(false);
                        break;
                    }
                    actual.sumarGastos(alquiler);

                    duenhoTransporte.sumarFortuna(alquiler);
                    System.out.printf("Se han pagado %.0f€ de transporte a %s.\n", alquiler, duenhoTransporte.getNombre());
                }
                break;
            //para tener un sitio donde almacenar los impuestos cree el boteParking como atributo
            case "impuesto":

                if(!actual.sumarFortuna((-1)*Valor.IMPUESTO)){
                    setSolvente(false);
                    break;
                }
                // Sumamos al bote usando el getter y setter
                // Buscamos la casilla de parking en el tablero y le sumamos el valor
                Casilla parking = this.getTablero().encontrar_casilla("parking");
                if (parking != null) {
                    parking.sumarValor(Valor.IMPUESTO);
                }
                System.out.printf("El jugador %s paga %.0f€ de impuestos que se depositan en el Parking.\n", actual.getNombre(),Valor.IMPUESTO);
                break;

            case "parking":
                if (destino.getValor() > 0) {
                    actual.sumarFortuna(destino.getValor());
                    System.out.printf("El jugador %s recibe %.0f€ del bote del Parking.\n",
                            actual.getNombre(), destino.getValor());

                    // Reiniciamos el bote a 0 usando el setter
                    destino.sumarValor((-1)*destino.getValor());
                } else {
                    System.out.println("El Parking no tiene bote acumulado.");
                }
                break;

            case "ircarcel":
                Jugador jugador = this.getJugadores().get(this.getTurno());
                jugador.encarcelar(this.getTablero());

                System.out.println("El avatar se ha movido directamente a la casilla de Cárcel.");
                break;

            case "suerte":
            case "caja":
                // No realiza acción en esta primera entrega
                break;
        }
    }

    // Funcion para construir un edificio
    private void construirEdificio(String tipoEdificio, Jugador jugadorActual) {
        // Obtener la casilla donde está ubicado el avatar del jugador
        Casilla casillaActual = jugadorActual.getAvatar().getLugar();

        // Validar que la casilla sea de tipo Solar
        if (!casillaActual.getTipo().toLowerCase().equals("solar")) {
            System.out.println("No se puede edificar en una casilla de tipo " + casillaActual.getTipo());
            return;
        }

        // Validar que el solar pertenezca al jugador actual
        if (casillaActual.getDuenho() == null || !casillaActual.getDuenho().equals(jugadorActual)) {
            System.out.println("No puedes edificar en " + casillaActual.getNombre() + " porque no eres el propietario.");
            return;
        }

        // Validar las reglas de edificacion
        if (!casillaActual.puedoEdificar(tipoEdificio)) {
            System.out.println("No se cumplen los requisitos para edificar un/a " + tipoEdificio + " en " + casillaActual.getNombre() + ".");
            return;
        }

        // Determinar el precio del edificio según su tipo
        float precioEdificio = 0;
        switch (tipoEdificio.toLowerCase()) {
            case "casa":
                precioEdificio = casillaActual.getPrecioCasa();
                break;
            case "hotel":
                precioEdificio = casillaActual.getPrecioHotel();
                break;
            case "piscina":
                precioEdificio = casillaActual.getPrecioPiscina();
                break;
            case "pista":
                precioEdificio = casillaActual.getPrecioPista();
                break;
            default:
                System.out.println("Tipo de edificio no válido.");
                return;
        }

        // Actualizar fortuna del jugador
        if(!jugadorActual.sumarFortuna((-1)*precioEdificio)){
            System.out.println("La fortuna de " + jugadorActual.getNombre() +
                    " no es suficiente para edificar un/a " + tipoEdificio +
                    " en la casilla " + casillaActual.getNombre() + ".");
            return;
        }

        // Efectuar la edificación
        casillaActual.anhadirEdificio(tipoEdificio);

        // Actualizar gastos del jugador
        jugadorActual.sumarGastos(precioEdificio);

        // Mensaje
        System.out.println("Se ha edificado un/a " + tipoEdificio + " en " + casillaActual.getNombre() +
                ". La fortuna de " + jugadorActual.getNombre() +
                " se reduce en " + precioEdificio + "€.");
    }

}
