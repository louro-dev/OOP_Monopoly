package monopoly;

import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

import Casillas.*;
import partida.*;

public class Juego {

    //Atributos
    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private Menu menu; //menu de la partida
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

    // Constructor
    public Juego(String[] args){
        jugadores = new ArrayList<>();
        avatares = new ArrayList<>();
        menu = new Menu(this);
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
                if(tablero.getPosiciones().get(i).get(j) instanceof Propiedad prop)
                {
                    banca.anhadirPropiedad(prop);
                }
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
                    menu.analizarComando(comand,aux);
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
                    Lanzar los dados: lanzar dados
                    Lanzar los dados determinados: lanzar dados <x+y>
                    Crear un jugador: crear jugador <nombre_jugador> <tipo_avatar>
                    Jugador al que le toca: jugador
                    Listar los jugadores: listar Jugadores
                    Describir una casilla: describir <nombre_casilla>
                    Describir a un jugador: describir jugador <nombre_jugador>
                    Acabar el turno: acabar turno
                    Ver el tablero: ver tablero
                    Comprar una casilla: comprar casilla
                    Edificar un edificio: edificar <nombre_edificio>
                    Listar los edificios: listar edificios
                    Listar los edificios de un grupo: listar edificios <color_grupo>
                    Hipotecar una casilla: hipotecar <nombre_casilla>
                    Deshipotecar una casilla: deshipotecar <nombre_casilla>
                    Listar las casillas en venta: listar enventa
                    Vender edificios: vender <nombre_edificio> <nombre_casilla> <cantidad>
                    Mostrar estadisticas de un jugador: estadisticas <nombre_jugador>
                    Mostrar estadisticas de juego: estadisticas
                    Acabar la partida: salir
                    """);
            if(!this.getJugadores().isEmpty() && this.getJugadores().get(this.getTurno()).getEnCarcel()){
                System.out.println("Salir de la carcel: salir carcel\n");
            }
            String answ = sc.nextLine();

            //separamos String en partes para los comandos de mas de 1 palabra
            String[] divEn = answ.split(" ");
            String comand,aux=null;

            if (divEn.length >= 4) {comand = divEn[0] + divEn[1];aux=divEn[2]+","+divEn[3];}
            else if (divEn.length == 3) {comand = divEn[0] + divEn[1];aux=divEn[2];}
            else if (divEn.length == 2) {comand = divEn[0] + divEn[1];}
            else {comand = divEn[0];}

            //pasamos el comando por el metodo analizarComando
            menu.analizarComando(comand,aux);
        }while(!end);
    }

    /*Metodo que realiza las acciones asociadas al comando 'describir jugador'.
     * Parámetro: nombre del jugador
     */
    public void descJugador(String nombre) {
        setCtrl(false);
        if(getJugadores().isEmpty()){
            System.out.println("\nNo hay jugadores creados\n");
            return;
        }
        Jugador ply = Jugador.encontrarJugador(nombre,this.getJugadores());
        if(ply != null) {
            System.out.println(ply.toString(ply.getNombre()));
        }
        else {
            System.out.println("Error. Uso: describir jugador <nombre>");
        }
    }

    public void descJugadorTurno(){
        //ponemos control de impresion a false para que no imprima
        this.setCtrl(false);
        //imprimimos cabecera de impresion
        if(!this.getJugadores().isEmpty()){
            System.out.println("---------------JUGADOR QUE TIENE EL TURNO---------------");
            //this.getTurno(); indice en la losta del jugador que tiene el turno
            Jugador turn=this.getJugadores().get(this.getTurno());
            System.out.println(turn+"\n");
        }
        else System.out.println("\nNo hay jugadores creados\n");
    }

    // Metodo que realiza las acciones asociadas al comando 'listar jugadores'.
    public void listarJugadores() {
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

    private Jugador jugadorMasVueltas() {
        Jugador jugadorMasVueltas = this.getJugadores().getFirst();
        for(Jugador j: this.getJugadores()) {
            if(j.getVueltas() > jugadorMasVueltas.getVueltas()) {
                jugadorMasVueltas = j;
            }
        }
        return jugadorMasVueltas;
    }

    private Jugador jugadorEnCabeza() {
        Jugador enCabeza = null;
        float maxFortuna = -1;

        for(Jugador j: this.getJugadores()) {
            float fortunaCalculada = (float) (j.getFortuna() + j.getDineroInvertidoPropiedades());

            if (fortunaCalculada > maxFortuna) {
                maxFortuna = fortunaCalculada;
                enCabeza = j;
            }
        }
        return enCabeza;
    }

    /* Metodo que realiza las acciones asociadas al comando 'describir nombre_casilla'.
     * Parámetros: nombre de la casilla a describir.
     */
    //MAL estan as dependencias mal dos toString
    public void descCasilla(String nombre) {
        if(nombre!=null) {
            setCtrl(false);
            Casilla atc = this.getTablero().encontrar_casilla(nombre);
            if (atc != null) System.out.println(atc.infoCasilla());
            else System.out.println("No existe esa casilla");
        }
        else{
            System.out.println("no se ha inrtoducido nombre de casilla o es invalido");
        }
    }

    private Casilla casillaMasRentable(){
        Casilla casillaRentable= null;
        float maxRentabilidadCasilla = -1;
        for(ArrayList<Casilla> lado : tablero.getPosiciones()){
            for (Casilla c : lado) {
                if (c.getRentabilidadAcumulada() > maxRentabilidadCasilla) {
                    maxRentabilidadCasilla = c.getRentabilidadAcumulada();
                    casillaRentable = c;
                }
            }
        }
        return casillaRentable;
    }

    private Casilla casillaMasVisitada(){
        Casilla casillaMasVisitada = null;
        int maxVisitas = -1;
        for(ArrayList<Casilla> lado : tablero.getPosiciones()){
            for (Casilla c : lado) {
                if (c.getVecesVisitada() > maxVisitas) {
                    maxVisitas = c.getVecesVisitada();
                    casillaMasVisitada = c;
                }
            }
        }
        return casillaMasVisitada;
    }

    private Grupo grupoMasRentable() {
        Grupo grupoMasRentable = null;
        float maxRentabilidad = -1;
        for (Grupo g : tablero.getGrupos().values()) {
            float rentabilidadGrupo = 0;
            for (Casilla c : g.getMiembros()) {
                rentabilidadGrupo += c.getRentabilidadAcumulada();
            }
            if (rentabilidadGrupo > maxRentabilidad) {
                maxRentabilidad = rentabilidadGrupo;
                grupoMasRentable = g;
            }
        }
        return grupoMasRentable;
    }

    /*Metodo que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
     * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    public void comprar() {
        if(this.getJugadores().isEmpty()){
            System.out.println("\nNo hay jugadores creados\n");
            return;
        }
        this.setCtrl(false);
        Casilla c= getAvatares().get(this.getTurno()).getLugar();
        if(c instanceof Propiedad p){
            p.comprarCasilla(this.getJugadores().get(this.getTurno()), this.getBanca());
        }
    }

    public void hipotecar(String nombreCasilla) {
        if(this.getJugadores().isEmpty()){
            System.out.println("\nNo hay jugadores creados\n");
            return;
        }
        setCtrl(false);
        Jugador actual = this.getJugadores().get(this.getTurno());
        Casilla c= this.getTablero().encontrar_casilla(nombreCasilla);
        if (c == null) {
            System.out.println("\nNo existe ninguna casilla llamada " + nombreCasilla + "\n");
            return;
        }
        if(c instanceof Propiedad prop){
            prop.hipotecar(actual);
        }
        else{
            System.out.println("No se peude hipotecas esa casilla");
        }
    }

    public void deshipotecar(String nombreCasilla) {
        if(this.getJugadores().isEmpty()){
            System.out.println("\nNo hay jugadores creados\n");
            return;
        }
        this.setCtrl(false);
        Jugador actual = this.getJugadores().get(this.getTurno());
        Casilla c= this.getTablero().encontrar_casilla(nombreCasilla);
        if(c!=null){
            if (c instanceof Propiedad prop) {
                prop.deshipotecar(actual);
            } else {
                System.out.println("No es una casilla deshipotecable.\n");
            }
        }
        else{
            System.out.println("No existe esa casill.\n");
        }
    }

    public void listarEdificios() {
        setCtrl(false);
        boolean hayEdificios = false;

        for (int i = 0; i < this.getTablero().getPosiciones().size(); i++) {
            ArrayList<Casilla> lado = this.getTablero().getPosiciones().get(i);

            for (Casilla casilla : lado) {
                if(casilla instanceof Solar sol){
                    String textoEdificios = sol.infoEdificios();
                    if (!textoEdificios.isEmpty()) {
                        System.out.println("\n------------------------EDIFICIOS------------------------\n");
                        System.out.print(textoEdificios);
                        hayEdificios = true;
                    }
                }
            }
        }

        if (!hayEdificios) {
            System.out.println("\nNo hay edificios creados\n");
        }
    }

    //EN ESTAS 2 MIRAR COMO SE PASABA POR TERMINAL
    public void construirEdificio(String tipo){
        if(getJugadores().get(getTurno()).getAvatar().getLugar() instanceof Solar sol){
            sol.construirEdificio(tipo,getTurno(),getJugadores());
        }
    }

    public void venderEdificios(String tipo, int cantidad, String nose){
        if(getJugadores().get(getTurno()).getAvatar().getLugar() instanceof Solar sol){
            sol.venderEdificios(tipo,cantidad,getJugadores().get(getTurno()));
        }
    }

    // Metodo que realiza las acciones asociadas al comando 'listar enventa'.
    public void listarVenta() {
        System.out.println("\n----------CASILLAS EN VENTA----------\n");
        for(int i = 0; i < getBanca().getPropiedades().size(); i++){
            Propiedad prop = getBanca().getPropiedades().get(i);
            String tipo =  "";
            if(prop instanceof Solar){
                tipo = "Solar".trim().toLowerCase(Locale.ROOT);;
            }
            if(prop instanceof Transporte){
                tipo = "Transporte".trim().toLowerCase(Locale.ROOT);;
            }
            if(prop instanceof Servicio){
                tipo = "Servicio".trim().toLowerCase(Locale.ROOT);;
            }

            // Solo imprimimos si es Solar, Transporte o Servicio
            if(tipo.equals("solar") || tipo.equals("transporte") || tipo.equals("servicio")){
                // Imprimimos la información de la casilla y un salto de línea para separarlas
                System.out.println(prop.casEnVenta(tipo) + "\n");
            }
        }
    }

    public void listarEdificios(String grupo) {
        this.setCtrl(false);
        boolean hayEdificios = false;

        for (int i = 0; i < this.getTablero().getPosiciones().size(); i++) {
            ArrayList<Casilla> lado = this.getTablero().getPosiciones().get(i);

            for (Casilla casilla : lado) {
                if(casilla instanceof Solar sol){
                    if (sol.getGrupo() != null && sol.getGrupo().getColorGrupo().equalsIgnoreCase(grupo)) {
                        String textoEdificios = sol.infoEdificios();
                        if (!textoEdificios.isEmpty()) {
                            // Imprimimos la cabecera solo la primera vez que encontramos edificios
                            if (!hayEdificios) {
                                System.out.println("\n------------------------EDIFICIOS " + grupo.toUpperCase() + "------------------------\n");
                                hayEdificios = true;
                            }
                            System.out.print(textoEdificios);
                        }
                    }
                }
            }
        }

        if (!hayEdificios) {
            System.out.println("\nNo hay edificios creados en el grupo " + grupo + "\n");
        }
    }

    //Metodo que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    public void lanzarDados(String vals) {
        if(!this.getJugadores().isEmpty()){
            if (!this.getTirado()) {
                if (vals!=null) {

                    //si el valor de la tirada viene determinado, casillas mover es con la suma de los valores introducidos
                    String[] dados = vals.split("\\+");

                    // 1. Comprobar que realmente hay dos partes (antes y después del +)
                    if (dados.length != 2) {
                        System.out.println("Error de formato. Usa: lanzar dados <numero>+<numero>");
                        setCtrl(false);
                        return; // Cortamos la ejecución de este metodo y volvemos al menú
                    }

                    //parseamos para tener los valores en entero
                    int val1 = 0, val2 = 0;

                    //Intentar convertir a número capturando el posible error
                    try {
                        val1 = Integer.parseInt(dados[0]);
                        val2 = Integer.parseInt(dados[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Los valores de los dados deben ser números enteros.");
                        setCtrl(false);
                        return; // Cortamos la ejecución de este metodo y volvemos al menú
                    }

                    //setteamos los valores de los dados
                    if (val1 > 6 || val1 < 1 || val2 > 6 || val2 < 1) {
                        this.getDado1().setValor(0);
                        this.getDado2().setValor(0);
                    }
                    else {
                        this.getDado1().setValor(val1);
                        this.getDado2().setValor(val2);
                    }
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
                    setCtrl(false);
                    jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel()+1);
                    if(jugadorActual.getTiradasCarcel() ==3 || this.getDado1().getValor()==this.getDado2().getValor()){
                        System.out.println(jugadorActual.getNombre() +"ha salido de la carcel!!");
                        jugadorActual.setEnCarcel(false);

                        jugadorActual.setTiradasCarcel(0);
                        setCtrl(true);
                    }
                }

                // Mirar si los dados son dobles
                boolean esDoble = (this.getDado1().getValor() == this.getDado2().getValor());

                if (esDoble) {
                    jugadorActual.setDobles(jugadorActual.getDobles() + 1);

                    if (jugadorActual.getDobles() == 3) {
                        System.out.println("¡" + jugadorActual.getNombre() + " ha sacado dobles 3 veces consecutivas! Vas directo a la cárcel.");
                        jugadorActual.encarcelar(this.getTablero()); // Metodo para encarcelar al jugador
                        jugadorActual.setDobles(0);
                        setTirado(true);
                        return; // No se mueve ni evalúa casilla
                    }

                    System.out.println("¡Has sacado dobles (" + this.getDado1().getValor() + " y " + this.getDado2().getValor() + ")! Puedes volver a tirar.");
                    setTirado(false); // Permite ejecutar de nuevo 'lanzar dados' en este turno
                }
                else {
                    jugadorActual.setDobles(0); // Reiniciamos contador si no saca dobles
                    setTirado(true);              // Finaliza su turno de tiradas
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
                setCtrl(false);
            }
        }
        else {
            System.out.println("\nNo hay jugadores creados\n");
        }
    }

    //metodo que evalua el tipo de casilla en el que cae el jugador y lo que ocurre en cada caso
    public void evaluarCasillaDestino(Jugador actual, Casilla destino, int sumaDados) {
        destino.sumarVecesVisitada();
        switch (destino) {
            case Solar dest -> {
                Jugador duenhoSolar = destino.getDuenho();
                Propiedad cas = (Propiedad) destino;

                // Comprobamos si tiene dueño, no es la banca, no es el jugador actual y NO está hipotecada
                if (duenhoSolar != null && !duenhoSolar.equals(this.getBanca()) && !duenhoSolar.equals(actual) && !cas.getHipotecada()) {

                    float alquiler;

                    boolean tieneEdificios = (dest.getNumeroCasas() > 0
                            || dest.getNumeroHoteles() > 0
                            || dest.getNumeroPiscinas() > 0
                            || dest.getNumeroPistas() > 0);

                    if (tieneEdificios) {
                        // Si tiene edificios, usamos el metodo de Casilla
                        alquiler = dest.alquilerPropiedad();
                    } else {
                        // Si no tiene edificios, parte del alquiler base
                        alquiler = destino.getImpuesto();

                        // Si el dueño posee todo el grupo sin edificar, el alquiler se duplica
                        if (dest.getGrupo() != null && dest.getGrupo().esDuenhoGrupo(duenhoSolar)) {
                            alquiler *= 2;
                        }
                    }

                    // Pago e insolvencia
                    if (!actual.sumarFortuna((-1) * alquiler)) {
                        this.setSolvente(false);
                        actual.declararBancarrota(duenhoSolar);
                        return;
                    }
                    actual.sumarAlquilerPagado(alquiler);
                    duenhoSolar.sumarFortuna(alquiler);
                    duenhoSolar.sumarAlquilerCobrado(alquiler);
                    destino.sumarRentabilidadAcumulada(alquiler);

                    System.out.printf("Se han pagado %.0f€ de alquiler a %s.\n", alquiler, duenhoSolar.getNombre());
                }
                // Si la casilla está hipotecada, se notifica y no se realiza cobro
                else if (dest.getHipotecada() && duenhoSolar != null && !duenhoSolar.equals(actual) && !duenhoSolar.equals(this.getBanca())) {
                    System.out.println("La casilla " + destino.getNombre().trim() + " está hipotecada. No se cobra alquiler.");
                }
                return;
            }
            case Servicio servicio -> {
                Jugador duenhoServicio = destino.getDuenho();

                if (duenhoServicio != null && !duenhoServicio.equals(this.getBanca()) && !duenhoServicio.equals(actual)) {
                    // Determinamos el multiplicador según el número de casillas de servicio del dueño (4 o 10)
                    int numServicios = duenhoServicio.numeroDeServicios();
                    float multiplicador = (numServicios >= 2) ? 10.0f : 4.0f;

                    float alquiler = multiplicador * sumaDados * Valor.FACTOR_SERVICIO;

                    if (!actual.sumarFortuna(-alquiler)) {
                        this.setSolvente(false);
                        actual.declararBancarrota(duenhoServicio);
                        return;
                    }
                    actual.sumarAlquilerPagado(alquiler);

                    duenhoServicio.sumarFortuna(alquiler);
                    duenhoServicio.sumarAlquilerCobrado(alquiler);
                    destino.sumarRentabilidadAcumulada(alquiler);

                    System.out.printf("Se han pagado %.0f€ de servicio a %s (Posee %d casilla/s de servicio).\n", alquiler, duenhoServicio.getNombre(), numServicios);
                }
            }
            case Transporte transporte -> {
                Jugador duenhoTransporte = destino.getDuenho();

                if (duenhoTransporte != null && !duenhoTransporte.equals(this.getBanca()) && !duenhoTransporte.equals(actual)) {
                    int numTransportes = duenhoTransporte.numeroDeTransportes();
                    float alquiler = destino.getImpuesto() * numTransportes;

                    if (!actual.sumarFortuna((-1) * alquiler)) {
                        this.setSolvente(false);
                        actual.declararBancarrota(duenhoTransporte);
                        return;
                    }
                    actual.sumarAlquilerPagado(alquiler);

                    duenhoTransporte.sumarFortuna(alquiler);
                    duenhoTransporte.sumarAlquilerCobrado(alquiler);
                    destino.sumarRentabilidadAcumulada(alquiler);
                    System.out.printf("Se han pagado %.0f€ de transporte a %s (posee %d transporte/s).\n",
                            alquiler, duenhoTransporte.getNombre(), numTransportes);
                }
            }
            case Parking park -> {
                if (park.getValorAcumulado() > 0) {
                    actual.sumarFortuna(park.getValorAcumulado());
                    actual.sumarPremiosInversionesParking(park.getValorAcumulado());

                    System.out.printf("El jugador %s recibe %.0f€ del bote del Parking.\n", actual.getNombre(), park.getValorAcumulado());

                    // Reiniciamos el bote a 0 usando el setter
                    ((Parking) destino).sumarValorParking((-1) * park.getValorAcumulado());
                } else {
                    System.out.println("El Parking no tiene bote acumulado.");
                }
                return;
            }
            case Especial esp -> {
                //sei q da error, este é dos que esta ben
                int type = esp.getTipo();
                switch (type) {
                    //para tener un sitio donde almacenar los impuestos cree el boteParking como atributo
                    //como ta por ver que e impuesto queda asi pa que de o fallo
                    case 4:

                        if (!actual.sumarFortuna((-1) * Valor.IMPUESTO)) {
                            this.setSolvente(false);
                            actual.declararBancarrota(this.getBanca()); // Las propiedades vuelven a la Banca
                            return;
                        }
                        actual.sumarTasasImpuestos(Valor.IMPUESTO);
                        // Sumamos al bote usando el getter y setter
                        // Buscamos la casilla de parking en el tablero y le sumamos el valor
                        Casilla parking = this.getTablero().encontrar_casilla("parking");
                        Parking park = (Parking) parking; //downcasting
                        if (parking != null) {
                            park.sumarValorParking(Valor.IMPUESTO);
                        }
                        System.out.printf("El jugador %s paga %.0f€ de impuestos que se depositan en el Parking.\n", actual.getNombre(), Valor.IMPUESTO);
                        return;

                    case 3:
                        Jugador jugador = getJugadores().get(getTurno());
                        jugador.encarcelar(getTablero());

                        System.out.println("El avatar se ha movido directamente a la casilla de Cárcel.");
                        return;
                    case 2: // No hace nada
                    case 1:// El cobro por pasar o caer en la salida ya se procesa durante el movimiento
                }
            }
            case Suerte luck -> {
                luck.suerte(this);
                return;
            }
            case CajaComunidad caj -> caj.cajaComunidad(this);
            default -> {
            }
        }
    }

    //Metodo que ejecuta todas las acciones relacionadas con el comando 'salir carcel'.
    public void salirCarcel() {
        if(this.getJugadores().isEmpty()){
            System.out.println("\nNo hay jugadores creados\n");
            return;
        }
        Jugador jug=this.getJugadores().get(this.getTurno());
        setCtrl(false);
        if(!jug.getEnCarcel()){
            System.out.println("Comando invalido");
            return;
        }
        if(!jug.sumarFortuna((-1)*Valor.COSTE_SALIR_CARCEL)){
            this.setSolvente(false);
            return;
        }
        jug.sumarTasasImpuestos(Valor.COSTE_SALIR_CARCEL);
        this.getBanca().sumarFortuna(Valor.COSTE_SALIR_CARCEL);
        jug.setTiradasCarcel(0);
        jug.setEnCarcel(false);

        System.out.println(jug.getNombre()+" ha pagado 500000 para salir de la carcel.\nPuede lanzar los dados\n");
    }

    public void mostrarEstadisticasJuego() {
        if (this.getJugadores() == null || this.getJugadores().isEmpty()) {
            System.out.println("\nNo hay jugadores creados\n");
            ctrl=false;
            return;
        }
        Casilla casillaRentable = casillaMasRentable();
        Grupo grupoRentable = grupoMasRentable();
        Casilla casillaFrecuente = casillaMasVisitada();
        Jugador masVueltas = jugadorMasVueltas();
        Jugador enCabeza = jugadorEnCabeza();

        System.out.println("=== ESTADÍSTICAS DE JUEGO ===");
        System.out.println("- Casilla mas rentable: " + casillaRentable.getNombre().trim());
        System.out.println("- Grupo mas rentable: " + grupoRentable.getColorGrupo());
        System.out.println("- Casilla mas frecuentada: " + casillaFrecuente.getNombre().trim());
        System.out.println("- Jugador con mas vueltas: " + masVueltas.getNombre());
        System.out.println("- Jugador en cabeza: " + enCabeza.getNombre());

        ctrl = false;
    }

    public void mostrarEstadisticasJugador(String nombrejugador) {
        Jugador jugador = null;
        for (Jugador j : this.getJugadores()) {
            if (j.getNombre().equalsIgnoreCase(nombrejugador)) {
                jugador = j;
                break;
            }
        }
        if(jugador == null) {
            System.out.println("No existe ningun jugador con el nombre '" + nombrejugador + "'.");
        }
        else {
            System.out.println("=== ESTADÍSTICAS DE FORTUNA: " + jugador.getNombre() + " ===");
            System.out.println("- Dinero invertido en propiedades y edificaciones: " + jugador.getDineroInvertidoPropiedades());
            System.out.println("- Dinero pagado en tasas e impuestos: " + jugador.getTasasImpuestosPagados());
            System.out.println("- Alquileres pagados: " + jugador.getAlquileresPagados());
            System.out.println("- Alquileres cobrados: " + jugador.getAlquileresCobrados());
            System.out.println("- Dinero recibido por pasar por la Salida: " + jugador.getDineroSalida());
            System.out.println("- Dinero recibido por premios, inversiones o parking: " + jugador.getDineroPremiosInversionesParking());
            System.out.println("- Veces que ha caído en la cárcel: " + jugador.getVecesCarcel());
            System.out.println("==================================================");
        }
        ctrl = false;
    }


    // Metodo que realiza las acciones asociadas al comando 'acabar turno'.
    public void acabarTurno() {
        if(!this.getJugadores().isEmpty()){
            if(this.getTirado()) {
                if (this.getTurno() == this.getAvatares().size() - 1) {
                    this.setTurno(0);
                } else {
                    this.setTurno(this.getTurno() + 1);
                }
                System.out.println("Jugador que tiene el turno:" + this.getJugadores().get(this.getTurno()).getNombre() + "\n");
                setCtrl(false);
                setTirado(false);
            }
            else{
                System.out.println("no se ha tirado aun, no se puede acabar el turno");
            }
        }
        else{
            System.out.println("\nNo hay jugadores creados\n");
        }
    }
}