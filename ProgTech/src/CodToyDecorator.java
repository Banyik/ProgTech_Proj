public class CodToyDecorator  extends Toy{
    private Toy decoratableToy;
    public Toy getDecoratableToy() {
        return decoratableToy;
    }
    public void setDecoratableToy(Toy decoratableToy) {
        this.decoratableToy = decoratableToy;
    }

    public CodToyDecorator(Toy toy) {
        this.decoratableToy = toy;
        this.setPrice(decoratableToy.getPrice() + 150);
        this.setId(toy.getId());
        this.setName(toy.getName());
    }
}
