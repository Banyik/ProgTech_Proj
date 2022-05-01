import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterPage extends JFrame{
    private final RegisterPage registerPageForm = this;
    private JPasswordField passwordField;
    private JFormattedTextField usernameField;
    private JButton registerButton;
    private JPasswordField passwordAgainField;
    private JPanel RegisterPanel;
    private JFormattedTextField emailField;
    private JButton loginButton;
    public RegisterPage() {
        this.setContentPane(RegisterPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String DB = "jdbc:mysql://localhost:3306/jatekaruhaz";
                String USERNAME = "root";
                String PASSWORD = "";
                try{
                    boolean isAbleToRegister = false;
                    Connection connection = DriverManager.getConnection(DB, USERNAME, PASSWORD);

                    Statement stmt = connection.createStatement();
                    ResultSet result = stmt.executeQuery("SELECT * FROM user");

                    String password = new String(passwordField.getPassword());
                    String passwordAgain = new String(passwordAgainField.getPassword());
                    if(password.equals(passwordAgain) && !password.isEmpty()){
                        while(result.next()){
                            if(!usernameField.getText().equals(result.getString("username")) && !usernameField.getText().isEmpty()){

                                if(!emailField.getText().equals(result.getString("email")) && !emailField.getText().isEmpty()){
                                    isAbleToRegister = true;
                                }
                                else if(!emailField.getText().isEmpty()){
                                    JOptionPane.showMessageDialog(null, "Ezzel az E-mail-lel már regisztráltak", "HIBA", JOptionPane.ERROR_MESSAGE);
                                    isAbleToRegister = false;
                                }
                            }
                            else if(!usernameField.getText().isEmpty()){
                                JOptionPane.showMessageDialog(null, "Ez a felhasználónév már létezik", "HIBA", JOptionPane.ERROR_MESSAGE);
                                isAbleToRegister = false;
                            }
                        }
                    }
                    else if(!password.isEmpty()){
                        JOptionPane.showMessageDialog(null, "A jelszavak nem egyeznek meg", "HIBA", JOptionPane.ERROR_MESSAGE);
                    }
                    if(isAbleToRegister){
                        stmt.execute("INSERT INTO user(email, username, password, auth, address) VALUES ('"+emailField.getText()+"', '"+usernameField.getText()+"', '"+password+"', 'User', 'N/A')");
                        ResultSet resultThis = stmt.executeQuery("SELECT * FROM user WHERE username = '"+ usernameField.getText() +"' ;");
                        while (resultThis.next()){
                            Users user = new Users(resultThis.getInt("id"), resultThis.getString("username"), resultThis.getString("auth"), resultThis.getString("email"), "N/A");
                            UserAddedObserver userObvserver = new UserAddedObserver(user);
                            userObvserver.update();
                            LoginPage LoginPageForm = new LoginPage();
                            dispose();
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
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage LoginPageForm = new LoginPage();
                dispose();
            }
        });
    }


}
