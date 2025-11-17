package service;

import config.DatabaseConnection;
import config.TransactionManager;
import dao.DomicilioFiscalDao;
import entities.DomicilioFiscal;

import java.sql.Connection;
import java.util.List;

public class DomicilioFiscalService implements GenericService<DomicilioFiscal> {

    private final DomicilioFiscalDao domicilioDao;

    
    public DomicilioFiscalService(DomicilioFiscalDao domicilioDao) {
        this.domicilioDao = domicilioDao;
    }

   
    // INSERTAR
    
    @Override
    public void insertar(DomicilioFiscal domicilio) throws Exception {
       
        // Un DomicilioFiscal no puede crearse de forma independiente, por eso, se agrga un mensaje
       
        throw new UnsupportedOperationException(
                "DomicilioFiscal no puede crearse de forma independiente. " +
                "Usar EmpresaService.insertar(Empresa) para dar de alta Empresa + Domicilio Fiscal."
        );
    }

    // ACTUALIZAR
   
    @Override
    public void actualizar(DomicilioFiscal domicilio) throws Exception {
        if (domicilio.getId() == null) {
            throw new IllegalArgumentException("El ID del domicilio es obligatorio para actualizar.");
        }
        validar(domicilio);

        try (Connection conn = DatabaseConnection.getConnection();
             TransactionManager tx = new TransactionManager(conn)) {

            tx.startTransaction();

            domicilioDao.actualizar(domicilio, conn);

            tx.commit();
        }
    }

   
    // ELIMINAR 
  
    @Override
    public void eliminar(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             TransactionManager tx = new TransactionManager(conn)) {

            tx.startTransaction();

            domicilioDao.eliminar(id, conn);

            tx.commit();
        }
    }

 
    // GET BY ID
   
    @Override
    public DomicilioFiscal getById(long id) throws Exception {
       
        try (Connection conn = DatabaseConnection.getConnection()) {
            return domicilioDao.leer(id, conn);
        }
    }

   
    // GET ALL
   
    @Override
    public List<DomicilioFiscal> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return domicilioDao.leerTodos(conn);
        }
    }


    // VALIDACIONES
  
    private void validar(DomicilioFiscal domicilio) {
        if (domicilio == null) {
            throw new IllegalArgumentException("El domicilio no puede ser null.");
        }
        if (domicilio.getCalle() == null || domicilio.getCalle().trim().isEmpty()) {
            throw new IllegalArgumentException("La calle es obligatoria.");
        }
        if (domicilio.getCiudad() == null || domicilio.getCiudad().trim().isEmpty()) {
            throw new IllegalArgumentException("La ciudad es obligatoria.");
        }
        if (domicilio.getProvincia() == null || domicilio.getProvincia().trim().isEmpty()) {
            throw new IllegalArgumentException("La provincia es obligatoria.");
        }
        if (domicilio.getPais() == null || domicilio.getPais().trim().isEmpty()) {
            throw new IllegalArgumentException("El país es obligatorio.");
        }

        
    }
}
