package Classes; // Define el paquete donde se encuentra la clase

import java.io.IOException;
import java.io.RandomAccessFile;

public class GestioPersonas { // Define la clase principal 'GestioPersonas'
    static GestioPersonas gp = new GestioPersonas(); // Crea una instancia estática de 'GestioPersonas'
    static GestioFitxers gf = new GestioFitxers(); // Crea una instancia estática de 'GestioFitxers' (otra clase para gestionar archivos)

    public static void main(String[] args) throws IOException {
        gp.inici();  // Llama al metodo 'inici' para comenzar el menú principal
    }

    public void inici() throws IOException {
        int opcio; // Variable para almacenar la opción del usuario

        do {
            // Muestra el menú de opciones al usuario
            System.out.println("1. Afegir nova persona");
            System.out.println("2. Modificar una persona existent");
            System.out.println("3. Mostrar una posició");
            System.out.println("4. Sortir");

            // Pide al usuario que elija una opción
            opcio = UtilsES.demanarEnter("Introdueix una opció del 1 al 4", "Opció no vàlida");

            // Evalúa la opción seleccionada por el usuario
            switch (opcio) {
                case 1:
                    afegirPersona();  // Si elige 1, llama al metodo para agregar una nueva persona
                    break;
                case 2:
                    modificarPersona();  // Si elige 2, llama al metodo para modificar una persona existente
                    break;
                case 3:
                    mostrarPersona();  // Si elige 3, llama al metodo para mostrar una persona
                    break;
                case 4:
                    System.out.println("Sortint...");  // Si elige 4, sale del programa
                    break;
                default:
                    System.out.println("Opció no vàlida."); // Si la opción no es válida, muestra un mensaje de error
            }
        } while (opcio != 4);  // El bucle se repite hasta que el usuario elija la opción 4 (salir)
    }

    // metodo para agregar una nueva persona
    private void afegirPersona() throws IOException {
        // Pide al usuario el nombre y apellido, con un límite de 50 caracteres
        String nom = UtilsES.demanaString("Introdueix el nom i cognoms (50 caràcters màxim): ", "Error");

        // Pregunta si tiene hijos (booleano true/false)
        boolean fills = UtilsES.demanarBoolea("Té fills? (true/false): ");

        // Pide la edad de la persona
        int edat = UtilsES.demanarEnter("Introdueix l'edat: ", "Edat no vàlida.");

        // Crea un objeto 'Persona' con los datos ingresados
        Persona novaPersona = new Persona(nom, fills, edat);

        // Llama al metodo 'guardaPersona' de 'GestioFitxers' para guardar la nueva persona en un archivo
        GestioFitxers.guardaPersona(novaPersona);

        // Informa al usuario que la persona ha sido añadida correctamente
        System.out.println("Persona afegida correctament.");
    }

    // metodo para modificar una persona existente
    private void modificarPersona() throws IOException {
        // Calcula el número máximo de personas en el archivo
        int maxPersones = calcularNombrePersones();

        // Muestra el número máximo de personas
        System.out.println("Nombre maxim de persones al fitxer: " + maxPersones);

        // Pide la posición de la persona a modificar
        int pos = UtilsES.demanarEnter("Introdueix la posició de la persona a modificar: ", "Posició no vàlida.");

        // Verifica si la posición es válida
        if (pos < 1) {
            System.out.println("Posició no vàlida");
        } else {
            // Busca la persona en la posición especificada
            Persona personaExist = GestioFitxers.buscaPersona(pos - 1); // La posición comienza desde 0

            // Verifica si la persona existe
            if (personaExist == null) {
                System.out.println("No hi ha cap persona a la posició especificada.");
                return;  // Si no existe, termina el metodo
            }

            // Pide nuevos datos para modificar a la persona
            String nom = UtilsES.demanaString("Introdueix el nou nom i cognoms (50 caràcters màxim): ", "Error");
            boolean fills = UtilsES.demanarBoolea("Té fills? (true/false): ");
            int edat = UtilsES.demanarEnter("Introdueix la nova edat: ", "Edat no vàlida.");

            // Crea un nuevo objeto 'Persona' con los datos modificados
            Persona personaModificada = new Persona(nom, fills, edat);

            // Llama al metodo para actualizar los datos de la persona en el archivo
            GestioFitxers.actPersona(pos - 1, personaModificada);

            // Informa al usuario que la persona ha sido modificada correctamente
            System.out.println("Persona modificada correctament.");
        }
    }

    // metodo para mostrar una persona según su posición
    private void mostrarPersona() throws IOException {
        // Calcula el número máximo de personas en el archivo
        int maxPersones = calcularNombrePersones();

        // Muestra el número máximo de personas
        System.out.println("Nombre maxim de persones al fitxer: " + maxPersones);

        // Pide la posición de la persona a mostrar
        int pos = UtilsES.demanarEnter("Introdueix la posició de la persona a mostrar: ", "Posició no vàlida.");

        // Verifica si la posición es válida
        if (pos > 0) {
            // Busca la persona en la posición indicada
            Persona persona = GestioFitxers.buscaPersona(pos - 1);

            // Si la persona existe, muestra sus datos
            if (persona != null) {
                System.out.println("Nom i cognoms: " + persona.getNom());
                System.out.println("Fills: " + persona.hasFills());
                System.out.println("Edat: " + persona.getEdat());
            } else {
                System.out.println("No s'ha trobat cap persona a la posició " + pos);
            }
        } else {
            System.out.println("Posició no vàlida");
        }
    }

    // metodo para calcular el número de personas en el archivo
    private int calcularNombrePersones() throws IOException {
        // Usa un archivo de acceso aleatorio para leer el archivo 'persones.dat'
        try (RandomAccessFile file = new RandomAccessFile("persones.dat", "r")) {
            // Calcula el número de personas dividiendo el tamaño del archivo por el tamaño de una persona
            return (int) (file.length() / GestioFitxers.PERSONA_SIZE);
        }
    }
}
