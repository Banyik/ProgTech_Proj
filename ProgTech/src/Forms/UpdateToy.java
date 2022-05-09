package Forms;

import BaseClasses.Toy;
import Exceptions.*;
import Observers.ToyUpdatedObserver;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateToy extends JFrame{
    private JTextField nameTField;
    private JTextField priceTField;
    private JLabel updateToyTitle;
    private JButton btnUpdate;
    private JPanel updateToyPanel;
    private final OrderToy orderToyFrame;
    private final ListToys listToysFrame;

    private final Toy toy;

    public UpdateToy(OrderToy orderToyFrame, ListToys listToysFrame, Toy toy) {
        this.orderToyFrame = orderToyFrame;
        this.listToysFrame = listToysFrame;
        this.toy = toy;
        nameTField.setText(toy.getName());
        priceTField.setText(Integer.toString(toy.getPrice()));
        setContentPane(updateToyPanel);
        setTitle("Játék módosítása");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);


        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                try {
                    listToysFrame.setEnabled(true);
                    listToysFrame.updateToyTable();
                    dispose();
                    orderToyFrame.dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (invalidToyIdException | invalidToyNameException | invalidToyPriceException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        priceTField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
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
                        String sql = "UPDATE toy SET name='"+nameTField.getText()+"', price='"+priceTField.getText()+"' WHERE id="+toy.getId();
                        System.out.println(sql);
                        stmt.execute(sql);
                        stmt.close();
                        connection.close();
                        JOptionPane.showMessageDialog(null, "Sikeres módosítás!");

                        ToyUpdatedObserver toyUpdatedObserver = new ToyUpdatedObserver(new Toy(toy.getId(),nameTField.getText(), Integer.parseInt(priceTField.getText())));
                        toyUpdatedObserver.update();
                        listToysFrame.setEnabled(true);
                        listToysFrame.updateToyTable();
                        dispose();
                        orderToyFrame.dispose();
                    }
                    catch(Exception ex) {
                        ex.printStackTrace();
                    }
                } catch (priceValueIsIllegalException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (priceFieldIsEmptyException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (nameFieldIsEmptyException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                catch(Exception ex) {
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
