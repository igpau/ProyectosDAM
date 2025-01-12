import java.io.BufferedReader; // Importem BufferedReader per llegir fitxers línia a línia
import java.io.File;           // Importem File per representar el fitxer
import java.io.FileReader;      // Importem FileReader per llegir caràcter per caràcter
import java.io.IOException;     // Importem IOException per capturar errors d'entrada/sortida

public class Main {
    public static void main(String[] args) {
        file(args);      // Crida a la funció que llegeix el fitxer caràcter a caràcter
        buffered(args);  // Crida a la funció que llegeix el fitxer línia a línia amb BufferedReader
    }

    public static void file(String[] args) {
        try {
            // Creem un objecte File que apunta al fitxer "test.txt"
            File file = new File("test.txt");

            // Obrim el fitxer per llegir-lo caràcter a caràcter amb FileReader
            FileReader fr = new FileReader(file);

            int caracter;  // Variable per emmagatzemar cada caràcter llegit

            // Mostrem un missatge per indicar que comencem la lectura amb FileReader
            System.out.print("Lectura amb FileReader:\n\n");

            // Bucle per llegir cada caràcter fins que no en quedi més (-1 indica final del fitxer)
            while ((caracter = fr.read()) != -1) {
                if (caracter == '}') {
                    caracter = '\n';  // Si trobem '}', el canviem per un salt de línia
                }

                // Convertim el valor numèric del caràcter a un caràcter i el mostrem
                System.out.print((char) caracter);
            }

            // Tanquem el FileReader per alliberar els recursos
            fr.close();
        } catch (IOException e) {
            // Si hi ha un error de lectura, es captura aquí i es mostra l'error
            e.printStackTrace();
        }
    }

    public static void buffered(String[] args) {
        try {
            // Creem un objecte File que apunta al fitxer "test.txt"
            File file = new File("test.txt");

            // Obrim el fitxer amb FileReader, i l'emboliquem dins un BufferedReader per llegir-lo línia a línia
            BufferedReader b = new BufferedReader(new FileReader(file));

            StringBuilder s = new StringBuilder();  // StringBuilder per acumular el contingut del fitxer
            String line;  // Variable per emmagatzemar cada línia llegida

            // Mostrem un missatge per indicar que comencem la lectura amb BufferedReader
            System.out.println("\n\nLectura amb BufferedReader:\n");

            // Bucle per llegir cada línia del fitxer
            while ((line = b.readLine()) != null) {
                s.append(line).append("\n");  // Afegim cada línia al StringBuilder, afegint també un salt de línia
            }

            // Tanquem el BufferedReader per alliberar els recursos
            b.close();

            // Mostrem el contingut acumulat al StringBuilder
            System.out.println(s.toString());
        } catch (IOException e) {
            // Si hi ha un error de lectura, es captura aquí i es mostra l'error
            e.printStackTrace();
        }
    }
}
