package Classes; // Define el paquete donde se encuentra la clase

import java.io.IOException;
import java.io.RandomAccessFile;

public class GestioFitxers { // Define la clase 'GestioFitxers' para gestionar archivos
    // Define el tamaño de una persona en bytes: 50 bytes para el nombre, 1 byte para 'fills' y 4 bytes para 'edat'
    public static final long PERSONA_SIZE = 50 + 1 + 4;
    private static final String fitxer = "persones.dat"; // Nombre del archivo donde se guardan las personas

    // Metodo para guardar una nueva persona en el archivo
    public static boolean guardaPersona(Persona persona) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(fitxer, "rw")) { // Abre el archivo en modo lectura/escritura ('rw')
            // Mueve el puntero al final del archivo para añadir una nueva persona
            file.seek(file.length());

            // Escribe los datos de la persona en el archivo
            file.writeChars(ajustaNom(persona.getNom()));  // Escribe el nombre ajustado a 50 bytes (25 caracteres)
            file.writeBoolean(persona.hasFills());         // Escribe si tiene hijos (1 byte)
            file.writeInt(persona.getEdat());              // Escribe la edad (4 bytes)

            file.close(); // Cierra el archivo
            return true;  // Retorna 'true' si la operación fue exitosa
        } catch (IOException e) {
            e.printStackTrace(); // Imprime la traza de la excepción en caso de error
            return false;  // Retorna 'false' si hubo un error
        }
    }

    // Metodo para actualizar una persona existente en el archivo
    public static boolean actPersona(int pos, Persona persona) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(fitxer, "rw")) { // Abre el archivo en modo lectura/escritura
            // Calcula la posición en bytes donde comienza la persona que se quiere modificar
            long bytePosition = pos * PERSONA_SIZE;

            // Verifica si la posición calculada es válida
            if (bytePosition >= file.length()) {
                System.out.println("Posició no vàlida."); // Muestra un mensaje si la posición es inválida
                return false; // Retorna 'false' si la posición es incorrecta
            }

            // Mueve el puntero a la posición correspondiente en el archivo
            file.seek(bytePosition);

            // Sobrescribe los datos de la persona en el archivo
            file.writeChars(ajustaNom(persona.getNom()));  // Escribe el nombre ajustado a 50 bytes
            file.writeBoolean(persona.hasFills());         // Escribe si tiene hijos
            file.writeInt(persona.getEdat());              // Escribe la edad

            return true; // Retorna 'true' si la actualización fue exitosa
        } catch (IOException e) {
            e.printStackTrace(); // Imprime la traza del error en caso de excepción
            return false;  // Retorna 'false' si hubo un error
        }
    }

    // Metodo para buscar una persona en el archivo según su posición
    public static Persona buscaPersona(int pos) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(fitxer, "r")) { // Abre el archivo en modo solo lectura
            // Calcula la posición en bytes donde se encuentra la persona buscada
            long bytePosition = pos * PERSONA_SIZE;

            // Verifica si la posición es válida
            if (bytePosition >= file.length()) {
                System.out.println("Posició no vàlida."); // Muestra un mensaje si la posición es incorrecta
                return null; // Retorna 'null' si la posición no existe en el archivo
            }

            // Mueve el puntero a la posición correspondiente en el archivo
            file.seek(bytePosition);

            // Lee los datos de la persona
            char[] nomChars = new char[25];  // Buffer para almacenar los 25 caracteres del nombre
            for (int i = 0; i < nomChars.length; i++) {
                nomChars[i] = file.readChar(); // Lee cada carácter (2 bytes por carácter)
            }
            String nom = new String(nomChars).trim();  // Convierte los caracteres a String y elimina espacios en blanco

            boolean fills = file.readBoolean();  // Lee si la persona tiene hijos (1 byte)
            int edat = file.readInt();  // Lee la edad de la persona (4 bytes)

            // Retorna un objeto 'Persona' con los datos leídos
            return new Persona(nom, fills, edat);
        }
    }

    // Metodo auxiliar para ajustar el nombre a 25 caracteres
    private static String ajustaNom(String nom) {
        // Si el nombre es mayor a 25 caracteres, lo corta
        if (nom.length() > 25) {
            return nom.substring(0, 25);  // Retorna los primeros 25 caracteres
        } else {
            // Si es menor a 25 caracteres, añade espacios en blanco hasta completar 25
            StringBuilder sb = new StringBuilder(nom);
            while (sb.length() < 25) {
                sb.append(" ");  // Añade espacios en blanco
            }
            return sb.toString();  // Retorna el nombre ajustado
        }
    }
}

