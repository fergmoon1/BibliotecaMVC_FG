package com.biblioteca.dao;

import com.biblioteca.model.Revista;
import java.sql.SQLException;
import java.util.List;

public interface RevistaDAO {
    List<Revista> obtenerTodas() throws SQLException;
    List<Revista> buscarPorCategoria(String categoria) throws SQLException;
    boolean agregarRevista(Revista revista) throws SQLException;
    boolean actualizarRevista(Revista revista) throws SQLException;
    boolean eliminarRevista(int id) throws SQLException;
}