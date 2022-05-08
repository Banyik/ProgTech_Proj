import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

class LoginPageTest {
    @Test
    void successfulLogin() throws SQLException {

        String DB = "jdbc:mysql://localhost:3306/jatekaruhaz";
        String USERNAME = "root";
        String PASSWORD = "";
        Connection connection = DriverManager.getConnection(DB, USERNAME, PASSWORD);
        Statement stmt = connection.createStatement();
        String username = "admin";
        String password = "admin";
        ResultSet result = stmt.executeQuery("SELECT * FROM user WHERE username = '"+ username +"'; ");
        LoginPage loginPage = new LoginPage();
        boolean testResult = false;
        while(result.next()){
            testResult = (loginPage.tryLogin(result, username, password));
            if(testResult)
                break;
        }
        Assertions.assertTrue(testResult);

    }

    @Test
    void unsuccessfullLogin()throws SQLException {

        String DB = "jdbc:mysql://localhost:3306/jatekaruhaz";
        String USERNAME = "root";
        String PASSWORD = "";
        Connection connection = DriverManager.getConnection(DB, USERNAME, PASSWORD);
        Statement stmt = connection.createStatement();
        String username = "notadmin";
        String password = "notadmin";
        ResultSet result = stmt.executeQuery("SELECT * FROM user WHERE username = '"+ username +"'; ");
        LoginPage loginPage = new LoginPage();
        boolean testResult = false;
        while(result.next()){
            testResult = (loginPage.tryLogin(result, username, password));
            if(testResult)
                break;
        }
        Assertions.assertFalse(testResult);

    }

}