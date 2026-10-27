package partida;

import java.util.ArrayList;

import monopoly.*;


public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private ArrayList<Casilla> propiedades;//Propiedades que posee el jugador.
    private int dobles = 0; // Entero para saber cuántas veces ha sacado dobles en los dados.

    //Atributos Gastos
    private double dineroInvertidoPropiedades;
    private double tasasImpuestosPagados;
    private double alquileresPagados;
    private double alquileresCobrados;
    private double dineroSalida;
    private double dineroPremiosInversionesParking;
    private int vecesCarcel;

    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        this.nombre = "banca";
        this.fortuna = Valor.FORTUNA_BANCA; // Se puede hacer porque FORTUNA_BANCA es público.
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<>();
        this.avatar = new Avatar();
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    private Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        this.nombre = nombre;
        this.fortuna = Valor.FORTUNA_INICIAL; // Se puede hacer porque FORTUNA_INICIAL es público.
        this.avatar = new Avatar(tipoAvatar,this,inicio,avCreados);
        // Inicializamos variables que van a guardar valores
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<>();

    }

    //Creamos un metodo que llame al constructor para poder tener control sobre los jugadores, ya que el constructor
    //esta obligado a no fallar
    public static Jugador newJugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados,ArrayList<Jugador> jugadores){
        if(avCreados.size()>=Valor.MAX_JUGADORES){
            System.out.println("\nmaximo de jugadores alcanzado\n");
            return null;
        }
        if(!Avatar.esTipoValido(tipoAvatar)){
            System.out.println("\ntipo de avatar invalido\n");
            return null;
        }
        Jugador j=new Jugador(nombre,tipoAvatar,inicio,avCreados);
        jugadores.add(j);
        return j;
    }

    // GETTERS
    public String getNombre() {return this.nombre;}
    public Avatar getAvatar() {return this.avatar;}
    public float getFortuna() {return this.fortuna;}
    public float getGastos() {return this.gastos;}
    public boolean getEnCarcel() {
        return this.enCarcel;
    }
    public int getTiradasCarcel() {
        return this.tiradasCarcel;
    }
    public int getVueltas() {
        return this.vueltas;
    }
    public ArrayList<Casilla> getPropiedades() {
        return this.propiedades;
    }
    public int getDobles() {return dobles;}

    //GETTERS Gastos
    public double getDineroInvertidoPropiedades() {return dineroInvertidoPropiedades;}
    public double getTasasImpuestosPagados() {return tasasImpuestosPagados;}
    public double getAlquileresPagados() {return alquileresPagados;}
    public double getAlquileresCobrados() {return alquileresCobrados;}
    public double getDineroSalida() {return dineroSalida;}
    public double getDineroPremiosInversionesParking() {return dineroPremiosInversionesParking;}
    public int getVecesCarcel() {return vecesCarcel;}

    // SETTERS
    public void setNombre(String n) {
        this.nombre = n;
    }
    public void setAvatar(Avatar a) {
        this.avatar = a;
    }
    public void setFortuna(float f) {
        this.fortuna = f;
    }
    public void setGastos(float g) {
        this.gastos = g;
    }
    public void setEnCarcel(boolean c) {
        this.enCarcel = c;
    }
    public void setTiradasCarcel(int t) {
        this.tiradasCarcel = t;
    }
    public void setVueltas(int v) {
        this.vueltas = v;
    }
    public void setPropiedades(ArrayList<Casilla> p) {this.propiedades = p;}
    public void setDobles(int d) {this.dobles = d;}

    //SETTERS Gastos
    public void setDineroInvertidoPropiedades(double dineroInvertidoPropiedades) {this.dineroInvertidoPropiedades = dineroInvertidoPropiedades;}
    public void setTasasImpuestosPagados(double tasasImpuestosPagados) {this.tasasImpuestosPagados = tasasImpuestosPagados;}
    public void setAlquileresPagados(double alquileresPagados) {this.alquileresPagados = alquileresPagados;}
    public void setAlquileresCobrados(double alquileresCobrados) {this.alquileresCobrados = alquileresCobrados;}
    public void setDineroSalida(double dineroSalida) {this.dineroSalida = dineroSalida;}
    public void setDineroPremiosInversionesParking(double dineroPremiosInversionesParking) {this.dineroPremiosInversionesParking = dineroPremiosInversionesParking;}
    public void setVecesCarcel(int vecesCarcel) {this.vecesCarcel = vecesCarcel;}

    //Métodos gastos
    public void sumarInversionPropiedades(double cantidad) {this.dineroInvertidoPropiedades += cantidad;}
    public void sumarTasasImpuestos(double cantidad) {this.tasasImpuestosPagados += cantidad;}
    public void sumarAlquilerPagado(double cantidad) {this.alquileresPagados += cantidad;}
    public void sumarAlquilerCobrado(double cantidad) {this.alquileresCobrados += cantidad;}
    public void sumarDineroSalida(double cantidad) {this.dineroSalida += cantidad;}
    public void sumarPremiosInversionesParking(double cantidad) {this.dineroPremiosInversionesParking += cantidad;}
    public void incrementarVecesCarcel() {this.vecesCarcel++;}

    //Otros métodos:
    //Metodo para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        propiedades.add(casilla);
    }

    //Metodo para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        propiedades.remove(casilla);
    }

    //Metodo que comprueba si un jugador es solvente a la hora de realizar cambios en su fortuna
    private boolean esSolvente(float precio){
        if(this.getFortuna()<((-1)*precio)){
            System.out.println("Fondos insuficientes");
            return false;
        }
        return true;
    }

    //Metodo para añadir o quitar fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public boolean sumarFortuna(float valor) {
        if (valor<0){
            if(!this.esSolvente(valor)){
                return false;
            }
        }
        fortuna+=valor;
        return true;
    }

    //Metodo para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    //public void sumarGastos(float valor) {gastos+=Math.abs(valor);}

    /*Metodo para establecer al jugador en la cárcel.
    * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(Tablero tab) {
        this.enCarcel = true;
        this.tiradasCarcel=0;

        //obtenemos la posicion de la carcel
        Casilla prison = tab.encontrar_casilla("Carcel");

        //calcular cual deberia ser el valor de la tirada para caer en la carcel
        int actual = this.avatar.getLugar().getPosicion();

        int distancia;
        // Calculamos la nueva posición (1 a 40)
        if(actual>prison.getPosicion()){
            distancia = 40+(prison.getPosicion()-actual);
        }
        else{
            distancia = prison.getPosicion()-actual;
        }

        this.avatar.moverAvatarCarcel(tab.getPosiciones(), distancia);
        incrementarVecesCarcel();
    }

    public static Jugador encontrarJugador(String nombre,ArrayList<Jugador> jugadores){
        for (Jugador jugadore : jugadores) {
            if (jugadore.getNombre().equals(nombre)) {
                return jugadore;
            }
        }
        System.out.println("Jugador no encontrado");
        return null;
    }

    public int numeroDeTransportes() {
        int contador = 0;
        for (Casilla c : this.propiedades) {
            if ("transporte".equalsIgnoreCase(c.getTipo())) {
                contador++;
            }
        }
        return contador;
    }

    public int numeroDeServicios() {
        int contador = 0;
        for (Casilla c : this.propiedades) { // Sustituye 'propiedades' por el nombre de tu ArrayList de propiedades
            if ("servicio".equalsIgnoreCase(c.getTipo())) {
                contador++;
            }
        }
        return contador;
    }

    /* Metodo para declarar en bancarrota a un jugador usando un bucle for-each.
     * Reasigna el dueño de cada propiedad al acreedor y deja la fortuna en 0. */
    public void declararBancarrota(Jugador acreedor) {
        System.out.println("¡El jugador " + this.nombre + " no tiene fondos suficientes y se declara en BANCARROTA!");

        // Recorremos las propiedades del jugador con un for-each
        for (Casilla c : this.propiedades) {
            if (acreedor != null) {
                acreedor.anhadirPropiedad(c);
                c.setDuenho(acreedor);
                System.out.println("  -> La propiedad " + c.getNombre().trim() + " pasa a ser de " + acreedor.getNombre());
            }
        }

        // Vaciamos la lista completa del jugador de una sola vez al terminar el bucle
        this.propiedades.clear();

        // Se resetea la fortuna del jugador insolvente
        this.fortuna = 0;
    }


    public void Suerte(Menu m) {
        int id=((int)(Math.random()*7))+1;

        switch(id){
            case 1:
                System.out.println("""
                Decides hacer un viaje de placer.
                Avanza hasta Solar19.
                Si pasas por la salida, cobra 2.000.000€.
                """);
                Casilla solar = m.getTablero().encontrar_casilla("Solar19");
                int actual = this.avatar.getLugar().getPosicion();
                int distancia;
                if(actual>solar.getPosicion()){
                    distancia = 40+(solar.getPosicion()-actual);
                }
                else {
                    distancia = solar.getPosicion() - actual;
                }
                this.getAvatar().moverAvatar(m.getTablero().getPosiciones(),distancia);
                m.evaluarCasillaDestino(this, getAvatar().getLugar(),0);
                break;

            case 2:
                System.out.println("""
                        Los acreedores te persiguen por impago.
                        Ve a la Carcel.
                        Ve directamente sin pasar por la casilla de salida y sin cobrar los 2.000.000€.
                        """);
                this.encarcelar(m.getTablero());
                break;

            case 3:
                System.out.println("""
                        !Has ganado el bote de la loteria!
                        Recibe 1.000.000€.
                        """);
                sumarFortuna(1000000);
                sumarPremiosInversionesParking(1000000);
                break;

            case 4:
                System.out.println("""
                        Has sido elegido presidente de la junta directiva.
                        Paga a cada jugador 250.000€
                        """);
                float valorTotal=250000*m.getJugadores().size();

                sumarFortuna((-1)*valorTotal);
                sumarTasasImpuestos(valorTotal);

                for(int i=0;i<m.getJugadores().size();i++){
                    m.getJugadores().get(i).sumarFortuna(250000);
                    m.getJugadores().get(i).sumarPremiosInversionesParking(250000);
                }
                break;

            case 5:
                System.out.println("""
                        ¡Hora punta de trafico!
                        Retrocede 3 casillas
                        """);
                this.getAvatar().moverAvatar(m.getTablero().getPosiciones(),-3);
                m.evaluarCasillaDestino(this, getAvatar().getLugar(),0);
                break;

            case 6:
                System.out.println("""
                        Te multan por usar el movil mientras conduces.
                        Paga 150.000€.
                        """);
                sumarFortuna((-1)*150000);
                sumarTasasImpuestos(150000);
                break;

            case 7:
                System.out.println("""
                        Avanza hasta la casilla de transporte mas cercana.
                        Si no tiene duenho, puedes comprarla.
                        Si tiene duenho, paga al duenho el doble del precio normal.
                        """);
                while(!this.getAvatar().getLugar().getTipo().equals("transporte"))
                {
                    this.getAvatar().moverAvatar(m.getTablero().getPosiciones(),1);
                }

                Jugador duenhoTransporte=getAvatar().getLugar().getDuenho();
                if (duenhoTransporte != null && !duenhoTransporte.equals(m.getBanca()) && !duenhoTransporte.equals(this)) {
                    int numTransportes = duenhoTransporte.numeroDeTransportes();
                    float alquiler = getAvatar().getLugar().getImpuesto() * numTransportes;

                    if(!this.sumarFortuna((-2)*alquiler)){
                        m.setSolvente(false);
                        this.declararBancarrota(duenhoTransporte);
                        break;
                    }
                    this.sumarAlquilerPagado(alquiler);

                    duenhoTransporte.sumarFortuna(alquiler);

                    duenhoTransporte.sumarAlquilerCobrado(alquiler);
                    System.out.printf("""
                                    Se han pagado %.0f€ de transporte a %s (posee %d transporte/s).
                                    Se ha pagado el doble del alquiler.
                                    """,
                                alquiler*2, duenhoTransporte.getNombre(), numTransportes);
                }
                break;
        }
    }

    // Es el toString de listar jugadores y describir jugador turno
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Nombre del jugador: ").append(this.getNombre())
                .append("\nAvatar del jugador: ").append(this.getAvatar())
                .append("\nFortuna del jugador: ").append(String.format(java.util.Locale.of("es", "ES"), "%,.0f", this.getFortuna()))
                .append("\nGastos totales: ").append(String.format(java.util.Locale.of("es", "ES"), "%,.0f", this.getGastos()))
                .append("\nEsta encarcelado?: ").append(this.getEnCarcel())
                .append("\nVueltas al tablero: ").append(this.getVueltas())
                .append("\nLista de propiedades: [");

        // Bucle para imprimir propiedades con StringBuilder
        for (int i = 0; i < getPropiedades().size(); i++) {
            sb.append(this.getPropiedades().get(i).getNombre().trim());
            if (i < getPropiedades().size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");

        // Listar edificios del jugador
        sb.append("\nLista de edificios: [");
        boolean primerEdificio = true;

        // Recorremos la lista de casillas (propiedades)
        for (int i = 0; i < getPropiedades().size(); i++) {
            Casilla c = getPropiedades().get(i);

            // Comprobamos si la casilla actual tiene edificios
            if (c.getEdificios() != null && !c.getEdificios().isEmpty()) {
                // Recorremos la lista de edificios de esta casilla
                for (int j = 0; j < c.getEdificios().size(); j++) {
                    if (!primerEdificio) {
                        sb.append(", ");
                    }
                    sb.append(c.getEdificios().get(j)).append(" (").append(c.getNombre().trim()).append(")");                    primerEdificio = false;
                }
            }
        }
        sb.append("]");


        return sb.toString();
    }

    // Es el toString de describir jugador
    public String toString(String nombre) {
        StringBuilder sb = new StringBuilder();

        sb.append("Nombre: ").append(nombre)
                .append("\nAvatar: ").append(this.getAvatar().getId())
                .append("\nFortuna: ").append(String.format(java.util.Locale.of("es", "ES"), "%,.0f", this.getFortuna()))
                .append("\nPropiedades: [");

        // Bucle para imprimir propiedades
        for (int i = 0; i < getPropiedades().size(); i++) {
            sb.append(this.getPropiedades().get(i).getNombre().trim());
            if (i < getPropiedades().size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]\nhipotecadas: [");

        // Propiedades hipotecadas
        boolean primerHipotecada = true;
        for (int i = 0; i < getPropiedades().size(); i++) {
            if (this.getPropiedades().get(i).getHipotecada()) {
                if (!primerHipotecada) {
                    sb.append(", ");
                }
                sb.append(this.getPropiedades().get(i).getNombre().trim());
                primerHipotecada = false;
            }
        }
        sb.append("]\n");

        // Listar edificios del jugador
        sb.append("edificios: [");
        boolean primerEdificio = true;

        for (int i = 0; i < this.getPropiedades().size(); i++) {
            Casilla c = this.getPropiedades().get(i);
            if (c.getEdificios() != null && !c.getEdificios().isEmpty()) {
                for (int j = 0; j < c.getEdificios().size(); j++) {
                    if (!primerEdificio) {
                        sb.append(", ");
                    }
                    sb.append(c.getEdificios().get(j));
                    primerEdificio = false;
                }
            }
        }
        sb.append("]\n\n");

        return sb.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador otro = (Jugador) o;
        return this.getAvatar().getId() != null && this.getAvatar().getId().equals(otro.getAvatar().getId());
    }

}
