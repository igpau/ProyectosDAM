package Part1;

public abstract class OperacioBancaria extends Thread{

    protected CompteBancari compte;

    public OperacioBancaria(CompteBancari compte) {
        this.compte = compte;
    }
    public float quantitatAleatoria() {
        return (float) (Math.random() * 1000);
    }
}
