import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderToy extends JFrame {
    private JPanel OrderToyPanel;
    private JTextField tfSzallitasiCim;
    private JCheckBox cbPriority;
    private JCheckBox cbCOD;
    private JCheckBox cbFragile;
    private JButton btnOrder;
    private final JFrame frame;
    private JLabel prioDesc;
    private JLabel fragDesc;
    private JLabel codDesc;
    private JLabel lbId;
    private final Toy toy;
    private final Users user;

    private void setComponents() {
        setContentPane(OrderToyPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Játék megrendelése");
        setSize(450, 300);
        setVisible(true);
    }
    public OrderToy(Toy toy, Users user, final JFrame frame) {
        setComponents();
        this.toy = toy;
        this.user = user;
        this.frame = this;

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                frame.setEnabled(true);
            }
        });
        btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Toy decorated = CreateOrderedToy();
                String DB_URL = "jdbc:mysql://localhost:3306/jatekaruhaz";
                String DB_USERNAME = "root";
                String PASSWORD = "";
                String address = tfSzallitasiCim.getText();
                try{
                    InsertIntoOrder(DB_URL, DB_USERNAME, PASSWORD, decorated, user.getUsername(), address);
                    JOptionPane.showMessageDialog(null, "Sikeres rendelés!\nFizetendő összeg: "+decorated.getPrice()+" Ft.");
                    frame.setEnabled(true);
                    dispose();
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
            private void InsertIntoOrder(String DB_URL,
                                         String USERNAME,
                                         String PASSWORD,
                                         Toy decorated,
                                         String userName,
                                         String address) throws SQLException {
                Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement stmt = connection.createStatement();
                String sql = "INSERT INTO `order` ( `object_id`, `addressee`, `mailing address`, `price`) VALUES ('"+decorated.getId()+"', '"+userName+"', '"+address+"', '"+decorated.getPrice()+"')";
                stmt.execute(sql);
                stmt.close();
                connection.close();
            }

            private Toy CreateOrderedToy() {
                Toy decorated = new Toy(toy.getId(), toy.getName(), toy.getPrice());
                if(cbCOD.isSelected()) {
                    decorated = new CodToyDecorator(decorated);
                }
                if(cbFragile.isSelected()) {
                    decorated = new FragileToyDecorator(decorated);
                }
                if(cbPriority.isSelected()) {
                    decorated = new PriorityToyDecorator(decorated);
                }
                return decorated;
            }
        });
    }
    /*public static void main(String[] args) {
        Toy jatek = new Toy(1, "Pelda nev", 123);
        Users user = new Users(2, "user", "user", "user@user.com", "User, User utca -1");
        OrderToy OrderToyForm = new OrderToy(jatek, user);
    }*/
}
