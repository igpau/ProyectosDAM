package cat.dam2.psp.act2;

import cat.dam2.psp.act2.Classes.XifratSimetric;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        XifratSimetric xifrat = new XifratSimetric();
        // Bucle infinit perquè el menú continuï apareixent fins que l'usuari decideixi sortir.
        while (true) {
            // Imprimeix el menú a la consola.
            System.out.println("+------------------------------------+");
            System.out.println("|          e·ni-gm·a system          |");
            System.out.println("+------------------------------------+");
            System.out.println("| 1. Encriptar fitxer                |");
            System.out.println("| 2. Desencriptar fitxer             |");
            System.out.println("| 3. Generar un hash d'un missatge   |");
            System.out.println("| 4. Verificar el hash d'un missatge |");
            System.out.println("| 5. Sortir                          |");
            System.out.println("+------------------------------------+");
            System.out.println("Selecciona una opció: ");

            // Llegeix l'opció seleccionada per l'usuari i la guarda com a String.
            String opcio;
            int valor = 0;

            // Tracta d'interpretar l'opció com un número enter.
            try {
                opcio = scan.next();
                valor = Integer.parseInt(opcio);
            } catch (NumberFormatException e) {
                opcio = "_NO_";
            }

            // Comprova si l'entrada no és vàlida; si és així, mostra un missatge d'error i continua.
            if (opcio.equals("_NO_")) {
                System.out.println("Opció no vàlida");
                continue;
            } else {
                // Selecciona l'opció adequada segons el valor introduït per l'usuari.
                switch (valor) {
                    case 1:
                        // Encriptació d'un fitxer
                        System.out.println("Introdueix el camí del fitxer a encriptar: ");
                        String path = scan.next();
                        System.out.println("Introdueix la clau secreta: ");
                        String key = scan.next();

                        // Crida al mètode per processar (encriptar) el fitxer.
                        String encryptedFile = XifratSimetric.processarFitxer(path, key, true);

                        // Genera un hash del camí del fitxer.
                        String hash = xifrat.generatPathHash(path);

                        // Treu l'extensió del fitxer original.
                        path = xifrat.treureExtensio(path);

                        // Crea un digest (hash) per al fitxer encriptat.
                        xifrat.generarDigest(path, hash);

                        // Mostra el resultat del hash generat i el procés d'encriptació.
                        System.out.println("S'ha generat un fitxer de hash <" + path + ".digest>: " + hash);
                        System.out.println("Procés realitzat correctament: " + encryptedFile);
                        break;

                    case 2:
                        // Desencriptació d'un fitxer
                        System.out.println();
                        System.out.println("Introdueix el camí del fitxer a desencriptar: ");
                        String pathDes = scan.next();
                        System.out.println("Introdueix la clau secreta: ");
                        String keyDes = scan.next();

                        // Crida al mètode per processar (desencriptar) el fitxer.
                        String decryptedFile = xifrat.processarFitxer(pathDes, keyDes, false);

                        // Mostra el resultat del procés de desencriptació.
                        System.out.println("Procés realitzat correctament: " + decryptedFile);

                        // Comprova si el missatge ha estat alterat.
                        System.out.println("Comprovant si el missatge ha estat alterat ...");
                        System.out.println("Introdueix la ruta al fitxer de resum _digest_: ");
                        String pathFitxerDigest = scan.next();

                        // Genera el hash de verificació i el compara amb el hash original.
                        String hashOriginal = xifrat.generatPathHash(decryptedFile);
                        xifrat.compararHash(hashOriginal, pathFitxerDigest);
                        break;

                    case 3:
                        // Generació d'un hash d'un missatge
                        System.out.println("Introdueix el missatge a resumir: ");
                        String missatge = scan.next();
                        // Mostra el resum (hash) SHA-256 del missatge introduït.
                        System.out.println("Resum del missatge (SHA-256): " + xifrat.generarStringHash(missatge));
                        break;

                    case 4:
                        // Verificació del hash d'un missatge
                        System.out.println("Introdueix el missatge a verificar: ");
                        String missatgeVerificar = scan.next();
                        System.out.println("Introdueix el hash del missatge: ");
                        String hashMissatge = scan.next();

                        // Verifica si el hash del missatge coincideix amb el hash introduït.
                        boolean coincideix = xifrat.verificarHashString(missatgeVerificar, hashMissatge);
                        System.out.println(coincideix ? "El missatge coincideix amb el hash." : "ALERTA: El missatge no coincideix amb el hash.");
                        break;

                    case 5:
                        // Finalitza el programa
                        System.out.println("Has seleccionat sortir");
                        return; // Atura l'execució del programa.

                    default:
                        // Mostra un missatge per a opcions no vàlides.
                        System.out.println("Opció no vàlida");
                        break;
                }
            }
        }
    }


}
