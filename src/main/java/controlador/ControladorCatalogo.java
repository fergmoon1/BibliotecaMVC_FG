package controlador;

import modelo.*;
import vista.*;
import javax.swing.*;
import java.sql.*;
import java.util.regex.Pattern;

public class ControladorCatalogo {
    private VistaCatalogo vista;
    private Connection conexion;

    public ControladorCatalogo(VistaCatalogo vista) {
        this.vista = vista;
        this.conexion = ConexionBD.getConexion();
        configurarListeners();
        actualizarTabla(null);
    }

    private void configurarListeners() {
        vista.getBtnBuscar().addActionListener(e -> filtrarPorGenero());
        vista.getBtnAgregar().addActionListener(e -> mostrarFormularioAgregar());
        vista.getBtnEditar().addActionListener(e -> editarElemento());
        vista.getBtnEliminar().addActionListener(e -> eliminarElemento());
        vista.getBtnActualizar().addActionListener(e -> actualizarTabla(null));
    }

    // ================== VALIDACIONES ================== //
    private boolean validarID(String idStr) {
        if (!Pattern.matches("^\\d+$", idStr)) return false;
        try {
            int id = Integer.parseInt(idStr);
            PreparedStatement ps = conexion.prepareStatement("SELECT id FROM ElementoBiblioteca WHERE id = ?");
            ps.setInt(1, id);
            return !ps.executeQuery().next();
        } catch (SQLException ex) {
            return false;
        }
    }

    private boolean validarAno(String anoStr) {
        if (!Pattern.matches("^\\d{4}$", anoStr)) return false;
        int ano = Integer.parseInt(anoStr);
        return ano <= java.time.Year.now().getValue() && ano > 1900;
    }

    private boolean validarTexto(String texto, int minLength) {
        return texto != null && !texto.trim().isEmpty() && texto.trim().length() >= minLength;
    }

    // ================== CRUD ================== //
    private void mostrarFormularioAgregar() {
        VistaAgregarEditar form = new VistaAgregarEditar(vista, "Agregar Elemento", false);

        form.setGuardarListener(e -> {
            try {
                if (validarDatosFormulario(form)) {
                    guardarElemento(form);
                    actualizarTabla(null);
                    form.dispose();
                    JOptionPane.showMessageDialog(vista, "Elemento guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                mostrarError("Error al guardar: " + ex.getMessage());
            }
        });

        form.setCancelarListener(e -> form.dispose());
        form.setVisible(true);
    }

    private boolean validarDatosFormulario(VistaAgregarEditar form) {
        try {
            // Validar ID
            if (!validarID(form.getId())) {
                mostrarError("ID inválido. Debe ser un número único.");
                return false;
            }

            // Validar título
            if (!validarTexto(form.getTitulo(), 3)) {
                mostrarError("Título inválido. Mínimo 3 caracteres.");
                return false;
            }

            // Validar año
            if (!validarAno(form.getAno())) {
                mostrarError("Año inválido. Formato: YYYY entre 1900 y actual.");
                return false;
            }

            // Validar campos específicos
            switch (form.getTipo()) {
                case "Libro":
                    if (!validarTexto(form.getIsbn(), 10)) {
                        mostrarError("ISBN inválido. Mínimo 10 caracteres.");
                        return false;
                    }
                    if (!Pattern.matches("^\\d+$", form.getPaginas())) {
                        mostrarError("Número de páginas inválido. Solo dígitos.");
                        return false;
                    }
                    break;
                case "DVD":
                    if (!Pattern.matches("^\\d+$", form.getDuracion())) {
                        mostrarError("Duración inválida. Ingrese minutos en números.");
                        return false;
                    }
                    break;
            }
            return true;
        } catch (Exception e) {
            mostrarError("Error en los datos: " + e.getMessage());
            return false;
        }
    }

    private void guardarElemento(VistaAgregarEditar form) throws SQLException {
        conexion.setAutoCommit(false);
        try {
            // 1. Insertar en tabla padre
            String sqlPadre = "INSERT INTO ElementoBiblioteca (id, titulo, autor, ano_publicacion, tipo) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psPadre = conexion.prepareStatement(sqlPadre)) {
                psPadre.setInt(1, Integer.parseInt(form.getId()));
                psPadre.setString(2, form.getTitulo());
                psPadre.setString(3, form.getAutor());
                psPadre.setInt(4, Integer.parseInt(form.getAno()));
                psPadre.setString(5, form.getTipo());
                psPadre.executeUpdate();
            }

            // 2. Insertar en tabla específica
            switch (form.getTipo()) {
                case "Libro":
                    String sqlLibro = "INSERT INTO Libro (id, isbn, numero_paginas, genero, editorial) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement psLibro = conexion.prepareStatement(sqlLibro)) {
                        psLibro.setInt(1, Integer.parseInt(form.getId()));
                        psLibro.setString(2, form.getIsbn());
                        psLibro.setInt(3, Integer.parseInt(form.getPaginas()));
                        psLibro.setString(4, form.getGeneroLibro());
                        psLibro.setString(5, form.getEditorial());
                        psLibro.executeUpdate();
                    }
                    break;
                case "DVD":
                    // Implementación similar para DVD
                    break;
            }
            conexion.commit();
        } catch (SQLException ex) {
            conexion.rollback();
            throw ex;
        } finally {
            conexion.setAutoCommit(true);
        }
    }

    // ... (resto de métodos se mantienen igual)
    private void filtrarPorGenero() { /* ... */ }
    private void actualizarTabla(String filtroGenero) { /* ... */ }
    private void editarElemento() { /* ... */ }
    private void eliminarElemento() { /* ... */ }
    private void mostrarError(String mensaje) { /* ... */ }
    private void mostrarAdvertencia(String mensaje) { /* ... */ }
}