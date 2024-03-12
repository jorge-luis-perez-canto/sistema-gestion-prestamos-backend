package gt.com.chn.jorgeperez.gestionprestamos.dto;

public class RespuestaAprobacionDTO {
    private String mensaje;
    private Long prestamoId;
    private String estado;

    // Constructor, getters y setters

    public RespuestaAprobacionDTO() {
    }

    public RespuestaAprobacionDTO(String mensaje) {
        this.mensaje = mensaje;
    }

    public RespuestaAprobacionDTO(String mensaje, Long prestamoId, String estado) {
        this.mensaje = mensaje;
        this.prestamoId = prestamoId;
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Long prestamoId) {
        this.prestamoId = prestamoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
