package com.biblioteca;

import com.biblioteca.controller.BibliotecaController;
import com.biblioteca.dao.DVDDAO;
import com.biblioteca.dao.DVDDAOImpl;
import com.biblioteca.dao.ElementoBibliotecaDAO;
import com.biblioteca.dao.LibroDAO;
import com.biblioteca.dao.LibroDAOImpl;
import com.biblioteca.dao.RevistaDAO;
import com.biblioteca.dao.RevistaDAOImpl;
import com.biblioteca.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        // Inicializar la conexión a la base de datos
        ConexionBD conexion = new ConexionBD();

        // Crear DAOs con las implementaciones concretas
        ElementoBibliotecaDAO elementoDAO = null; // Implementación genérica (puede ser nula si no se usa)
        DVDDAO dvdDAO = new DVDDAOImpl(conexion.getConnection());
        LibroDAO libroDAO = new LibroDAOImpl(conexion.getConnection());
        RevistaDAO revistaDAO = new RevistaDAOImpl(conexion.getConnection());

        // Crear la ventana principal
        MainFrame mainFrame = new MainFrame();

        // Inicializar el controlador principal
        new BibliotecaController(mainFrame, elementoDAO, libroDAO, revistaDAO, dvdDAO);

        // Hacer visible la ventana
        mainFrame.setVisible(true);

        // Cerrar la conexión al salir
        Runtime.getRuntime().addShutdownHook(new Thread(conexion::close));
    }
}