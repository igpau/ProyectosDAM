package Part2;

abstract class OperacioBancaria extends Thread{
    protected CompteBancari compte;

    public OperacioBancaria(CompteBancari compte) {
        this.compte = compte;
    }

    public float quantitatAleatoria() {
        return (float) (Math.random() * 1000);
    }


}
