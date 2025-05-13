package com.biblioteca.controller;

import com.biblioteca.model.DVD;
import com.biblioteca.model.ElementoBiblioteca;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Revista;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaController {
    private List<ElementoBiblioteca> elementos;
    private DefaultTableModel tableModel;
    private BibliotecaDAO bibliotecaDAO;

    public BibliotecaController() {
        this.bibliotecaDAO = new BibliotecaDAO();
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        try {
            this.elementos = bibliotecaDAO.cargarTodosElementos();
            if (this.elementos.isEmpty()) {
                agregarDatosEjemplo();
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
            this.elementos = new ArrayList<>();
            agregarDatosEjemplo();
        }
    }

    private void agregarDatosEjemplo() {
        // DVDs de ejemplo
        agregarDVD(new DVD(11, "El Padrino", "Francis Ford Coppola", 1972, 175, "Drama"));
        agregarDVD(new DVD(12, "Matrix", "Wachowski Brothers", 1999, 136, "Ciencia Ficción"));

        // Libros de ejemplo
        agregarLibro(new Libro(101, "Cien años de soledad", "Gabriel García Márquez", 1967,
                "978-0307474728", 417, "Realismo mágico", "Sudamericana"));

        // Revistas de ejemplo
        agregarRevista(new Revista(201, "National Geographic", 2023, 256, "Ciencia y Naturaleza"));
    }

    // Métodos CRUD con persistencia
    public void agregarElemento(ElementoBiblioteca elemento) {
        if (elemento != null) {
            try {
                bibliotecaDAO.guardarElemento(elemento);
                elementos.add(elemento);
                actualizarTabla();
            } catch (SQLException e) {
                System.err.println("Error al guardar elemento: " + e.getMessage());
            }
        }
    }

    public boolean eliminarElemento(int id) {
        try {
            ElementoBiblioteca elemento = buscarPorId(id);
            if (elemento != null) {
                String tipo = elemento instanceof DVD ? "dvd" :
                        elemento instanceof Libro ? "libro" : "revista";
                bibliotecaDAO.eliminarElemento(id, tipo);
                boolean eliminado = elementos.removeIf(e -> e.getId() == id);
                if (eliminado) {
                    actualizarTabla();
                }
                return eliminado;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar elemento: " + e.getMessage());
        }
        return false;
    }

    public boolean actualizarElemento(ElementoBiblioteca elementoActualizado) {
        try {
            bibliotecaDAO.guardarElemento(elementoActualizado);
            for (int i = 0; i < elementos.size(); i++) {
                if (elementos.get(i).getId() == elementoActualizado.getId()) {
                    elementos.set(i, elementoActualizado);
                    actualizarTabla();
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar elemento: " + e.getMessage());
        }
        return false;
    }

    // Métodos de búsqueda
    public ElementoBiblioteca buscarPorId(int id) {
        return elementos.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<ElementoBiblioteca> buscarPorGenero(String genero) {
        List<ElementoBiblioteca> resultados = new ArrayList<>();
        for (ElementoBiblioteca elemento : elementos) {
            if ((elemento instanceof Libro && ((Libro) elemento).getGenero().equalsIgnoreCase(genero)) ||
                    (elemento instanceof DVD && ((DVD) elemento).getGenero().equalsIgnoreCase(genero)) ||
                    (elemento instanceof Revista && ((Revista) elemento).getCategoria().equalsIgnoreCase(genero))) {
                resultados.add(elemento);
            }
        }
        return resultados;
    }

    // Métodos específicos por tipo
    public void agregarDVD(DVD dvd) {
        agregarElemento(dvd);
    }

    public void agregarLibro(Libro libro) {
        agregarElemento(libro);
    }

    public void agregarRevista(Revista revista) {
        agregarElemento(revista);
    }

    // Métodos para gestión de datos
    public List<ElementoBiblioteca> getTodosElementos() {
        return new ArrayList<>(elementos);
    }

    public List<DVD> getTodosDVDs() {
        List<DVD> dvds = new ArrayList<>();
        for (ElementoBiblioteca elemento : elementos) {
            if (elemento instanceof DVD) {
                dvds.add((DVD) elemento);
            }
        }
        return dvds;
    }

    public List<Libro> getTodosLibros() {
        List<Libro> libros = new ArrayList<>();
        for (ElementoBiblioteca elemento : elementos) {
            if (elemento instanceof Libro) {
                libros.add((Libro) elemento);
            }
        }
        return libros;
    }

    public List<Revista> getTodasRevistas() {
        List<Revista> revistas = new ArrayList<>();
        for (ElementoBiblioteca elemento : elementos) {
            if (elemento instanceof Revista) {
                revistas.add((Revista) elemento);
            }
        }
        return revistas;
    }

    // Métodos para la interfaz gráfica
    public void configurarModeloTabla(DefaultTableModel model) {
        this.tableModel = model;
        actualizarTabla();
    }

    public void actualizarTabla() {
        if (tableModel != null) {
            tableModel.setRowCount(0);
            for (ElementoBiblioteca elemento : elementos) {
                if (elemento instanceof DVD) {
                    tableModel.addRow(((DVD) elemento).toTableRow());
                } else if (elemento instanceof Libro) {
                    tableModel.addRow(((Libro) elemento).toTableRow());
                } else if (elemento instanceof Revista) {
                    tableModel.addRow(((Revista) elemento).toTableRow());
                }
            }
        }
    }

    public void mostrarSoloDVDs() {
        if (tableModel != null) {
            tableModel.setRowCount(0);
            getTodosDVDs().forEach(dvd -> tableModel.addRow(dvd.toTableRow()));
        }
    }

    public void mostrarSoloLibros() {
        if (tableModel != null) {
            tableModel.setRowCount(0);
            getTodosLibros().forEach(libro -> tableModel.addRow(libro.toTableRow()));
        }
    }

    public void mostrarSoloRevistas() {
        if (tableModel != null) {
            tableModel.setRowCount(0);
            getTodasRevistas().forEach(revista -> tableModel.addRow(revista.toTableRow()));
        }
    }

    public void mostrarTodos() {
        actualizarTabla();
    }

    // Método para sincronizar con la base de datos
    public void sincronizarConBD() {
        try {
            this.elementos = bibliotecaDAO.cargarTodosElementos();
            actualizarTabla();
        } catch (SQLException e) {
            System.err.println("Error al sincronizar con BD: " + e.getMessage());
        }
    }
}