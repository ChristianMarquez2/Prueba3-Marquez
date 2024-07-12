import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/sistema_hospitalario";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Cambia esto a tu contraseña de MySQL

    public Login() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Usuario:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(userLabel, constraints);

        userTextField = new JTextField(20);
        constraints.gridx = 1;
        panel.add(userTextField, constraints);

        JLabel passwordLabel = new JLabel("Contraseña:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        panel.add(passwordField, constraints);

        loginButton = new JButton("Login");
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, constraints);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText();
                String password = new String(passwordField.getPassword());

                if (validateCredentials(username, password)) {
                    dispose(); // Cierra la ventana de login
                    mostrarAplicacion(); // Muestra la aplicación principal
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel);
        setVisible(true);
    }

    private boolean validateCredentials(String username, String password) {
        boolean isValid = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT * FROM USUARIO WHERE username = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                isValid = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return isValid;
    }

    private void mostrarAplicacion() {
        // Mostrar la aplicación principal después de iniciar sesión
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainApplication().start();
            }
        });
    }
}
