package dam.java.dao;

import dam.java.classes.Artist;
import dam.java.classes.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Artista_DAO {
    static Artist artist = new Artist();

    // Mètode per inserir un objecte Artista a la base de dades
    public static void insertarArtista(Artist artista) {
        /*Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT titulo, precio FROM Libros WHERE precio > 2");*/

        // Sentència SQL per inserir un nou artista
        String sql = "INSERT INTO artist (name, code) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim els valors de la consulta des de l'objecte Artista
            statement.setString(1, Artist.getName());
            statement.setString(2, Artist.getCode());

            // Executem la inserció
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Artista afegit correctament.");
            }
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al inserir l'artista: " + e.getMessage());
        }
    }

    public static boolean mostrarArtista(String code) {
        // Sentència SQL per obtenir un artista
        String sql = "SELECT * FROM artist WHERE code = ?";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim els valors de la consulta
            statement.setString(1, code);

            // Executem la consulta
            ResultSet rs = statement.executeQuery();

            // Processar el resultat
            if (rs.next()) { // Si trobem l'artista amb el codi proporcionat
                String artistCode = rs.getString("code");
                String artistName = rs.getString("name");
                System.out.println("Artista trobat:\n" + "ID_Artista:" + artistCode + "\nNom artista: " + artistName + "\n");
            return true;
            } else {
                System.out.println("No s'ha trobat cap artista amb el codi: " + code);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al mostrar l'artista: " + e.getMessage());
        }

        return false; // Retornem l'objecte artista (o null si no s'ha trobat)
    }

    public static void modificarArtista(String codi, String nouNom) {
        // Sentència SQL per modificar un artista
        String sql = "UPDATE artist SET name = ? WHERE code = ?";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim els valors de la consulta
            statement.setString(1, nouNom);
            statement.setString(2, codi);

            // Executem la modificació
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Artista modificat correctament.");
            } else {
                System.out.println("No s'ha trobat cap artista amb el codi: " + codi);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al modificar l'artista: " + e.getMessage());
        }
    }

    public static void eliminarArtista(String codi) {
        // Sentències SQL per eliminar àlbums i després l'artista

        String sql = "DELETE FROM artist WHERE code = ?";

        Connection conn = null; // Declarem la connexió fora del bloc try

        try  {            // Establim la connexió
            conn = DatabaseConnection.conectarBD();
            PreparedStatement statementArtist = conn.prepareStatement(sql);
            statementArtist.setString(1, codi);
                int artistDeleted = statementArtist.executeUpdate();

                if (artistDeleted > 0) {
                    System.out.println("L'artista s'ha eliminat correctament.");
                } else {
                    System.out.println("No s'ha trobat cap artista amb el codi: " + codi);
                }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al eliminar l'artista: " + e.getMessage());
        } finally {
            // Assegurar que la connexió es tanca
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connexió tancada.");
                } catch (SQLException e) {
                    System.out.println("Error en tancar la connexió: " + e.getMessage());
                }
            }
        }
        System.out.println("");
    }


}
