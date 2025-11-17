package service;

import config.DatabaseConnection;
import config.TransactionManager;
import dao.DomicilioFiscalDao;
import dao.EmpresaDao;
import entities.DomicilioFiscal;
import entities.Empresa;

import java.sql.Connection;
import java.util.List;

public class EmpresaService implements GenericService<Empresa> {

    private final EmpresaDao empresaDao;
    private final DomicilioFiscalDao domicilioDao;
    private final DomicilioFiscalService domicilioService;
    
    public EmpresaService(EmpresaDao empresaDao, DomicilioFiscalDao domicilioDao, DomicilioFiscalService domicilioService) {
        this.empresaDao = empresaDao;
        this.domicilioDao = domicilioDao;
        this.domicilioService = domicilioService;
    }

    
    @Override
    public void insertar(Empresa empresa) throws Exception {
        validarEmpresa(empresa);

        try (Connection conn = DatabaseConnection.getConnection();
             TransactionManager tx = new TransactionManager(conn)) {

            tx.startTransaction();

           
            Empresa existente = empresaDao.leerPorCuit(empresa.getCuit(), conn);
            if (existente != null) {
                throw new IllegalArgumentException("Ya existe una empresa con el CUIT: " + empresa.getCuit());
            }

            
            empresaDao.crear(empresa, conn);  

            DomicilioFiscal domicilio = empresa.getDomicilioFiscal();
            if (domicilio != null) {
                validarDomicilio(domicilio);

                
                domicilio.setEmpresaId(empresa.getId());
                domicilioDao.crear(domicilio, conn);
            }

            tx.commit();
        }
    }

    
    
   
    @Override
    public void actualizar(Empresa empresa) throws Exception {
        if (empresa.getId() == null) {
            throw new IllegalArgumentException("El ID de la empresa es obligatorio para actualizar.");
        }
        validarEmpresa(empresa);

        try (Connection conn = DatabaseConnection.getConnection();
             TransactionManager tx = new TransactionManager(conn)) {

            tx.startTransaction();
            empresaDao.actualizar(empresa, conn);
            tx.commit();
        }
    }

    
    
   
    @Override
    public void eliminar(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             TransactionManager tx = new TransactionManager(conn)) {

            tx.startTransaction();

            
            Empresa emp = empresaDao.leer(id, conn);
            if (emp != null && emp.getDomicilioFiscal() != null) {
                DomicilioFiscal dom = emp.getDomicilioFiscal();
                domicilioDao.eliminar(dom.getId(), conn);
            }

            empresaDao.eliminar(id, conn);

            tx.commit();
        }
    }

  
    @Override
    public Empresa getById(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return empresaDao.leer(id, conn);
        }
    }

    @Override
    public List<Empresa> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return empresaDao.leerTodos(conn);
        }
    }

    
    public Empresa getByCuit(String cuit) throws Exception {
        if (cuit == null || cuit.trim().isEmpty()) {
            throw new IllegalArgumentException("El CUIT es obligatorio para buscar.");
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            return empresaDao.leerPorCuit(cuit.trim(), conn);
        }
    }

    
    private void validarEmpresa(Empresa empresa) {
        if (empresa == null) {
            throw new IllegalArgumentException("La empresa no puede ser null.");
        }
        if (empresa.getRazonSocial() == null || empresa.getRazonSocial().trim().isEmpty()) {
            throw new IllegalArgumentException("La razón social es obligatoria.");
        }
        if (empresa.getCuit() == null || empresa.getCuit().trim().isEmpty()) {
            throw new IllegalArgumentException("El CUIT es obligatorio.");
        }
        
        if (!empresa.getCuit().matches("\\d{2}-\\d{8}-\\d")) {
           
            throw new IllegalArgumentException("El CUIT debe tener formato NN-NNNNNNNN-N (ej: 30-12345678-9).");
        }
       
        if (empresa.getEmail() != null && !empresa.getEmail().trim().isEmpty()) {
            if (!empresa.getEmail().contains("@")) {
                throw new IllegalArgumentException("El email de la empresa no tiene un formato válido.");
            }
        }
    }

    private void validarDomicilio(DomicilioFiscal domicilio) {
        if (domicilio == null) {
            throw new IllegalArgumentException("El domicilio fiscal no puede ser null.");
        }
        if (domicilio.getCalle() == null || domicilio.getCalle().trim().isEmpty()) {
            throw new IllegalArgumentException("La calle del domicilio fiscal es obligatoria.");
        }
        if (domicilio.getCiudad() == null || domicilio.getCiudad().trim().isEmpty()) {
            throw new IllegalArgumentException("La ciudad del domicilio fiscal es obligatoria.");
        }
        if (domicilio.getProvincia() == null || domicilio.getProvincia().trim().isEmpty()) {
            throw new IllegalArgumentException("La provincia del domicilio fiscal es obligatoria.");
        }
        if (domicilio.getPais() == null || domicilio.getPais().trim().isEmpty()) {
            throw new IllegalArgumentException("El país del domicilio fiscal es obligatorio.");
        }
    }
}
