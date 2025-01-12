package Part2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CompteBancari {
    float saldo; // Saldo actual del compte
    String moneda; // Moneda associada al compte
    ReentrantLock operacio = new ReentrantLock(); // Objecte per sincronitzar l'accés a les dades del compte
    Condition saldoSuficient = operacio.newCondition(); // Condició per esperar fins que hi hagi prou saldo per retirar

    // Constructor: inicialitza la moneda i el saldo inicial del compte
    public CompteBancari(String moneda, float saldoInicial) {
        this.moneda = moneda;
        this.saldo = saldoInicial;
    }

    // Mètode per ingressar diners al compte
    public void ingressar(float quantitat) {
        try {
            operacio.lock(); // Bloqueja l'accés a les dades del compte
            saldo += quantitat; // Incrementa el saldo amb la quantitat ingressada
            System.out.println("Productor ingressa "+quantitat+" al compte.\nSaldo actual: "+saldo);
            saldoSuficient.signalAll(); // Notifica a tots els fils que estan esperant, en cas que el saldo hagi canviat
        } finally {
            operacio.unlock(); // Desbloqueja l'accés a les dades del compte
        }
    }

    // Mètode per retirar diners del compte
    public void retirar(float quantitat) throws InterruptedException {
        try {
            operacio.lock(); // Bloqueja l'accés a les dades del compte
            // Espera mentre no hi hagi prou saldo per retirar la quantitat desitjada
            if (saldo - quantitat < 0) {
                System.out.println("Et quedaras en descobert al restar el saldo " + getSaldo() + " amb el que vols treure " + quantitat);
                saldoSuficient.await(); // Suspèn el fil fins que hi hagi un canvi en el saldo
            }
            saldo -= quantitat; // Redueix el saldo amb la quantitat retirada
            System.out.println("Consumidor retira " + quantitat + " del compte.\nSaldo actual: " + saldo);
        } finally {
            operacio.unlock(); // Desbloqueja l'accés a les dades del compte
        }
    }

    public float getSaldo() {
        return saldo;
    }
}


