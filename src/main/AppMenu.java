package main;

import dao.DomicilioFiscalDao;
import dao.EmpresaDao;
import entities.DomicilioFiscal;
import entities.Empresa;
import service.DomicilioFiscalService;
import service.EmpresaService;

import java.util.List;
import java.util.Scanner;

public class AppMenu {

    private final EmpresaService empresaService;
    private final DomicilioFiscalService domicilioService;
    private final Scanner scanner;

    public AppMenu() {
        EmpresaDao empresaDao = new EmpresaDao();
        DomicilioFiscalDao domicilioDao = new DomicilioFiscalDao();

        // Servicio de B
        this.domicilioService = new DomicilioFiscalService(domicilioDao);

        // EmpresaService requiere 3 dependencias:
        // EmpresaDao, DomicilioFiscalDao y DomicilioFiscalService
        this.empresaService = new EmpresaService(
                empresaDao,
                domicilioDao,
                this.domicilioService
        );

        this.scanner = new Scanner(System.in);
    }

    public void ejecutarMenu() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Ingrese una opcion: ");

            switch (opcion) {
                case 1 -> altaEmpresa();
                case 2 -> listarEmpresas();
                case 3 -> buscarEmpresaPorCuit();
                case 4 -> actualizarEmpresa();
                case 5 -> eliminarEmpresa();
                case 6 -> listarDomicilios();
                case 7 -> verDomicilioPorId();
                case 8 -> actualizarDomicilio();
                case 9 -> eliminarDomicilio();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opcion invalida. Intente nuevamente.");
            }

            System.out.println();
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("========== MENU PRINCIPAL ==========");
        System.out.println("1) Alta de Empresa (con domicilio fiscal)");
        System.out.println("2) Listar todas las Empresas");
        System.out.println("3) Buscar Empresa por CUIT");
        System.out.println("4) Actualizar datos de una Empresa");
        System.out.println("5) Eliminar (logico) una Empresa");
        System.out.println("6) Listar Domicilios Fiscales");
        System.out.println("7) Ver Domicilio Fiscal por ID");
        System.out.println("8) Actualizar Domicilio Fiscal");
        System.out.println("9) Eliminar (logicamente) Domicilio Fiscal");
        System.out.println("0) Salir");
        System.out.println("====================================");
    }

    // ===================== A: EMPRESA =====================

    private void altaEmpresa() {
        try {
            System.out.println("=== Alta de Empresa ===");
            Empresa empresa = leerDatosEmpresaBasicos();

            DomicilioFiscal domicilio = leerDatosDomicilio();
            empresa.setDomicilioFiscal(domicilio); 
    
            empresaService.insertar(empresa);
            System.out.println("Empresa creada correctamente.");
        } catch (Exception e) {
            
            System.out.println("Error al dar de alta la empresa: " + e.getMessage());
        }
    }
    
    
    

    private void listarEmpresas() {
        System.out.println("=== Listado de Empresas ===");
        try {
            List<Empresa> empresas = empresaService.getAll();
            if (empresas.isEmpty()) {
                System.out.println("No hay empresas registradas.");
            } else {
                for (Empresa e : empresas) {
                    System.out.println("--------------------------------");
                    System.out.println(e);
                }
                System.out.println("--------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error al listar empresas: " + e.getMessage());
        }
    }

    private void buscarEmpresaPorCuit() {
        System.out.println("=== Buscar Empresa por CUIT ===");
        System.out.print("Ingrese el CUIT (NN-NNNNNNNN-N): ");
        String cuit = scanner.nextLine().trim();

        try {
            Empresa empresa = empresaService.getByCuit(cuit);
            if (empresa == null) {
                System.out.println("No se encontró una empresa con CUIT: " + cuit);
            } else {
                System.out.println("Empresa encontrada:");
                System.out.println(empresa);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void actualizarEmpresa() {
        System.out.println("=== Actualizar Empresa ===");
        long id = leerLong("Ingrese el ID de la empresa: ");

        try {
            Empresa emp = empresaService.getById(id);
            if (emp == null) {
                System.out.println("No existe una empresa con ese ID.");
                return;
            }

            System.out.println("Datos actuales:");
            System.out.println(emp);

            System.out.println("Ingrese nuevos valores (enter para mantener actual):");

            System.out.print("Razon Social [" + emp.getRazonSocial() + "]: ");
            String razon = scanner.nextLine();
            if (!razon.trim().isEmpty()) emp.setRazonSocial(razon.trim());

            System.out.print("CUIT [" + emp.getCuit() + "]: ");
            String cuit = scanner.nextLine();
            if (!cuit.trim().isEmpty()) emp.setCuit(cuit.trim());

            System.out.print("Actividad [" + emp.getActividadPrincipal() + "]: ");
            String act = scanner.nextLine();
            if (!act.trim().isEmpty()) emp.setActividadPrincipal(act.trim());

            System.out.print("Email [" + emp.getEmail() + "]: ");
            String email = scanner.nextLine();
            if (!email.trim().isEmpty()) emp.setEmail(email.trim());

            empresaService.actualizar(emp);
            System.out.println("Empresa actualizada correctamente.");
        } catch (Exception e) {
            System.out.println("Error al actualizar empresa: " + e.getMessage());
        }
    }

    private void eliminarEmpresa() {
        System.out.println("=== Eliminar Empresa ===");
        long id = leerLong("Ingrese el ID: ");

        try {
            empresaService.eliminar(id);
            System.out.println("Empresa eliminada (logicamente) si existia.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ===================== B: DOMICILIO FISCAL =====================

    private void listarDomicilios() {
        System.out.println("=== Listado de Domicilios Fiscales ===");
        try {
            List<DomicilioFiscal> domicilios = domicilioService.getAll();
            if (domicilios.isEmpty()) {
                System.out.println("No hay domicilios fiscales.");
            } else {
                for (DomicilioFiscal d : domicilios) {
                    System.out.println("--------------------------------");
                    System.out.println("ID: " + d.getId());
                    System.out.println(d);
                }
                System.out.println("--------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void verDomicilioPorId() {
        System.out.println("=== Ver Domicilio Fiscal por ID ===");
        long id = leerLong("Ingrese el ID: ");

        try {
            DomicilioFiscal d = domicilioService.getById(id);
            if (d == null) {
                System.out.println("No existe un domicilio con ese ID.");
            } else {
                System.out.println("Domicilio encontrado:");
                System.out.println(d);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void actualizarDomicilio() {
        System.out.println("=== Actualizar Domicilio Fiscal ===");
        long id = leerLong("Ingrese el ID del domicilio: ");

        try {
            DomicilioFiscal d = domicilioService.getById(id);
            if (d == null) {
                System.out.println("No existe un domicilio con ese ID.");
                return;
            }

            System.out.println("Datos actuales:");
            System.out.println(d);

            System.out.print("Calle [" + d.getCalle() + "]: ");
            String calle = scanner.nextLine();
            if (!calle.trim().isEmpty()) d.setCalle(calle.trim());

            System.out.print("Numero [" + (d.getNumero() != null ? d.getNumero() : "") + "]: ");
            String numStr = scanner.nextLine();
            if (!numStr.trim().isEmpty()) {
                try {
                    d.setNumero(Integer.parseInt(numStr.trim()));
                } catch (NumberFormatException e) {
                    System.out.println("Numero inválido. Se mantiene el valor anterior.");
                }
            }

            System.out.print("Ciudad [" + d.getCiudad() + "]: ");
            String ciudad = scanner.nextLine();
            if (!ciudad.trim().isEmpty()) d.setCiudad(ciudad.trim());

            System.out.print("Provincia [" + d.getProvincia() + "]: ");
            String prov = scanner.nextLine();
            if (!prov.trim().isEmpty()) d.setProvincia(prov.trim());

            System.out.print("Codigo Postal [" + d.getCodigoPostal() + "]: ");
            String cp = scanner.nextLine();
            if (!cp.trim().isEmpty()) d.setCodigoPostal(cp.trim());

            System.out.print("Pais [" + d.getPais() + "]: ");
            String pais = scanner.nextLine();
            if (!pais.trim().isEmpty()) d.setPais(pais.trim());

            domicilioService.actualizar(d);
            System.out.println("Domicilio actualizado correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarDomicilio() {
        System.out.println("=== Eliminar Domicilio Fiscal (baja lógica) ===");
        long id = leerLong("Ingrese el ID: ");

        try {
            domicilioService.eliminar(id);
            System.out.println("Domicilio eliminado (logicamente) si existia.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ===================== Lectura de datos =====================

    private Empresa leerDatosEmpresaBasicos() {
        Empresa e = new Empresa();

        System.out.print("Razon Social: ");
        e.setRazonSocial(scanner.nextLine().trim());

        System.out.print("CUIT (NN-NNNNNNNN-N): ");
        e.setCuit(scanner.nextLine().trim());

        System.out.print("Actividad principal: ");
        e.setActividadPrincipal(scanner.nextLine().trim());

        System.out.print("Email formato 'ej@ej.com (opcional): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) e.setEmail(email);

        return e;
    }

    private DomicilioFiscal leerDatosDomicilio() {
        DomicilioFiscal d = new DomicilioFiscal();

        System.out.print("Calle: ");
        d.setCalle(scanner.nextLine().trim());

        System.out.print("Numero (opcional): ");
        String n = scanner.nextLine().trim();
        if (!n.isEmpty()) {
            try {
                d.setNumero(Integer.parseInt(n));
            } catch (NumberFormatException e) {
                System.out.println("Numero invalido, se deja vacio.");
                d.setNumero(null);
            }
        }

        System.out.print("Ciudad: ");
        d.setCiudad(scanner.nextLine().trim());

        System.out.print("Provincia: ");
        d.setProvincia(scanner.nextLine().trim());

        System.out.print("Codigo Postal (opcional): ");
        d.setCodigoPostal(scanner.nextLine().trim());

        System.out.print("Pais: ");
        d.setPais(scanner.nextLine().trim());

        return d;
    }

    // ===================== Helpers =====================

    private int leerEntero(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero valido.");
            }
        }
    }

    private long leerLong(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero valido.");
            }
        }
    }
}