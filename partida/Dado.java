package partida;


public class Dado {
    //El dado solo tiene un atributo en nuestro caso: su valor.
    private int valor;



    //Metodo para simular lanzamiento de un dado: devolverá un valor aleatorio entre 1 y 6.
    public int hacerTirada() {
        this.valor = (int) (Math.random()*6) + 1; // Genera un valor aleatorio entre 0 y 5 y suma 1 (entre 1 y 6).
        return this.valor;
    }

    // CONSTRUCTOS
    public Dado(){this.valor =0;}

    // SETTERS
    public void setValor(int numero) {

        if(numero<7 && numero >0){this.valor = numero;}
        else{
            //si el valor del dado es invalido(se ha dado en la funcion de forzar un dado mayor que 6) se pone ese dado
            //a 0 para que no sume
            System.out.println("Valor invalido para el dado");
            this.valor=0;
        }
    }

    // GETTERS
    public int getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return " "+this.getValor();
    }
}
