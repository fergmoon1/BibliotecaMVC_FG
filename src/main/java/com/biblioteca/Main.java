package com.biblioteca;

import com.biblioteca.controller.BibliotecaController;
import com.biblioteca.dao.DVDDAO;
import com.biblioteca.dao.ElementoBibliotecaDAO;
import com.biblioteca.dao.LibroDAO;
import com.biblioteca.dao.RevistaDAO;
import com.biblioteca.dao.DVDDAOImpl;
import com.biblioteca.dao.ElementoBibliotecaDAOImpl;
import com.biblioteca.dao.LibroDAOImpl;
import com.biblioteca.dao.RevistaDAOImpl;
import com.biblioteca.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        // Inicializar la ventana principal
        MainFrame mainFrame = new MainFrame();

        // Crear instancias de los DAOs
        ElementoBibliotecaDAO elementoDAO = new ElementoBibliotecaDAOImpl();
        LibroDAO libroDAO = new LibroDAOImpl();
        RevistaDAO revistaDAO = new RevistaDAOImpl();
        DVDDAO dvdDAO = new DVDDAOImpl();

        // Inicializar el controlador principal
        new BibliotecaController(mainFrame, elementoDAO, libroDAO, revistaDAO, dvdDAO);

        // Hacer visible la ventana
        mainFrame.setVisible(true);
    }
}