package Tests;

import BaseClasses.Toy;
import Decorators.CodToyDecorator;
import Decorators.FragileToyDecorator;
import Exceptions.invalidToyIdException;
import Exceptions.invalidToyNameException;
import Exceptions.invalidToyPriceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FragileToyDecoratorTest {
    @Test
    void fragileShouldIncreasePriceBy300() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        Toy testToy = new Toy(1, "Test Toy", 1);
        Toy codToy = new FragileToyDecorator(testToy);
        Toy expectedDecorated = new Toy(1, "Test Toy", 1 + 300);
        assertEquals(expectedDecorated.getPrice(), codToy.getPrice());
    }
}