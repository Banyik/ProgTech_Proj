import javax.swing.*;

public class PriorityToyDecorator extends Toy{
    private Toy decoratableToy;
    public Toy getDecoratableToy() {
        return decoratableToy;
    }
    public void setDecoratableToy(Toy decoratableToy) {
        this.decoratableToy = decoratableToy;
    }

    public PriorityToyDecorator(Toy toy) throws invalidToyIdException, invalidToyNameException {
        try{
            this.decoratableToy = toy;
            this.setPrice(decoratableToy.getPrice() + 450);
            this.setId(toy.getId());
            this.setName(toy.getName());
        } catch(invalidToyNameException  | invalidToyIdException ex)  {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
