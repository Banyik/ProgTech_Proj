import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListToys extends JFrame {
    private JPanel ListToysPanel;
    private JButton button1;
    private final ListToys ListToysForm = this;
    private JTable table;
    private final List<Toy> Toys = getToys();
    public ListToys() throws SQLException {
        setTitle("Játékok áruháza");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        createTable();

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
        }
        stmt.close();
        connection.close();
        return toys;
    }
    private void createTable() {
        JFrame frame = this;
        ListToysPanel = new JPanel();
        String[][] ToysArr = new String[Toys.size()][3];
        for (int i = 0; i < Toys.size()-1; i++) {
            ToysArr[i][0] = String.valueOf(Toys.get(i).getId());
            ToysArr[i][1] = String.valueOf(Toys.get(i).getName());
            System.out.println(Toys.get(i).toString());
            ToysArr[i][2] = String.valueOf(Toys.get(i).getPrice());
        }
        String[] header = { "Id", "Megnevezés", "Egységár" };
        table = new JTable(ToysArr, header);
        for (int c = 0; c < table.getColumnCount(); c++)
        {
            Class<?> col_class = table.getColumnClass(c);
            table.setDefaultEditor(col_class, null);        // remove editor
        }
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListToysPanel.add(new JScrollPane(table));
        frame.add(ListToysPanel);
        frame.setSize(550, 400);
        frame.setVisible(true);
        setContentPane(ListToysPanel);
    }

    public static void main(String[] args) throws SQLException {
        new ListToys();
    }
}