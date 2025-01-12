package Classes; // Define el paquete donde se encuentra la clase

public class Persona { // Define la clase 'Persona'
    private String nom; // Almacena el nombre de la persona (máximo 50 caracteres)
    private boolean fills; // Almacena si la persona tiene hijos o no (booleano)
    private int edat; // Almacena la edad de la persona

    // Constructor que recibe el nombre, si tiene hijos, y la edad
    public Persona(String nom, boolean fills, int edat) {
        this.nom = ajustaNom(nom);  // Ajusta el nombre para que tenga exactamente 50 caracteres
        this.fills = fills; // Asigna si la persona tiene hijos
        this.edat = edat; // Asigna la edad de la persona
    }

    // Constructor vacío para crear una persona sin asignar valores iniciales
    public Persona() {
    }

    // metodo getter para obtener el nombre de la persona
    public String getNom() {
        return nom;
    }

    // metodo para saber si la persona tiene hijos (retorna un booleano)
    public boolean hasFills() {
        return fills;
    }

    // metodo getter para obtener la edad de la persona
    public int getEdat() {
        return edat;
    }

    // Metodo privado que ajusta el nombre para que siempre tenga 50 caracteres
    private String ajustaNom(String nom) {
        StringBuilder sb = new StringBuilder(nom); // Usa StringBuilder para manipular el string

        // Si el nombre es mayor a 50 caracteres, lo corta
        if (nom.length() > 50) {
            return nom.substring(0, 50); // Retorna los primeros 50 caracteres
        } else {
            // Si el nombre es menor a 50 caracteres, añade espacios en blanco hasta completar 50
            for (int i = sb.length(); i < 50; i++) {
                sb.append(' '); // Añade un espacio en blanco hasta alcanzar 50 caracteres
            }
            return sb.toString(); // Retorna el nombre ajustado con 50 caracteres
        }
    }

    // Sobrescribe el metodo 'toString' para devolver una representación en texto de la persona
    @Override
    public String toString() {
        // Muestra el nombre (sin espacios extras), si tiene hijos y la edad
        return "Nom: " + nom.trim() + "\nFills: " + fills + "\nEdat: " + edat;
    }
}
