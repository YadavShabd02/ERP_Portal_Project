import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.EmptyBorder;

public class IIITDLoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public IIITDLoginPage() {
        setTitle("ERP Login - IIIT Delhi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage;
            {
                try {
                    backgroundImage = new ImageIcon("background.jpg").getImage();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Background image not found! Make sure 'background.jpg' is in your classpath.");
         }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        setContentPane(backgroundPanel);

        JPanel loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Dimension arcs = new Dimension(25, 25);
                int width = getWidth();
                int height = getHeight();
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                graphics.setColor(getBackground());
                graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
            }
        };
        loginPanel.setOpaque(false);
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setPreferredSize(new Dimension(550, 500));
        loginPanel.setBackground(Color.decode("#FFFFFF"));
        loginPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JLabel signInText = new JLabel("Sign-in to your ERP");
        signInText.setFont(new Font("Open Sans", Font.BOLD, 30));
        signInText.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 20, 0);
        loginPanel.add(signInText, gbc);

        JLabel infoLabel = new JLabel("<html><center>Please log in with your University email ID for accessing the ERP system. <br>The below username and password is only for some specific user.</center></html>");
        infoLabel.setFont(new Font("Open Sans", Font.BOLD, 12));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 10, 0);
        loginPanel.add(infoLabel, gbc);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.LIGHT_GRAY);
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 10, 0);
        loginPanel.add(separator, gbc);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Open Sans", Font.BOLD, 16));
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 5, 0);
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField("Enter your username here", 25);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
        BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        usernameField.setFont(new Font("Open Sans", Font.PLAIN, 18));
        usernameField.setForeground(Color.GRAY);
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Enter your username here")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setForeground(Color.GRAY);
                    usernameField.setText("Enter your username here");
                }
            }
        });
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 15, 0);
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Open Sans", Font.BOLD, 16));
        gbc.gridy = 9;
        gbc.insets = new Insets(0, 0, 5, 0);
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField("Enter your password here", 25);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        passwordField.setFont(new Font("Open Sans", Font.PLAIN, 18));
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("Enter your password here")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('\u2022');
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText("Enter your password here");
                    passwordField.setEchoChar((char) 0);
                }
            }
        });
        gbc.gridy = 10;
        gbc.insets = new Insets(0, 0, 5, 0);
        loginPanel.add(passwordField, gbc);

        JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setFont(new Font("Open Sans", Font.PLAIN, 12));
        showPasswordCheckBox.setOpaque(false);
        showPasswordCheckBox.setFocusPainted(false);
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 15, 0);
        loginPanel.add(showPasswordCheckBox, gbc);

        final char defaultEchoChar = passwordField.getEchoChar();
        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar(defaultEchoChar);
            }
        });

        JButton loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isArmed()) {
                    g2.setColor(getBackground().darker());
                } else {
                    g2.setColor(getBackground());
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        loginButton.setContentAreaFilled(false);
        loginButton.setBackground(Color.decode("#3FADA8")); 
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Open Sans", Font.BOLD, 18));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridy = 12;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipadx = 100;
        gbc.insets = new Insets(0, 0, 0, 0);
        loginPanel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                if ("admin".equals(username) && "password".equals(password)) {
                    JOptionPane.showMessageDialog(IIITDLoginPage.this, "Login Successful!");
                } else {
                    JOptionPane.showMessageDialog(IIITDLoginPage.this, "Invalid Username or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backgroundPanel.add(loginPanel, new GridBagConstraints());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IIITDLoginPage loginPage = new IIITDLoginPage();
            loginPage.setVisible(true);
        });
    }
}