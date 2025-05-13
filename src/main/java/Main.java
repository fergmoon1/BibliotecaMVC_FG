import vista.VistaPrincipal;
import controlador.ControladorPrincipal;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaPrincipal vistaPrincipal = new VistaPrincipal();
            new ControladorPrincipal(vistaPrincipal); // Conecta el controlador
            vistaPrincipal.setVisible(true);
        });
    }
}