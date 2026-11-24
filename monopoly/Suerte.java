package monopoly;

import partida.Jugador;

import java.util.ArrayList;

public class Suerte extends Accion {

    int valor;

    public Suerte(String nombre, int posicion){
        setNombre(nombre);
        setPosicion(posicion);
    }

    //SETTERS
    void setValor(int val){
        if(val>0 && val<7){
            valor=val;
        }
    }

    //GETTERS
    int getValor(){
        return valor;
    }

    void actualizarValor(){
        setValor((int)(Math.random()*7)+1);
    }

    public void Suerte(Tablero tab, ArrayList<Jugador> jugadores, int nActual) {

        actualizarValor();
        Jugador actual=jugadores.get(nActual);


        switch(getValor()){
            case 1:
                System.out.println("""
                Decides hacer un viaje de placer.
                Avanza hasta Solar19.
                Si pasas por la salida, cobra 2.000.000€.
                """);
                Casilla solar = tab.encontrar_casilla("Solar19");
                int distancia;
                if(actual.getAvatar().getLugar().getPosicion()>solar.getPosicion()){
                    distancia = 40+(solar.getPosicion()-actual.getAvatar().getLugar().getPosicion());
                }
                else {
                    distancia = solar.getPosicion() - actual.getAvatar().getLugar().getPosicion();
                }
                actual.getAvatar().moverAvatar(tab.getPosiciones(),distancia);
                Juego.evaluarCasillaDestino(actual, actual.getAvatar().getLugar(),0);
                break;

            case 2:
                System.out.println("""
                        Los acreedores te persiguen por impago.
                        Ve a la Carcel.
                        Ve directamente sin pasar por la casilla de salida y sin cobrar los 2.000.000€.
                        """);
                Jugador.encarcelar(tab);
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

            default:
                System.out.println("Error");
                break;
        }
    }

}
