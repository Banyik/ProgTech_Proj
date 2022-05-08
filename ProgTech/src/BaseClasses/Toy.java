package BaseClasses;

import Exceptions.invalidToyIdException;
import Exceptions.invalidToyNameException;
import Exceptions.invalidToyPriceException;

import java.util.Objects;

public class Toy {
    public static int idCounter = 1;

    private int id;
    public int getId() {
        return this.id;
    }
    public void setId(int id) throws invalidToyIdException {
        if(id <= 0)
            throw new invalidToyIdException("Érvénytelen Játék Id");
        this.id = id;
    }

    private String name;
    public String getName() {
        return this.name;
    }
    public void setName(String name) throws invalidToyNameException {
        if(name.equals(""))
            throw new invalidToyNameException("A játék neve nem lehet üres!");
        this.name = name;
    }

    private int price;
    public int getPrice() {
        return this.price;
    }
    public void setPrice(int price) throws invalidToyPriceException {
        if(price <= 0)
            throw new invalidToyPriceException("Érvénytelen a beírt ár!");
        this.price = price;
    }

    public Toy() {

    }
    public Toy(int id, String name, int price) throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        setId(id);
        setName(name);
        setPrice(price);
    }

    @Override
    public String toString() {
        return "BaseClasses.Toy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Toy toy = (Toy) o;
        return id == toy.id && price == toy.price && Objects.equals(name, toy.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
