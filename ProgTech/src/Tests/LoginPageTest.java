package Tests;

import Exceptions.invalidToyIdException;
import Exceptions.invalidToyNameException;
import Exceptions.invalidToyPriceException;
import Forms.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

class LoginPageTest {
    String DB = "jdbc:mysql://localhost:3306/jatekaruhaz";
    String USERNAME = "root";
    String PASSWORD = "";
    Connection connection = DriverManager.getConnection(DB, USERNAME, PASSWORD);
    Statement stmt = connection.createStatement();

    LoginPageTest() throws SQLException {
    }

    @Test
    void successfulLogin() throws SQLException, invalidToyIdException, invalidToyNameException, invalidToyPriceException {


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
    void unsuccessfullLogin() throws SQLException, invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        String username = "notadmin";
        String password = "notadmin";
        ResultSet result = stmt.executeQuery("SELECT * FROM user WHERE username = '"+ username +"'; ");
        LoginPage loginPage = new LoginPage();
        boolean testResult = false;
        while(result.next()){
            testResult = (loginPage.tryLogin(result, username, password));
            if(!testResult)
                break;
        }
        Assertions.assertFalse(testResult);

    }
    @Test
    void unsuccessfullLoginWithPassword() throws SQLException, invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        String username = "admin";
        String password = "notadmin";
        ResultSet result = stmt.executeQuery("SELECT * FROM user WHERE username = '"+ username +"'; ");
        LoginPage loginPage = new LoginPage();
        boolean testResult = true;
        while(result.next()){
            testResult = (loginPage.tryLogin(result, username, password));
            if(!testResult)
                break;
        }
        Assertions.assertFalse(testResult);

    }

}