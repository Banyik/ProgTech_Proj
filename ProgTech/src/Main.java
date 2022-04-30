import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    static void main(String[] args) throws IOException, SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/jatekaruhaz";
        String username = "root";
        String password = "";

        Connection connection = DriverManager.getConnection(jdbcURL, username, password);

        String sql = "INSERT INTO order(object_id, addressee, `mailing address`, price) VALUES (1, 'Minta Márton', 'ASD', 123)";
    }
}
