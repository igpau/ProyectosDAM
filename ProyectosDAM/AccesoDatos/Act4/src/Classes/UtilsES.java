package Classes;

import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

// Clase UtilsES: proporciona herramientas para la interacción con el usuario a través de la consola.
public class UtilsES {

    // Constantes para mensajes predefinidos.
    static final String MISSATGE_LINIA_SEPARACIO = "------------------------------------------";
    static final String MISSATGE_ERROR_LECTURA = "Error de lectura";

    /**
     * Muestra un menú en la consola a partir de un array de cadenas.
     *
     * @param menu Array de cadenas con las opciones del menú.
     */
    static public void mostrarMenu(String[] menu) {
        System.out.println();
        System.out.println(MISSATGE_LINIA_SEPARACIO);
        System.out.println(menu[0]); // Se asume que el primer elemento es el título del menú.
        System.out.println(MISSATGE_LINIA_SEPARACIO);
        for (int pos = 1; pos < menu.length; pos++) {
            System.out.println(menu[pos]); // Muestra cada opción del menú.
        }
    }

    /**
     * Solicita al usuario un código de una lista de opciones.
     *
     * @param missatge   Mensaje que se muestra al usuario.
     * @param arrayCodis Array con los códigos disponibles.
     * @return El código seleccionado por el usuario.
     */
    public static String demanaCodiOpcions(String missatge, String[] arrayCodis) {
        int resp;
        for (int pos = 0; pos < arrayCodis.length; pos++) {
            System.out.println(pos + "." + arrayCodis[pos]); // Muestra las opciones con índices numéricos.
        }
        do {
            resp = demanarEnter(missatge, MISSATGE_ERROR_LECTURA); // Solicita un entero válido.
        } while (resp < 0 || resp >= arrayCodis.length); // Valida que esté dentro del rango.
        return arrayCodis[resp]; // Devuelve el código seleccionado.
    }

    /**
     * Solicita al usuario un año dentro de un rango válido.
     *
     * @param missatge Mensaje que se muestra al usuario.
     * @param anyMin   Año mínimo aceptable.
     * @param anyMax   Año máximo aceptable.
     * @return El año ingresado por el usuario como cadena.
     */
    public static String demanaAnyRang(String missatge, int anyMin, int anyMax) {
        int resp;
        do {
            resp = demanarEnter(missatge, MISSATGE_ERROR_LECTURA); // Solicita un entero.
        } while (resp < anyMin || resp > anyMax); // Valida que esté dentro del rango.
        return Integer.toString(resp); // Convierte el entero a cadena.
    }

    /**
     * Solicita un entero al usuario.
     *
     * @param missatge Mensaje que se muestra al usuario.
     * @param mError   Mensaje de error en caso de entrada inválida.
     * @return El número entero ingresado.
     */
    public static int demanarEnter(String missatge, String mError) {
        Scanner scanner = new Scanner(System.in);
        int ret;
        boolean correcte = false;
        do {
            System.out.print(missatge + "\n");
            correcte = scanner.hasNextInt(); // Verifica si la entrada es un entero.
            if (!correcte) {
                scanner.next(); // Limpia la entrada inválida.
                System.out.println("\nDebes ingresar un número entero, por favor.");
            }
        } while (!correcte);
        ret = scanner.nextInt();
        scanner.nextLine(); // Limpia el buffer.
        return ret;
    }

    /**
     * Solicita un número decimal (float) al usuario.
     *
     * @param missatge Mensaje que se muestra al usuario.
     * @param mError   Mensaje de error en caso de entrada inválida.
     * @return El número decimal ingresado.
     */
    public static float demanarFloat(String missatge, String mError) {
        Scanner scanner = new Scanner(System.in);
        float ret;
        boolean correcte = false;
        do {
            System.out.print(missatge + "\n");
            correcte = scanner.hasNextFloat(); // Verifica si la entrada es un float.
            if (!correcte) {
                scanner.next(); // Limpia la entrada inválida.
                System.out.println("\nDebes ingresar un número decimal, por favor.");
            }
        } while (!correcte);
        ret = scanner.nextFloat();
        scanner.nextLine(); // Limpia el buffer.
        return ret;
    }

    /**
     * Muestra un mensaje en la consola.
     *
     * @param missatge Mensaje que se mostrará.
     */
    public static void mostrarMissatge(String missatge) {
        System.out.println(missatge);
    }

    /**
     * Solicita una cadena de texto al usuario, asegurándose de que no esté vacía.
     *
     * @param missatge Mensaje que se muestra al usuario.
     * @param mError   Mensaje de error si la entrada está vacía.
     * @return La cadena ingresada por el usuario.
     */
    public static String demanaString(String missatge, String mError) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(missatge);
        String textIntroduit = scanner.nextLine(); // Lee la entrada.
        while (textIntroduit.isEmpty()) { // Verifica que no esté vacía.
            System.out.println(mError);
            System.out.println(missatge);
            textIntroduit = scanner.nextLine();
        }
        return textIntroduit;
    }

    // Scanner global para evitar crear múltiples instancias.
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Solicita un valor booleano al usuario (true/false).
     *
     * @param missatge Mensaje que se muestra al usuario.
     * @return El valor booleano ingresado.
     */
    public static boolean demanarBoolea(String missatge) {
        while (true) {
            System.out.print(missatge);
            String resposta = scanner.nextLine().trim().toLowerCase(); // Convierte a minúsculas y elimina espacios.
            if (resposta.equals("true")) {
                return true;
            } else if (resposta.equals("false")) {
                return false;
            } else {
                System.out.println("Entrada no válida. Por favor, introduce 'true' o 'false'.");
            }
        }
    }

    /**
     * Obtiene la fecha y hora actual como una cadena formateada.
     *
     * @return La fecha actual en formato "dd-MM-yyyy hh-mm-ss".
     */
    public static String getStringDate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
        return dateFormat.format(date); // Devuelve la fecha formateada.
    }

    /**
     * Pausa la ejecución hasta que el usuario presione Enter.
     *
     * @param missatge Mensaje que se muestra al usuario.
     */
    public static void demanaReturn(String missatge) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(missatge);
        scanner.nextLine(); // Espera a que el usuario presione Enter.
    }
}
