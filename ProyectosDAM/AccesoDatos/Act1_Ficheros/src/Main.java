import java.io.File;
        import java.io.IOException;
        import java.nio.file.Files;
        import java.nio.file.StandardCopyOption;
        import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Demanar la ruta del directori d'origen
        System.out.print("Introdueix la ruta del directori d'origen: ");
        String rutaOrigen = scanner.nextLine();

        // Demanar la ruta del directori de destí
        System.out.print("Introdueix la ruta del directori de destí: ");
        String rutaDesti = scanner.nextLine();

        // Copiar els arxius
        copiarArxius(rutaOrigen, rutaDesti);
    }

    public static void copiarArxius(String origen, String desti) {
        // Comprovar si el directori d'origen existeix
        File dirOrigen = new File(origen);
        if (!dirOrigen.exists()) {
            System.out.println("El directori origen no existeix.");
            return;
        }

        // Comprovar si el directori de destí existeix, si no, intentar crear-lo
        File dirDesti = new File(desti);
        if (!dirDesti.exists()) {
            boolean dirCreat = dirDesti.mkdirs(); // Crear el directori de destí
            if (dirCreat) {
                System.out.println("S'ha creat el directori destí.");
            } else {
                System.out.println("No s'ha pogut crear el directori destí.");
                return;
            }
        }

        // Copiar cada arxiu del directori d'origen al destí
        File[] arxius = dirOrigen.listFiles();
        if (arxius != null) {
            for (File arxiu : arxius) {
                File destFile = new File(dirDesti, arxiu.getName());
                try {
                    Files.copy(arxiu.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.out.println("Error al copiar l'arxiu: " + arxiu.getName());
                }
            }
            System.out.println("Còpia completada!");
        } else {
            System.out.println("El directori d'origen està buit o hi ha hagut un error.");
        }
    }
}
