package cat.dam2.psp.act01.Cesar;

public class Cesar {
    static int clau = 7;
    // Abecedari que inclou lletres amb accents
    static String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÈÉÌÍÒÓÙÚÑ";
    static String textoriginal = "La sort està tirada";
    static String textadesxifrar;

    public static void xifrar() {
        // Convertir el text original a majúscules
        String textUpperCase = textoriginal.toUpperCase();

        // Variable per acumular el text xifrat
        StringBuilder textXifrat = new StringBuilder();

        // Recórrer el string lletra per lletra
        for (int i = 0; i < textUpperCase.length(); i++) {
            char lletra = textUpperCase.charAt(i);
            int posicio = -1;

            // Buscar la posició de la lletra a l'abecedari manualment
            for (int j = 0; j < abecedario.length(); j++) {
                if (abecedario.charAt(j) == lletra) {
                    posicio = j;
                    break; // Si trobem la lletra, sortim del bucle
                }
            }

            // Només xifrar si hem trobat la lletra a l'abecedari
            if (posicio != -1) {
                int posicioXifrada = (posicio + clau) % abecedario.length(); // Aplicar mòdul per mantenir dins de l'abecedari
                textXifrat.append(abecedario.charAt(posicioXifrada)); // Afegir la lletra xifrada
            } else {
                // Si no és una lletra, afegir el caràcter tal qual (espais, puntuacions)
                textXifrat.append(lletra);
            }
        }

        // Mostrar el text original
        System.out.println("Text original: " + textoriginal);
        // Mostrar el text xifrat
        System.out.println("Text xifrat: " + textXifrat.toString());
        textadesxifrar = textXifrat.toString();
    }

    public static void desxifra() {
        // Variable per acumular el text desxifrat
        StringBuilder textDesxifrat = new StringBuilder();

        // Recórrer el string lletra per lletra
        for (int i = 0; i < textadesxifrar.length(); i++) {
            char lletra = textadesxifrar.charAt(i);
            int posicio = -1;

            // Buscar la posició de la lletra a l'abecedari manualment
            for (int j = 0; j < abecedario.length(); j++) {
                if (abecedario.charAt(j) == lletra) {
                    posicio = j;
                    break; // Si trobem la lletra, sortim del bucle
                }
            }

            // Només desxifrar si hem trobat la lletra a l'abecedari
            if (posicio != -1) {
                int posicioDesxifrada = (posicio - clau + abecedario.length()) % abecedario.length(); // Resta la clau, ajustant amb el mòdul
                textDesxifrat.append(abecedario.charAt(posicioDesxifrada)); // Afegir la lletra desxifrada
            } else {
                // Si no és una lletra, afegir el caràcter tal qual (espais, puntuacions)
                textDesxifrat.append(lletra);
            }
        }
        // Mostrar el text desxifrat
        System.out.println("Text desxifrat: " + textDesxifrat.toString());
    }

}
