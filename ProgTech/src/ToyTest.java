import static org.junit.jupiter.api.Assertions.*;

class ToyTest {

    @org.junit.jupiter.api.Test
    void returnedIdShouldBeOne() throws invalidToyIdException, invalidToyNameException {
        Toy testToy = new Toy(1, "TestToy", 1);
        int expected = 1;
        int actual = testToy.getId();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void setIdShouldWorkWithOne() throws invalidToyIdException, invalidToyNameException {
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
    void returnedNameShouldBeTestToy() throws invalidToyIdException, invalidToyNameException {
        Toy testToy = new Toy(1, "TestToy", 1);
        String expected = "TestToy";
        String actual = testToy.getName();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void setNameShouldWorkWithTestToy() throws invalidToyIdException, invalidToyNameException {
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
    void getPrice() {
    }

    @org.junit.jupiter.api.Test
    void setPrice() {
    }
}