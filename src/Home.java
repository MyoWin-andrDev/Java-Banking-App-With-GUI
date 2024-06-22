package Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Home extends JFrame {
    Connection connect = DatabaseConnection.initializeDatabase();
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel title;
    private JLabel userNameHome;
    private JLabel phoneNoHome;
    private JLabel cityHome;
    private JLabel amountHome;
    private JLabel unitHome;
    private JToggleButton toggleBtn;
    private JButton cashInBtn;
    private JButton transferBtn;
    private JButton historyBtn;
    private JButton editBtn;
    private JButton logoutBtn;

    /**
     * Launch the application.
     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    Home frame = new Home();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    /**
     * Create the frame.
     */
    public Home(String paraName) {
        setTitle("Home Page");
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

        userNameHome = new JLabel();
        userNameHome.setFont(new Font("FreeSans", Font.BOLD, 15));
        userNameHome.setBounds(27, 84, 96, 27);
        contentPane.add(userNameHome);

        phoneNoHome = new JLabel();
        phoneNoHome.setFont(new Font("FreeSans", Font.BOLD, 15));
        phoneNoHome.setBounds(27, 112, 96, 27);
        contentPane.add(phoneNoHome);

        cityHome = new JLabel();
        cityHome.setFont(new Font("FreeSans", Font.BOLD, 15));
        cityHome.setBounds(27, 138, 96, 27);
        contentPane.add(cityHome);

        amountHome = new JLabel();
        amountHome.setFont(new Font("Gargi", Font.BOLD | Font.ITALIC, 18));
        amountHome.setBounds(52, 175, 82, 27);
        contentPane.add(amountHome);

        unitHome = new JLabel("$");
        unitHome.setFont(new Font("Gargi", Font.BOLD, 15));
        unitHome.setBounds(27, 177, 52, 27);
        contentPane.add(unitHome);

        toggleBtn = new JToggleButton("Hide");
        toggleBtn.setBounds(167, 182, 82, 25);
        contentPane.add(toggleBtn);

        cashInBtn = new JButton("Cash In");
        cashInBtn.setBounds(27, 287, 130, 33);
        contentPane.add(cashInBtn);

        transferBtn = new JButton("Transfer");
        transferBtn.setBounds(193, 287, 130, 33);
        contentPane.add(transferBtn);

        historyBtn = new JButton("History");
        historyBtn.setBounds(27, 359, 130, 33);
        contentPane.add(historyBtn);

        editBtn = new JButton("Edit");
        editBtn.setBounds(193, 359, 130, 33);
        contentPane.add(editBtn);

        logoutBtn = new JButton("Log Out");
        logoutBtn.setBounds(67, 418, 220, 33);
        contentPane.add(logoutBtn);


        /**
         * Getting User's Info  from Database
         */
        try {
            connect = DatabaseConnection.initializeDatabase();
            String sql = "SELECT * FROM BankingDB.Users WHERE Usernames=?";
            PreparedStatement stmt = connect.prepareStatement(sql);
            stmt.setString(1, paraName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("Usernames");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                String amount = rs.getString("Amount");

                userNameHome.setText(userName);
                phoneNoHome.setText(phone);
                cityHome.setText(address);
                amountHome.setText(amount);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        /**
         * Adding ActionListener to Btn
         */

        //Hide Btn
        toggleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isSelected = toggleBtn.isSelected();
                if (isSelected) {
                    amountHome.setText("*****");
                    toggleBtn.setText("Show");
                } else {
                    connect = DatabaseConnection.initializeDatabase();
                    String sql = "SELECT * FROM BankingDB.Users WHERE Usernames = ?";
                    try {
                        PreparedStatement stmt = connect.prepareStatement(sql);
                        stmt.setString(1, paraName);
                        ResultSet rs = stmt.executeQuery();
                        while (rs.next()) {
                            String amount = rs.getString("Amount");
                            amountHome.setText(amount);
                            toggleBtn.setText("Hide");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        //CashIn Btn

        cashInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CashIn cashIn = new CashIn(paraName);
                cashIn.setVisible(true);
            }
        });

        //Transfer Btn
        transferBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = JOptionPane.showInputDialog(null,"Enter Phone Number To Transfer ","Transfer",JOptionPane.OK_CANCEL_OPTION);
                System.out.println(result);
                String sql = "SELECT * FROM BankingDB.Users WHERE Phone = ?";
                try {
                    PreparedStatement stmt = connect.prepareStatement(sql);
                    stmt.setString(1,result);
                    ResultSet rs = stmt.executeQuery();
                    if(rs.next()){
                        String paraPhone = rs.getString("Phone");
                        Transfer transfer = new Transfer(paraName,paraPhone);//First argument is Sender and the other is receiver
                        transfer.setVisible(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Invalid Number","Transfer",JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        //Edit Btn
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPasswordField passwordField = new JPasswordField();
                int result = JOptionPane.showConfirmDialog(null, passwordField, "Enter Password To Edit Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String password = String.valueOf(passwordField.getPassword());


                    connect = DatabaseConnection.initializeDatabase();
                    String sql = "SELECT * FROM BankingDB.Users WHERE Usernames = ? AND Passwords = ?";
                    try {
                        PreparedStatement stmt = connect.prepareStatement(sql);
                        stmt.setString(1, paraName);
                        stmt.setString(2, password);
                        ResultSet rs = stmt.executeQuery();
                        if(rs.next()){
                            Edit edit = new Edit(paraName);
                            edit.setVisible(true);
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Incorrect Password","Edit",JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        //Histroy Btn
        historyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                History history = new History(paraName);
                history.setVisible(true);
            }
        });

        //Logout Btn
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null,"Are You Sure To Log Out ?","Log Out",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
                if(result == JOptionPane.OK_OPTION){
                    Home.this.dispose();
                }
            }
        });


    }
}


