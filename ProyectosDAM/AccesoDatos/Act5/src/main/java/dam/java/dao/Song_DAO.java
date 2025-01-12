package dam.java.dao;

import dam.java.classes.DatabaseConnection;
import dam.java.classes.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Song_DAO {
public static boolean verificarAlbum(Song song) {
    String sql = "SELECT * FROM album WHERE code = ?";
    try (Connection conn = DatabaseConnection.conectarBD();
         PreparedStatement statement = conn.prepareStatement(sql)) {
        statement.setString(1, Song.getAlbum());
        ResultSet rs = statement.executeQuery();
        if (!rs.next()) {
            System.out.println("L'àlbum amb codi " + song.getAlbum() + " no existeix.");
            return false;
        }
    } catch (SQLException | ClassNotFoundException e) {
        System.out.println("Error al verificar l'àlbum: " + e.getMessage());
    }
    return true;
}

    public static void insertarSong(Song song) {

        // Sentència SQL per inserir una nova cançó
        String sql = "INSERT INTO song (code, title, album, track) VALUES (?,?,?,?)";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement1 = conn.prepareStatement(sql)) {

            // Establim els valors de la consulta des de l'objecte Song
            statement1.setString(1, song.getCode());
            statement1.setString(2, song.getTitle());
            statement1.setString(3, song.getAlbum());
            statement1.setString(4, song.getTrack());

            // Executem la inserció
            int rowsInserted = statement1.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Cançó afegida correctament.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al inserir la cançó: " + e.getMessage());
        }
    }

    public static boolean mostrarCanco(String codi) {
        // Sentència SQL per obtenir una cançó
        String sql = "SELECT * FROM song WHERE code = ?";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim els valors de la consulta
            statement.setString(1, codi);

            // Executem la consulta
            ResultSet rs = statement.executeQuery();

            // Processar el resultat
            if (rs.next()) { // Si trobem la cançó amb el codi proporcionat
                String songCode = rs.getString("code");
                String songTitle = rs.getString("title");
                String songAlbum = rs.getString("album");
                String songTrack = rs.getString("track");
                System.out.println("Cançó trobada:\n" + "ID_Cançó:" + songCode + "\nTítol cançó: " + songTitle + "\nÀlbum: " + songAlbum + "\nTrack: " + songTrack + "\n");
                return true;
            } else {
                System.out.println("No s'ha trobat cap cançó amb el codi: " + codi);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al mostrar la cançó: " + e.getMessage());
        }
        return false;
    }

    public static void modificarCanco(Song song) {
        // Sentència SQL per modificar una cançó
        String sql = "UPDATE song SET title = ?, album = ?, track = ? WHERE code = ?";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim els valors de la consulta
            statement.setString(1, song.getTitle());
            statement.setString(2, song.getAlbum());
            statement.setString(3, song.getTrack());
            statement.setString(4, song.getCode());

            // Executem la modificació
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cançó modificada correctament.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al modificar la cançó: " + e.getMessage());
        }
    }

    public static void eliminarCanco(String codi) {
        // Sentència SQL per eliminar una cançó
        String sql = "DELETE FROM song WHERE code = ?";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim el valor del paràmetre
            statement.setString(1, codi);

            // Executem l'eliminació
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Cançó eliminada correctament.");
            } else {
                System.out.println("No s'ha trobat cap cançó amb el codi: " + codi);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al eliminar la cançó: " + e.getMessage());
        }
    }

    public static boolean verificar_id_song(String codi) {
        // Sentència SQL per verificar si existeix una cançó amb el codi proporcionat
        String sql = "SELECT * FROM song WHERE code = ?";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim el valor del paràmetre
            statement.setString(1, codi);

            // Executem la consulta
            ResultSet rs = statement.executeQuery();

            // Processar el resultat
            if (rs.next()) {
                System.out.println("Ja existeix una cançó amb el codi: " + codi);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al verificar la cançó: " + e.getMessage());
        }
        return false;
    }
}