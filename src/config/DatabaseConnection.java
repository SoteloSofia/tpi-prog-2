package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException; 

public class DatabaseConnection {

    
    private static final String URL =  "jdbc:mysql://localhost:3306/tpi_empresa_domicilio?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "tpi"; // Usuario
    private static final String PASS = "1234"; // Contraseña de MySQL

    
    static {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(
                "Error: No se encontró el driver JDBC de MySQL. " +
                "Asegúrate de haber agregado el archivo .jar a las 'Libraries' del proyecto."
            );
        }
    }
    

   
    private DatabaseConnection() {
        throw new UnsupportedOperationException("Esta es una clase utilitaria y no debe ser instanciada");
    }

   
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}