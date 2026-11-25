package Casillas;

import monopoly.*;
import partida.Jugador;

import java.util.ArrayList;

public class Suerte extends Accion {

    public Suerte(String nombre, int posicion,Jugador dueno){
        super(nombre,posicion,dueno);
    }

    public void suerte(Juego j) {

        actualizarValor();
        ArrayList<Jugador> jugadores = j.getJugadores();
        Jugador actual=jugadores.get(j.getTurno());
        Tablero tab=j.getTablero();


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
                j.evaluarCasillaDestino(actual, actual.getAvatar().getLugar(),0);
                break;

            case 2:
                System.out.println("""
                        Los acreedores te persiguen por impago.
                        Ve a la Carcel.
                        Ve directamente sin pasar por la casilla de salida y sin cobrar los 2.000.000€.
                        """);
                actual.encarcelar(tab);
                break;

            case 3:
                System.out.println("""
                        !Has ganado el bote de la loteria!
                        Recibe 1.000.000€.
                        """);
                actual.sumarFortuna(1000000);
                actual.sumarPremiosInversionesParking(1000000);
                break;

            case 4:
                System.out.println("""
                        Has sido elegido presidente de la junta directiva.
                        Paga a cada jugador 250.000€
                        """);
                float valorTotal=250000*jugadores.size();

                actual.sumarFortuna((-1)*valorTotal);
                actual.sumarTasasImpuestos(valorTotal);

                for (Jugador jug : jugadores) {
                    jug.sumarFortuna(250000);
                    jug.sumarPremiosInversionesParking(250000);
                }
                break;

            case 5:
                System.out.println("""
                        ¡Hora punta de trafico!
                        Retrocede 3 casillas
                        """);
                actual.getAvatar().moverAvatar(tab.getPosiciones(),-3);
                j.evaluarCasillaDestino(actual, actual.getAvatar().getLugar(),0);
                break;

            case 6:
                System.out.println("""
                        Te multan por usar el movil mientras conduces.
                        Paga 150.000€.
                        """);
                actual.sumarFortuna((-1)*150000);
                actual.sumarTasasImpuestos(150000);
                break;

            case 7:
                System.out.println("""
                        Avanza hasta la casilla de transporte mas cercana.
                        Si no tiene duenho, puedes comprarla.
                        Si tiene duenho, paga al duenho el doble del precio normal.
                        """);
                //revisar que funciona bien, getLugar devuelve tipo Casillam ves si funciona pedir clase y que devuelva la clase derivada que es
                while(!actual.getAvatar().getLugar().getClass().getSimpleName().equals("Transporte"))
                {
                    actual.getAvatar().moverAvatar(tab.getPosiciones(),1);
                }

                Jugador duenhoTransporte=actual.getAvatar().getLugar().getDuenho();
                if (duenhoTransporte != null && !duenhoTransporte.equals(j.getBanca()) && !duenhoTransporte.equals(actual)) {
                    int numTransportes = duenhoTransporte.numeroDeTransportes();
                    float alquiler = actual.getAvatar().getLugar().getImpuesto() * numTransportes;

                    if(!actual.sumarFortuna((-2)*alquiler)){
                        j.setSolvente(false);
                        actual.declararBancarrota(duenhoTransporte);
                        break;
                    }
                    actual.sumarAlquilerPagado(alquiler);

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

    @Override
    public String infoCasilla() {
        return "Tipo: suerte\nREaliza una accion aleatoria dentro de las acciones definidas\n";
    }
}
