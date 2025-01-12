package dam.java.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:mysql://cicles.ies-eugeni.cat:3306/pibanez_db";
    private static final String USER = "pibanez";
    private static final String PASSWORD = "m13_pibanez#2425";

        public static Connection conectarBD() throws ClassNotFoundException, SQLException {
            // Carregar el driver JDBC per MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                try {
                // Establir la connexió
                Connection conexion = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                //System.out.println("Connexió establerta correctament");
                return conexion;
            } catch (SQLException e) {
                System.out.println("Error al connectar amb la base de dades: " + e.getMessage());
                e.printStackTrace();  // Opcional: imprimir el stack trace per depurar
                return null;
            }
        }

        // Exemple de mètode per tancar la connexió (el cridaràs des de la teva aplicació)
        public static void closeConnection(Connection conexion) {
            if (conexion != null) {
                try {
                    conexion.close();
                    System.out.println("Connexió tancada correctament");
                } catch (SQLException e) {
                    System.out.println("Error al tancar la connexió: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
