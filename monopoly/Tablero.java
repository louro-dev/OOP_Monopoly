package monopoly;

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

    /*Metodo auxiliar para crear una casilla de tipo Solar con todos sus valores (precio, hipoteca,
     * alquiler base y costes/alquileres de edificación), evitando repetir el mismo bloque de código
     * 22 veces. Los valores se corresponden EXACTAMENTE con los indicados en el Apéndice I.
     * Además, registra el solar como propiedad inicial de la banca.
     */
    private Casilla crearSolar(String nombre, int posicion, float precio, float hipoteca, float alquiler,
                               float precioCasa, float precioPiscina, float precioPista,
                               float alquilerCasa, float alquilerHotel, float alquilerPiscina, float alquilerPista) {
        Casilla solar = new Casilla(nombre, "Solar", posicion, precio, banca);
        solar.setHipoteca(hipoteca);
        solar.setImpuesto(alquiler); // Reutilizamos el atributo 'impuesto' de Casilla como alquiler base sin edificar.
        solar.setPrecioCasa(precioCasa);
        solar.setPrecioHotel(precioCasa); // En el Apéndice I, precio de hotel == precio de casa en todos los solares.
        solar.setPrecioPiscina(precioPiscina);
        solar.setPrecioPista(precioPista);
        solar.setAlquilerCasa(alquilerCasa);
        solar.setAlquilerHotel(alquilerHotel);
        solar.setAlquilerPiscina(alquilerPiscina);
        solar.setAlquilerPista(alquilerPista);
        banca.anhadirPropiedad(solar);
        return solar;
    }

    /*Metodo auxiliar para crear una casilla de Transporte o Servicio (mismo precio de compra:
     * 500.000€). El alquiler de transporte es fijo; el de servicio se calculará dinámicamente
     * más adelante (en evaluarCasilla), según el valor de los dados, así que aquí no se fija.
     */
    private Casilla crearTransporteServicio(String nombre, String tipo, int posicion) {
        Casilla casilla = new Casilla(nombre, tipo, posicion, Valor.PRECIO_TRANSPORTE_SERVICIO, banca);
        if (tipo.equals("Transporte")) {casilla.setImpuesto(Valor.ALQUILER_TRANSPORTE);}
        banca.anhadirPropiedad(casilla);
        return casilla;
    }
    
    //Metodo para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> norte = new ArrayList<>();

        norte.add(new Casilla("Parking       ", "Especial", 21, banca));

        Casilla solar12 = crearSolar("Solar12       ", 22, 2200000, 1100000, 180000,
                1500000, 300000, 600000, 2200000, 10500000, 2100000, 2100000);
        norte.add(solar12);

        norte.add(new Casilla("Suerte        ", "Suerte", 23, banca));

        Casilla solar13 = crearSolar("Solar13       ", 24, 2200000, 1100000, 180000,
                1500000, 300000, 600000, 2200000, 10500000, 2100000, 2100000);
        norte.add(solar13);

        Casilla solar14 = crearSolar("Solar14       ", 25, 2400000, 1200000, 200000,
                1500000, 300000, 600000, 2325000, 11000000, 2200000, 2200000);
        norte.add(solar14);

        norte.add(crearTransporteServicio("Trans3        ", "Transporte", 26));

        Casilla solar15 = crearSolar("Solar15       ", 27, 2600000, 1300000, 220000,
                1500000, 300000, 600000, 2450000, 11500000, 2300000, 2300000);
        norte.add(solar15);

        Casilla solar16 = crearSolar("Solar16       ", 28, 2600000, 1300000, 220000,
                1500000, 300000, 600000, 2450000, 11500000, 2300000, 2300000);
        norte.add(solar16);

        norte.add(crearTransporteServicio("Serv2         ", "Servicio", 29));

        Casilla solar17 = crearSolar("Solar17       ", 30, 2800000, 1400000, 240000,
                1500000, 300000, 600000, 2600000, 12000000, 2400000, 2400000);
        norte.add(solar17);

        norte.add(new Casilla("IrCarcel      ", "IrCarcel", 31, banca));

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

        sur.add(new Casilla("Salida        ", "Especial", 1, banca));

        Casilla solar1 = crearSolar("Solar1        ", 2, 600000, 300000, 20000,
                500000, 100000, 200000, 400000, 2500000, 500000, 500000);
        sur.add(solar1);

        sur.add(new Casilla("Caja          ", "Comunidad", 3, banca));

        Casilla solar2 = crearSolar("Solar2        ", 4, 600000, 300000, 40000,
                500000, 100000, 200000, 800000, 4500000, 900000, 900000);
        sur.add(solar2);

        sur.add(new Casilla("Imp1          ", 5, Valor.IMPUESTO, banca));

        sur.add(crearTransporteServicio("Trans1        ", "Transporte", 6));

        Casilla solar3 = crearSolar("Solar3        ", 7, 1000000, 500000, 60000,
                500000, 100000, 200000, 1000000, 5500000, 1100000, 1100000);
        sur.add(solar3);

        sur.add(new Casilla("Suerte        ", "Suerte", 8, banca));

        Casilla solar4 = crearSolar("Solar4        ", 9, 1000000, 500000, 60000,
                500000, 100000, 200000, 1000000, 5500000, 1100000, 1100000);
        sur.add(solar4);

        Casilla solar5 = crearSolar("Solar5        ", 10, 1200000, 600000, 80000,
                500000, 100000, 200000, 1250000, 6000000, 1200000, 1200000);
        sur.add(solar5);

        sur.add(new Casilla("Carcel        ", "Carcel", 11, banca));

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

        Casilla solar6 = crearSolar("Solar6        ", 12, 1400000, 700000, 100000,
                1000000, 200000, 400000, 1500000, 7500000, 1500000, 1500000);
        oeste.add(solar6);

        oeste.add(crearTransporteServicio("Serv1         ", "Servicio", 13));

        Casilla solar7 = crearSolar("Solar7        ", 14, 1400000, 700000, 100000,
                1000000, 200000, 400000, 1500000, 7500000, 1500000, 1500000);
        oeste.add(solar7);

        Casilla solar8 = crearSolar("Solar8        ", 15, 1600000, 800000, 120000,
                1000000, 200000, 400000, 1750000, 9000000, 1800000, 1800000);
        oeste.add(solar8);

        oeste.add(crearTransporteServicio("Trans2        ", "Transporte", 16));

        Casilla solar9 = crearSolar("Solar9        ", 17, 1800000, 900000, 140000,
                1000000, 200000, 400000, 1850000, 9500000, 1900000, 1900000);
        oeste.add(solar9);

        oeste.add(new Casilla("Caja          ", "Comunidad", 18, banca));

        Casilla solar10 = crearSolar("Solar10       ", 19, 1800000, 900000, 140000,
                1000000, 200000, 400000, 1850000, 9500000, 1900000, 1900000);
        oeste.add(solar10);

        Casilla solar11 = crearSolar("Solar11       ", 20, 2200000, 1000000, 160000,
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

        Casilla solar18 = crearSolar("Solar18       ", 32, 3000000, 1500000, 260000,
                2000000, 400000, 800000, 2750000, 12750000, 2550000, 2550000);
        este.add(solar18);

        Casilla solar19 = crearSolar("Solar19       ", 33, 3000000, 1500000, 260000,
                2000000, 400000, 800000, 2750000, 12750000, 2550000, 2550000);
        este.add(solar19);

        este.add(new Casilla("Caja          ", "Comunidad", 34, banca));

        Casilla solar20 = crearSolar("Solar20       ", 35, 3200000, 1600000, 280000,
                2000000, 400000, 800000, 3000000, 14000000, 2800000, 2800000);
        este.add(solar20);

        este.add(crearTransporteServicio("Trans4        ", "Transporte", 36));

        este.add(new Casilla("Suerte        ", "Suerte", 37, banca));

        Casilla solar21 = crearSolar("Solar21       ", 38, 3500000, 1750000, 350000,
                2000000, 400000, 800000, 3250000, 17000000, 3400000, 3400000);
        este.add(solar21);

        este.add(new Casilla("Imp2          ", 39, Valor.IMPUESTO, banca));

        Casilla solar22 = crearSolar("Solar22       ", 40, 4000000, 2000000, 500000,
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
        String tablero = "";
        ArrayList<Casilla> sur = posiciones.get(0);
        ArrayList<Casilla> oeste = posiciones.get(1);
        ArrayList<Casilla> norte = posiciones.get(2);
        ArrayList<Casilla> este = posiciones.get(3);
        String lineaBaja="_____________________________________________________________________________________________"+
                "_________________________________________________________________________\n";
        String lineaAlta="---------------------------------------------------------------------------------------------" +
                "-------------------------------------------------------------------------\n";

        // Norte
        tablero += lineaBaja;
        for(int i=0; i<norte.size(); i++) {
            tablero = tablero + "|" + norte.get(i).toString();
        }
        tablero += "|\n";
        tablero += lineaAlta;

        // Este y Oeste
        int tamanoLaterales = oeste.size();

        for (int i = 0; i < tamanoLaterales; i++) {
            //definiciones
            int indiceOeste= tamanoLaterales-1-i,indiceEste=i;
            String espacioCentral="                                                                                     " +
                    "                                                 ";
            //Casillas
            tablero+="|"+oeste.get(indiceOeste)+"|"
                    +espacioCentral+
                    "|"+este.get(indiceEste)+"|\n";

            //separadores
            if(i<tamanoLaterales-1) {
                tablero += "|--------------|"
                        + espacioCentral
                        + "|--------------|\n";
            }
        }

        // Sur
        tablero += lineaBaja;
        for(int i=sur.size()-1; i>=0; i--) {
            tablero+= "|" + sur.get(i).toString();
        }
        tablero += "|\n";
        tablero += lineaAlta;

        return tablero;
    }
    
    //Metodo usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre){
        ArrayList<Casilla> sur = posiciones.get(0);
         ArrayList<Casilla> oeste = posiciones.get(1);
        ArrayList<Casilla> norte = posiciones.get(2);
        ArrayList<Casilla> este = posiciones.get(3);
        int tamLat = oeste.size();
        int tamTop =  norte.size();

        for(int i=0; i<tamTop; i++) {
            String nom = sur.get(i).getNombre().trim().toLowerCase();
            if(nom.equals(nombre.toLowerCase())) {
                return sur.get(i);
            }
        }
        for(int i=0; i<tamTop; i++) {
            String nom = norte.get(i).getNombre().trim().toLowerCase();
            if(nom.equals(nombre.toLowerCase())) {
                return norte.get(i);
            }
        }
        for(int i = 0; i< tamLat; i++) {
            String nom = oeste.get(i).getNombre().trim().toLowerCase();
            if(nom.equals(nombre.toLowerCase())) {
                return oeste.get(i);
            }
        }
        for(int i=0; i<tamLat; i++) {
            String nom = este.get(i).getNombre().trim().toLowerCase();
            if(nom.equals(nombre.toLowerCase())) {
                return este.get(i);
            }
        }
        return null;
    }

}

