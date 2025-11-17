package dao;

import java.sql.Connection;
import java.util.List;


public interface GenericDao<T> {

    // Crear usando una conexión externa (transacciones)
    void crear(T entidad, Connection conn) throws Exception;

    // Leer por ID
    T leer(long id, Connection conn) throws Exception;

    // Leer todos los NO eliminados
    List<T> leerTodos(Connection conn) throws Exception;

    // Actualizar
    void actualizar(T entidad, Connection conn) throws Exception;

    // Eliminación lógica 
    void eliminar(long id, Connection conn) throws Exception;
}


