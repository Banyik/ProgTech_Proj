package Decorators;

import Exceptions.invalidToyIdException;
import Exceptions.invalidToyNameException;
import Exceptions.invalidToyPriceException;
import BaseClasses.Toy;
import javax.swing.*;

public class CodToyDecorator  extends Toy {
    private Toy decoratableToy;

    public CodToyDecorator(Toy toy) throws invalidToyIdException, invalidToyNameException {
        try{
            this.decoratableToy = toy;
            this.setPrice(decoratableToy.getPrice() + 150);
            this.setId(toy.getId());
            this.setName(toy.getName());
        } catch(invalidToyNameException  | invalidToyIdException | invalidToyPriceException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
