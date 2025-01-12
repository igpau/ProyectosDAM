package dam.java.dao;

import dam.java.classes.Album;
import dam.java.classes.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Album_DAO {
    static Album album = new Album();

    public static void insertarAlbum(Album album) {
        String existeixCodi = "SELECT * FROM album WHERE code = ?";
        String existeixArtista = "SELECT * FROM artist WHERE code = ?";
        String existeixStyle = "SELECT * FROM style WHERE code = ?";
        // Sentència SQL per inserir un nou artista
        String insertaAlbum = "INSERT INTO album (code, title, artist, year, style) VALUES (?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement1 = conn.prepareStatement(existeixCodi);
             PreparedStatement statement2 = conn.prepareStatement(existeixArtista);
             PreparedStatement statement3 = conn.prepareStatement(existeixStyle);
             PreparedStatement statement = conn.prepareStatement(insertaAlbum)) {

            // Establim els valors de la consulta des de l'objecte Artista
            statement1.setString(1, album.getCode());
            statement2.setString(1, album.getArtist());
            statement3.setString(1, album.getStyle());
            ResultSet rs = statement1.executeQuery();
            ResultSet rs1 = statement2.executeQuery();
            ResultSet rs2 = statement3.executeQuery();

            boolean noExist = false;
            // Comprovem si l'artista existeix
            if (rs.next()) {
                System.out.println("Ja existeix un album amb el codi: " + album.getCode());
                noExist = true;
            }
            if (!rs1.next()) {
                System.out.println("No existeix l'artista amb el codi: " + album.getArtist());
                noExist = true;
            }

            // Comprovem si l'estil existeix
            if (!rs2.next()) {
                System.out.println("No existeix l'estil amb el codi: " + album.getStyle());
                noExist = true;
            }

            // Si alguna de les comprovacions ha fallat, sortim
            if (noExist) {
                return;
            }


            // Establim els valors de la consulta des de l'objecte Artista
            statement.setString(1, album.getCode());
            statement.setString(2, album.getTitle());
            statement.setString(3, album.getArtist());
            statement.setString(4, album.getYear());
            statement.setString(5, album.getStyle());


            // Executem la inserció
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Artista afegit correctament.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al inserir l'artista: " + e.getMessage());
        }
    }

    public static Album mostrarAlbum(String codi) {
        // Sentència SQL per obtenir un àlbum
        String sql = "SELECT * FROM album WHERE code = ?";
        Album album = null; // Inicialitzem l'objecte àlbum com a null

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim el valor del paràmetre
            statement.setString(1, codi);

            // Executem la consulta
            ResultSet rs = statement.executeQuery();

            // Processar el resultat
            if (rs.next()) { // Si trobem l'àlbum amb el codi proporcionat
                String albumCode = rs.getString("code");
                String albumTitle = rs.getString("title");
                String albumArtist = rs.getString("artist");
                String albumYear = rs.getString("year");
                String albumStyle = rs.getString("style");

                // Creem un objecte Album amb les dades obtingudes
                album = new Album(albumCode, albumTitle, albumArtist, albumYear, albumStyle);

                // Mostrem les dades per consola
                System.out.println("Àlbum trobat:\n" +
                        "Codi: " + albumCode + "\n" +
                        "Títol: " + albumTitle + "\n" +
                        "Artista: " + albumArtist + "\n" +
                        "Any: " + albumYear + "\n" +
                        "Estil: " + albumStyle);
            } else {
                System.out.println("No s'ha trobat cap àlbum amb el codi: " + codi);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al mostrar l'àlbum: " + e.getMessage());
        }

        return album; // Retornem l'objecte àlbum (o null si no s'ha trobat)
    }


    public static void modificarAlbum(Album album) {
        String existeixArtista = "SELECT * FROM artist WHERE code = ?";
        String existeixStyle = "SELECT * FROM style WHERE code = ?";
        String modificaAlbum = "UPDATE album SET title = ?, artist = ?, year = ?, style = ? WHERE code = ?";
        boolean noExist = false;
        try (Connection conn = DatabaseConnection.conectarBD()) {
            assert conn != null;
            try (PreparedStatement statement1 = conn.prepareStatement(existeixArtista);
                 PreparedStatement statement2 = conn.prepareStatement(existeixStyle);
                 PreparedStatement statement = conn.prepareStatement(modificaAlbum)) {

                // Comprovem si l'artista existeix
                statement1.setString(1, album.getArtist());
                try (ResultSet rs1 = statement1.executeQuery()) {
                    if (!rs1.next()) {
                        System.out.println("No existeix l'artista amb el codi: " + album.getArtist());
                        noExist = true;
                    }
                }

                // Comprovem si l'estil existeix
                statement2.setString(1, album.getStyle());
                try (ResultSet rs2 = statement2.executeQuery()) {
                    if (!rs2.next()) {
                        System.out.println("No existeix l'estil amb el codi: " + album.getStyle());
                        noExist = true;
                    }
                }
                if (noExist) {
                    return;
                }
                // Realitzem la modificació
                statement.setString(1, album.getTitle());
                statement.setString(2, album.getArtist());
                statement.setString(3, album.getYear());
                statement.setString(4, album.getStyle());
                statement.setString(5, album.getCode());

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("L'àlbum s'ha modificat correctament.");
                } else {
                    System.out.println("No s'ha pogut modificar l'àlbum. Potser no existeix.");
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al modificar l'àlbum: " + e.getMessage());
        }
    }

    public static void eliminarAlbum(String codi) {
        // Sentència SQL per eliminar un àlbum
        String sql = "DELETE FROM album WHERE code = ?";


        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim el valor del paràmetre
            statement.setString(1, codi);

            // Executem la modificació
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("L'àlbum s'ha eliminat correctament.");
            } else {
                System.out.println("No s'ha trobat cap àlbum amb el codi: " + codi);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al eliminar l'àlbum: " + e.getMessage());
        }
    }
}


