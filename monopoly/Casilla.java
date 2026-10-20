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
    private boolean hipotecada; //atributo que dice si una casilla esta hipotecada o no

    // Atributos adicionales para saber el numero de edificios y su ID
    private int numeroCasas = 0;
    private int numeroHoteles = 0;
    private int numeroPiscinas = 0;
    private int numeroPistas = 0;
    private ArrayList<String> edificios = new ArrayList<>();

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
    public String getNombre() {return nombre;}
    public String getTipo() {return tipo;}
    public float getValor() {return valor;}
    public int getPosicion() {return posicion;}
    public Jugador getDuenho() {return duenho;}
    public Grupo getGrupo() {return grupo;}
    public float getImpuesto() {return impuesto;}
    public float getHipoteca() {return hipoteca;}
    public ArrayList<Avatar> getAvatares() {return avatares;}
    public float getPrecioCasa() {return precioCasa;}
    public float getPrecioHotel() {return precioHotel;}
    public float getPrecioPiscina() {return precioPiscina;}
    public float getPrecioPista() {return precioPista;}
    public float getAlquilerCasa() {return alquilerCasa;}
    public float getAlquilerHotel() {return alquilerHotel;}
    public float getAlquilerPiscina() {return alquilerPiscina;}
    public float getAlquilerPista() {return alquilerPista;}
    public boolean getHipotecada() {return hipotecada;}
    public int getNumeroCasas() { return numeroCasas;}
    public int getNumeroHoteles() {return numeroHoteles;}
    public int getNumeroPiscinas() {return numeroPiscinas;}
    public int getNumeroPistas() {return numeroPistas;}
    public ArrayList<String> getEdificios() {return edificios;}

    // SETTERS
    public void setNombre(String n) {this.nombre = n;}
    public void setTipo(String t) {this.tipo = t;}
    public void setValor(int v) {this.valor = v;}
    public void setPosicion(int p) {this.posicion = p;}
    public void setDuenho(Jugador d) {this.duenho = d;}
    public void setGrupo(Grupo g) {this.grupo = g;}
    public void setImpuesto(float i) {this.impuesto = i;}
    public void setHipoteca(float h) {this.hipoteca = h;}
    public void setAvatares(ArrayList<Avatar> a) {this.avatares = a;}
    public void setPrecioCasa(float p) {this.precioCasa = p;}
    public void setPrecioHotel(float p) {this.precioHotel = p;}
    public void setPrecioPiscina(float p) {this.precioPiscina = p;}
    public void setPrecioPista(float p) {this.precioPista = p;}
    public void setAlquilerCasa(float a) {this.alquilerCasa = a;}
    public void setAlquilerHotel(float a) {this.alquilerHotel = a;}
    public void setAlquilerPiscina(float a) {this.alquilerPiscina = a;}
    public void setAlquilerPista(float a) {this.alquilerPista = a;}
    public void setHipotecada(boolean b) {this.hipotecada = b;}
    public void setNumeroCasas(int c) {this.numeroCasas = c;}
    public void setNumeroHoteles(int h) {this.numeroHoteles = h;}
    public void setNumeroPiscinas(int p) {this.numeroPiscinas = p;}
    public void setNumeroPistas(int p) {this.numeroPistas = p;}
    public void setEdificios(ArrayList<String> e) {this.edificios = e;}

    //Metodo utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        if (this.avatares == null) {this.avatares = new ArrayList<>();}
        this.avatares.add(av);
    }
    //Metodo utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        if (this.avatares != null) {this.avatares.remove(av);}
    }

    /*Metodo para evaluar qué hacer en una casilla concreta. Parámetros:
    * - Jugador cuyo avatar está en esa casilla.
    * - La banca (para ciertas comprobaciones).
    * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
    * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
    * en caso de no cumplirlas.
    public boolean evaluarCasilla(Jugador actual, Casilla destino, int sumaDados) {
    }
     */

    /*Metodo usado para comprar una casilla determinada. Parámetros:
     * - Jugador que solicita la compra de la casilla.
     * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if (this.getDuenho().getAvatar().getId().equals(banca.getAvatar().getId())) {
            if (solicitante.sumarFortuna((-1) * this.getValor())) {
                banca.eliminarPropiedad(this);
                solicitante.anhadirPropiedad(this);
                this.setDuenho(solicitante);
                solicitante.sumarGastos(this.getValor());
                System.out.println("el jugador " + solicitante.getNombre() + " ha comprado la casilla " + this.getNombre().trim() + "\n");
            }
        } else {System.out.println("Esa casilla ya tiene duenho");}
    }

    /*Metodo para añadir valor a una casilla. Utilidad:
     * - Sumar valor a la casilla de parking.
     * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
     * Este metodo toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
        valor += suma;
    }

    /*Metodo para mostrar información sobre una casilla.
     * Devuelve una cadena con información específica de cada tipo de casilla.*/
    /*Está pensado para devolver la información general e identificativa
     * de cualquier casilla (nombre, tipo, propietario, valor actual, etc.).*/
    public String infoCasilla() {
        return this.toString(this.tipo);
    }

    /* Metodo para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    /*Está pensado para filtrar y formatear la información únicamente de las casillas
     * que están disponibles para comprar (o mostrar los datos financieros específicos de venta).*/
    public String casEnVenta(String tipo) {
        tipo = tipo.trim().toLowerCase();
        return switch (tipo) {
            case "solar" -> ("tipo: solar\ngrupo: " + getGrupo() + "\nvalor: " + getValor());
            case "transporte" -> ("tipo: transporte\nvalor: " + getValor());
            case "servicio" -> ("tipo: servicio\nvalor: " + getValor());
            default -> ""; // Por seguridad, aunque con el filtro del menú nunca entrará aquí
        };
    }

    // Metodo para obtener los colores de cada grupo para luego poder imprimirlos
    public String obtenerCodigoColor() {
        // Si la casilla no pertenece a ningún grupo (por ejemplo, Salida, Cárcel, etc.)
        if (this.grupo == null || this.grupo.getColorGrupo() == null) {return Valor.RESET;}

        String color = this.grupo.getColorGrupo().toLowerCase();

        return switch (color) {
            case "negro" -> Valor.BLACK;
            case "rojo" -> Valor.RED;
            case "verde" -> Valor.GREEN;
            case "amarillo" -> Valor.YELLOW;
            case "azul" -> Valor.BLUE;
            case "rosa", "magenta" -> Valor.PURPLE;
            case "cian", "celeste" -> Valor.CYAN;
            case "blanco" -> Valor.WHITE;
            case "marron", "marrón" -> Valor.BROWN;
            case "naranja" -> Valor.ORANGE;
            default -> Valor.RESET;
        };
    }



    public boolean puedoEdificar(String tipo) {
        if(this.getHipotecada()) {
            return false;
        }

        if (this.grupo == null || this.duenho == null || this.duenho.getNombre().equalsIgnoreCase("banca")) {
            return false;
        }
        ArrayList<Casilla> casillasGrupo = this.grupo.getMiembros();
        for (int i = 0; i < casillasGrupo.size(); i++) {
            Casilla c = casillasGrupo.get(i);
            // Si alguna casilla del grupo no la posee este mismo dueño, no puede edificar
            if (c.getDuenho() == null || !c.getDuenho().equals(this.duenho)) {
                return false;
            }
        }
        return switch (tipo.toLowerCase()) {
            case "casa" ->
                // Se construyen si no hay hotel y hay menos de 4 casas
                    this.numeroHoteles == 0 && this.numeroCasas < 4;
            case "hotel" ->
                // Se puede construir exactamente 1 hotel si hay 4 casas y 0 hoteles
                    this.numeroCasas == 4 && this.numeroHoteles == 0;
            case "piscina" ->
                // Requiere tener exactamente 1 hotel y no tener piscina
                    this.numeroHoteles == 1 && this.numeroPiscinas == 0;
            case "pista" ->
                // Requiere tener exactamente 1 piscina y no tener pista
                    this.numeroPiscinas == 1 && this.numeroPistas == 0;
            default -> false;
        };
    }

    public void anhadirEdificio(String tipo) {
        String idEdificio = switch (tipo.toLowerCase()) {
            case "casa" -> {
                this.setNumeroCasas(getNumeroCasas() + 1);
                yield "casa-" + getNumeroCasas();
            }
            case "hotel" -> {
                this.setNumeroHoteles(getNumeroHoteles() + 1);
                this.setNumeroCasas(0);
                edificios.remove("casa-1");
                edificios.remove("casa-2");
                edificios.remove("casa-3");
                edificios.remove("casa-4");
                yield "hotel-" + getNumeroHoteles();
            }
            case "piscina" -> {
                this.setNumeroPiscinas(getNumeroPiscinas() + 1);
                yield "piscina-" + getNumeroPiscinas();
            }
            case "pista" -> {
                this.setNumeroPistas(getNumeroPistas() + 1);
                yield "pista-" + getNumeroPistas();
            }
            default -> "";
        };
        edificios.add(idEdificio);
    }

    public String infoEdificios() {
        StringBuilder resultado = new StringBuilder();

        if (this.tipo.equalsIgnoreCase("solar") && !this.duenho.getNombre().equalsIgnoreCase("banca")) {

            for (String idEdificio : this.edificios) {
                float coste = 0;

                // Determinamos el coste según el tipo de edificio en la ID
                if (idEdificio.startsWith("casa")) {
                    coste = this.precioCasa;
                } else if (idEdificio.startsWith("hotel")) {
                    coste = this.precioHotel;
                } else if (idEdificio.startsWith("piscina")) {
                    coste = this.precioPiscina;
                } else if (idEdificio.startsWith("pista")) {
                    coste = this.precioPista;
                }

                resultado.append("{\n" + "  id: ").append(idEdificio).append(",\n").append("  propietario: ")
                        .append(this.duenho.getNombre()).append(",\n").append("  casilla: ").append(this.nombre.trim()).append(",\n").append("  grupo: ").append(this.grupo.getColorGrupo()).append(",\n").append("  coste: ").append((int) coste).append("\n").append("},\n");
            }
        }

        return resultado.toString();
    }

    public void hipotecar(Jugador solicitante){
        String tipo;
        if(this.tipo==null){tipo="";}
        else{tipo = this.tipo.trim().toLowerCase();}
        String nombreCasilla = this.nombre.trim();
        if(!this.tipo.equalsIgnoreCase("solar") && !this.tipo.equalsIgnoreCase("transporte") && !this.tipo.equalsIgnoreCase("servicio")){
            System.out.println(solicitante.getNombre() + " no puede hipotecar " + nombreCasilla + ". No es una propiedad hipotecable.\n");
            return;
        }
        if(!this.duenho.equals(solicitante)){
            System.out.println(solicitante.getNombre() + " no puede hipotecar " + nombreCasilla + ". No es su propiedad.\n");
            return;
        }
        if(this.hipotecada){
            System.out.println(solicitante.getNombre() + " no puede hipotecar " + nombreCasilla + ". Ya está hipotecada.\n");
            return;
        }
        if(tipo.equals("solar")&& (numeroCasas>0 || numeroHoteles>0 || numeroPistas>0 || numeroPiscinas>0)){
            System.out.println(solicitante.getNombre() + " no puede hipotecar " + nombreCasilla + ". Debe vender antes los edificios.\n");
            return;
        }
        setHipotecada(true);
        solicitante.sumarFortuna(getHipoteca());
        String mensaje= solicitante.getNombre() + " recibe " + (long) this.hipoteca +  "€ por la hipoteca de " + nombreCasilla + ".\n";
        if (tipo.equalsIgnoreCase("solar") && this.grupo != null) {
            mensaje += "No puede recibir alquileres ni edificar en el grupo " + this.grupo.getColorGrupo() + ".";
        } else {
            mensaje += "No puede recibir alquileres por esta propiedad.";
        }
        System.out.println(mensaje);
    }

    public void deshipotecar(Jugador solicitante){
        //comprobamos si la propiedad esta hipotecada
        if(!getHipotecada()){
            System.out.println(solicitante.getNombre() +" no puede deshipotecar "+getNombre().trim()+", no esta hipotecada\n");
            return;
        }
        //comprobamos si es un tipo de casilla comprable
        String tipoCasilla=this.getTipo().toLowerCase();
        if(!tipoCasilla.equals("solar")&&!tipoCasilla.equals("transporte")&&!tipoCasilla.equals("servicio")){
            System.out.println(solicitante.getNombre() +" no puede deshipotecar "+getNombre().trim()+", no es una casilla hipotecable.");
            return;
        }
        //comprobamos que el duenho de la casilla sea el jugador solicitante
        if(!getDuenho().equals(solicitante)){
            System.out.println(solicitante.getNombre() +" no puede deshipotecar "+getNombre().trim()+", no es duenho\n");
            return;
        }

        if(solicitante.sumarFortuna((-1)*getHipoteca())){
            solicitante.sumarGastos(getHipoteca());
            setHipotecada(false);
            System.out.println(solicitante.getNombre() +" ha pagado "+getHipoteca()+" por deshipotecar la propiedad "+ getNombre());
        }
    }

    @Override
    public String toString() { // Para imprimir los nombres e ID de las casillas
        StringBuilder id = new StringBuilder();
        if (!avatares.isEmpty()) {
            id = new StringBuilder("&");
            for (Avatar avatare : avatares) {
                id.append(avatare.getId());}
        }
        //quitamos los espacios entre el fin del nombre y la casilla
        String CasillaLimpia = this.nombre.trim();

        //Calculamos cuantos espacios hay que poner para rellenar una casilla de 14 de largo
        int numEspacios = 14 - CasillaLimpia.length() - id.length();

        //creamos el String que tenga los espacios necesarios
        StringBuilder espacios = new StringBuilder();
        espacios.repeat(" ", Math.max(0, numEspacios));

        //Unimos todo
        String celda = CasillaLimpia + espacios + id;

        // Resetea el color ANSI al final para no teñir las siguientes casillas ni los bordes.
        return obtenerCodigoColor() + celda + Valor.RESET;
    }

    public String toString(String tipo) {
        tipo = tipo.trim().toLowerCase();
        //acabar de implementar os
        switch (tipo) {
            case "solar":
                return ("tipo: solar\ngrupo: " + getGrupo() + "\npropietario: " + getDuenho().getNombre() + "\nvalor: " + getValor() +
                        "\nalquiler: " + getImpuesto() + "\nvalor hotel: " + getPrecioHotel() + "\nvalor casa: " + getPrecioCasa() +
                        "\nvalor piscina: " + getPrecioPiscina() + "\nvalor pista de deporte: " + getPrecioPista() +
                        "\nalquiler casa: " + getAlquilerCasa() + "\nalquiler hotel: " + getPrecioHotel() + "\nalquiler piscina: " +
                        getAlquilerPista() + "\nalquiler pista de deporte: " + this.getAlquilerPista() + "\n\n");

            case "transporte":
                return ("tipo: transporte\nvalor: " + getValor() + "\n");

            case "impuesto":
                return ("tipo: impuesto\na pagar: " + getImpuesto() + "\n");

            case "servicio":
                return "tipo: servicio\nvalor: " + getValor() + "\n";

            //parking es de tipo especial
            case "especial":
                StringBuilder ret = new StringBuilder(("bote: " + getImpuesto() + "\n["));
                for (int i = 0; i < getAvatares().size(); i++) {
                    ret.append(this.getAvatares().get(i).getJugador().getNombre());}

                ret.append("]\n\n");
                return ret.toString();

            case "carcel":
                StringBuilder rt = new StringBuilder(("salir: " + Valor.COSTE_SALIR_CARCEL + "\n"));
                for (int i = 0; i < getAvatares().size(); i++) {
                    rt.append("[").append(getAvatares().get(i).getJugador().getNombre()).append(",").append(getAvatares().get(i).getJugador().getTiradasCarcel()).append("]");}
                rt.append("\n\n");
                return rt.toString();

            default:
                return " ";
        }
    }
}



