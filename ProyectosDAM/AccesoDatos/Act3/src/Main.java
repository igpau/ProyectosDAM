import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main f = new Main();
        f.inicia();
    }

    public void inicia() {
        Scanner scanner = new Scanner(System.in);
        String nomFitxer = null;
        int opcio = 0;

        while (opcio != 3) {
            if (nomFitxer == null) {
                System.out.print("Introdueix el nom del fitxer: ");
                nomFitxer = scanner.nextLine() + ".txt";
            }

            System.out.println("Que vols fer?");
            System.out.println("1. Que sobreescrigui el fitxer cada cop.");
            System.out.println("2. Que afegeixi al final.");
            System.out.println("3. Sortir");
            System.out.print("Selecciona una opció (1, 2 o 3): ");
            opcio = scanner.nextInt();
            scanner.nextLine(); // Consumir la nova línia després d'introduir el número

            switch (opcio) {
                case 1:
                    escriureFitxer(nomFitxer, false, scanner);  // false per sobreescriure el fitxer
                    break;
                case 2:
                    escriureFitxer(nomFitxer, true, scanner);   // true per afegir al final del fitxer
                    break;
                case 3:
                    System.out.println("Sortint del programa...\n");
                    break;
                default:
                    System.out.println("Opció no vàlida. Intenta-ho de nou.\n");
            }
        }
    }

    private void escriureFitxer(String nomFitxer, boolean afegir, Scanner scanner) {
        try (FileWriter fitxer = new FileWriter(nomFitxer, afegir)) {  // 'afegir' defineix si afegir o sobreescriure
            System.out.print("Escriu el text per guardar al fitxer: ");
            String text = scanner.nextLine();
            fitxer.write(text + "\n");
            System.out.println("El text s'ha desat correctament.\n");
        } catch (IOException e) {
            System.out.println("Error en escriure al fitxer: " + e.getMessage());
        }
    }
}
