import java.text.SimpleDateFormat;
import java.util.Date;

public class ToyAddedObserver extends Observer {
    public ToyAddedObserver(Toy toy){
        this.toy = toy;
    }
    @Override
    public void update() {
        SimpleDateFormat formatter = new SimpleDateFormat(" yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Toy added: " + toy.toString() + formatter.format(date));
    }
}
