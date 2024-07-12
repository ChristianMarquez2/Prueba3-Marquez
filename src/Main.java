import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Iniciar la ventana de login
            new Login();
        });
    }
}
