package Part1;

public class CompteBancari {

    private float saldo; // Saldo actual del compte
    private String moneda; // Moneda associada al compte

    // Constructor: inicialitza la moneda i el saldo inicial del compte
    public CompteBancari(String moneda, float saldoInicial) {
        this.moneda = moneda;
        this.saldo = saldoInicial;
    }

    // Mètode sincronitzat per ingressar diners al compte
    public synchronized void ingressar(float quantitat) {
        saldo += quantitat; // Incrementa el saldo amb la quantitat ingressada
        System.out.println("Productor ingressa "+quantitat+" al compte.\nSaldo actual: "+saldo);
        notifyAll(); // Notifica a tots els fils que estan esperant, en cas que el saldo hagi canviat
    }

    // Mètode sincronitzat per retirar diners del compte
    public synchronized void retirar(float quantitat) throws InterruptedException {
        // Espera mentre no hi hagi prou saldo per retirar la quantitat desitjada
        if (saldo - quantitat < 0) {
            System.out.println("Et quedaras en descobert al restar el saldo "+getSaldo()+" amb el que vols treure "+ quantitat);
            wait(); // Suspèn el fil fins que hi hagi un canvi en el saldo
        }
        saldo -= quantitat; // Redueix el saldo amb la quantitat retirada
        System.out.println("Consumidor retira "+quantitat+" del compte.\nSaldo actual: "+saldo);
    }

    // Mètode sincronitzat per obtenir el saldo actual
    public synchronized float getSaldo() {
        return saldo; // Retorna el saldo actual
    }

    // Mètode sincronitzat per obtenir la moneda del compte
    public synchronized String getMoneda() {
        return moneda; // Retorna la moneda associada al compte
    }
}
