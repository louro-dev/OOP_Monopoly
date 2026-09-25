package partida;


public class Dado {
    //El dado solo tiene un atributo en nuestro caso: su valor.
    private int valor;

    //@ROcio para que entendas o cambio, para chamar a esto fariase Dado.setValor(hacerTirada), co cual nn faria falta poñer o
    //this.set valor dentro do hacer tirada

    //Metodo para simular lanzamiento de un dado: devolverá un valor aleatorio entre 1 y 6.
    public int hacerTirada() {
        return (int) (Math.random()*6) + 1; // Genera un valor aleatorio entre 0 y 5 y suma 1 (entre 1 y 6).
    }

    public void setValor(int numero) {
        if(numero<=6 && numero>0){
            this.valor=numero;
        }
    }

    public int getValor() {
        return this.valor;
    }
}
