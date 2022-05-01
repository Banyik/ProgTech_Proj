import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListToys extends JFrame {
    private JButton btnNewToy;
    public static final String[] columns = {
            "Id", "Megnevezés", "Egységár"
    };
    private DefaultTableModel model = new DefaultTableModel(columns, 0);
    private JTable table = new JTable(model);
    private JPanel mainPanel = new JPanel(new BorderLayout());
    private final ListToys ListToysForm = this;
    private final List<Toy> Toys = getToys();
    public ListToys() throws SQLException {

        setTitle("Játékok áruháza");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                    Users user = new Users(2, "user", "user", "user@user.com", "User, User utca -1");
                    ListToysForm.setEnabled(false);
                    OrderToy OrderToy = new OrderToy(toy, user, ListToysForm);
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
    }

    private List<Toy> getToys() throws SQLException {
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
            System.out.println(toy.toString());
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
        }
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

    }
    public void updateToyTable() throws SQLException {
        String DB_URL = "jdbc:mysql://localhost:3306/jatekaruhaz";
        String DB_USERNAME = "root";
        String PASSWORD = "";
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, PASSWORD);
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM toy";
        ResultSet result = stmt.executeQuery(sql);
        List<Toy> toys = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        while (result.next()) {
            Toy toy = new Toy(Integer.parseInt(result.getString("id")), result.getString("name"), Integer.parseInt(result.getString("price")));
            if(!this.Toys.contains(toy))
            {
                Toys.add(toy);
                model.addRow(new Object[]{String.valueOf(toy.getId()), String.valueOf(toy.getName()), String.valueOf(toy.getPrice())});
            }
        }
        stmt.close();
        connection.close();



    }
    private void createButton() {
        JPanel buttonPanel = new JPanel();
        btnNewToy = new JButton("Új játék felvitele");
        //if(this.user.getAuth() != "admin") {
        //      button.setVisible(false);
        //}
        buttonPanel.add(btnNewToy);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    public static void main(String[] args) throws SQLException {
        new ListToys();
    }
}