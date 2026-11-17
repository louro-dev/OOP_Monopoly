package monopoly;

import java.io.File;
import java.util.Locale;

import partida.*;

public class Menu {

    // ATRIBUTOS
    private Juego juego;

    //CONSTRUCTOR VACIO
    public Menu(Juego j){
        setJuego(j);
    }

    // GETTERS
    Juego getJuego() {
        return this.juego;
    }

    // SETTERS
    void setJuego(Juego j) {
        this.juego = j;
    }

    /*Metodo que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetros: cadena de caracteres (el comando).
    */
    public void analizarComando(String comando, String auxiliar) {

        Casilla ini = juego.getTablero().encontrar_casilla("salida");
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
                juego.descJugador(auxiliar);
            }
            else {
                System.out.println("Debes indicar el nombre del jugador.");
                juego.setCtrl(false);
            }
        }
        else if(cmd.startsWith("describir")){
            juego.descCasilla(cmd.substring(9));}
        else if(cmd.startsWith("hipotecar")){
            juego.hipotecar(cmd.substring(9));
        }
        else if(cmd.startsWith("deshipotecar")) {
            juego.deshipotecar(cmd.substring(12));
        }
        else if(cmd.startsWith("edificar")){
            juego.construirEdificio(cmd.substring(8),this.juego.getJugadores().get(this.juego.getTurno()));
        }
        else if(cmd.equals("estadisticas")) {
            juego.mostrarEstadisticasJuego();
        }
        else if(cmd.startsWith("estadisticas")){
            juego.mostrarEstadisticasJugador(cmd.substring(12));
        }
        else if(cmd.startsWith("vender")) {
            if(auxiliar!=null) {
                String[] partes= auxiliar.split(",");
                if(partes.length == 2) {
                    juego.venderEdificios(cmd.substring(6),partes[0],Integer.parseInt(partes[1]));
                }
                else {
                    System.out.println("Error. Uso: vender <nombre_edificio> <nombre_casilla> <cantidad>");
                }
            }
            else {
                System.out.println("Error. Uso: vender <nombre_edificio> <nombre_casilla> <cantidad>");
            }
            juego.setCtrl(false);
        }
        else{
            //capa 2: hacemos un switch con todos los comandos posibles para ver que coincida con uno de ellos
            // el toLowerCase se usa por si hya mayusculas
            switch (cmd){
                case "lanzardados":
                    juego.lanzarDados(auxiliar);
                    break;
                case "crearjugador":
                    if(auxiliar!=null) {
                        String[] partes= auxiliar.split(",");
                        if(partes.length == 2) {
                            Jugador j = Jugador.newJugador(partes[0],partes[1],ini,juego.getAvatares(),juego.getJugadores());
                            if(j==null){
                                juego.setCtrl(false);
                            }
                        }
                        else {
                            System.out.println("Error. Uso: crear jugador <nombre> <avatar>");
                            juego.setCtrl(false);
                        }
                    }
                    else {
                        System.out.println("Error. Uso: crear jugador <nombre> <avatar>");
                        juego.setCtrl(false);
                    }
                    break;
                case "jugador":
                    juego.descJugadorTurno();
                    break;
                case "listarjugadores":
                    juego.listarJugadores();
                    break;
                case "listaredificios":
                    if(auxiliar == null) {
                        juego.listarEdificios();
                    }
                    else {
                        juego.listarEdificios(auxiliar);
                    }
                    break;
                case "acabarturno":
                    juego.acabarTurno();
                    break;
                case "salircarcel":
                    juego.salirCarcel();
                    break;
                //hueco de describir casilla que sale antes por el return
                //hueco de describir jugador, que no puede ir aqui porque se confundiria con el 2
                case "comprarcasilla":
                    juego.comprar();
                    break;
                case "vertablero":
                    break;
                case "listarenventa":
                    juego.listarVenta();
                    break;
                case "salir":
                    juego.setEnd(true);
                    juego.setCtrl(true);
                    System.out.println("Terminando partida...");
                    break;
                default:
                    juego.setCtrl(false);
                    System.out.println("comando invalido");
                    break;
            }
        }
        if(juego.getCtrl())System.out.println("\n"+this.juego.getTablero());
    }

    /*Metodo que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    * private void descAvatar(String ID) {}
    *
    *Metodo que realiza las acciones asociadas al comando 'listar avatares'.
    *private void listarAvatares() {}
     */
}
