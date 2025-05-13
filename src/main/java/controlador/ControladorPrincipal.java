package controlador;

import vista.VistaPrincipal;
import vista.VistaCatalogo;
import vista.VistaAyuda;
import java.awt.event.ActionEvent;

public class ControladorPrincipal {
    private VistaPrincipal vistaPrincipal;

    public ControladorPrincipal(VistaPrincipal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        configurarListeners();
    }

    private void configurarListeners() {
        // 1. Evento para el ítem "Catálogo"
        vistaPrincipal.getItemCatalogo().addActionListener(e -> abrirCatalogo());

        // 2. Evento para el ítem "Ayuda"
        vistaPrincipal.getItemAyuda().addActionListener(e -> abrirAyuda());
    }

    private void abrirCatalogo() {
        VistaCatalogo vistaCatalogo = new VistaCatalogo();
        new ControladorCatalogo(vistaCatalogo); // Reutiliza el controlador existente
        vistaCatalogo.setVisible(true);
    }

    private void abrirAyuda() {
        VistaAyuda vistaAyuda = new VistaAyuda();
        vistaAyuda.setVisible(true);
    }
}