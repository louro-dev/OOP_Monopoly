package monopoly;

import partida.Jugador;

import java.util.ArrayList;

public class Solar extends Propiedad {

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
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    private Tablero tablero;

    // Atributos adicionales para saber el numero de edificios y su ID
    private int numeroCasas = 0;
    private int numeroHoteles = 0;
    private int numeroPiscinas = 0;
    private int numeroPistas = 0;
    private ArrayList<String> edificios = new ArrayList<>();

    public float getPrecioCasa() {return precioCasa;}
    public float getPrecioHotel() {return precioHotel;}
    public float getPrecioPiscina() {return precioPiscina;}
    public float getPrecioPista() {return precioPista;}
    public float getAlquilerCasa() {return alquilerCasa;}
    public float getAlquilerHotel() {return alquilerHotel;}
    public float getAlquilerPiscina() {return alquilerPiscina;}
    public float getAlquilerPista() {return alquilerPista;}
    public int getNumeroCasas() { return numeroCasas;}
    public int getNumeroHoteles() {return numeroHoteles;}
    public int getNumeroPiscinas() {return numeroPiscinas;}
    public int getNumeroPistas() {return numeroPistas;}
    public ArrayList<String> getEdificios() {return edificios;}
    public Grupo getGrupo() {return grupo;}
    public Tablero getTablero() {return tablero;}

    public void setPrecioCasa(float p) {this.precioCasa = p;}
    public void setPrecioHotel(float p) {this.precioHotel = p;}
    public void setPrecioPiscina(float p) {this.precioPiscina = p;}
    public void setPrecioPista(float p) {this.precioPista = p;}
    public void setAlquilerCasa(float a) {this.alquilerCasa = a;}
    public void setAlquilerHotel(float a) {this.alquilerHotel = a;}
    public void setAlquilerPiscina(float a) {this.alquilerPiscina = a;}
    public void setAlquilerPista(float a) {this.alquilerPista = a;}
    public void setNumeroCasas(int c) {this.numeroCasas = c;}
    public void setNumeroHoteles(int h) {this.numeroHoteles = h;}
    public void setNumeroPiscinas(int p) {this.numeroPiscinas = p;}
    public void setNumeroPistas(int p) {this.numeroPistas = p;}
    public void setEdificios(ArrayList<String> e) {this.edificios = e;}
    public void setGrupo(Grupo g) {this.grupo = g;}
    public void setTablero(Tablero t) {this.tablero = t;}

    /*Metodo auxiliar para crear una casilla de tipo Solar con todos sus valores (precio, hipoteca,
     * alquiler base y costes/alquileres de edificación), evitando repetir el mismo bloque de código
     * 22 veces. Los valores se corresponden EXACTAMENTE con los indicados en el Apéndice I.
     * Además, registra el solar como propiedad inicial de la banca.
     */
    public Solar (String nombre, int posicion, int precio, float hipoteca, float alquiler, float precioCasa, float precioPiscina, float precioPista, float alquilerCasa,
                  float alquilerHotel, float alquilerPiscina, float alquilerPista) {
        this.setNombre(nombre);
        this.setPosicion(posicion);
        this.setValor(precio);
        this.setHipoteca(hipoteca);
        this.setImpuesto(alquiler); // Reutilizamos el atributo 'impuesto' de Casilla como alquiler base sin edificar.
        this.setPrecioCasa(precioCasa);
        this.setPrecioHotel(precioCasa); // En el Apéndice I, precio de hotel == precio de casa en todos los solares.
        this.setPrecioPiscina(precioPiscina);
        this.setPrecioPista(precioPista);
        this.setAlquilerCasa(alquilerCasa);
        this.setAlquilerHotel(alquilerHotel);
        this.setAlquilerPiscina(alquilerPiscina);
        this.setAlquilerPista(alquilerPista);
        tablero.getBanca().anhadirPropiedad(this);
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

    // Metodo para eliminar de la lista 'edificios' y decrementar los contadores
    private void eliminarEdificiosCasilla(String tipoEdificio, int cantidad) {
        switch (tipoEdificio) {
            case "casa":
                for (int i = 0; i < cantidad; i++) {
                    edificios.remove("casa-" + (this.getNumeroCasas() - i));
                }
                this.setNumeroCasas(this.getNumeroCasas() - cantidad);
                break;

            case "hotel":
                for (int i = 0; i < cantidad; i++) {
                    edificios.remove("hotel-" + (this.getNumeroHoteles() - i));
                }
                this.setNumeroHoteles(this.getNumeroHoteles() - cantidad);
                break;

            case "piscina":
                for (int i = 0; i < cantidad; i++) {
                    edificios.remove("piscina-" + (this.getNumeroPiscinas() - i));
                }
                this.setNumeroPiscinas(this.getNumeroPiscinas() - cantidad);
                break;

            case "pista":
                for (int i = 0; i < cantidad; i++) {
                    edificios.remove("pista-" + (this.getNumeroPistas() - i));
                }
                this.setNumeroPistas(this.getNumeroPistas() - cantidad);
                break;
        }
    }

    // Metodo auxiliar para pasar cualquier plural a singular ("casas" -> "casa", "hoteles" -> "hotel")
    private String normalizarTipoEdificio(String tipo) {
        String t = tipo.toLowerCase().trim();
        if (t.endsWith("es")) {
            return t.substring(0, t.length() - 2);
        } else if (t.endsWith("s")) {
            return t.substring(0, t.length() - 1);
        }
        return t;
    }


    public void venderEdificios(String tipo, int cantidad, Jugador jugador) {
        String tipoEdificio = normalizarTipoEdificio(tipo);
        int disponibles = 0;
        float precioUnidad = 0;

        switch (tipoEdificio) {
            case "casa":
                disponibles = this.getNumeroCasas();
                precioUnidad = this.getPrecioCasa();
                break;
            case "hotel":
                disponibles = this.getNumeroHoteles();
                precioUnidad = this.getPrecioHotel();
                break;
            case "piscina":
                disponibles = this.getNumeroPiscinas();
                precioUnidad = this.getPrecioPiscina();
                break;
            case "pista":
                disponibles = this.getNumeroPistas();
                precioUnidad = this.getPrecioPista();
                break;
            default:
                System.out.println("El tipo de edificio '" + tipo + "' no es válido.");
                return;
        }

        // Si no tiene ningún edificio del tipo indicado
        if (disponibles == 0) {
            System.out.println(jugador.getNombre() + " no tiene " + tipo + " en " + this.getNombre().trim() + ".");
            return;
        }

        // Si la cantidad pedida supera las disponibles, mostramos el aviso y cancelamos la operación
        if (cantidad > disponibles) {
            System.out.println("Solamente se pueden vender " + disponibles + " " + tipo +
                    ", recibiendo " + (disponibles * precioUnidad) + "€.");
            return; // No vende nada y sale directamente
        }

        // Si la cantidad es válida (cantidad <= disponibles), ejecutamos la venta completa
        eliminarEdificiosCasilla(tipoEdificio, cantidad);

        float totalGanado = cantidad * precioUnidad;
        jugador.sumarFortuna(totalGanado);

        int restantes = disponibles - cantidad;

        System.out.println(jugador.getNombre() + " ha vendido " + cantidad + " " + tipo +
                " en " + this.getNombre().trim() + ", recibiendo " + totalGanado +
                "€. En la propiedad queda " + restantes + " " + tipoEdificio + ".");
    }

    public String infoEdificios() {
        StringBuilder resultado = new StringBuilder();

        if (!this.getDuenho().getNombre().equalsIgnoreCase("banca")) {

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
                        .append(this.getDuenho().getNombre()).append(",\n").append("  casilla: ").append(this.getNombre().trim()).append(",\n").append("  grupo: ").append(this.grupo.getColorGrupo()).append(",\n").append("  coste: ").append((int) coste).append("\n").append("},\n");
            }
        }

        return resultado.toString();
    }

    public float alquilerPropiedad() {
        float alquilerTotal = this.getImpuesto(); // Alquiler básico

        // Sumamos los alquileres de los edificios que tenga la casilla
        if (this.numeroCasas > 0) {
            alquilerTotal += this.numeroCasas * this.alquilerCasa;
        }
        if (this.numeroHoteles > 0) {
            alquilerTotal += this.numeroHoteles * this.alquilerHotel;
        }
        if (this.numeroPiscinas > 0) {
            alquilerTotal += this.numeroPiscinas * this.alquilerPiscina;
        }
        if (this.numeroPistas > 0) {
            alquilerTotal += this.numeroPistas * this.alquilerPista;
        }

        return alquilerTotal;
    }

    // Funcion para construir un edificio
    public void construirEdificio(String tipoEdificio, int nActual, ArrayList<Jugador> jugadores) {
        Jugador jugadorActual=jugadores.get(nActual);
        if(jugadores.isEmpty()){
            System.out.println("\nNo hay jugadores creados\n");
            return;
        }
        // Obtener la casilla donde está ubicado el avatar del jugador
        Solar casillaActual = jugadorActual.getAvatar().SolarActual();

        // Validar que el solar pertenezca al jugador actual
        if (casillaActual.getDuenho() == null || !casillaActual.getDuenho().equals(jugadorActual)) {
            System.out.println("No puedes edificar en " + casillaActual.getNombre().trim() + " porque no eres el propietario.");
            return;
        }

        // Validar las reglas de edificacion
        if (!casillaActual.puedoEdificar(tipoEdificio)) {
            System.out.println("No se cumplen los requisitos para edificar un/a " + tipoEdificio + " en " + casillaActual.getNombre().trim() + ".");
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
                    " en la casilla " + casillaActual.getNombre().trim() + ".");
            return;
        }

        // Efectuar la edificación
        casillaActual.anhadirEdificio(tipoEdificio);

        // Actualizar gastos del jugador
        jugadorActual.sumarInversionPropiedades(precioEdificio);

        // Mensaje
        System.out.println("Se ha edificado un/a " + tipoEdificio + " en " + casillaActual.getNombre().trim() +
                ". La fortuna de " + jugadorActual.getNombre() +
                " se reduce en " + precioEdificio + "€.");
    }

    public boolean puedoEdificar(String tipo) {
        if(this.getHipotecada()) {
            return false;
        }

        if (this.grupo == null || this.getDuenho() == null || this.getDuenho().getNombre().equalsIgnoreCase("banca") || !this.grupo.esDuenhoGrupo(this.getDuenho())) {
            return false;
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

    //imprime informacion relevante para la venta de la casilla
    public String casEnVenta() {return "tipo: solar\ngrupo: " + getGrupo() + "\nvalor: " + getValor() ;}

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

    @Override
    public void hipotecar(Jugador solicitante) {
        String nombreCasilla = this.getNombre().trim();
        if(!this.getDuenho().equals(solicitante)){
            System.out.println(solicitante.getNombre() + " no puede hipotecar " + nombreCasilla + ". No es su propiedad.\n");
            return;
        }
        if(this.getHipotecada()){
            System.out.println(solicitante.getNombre() + " no puede hipotecar " + nombreCasilla + ". Ya está hipotecada.\n");
            return;
        }
        if(numeroCasas>0 || numeroHoteles>0 || numeroPistas>0 || numeroPiscinas>0){
            System.out.println(solicitante.getNombre() + " no puede hipotecar " + nombreCasilla + ". Debe vender antes los edificios.\n");
            return;
        }
        setHipotecada(true);
        solicitante.sumarFortuna(getHipoteca());
        solicitante.sumarInversionPropiedades((-1)*getHipoteca());

        String mensaje= solicitante.getNombre() + " recibe " + (long) this.getHipoteca()+  "€ por la hipoteca de " + nombreCasilla + ".\n";
        if (this.grupo != null) {
            mensaje += "No puede recibir alquileres ni edificar en el grupo " + this.grupo.getColorGrupo() + ".";
        } else {
            mensaje += "No puede recibir alquileres por esta propiedad.";
        }
        System.out.println(mensaje);
    }

    @Override
    public String toString() {
        return ("tipo: solar\ngrupo: " + getGrupo() + "\npropietario: " + getDuenho().getNombre() + "\nvalor: " + getValor() + "\nalquiler: " + getImpuesto() +
                "\nvalor hotel: " + getPrecioHotel() + "\nvalor casa: " + getPrecioCasa() + "\nvalor piscina: " + getPrecioPiscina() + "\nvalor pista de deporte: "
                + getPrecioPista() + "\nalquiler casa: " + getAlquilerCasa() + "\nalquiler hotel: " + getPrecioHotel() + "\nalquiler piscina: " +
                getAlquilerPista() + "\nalquiler pista de deporte: " + this.getAlquilerPista() + "\n\n");

    }
}