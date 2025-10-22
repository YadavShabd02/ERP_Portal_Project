import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        setTitle("ERP Login - IIIT Delhi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage;
            {
                try {
                    backgroundImage = new ImageIcon("background.jpeg").getImage();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Background image not found! Make sure 'background.jpeg' is in your classpath.");
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
        loginPanel.setPreferredSize(new Dimension(550, 650));
        loginPanel.setBackground(Color.decode("#FFFFFF"));
        loginPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon originalIcon = new ImageIcon("logo.jpg");
            Image image = originalIcon.getImage();
            Image newimg = image.getScaledInstance(150, 75, java.awt.Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(newimg));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Logo image not found! Make sure 'logo.jpg' is in your classpath.");
            logoLabel.setText("IIIT-D Logo"); // Fallback text
            logoLabel.setFont(new Font("Open Sans", Font.BOLD, 22));
        }
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0);
        loginPanel.add(logoLabel, gbc);

        JLabel instituteNameLabel = new JLabel("INDRAPRASTHA INSTITUTE OF");
        instituteNameLabel.setFont(new Font("Open Sans", Font.PLAIN, 16));
        instituteNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 2, 0);
        loginPanel.add(instituteNameLabel, gbc);

        JLabel infoTechLabel = new JLabel("INFORMATION TECHNOLOGY");
        infoTechLabel.setFont(new Font("Open Sans", Font.PLAIN, 16));
        infoTechLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 2, 0);
        loginPanel.add(infoTechLabel, gbc);

        JLabel delhiLabel = new JLabel("DELHI");
        delhiLabel.setFont(new Font("Open Sans", Font.BOLD, 16));
        delhiLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 25, 0);
        loginPanel.add(delhiLabel, gbc);

        JLabel signInText = new JLabel("Sign in to your ERP");
        signInText.setFont(new Font("Open Sans", Font.BOLD, 22));
        signInText.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 20, 0);
        loginPanel.add(signInText, gbc);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Open Sans", Font.BOLD, 16));
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 5, 0);
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(25);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        usernameField.setFont(new Font("Open Sans", Font.PLAIN, 18));
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 15, 0);
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Open Sans", Font.BOLD, 16));
        gbc.gridy = 9;
        gbc.insets = new Insets(0, 0, 5, 0);
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(25);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        passwordField.setFont(new Font("Open Sans", Font.PLAIN, 18));
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
        loginButton.setFont(new Font("Verdana", Font.BOLD, 15));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
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
                    JOptionPane.showMessageDialog(Login.this, "Login Successful!");
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid Username or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backgroundPanel.add(loginPanel, new GridBagConstraints());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login loginPage = new Login();
            loginPage.setVisible(true);
        });
    }
}