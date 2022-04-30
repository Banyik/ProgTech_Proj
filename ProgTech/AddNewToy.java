import javax.swing.*;

public class AddNewToy extends JFrame {
    private JTextField nameTField;
    private JTextField priceTField;
    private JLabel newtoyTitle;
    private JButton addBtn;
    private JComboBox cbTypes;
    private JPanel addnewtoyPanel;

    public AddNewToy() {
        setContentPane(addnewtoyPanel);
        setTitle("Új játék felvitele");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        cbTypes = new JComboBox();
    }

    public static void main(String[] args) {
        AddNewToy addnewtoyForm = new AddNewToy();
    }
}
