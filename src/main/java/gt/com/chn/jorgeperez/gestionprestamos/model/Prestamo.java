package gt.com.chn.jorgeperez.gestionprestamos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * La clase Prestamo representa un préstamo bancario en el sistema.
 * Contiene los detalles del préstamo solicitado por un cliente, incluyendo la cantidad, el plazo, y el estado del préstamo.
 */

@Entity
@Table(name = "prestamo")
public class Prestamo {

    /**
     * Identificador único del préstamo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prestamo_id")
    private Long prestamoId;

    /**
     * Cliente que solicita el préstamo. Representa una relación muchos a uno.
     */

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id", nullable = false)
    private Cliente cliente;

    /**
     * Monto solicitado en el préstamo.
     */
    @NotNull
    @Column(name = "monto_solicitado")
    private BigDecimal montoSolicitado;

    /**
     * Plazo del préstamo en meses.
     */
    @NotNull
    @Column(name = "plazo")
    private Integer plazo;

    /**
     * Estado actual del préstamo (por ejemplo, "Aprobado", "Rechazado", "En Proceso").
     */
    @NotEmpty
    @Column(name = "estado")
    private String estado;

    /**
     * Fecha en que se solicitó el préstamo.
     */
    @NotNull
    @FutureOrPresent
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_solicitud")
    private Date fechaSolicitud;

    /**
     * Tasa de interés aplicable al préstamo.
     */
    @NotNull
    @Column(name = "tasa_interes")
    private BigDecimal tasaInteres;

    // Nuevo atributo agregado
    //@NotNull
    @Column(name = "saldo_actual")
    private BigDecimal saldoActual;

    @Column(name = "fecha_finalizacion")
    @Temporal(TemporalType.DATE)
    private Date fechaFinalizacion;

    @Column(name = "detalles", length = 500)
    private String detalles;

    /**
     * Conjunto de pagos realizados hacia el préstamo.
     */
    @OneToMany(mappedBy = "prestamo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pago> pagos = new HashSet<>();

    public Long getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Long prestamoId) {
        this.prestamoId = prestamoId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(BigDecimal montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Set<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(Set<Pago> pagos) {
        this.pagos = pagos;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "prestamoId=" + prestamoId + ", cliente=" + cliente + ", montoSolicitado=" + montoSolicitado + ", plazo=" + plazo + ", estado='" + estado + '\'' + ", fechaSolicitud=" + fechaSolicitud + ", tasaInteres=" + tasaInteres + ", saldoActual=" + saldoActual + ", fechaFinalizacion=" + fechaFinalizacion + ", detalles='" + detalles + '\'' + ", pagos=" + pagos + '}';
    }
}
