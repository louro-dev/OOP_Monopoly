package monopoly;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Scanner;
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

    //CONSTRUCTORf
    public Menu(String args[]){
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


    // Metodo para inciar una partida: crea los jugadores y avatares.
    //por ahora solo pedimos casilla de Salida, si hiciesen falta mas se pediria el tablero
    private void iniciarPartida(String args[]) {
        //creamos variable de control para gestionar en que iteraciones se imprime el tablero: si se va a mover un jugador
        //si hace falta, pero si solo se pide que se imprima algo no hace falta reimprimirlo
        boolean ctrl=true;
        boolean end=false;

        File iniDoc = null;
        //Si se pasa el documento por argumento se abre y ya
        if(args.length>0)   iniDoc=new File(args[0]);
        //si no se pregunta si se quiere usar o no. Si sí se pide.
        else{
            System.out.println("Ningun documento introducido en linea de comandos\n quiere usar documento inicial?: y/n\n");
            String ans=new Scanner(System.in).next();
            switch (ans) {
                case "y":
                    System.out.println("dea el nombre del documento:\n");
                    String doc=new Scanner(System.in).next();
                    iniDoc=new File(doc);
                    break;
                case "n":
                default:
                    //temporalmente usamos la variable de control para controlar si se va a usar documento inicial o no
                    ctrl=false;
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
                    String comand;
                    if (divCom.length >= 2) {comand = divCom[0] + divCom[1];}
                    else {comand = divCom[0];}

                    //case con analizarComando que raaliza las acciones pedidas
                    switch (analizarComando(comand)) {
                        //0 y -1 son errores
                        case 0:
                        case -1:
                            System.out.println("Error al leer el archivo1");
                            return;
                        //1 se ignora, no puede mandarte a outro nuevo archivo
                        case 1: break;
                        //21 se lanzan los dados y mueve al jugador que tiene el turno
                        case 21:
                            if(this.getJugadores().isEmpty()){System.out.println("\nNo hay jugadores creados\n");break;}
                            if (tirado) {
                                System.out.println("ya se ha tirado en este turno");
                                ctrl = false;
                                break;
                            }
                            if (divCom.length >= 3) {
                                //si el valor de la tirada viene determinado, casillas mover es con la suma de los valores introducidos
                                String[] num1 = divCom[2].split("\\+");

                                //parseamos para tener los valores en entero
                                int val1 = Integer.parseInt(num1[0]), val2 = Integer.parseInt(num1[1]);

                                //setteamos los valores de los dados
                                this.getDado1().setValor(val1);
                                this.getDado2().setValor(val2);
                            } else {
                                //si no esta determimado valdrá el valor aleatorio que se genere
                                this.lanzarDados(this.getDado1(), this.getDado2());
                            }

                            //sacamos el entero con el valor
                            int casillasMover = this.getDado1().getValor() + this.getDado2().getValor();
                            System.out.println("Dado1:"+this.getDado1()+"\nDado2:"+this.getDado2());


                            //vemos que jugador tiene el turno y lo sacamos
                            Avatar jugador = this.getAvatares().get(this.getTurno());
                            Jugador jugadorActual = this.getJugadores().get(this.getTurno());

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

                            if(!this.getAvatares().get(this.getTurno()).getJugador().getEnCarcel()){
                                //invocamos a la funcion que mueve el avatar para
                                jugador.moverAvatar(this.getTablero().getPosiciones(), casillasMover);

                                //Evaluamos la casilla de destino (alquileres, impuestos, parking, carcel, etc.)
                                this.evaluarCasillaDestino(jugadorActual, jugador.getLugar(), casillasMover);
                            }

                            tirado = true;
                            break;

                        //22 creamos nuevo jugador
                        case 22:
                            int tam = this.getAvatares().size();
                            Jugador j = Jugador.newJugador(divCom[2], divCom[3], ini, this.getAvatares(), this.getJugadores());

                            if(this.getAvatares().size() == tam){ctrl=false;}
                            break;
                        //23 imprimir jugador que tiene el turno
                        case 23:
                            //ponemos control de impresion a false para que no imprima
                            ctrl = false;
                            if(this.getJugadores().isEmpty()){System.out.println("\nNo hay jugadores creados\n");break;}
                            //imprimimos cabecera de impresion
                            System.out.println("\n---------------JUGADOR QUE TIENE EL TURNO---------------");
                            //this.getTurno(); indice en la losta del jugador que tiene el turno
                            Jugador turn = this.getJugadores().get(this.getTurno());
                            System.out.println(turn + "\n\n");
                            break;
                        //24 listamos los jugadores
                        case 24:
                            //ponemos control de impresion a false para que no imprima
                            ctrl = false;
                            if(this.getJugadores().isEmpty()){System.out.println("\nNo hay jugadores creados\n");break;}
                            //imprimimos cabecera de impresion
                            System.out.println("\n------------------------JUGADORES------------------------\n");
                            //imprimimos jugador con bucle que itera el ArrayList
                            for (int i = 0; i < this.getJugadores().size(); i++) {
                                System.out.println("\nJugador" + (i + 1) + ":");
                                Jugador jug = this.getJugadores().get(i);
                                System.out.println(jug + "\n");
                            }
                            break;

                        case 25:
                            if(this.getJugadores().isEmpty()){System.out.println("\nNo hay jugadores creados\n");break;}
                            if (this.getTurno() == this.getAvatares().size() - 1) {this.turno = 0;}
                            else {this.turno = this.getTurno() + 1;}

                            System.out.println("Jugador que tiene el turno:" + this.getJugadores().get(this.getTurno()).getNombre() + "\n");
                            ctrl = false;
                            tirado=false;
                            break;

                        case 26:
                            Jugador jug=this.getJugadores().get(this.getTurno());
                            ctrl=false;

                            if(this.getJugadores().isEmpty()){System.out.println("\nNo hay jugadores creados\n");break;}
                            if(!jug.getEnCarcel()){System.out.println("Comando invalido");break;}

                            if(!jug.sumarFortuna((-1)*Valor.COSTE_SALIR_CARCEL)){setSolvente(false);break;}

                            this.getBanca().sumarFortuna(Valor.COSTE_SALIR_CARCEL);
                            jug.setTiradasCarcel(0);
                            jug.setEnCarcel(false);

                            System.out.println(jug.getNombre()+" ha pagado 500000 para salir de la carcel.\nPuede lanzar los dados\n");
                            break;

                        case 27:
                            ctrl=false;
                            Casilla atc= this.getTablero().encontrar_casilla(divCom[1]);
                            if(atc!=null) {System.out.println(atc.toString(atc.getTipo()));}
                            else{System.out.println("No existe esa casilla");}

                            break;

                        case 28:
                            if(this.getJugadores().isEmpty()){System.out.println("\nNo hay jugadores creados\n");break;}
                            ctrl=false;
                            Jugador ply = Jugador.encontrarJugador(divCom[2],this.getJugadores());
                            if(ply != null) {System.out.println(ply.toString(ply.getNombre()));}
                            break;
                        case 29:
                            if(this.getJugadores().isEmpty()){System.out.println("\nNo hay jugadores creados\n");break;}
                            ctrl=false;
                            Casilla c=this.getAvatares().get(this.getTurno()).getLugar();
                            c.comprarCasilla(this.getJugadores().get(this.getTurno()),this.getBanca());
                            break;

                        case 30:
                            break;

                        case 31:
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
                            break;

                        default:
                            ctrl=false;
                            break;
                    }
                    if (ctrl) System.out.println("\n" + this.getTablero());
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo2");
            }
        }
        //________________________________________________________________
        //----------------------------------------------------------------
        do{
            //reiniciamos variable de control de impresion
            ctrl=true;
            //imprimimos la opcion para pedir los dados y la escaneamos con sc.nextLine()
            Scanner sc = new Scanner(System.in);
            System.out.println("--------------------MENU--------------------\nlanzar los dados: lanzar Dados\n" +
                    "Lanzar los dados determinados: lanzad Dados <x+y>\nCrear un jugador: crear Jugador <nombre> <tipo_avatar>"+
                    "\nJugador al que le toca: jugador\nListar los jugadores: listar Jugadores\n"+"Describir una casilla: describir <casilla>\n"+
                    "Describir a un jugador: describir jugador <nombre>"+"\nAcabar el turno: Acabar Turno\nVer el tablero: ver tablero\n" +
                    "Comprar una casilla: comprar casilla\nListar las casillas en venta: Listar enventa\nAcabar la partida: salir\n");
            if(!this.getJugadores().isEmpty() && this.getJugadores().get(this.getTurno()).getEnCarcel()){
                System.out.println("Salir de la carcel: Salir Carcel\n");
            }
            String answ = sc.nextLine();

            //separamos String en partes para los comandos de mas de 1 palabra
            String[] divEn = answ.split(" ");
            String comand;

            if (divEn.length >= 2) {comand = divEn[0] + divEn[1];}
            else {comand = divEn[0];}

            //pasamos el comando por el método analizarComando
            int sec = this.analizarComando(comand);
            if (sec == -1) System.out.println("Comando invalido1");
            else if (sec >= 20 && sec <=35) {
                //switch para saber que accion hacer
                switch (sec) {
                    case 21:
                        if(this.getJugadores().isEmpty()){
                            System.out.println("\nNo hay jugadores creados\n");
                            break;
                        }
                        if (tirado) {
                            System.out.println("ya se ha tirado en este turno");
                            ctrl = false;
                            break;
                        }
                        if (divEn.length >= 3) {
                            //si el valor de la tirada viene determinado, casillas mover es con la suma de los valores introducidos
                            String[] num1 = divEn[2].split("\\+");

                            //parseamos para tener los valores en entero
                            int val1 = Integer.parseInt(num1[0]), val2 = Integer.parseInt(num1[1]);

                            //setteamos los valores de los dados
                            this.getDado1().setValor(val1);
                            this.getDado2().setValor(val2);

                        } else {
                            //si no esta determimado valdrá el valor aleatorio que se genere
                            this.lanzarDados(this.getDado1(), this.getDado2());
                        }

                        //sacamos el entero con el valor
                        int casillasMover = this.getDado1().getValor() + this.getDado2().getValor();
                        System.out.println("Dado1:"+this.getDado1()+"\nDado2:"+this.getDado2());


                        //vemos que jugador tiene el turno y lo sacamos
                        Avatar jugador = this.getAvatares().get(this.getTurno());
                        Jugador jugadorActual = this.getJugadores().get(this.getTurno());

                        //logica de tirada si esta en la carcel
                        if(jugadorActual.getEnCarcel()){
                            System.out.println("tirada numero: "+(jugadorActual.getTiradasCarcel()+1));
                            System.out.println(" tiradas para salir:3");
                            ctrl=false;
                            jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel()+1);
                            if(jugadorActual.getTiradasCarcel() ==3 || this.getDado1().getValor()==this.getDado2().getValor()){
                                System.out.println(jugadorActual.getNombre() +" ha salido de la carcel!!");
                                jugadorActual.setEnCarcel(false);

                                jugadorActual.setTiradasCarcel(0);
                                ctrl=true;
                            }
                        }

                        if(!this.getAvatares().get(this.getTurno()).getJugador().getEnCarcel()){
                            //invocamos a la funcion que mueve el avatar para
                            jugador.moverAvatar(this.getTablero().getPosiciones(), casillasMover);

                            //Evaluamos la casilla de destino (alquileres, impuestos, parking, carcel, etc.)
                            this.evaluarCasillaDestino(jugadorActual, jugador.getLugar(), casillasMover);
                        }

                        tirado = true;
                        break;

                    case 22:
                        int tam = this.getAvatares().size();
                        //utilizamos los otros Strings de divEn[i] para los parametros
                        Jugador j = Jugador.newJugador(divEn[2],divEn[3],ini,this.getAvatares(),this.getJugadores());
                        if(this.getAvatares().size() == tam){
                            ctrl=false;
                        }

                        break;

                    case 23:
                        //ponemos control de impresion a false para que no imprima
                        ctrl=false;
                        //imprimimos cabecera de impresion
                        if(this.getJugadores().isEmpty()){
                            System.out.println("\nNo hay jugadores creados\n");
                            break;
                        }
                        System.out.println("---------------JUGADOR QUE TIENE EL TURNO---------------");
                        //this.getTurno(); indice en la losta del jugador que tiene el turno
                        Jugador turn=this.getJugadores().get(this.getTurno());
                        System.out.println(turn+"\n");
                        break;

                    case 24:
                        //ponemos control de impresion a false para que no imprima
                        ctrl=false;
                        if(this.getJugadores().isEmpty()){
                            System.out.println("\nNo hay jugadores creados\n");
                            break;
                        }
                        //imprimimos cabecera de impresion
                        System.out.println("\n------------------------JUGADORES------------------------\n");
                        //imprimimos jugador con bucle que itera el ArrayList
                        for(int i=0;i<this.getJugadores().size();i++) {
                            System.out.println("\nJugador"+(i+1)+":");
                            Jugador jug = this.getJugadores().get(i);
                            System.out.println(jug+"\n");
                        }
                        break;

                    case 25:
                        if(this.getJugadores().isEmpty()){
                            System.out.println("\nNo hay jugadores creados\n");
                            break;
                        }
                        if (this.getTurno() == this.getAvatares().size() - 1) {
                            this.turno = 0;
                        } else {
                            this.turno = this.getTurno() + 1;
                        }
                        System.out.println("Jugador que tiene el turno:" + this.getJugadores().get(this.getTurno()).getNombre() + "\n");
                        ctrl = false;
                        tirado=false;
                        break;

                    case 26:
                        if(this.getJugadores().isEmpty()){
                            System.out.println("\nNo hay jugadores creados\n");
                            break;
                        }
                        Jugador jug=this.getJugadores().get(this.getTurno());
                        ctrl=false;
                        if(!jug.getEnCarcel()){
                            System.out.println("Comando invalido");
                            break;
                        }
                        if(!jug.sumarFortuna((-1)*Valor.COSTE_SALIR_CARCEL)){
                            setSolvente(false);
                            break;
                        }
                        this.getBanca().sumarFortuna(Valor.COSTE_SALIR_CARCEL);
                        jug.setTiradasCarcel(0);
                        jug.setEnCarcel(false);

                        System.out.println(jug.getNombre()+" ha pagado 500000 para salir de la carcel.\nPuede lanzar los dados\n");
                        break;

                    case 27:
                        ctrl=false;
                        Casilla atc= this.getTablero().encontrar_casilla(divEn[1]);
                        if(atc!=null) {
                            System.out.println(atc.toString(atc.getTipo()));
                        }
                        else{
                            System.out.println("No existe esa casilla");
                        }

                        break;

                    case 28:
                        ctrl=false;
                        if(this.getJugadores().isEmpty()){
                            System.out.println("\nNo hay jugadores creados\n");
                            break;
                        }
                        Jugador ply = Jugador.encontrarJugador(divEn[2],this.getJugadores());
                        if(ply != null) {
                            System.out.println(ply.toString(ply.getNombre()));
                        }
                        break;

                    case 20:
                        System.out.println("\n"+this.getTablero());
                        end = true;
                        ctrl=false;
                        System.out.println("Terminando partida...");
                        break;

                    case 29:
                        ctrl=false;
                        if(this.getJugadores().isEmpty()){
                            System.out.println("\nNo hay jugadores creados\n");
                            break;
                        }
                        Casilla c=this.getAvatares().get(this.getTurno()).getLugar();
                        c.comprarCasilla(this.getJugadores().get(this.getTurno()),this.getBanca());
                        break;
                    case 30:
                        break;

                    case 31:
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
                        break;

                    default:
                        ctrl=false;
                        System.out.println("Comando invalido");
                }
            }
            if (ctrl) System.out.println("\n"+this.getTablero());
        }while(!end);
    }

    
    /*Metodo que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetros: cadena de caracteres (el comando).
    */
    //devuelve:
    // -1 si ha habido un error
    // 1 si la entrada es un documento
    // 2x/3x si la entrada es un comando:
    private int analizarComando(String comando) {
        //capa1: analizamos si es documento o si es comando
        if(comando.toLowerCase().endsWith(".txt")){
            //capa 2: revisamos que el documento exista
            File doc = new File(comando);
            if(doc.exists() && doc.isFile()) return 1;
            else return -1;

        }

        String cmd=comando.toLowerCase(Locale.ROOT);
        if(cmd.equals("describirjugador"))return 28;
        if(cmd.startsWith("describir")) return 27;
        else{
            //capa 2: hacemos un switch con todos los comandos posibles para ver que coincida con uno de ellos
            // el toLowerCase se usa por si hya mayusculas
            switch (cmd){
                case "lanzardados": return 21;
                case "crearjugador": return 22;
                case "jugador": return 23;
                case "listarjugadores": return 24;
                case "acabarturno":return 25;
                case "salircarcel": return 26;
                //hueco del "case 27" de describir casilla que sale antes por el return
                //hueco del "case 28" de describir jugador, que no puede ir aqui porque se confundiria con el 2
                case "comprarcasilla": return 29;
                case "vertablero": return 30;
                case "listarenventa": return 31;
                case "salir": return 20;
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
    private void lanzarDados(Dado d1, Dado d2) {
        d1.setValor(d1.hacerTirada());
        d2.setValor(d2.hacerTirada());
        tirado = true;
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
                    if(!actual.sumarFortuna((-1) * alquiler)){
                        setSolvente(false);
                        break;
                    };
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
                destino.sumarValor(Valor.IMPUESTO);
                System.out.printf("El jugador %s paga %.0f€ de impuestos que se depositan en el Parking.\n", actual.getNombre(),Valor.IMPUESTO);
                break;

            case "parking":
                if (destino.getValor() > 0) {
                    actual.sumarFortuna(destino.getValor());
                    System.out.printf("El jugador %s recibe %.0f€ del bote del Parking.\n",
                            actual.getNombre(), destino.getValor());

                    // Reiniciamos el bote a 0 usando el setter
                    destino.sumarValor((-1)*destino.getValor());//[cite: 4]
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
                // No realiza acción en esta primera entrega[cite: 6]
                break;
        }
    }

}
