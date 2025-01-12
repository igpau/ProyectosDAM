import Classes.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    static String contingut;

    public static void main(String[] args) {
        inicial();
    }

    public static void inicial() {
        llegeixFitxer();
        comptadorSincro();
        comptadorAsincro();
    }

    private static void comptadorAsincro() {
        Thread aCounter = new ACounter(contingut);
        Thread eCounter = new ECounter(contingut);
        Thread iCounter = new ICounter(contingut);
        Thread oCounter = new OCounter(contingut);
        Thread uCounter = new UCounter(contingut);
        System.out.println("Inici sincronitzacio asincrona");
        long startTime = System.currentTimeMillis();
        aCounter.start();
        eCounter.start();
        iCounter.start();
        oCounter.start();
        uCounter.start();
        long endTime = System.currentTimeMillis();
        System.out.println("Final sincronitzacio asincrona");
        System.out.println("Temps de sincronitzacio asincrona: " + (endTime - startTime) + "ms");
    }

    private static void llegeixFitxer() {
        String rutaFitxer = "fitxer.txt"; // Substitueix per la ruta al teu fitxer
        try {
            contingut = Files.readString(Path.of(rutaFitxer));
        } catch (IOException e) {
            System.err.println("Error en llegir el fitxer: " + e.getMessage());
        }
    }


    public static void comptadorSincro() {
        Thread aCounter = new ACounter(contingut);
        Thread eCounter = new ECounter(contingut);
        Thread iCounter = new ICounter(contingut);
        Thread oCounter = new OCounter(contingut);
        Thread uCounter = new UCounter(contingut);
        System.out.println("Inici sincronitzacio sincrona");
        long startTime = System.currentTimeMillis();
        aCounter.start();
        eCounter.start();
        iCounter.start();
        oCounter.start();
        uCounter.start();

        try {
            aCounter.join();
            eCounter.join();
            iCounter.join();
            oCounter.join();
            uCounter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Final sincronitzacio sincrona");
        System.out.println("Temps de sincronitzacio sincrona: " + (endTime - startTime) + "ms\n\n");

    }
}