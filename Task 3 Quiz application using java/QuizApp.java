import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

// ---------- LOGIN FRAME ----------
class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JLabel statusLabel;

    private static final  String [] USERNAME ={ "Nikita","Shubham","preeti"};
    private static final String []PASSWORD = {"2912" ,"54321","1931"};
    
    public LoginFrame() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 220);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 248, 255));

        userField = new JTextField(15);
        passField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);

        loginButton.addActionListener(e -> login());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 248, 255));
        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void login() {
        String username = userField.getText();
        String password = new String(passField.getPassword());
        
      for (int i = 0; i < USERNAME.length; i++) {
           if (username.equals(USERNAME[i]) && password.equals(PASSWORD[i])) {
               dispose();
               new QuizFrame();
               return;
            }
        }

        
        statusLabel.setText("Invalid login.");
        
    }
}

// ---------- QUIZ FRAME WITH STYLING ----------
class QuizFrame extends JFrame {
    private JLabel timerLabel;
    private int secondsElapsed = 0;
    private Timer timer;

    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup group;
    private JButton nextButton;

    private String[] questions = {
        "What is the capital of France?",
        "Which language is used for Android development?",
         "What is the size of an int variable in Java ?",
        "Which keyword is used to inherit a class in Java ?",
        "Which method is the entry point of any Java program?"
                        

    };

    private String[][] choices = {
        {"Paris", "London", "Rome", "Berlin"},
        {"Java", "Python", "Swift", "Kotlin"},
        {"4 byte","8 byte","2 byte","depend on system"},
        {"this","super","implements","extends"},
        {"start()"," run()","main()"," init()"}
                        
                        
                        
    };

    private int[] correctAnswers = {0, 3, 0, 3, 2};

    private int currentQuestion = 0;
    private int score = 0;

    public QuizFrame() {
        setTitle("Quiz With Timer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);

        // Top bar for timer
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        timerLabel = new JLabel("Time: 0s", SwingConstants.CENTER);
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(timerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Quiz panel
        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));
        quizPanel.setBackground(new Color(245, 245, 245));
        quizPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        quizPanel.add(questionLabel);

        options = new JRadioButton[4];
        group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
            options[i].setBackground(new Color(245, 245, 245));
            group.add(options[i]);
            quizPanel.add(options[i]);
        }

        nextButton = new JButton("Next");
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.setBackground(new Color(60, 179, 113));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        nextButton.setFocusPainted(false);
        nextButton.addActionListener(e -> checkAnswerAndNext());

        quizPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        quizPanel.add(nextButton);

        add(quizPanel, BorderLayout.CENTER);

        startTimer();
        showQuestion();

        setVisible(true);
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                secondsElapsed++;
                SwingUtilities.invokeLater(() ->
                    timerLabel.setText("Time: " + secondsElapsed + "s"));
            }
        }, 1000, 1000);
    }

    private void showQuestion() {
        if (currentQuestion < questions.length) {
            questionLabel.setText((currentQuestion + 1) + "." + questions[currentQuestion]);
            for (int i = 0; i < 4; i++) {
                options[i].setText(choices[currentQuestion][i]);
                options[i].setSelected(false);
            }
        } else {
            endQuiz();
        }
    }

    private void checkAnswerAndNext() {
        int selected = -1;
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selected = i;
                break;
            }
        }

        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Please select an answer!");
            return;
        }

        if (selected == correctAnswers[currentQuestion]) {
            score++;
        }

        currentQuestion++;
        showQuestion();
    }

    private void endQuiz() {
        timer.cancel();
        String result = "🎉 Quiz Complete!\n\n" +
                        "✅ Score: " + score + " / " + questions.length + "\n" +
                        "⏱ Time Taken: " + secondsElapsed + " seconds";

        JOptionPane.showMessageDialog(this, result, "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}

   