public class FragileToyDecorator  extends Toy{
    private Toy decoratableToy;
    public Toy getDecoratableToy() {
        return decoratableToy;
    }
    public void setDecoratableToy(Toy decoratableToy) {
        this.decoratableToy = decoratableToy;
    }

    public FragileToyDecorator(Toy toy) {
        this.decoratableToy = toy;
        this.setPrice(decoratableToy.getPrice() + 300);
        this.setId(toy.getId());
        this.setName(toy.getName());
    }
}
