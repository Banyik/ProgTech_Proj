package Forms;

import BaseClasses.Toy;
import BaseClasses.Users;
import Decorators.CodToyDecorator;
import Decorators.FragileToyDecorator;
import Decorators.PriorityToyDecorator;
import Exceptions.invalidToyIdException;
import Exceptions.invalidToyNameException;
import Exceptions.invalidToyPriceException;
import Observers.ToyOrderedObserver;

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
    private final OrderToy frame;
    private final ListToys listToysFrame;
    private JLabel prioDesc;
    private JLabel fragDesc;
    private JLabel codDesc;
    private JButton btnDelete;
    private JButton btnEdit;
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

    public OrderToy(Toy toy, Users user, ListToys prevFrame) {
        setComponents();
        this.listToysFrame = prevFrame;
        this.toy = toy;
        this.user = user;
        this.frame = this;
        if(this.user.getAuth().equals("admin")) {
            btnOrder.setVisible(false);
            btnDelete.setVisible(true);
            btnEdit.setVisible(true);
        }
        else {
            btnOrder.setVisible(true);
            btnDelete.setVisible(false);
            btnEdit.setVisible(false);
        }
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                prevFrame.setEnabled(true);
            }
        });
        btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Toy decorated = CreateOrderedToy();
                    String DB_URL = "jdbc:mysql://localhost:3306/jatekaruhaz";
                    String DB_USERNAME = "root";
                    String PASSWORD = "";
                    String address = tfSzallitasiCim.getText();
                    try {
                        if (validateOrder()) {
                            InsertIntoOrder(DB_URL, DB_USERNAME, PASSWORD, decorated, user.getUsername(), address);
                            JOptionPane.showMessageDialog(null, "Sikeres rendelés!\nFizetendő összeg: " + decorated.getPrice() + " Ft.");

                            prevFrame.setEnabled(true);
                            dispose();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } catch(invalidToyIdException | invalidToyNameException | invalidToyPriceException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());

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
                ToyOrderedObserver toyOrderedObvserver = new ToyOrderedObserver (toy);
                toyOrderedObvserver.update();
            }

            private Toy CreateOrderedToy() throws invalidToyIdException, invalidToyNameException, invalidToyPriceException {
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
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String DB_URL = "jdbc:mysql://localhost:3306/jatekaruhaz";
                    String DB_USERNAME = "root";
                    String PASSWORD = "";
                    Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, PASSWORD);
                    Statement stmt = connection.createStatement();
                    String sql = "DELETE FROM toy WHERE id = "+toy.getId();
                    stmt.execute(sql);
                    sql = "ALTER TABLE `toy` AUTO_INCREMENT = 1";
                    stmt.execute(sql);
                    stmt.close();
                    connection.close();

                    listToysFrame.updateToyTable();
                    prevFrame.setEnabled(true);
                    dispose();
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Hiba adódott a játék törlése során...");
                } catch (invalidToyIdException | invalidToyNameException | invalidToyPriceException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setEnabled(false);
                UpdateToy updateToyForm = new UpdateToy(frame, prevFrame, toy);
            }
        });

    }

    public boolean validateOrder() {
        if(tfSzallitasiCim.getText().length() <= 0)
        {
            JOptionPane.showMessageDialog(null, "Adjon meg egy kiszállítási címet!");
            return false;
        }
        return true;
    }
}
