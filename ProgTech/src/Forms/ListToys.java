package Forms;

import BaseClasses.Toy;
import BaseClasses.Users;
import Exceptions.invalidToyIdException;
import Exceptions.invalidToyNameException;
import Exceptions.invalidToyPriceException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListToys extends JFrame {
    private final Users userLoggedIn;
    private final LoginPage LoginPageForm;
    private JButton btnNewToy;
    private JButton btnLogout;

    public static final String[] columns = {
            "Id", "Megnevezés", "Egységár"
    };
    private DefaultTableModel model = new DefaultTableModel(columns, 0);
    private JTable table = new JTable(model);
    private JPanel mainPanel = new JPanel(new BorderLayout());
    private final ListToys ListToysForm = this;
    private final List<Toy> Toys = getToys();
    public ListToys(Users user, LoginPage LoginPageForm) throws SQLException, invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        this.userLoggedIn = user;
        this.LoginPageForm = LoginPageForm;
        setTitle("Játékok áruháza");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        createButton();
        createTable();
        this.add(mainPanel);
        this.setSize(550, 400);
        this.setVisible(true);

        setContentPane(mainPanel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (table.getSelectedRow() > -1) {
                    Toy toy = Toys.get(table.getSelectedRow());
                    ListToysForm.setEnabled(false);
                    OrderToy OrderToy = new OrderToy(toy, userLoggedIn, ListToysForm);
                }
            }
        });
        btnNewToy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListToysForm.setEnabled(false);
                AddNewToy AddNewToyForm = new AddNewToy(ListToysForm);
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPageForm.setEnabled(true);
                dispose();
            }
        });

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                LoginPageForm.setEnabled(true);
            }
        });
    }

    private List<Toy> getToys() throws SQLException, invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        String DB_URL = "jdbc:mysql://localhost:3306/jatekaruhaz";
        String DB_USERNAME = "root";
        String PASSWORD = "";
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, PASSWORD);
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM toy";
        ResultSet result = stmt.executeQuery(sql);
        List<Toy> toys = new ArrayList<>();
        while (result.next()) {
            Toy toy = new Toy(Integer.parseInt(result.getString("id")), result.getString("name"), Integer.parseInt(result.getString("price")));
            toys.add(toy);
        }
        stmt.close();
        connection.close();
        return toys;
    }
    private void createTable() {
        for (int i = 0; i < Toys.size(); i++) {
            model.addRow(
                    new Object[]{
                            Toys.get(i).getId(),
                            Toys.get(i).getName(),
                            Toys.get(i).getPrice()
                    }
            );
            Toy.idCounter = Toys.get(i).getId()+1;
        }
        System.out.println("Next ID: " + Toy.idCounter);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
    }
    public void updateToyTable() throws SQLException, invalidToyIdException, invalidToyNameException, invalidToyPriceException {
        String DB_URL = "jdbc:mysql://localhost:3306/jatekaruhaz";
        String DB_USERNAME = "root";
        String PASSWORD = "";
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, PASSWORD);
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM toy";
        ResultSet result = stmt.executeQuery(sql);
        Toys.removeAll(Toys);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        while (result.next()) {
            Toy toy = new Toy(Integer.parseInt(result.getString("id")), result.getString("name"), Integer.parseInt(result.getString("price")));
            Toys.add(toy);
            model.addRow(new Object[]{String.valueOf(toy.getId()), String.valueOf(toy.getName()), String.valueOf(toy.getPrice())});

        }
        stmt.close();
        connection.close();



    }
    private void createButton() {
        JPanel buttonPanel = new JPanel();
        btnNewToy = new JButton("Új játék felvitele");
        btnNewToy.setVisible(false);
        if(this.userLoggedIn.getAuth().equals("admin")) {
            btnNewToy.setVisible(true);
        }
        btnLogout = new JButton("Kijelentkezés");
        buttonPanel.add(btnNewToy);
        buttonPanel.add(btnLogout);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
}