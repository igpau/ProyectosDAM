import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;

public class Main {
    private static final String HASH_ALGORITHM = "SHA-256";
    static Scanner scanner = new Scanner(System.in);
    static PrivateKey clauPrivada;
    static PublicKey clauPublica;
    static boolean clausGenerades = false;

    public static void main(String[] args){
        inici();
    }

    public static void inici() {
        while (true) {
            System.out.println("+-----------------------------------------+");
            System.out.println("|            e·ni-gm·a system             |");
            System.out.println("+-----------------------------------------+");
            System.out.println("| 1. Generar un nou parell de claus       |");
            System.out.println("| 2. Mostra la clau pública               |");
            System.out.println("| 3. Mostra la clau privada               |");
            System.out.println("| 4. Signar un document                   |");
            System.out.println("| 5. Verificar la signatura d'un document |");
            System.out.println("| 6. Sortir                               |");
            System.out.println("+-----------------------------------------+");
            System.out.print("Selecciona una opció: ");
            int opcio = scanner.nextInt();
            scanner.nextLine(); // netejar el buffer

            switch (opcio) {
                case 1:
                    generarClaus();
                    break;
                case 2:
                    mostraClauPublica();
                    break;
                case 3:
                    mostraClauPrivada();
                    break;
                case 4:
                    System.out.print("Introdueix la ruta del document a signar: ");
                    String rutaSignar = scanner.nextLine();
                    signarDocument(rutaSignar);
                    break;
                case 5:
                    System.out.print("Introdueix la ruta del document original: ");
                    String rutaOriginal = scanner.nextLine();
                    System.out.print("Introdueix la ruta del document amb la signatura: ");
                    String rutaSignatura = scanner.nextLine();
                    verificarDocument(rutaOriginal, rutaSignatura);
                    break;
                case 6:
                    System.out.println("Sortint de l'aplicació...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opció no vàlida. Torna-ho a intentar.");
            }

        }

    }


    /**
     * Verifica la signatura d'un document.
     *
     * @param originalPath
     * @param signaturePath
     */
    // Mètode per verificar la signatura d'un document.
    public static void verificarDocument(String originalPath, String signaturePath) {
        try {
            // Comprova si la clau pública existeix.
            if (clauPublica == null) {
                System.out.println("ERROR: La clau pública no ha estat proporcionada.");
                return;
            }

            // Llegeix el contingut del document original.
            byte[] document = Files.readAllBytes(Paths.get(originalPath));

            // Llegeix i decodifica la signatura en Base64.
            String signaturaBase64 = new String(Files.readAllBytes(Paths.get(signaturePath)));
            byte[] signatura = Base64.getDecoder().decode(signaturaBase64);

            // Inicialitza l'objecte Signature per verificar la signatura.
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(clauPublica);  // Configura la verificació amb la clau pública.
            signature.update(document);  // Afegir el contingut del document per verificar.

            // Verifica si la signatura és vàlida.
            if (signature.verify(signatura)) {
                System.out.println("Document verificat amb èxit.");
            } else {
                System.out.println("La verificació ha fallat. La signatura no coincideix.");
            }
        } catch (Exception e) {
            System.err.println("Error al verificar el document: " + e.getMessage());
        }
    }

    /**
     * Signa un document.
     *
     * @param rutaSignar
     */
    // Mètode per signar un document.
    public static void signarDocument(String rutaSignar) {
        try {
            // Comprova si s'han generat les claus.
            if (clausGenerades) {
                // Verifica si el document existeix.
                File documentFitxer = new File(rutaSignar);
                if (!documentFitxer.exists()) {
                    System.out.println("El document no existeix. Verifica la ruta.");
                    return;
                }

                // Llegeix el contingut del document.
                String contingutDocument = new String(Files.readAllBytes(Paths.get(rutaSignar)));

                // Crea una instància de Signature per generar la signatura.
                Signature signatura = Signature.getInstance("SHA256withRSA");
                signatura.initSign(clauPrivada);  // Configura la signatura amb la clau privada.

                signatura.update(contingutDocument.getBytes());  // Afegir el contingut del document.

                // Genera la signatura digital.
                byte[] signaturaDigital = signatura.sign();

                // Codifica la signatura en Base64.
                String signaturaBase64 = Base64.getEncoder().encodeToString(signaturaDigital);
                String fitxer = Base64.getEncoder().encodeToString(contingutDocument.getBytes());

                // Obté el nom del fitxer sense extensió.
                String newPath = StringNotExtension(rutaSignar);

                // Defineix el fitxer de sortida amb l'extensió .sig.
                File fitxerSortida = new File(newPath + ".sig");

                // Escriu la signatura al fitxer de sortida.
                FileOutputStream fos = new FileOutputStream(fitxerSortida);
                fos.write(signaturaBase64.getBytes());
                System.out.println("Document signat amb èxit. Signatura (en format base64):"+ signaturaBase64);
                fos.close();
            } else {
                System.out.println("No s'ha generat cap parell de claus. Genera un parell de claus primer.");
            }
        } catch (Exception e) {
            System.err.println("Error al signar el document: " + e.getMessage());
        }
    }

    /**
     * Mostra la clau privada codificada en Base64.
     */
    // Mètode per mostrar la clau privada codificada en Base64.
    private static void mostraClauPrivada() {
        if (clausGenerades) {
            String clauPrivadaBase64 = Base64.getEncoder().encodeToString(clauPrivada.getEncoded());
            String hashClauPrivada = generarHash(clauPrivadaBase64);  // Genera el hash de la clau privada.
            System.out.println("Clau privada: " + hashClauPrivada);  // Mostra el hash de la clau privada.
        } else {
            System.out.println("No s'ha generat cap parell de claus. Genera un parell de claus primer.");
        }
    }

    /**
     * Mostra la clau pública codificada en Base64.
     */
    // Mètode per mostrar la clau pública codificada en Base64.
    private static void mostraClauPublica() {
        if (clausGenerades) {
            String clauPublicaBase64 = Base64.getEncoder().encodeToString(clauPublica.getEncoded());
            String hashClauPublica = generarHash(clauPublicaBase64);  // Genera el hash de la clau pública.
            System.out.println("Clau pública: " + hashClauPublica);  // Mostra el hash de la clau pública.
        } else {
            System.out.println("No s'ha generat cap parell de claus. Genera un parell de claus primer.");
        }
    }

    /**
     * Genera un nou parell de claus RSA.
     */
    // Mètode per generar un nou parell de claus RSA.
    private static void generarClaus() {
        try {
            System.out.println("Inicialitzant el motor d'encriptació...");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");  // Crea una instància de KeyPairGenerator.
            keyGen.initialize(2048);  // Configura el generador per utilitzar claus de 2048 bits.
            System.out.println("INFO: Encara no s'ha generat cap parell de claus. Creant un de nou...");
            KeyPair parellDeClaus = keyGen.generateKeyPair();  // Genera el parell de claus.
            clauPublica = parellDeClaus.getPublic();  // Desa la clau pública.
            clauPrivada = parellDeClaus.getPrivate();  // Desa la clau privada.
            clausGenerades = true;  // Marca que les claus han estat generades.
            System.out.println("Parell de claus generat amb èxit!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera un hash utilitzant SHA-256.
     *
     * @param text
     * @return
     */
    // Funció per generar un hash utilitzant SHA-256.
    public static String generarHash(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);  // Crea una instància de MessageDigest.
            byte[] hash = digest.digest(text.getBytes("UTF-8"));  // Calcula el hash del text.
            return Base64.getEncoder().encodeToString(hash);  // Codifica el hash en Base64 i el retorna.
        } catch (Exception e) {
            System.out.println("Error en generar el hash: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obté el nom del fitxer sense extensió.
     *
     * @param filePath
     * @return
     */
    // Funció per obtenir el nom del fitxer sense extensió.
    private static String StringNotExtension(String filePath) {
        String notExtension = Paths.get(filePath)
                .getFileName()  // Obté el nom del fitxer amb l'extensió.
                .toString()  // Converteix l'objecte Path a String.
                .replaceFirst("[.][^.]+$", "");  // Elimina l'extensió utilitzant una expressió regular.
        return notExtension;  // Retorna el nom del fitxer sense extensió.
    }
}
