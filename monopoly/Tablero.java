package monopoly;

import Casillas.*;
import partida.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Tablero {
    //Atributos
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.

    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {
        this.banca = banca;
        this.posiciones = new ArrayList<>();
        this.grupos = new HashMap<>();
        generarCasillas();
    }

    // GETTERS
    public ArrayList<ArrayList<Casilla>> getPosiciones() {return posiciones;}
    public HashMap<String, Grupo> getGrupos() {return grupos;}
    public Jugador getBanca() {return banca;}

    // SETTERS
    public void setPosiciones(ArrayList<ArrayList<Casilla>> p) {this.posiciones = p;}
    public void setGrupos(HashMap<String, Grupo> g) {this.grupos = g;}
    public void setBanca(Jugador b) {this.banca = b;}
    
    //Metodo para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {
        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();
    }
    
    //Metodo para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> norte = new ArrayList<>();

        Parking parking = new Parking("Parking       ",21,banca);
        norte.add(parking);

        Solar solar12 = new Solar("Solar12       ", 22, 2200000, 1100000, 180000,
                1500000, 300000, 600000, 2200000, 10500000, 2100000, 2100000);
        norte.add(solar12);

        Suerte suerte3=new Suerte("Suerte        ",23,banca);
        norte.add(suerte3);


        Solar solar13 = new Solar("Solar13       ", 24, 2200000, 1100000, 180000,
                1500000, 300000, 600000, 2200000, 10500000, 2100000, 2100000);
        norte.add(solar13);

        Solar solar14 = new Solar("Solar14       ", 25, 2400000, 1200000, 200000,
                1500000, 300000, 600000, 2325000, 11000000, 2200000, 2200000);
        norte.add(solar14);

        Transporte trans3 = new Transporte("Trans3        ", 26, banca);
        norte.add(trans3);

        Solar solar15 = new Solar("Solar15       ", 27, 2600000, 1300000, 220000,
                1500000, 300000, 600000, 2450000, 11500000, 2300000, 2300000);
        norte.add(solar15);

        Solar solar16 = new Solar("Solar16       ", 28, 2600000, 1300000, 220000,
                1500000, 300000, 600000, 2450000, 11500000, 2300000, 2300000);
        norte.add(solar16);

        norte.add(new Servicio("Serv2         ", 29, banca));

        Solar solar17 = new Solar("Solar17       ", 30, 2800000, 1400000, 240000,
                1500000, 300000, 600000, 2600000, 12000000, 2400000, 2400000);
        norte.add(solar17);

        Especial irCarcel = new Especial(3,"IrCarcel      ",31,banca);
        norte.add(irCarcel);

        // Grupos del lado norte.
        Grupo rojo = new Grupo(solar12, solar13, solar14, "Rojo");
        solar12.setGrupo(rojo);
        solar13.setGrupo(rojo);
        solar14.setGrupo(rojo);
        grupos.put("Rojo", rojo);

        Grupo amarillo = new Grupo(solar15, solar16, solar17, "Amarillo");
        solar15.setGrupo(amarillo);
        solar16.setGrupo(amarillo);
        solar17.setGrupo(amarillo);
        grupos.put("Amarillo", amarillo);

        posiciones.add(norte);
    }

    //Metodo para insertar las casillas del lado sur.
    private void insertarLadoSur() {
        ArrayList<Casilla> sur = new ArrayList<>();

        Especial salida = new Especial(1,"Salida        ",1,banca);
        sur.add(salida);

        Solar solar1 = new Solar("Solar1        ", 2, 600000, 300000, 20000,
                500000, 100000, 200000, 400000, 2500000, 500000, 500000);
        sur.add(solar1);

        CajaComunidad caja3 = new CajaComunidad("Caja          ",3,banca);
        sur.add(caja3);

        Solar solar2 = new Solar("Solar2        ", 4, 600000, 300000, 40000,
                500000, 100000, 200000, 800000, 4500000, 900000, 900000);
        sur.add(solar2);

        Especial Imp1= new Especial(4,"Imp1          ",5,Valor.IMPUESTO,banca);
        sur.add(Imp1);

        Transporte trans1 = new Transporte("Trans1        ", 6, banca);
        sur.add(trans1);

        Solar solar3 = new Solar("Solar3        ", 7, 1000000, 500000, 60000,
                500000, 100000, 200000, 1000000, 5500000, 1100000, 1100000);
        sur.add(solar3);

        Suerte suerte1=new Suerte("Suerte        ",8,banca);
        sur.add(suerte1);

        Solar solar4 = new Solar("Solar4        ", 9, 1000000, 500000, 60000,
                500000, 100000, 200000, 1000000, 5500000, 1100000, 1100000);
        sur.add(solar4);

        Solar solar5 = new Solar("Solar5        ", 10, 1200000, 600000, 80000,
                500000, 100000, 200000, 1250000, 6000000, 1200000, 1200000);
        sur.add(solar5);

        Especial carcel= new Especial(2,"Carcel        ",11,banca);
        sur.add(carcel);

        // Grupos del lado sur.
        Grupo marron = new Grupo(solar1, solar2, "Marron");
        solar1.setGrupo(marron);
        solar2.setGrupo(marron);
        grupos.put("Marron", marron);

        Grupo celeste = new Grupo(solar3, solar4, solar5, "Celeste");
        solar3.setGrupo(celeste);
        solar4.setGrupo(celeste);
        solar5.setGrupo(celeste);
        grupos.put("Celeste", celeste);

        posiciones.add(sur);
    }

    //Metodo que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> oeste = new ArrayList<>();

        Solar solar6 = new Solar("Solar6        ", 12, 1400000, 700000, 100000,
                1000000, 200000, 400000, 1500000, 7500000, 1500000, 1500000);
        oeste.add(solar6);

        oeste.add(new Servicio("Serv1         ", 13, banca));

        Solar solar7 = new Solar("Solar7        ", 14, 1400000, 700000, 100000,
                1000000, 200000, 400000, 1500000, 7500000, 1500000, 1500000);
        oeste.add(solar7);

        Solar solar8 = new Solar("Solar8        ", 15, 1600000, 800000, 120000,
                1000000, 200000, 400000, 1750000, 9000000, 1800000, 1800000);
        oeste.add(solar8);

        Transporte trans2 = new Transporte("Trans2        ", 16, banca);
        oeste.add(trans2);

        Solar solar9 = new Solar("Solar9        ", 17, 1800000, 900000, 140000,
                1000000, 200000, 400000, 1850000, 9500000, 1900000, 1900000);
        oeste.add(solar9);

        CajaComunidad caja1 = new CajaComunidad("Caja          ",18,banca);
        oeste.add(caja1);

        Solar solar10 = new Solar("Solar10       ", 19, 1800000, 900000, 140000,
                1000000, 200000, 400000, 1850000, 9500000, 1900000, 1900000);
        oeste.add(solar10);

        Solar solar11 = new Solar("Solar11       ", 20, 2200000, 1000000, 160000,
                1000000, 200000, 400000, 2000000, 10000000, 2000000, 2000000);
        oeste.add(solar11);

        // Grupos del lado oeste.
        Grupo rosa = new Grupo(solar6, solar7, solar8, "Rosa");
        solar6.setGrupo(rosa);
        solar7.setGrupo(rosa);
        solar8.setGrupo(rosa);
        grupos.put("Rosa", rosa);

        Grupo naranja = new Grupo(solar9, solar10, solar11, "Naranja");
        solar9.setGrupo(naranja);
        solar10.setGrupo(naranja);
        solar11.setGrupo(naranja);
        grupos.put("Naranja", naranja);

        posiciones.add(oeste);
    }

    //Metodo que inserta las casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> este = new ArrayList<>();

        Solar solar18 = new Solar("Solar18       ", 32, 3000000, 1500000, 260000,
                2000000, 400000, 800000, 2750000, 12750000, 2550000, 2550000);
        este.add(solar18);

        Solar solar19 = new Solar("Solar19       ", 33, 3000000, 1500000, 260000,
                2000000, 400000, 800000, 2750000, 12750000, 2550000, 2550000);
        este.add(solar19);

        CajaComunidad caja2 = new CajaComunidad("Caja          ",34,banca);
        este.add(caja2);

        Solar solar20 = new Solar("Solar20       ", 35, 3200000, 1600000, 280000,
                2000000, 400000, 800000, 3000000, 14000000, 2800000, 2800000);
        este.add(solar20);

        Transporte trans4 = new Transporte("Trans4        ", 36, banca);
        este.add(trans4);

        Suerte suerte2=new Suerte("Suerte        ",37,banca);
        este.add(suerte2);

        Solar solar21 = new Solar("Solar21       ", 38, 3500000, 1750000, 350000,
                2000000, 400000, 800000, 3250000, 17000000, 3400000, 3400000);
        este.add(solar21);

        Especial Imp2= new Especial(4,"Imp2          ",39,Valor.IMPUESTO,banca);
        este.add(Imp2);

        Solar solar22 = new Solar("Solar22       ", 40, 4000000, 2000000, 500000,
                2000000, 400000, 800000, 4250000, 20000000, 4000000, 4000000);
        este.add(solar22);

        // Grupos del lado este.
        Grupo verde = new Grupo(solar18, solar19, solar20, "Verde");
        solar18.setGrupo(verde);
        solar19.setGrupo(verde);
        solar20.setGrupo(verde);
        grupos.put("Verde", verde);

        Grupo azul = new Grupo(solar21, solar22, "Azul");
        solar21.setGrupo(azul);
        solar22.setGrupo(azul);
        grupos.put("Azul", azul);

        posiciones.add(este);
    }

    //Para imprimir el tablero, modificamos el metodo toString().
    @Override
    public String toString() {
        StringBuilder tablero = new StringBuilder();
        ArrayList<Casilla> sur = posiciones.get(0);
        ArrayList<Casilla> oeste = posiciones.get(1);
        ArrayList<Casilla> norte = posiciones.get(2);
        ArrayList<Casilla> este = posiciones.get(3);
        String lineaBaja="_____________________________________________________________________________________________"+
                "_________________________________________________________________________\n";
        String lineaAlta="---------------------------------------------------------------------------------------------" +
                "-------------------------------------------------------------------------\n";

        // Norte
        tablero.append(lineaBaja);
        for (Casilla casilla : norte) {
            tablero.append("|").append(casilla.toString());
        }
        tablero.append("|\n");
        tablero.append(lineaAlta);

        // Este y Oeste
        int tamanoLaterales = oeste.size();

        for (int i = 0; i < tamanoLaterales; i++) {
            //definiciones
            int indiceOeste= tamanoLaterales-1-i,indiceEste=i;
            String espacioCentral="                                                                                     " +
                    "                                                 ";
            //Casillas
            tablero.append("|").append(oeste.get(indiceOeste)).append("|").append(espacioCentral).append("|").append(este.get(indiceEste)).append("|\n");

            //separadores
            if(i<tamanoLaterales-1) {
                tablero.append("|--------------|").append(espacioCentral).append("|--------------|\n");
            }
        }

        // Sur
        tablero.append(lineaBaja);
        for(int i=sur.size()-1; i>=0; i--) {
            tablero.append("|").append(sur.get(i).toString());
        }
        tablero.append("|\n");
        tablero.append(lineaAlta);

        return tablero.toString();
    }
    
    //Metodo usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre){
        ArrayList<Casilla> sur = posiciones.get(0);
        ArrayList<Casilla> oeste = posiciones.get(1);
        ArrayList<Casilla> norte = posiciones.get(2);
        ArrayList<Casilla> este = posiciones.get(3);
        int tamLat = oeste.size();
        int tamTop =  norte.size();

        for(Casilla CasS : sur) {
            String nom = CasS.getNombre().trim().toLowerCase();
            if(nom.equals(nombre.toLowerCase())) {
                return CasS;
            }
        }
        for (Casilla CasN : norte) {
            String nom = CasN.getNombre().trim().toLowerCase();
            if (nom.equals(nombre.toLowerCase())) {
                return CasN;
            }
        }
        for (Casilla CasW : oeste) {
            String nom = CasW.getNombre().trim().toLowerCase();
            if (nom.equals(nombre.toLowerCase())) {
                return CasW;
            }
        }
        for(Casilla CasE : este) {
            String nom = CasE.getNombre().trim().toLowerCase();
            if(nom.equals(nombre.toLowerCase())) {
                return CasE;
            }
        }
        return null;
    }

}

