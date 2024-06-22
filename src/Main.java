package Main;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JLabel bankTitle;
    private JPanel loginPanel;
    private JLabel loginUserName;
    private JLabel loginPassword;
    private JButton loginBtn;
    private JButton signUpBtn;
    private JButton exitBtn;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Main() {
        setTitle("Bank Management System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 350, 500);
        contentPane = new JPanel();
        contentPane.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        bankTitle = new JLabel("Java Banking");
        bankTitle.setFont(new Font("Nimbus Sans L", Font.BOLD | Font.ITALIC, 30));
        bankTitle.setBounds(66, 28, 251, 64);
        contentPane.add(bankTitle);

        loginPanel = new JPanel();
        loginPanel.setBackground(UIManager.getColor("Button.background"));
        loginPanel.setForeground(Color.DARK_GRAY);
        loginPanel.setBounds(40, 104, 266, 335);
        contentPane.add(loginPanel);
        loginPanel.setLayout(null);

        loginUserName = new JLabel("Username");
        loginUserName.setFont(new Font("Dialog", Font.BOLD, 14));
        loginUserName.setBounds(25, 26, 92, 21);
        loginPanel.add(loginUserName);

        userNameField = new JTextField();
        userNameField.setBounds(25, 59, 159, 21);
        loginPanel.add(userNameField);
        userNameField.setColumns(10);

        loginPassword = new JLabel("Password");
        loginPassword.setFont(new Font("Dialog", Font.BOLD, 14));
        loginPassword.setBounds(25, 126, 92, 21);
        loginPanel.add(loginPassword);

        passwordField = new JPasswordField();
        passwordField.setColumns(10);
        passwordField.setBounds(25, 159, 159, 21);
        loginPanel.add(passwordField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(12, 226, 108, 25);
        loginPanel.add(loginBtn);

        signUpBtn = new JButton("Register");

        signUpBtn.setBounds(146, 226, 108, 25);
        loginPanel.add(signUpBtn);

        exitBtn = new JButton("Exit");
        exitBtn.setBounds(76, 281, 108, 25);
        loginPanel.add(exitBtn);

        /**
         * Adding ActionPerformed to Btn
         */

        //Login Btn
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                try {
                Connection connect = DatabaseConnection.initializeDatabase();
                String sql = "SELECT * FROM  BankingDB.Users WHERE Usernames = ? AND Passwords = ?";
                PreparedStatement stmt = connect.prepareStatement(sql);
                stmt.setString(1,userName);
                stmt.setString(2,password);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(null,"Successfully Logged In","Login Info",JOptionPane.INFORMATION_MESSAGE);
                    userNameField.setText("");
                    passwordField.setText("");
                    String paraName = userName;
                    Home home = new Home(paraName);
                    home.setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Invalid Username or Password","Login Info",JOptionPane.ERROR_MESSAGE);
                    userNameField.setText("");
                    passwordField.setText("");
                }


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //SignUp Btn
        signUpBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            Register register = new Register();
            register.setVisible(true);
            }
        });

        //ExitBtn
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}

