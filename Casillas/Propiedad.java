package Casillas;

import partida.Jugador;

import java.util.Locale;

public abstract class Propiedad extends Casilla {

    private boolean hipotecada;//atributo que dice si una casilla esta hipotecada o no
    private float hipoteca; //Valor otorgado por hipotecar una casilla
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).

    public boolean getHipotecada() {return hipotecada;}
    public float getHipoteca() {return hipoteca;}
    public float getValor() {return valor;}

    public void setHipotecada(boolean b) {this.hipotecada = b;}
    public void setHipoteca(float h) {this.hipoteca = h;}
    public void setValor(int v) {this.valor = v;}

    public abstract void hipotecar(Jugador solicitante);

    public void deshipotecar(Jugador solicitante) {
        //comprobamos si la propiedad esta hipotecada
        if(!getHipotecada()){
            System.out.println(solicitante.getNombre() +" no puede deshipotecar "+getNombre().trim()+", no esta hipotecada\n");
            return;
        }
        //comprobamos que el duenho de la casilla sea el jugador solicitante
        if(!getDuenho().equals(solicitante)){
            System.out.println(solicitante.getNombre() +" no puede deshipotecar "+getNombre().trim()+", no es duenho\n");
            return;
        }

        if(solicitante.sumarFortuna((-1)*getHipoteca())){
            solicitante.sumarInversionPropiedades(getHipoteca());
            setHipotecada(false);
            System.out.println(solicitante.getNombre() +" ha pagado "+getHipoteca()+" por deshipotecar la propiedad "+ getNombre());
        }
        else {
            System.out.println(solicitante.getNombre() + " no dispone de suficiente dinero para deshipotecar " + getNombre().trim() + ".");
        }
    }

    /*Metodo para añadir valor a una casilla. Utilidad:
     * - Sumar valor a la casilla de parking.
     * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
     * Este metodo toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {valor += suma;}

    /*Metodo usado para comprar una casilla determinada. Parámetros:
     * - Jugador que solicita la compra de la casilla.
     * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if (this.getDuenho().getAvatar().getId().equals(banca.getAvatar().getId())) {
            if (solicitante.sumarFortuna((-1) * this.getValor())) {
                banca.eliminarPropiedad(this);
                solicitante.anhadirPropiedad(this);
                this.setDuenho(solicitante);
                solicitante.sumarInversionPropiedades(this.getValor());
                System.out.println("el jugador " + solicitante.getNombre() + " ha comprado la casilla " + this.getNombre().trim() + "\n");
            }
            else {
                System.out.println(solicitante.getNombre() + " no tiene suficiente dinero para comprar " + getNombre().trim() + ".");
            }
        } else {System.out.println("Esa casilla ya tiene duenho");}
    }

    /* Metodo para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    /*Está pensado para filtrar y formatear la información únicamente de las casillas
     * que están disponibles para comprar (o mostrar los datos financieros específicos de venta).*/
    public String casEnVenta(String tipo) {
        tipo = tipo.trim().toLowerCase();
        return switch (tipo) {
            case "transporte" -> ("tipo: transporte\nvalor: " + getValor());
            case "servicio" -> ("tipo: servicio\nvalor: " + getValor());
            default -> ""; // Por seguridad, aunque con el filtro del menú nunca entrará aquí
        };
    }

    // Metodo que realiza las acciones asociadas al comando 'listar enventa'.
    public void listarVenta() {
        System.out.println("\n----------CASILLAS EN VENTA----------\n");
        for(int i = 0; i < j.getBanca().getPropiedades().size(); i++){
            Propiedad prop = j.getBanca().getPropiedades().get(i);
            String tipo;
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
}
