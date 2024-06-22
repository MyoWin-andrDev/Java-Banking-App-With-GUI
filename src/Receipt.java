package Main;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class Receipt extends JFrame {
    Connection connect = DatabaseConnection.initializeDatabase();
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel title;
    private JLabel paymentSuccessLabel;
    private JLabel trans_TimeLabel;
    private JLabel trans_NoLabel;
    private JLabel trans_To;
    private JLabel trans_Amount;
    private JLabel trans_Note;
    private JButton saveBtn;
    private JLabel trans_TimeDB;
    private JLabel trans_NoDB;
    private JLabel trans_ToDB;
    private JLabel trans_AmountDB;
    private JLabel trans_NoteDB;
    private JLabel trans_Unit;

    /**
     * Create the frame.
     */
    public Receipt(String senderName) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 350, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        title = new JLabel("Java Banking");
        title.setFont(new Font("Nimbus Sans L", Font.BOLD, 16));
        title.setBounds(123, 10, 104, 21);
        contentPane.add(title);

        paymentSuccessLabel = new JLabel("Payment Successful");
        paymentSuccessLabel.setFont(new Font("Jamrul", Font.BOLD, 17));
        paymentSuccessLabel.setBounds(79, 43, 224, 48);
        contentPane.add(paymentSuccessLabel);

        trans_TimeLabel = new JLabel("Transaction Time");
        trans_TimeLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        trans_TimeLabel.setBounds(32, 110, 136, 21);
        contentPane.add(trans_TimeLabel);

        trans_NoLabel = new JLabel("Transaction No.");
        trans_NoLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        trans_NoLabel.setBounds(32, 171, 136, 21);
        contentPane.add(trans_NoLabel);

        trans_To = new JLabel("Transfer To");
        trans_To.setFont(new Font("Dialog", Font.BOLD, 14));
        trans_To.setBounds(32, 229, 122, 21);
        contentPane.add(trans_To);

        trans_Amount = new JLabel("Amount");
        trans_Amount.setFont(new Font("Dialog", Font.BOLD, 14));
        trans_Amount.setBounds(32, 287, 88, 21);
        contentPane.add(trans_Amount);

        trans_Note = new JLabel("Notes");
        trans_Note.setFont(new Font("Dialog", Font.BOLD, 14));
        trans_Note.setBounds(32, 345, 88, 21);
        contentPane.add(trans_Note);

        saveBtn = new JButton("Save");
        saveBtn.setBounds(110, 393, 130, 33);
        contentPane.add(saveBtn);

        trans_TimeDB = new JLabel("Phyo Hein");
        trans_TimeDB.setFont(new Font("FreeSans", Font.BOLD, 15));
        trans_TimeDB.setBounds(195, 108, 143, 27);
        contentPane.add(trans_TimeDB);

        trans_NoDB = new JLabel("Phyo Hein");
        trans_NoDB.setFont(new Font("FreeSans", Font.BOLD, 15));
        trans_NoDB.setBounds(195, 169, 143, 27);
        contentPane.add(trans_NoDB);

        trans_ToDB = new JLabel("Phyo Hein");
        trans_ToDB.setFont(new Font("FreeSans", Font.BOLD, 15));
        trans_ToDB.setBounds(195, 227, 143, 27);
        contentPane.add(trans_ToDB);

        trans_AmountDB = new JLabel("1000");
        trans_AmountDB.setFont(new Font("FreeSans", Font.BOLD, 15));
        trans_AmountDB.setBounds(207, 287, 143, 27);
        contentPane.add(trans_AmountDB);

        trans_NoteDB = new JLabel("Phyo Hein");
        trans_NoteDB.setFont(new Font("FreeSans", Font.BOLD, 15));
        trans_NoteDB.setBounds(195, 343, 143, 27);
        contentPane.add(trans_NoteDB);

        trans_Unit = new JLabel("$");
        trans_Unit.setFont(new Font("FreeSans", Font.BOLD, 15));
        trans_Unit.setBounds(195, 290, 17, 21);
        contentPane.add(trans_Unit);

        /**
         * Getting User's Info  from Database
         */

        //Getting Lastest Transaction ID
        try {
            String sql = """
                    SELECT MAX(Trans_ID)
                    FROM BankingDB.Transactions AS t
                    RIGHT JOIN BankingDB.Users AS u
                    ON t.Sender = u.Usernames
                    WHERE Sender = ?
                    GROUP BY Sender;""";


            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.setString(1,senderName);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int MAX_TRANSID = rs.getInt("MAX(Trans_ID)");
                System.out.println(MAX_TRANSID);
                String readSql = "SELECT Trans_Time, Sender, Receiver, Trans_Amount, Trans_Note FROM BankingDB.Transactions WHERE Trans_ID =?";
                PreparedStatement readStmt = connect.prepareStatement(readSql);
                readStmt.setInt(1,MAX_TRANSID);
                ResultSet readRs = readStmt.executeQuery();
                while(readRs.next()){
                    Timestamp transTimestamp = readRs.getTimestamp("Trans_Time");
                    String transTimeString = new SimpleDateFormat("mm:HH MM/dd/yyyy").format(transTimestamp);
                    String Date = String.valueOf(readRs.getTimestamp("Trans_Time"));
                    String Receiver = readRs.getString("Receiver");
                    String Amount = readRs.getString("Trans_Amount");
                    String Note = readRs.getString("Trans_Note");

                    trans_TimeDB.setText(transTimeString);
                    trans_NoDB.setText(String.valueOf(MAX_TRANSID));
                    trans_ToDB.setText(Receiver);
                    trans_AmountDB.setText(Amount);
                    trans_NoteDB.setText(Note);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Save Btn
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Receipt.this.dispose();

            }
        });
    }


}

