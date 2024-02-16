package src.test;

import org.junit.Test;
import src.database.DatabaseConnection;

import java.sql.Connection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class DatabaseConnectionTest {

    @Test
    public void getConnection_ShouldReturnValidConnection() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            assertNotNull("La connexion ne devrait pas être null", connection);
            // Assurez-vous de fermer la connexion après le test pour éviter les fuites de ressources
            connection.close();
        } catch (Exception e) {
            fail("La récupération de la connexion ne devrait pas lancer d'exception");
        }
    }
}
