
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATMApp {

    // Simulated database of users
    static String[] userNames = {"Nikita", "Shubham", "Preeti"};
    static String[] pins = {"1234", "4321", "1111"};
    static double[] balances = {1000.0, 750.0, 1200.0};

    // Keep track of logged-in user index
    static int currentUserIndex = -1;

    static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ATMApp::showLoginScreen);
    }

    public static void showLoginScreen() {
        frame = new JFrame("ATM Login");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(230, 240, 255));

        JLabel title = new JLabel("Welcome to Simple ATM");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(100, 20, 250, 30);
        frame.add(title);

        JLabel userLabel = new JLabel("User Name:");
        userLabel.setBounds(50, 70, 100, 25);
        frame.add(userLabel);

        JComboBox<String> userDropdown = new JComboBox<>(userNames);
        userDropdown.setBounds(150, 70, 180, 25);
        frame.add(userDropdown);

        JLabel pinLabel = new JLabel("Enter PIN:");
        pinLabel.setBounds(50, 110, 100, 25);
        frame.add(pinLabel);

        JPasswordField pinField = new JPasswordField();
        pinField.setBounds(150, 110, 180, 25);
        frame.add(pinField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(150, 150, 100, 30);
        loginBtn.setBackground(new Color(100, 149, 237));
        loginBtn.setForeground(Color.WHITE);
        frame.add(loginBtn);

        loginBtn.addActionListener(e -> {
            String selectedUser = (String) userDropdown.getSelectedItem();
            String enteredPin = new String(pinField.getPassword());

            for (int i = 0; i < userNames.length; i++) {
                if (userNames[i].equals(selectedUser) && pins[i].equals(enteredPin)) {
                    currentUserIndex = i;
                    showMainMenu();
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Incorrect PIN. Try again.");
        });

        frame.setVisible(true);
    }

    public static void showMainMenu() {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setTitle("ATM - Welcome " + userNames[currentUserIndex]);

        JLabel welcomeLabel = new JLabel("Hello, " + userNames[currentUserIndex]);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBounds(120, 20, 200, 30);
        frame.add(welcomeLabel);

        JLabel balanceLabel = new JLabel("Balance: $" + balances[currentUserIndex]);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        balanceLabel.setBounds(130, 60, 200, 25);
        frame.add(balanceLabel);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(130, 100, 140, 30);
        frame.add(depositBtn);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(130, 140, 140, 30);
        frame.add(withdrawBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(130, 180, 140, 30);
        logoutBtn.setBackground(new Color(220, 20, 60));
        logoutBtn.setForeground(Color.WHITE);
        frame.add(logoutBtn);

        depositBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter deposit amount:");
            try {
                double amount = Double.parseDouble(input);
                if (amount > 0) {
                    balances[currentUserIndex] += amount;
                    balanceLabel.setText("Balance: $" + balances[currentUserIndex]);
                } else {
                    JOptionPane.showMessageDialog(frame, "Enter a positive amount.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input.");
            }
        });

        withdrawBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter withdrawal amount:");
            try {
                double amount = Double.parseDouble(input);
                if (amount > 0 && amount <= balances[currentUserIndex]) {
                    balances[currentUserIndex] -= amount;
                    balanceLabel.setText("Balance: $" + balances[currentUserIndex]);
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient funds or invalid amount.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input.");
            }
        });

        logoutBtn.addActionListener(e -> {
            currentUserIndex = -1;
            showLoginScreen();
        });

        frame.repaint();
    }
}


