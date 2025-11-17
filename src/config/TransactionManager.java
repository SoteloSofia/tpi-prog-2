package config;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Gestiona transacciones (commit/rollback) y cierre automático de la conexión.
 * Se usa con try-with-resources:

 * Si ocurre una excepción, al salir del try-with-resources:
 * - rollback() automático
 * - se restaura autoCommit
 * - se cierra la conexión
 */

public class TransactionManager implements AutoCloseable {

    private final Connection conn;
    private boolean transactionActive;

    public TransactionManager(Connection conn) throws SQLException {
        if (conn == null) {
            throw new IllegalArgumentException("La conexión no puede ser null");
        }
        this.conn = conn;
        this.transactionActive = false;
    }

  
    public Connection getConnection() {
        return conn;
    }

    /**
     * Inicia una transacción.
     */
    public void startTransaction() throws SQLException {
        if (conn.isClosed()) {
            throw new SQLException("La conexión está cerrada, no se puede iniciar transacción.");
        }
        conn.setAutoCommit(false);
        transactionActive = true;
    }

    /**
     * Confirma la transacción.
     */
    public void commit() throws SQLException {
        if (!transactionActive) {
            throw new SQLException("No hay transacción activa para commit()");
        }
        conn.commit();
        transactionActive = false;
    }

    /**
     * Revierte la transacción.
     */
    public void rollback() {
        if (transactionActive) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error durante rollback: " + e.getMessage());
            } finally {
                transactionActive = false;
            }
        }
    }

  
    
    @Override
    public void close() {
        try {
            if (transactionActive) {
                rollback();
            }
            conn.setAutoCommit(true);
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar TransactionManager: " + e.getMessage());
        }
    }
}

