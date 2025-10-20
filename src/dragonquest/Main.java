package dragonquest;

import dragonquest.combate.BatallaGUI;
import dragonquest.gui.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BatallaGUI batalla = new BatallaGUI();   // ✅ usa la versión para la GUI
            VentanaPrincipal ventana = new VentanaPrincipal(batalla);
            ventana.setVisible(true);
        });
    }
}
