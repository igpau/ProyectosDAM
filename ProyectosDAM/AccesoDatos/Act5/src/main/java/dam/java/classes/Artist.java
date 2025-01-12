package dam.java.classes;

public class Artist {
    DatabaseConnection con = new DatabaseConnection();
    private static String name;
    private static String code;

    // Constructor de la classe Artista
    public Artist(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Artist() {
    }

    // Getters per accedir als atributs
    public static String getName() {
        return name;
    }


    public static String getCode() {
        return code;
    }


}



