package entities;

public abstract class BaseEntity {

    protected Long id;
    protected boolean eliminado;

    // Constructor vacío 
    public BaseEntity() {
        this.eliminado = false;
    }

    // Constructor completo 
    public BaseEntity(Long id, boolean eliminado) {
        this.id = id;
        this.eliminado = eliminado;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}
