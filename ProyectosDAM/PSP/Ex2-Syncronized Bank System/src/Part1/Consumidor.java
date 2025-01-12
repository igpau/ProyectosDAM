package Part1;

public class Consumidor extends OperacioBancaria{
    public Consumidor(CompteBancari compte) {
        super(compte);
    }
    @Override
    public void run() {
        while (true) {
            try {
                this.compte.retirar(quantitatAleatoria());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
