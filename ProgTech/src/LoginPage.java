import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginPage extends JFrame{
    private final LoginPage LoginPageForm = this;
    private JFormattedTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel LoginPanel;
    private JButton registerButton;


    public LoginPage() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String DB = "jdbc:mysql://localhost:3306/jatekaruhaz";
                String USERNAME = "root";
                String PASSWORD = "";
                try{
                    Connection connection = DriverManager.getConnection(DB, USERNAME, PASSWORD);

                    Statement stmt = connection.createStatement();
                    ResultSet result = stmt.executeQuery("SELECT * FROM user WHERE username = '"+ usernameField.getText() +"'; ");
                    String password = new String(passwordField.getPassword());
                    while(result.next()){
                        if(usernameField.getText().equals(result.getString("username")) && password.equals(result.getString("password"))){
                            //TODO: Átirányítás
                            Users user = new Users(result.getInt("id"), result.getString("username"), result.getString("auth"), result.getString("email"), result.getString("address"));
                            UserLoggedInObserver userObvserver = new UserLoggedInObserver(user);
                            userObvserver.update();
                            LoginPageForm.setEnabled(false);
                            ListToys ListToysForm = new ListToys(user, LoginPageForm);
                            break;
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Hibás jelszó vagy felhasználó név", "HIBA", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    stmt.close();
                    connection.close();
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }

            }

        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPageForm.setEnabled(false);
                RegisterPage registerPageForm = new RegisterPage();
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

