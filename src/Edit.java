package Main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Edit extends JFrame {
    Connection connect = DatabaseConnection.initializeDatabase();
    private static final long serialVersionUID = 1L;
    private JLabel title ;
    private JPanel contentPane;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField phonenoField;
    private JTextField addressField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel phonenoLabel;
    private JLabel addressLabel;
    private JButton saveBtn;
    private JButton cancelBtn;


    /**
     * Create the frame.
     */
    public Edit(String paraName) {
        setTitle("Edit");
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

        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        usernameLabel.setBounds(43, 79, 88, 21);
        contentPane.add(usernameLabel);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        passwordLabel.setBounds(43, 141, 88, 21);
        contentPane.add(passwordLabel);

        phonenoLabel = new JLabel("Phone No");
        phonenoLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        phonenoLabel.setBounds(43, 204, 88, 21);
        contentPane.add(phonenoLabel);

        addressLabel = new JLabel("Address");
        addressLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        addressLabel.setBounds(43, 267, 88, 21);
        contentPane.add(addressLabel);

        usernameField = new JTextField();
        usernameField.setBounds(167, 80, 151, 20);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        passwordField = new JTextField();
        passwordField.setColumns(10);
        passwordField.setBounds(167, 142, 151, 20);
        contentPane.add(passwordField);

        phonenoField = new JTextField();
        phonenoField.setColumns(10);
        phonenoField.setBounds(167, 205, 151, 20);
        contentPane.add(phonenoField);

        addressField = new JTextField();
        addressField.setColumns(10);
        addressField.setBounds(167, 268, 151, 20);
        contentPane.add(addressField);

        saveBtn = new JButton("Save");
        saveBtn.setBounds(43, 375, 130, 33);
        contentPane.add(saveBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(188, 375, 130, 33);
        contentPane.add(cancelBtn);


        /**
         * Getting User's Info from Database
         */
            String ReadSql = "SELECT * FROM BankingDB.Users WHERE Usernames = ?";
        try {
            PreparedStatement stmt = connect.prepareStatement(ReadSql);
            stmt.setString(1,paraName);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String username = rs.getString("Usernames");
                String password = rs.getString("Passwords");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");

                usernameField.setText(username);
                passwordField.setText(password);
                phonenoField.setText(phone);
                addressField.setText(address);


            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        /**
         * Adding ActionListener to Btn
         */

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String ReadSql = "SELECT * FROM BankingDB.Users WHERE Usernames = ?";
                    PreparedStatement updateStmt = connect.prepareStatement(ReadSql);
                    updateStmt.setString(1,paraName);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String username = usernameField.getText();
                String password = passwordField.getText();
                String phone = phonenoField.getText();
                String address = addressField.getText();
                try {
                    String updateSQL = "Update BankingDB.Users SET Usernames = ? , Passwords = ? , Phone = ? , Address = ? WHERE Usernames = ?";
                    PreparedStatement stmt = connect.prepareStatement(updateSQL);
                   stmt.setString(1,username);
                   stmt.setString(2,password);
                   stmt.setString(3,phone);
                   stmt.setString(4,address);
                   stmt.setString(5,paraName);
                   stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Successfully Edited");
                    Edit.this.dispose();


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //Cancel Btn
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Edit.this.dispose();
            }
        });


    }

}

