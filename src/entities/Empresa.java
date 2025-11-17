package entities;

public class Empresa extends BaseEntity {

    private String razonSocial;
    private String cuit;
    private String actividadPrincipal;
    private String email;
    private DomicilioFiscal domicilioFiscal; 

    
    public Empresa() {
    }

    
    public Empresa(Long id, boolean eliminado, String razonSocial, String cuit,
                   String actividadPrincipal, String email,
                   DomicilioFiscal domicilioFiscal) {
        super(id, eliminado);
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.actividadPrincipal = actividadPrincipal;
        this.email = email;
        this.domicilioFiscal = domicilioFiscal;
    }

    
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getActividadPrincipal() {
        return actividadPrincipal;
    }

    public void setActividadPrincipal(String actividadPrincipal) {
        this.actividadPrincipal = actividadPrincipal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DomicilioFiscal getDomicilioFiscal() {
        return domicilioFiscal;
    }

    public void setDomicilioFiscal(DomicilioFiscal domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }

    
    @Override
    public String toString() {
        String domicilioStr = (domicilioFiscal != null)
                ? domicilioFiscal.toString()
                : "  (Sin domicilio fiscal asignado)\n";

        return "Empresa:\n" +
                "  ID: " + id + "\n" +
                "  Razón Social: " + razonSocial + "\n" +
                "  CUIT: " + cuit + "\n" +
                "  Actividad Principal: " + actividadPrincipal + "\n" +
                "  Email: " + email + "\n" +
                "  Domicilio Fiscal:\n" + domicilioStr;
    }
}
