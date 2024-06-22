package Main;

import java.awt.Font;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class History extends JFrame {
    Connection connect = DatabaseConnection.initializeDatabase();
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;


    /**
     * Create the frame.
     */
    public History(String senderName) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel title = new JLabel("Java Banking");
        title.setBounds(123, 10, 104, 21);
        title.setFont(new Font("Nimbus Sans L", Font.BOLD, 16));
        contentPane.add(title);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(24, 54, 661, 390);
        contentPane.add(scrollPane);

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "ID", "Date", "Receiver", "Amount", "Note"
                }
        ));
        table.getColumnModel().getColumn(0).setPreferredWidth(45);
        table.getColumnModel().getColumn(1).setPreferredWidth(270);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(210);
        scrollPane.setViewportView(table);

        /**
         * Getting User's Info  from Database
         */
        try {
            String sql = "SELECT * FROM BankingDB.Transactions WHERE Sender =?";
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.setString(1,senderName);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Timestamp transTimestamp = rs.getTimestamp("Trans_Time");
                String transTimeString = new SimpleDateFormat("mm:HH        MM/dd/yyyy").format(transTimestamp);
                int ID = rs.getInt("Trans_ID");
                String Date = String.valueOf(rs.getTimestamp("Trans_Time"));
                String Receiver = rs.getString("Receiver");
                String Amount = rs.getString("Trans_Amount");
                String Note = rs.getString("Trans_Note");
                ((DefaultTableModel)table.getModel()).addRow(new Object[]{ID , transTimeString, Receiver , Amount, Note});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

