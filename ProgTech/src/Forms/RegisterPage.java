package Forms;

import BaseClasses.Users;
import Observers.UserAddedObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterPage extends JFrame{
    private final RegisterPage registerPageForm = this;
    private JPasswordField passwordField;
    private JFormattedTextField usernameField;
    private JButton registerButton;
    private JPasswordField passwordAgainField;
    private JPanel RegisterPanel;
    private JFormattedTextField emailField;
    private JButton loginButton;
    public boolean checkPassword(String password, String passwordAgain){
        if(password.equals(passwordAgain) && !password.isEmpty())
            return true;
        else
            JOptionPane.showMessageDialog(null, "A jelszavak nem egyeznek meg", "HIBA", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    public boolean checkUsername(ResultSet result, String username) throws SQLException {
        if(!username.equals(result.getString("username")))
            return true;
        else
            JOptionPane.showMessageDialog(null, "Ez a felhasználónév már létezik", "HIBA", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    public boolean checkEmail(ResultSet result, String email) throws SQLException {
        if(!email.equals(result.getString("email")))
            return true;
        else
            JOptionPane.showMessageDialog(null, "Ezzel az E-mail-lel már regisztráltak", "HIBA", JOptionPane.ERROR_MESSAGE);
        return false;
    }
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
                    Connection connection = DriverManager.getConnection(DB, USERNAME, PASSWORD);
                    Statement stmt = connection.createStatement();
                    ResultSet result = stmt.executeQuery("SELECT * FROM user");
                    String password = new String(passwordField.getPassword());
                    String passwordAgain = new String(passwordAgainField.getPassword());
                    if(!password.isEmpty() && checkPassword(password, passwordAgain)){
                        while(result.next()){
                            if(!usernameField.getText().isEmpty() && checkUsername(result, usernameField.getText())){
                                if(!emailField.getText().isEmpty() && checkEmail(result, emailField.getText())){
                                    stmt.execute("INSERT INTO user(email, username, password, auth) VALUES ('"+emailField.getText()+"', '"+usernameField.getText()+"', '"+password+"', 'User')");
                                    ResultSet resultThis = stmt.executeQuery("SELECT * FROM user WHERE username = '"+ usernameField.getText() +"' ;");
                                    while (resultThis.next()){
                                        Users user = new Users(resultThis.getInt("id"), resultThis.getString("username"), resultThis.getString("auth"), resultThis.getString("email"));
                                        UserAddedObserver userObvserver = new UserAddedObserver(user);
                                        userObvserver.update();
                                        LoginPage LoginPageForm = new LoginPage();
                                        dispose();
                                    }
                                    break;
                                }
                                else
                                    break;
                            }
                            else
                                break;
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
