package dam.java.dao;

import dam.java.classes.DatabaseConnection;
import dam.java.classes.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sale_DAO {

    static Sale sale = new Sale();

    public static void insertarVenda(Sale sale) {

        // Sentència SQL per inserir una nova venda
        String sql = "INSERT INTO sale (song, card, sale_date) VALUES (?,?,?)";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim els valors de la consulta des de l'objecte Venda
            statement.setString(1, Sale.getSong());
            statement.setString(2, Sale.getCard());
            statement.setDate(3, Sale.getSale_date());

            // Executem la inserció
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Venda afegida correctament.");

            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void llistarVendesPerArtista(String nomArtista) {
        // Consulta SQL para listar las ventas por artista
        String sql = """
                SELECT 
                    artist.name AS artist_name, 
                    album.title AS album_title, 
                    song.title AS song_title, 
                    COUNT(sale.song) AS total_sales 
                FROM 
                    sale 
                JOIN song ON sale.song = song.code 
                JOIN album ON song.album = album.code 
                JOIN artist ON album.artist = artist.code 
                WHERE artist.name = ?
                GROUP BY 
                    artist.name, album.title, song.title 
                ORDER BY 
                    total_sales DESC;
                """;

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establecemos el valor del parámetro para el nombre del artista
            statement.setString(1, nomArtista);

            // Ejecutamos la consulta
            ResultSet rs = statement.executeQuery();

            // Procesamos los resultados
            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                String artistName = rs.getString("artist_name");
                String albumTitle = rs.getString("album_title");
                String songTitle = rs.getString("song_title");
                int totalSales = rs.getInt("total_sales");

                System.out.println("\nArtista: " + artistName +
                        " | Álbum: " + albumTitle +
                        " | Canción: " + songTitle +
                        " | Ventas: " + totalSales);
            }

            if (!hayResultados) {
                System.out.println("No se encontraron ventas para el artista: " + nomArtista);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al listar las ventas por artista: " + e.getMessage());
        }
    }

}
