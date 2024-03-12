package gt.com.chn.jorgeperez.gestionprestamos.repository;

import gt.com.chn.jorgeperez.gestionprestamos.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repositorio para la entidad Prestamo.
 * Proporciona operaciones CRUD básicas sin necesidad de implementación adicional.
 * <p>
 * Operaciones CRUD heredadas de JpaRepository:
 * - save(S entity): Guarda una entidad y la devuelve una vez guardada.
 * - findById(ID id): Encuentra una entidad por su ID.
 * - findAll(): Recupera todas las entidades.
 * - deleteById(ID id): Elimina la entidad con el ID especificado.
 * - delete(S entity): Elimina la entidad dada.
 * - count(): Devuelve la cantidad de entidades disponibles.
 * - existsById(ID id): Verifica si existe una entidad con el ID dado.
 */
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    // Métodos personalizados...

    /**
     * Encuentra préstamos por su estado.
     *
     * @param estado El estado del préstamo.
     * @return Una lista de préstamos que se encuentran en el estado especificado.
     */
    List<Prestamo> findByEstado(String estado);

    /**
     * Encuentra préstamos que superen un monto solicitado específico.
     *
     * @param monto El monto mínimo del préstamo.
     * @return Una lista de préstamos cuyo monto solicitado sea mayor que el especificado.
     */
    List<Prestamo> findByMontoSolicitadoGreaterThan(BigDecimal monto);


    /**
     * Encuentra préstamos por el ID del cliente.
     *
     * @param clienteId El ID del cliente.
     * @return Una lista de préstamos solicitados por el cliente especificado.
     */
    List<Prestamo> findByClienteClienteId(Long clienteId);


    /**
     * Encuentra el total de monto prestado por estado del préstamo.
     *
     * @param estado El estado del préstamo.
     * @return El total del monto prestado para préstamos en el estado especificado.
     */
    @Query("select sum(p.montoSolicitado) from Prestamo p where p.estado = :estado")
    BigDecimal findTotalMontoPrestadoByEstado(@Param("estado") String estado);

    /**
     * Encuentra préstamos que tienen pagos pendientes o atrasados.
     *
     * @return Una lista de préstamos con pagos pendientes o atrasados.
     */
    @Query("SELECT p FROM Prestamo p INNER JOIN p.pagos pg WHERE pg.fechaPago < CURRENT_DATE AND p.estado = 'En Proceso'")
    List<Prestamo> findPrestamosConPagosPendientes();


    // Filtrar préstamos por clienteId y estado
    List<Prestamo> findByClienteClienteIdAndEstado(Long clienteId, String estado);

}
