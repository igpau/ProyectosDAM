package Part1;

public class MainPart1 {
    public static void main(String[] args) {
        Part1.CompteBancari compte = new CompteBancari("EUR", 1000);
        Part1.Productor productor = new Productor(compte);
        Part1.Consumidor consumidor=new Consumidor(compte);
        productor.start();
        consumidor.start();

    }
}