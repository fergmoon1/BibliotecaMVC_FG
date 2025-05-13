package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaAgregarEditar extends JDialog {
    private JComboBox<String> comboTipo;
    private JPanel panelCamposDinamicos;
    private JTextField txtId, txtTitulo, txtAutor, txtAno;
    // Campos específicos
    private JTextField txtIsbn, txtPaginas, txtGeneroLibro, txtEditorial; // Libro
    private JTextField txtEdicion, txtCategoria; // Revista
    private JTextField txtDuracion, txtGeneroDVD; // DVD
    private JButton btnGuardar, btnCancelar;

    public VistaAgregarEditar(JFrame parent, String titulo, boolean esEdicion) {
        super(parent, titulo, true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        initUI(esEdicion);
    }

    private void initUI(boolean esEdicion) {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior: Tipo y campos comunes
        JPanel panelSuperior = new JPanel(new GridLayout(0, 2, 5, 5));
        comboTipo = new JComboBox<>(new String[]{"Libro", "Revista", "DVD"});
        comboTipo.addActionListener(e -> actualizarCamposDinamicos());

        panelSuperior.add(new JLabel("Tipo:"));
        panelSuperior.add(comboTipo);
        panelSuperior.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(!esEdicion); // ID no editable en edición
        panelSuperior.add(txtId);
        panelSuperior.add(new JLabel("Título:"));
        panelSuperior.add(txtTitulo = new JTextField());
        panelSuperior.add(new JLabel("Autor:"));
        panelSuperior.add(txtAutor = new JTextField());
        panelSuperior.add(new JLabel("Año:"));
        panelSuperior.add(txtAno = new JTextField());

        // Panel dinámico (campos específicos)
        panelCamposDinamicos = new JPanel(new GridLayout(0, 2, 5, 5));
        actualizarCamposDinamicos(); // Inicializar campos

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);

        // Ensamblar
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCamposDinamicos, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    private void actualizarCamposDinamicos() {
        panelCamposDinamicos.removeAll();
        String tipo = (String) comboTipo.getSelectedItem();

        switch (tipo) {
            case "Libro":
                panelCamposDinamicos.add(new JLabel("ISBN:"));
                panelCamposDinamicos.add(txtIsbn = new JTextField());
                panelCamposDinamicos.add(new JLabel("Páginas:"));
                panelCamposDinamicos.add(txtPaginas = new JTextField());
                panelCamposDinamicos.add(new JLabel("Género:"));
                panelCamposDinamicos.add(txtGeneroLibro = new JTextField());
                panelCamposDinamicos.add(new JLabel("Editorial:"));
                panelCamposDinamicos.add(txtEditorial = new JTextField());
                break;
            case "Revista":
                panelCamposDinamicos.add(new JLabel("Núm. Edición:"));
                panelCamposDinamicos.add(txtEdicion = new JTextField());
                panelCamposDinamicos.add(new JLabel("Categoría:"));
                panelCamposDinamicos.add(txtCategoria = new JTextField());
                break;
            case "DVD":
                panelCamposDinamicos.add(new JLabel("Duración (mins):"));
                panelCamposDinamicos.add(txtDuracion = new JTextField());
                panelCamposDinamicos.add(new JLabel("Género:"));
                panelCamposDinamicos.add(txtGeneroDVD = new JTextField());
                break;
        }
        panelCamposDinamicos.revalidate();
        panelCamposDinamicos.repaint();
    }

    // --- Getters para obtener datos del formulario ---
    // En VistaAgregarEditar.java (dentro de la clase)
    public String getId() {
        return txtId.getText();
    }

    public String getTitulo() {
        return txtTitulo.getText();
    }

    public String getAutor() {
        return txtAutor.getText();
    }

    public String getAno() {
        return txtAno.getText();
    }

    public String getIsbn() {
        return txtIsbn != null ? txtIsbn.getText() : "";
    }

    public String getPaginas() {
        return txtPaginas != null ? txtPaginas.getText() : "0";
    }

    public String getDuracion() {
        return txtDuracion != null ? txtDuracion.getText() : "0";
    }

    public String getGeneroLibro() {
        return txtGeneroLibro != null ? txtGeneroLibro.getText() : "";
    }

    public String getEditorial() {
        return txtEditorial != null ? txtEditorial.getText() : "";
    }

    public String getTipo() {
        return (String) comboTipo.getSelectedItem();
    }
}