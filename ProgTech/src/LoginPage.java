import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage {
    private JFormattedTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel LoginPanel;

    public LoginPage() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hibás jelszó vagy felhasználó név", "HIBA", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginPage");
        frame.setContentPane(new LoginPage().LoginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}

