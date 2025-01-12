package Part2;

public class MainPart2 {
    public static void main(String[] args) {
        CompteBancari compte = new CompteBancari("EUR", 1000);
        Productor productor = new Productor(compte);
        Consumidor consumidor=new Consumidor(compte);
        productor.start();
        consumidor.start();

    }
}