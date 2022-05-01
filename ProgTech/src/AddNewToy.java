import javax.swing.*;
import java.awt.event.*;
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
    private final JFrame frame = this;
    private JPanel addnewtoyPanel;
    private final ListToys prevFrame;
    public AddNewToy(ListToys listtoysframe) {
        this.prevFrame = listtoysframe;
        setContentPane(addnewtoyPanel);
        setTitle("Új játék felvitele");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);

        priceTField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                prevFrame.setEnabled(true);
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
                        prevFrame.setEnabled(true);
                        prevFrame.updateToyTable();
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
            JOptionPane.showMessageDialog(null, "Adjon meg egy Egységárat a terméknek!");
            return false;
        }
        if (Integer.parseInt(priceTField.getText()) <= 0) {
            JOptionPane.showMessageDialog(null, "Érvényes árat adjon meg a terméknek!");
            return false;
        }
        return true;
    }
}
