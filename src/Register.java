package Main;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Register extends JFrame {
    /**
     * Regular Expression
     */
    private static final String VALID_USERNAME_PATTERN = "[A-Z][a-zA-Z0-9_ ]*";
    private static final String VALID_PASSWORD_PATTERN = "[A-Z][a-zA-Z0-9_#$@?%]*";
    private static final String VALID_PHONENO_PATTERN = "09[0-9]{6,12}";
    private static final String VALID_EMAIL_PATTERN = "[a-z0-9_]*+@+[a-z]+.+[a-z]{3}";
    private static final String VALID_ADDRESS_PATTERN = "[a-zA-Z0-9(),./]*";

    Connection connect = DatabaseConnection.initializeDatabase();
    private static final long serialVersionUID = 1L;
    private JLabel title;
    private JLabel registerLabel;
    private JPanel contentPane;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField emailField;
    private JTextField phonenoField;
    private JTextField addressField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel emailLabel;
    private JLabel phonenoLabel;
    private JLabel addressLabel;
    private JLabel genderRadio;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private JRadioButton otherRadio;
    private ButtonGroup gender;
    private JButton registerBtn;
    private JLabel validUsername;
    private JLabel validPassword;
    private JLabel validPhoneNo;
    private JLabel validEmail;
    private JLabel validAddress;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Register frame = new Register();
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
    public Register() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 350, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        title = new JLabel("Java Banking");
        title.setFont(new Font("Nimbus Sans L", Font.BOLD, 16));
        title.setBounds(27, 12, 130, 33);
        contentPane.add(title);

        registerLabel = new JLabel("Registration Form");
        registerLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        registerLabel.setBounds(94, 58, 160, 33);
        contentPane.add(registerLabel);

        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        usernameLabel.setBounds(27, 103, 92, 21);
        contentPane.add(usernameLabel);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        passwordLabel.setBounds(27, 156, 92, 21);
        contentPane.add(passwordLabel);

        emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        emailLabel.setBounds(27, 212, 92, 21);
        contentPane.add(emailLabel);

        phonenoLabel = new JLabel("Phone No");
        phonenoLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        phonenoLabel.setBounds(27, 266, 92, 21);
        contentPane.add(phonenoLabel);

        addressLabel = new JLabel("Address");
        addressLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        addressLabel.setBounds(27, 325, 92, 21);
        contentPane.add(addressLabel);

        genderRadio = new JLabel("Gender");
        genderRadio.setFont(new Font("Dialog", Font.BOLD, 14));
        genderRadio.setBounds(27, 378, 92, 21);
        contentPane.add(genderRadio);

        registerBtn = new JButton("Register");
        registerBtn.setBounds(112, 418, 130, 33);
        contentPane.add(registerBtn);

        usernameField = new JTextField();
        usernameField.setBounds(156, 104, 160, 21);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        passwordField = new JTextField();
        passwordField.setColumns(10);
        passwordField.setBounds(156, 157, 160, 21);
        contentPane.add(passwordField);

        emailField = new JTextField();
        emailField.setColumns(10);
        emailField.setBounds(156, 213, 160, 21);
        contentPane.add(emailField);

        phonenoField = new JTextField();
        phonenoField.setColumns(10);
        phonenoField.setBounds(156, 267, 160, 21);
        contentPane.add(phonenoField);

        addressField = new JTextField();
        addressField.setColumns(10);
        addressField.setBounds(156, 326, 160, 21);
        contentPane.add(addressField);

        maleRadio = new JRadioButton("Male");
        maleRadio.setBounds(133, 377, 59, 23);
        contentPane.add(maleRadio);

        femaleRadio = new JRadioButton("Female");
        femaleRadio.setBounds(196, 377, 81, 23);
        contentPane.add(femaleRadio);

        otherRadio = new JRadioButton("Other");
        otherRadio.setBounds(275, 377, 67, 23);
        contentPane.add(otherRadio);

        gender = new ButtonGroup();
        gender.add(maleRadio);
        gender.add(femaleRadio);
        gender.add(otherRadio);

        validUsername = new JLabel("Enter Valid Username.");
        validUsername.setForeground(Color.RED);
        validUsername.setBounds(156, 125, 160, 15);
        contentPane.add(validUsername);
        validUsername.setVisible(false);

        validPassword = new JLabel("Enter Valid Password.");
        validPassword.setForeground(Color.RED);
        validPassword.setBounds(156, 176, 160, 15);
        contentPane.add(validPassword);
        validPassword.setVisible(false);

        validEmail = new JLabel("Enter Valid Email Address.");
        validEmail.setForeground(Color.RED);
        validEmail.setBounds(156, 234, 186, 15);
        contentPane.add(validEmail);
        validEmail.setVisible(false);

        validPhoneNo = new JLabel("Enter Valid Phone Number.");
        validPhoneNo.setForeground(Color.RED);
        validPhoneNo.setBounds(156, 289, 194, 15);
        contentPane.add(validPhoneNo);
        validPhoneNo.setVisible(false);

        validAddress = new JLabel("Enter Valid Address.");
        validAddress.setForeground(Color.RED);
        validAddress.setBounds(156, 346, 160, 15);
        contentPane.add(validAddress);
        validAddress.setVisible(false);


        /**
         * Adding ActionListener to Btn
         */
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String email = emailField.getText();
                String phone = phonenoField.getText();
                String address = addressField.getText();

                String sql = "INSERT INTO BankingDB.Users (Usernames,Passwords,Email,Phone,Address,Gender) values (?,?,?,?,?,?)";
                PreparedStatement stmt = null;
                try {
                    stmt = connect.prepareStatement(sql);
                    //UserName InputField
                    if(username.matches(VALID_USERNAME_PATTERN)){
                        stmt.setString(1,username);
                    }
                    else{
                        validUsername.setVisible(true);
                        usernameField.setText("");
                    }

                    //Password InputField
                    if(password.matches(VALID_PASSWORD_PATTERN)){
                        stmt.setString(2,password);
                    }
                    else{
                        validPassword.setVisible(true);
                        passwordField.setText("");
                    }

                    //Email InputField
                    if(email.matches(VALID_EMAIL_PATTERN)){
                        stmt.setString(3,email);
                    }
                    else{
                        validEmail.setVisible(true);
                        emailField.setText("");
                    }

                    //Phone No InputField
                    if(phone.matches(VALID_PHONENO_PATTERN)){
                        stmt.setString(4,phone);
                    }
                    else{
                        validPhoneNo.setVisible(true);
                        phonenoField.setText("");
                    }

                    //Address InputField
                    if(address.matches(VALID_ADDRESS_PATTERN)){
                        stmt.setString(5,address);
                    }
                    else{
                        validAddress.setVisible(true);
                        addressField.setText("");
                    }

                    //Gender Radio Button
                    String selectedGender ;
                    if(maleRadio.isSelected()){
                        selectedGender = maleRadio.getText();
                        stmt.setString(6,"Male");
                    }else if(femaleRadio.isSelected()){
                        selectedGender = femaleRadio.getText();
                        stmt.setString(6,"Female");
                    }else if(otherRadio.isSelected()){
                        selectedGender = otherRadio.getText();
                        stmt.setString(6,"Other");
                    }
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null,"You Have Got 3000 Ks For Signning Up","Gift",JOptionPane.INFORMATION_MESSAGE);
                    Register.this.dispose();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });


    }

}
