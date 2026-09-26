package monopoly;

import partida.*;
import java.util.ArrayList;


class Grupo {

    //Atributos
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.

    //Constructor vacío.
    public Grupo() {
        this.miembros = new ArrayList<>();
        this.setColorGrupo("");
        this.setNumCasillas(0);
    }

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
    * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    //Rodrigo-23/09 (añadido debaixo comentario)
    public Grupo(Casilla cas1, Casilla cas2, String colorGrupo) {
        this.miembros = new ArrayList<>();
        this.setColorGrupo(colorGrupo);
        this.setNumCasillas(0);
        this.anhadirCasilla(cas1);
        this.anhadirCasilla(cas2);
    }

    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    //Rodrigo-23/09 (añadido debaixo comentario)
    public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo) {
        this.miembros = new ArrayList<>();
        this.setColorGrupo(colorGrupo);
        this.setNumCasillas(0);
        this.anhadirCasilla(cas1);
        this.anhadirCasilla(cas2);
        this.anhadirCasilla(cas3);
    }

    // GETTERS
    public ArrayList<Casilla> getMiembros() {
        return miembros;
    }
    public String getColorGrupo() {
        return colorGrupo;
    }
    public int getNumCasillas() {
        return numCasillas;
    }

    // SETTERS
    public void setMiembros(ArrayList<Casilla> m) {
        this.miembros = m;
    }
    public void setColorGrupo(String c) {
        this.colorGrupo = c;
    }
    public void setNumCasillas(int n) {
        this.numCasillas = n;
    }

    /* Metodo que anhade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Casilla miembro) {
        this.miembros.add(miembro);
        this.setNumCasillas(this.getNumCasillas()+1);
    }

    /*Metodo que anhade todas las casillas a la banca, para no tener que llamar
    *25 veces a anhadecasillas en el programa.
    * Parametro: ArrayList de todas las casillas
     */

    /*Metodo que comprueba si el jugador pasado tiene en su haber todas las casillas del grupo:
    * Parámetro: jugador que se quiere evaluar.
    * Valor devuelto: true si es dueño de todas las casillas del grupo, false en otro caso.
     */
    //Rodrigo-24/09
    public boolean esDuenhoGrupo(Jugador jugador) {
        ArrayList<Casilla> miembros = this.getMiembros();
        for(int c=0; c<miembros.size();c++){
            Casilla cas = miembros.get(c);
            String id = cas.getDuenho().getAvatar().getId();
            if(!jugador.getAvatar().getId().equals(id)){
                return false;
            }
        }
        return true;
    }

}
