package dao;

import entities.DomicilioFiscal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DomicilioFiscalDao implements GenericDao<DomicilioFiscal> {

    private static final String INSERT_SQL =
            "INSERT INTO domicilio_fiscal " +
            "(eliminado, calle, numero, ciudad, provincia, codigoPostal, pais, empresa_id) " +
            "VALUES (FALSE, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT id, eliminado, calle, numero, ciudad, provincia, codigoPostal, pais, empresa_id " +
            "FROM domicilio_fiscal " +
            "WHERE id = ? AND eliminado = FALSE";

    private static final String SELECT_ALL_SQL =
            "SELECT id, eliminado, calle, numero, ciudad, provincia, codigoPostal, pais, empresa_id " +
            "FROM domicilio_fiscal " +
            "WHERE eliminado = FALSE";

    private static final String UPDATE_SQL =
            "UPDATE domicilio_fiscal SET " +
            "calle = ?, numero = ?, ciudad = ?, provincia = ?, codigoPostal = ?, pais = ? " +
            "WHERE id = ? AND eliminado = FALSE";

    private static final String SOFT_DELETE_SQL =
            "UPDATE domicilio_fiscal SET eliminado = TRUE " +
            "WHERE id = ? AND eliminado = FALSE";


    @Override
    public void crear(DomicilioFiscal entidad, Connection conn) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entidad.getCalle());

            if (entidad.getNumero() != null) {
                ps.setInt(2, entidad.getNumero());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }

            ps.setString(3, entidad.getCiudad());
            ps.setString(4, entidad.getProvincia());
            ps.setString(5, entidad.getCodigoPostal());
            ps.setString(6, entidad.getPais());

            
            if (entidad.getEmpresaId() == null) {
                throw new IllegalArgumentException("empresaId no puede ser null al crear DomicilioFiscal");
            }
            ps.setLong(7, entidad.getEmpresaId());

            ps.executeUpdate();

            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entidad.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public DomicilioFiscal leer(long id, Connection conn) throws Exception {
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
    public List<DomicilioFiscal> leerTodos(Connection conn) throws Exception {
        List<DomicilioFiscal> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }

        return lista;
    }

    @Override
    public void actualizar(DomicilioFiscal entidad, Connection conn) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, entidad.getCalle());

            if (entidad.getNumero() != null) {
                ps.setInt(2, entidad.getNumero());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }

            ps.setString(3, entidad.getCiudad());
            ps.setString(4, entidad.getProvincia());
            ps.setString(5, entidad.getCodigoPostal());
            ps.setString(6, entidad.getPais());
            ps.setLong(7, entidad.getId());

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

    
    private DomicilioFiscal mapRow(ResultSet rs) throws Exception {
        DomicilioFiscal d = new DomicilioFiscal();

        d.setId(rs.getLong("id"));
        d.setEliminado(rs.getBoolean("eliminado"));
        d.setCalle(rs.getString("calle"));

        int numero = rs.getInt("numero");
        if (rs.wasNull()) {
            d.setNumero(null);
        } else {
            d.setNumero(numero);
        }

        d.setCiudad(rs.getString("ciudad"));
        d.setProvincia(rs.getString("provincia"));
        d.setCodigoPostal(rs.getString("codigoPostal"));
        d.setPais(rs.getString("pais"));
        d.setEmpresaId(rs.getLong("empresa_id"));

        return d;
    }
}
