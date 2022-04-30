import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JLabel prioDesc;
    private JLabel fragDesc;
    private JLabel codDesc;
    private JTextField tfToyId;
    private JTextField tfName;
    private JTextField tfPrice;
    private JLabel lbId;
    private JLabel lblName;
    private JLabel lblPrice;
    private JTextField tfUserId;

    private void setComponents(Toy toy, Users user) {
        tfToyId.setVisible(false);
        tfUserId.setVisible(false);
        setContentPane(OrderToyPanel);
        setTitle("Játék megrendelése");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        tfName.setText(toy.getName());
        tfPrice.setText(Integer.toString(toy.getPrice()));
        tfToyId.setText(Integer.toString(toy.getId()));
        tfUserId.setText(user.getUsername());
    }
    public OrderToy(Toy toy, Users user) {
        setComponents(toy, user);
        btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Toy toy = CreateOrderedToy();
                String DB_URL = "jdbc:mysql://localhost:3306/jatekaruhaz";
                String DB_USERNAME = "root";
                String PASSWORD = "";
                String toyId = tfToyId.getText();
                String userName = tfUserId.getText();
                String address = tfSzallitasiCim.getText();
                String price = Integer.toString(toy.getPrice());
                try{
                    InsertIntoOrder(DB_URL, DB_USERNAME, PASSWORD, toyId, userName, address, price);
                    JOptionPane.showMessageDialog(null, "Sikeres rendelés!\nFizetendő összeg: "+toy.getPrice()+" Ft.");
                    dispose();
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
            }

            private void InsertIntoOrder(String DB_URL,
                                         String USERNAME,
                                         String PASSWORD,
                                         String toyId,
                                         String userName,
                                         String address,
                                         String price) throws SQLException {
                Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement stmt = connection.createStatement();
                String sql = "INSERT INTO `order` ( `object_id`, `addressee`, `mailing address`, `price`) VALUES ('"+toyId+"', '"+userName+"', '"+address+"', '"+price+"')";
                stmt.execute(sql);
                stmt.close();
                connection.close();
            }

            private Toy CreateOrderedToy() {
                Toy toy = new Toy(Integer.parseInt(tfToyId.getText()), tfName.getText(), Integer.parseInt(tfPrice.getText()));
                if(cbCOD.isSelected()) {
                    toy = new CodToyDecorator(toy);
                }
                if(cbFragile.isSelected()) {
                    toy = new FragileToyDecorator(toy);
                }
                if(cbPriority.isSelected()) {
                    toy = new PriorityToyDecorator(toy);
                }
                return toy;
            }
        });
    }
    public static void main(String[] args) {
        Toy jatek = new Toy(1, "Pelda nev", 123);
        Users user = new Users(2, "user", "user", "user@user.com", "User, User utca -1");
        OrderToy OrderToyForm = new OrderToy(jatek, user);
    }
}
