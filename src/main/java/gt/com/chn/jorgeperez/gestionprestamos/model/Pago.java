package gt.com.chn.jorgeperez.gestionprestamos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.util.Date;

/**
 * La clase Pago representa un pago realizado por un cliente hacia un préstamo bancario.
 * Contiene detalles del pago, incluyendo el monto y la fecha del pago.
 */

@Entity
@Table(name = "pago")

public class Pago {

    /**
     * Identificador único del pago.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_id")
    private Long pagoId;

    /**
     * Préstamo asociado a este pago. Representa una relación muchos a uno.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestamo_id", nullable = false)
    private Prestamo prestamo;

    /**
     * Monto del pago realizado.
     */
    @NotNull
    @Column(name = "monto_pago")
    private BigDecimal montoPago;

    /**
     * Fecha en que se realizó el pago.
     */
    @NotNull
    @PastOrPresent
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_pago")
    private Date fechaPago;

    /**
     * Saldo del préstamo antes de realizar el pago.
     */
    @NotNull
    @Column(name = "saldo_anterior")
    private BigDecimal saldoAnterior;

    /**
     * Saldo del préstamo después de realizar el pago.
     */
    @NotNull
    @Column(name = "saldo_actual")
    private BigDecimal saldoActual;

    public Long getPagoId() {
        return pagoId;
    }

    public void setPagoId(Long pagoId) {
        this.pagoId = pagoId;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
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

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }

    @Override
    public String toString() {
        return "Pago{" + "pagoId=" + pagoId + ", prestamo=" + prestamo + ", montoPago=" + montoPago + ", fechaPago=" + fechaPago + ", saldoAnterior=" + saldoAnterior + ", saldoActual=" + saldoActual + '}';
    }
}
