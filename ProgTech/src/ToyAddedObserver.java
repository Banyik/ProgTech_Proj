import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToyAddedObserver extends Observer {
    public ToyAddedObserver(Toy toy){
        this.toy = toy;
    }
    @Override
    public void update() {
        File myObj = new File("log.txt");
        try {
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat formatter = new SimpleDateFormat(" yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Toy added: " + toy.toString() + formatter.format(date));
    }
}
