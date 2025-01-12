package cat.dam2.psp.act2.Classes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Base64;

public class XifratSimetric {
    // Especifica l'algoritme AES per encriptar i desencriptar
    private static final String ALGORITHM = "AES";
    // Especifica l'algoritme SHA-256 per generar el hash
    private static final String HASH_ALGORITHM = "SHA-256";

    // Genera una clau simètrica AES amb validació de la mida de la clau
    public static SecretKey generarClau(String key) throws Exception {
        // Converteix la clau de text (String) a bytes en format UTF-8
        byte[] keyBytes = key.getBytes("UTF-8");
        int keyLength = keyBytes.length;

        // Validació de la mida de la clau, ha de ser 16, 24 o 32 bytes
        if (keyLength != 16 && keyLength != 24 && keyLength != 32) {
            throw new Exception("La mida de la clau ha de ser de 16, 24 o 32 bytes");
        }

        // Realitza un hash de la clau per assegurar-se que té la longitud correcta (AES
        // admet 16, 24 o 32 bytes)
        MessageDigest sha = MessageDigest.getInstance(HASH_ALGORITHM);
        keyBytes = sha.digest(keyBytes);

        // Retorna la clau SecretKey específicament per AES (utilitza els bytes
        // processats per SHA-256)
        return new SecretKeySpec(keyBytes, 0, keyLength, ALGORITHM);
    }

    // Xifra o desxifra un fitxer depenent del mode (isEncrypting: true per xifrar,
    // false per desxifrar)
    public static String processarFitxer(String filePath, String key, boolean isEncrypting) throws Exception {
        // Genera la clau per AES a partir de la clau secreta
        key = ajustarClau(key);
        System.out.println("Clau ajustada: " + key);
        SecretKey secretKey = generarClau(key);

        // Llegeix el fitxer com a bytes
        File inputFile = new File(filePath); // Especifica el fitxer d'entrada
        FileInputStream inputStream = new FileInputStream(inputFile); // Obre un flux d'entrada per llegir el fitxer
        byte[] inputBytes = new byte[(int) inputFile.length()]; // Crea un array per emmagatzemar els bytes del fitxer
        inputStream.read(inputBytes); // Llegeix els bytes del fitxer
        inputStream.close(); // Tanca el flux d'entrada

        // Inicialitza el cipher per encriptar o desencriptar depenent del mode
        Cipher cipher = Cipher.getInstance("AES"); // Utilitza l'algoritme AES
        int cipherMode = isEncrypting ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE; // Decideix el mode en funció de si
                                                                                   // estem encriptant o desencriptant
        cipher.init(cipherMode, secretKey); // Inicialitza el cipher amb la clau secreta

        // Processa el fitxer (encripta o desencripta els bytes)
        byte[] processedBytes = cipher.doFinal(inputBytes); // Aplica el cipher als bytes del fitxer
        String path = treureExtensio(filePath);
        // Guarda el fitxer processat (amb extensió .encrypted per a fitxers encriptats
        // i .decrypted per a fitxers desencriptats)
        String processedFilePath = path + (isEncrypting ? ".encrypted" : ".decrypted"); // Decideix el nom del fitxer de
                                                                                        // sortida
        FileOutputStream outputStream = new FileOutputStream(processedFilePath); // Obre un flux de sortida per escriure
                                                                                 // el fitxer processat
        outputStream.write(processedBytes); // Escriu els bytes processats al fitxer de sortida
        outputStream.close(); // Tanca el flux de sortida

        return processedFilePath;
    }
    // Ajusta la clau perquè tingui una longitud de 16 bytes
    public static String ajustarClau(String key) {
        // Si la clau és més gran que 16, la trunquem
        if (key.length() > 16) {
            return key.substring(0, 16);
        }
        // Si la clau és més petita, la complementem amb espais
        StringBuilder paddedKey = new StringBuilder(key);
        while (paddedKey.length() < 16) {
            paddedKey.append(" "); // Omple amb espais
        }
        return paddedKey.toString(); // Retorna la clau ajustada
    }

    // Genera un hash per un missatge utilitzant SHA-256
    public static String generarStringHash(String missatge) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM); // Inicialitza MessageDigest per SHA-256
        byte[] hashBytes = digest.digest(missatge.getBytes()); // Calcula el hash del missatge
        return Base64.getEncoder().encodeToString(hashBytes); // Converteix el hash en una cadena Base64 per facilitar
                                                              // la seva visualització
    }

    // Genera un hash per un missatge utilitzant SHA-256
    public static String generatPathHash(String path) throws Exception {
        String missatge = Files.readAllLines(Paths.get(path)).toString();
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM); // Inicialitza MessageDigest per SHA-256
        byte[] hashBytes = digest.digest(missatge.getBytes()); // Calcula el hash del missatge
        return Base64.getEncoder().encodeToString(hashBytes); // Converteix el hash en una cadena Base64 per facilitar
                                                              // la seva visualització
    }

    public static String treureExtensio(String nomFitxer) {
        int posicio = nomFitxer.lastIndexOf(".");
        return (posicio == -1) ? nomFitxer : nomFitxer.substring(0, posicio);
    }

    public static String generarDigest(String path, String hash) throws IOException {
        FileOutputStream outputStreamDigest = new FileOutputStream(path + ".digest"); // Obre un flux de sortida per
                                                                                      // escriure el fitxer processat
        outputStreamDigest.write(hash.getBytes()); // Escriu els bytes processats al fitxer de sortida
        outputStreamDigest.close(); // Tanca el flux de sortida
        return path + ".digest";
    }

    // Compara el digest amb el fitxer desencriptat
    public static void compararHash(String hashDesencriptat, String pathFitxerDigest) throws Exception {
        try {
            // Llegeix el contingut del fitxer digest com a text
            String digestOriginal = Files.readString(Paths.get(pathFitxerDigest)).trim();

            System.out.println(digestOriginal);
            // Compara el hash generat amb el hash original
            if (hashDesencriptat.equals(digestOriginal)) {
                System.out.println("El missatge no ha estat alterat.");
            } else {
                System.out.println("ALERTA: El missatge ha estat alterat.");
            }
        } catch (IOException e) {
            System.err.println("Error llegint els fitxers: " + e.getMessage());
        }
    }

    // Funció per verificar el hash d'un text
    public static boolean verificarHashString(String text, String hash) throws Exception {
        String hashGenerat = generarStringHash(text);
        return hashGenerat != null && hashGenerat.equals(hash);
    }

}
