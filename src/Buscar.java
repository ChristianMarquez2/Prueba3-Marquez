import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Buscar {
    private JPanel panel1;
    private JTextField cedulaTextField;
    private JButton buscarButton;
    private JTextArea resultadoTextArea;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/sistema_hospitalario";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Cambia esto a tu contraseña de MySQL

    public Buscar() {
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = cedulaTextField.getText();
                String resultado = buscarPacientePorCedula(cedula);
                resultadoTextArea.setText(resultado);
            }
        });
    }

    private String buscarPacientePorCedula(String cedula) {
        StringBuilder resultado = new StringBuilder();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT * FROM PACIENTE WHERE cedula = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cedula);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                resultado.append("Cédula: ").append(rs.getString("cedula")).append("\n");
                resultado.append("Historial Clínico: ").append(rs.getInt("n_historial_clinico")).append("\n");
                resultado.append("Nombre: ").append(rs.getString("nombre")).append("\n");
                resultado.append("Apellido: ").append(rs.getString("apellido")).append("\n");
                resultado.append("Teléfono: ").append(rs.getString("telefono")).append("\n");
                resultado.append("Edad: ").append(rs.getInt("edad")).append("\n");
                resultado.append("Descripción de Enfermedad: ").append(rs.getString("descripcion_enfermedad")).append("\n");
            } else {
                resultado.append("No se encontró ningún paciente con esa cédula.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resultado.append("Error al buscar paciente.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return resultado.toString();
    }

    public JPanel getPanel() {
        return panel1;
    }
}
