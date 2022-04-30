import java.text.SimpleDateFormat;
import java.util.Date;

public class UserAddedObserver extends Observer{
    public UserAddedObserver (Users users){
        this.users = users;
    }
    @Override
    public void update() {
        SimpleDateFormat formatter = new SimpleDateFormat(" yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Registered: " + users.toString() + formatter.format(date));
    }
}
