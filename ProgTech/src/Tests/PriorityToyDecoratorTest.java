package Tests;

import BaseClasses.Toy;
import Decorators.PriorityToyDecorator;
import Exceptions.invalidToyIdException;
import Exceptions.invalidToyNameException;
import Exceptions.invalidToyPriceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriorityToyDecoratorTest {
    @Test
    void priorityShouldIncreasePriceBy450() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        Toy testToy = new Toy(1, "Test Toy", 1);
        Toy codToy = new PriorityToyDecorator(testToy);
        Toy expectedDecorated = new Toy(1, "Test Toy", 1 + 450);
        assertEquals(expectedDecorated.getPrice(), codToy.getPrice());
    }
}