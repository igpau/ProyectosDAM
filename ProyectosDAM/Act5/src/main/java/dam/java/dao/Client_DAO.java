package dam.java.dao;

import dam.java.classes.Card;
import dam.java.classes.Client;
import dam.java.classes.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client_DAO {

    // Mètode per inserir client i targeta dins d'una transacció
    public static void insertClientAndCard(Client client, Card card) {
        // Sentència SQL per inserir un nou client
        String sqlClient = "INSERT INTO client (clientID, name, surname1, surname2, region) VALUES (?,?,?,?,?)";
        // Sentència SQL per inserir una nova targeta
        String sqlCard = "INSERT INTO card (code, card_number, csv, exp_date, client) VALUES (?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.conectarBD()) {
            // Desactivar auto-commit per començar la transacció
            assert conn != null;
            conn.setAutoCommit(false);

            try (PreparedStatement statementClient = conn.prepareStatement(sqlClient);
                 PreparedStatement statementCard = conn.prepareStatement(sqlCard)) {

                // Inserció del client
                statementClient.setInt(1, client.getClientID());
                statementClient.setString(2, client.getName());
                statementClient.setString(3, client.getSurname1());
                statementClient.setString(4, client.getSurname2());
                statementClient.setString(5, client.getRegion());
                int rowsClient = statementClient.executeUpdate();

                // Inserció de la targeta
                statementCard.setString(1, card.getCode());
                statementCard.setString(2, card.getCard_number());
                statementCard.setString(3, card.getCsv());
                statementCard.setString(4, card.getExp_date());
                statementCard.setInt(5, client.getClientID()); // Relació amb el client
                int rowsCard = statementCard.executeUpdate();

                // Si totes dues operacions tenen èxit, confirmar la transacció
                conn.commit();
                System.out.println("Client i targeta afegits correctament.");

            } catch (SQLException e) {
                // Si hi ha un error, desfer la transacció
                conn.rollback();
                System.err.println("Error en la transacció: " + e.getMessage());
            } finally {
                // Tornar a activar l'auto-commit
                conn.setAutoCommit(true);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al connectar a la base de dades: " + e.getMessage());
        }
    }

    public static void llistarCompres(String nomClient) {
        // Consulta SQL para listar las compras de un cliente específico
        String sql = """
                SELECT client.name AS client_name,
                    client.surname1 AS client_surname1,
                    client.surname2 AS client_surname2,
                    COUNT(sale.song) AS total_purchases
                FROM 
                    sale
                JOIN card ON sale.card = card.code
                JOIN client ON card.client = client.clientID
                WHERE 
                    client.name = ?
                GROUP BY 
                    client.name, client.surname1, client.surname2;
                """;

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establecemos el valor del parámetro para el nombre del cliente
            statement.setString(1, nomClient);

            // Ejecutamos la consulta
            ResultSet rs = statement.executeQuery();

            // Procesamos los resultados
            System.out.println("\nListado de compras para el cliente: " + nomClient + "\n");
            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                String clientName = rs.getString("client_name");
                String surname1 = rs.getString("client_surname1");
                String surname2 = rs.getString("client_surname2");
                int totalPurchases = rs.getInt("total_purchases");

                System.out.println("Cliente: " + clientName + " " + surname1 + " " + (surname2 != null ? surname2 : "") +
                        " | Total compras: " + totalPurchases);
            }

            if (!hayResultados) {
                System.out.println("No se encontraron compras para el cliente: " + nomClient);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al listar las compras del cliente: " + e.getMessage());
        }
    }

    public static boolean mostrarClient(int codi) {
        // Sentència SQL per obtenir un client
        String sql = "SELECT * FROM client WHERE clientID = ?";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim els valors de la consulta
            statement.setInt(1, codi);

            // Executem la consulta
            ResultSet rs = statement.executeQuery();

            // Processar el resultat
            if (rs.next()) { // Si trobem el client amb el codi proporcionat
                int clientID = rs.getInt("clientID");
                String clientName = rs.getString("name");
                String clientSurname1 = rs.getString("surname1");
                String clientSurname2 = rs.getString("surname2");
                String clientRegion = rs.getString("region");
                System.out.println("Client trobat:\n" + "ID_Client:" + clientID + "\nNom client: " + clientName + "\nCognom1: " + clientSurname1 + "\nCognom2: " + clientSurname2 + "\nRegió: " + clientRegion + "\n");
                return true;
            } else {
                System.out.println("No s'ha trobat cap client amb el codi: " + codi);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al mostrar el client: " + e.getMessage());
        }
        return false; // Retornem l'objecte client (o null si no s'ha trobat)
    }

    public static void modificarClient(Client client) {
        // Sentència SQL per modificar un client
        String sql = "UPDATE client SET name = ?, surname1 = ?, surname2 = ?, region = ? WHERE clientID = ?";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim els valors de la consulta
            statement.setString(1, client.getName());
            statement.setString(2, client.getSurname1());
            statement.setString(3, client.getSurname2());
            statement.setString(4, client.getRegion());
            statement.setInt(5, client.getClientID());

            // Executem la modificació
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Client modificat correctament.");
            } else {
                System.out.println("No s'ha trobat cap client amb el codi: " + client.getClientID());
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al modificar el client: " + e.getMessage());
        }
    }

    public static void eliminarClient(int codi) {
        // Sentència SQL per eliminar un client
        String sql = "DELETE FROM client WHERE clientID = ?";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim el valor del paràmetre
            statement.setInt(1, codi);

            // Executem l'eliminació
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Client eliminat correctament.");
            } else {
                System.out.println("No s'ha trobat cap client amb el codi: " + codi);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al eliminar el client: " + e.getMessage());
        }
    }

    public static boolean comproba_id_targeta(String codi) {
        // Sentència SQL per comprovar si existeix una targeta
        String sql = "SELECT * FROM card WHERE code = ?";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim el valor del paràmetre
            statement.setString(1, codi);

            // Executem la consulta
            ResultSet rs = statement.executeQuery();

            // Processar el resultat
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al comprovar la targeta: " + e.getMessage());
            return true;
        }
    }

    public static boolean verificar_id_client(int codi) {
        // Sentència SQL per obtenir un client
        String sql = "SELECT * FROM client WHERE clientID = ?";

        try (Connection conn = DatabaseConnection.conectarBD();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Establim els valors de la consulta
            statement.setInt(1, codi);

            // Executem la consulta
            ResultSet rs = statement.executeQuery();

            // Processar el resultat
            if (rs.next()) { // Si trobem el client amb el codi proporcionat
                return true;
            } else {
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al verificar el client: " + e.getMessage());
        }
        return false; // Retornem l'objecte client (o null si no s'ha trobat)
    }
}

