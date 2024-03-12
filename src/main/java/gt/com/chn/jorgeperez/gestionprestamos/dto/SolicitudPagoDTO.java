package gt.com.chn.jorgeperez.gestionprestamos.dto;

import java.math.BigDecimal;
import java.util.Date;

public class SolicitudPagoDTO {
    private Long prestamoId;
    private BigDecimal montoPago;
    private Date fechaPago;

    // Getters y Setters

    public Long getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Long prestamoId) {
        this.prestamoId = prestamoId;
    }

    public BigDecimal getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(BigDecimal montoPago) {
        this.montoPago = montoPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }
}
