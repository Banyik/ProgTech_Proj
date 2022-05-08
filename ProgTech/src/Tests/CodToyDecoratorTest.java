package Tests;

import BaseClasses.Toy;
import Decorators.CodToyDecorator;
import Exceptions.invalidToyIdException;
import Exceptions.invalidToyNameException;
import Exceptions.invalidToyPriceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodToyDecoratorTest {

    @Test
    void codShouldIncreasePriceBy150() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        Toy testToy = new Toy(1, "Test Toy", 1);
        Toy codToy = new CodToyDecorator(testToy);
        Toy expectedDecorated = new Toy(1, "Test Toy", 1 + 150);
        assertEquals(expectedDecorated.getPrice(), codToy.getPrice());
    }

}