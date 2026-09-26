package partida;


public class Dado {
    //El dado solo tiene un atributo en nuestro caso: su valor.
    private int valor;

    //Metodo para simular lanzamiento de un dado: devolverá un valor aleatorio entre 1 y 6.
    public int hacerTirada() {
        this.valor = (int) (Math.random()*6) + 1; // Genera un valor aleatorio entre 0 y 5 y suma 1 (entre 1 y 6).
        return this.valor;
    }

    // SETTERS
    public void setValor(int numero) {
        this.valor = numero;
    }

    // GETTERS
    public int getValor() {
        return valor;
    }

}
