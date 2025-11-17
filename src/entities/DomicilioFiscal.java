package entities;

public class DomicilioFiscal extends BaseEntity {

    private String calle;
    private Integer numero;
    private String ciudad;
    private String provincia;
    private String codigoPostal;
    private String pais;
    // FK a Empresa (tabla A)
    private Long empresaId;

    // Constructor vacío (requerido por el TP)
    public DomicilioFiscal() {
    }

    // Constructor completo (incluye campos de BaseEntity)
    public DomicilioFiscal(Long id, boolean eliminado, String calle, Integer numero,
                           String ciudad, String provincia, String codigoPostal,
                           String pais, Long empresaId) {
        super(id, eliminado);
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.empresaId = empresaId;
    }

    // Getters y Setters
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    @Override
    public String toString() {
        return  "  Calle: " + calle + " " + (numero != null ? numero : "") + "\n" +
                "  Ciudad: " + ciudad + "\n" +
                "  Provincia: " + provincia + "\n" +
                "  Código Postal: " + codigoPostal + "\n" +
                "  País: " + pais + "\n";
    }
}
