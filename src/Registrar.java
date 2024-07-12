import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registrar {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton button1;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/sistema_hospitalario";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Cambia esto a tu contraseña de MySQL

    public Registrar() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String historialClinico = textField1.getText();
                String nombre = textField2.getText();
                String apellido = textField3.getText();
                String telefono = textField4.getText();
                String edad = textField5.getText();
                String descripcionEnfermedad = textField6.getText();

                if (registrarPaciente(historialClinico, nombre, apellido, telefono, edad, descripcionEnfermedad)) {
                    JOptionPane.showMessageDialog(panel1, "Paciente registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(panel1, "Error al registrar paciente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean registrarPaciente(String historialClinico, String nombre, String apellido, String telefono, String edad, String descripcionEnfermedad) {
        boolean isRegistered = false;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO PACIENTE (cedula, n_historial_clinico, nombre, apellido, telefono, edad, descripcion_enfermedad) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, historialClinico);
            pstmt.setInt(2, 0);
            pstmt.setString(3, nombre);
            pstmt.setString(4, apellido);
            pstmt.setString(5, telefono);
            pstmt.setInt(6, Integer.parseInt(edad));
            pstmt.setString(7, descripcionEnfermedad);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                isRegistered = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return isRegistered;
    }

    public JPanel getPanel() {
        return panel1;
    }
}
