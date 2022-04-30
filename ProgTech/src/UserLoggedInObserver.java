import java.text.SimpleDateFormat;
import java.util.Date;

public class UserLoggedInObserver extends Observer{
    public UserLoggedInObserver (Users users){
        this.users = users;
    }
    @Override
    public void update() {
        SimpleDateFormat formatter = new SimpleDateFormat(" yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Logged in: " + users.toString() + formatter.format(date));
    }
}
