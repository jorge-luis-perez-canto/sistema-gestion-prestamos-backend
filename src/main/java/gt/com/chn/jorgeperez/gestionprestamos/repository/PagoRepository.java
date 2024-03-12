package gt.com.chn.jorgeperez.gestionprestamos.repository;

import gt.com.chn.jorgeperez.gestionprestamos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Pago.
 * Permite realizar operaciones CRUD y consultas personalizadas para pagos.
 * Este interfaz extiende JpaRepository, heredando operaciones CRUD básicas para la entidad Pago sin necesidad de implementación adicional. Incluye:
 * <p>
 * - save(S entity): Guarda una entidad dada. Usa merge si la entidad ya existe (es decir, tiene un ID) o persist si es nueva. Retorna la entidad guardada.
 * - findById(ID id): Busca una entidad por su ID. Retorna un Optional que puede estar vacío si no se encuentra la entidad.
 * - findAll(): Recupera todas las entidades de este tipo. Retorna una lista de entidades.
 * - count(): Cuenta el total de entidades disponibles.
 * - deleteById(ID id): Elimina la entidad con el ID proporcionado.
 * - delete(S entity): Elimina la entidad dada.
 * - existsById(ID id): Verifica si una entidad con el ID dado existe.
 * <p>
 * Además, a continuación se agregan métodos personalizados según las necesidades específicas de la aplicación.
 */


@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    // Métodos personalizados...

    /**
     * Encuentra pagos realizados en un rango de fechas específico.
     *
     * @param inicio La fecha de inicio del rango.
     * @param fin    La fecha de fin del rango.
     * @return Una lista de pagos realizados dentro del rango de fechas especificado.
     */
    List<Pago> findByFechaPagoBetween(Date inicio, Date fin);

    /**
     * Encuentra pagos cuyo monto supera un valor específico.
     *
     * @param monto El monto mínimo del pago.
     * @return Una lista de pagos cuyo monto es mayor al especificado.
     */
    List<Pago> findByMontoPagoGreaterThan(BigDecimal monto);


    /**
     * Encuentra los pagos realizados por un cliente específico, basado en el ID del cliente.
     *
     * @param clienteId El ID del cliente.
     * @return Una lista de pagos realizados por el cliente.
     */
    @Query("SELECT p FROM Pago p WHERE p.prestamo.cliente.clienteId = :clienteId")
    List<Pago> findPagosByClienteId(@Param("clienteId") Long clienteId);

    /**
     * Encuentra los pagos realizados en un día específico.
     *
     * @param fecha La fecha de pago.
     * @return Una lista de pagos realizados en la fecha especificada.
     */
    List<Pago> findByFechaPago(Date fecha);

    /**
     * Encuentra pagos realizados para préstamos de una cierta cantidad.
     *
     * @param montoSolicitado El monto solicitado del préstamo.
     * @return Una lista de pagos realizados para préstamos de la cantidad especificada.
     */
    @Query("SELECT pg FROM Pago pg INNER JOIN pg.prestamo p WHERE p.montoSolicitado = :montoSolicitado")
    List<Pago> findPagosParaPrestamosDeMontoEspecifico(@Param("montoSolicitado") BigDecimal montoSolicitado);


    /**
     * Encuentra pagos realizados para un préstamo específico.
     *
     * @param prestamoId El ID del préstamo asociado.
     * @return Una lista de pagos asociados al préstamo.
     */
    List<Pago> findByPrestamoPrestamoId(Long prestamoId);


    List<Pago> findByPrestamoPrestamoIdOrderByFechaPagoDesc(Long prestamoId);

    /**
     * Encuentra el último pago realizado para un préstamo específico, ordenado por fecha de pago de manera descendente.
     *
     * @param prestamoId El identificador del préstamo.
     * @return Un Optional conteniendo el pago más reciente si existe.
     */
    Optional<Pago> findFirstByPrestamoPrestamoIdOrderByFechaPagoDesc(Long prestamoId);

}
