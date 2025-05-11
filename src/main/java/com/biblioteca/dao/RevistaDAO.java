package com.biblioteca.dao;

import com.biblioteca.model.Revista;
import java.util.List;

public interface RevistaDAO {
    void agregarRevista(Revista revista);
    void actualizarRevista(Revista revista);
    void eliminarRevista(int id);
    Revista obtenerRevistaPorId(int id);
    List<Revista> obtenerTodasLasRevistas();
}
