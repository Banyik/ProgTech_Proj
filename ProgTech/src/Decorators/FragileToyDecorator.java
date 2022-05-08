package Decorators;

import BaseClasses.Toy;
import Exceptions.invalidToyIdException;
import Exceptions.invalidToyNameException;
import Exceptions.invalidToyPriceException;

import javax.swing.*;

public class FragileToyDecorator  extends Toy {
    private Toy decoratableToy;

    public FragileToyDecorator(Toy toy) throws invalidToyIdException, invalidToyNameException {
        try{
            this.decoratableToy = toy;
            this.setPrice(decoratableToy.getPrice() + 300);
            this.setId(toy.getId());
            this.setName(toy.getName());
        } catch(invalidToyNameException  | invalidToyIdException ex)  {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (invalidToyPriceException e) {
            throw new RuntimeException(e);
        }

    }
}
