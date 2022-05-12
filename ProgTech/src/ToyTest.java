import BaseClasses.Toy;
import Exceptions.invalidToyIdException;
import Exceptions.invalidToyNameException;
import Exceptions.invalidToyPriceException;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class ToyTest {

    @org.junit.jupiter.api.Test
    void returnedIdShouldBeOne() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        Toy testToy = new Toy(1, "TestToy", 1);
        int expected = 1;
        int actual = testToy.getId();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void setIdShouldWorkWithOne() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        Toy testToy = new Toy(1, "TestToy", 1);
        int expected = 1;
        int actual = testToy.getId();
        assertEquals(expected, actual);
    }
    @org.junit.jupiter.api.Test
    void setIdShouldThrowExceptionForZero() {
        assertThrows(invalidToyIdException.class, () -> {
            Toy testToy = new Toy(0, "TestToy", 1);
        });
    }
    @org.junit.jupiter.api.Test
    void returnedNameShouldBeTestToy() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        Toy testToy = new Toy(1, "TestToy", 1);
        String expected = "TestToy";
        String actual = testToy.getName();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void setNameShouldWorkWithTestToy() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        Toy testToy = new Toy(1, "TestToy", 1);
        String expected = "TestToy";
        String actual = testToy.getName();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void setNameShouldThrowExceptionForEmpty() throws invalidToyIdException {
        assertThrows(invalidToyNameException.class, () -> {
            Toy testToy = new Toy(1, "", 1);
        });
    }

    @org.junit.jupiter.api.Test
    void getPriceShouldReturnOne() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        Toy testToy = new Toy(1, "Test BaseClasses.Toy", 1);
        int expected = 1;
        int actual = testToy.getId();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void setPriceShouldReturnExceptionForZeroOrLess() {
        assertThrows(invalidToyPriceException.class, () -> {
            Toy testToy = new Toy(1, "Test BaseClasses.Toy", 0);
        });
    }

    @org.junit.jupiter.api.Test
    void setPriceShouldWorkWithNoParamsConst() throws invalidToyPriceException {
        Toy toy = new Toy();
        toy.setPrice(100);
        int expected = 100;
        int actual = toy.getPrice();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void toStringTest() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        Toy testToy = new Toy(1, "Test ToString BaseClasses.Toy", 100);
        String expected = "Toy{id=1, name='Test ToString BaseClasses.Toy\', price=100}";
        String actual = testToy.toString();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void equalsShoudlReturnFalseForNull() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        Toy testToy1 = null;
        Toy testToy2 = new Toy(1, "Test Toy", 2);
        assertFalse(testToy2.equals(testToy1));
    }

    @org.junit.jupiter.api.Test
    void equalsShoudlReturnFalseForOtherClasses() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        String testToy1 = "False Toy";
        Toy testToy2 = new Toy(1, "Test Toy", 2);
        assertFalse(testToy2.equals(testToy1));
    }

    @org.junit.jupiter.api.Test
    void equalsShoudlReturnFalseForOtherToy() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        Toy testToy1 = new Toy(1, "Test Toy", 2);
        Toy testToy2 = new Toy(2, "Test Toy", 2);
        assertFalse(testToy2.equals(testToy1));
    }

    @org.junit.jupiter.api.Test
    void equalsShoudlReturnFalseForSameToy() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        Toy testToy1 = new Toy(1, "Test Toy", 2);
        Toy testToy2 = new Toy(1, "Test Toy", 2);
        assertTrue(testToy2.equals(testToy1));
    }

    @org.junit.jupiter.api.Test
    void TestToyInsert() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException, SQLException {
        String toyName = "Test Toy";
        int toyPrice = 9999;
        String DB_URL = "jdbc:mysql://localhost:3306/jatekaruhaz";
        String USERNAME = "root";
        String PASSWORD = "";
        Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        Statement stmt = connection.createStatement();
        String sql = "INSERT INTO toy(name, price) VALUES('"+toyName+"', "+toyPrice+")";
        stmt.execute(sql);
        sql = "SELECT * FROM toy ORDER BY id DESC LIMIT 1";
        ResultSet result = stmt.executeQuery(sql);
        Toy actual = null;
        Toy expected = null;
        int InsertedToyId = 0;
        while(result.next()) {
            InsertedToyId = Integer.parseInt(result.getString("id"));
            String InsertedToyName = result.getString("name");
            int InsertedToyPrice = Integer.parseInt(result.getString("price"));
            actual = new Toy(InsertedToyId, InsertedToyName, InsertedToyPrice);
            expected = new Toy(InsertedToyId, toyName, toyPrice);
        }
        sql = "DELETE FROM toy WHERE id="+InsertedToyId;
        stmt.execute(sql);
        sql = "ALTER TABLE `toy` AUTO_INCREMENT = 1";
        stmt.execute(sql);
        connection.close();
        stmt.close();
        assertEquals(expected, actual);
    }
}