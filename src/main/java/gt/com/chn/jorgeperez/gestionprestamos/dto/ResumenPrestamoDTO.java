package gt.com.chn.jorgeperez.gestionprestamos.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ResumenPrestamoDTO {
    private Long prestamoId;
    private BigDecimal montoTotalPrestado;
    private BigDecimal saldoPendiente;
    private String estado;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaUltimoPago;

    // Constructor sin argumentos
    public ResumenPrestamoDTO() {
    }

    // Constructor con todos los campos
    public ResumenPrestamoDTO(Long prestamoId, BigDecimal montoTotalPrestado, BigDecimal saldoPendiente, String estado, Date fechaInicio, Date fechaFin, Date fechaUltimoPago) {
        this.prestamoId = prestamoId;
        this.montoTotalPrestado = montoTotalPrestado;
        this.saldoPendiente = saldoPendiente;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaUltimoPago = fechaUltimoPago;
    }

    public Long getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Long prestamoId) {
        this.prestamoId = prestamoId;
    }

    public BigDecimal getMontoTotalPrestado() {
        return montoTotalPrestado;
    }

    public void setMontoTotalPrestado(BigDecimal montoTotalPrestado) {
        this.montoTotalPrestado = montoTotalPrestado;
    }

    public BigDecimal getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(BigDecimal saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaUltimoPago() {
        return fechaUltimoPago;
    }

    public void setFechaUltimoPago(Date fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
    }
}
