package dao;

import entities.DomicilioFiscal;
import entities.Empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDao implements GenericDao<Empresa> {

    
    private static final String INSERT_SQL =
            "INSERT INTO empresa (eliminado, razonSocial, cuit, actividadPrincipal, email) " +
            "VALUES (FALSE, ?, ?, ?, ?)";

   
    private static final String SELECT_BY_ID_SQL =
            "SELECT " +
            " e.id              AS emp_id, " +
            " e.eliminado       AS emp_eliminado, " +
            " e.razonSocial     AS emp_razonSocial, " +
            " e.cuit            AS emp_cuit, " +
            " e.actividadPrincipal AS emp_actividadPrincipal, " +
            " e.email           AS emp_email, " +
            " d.id              AS dom_id, " +
            " d.eliminado       AS dom_eliminado, " +
            " d.calle           AS dom_calle, " +
            " d.numero          AS dom_numero, " +
            " d.ciudad          AS dom_ciudad, " +
            " d.provincia       AS dom_provincia, " +
            " d.codigoPostal    AS dom_codigoPostal, " +
            " d.pais            AS dom_pais, " +
            " d.empresa_id      AS dom_empresaId " +
            "FROM empresa e " +
            "LEFT JOIN domicilio_fiscal d " +
            "  ON d.empresa_id = e.id AND d.eliminado = FALSE " +
            "WHERE e.id = ? AND e.eliminado = FALSE";

    private static final String SELECT_ALL_SQL =
            "SELECT " +
            " e.id              AS emp_id, " +
            " e.eliminado       AS emp_eliminado, " +
            " e.razonSocial     AS emp_razonSocial, " +
            " e.cuit            AS emp_cuit, " +
            " e.actividadPrincipal AS emp_actividadPrincipal, " +
            " e.email           AS emp_email, " +
            " d.id              AS dom_id, " +
            " d.eliminado       AS dom_eliminado, " +
            " d.calle           AS dom_calle, " +
            " d.numero          AS dom_numero, " +
            " d.ciudad          AS dom_ciudad, " +
            " d.provincia       AS dom_provincia, " +
            " d.codigoPostal    AS dom_codigoPostal, " +
            " d.pais            AS dom_pais, " +
            " d.empresa_id      AS dom_empresaId " +
            "FROM empresa e " +
            "LEFT JOIN domicilio_fiscal d " +
            "  ON d.empresa_id = e.id AND d.eliminado = FALSE " +
            "WHERE e.eliminado = FALSE";

    
    private static final String SELECT_BY_CUIT_SQL =
            "SELECT " +
            " e.id              AS emp_id, " +
            " e.eliminado       AS emp_eliminado, " +
            " e.razonSocial     AS emp_razonSocial, " +
            " e.cuit            AS emp_cuit, " +
            " e.actividadPrincipal AS emp_actividadPrincipal, " +
            " e.email           AS emp_email, " +
            " d.id              AS dom_id, " +
            " d.eliminado       AS dom_eliminado, " +
            " d.calle           AS dom_calle, " +
            " d.numero          AS dom_numero, " +
            " d.ciudad          AS dom_ciudad, " +
            " d.provincia       AS dom_provincia, " +
            " d.codigoPostal    AS dom_codigoPostal, " +
            " d.pais            AS dom_pais, " +
            " d.empresa_id      AS dom_empresaId " +
            "FROM empresa e " +
            "LEFT JOIN domicilio_fiscal d " +
            "  ON d.empresa_id = e.id AND d.eliminado = FALSE " +
            "WHERE e.cuit = ? AND e.eliminado = FALSE";

    private static final String UPDATE_SQL =
            "UPDATE empresa SET " +
            "razonSocial = ?, cuit = ?, actividadPrincipal = ?, email = ? " +
            "WHERE id = ? AND eliminado = FALSE";

    private static final String SOFT_DELETE_SQL =
            "UPDATE empresa SET eliminado = TRUE " +
            "WHERE id = ? AND eliminado = FALSE";


    @Override
    public void crear(Empresa entidad, Connection conn) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entidad.getRazonSocial());
            ps.setString(2, entidad.getCuit());
            ps.setString(3, entidad.getActividadPrincipal());
            ps.setString(4, entidad.getEmail());

            ps.executeUpdate();

            // ID generado para la empresa
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entidad.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public Empresa leer(long id, Connection conn) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Empresa> leerTodos(Connection conn) throws Exception {
        List<Empresa> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }

        return lista;
    }

    @Override
    public void actualizar(Empresa entidad, Connection conn) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, entidad.getRazonSocial());
            ps.setString(2, entidad.getCuit());
            ps.setString(3, entidad.getActividadPrincipal());
            ps.setString(4, entidad.getEmail());
            ps.setLong(5, entidad.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(long id, Connection conn) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(SOFT_DELETE_SQL)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    
    
    public Empresa leerPorCuit(String cuit, Connection conn) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_CUIT_SQL)) {
            ps.setString(1, cuit);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

  
    private Empresa mapRow(ResultSet rs) throws Exception {
        Empresa e = new Empresa();

        e.setId(rs.getLong("emp_id"));
        e.setEliminado(rs.getBoolean("emp_eliminado"));
        e.setRazonSocial(rs.getString("emp_razonSocial"));
        e.setCuit(rs.getString("emp_cuit"));
        e.setActividadPrincipal(rs.getString("emp_actividadPrincipal"));
        e.setEmail(rs.getString("emp_email"));

        
        Long domId = rs.getLong("dom_id");
        if (!rs.wasNull()) {
            DomicilioFiscal d = new DomicilioFiscal();
            d.setId(domId);
            d.setEliminado(rs.getBoolean("dom_eliminado"));
            d.setCalle(rs.getString("dom_calle"));

            int numero = rs.getInt("dom_numero");
            if (rs.wasNull()) {
                d.setNumero(null);
            } else {
                d.setNumero(numero);
            }

            d.setCiudad(rs.getString("dom_ciudad"));
            d.setProvincia(rs.getString("dom_provincia"));
            d.setCodigoPostal(rs.getString("dom_codigoPostal"));
            d.setPais(rs.getString("dom_pais"));
            d.setEmpresaId(rs.getLong("dom_empresaId"));

            e.setDomicilioFiscal(d);
        }

        return e;
    }
}
