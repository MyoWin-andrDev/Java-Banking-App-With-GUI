package Main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Transfer extends JFrame {

    Connection connect = DatabaseConnection.initializeDatabase();
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField transferField;
    private JTextField noteField;


    /**
     * Create the frame.
     */
    public Transfer(String senderName,String receiverPhone) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 350, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel title = new JLabel("Java Banking");
        title.setFont(new Font("Nimbus Sans L", Font.BOLD, 16));
        title.setBounds(123, 10, 104, 21);
        contentPane.add(title);

        JLabel receiverLabel = new JLabel("Receiver Details");
        receiverLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        receiverLabel.setBounds(32, 73, 154, 21);
        contentPane.add(receiverLabel);

        JLabel name = new JLabel("Name");
        name.setFont(new Font("Dialog", Font.BOLD, 14));
        name.setBounds(42, 106, 70, 15);
        contentPane.add(name);

        JLabel phone = new JLabel("Phone ");
        phone.setFont(new Font("Dialog", Font.BOLD, 14));
        phone.setBounds(42, 154, 70, 15);
        contentPane.add(phone);

        JLabel address = new JLabel("Address");
        address.setFont(new Font("Dialog", Font.BOLD, 14));
        address.setBounds(42, 200, 70, 15);
        contentPane.add(address);

        JLabel nameDB = new JLabel("Phyo Hein");
        nameDB.setFont(new Font("FreeSans", Font.BOLD, 15));
        nameDB.setBounds(187, 101, 96, 27);
        contentPane.add(nameDB);

        JLabel phoneDB = new JLabel("09775138454");
        phoneDB.setFont(new Font("FreeSans", Font.BOLD, 15));
        phoneDB.setBounds(187, 149, 96, 27);
        contentPane.add(phoneDB);

        JLabel addressDB = new JLabel("Bangkok");
        addressDB.setFont(new Font("FreeSans", Font.BOLD, 15));
        addressDB.setBounds(187, 195, 96, 27);
        contentPane.add(addressDB);

        JLabel transferLabel = new JLabel("Enter amount to transfer");
        transferLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        transferLabel.setBounds(32, 252, 229, 21);
        contentPane.add(transferLabel);

        transferField = new JTextField();
        transferField.setBounds(32, 285, 229, 27);
        contentPane.add(transferField);
        transferField.setColumns(10);

        JButton submitBtn = new JButton("Submit");
        submitBtn.setBounds(32, 404, 130, 33);
        contentPane.add(submitBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(187, 404, 130, 33);
        contentPane.add(cancelBtn);

        JLabel noteLabel = new JLabel("Note ");
        noteLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        noteLabel.setBounds(32, 324, 229, 21);
        contentPane.add(noteLabel);

        noteField = new JTextField();
        noteField.setColumns(10);
        noteField.setBounds(32, 357, 229, 27);
        contentPane.add(noteField);

        /**
         * Getting User's Info from Database
         */
            String sql = "SELECT * FROM BankingDB.Users WHERE Phone = ?";
        try {
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.setString(1,receiverPhone);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String receiverUserName = rs.getString("Usernames");
                String userAddress = rs.getString("Address");
                nameDB.setText(receiverUserName);
                addressDB.setText(userAddress);

                //Getting the last 4 digits of receiver phone no

                String subStringPhone = receiverPhone.substring(receiverPhone.length()-4);
                String lastFourNumber= "*******"+subStringPhone;
                phoneDB.setText(lastFourNumber);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        /**
         * Adding ActionListener to Btn
         */
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /**
                 * Setting Transfer Amount Input Box
                 */
                try {
                    String transferAmount = transferField.getText();
                    int intTransferAmount = Integer.parseInt(transferAmount);// Changing String to int value
                    String senderReadSql = "SELECT * FROM BankingDB.Users WHERE Usernames = ?";
                    String senderUpdateSql = "UPDATE BankingDB.Users SET Amount = ? WHERE Usernames = ?";

                    //Reading Sender Balance
                    PreparedStatement senderReadStmt = connect.prepareStatement(senderReadSql);
                    senderReadStmt.setString(1,senderName);
                    ResultSet senderRs = senderReadStmt.executeQuery();
                    int senderRemainingBalance = 0;
                    if(senderRs.next()){
                        senderRemainingBalance = senderRs.getInt("Amount");
                    }

                    //Updating Sender Balance
                    PreparedStatement senderUpdateStmt = connect.prepareStatement(senderUpdateSql);
                    senderUpdateStmt.setString(2,senderName);

                        if( senderRemainingBalance > intTransferAmount ){
                            senderUpdateStmt.setInt(1,senderRemainingBalance - intTransferAmount);
                            senderUpdateStmt.executeUpdate();

                            //Getting Receiver Remaining Balance Similar To Line No : 97
                            String sql = "SELECT * FROM BankingDB.Users WHERE Phone = ?";
                            PreparedStatement stmt = connect.prepareStatement(sql);
                            stmt.setString(1,receiverPhone);
                            ResultSet rs = stmt.executeQuery();
                            while(rs.next()) {
                                String receiverName = rs.getString("Usernames");
                                int receiverBalance = rs.getInt("Amount");
                                int receiverUpdateBalance = receiverBalance + intTransferAmount;
                                String receiverUpdateSql = "UPDATE BankingDB.Users SET Amount = ? WHERE Phone = ?";
                                PreparedStatement receiverUpdateStmt = connect.prepareStatement(receiverUpdateSql);
                                receiverUpdateStmt.setInt(1, receiverUpdateBalance);
                                receiverUpdateStmt.setString(2, receiverPhone);
                                receiverUpdateStmt.executeUpdate();
                                String note = noteField.getText();

                                TransactionNote trans = new TransactionNote();
                                if(note.isEmpty()){
                                    trans.noteOverloading(senderName,receiverName,intTransferAmount);
                                    Receipt receipt = new Receipt(senderName);
                                    receipt.setVisible(true);
                                }
                                else{
                                    trans.noteOverloading(senderName,receiverName,intTransferAmount,note);
                                    Receipt receipt = new Receipt(senderName);
                                    receipt.setVisible(true);
                                }

                            }

                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Insufficient Amount To Transfer.\n Please Cash In","Transfer",JOptionPane.ERROR_MESSAGE);
                        }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transfer.this.dispose();
            }
        });

    }
}

class TransactionNote {

    void noteOverloading(String senderName, String receiverPhone, int Trans_Amount ) {
        {
            try {
                Connection connect = DatabaseConnection.initializeDatabase();
                String createSql = "INSERT INTO BankingDB.Transactions (Trans_Time, Sender, Receiver,Trans_Amount) values(?,?,?,?)";
                PreparedStatement createStmt = connect.prepareStatement(createSql);

                // Getting the current timestamp
                LocalDateTime now = LocalDateTime.now();
                Timestamp currentTimeStamp = Timestamp.valueOf(now);
                createStmt.setTimestamp(1, currentTimeStamp);
                createStmt.setString(2,senderName);
                createStmt.setString(3,receiverPhone);
                createStmt.setInt(4,Trans_Amount);
                createStmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    void noteOverloading(String senderName, String receiverPhone, int Trans_Amount ,String note){
        {
            try {
                Connection connect = DatabaseConnection.initializeDatabase();
                String createSql = "INSERT INTO BankingDB.Transactions (Trans_Time, Sender, Receiver,Trans_Amount, Trans_Note) values(?,?,?,?,?)";
                PreparedStatement createStmt = connect.prepareStatement(createSql);

                // Getting the current timestamp
                LocalDateTime now = LocalDateTime.now();
                Timestamp currentTimeStamp = Timestamp.valueOf(now);
                createStmt.setTimestamp(1, currentTimeStamp);
                createStmt.setString(2,senderName);
                createStmt.setString(3,receiverPhone);
                createStmt.setInt(4,Trans_Amount);
                createStmt.setString(5,note);
                createStmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}




