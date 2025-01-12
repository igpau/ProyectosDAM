package Part2;


public class Productor extends OperacioBancaria {
    public Productor(CompteBancari compte) {
        super(compte);
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.compte.ingressar(quantitatAleatoria());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
