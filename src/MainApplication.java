import javax.swing.*;

public class MainApplication {
    public void start() {
        JFrame frame = new JFrame("Aplicación Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Aquí agregamos los componentes y funcionalidades necesarios
        JPanel mainPanel = new JPanel();
        mainPanel.add(new JLabel("Bienvenido a la aplicación principal"));

        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
