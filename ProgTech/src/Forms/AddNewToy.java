package Forms;

import BaseClasses.Toy;
import Exceptions.*;
import Observers.ToyAddedObserver;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
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
                try{
                    validateTextFields();
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
                        ToyAddedObserver toyAddedObserver = new ToyAddedObserver(new Toy(Toy.idCounter, ToyName, Integer.parseInt(ToyPrice)));
                        toyAddedObserver.update();
                        JOptionPane.showMessageDialog(null, "Sikeres adatfelvitel!");
                        prevFrame.setEnabled(true);
                        prevFrame.updateToyTable();
                        dispose();
                    }
                    catch (invalidToyIdException | invalidToyNameException | invalidToyPriceException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                    catch(Exception ex) {
                        ex.printStackTrace();
                    }
                } catch (priceValueIsIllegalException | priceFieldIsEmptyException | nameFieldIsEmptyException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Valami hiba történt");
                }
            }
        });
    }
    private void validateTextFields() throws nameFieldIsEmptyException, priceFieldIsEmptyException, priceValueIsIllegalException {
        if (nameTField.getText().length() == 0) {
            throw new nameFieldIsEmptyException("A termék nevét üresen hagyta!");
        }

        if (priceTField.getText().length() == 0) {
            throw new priceFieldIsEmptyException("Az árnak nem adott értéket!");
        }
        if (Integer.parseInt(priceTField.getText()) <= 0) {
            throw new priceValueIsIllegalException("Az ár értéke nem lehet negatív!");
        }
    }
}
