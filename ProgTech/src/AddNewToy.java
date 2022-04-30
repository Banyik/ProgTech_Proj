import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.channels.SelectionKey;
import java.sql.*;

public class AddNewToy extends JFrame {
    private JTextField nameTField;
    private JTextField priceTField;
    private JLabel newtoyTitle;
    private JButton addBtn;

    private JPanel addnewtoyPanel;
    public AddNewToy() {
        setContentPane(addnewtoyPanel);
        setTitle("Új játék felvitele");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        priceTField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateTextFields()) {
                    String ToyName = nameTField.getText();
                    String ToyPrice = priceTField.getText();

                    String DB_URL = "jdbc:mysql://localhost:3306/jatekaruhaz";
                    String USERNAME = "root";
                    String PASSWORD = "";

                    try{
                        Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

                        Statement stmt = connection.createStatement();
                        String sql = "INSERT INTO toy(name, price) VALUES('"+ToyName+"', "+ToyPrice+")";
                        stmt.execute(sql);
                        stmt.close();
                        connection.close();
                        JOptionPane.showMessageDialog(null, "Sikeres adatfelvitel!");
                        // Előző oldal értesítése az UPDATE metódussal
                        dispose();
                    }
                    catch(Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
    }

    private boolean validateTextFields() {
        if (nameTField.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Adjon meg egy Megnevezést a temréknek!");
            return false;
        }

        if (priceTField.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Adjon meg egy Egységárat a(z) Autónak!");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        AddNewToy addnewtoyForm = new AddNewToy();
    }
}
