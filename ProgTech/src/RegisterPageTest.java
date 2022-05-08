import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class RegisterPageTest {
    String DB = "jdbc:mysql://localhost:3306/jatekaruhaz";
    String USERNAME = "root";
    String PASSWORD = "";
    Connection connection = DriverManager.getConnection(DB, USERNAME, PASSWORD);
    Statement stmt = connection.createStatement();
    ResultSet result = stmt.executeQuery("SELECT * FROM user");
    RegisterPage registerPage = new RegisterPage();
    @Test
    void successfulPassword(){
        assertTrue(registerPage.checkPassword("1", "1"));
    }
    @Test
    void unsuccessfulPassword(){
        assertFalse(registerPage.checkPassword("1", "2"));
    }
    @Test
    void successfulUsername() throws SQLException {
        boolean testResult = false;
        while(result.next()){
            testResult = registerPage.checkUsername(result, "Test");
            if(testResult)
                break;
        }
        assertTrue(testResult);
    }
    @Test
    void unsuccessfulUsername() throws SQLException {
        boolean testResult = false;
        while(result.next()){
            testResult = registerPage.checkUsername(result, "admin");
            if(!testResult)
                break;
        }
        assertFalse(testResult);
    }
    @Test
    void successfulEmail() throws SQLException {
        boolean testResult = false;
        while(result.next()){
            testResult = registerPage.checkEmail(result, "test@test.com");
            if(testResult)
                break;
        }
        assertTrue(testResult);
    }
    @Test
    void unsuccessfulEmail() throws SQLException {
        boolean testResult = false;
        while(result.next()){
            testResult = registerPage.checkEmail(result, "admin@admin.com");
            if(!testResult)
                break;
        }
        assertFalse(testResult);
    }
    RegisterPageTest() throws SQLException {
    }
}