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

public class CashIn extends JFrame {
    Connection connect = DatabaseConnection.initializeDatabase();
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField amountInput;
    private JLabel title;
    private JLabel greetingLabel;
    private JLabel userNameLabel;
    private JLabel balanceLabel;
    private JLabel userAmountLabel;
    private JLabel amountLabel;
    private JButton submitBtn;
    private JButton exitBtn;


    /**
     * Create the frame.
     */
    public CashIn(String paraName) {
        setTitle("Cash In");
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

        greetingLabel = new JLabel("Hi ,");
        greetingLabel.setFont(new Font("FreeSans", Font.BOLD, 20));
        greetingLabel.setBounds(27, 91, 48, 33);
        contentPane.add(greetingLabel);

        userNameLabel = new JLabel();
        userNameLabel.setFont(new Font("FreeSans", Font.BOLD, 20));
        userNameLabel.setBounds(69, 91, 108, 33);
        contentPane.add(userNameLabel);

        balanceLabel = new JLabel("Your Current Balance is $ ");
        balanceLabel.setFont(new Font("FreeSans", Font.BOLD, 17));
        balanceLabel.setBounds(28, 153, 220, 33);
        contentPane.add(balanceLabel);

        userAmountLabel = new JLabel();
        userAmountLabel.setFont(new Font("FreeSans", Font.BOLD, 20));
        userAmountLabel.setBounds(242, 153, 108, 33);
        contentPane.add(userAmountLabel);

        amountLabel = new JLabel("Enter amount to cash in");
        amountLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        amountLabel.setBounds(27, 235, 221, 33);
        contentPane.add(amountLabel);

        amountInput = new JTextField();
        amountInput.setBounds(37, 280, 163, 33);
        contentPane.add(amountInput);
        amountInput.setColumns(10);

        submitBtn = new JButton("Submit");
        submitBtn.setBounds(27, 388, 130, 33);
        contentPane.add(submitBtn);

        exitBtn = new JButton("Exit");
        exitBtn.setBounds(191, 388, 130, 33);
        contentPane.add(exitBtn);

        /**
         * Getting User's Info  from Database
         */
        String sql = "SELECT * FROM BankingDB.Users WHERE Usernames = ?";
        try {
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.setString(1,paraName);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String name = rs.getString("Usernames");
                String amount = rs.getString("Amount");
                userNameLabel.setText(name);
                userAmountLabel.setText(amount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        /**
         * Adding ActionListener to Btn
         */

        // Amount Input Box
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newInputAmount = amountInput.getText();// New added input balance
                String currentBalanceQuery = "SELECT Amount FROM BankingDB.Users WHERE Usernames = ?";
                try {
                    PreparedStatement currentBalanceStmt = connect.prepareStatement(currentBalanceQuery);
                    currentBalanceStmt.setString(1,paraName);
                    ResultSet rs = currentBalanceStmt.executeQuery();
                    if(rs.next()){
                        int currentBalance = rs.getInt("Amount");
                        int updatedBalance = currentBalance + Integer.parseInt(newInputAmount);
                        String updateSQL = "UPDATE BankingDB.Users SET Amount = ? WHERE Usernames = ?";
                        PreparedStatement stmt = connect.prepareStatement(updateSQL);
                        stmt.setInt(1,updatedBalance);
                        stmt.setString(2,paraName);
                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(null," Cash In Successful","Cash In",JOptionPane.INFORMATION_MESSAGE);
                        CashIn.this.dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(null," Cash In Failed","Cash In",JOptionPane.ERROR_MESSAGE);
                    }



            } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

    }

}

