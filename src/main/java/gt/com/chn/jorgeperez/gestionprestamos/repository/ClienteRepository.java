package gt.com.chn.jorgeperez.gestionprestamos.repository;

import gt.com.chn.jorgeperez.gestionprestamos.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Interfaz para definir operaciones de base de datos relacionadas con la entidad Cliente.
 * Extiende JpaRepository, proporcionando operaciones CRUD básicas sin necesidad de implementación:
 * - save(S entity): Guarda una entidad y la devuelve una vez guardada.
 * - findById(ID id): Encuentra una entidad por su ID.
 * - findAll(): Recupera todas las entidades.
 * - deleteById(ID id): Elimina la entidad con el ID especificado.
 * - delete(S entity): Elimina la entidad dada.
 * - count(): Devuelve la cantidad de entidades disponibles.
 * - existsById(ID id): Verifica si existe una entidad con el ID dado.
 * Estos métodos no necesitan ser definidos explícitamente en esta interfaz.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Métodos personalizados...

    /**
     * Encuentra clientes por apellido.
     *
     * @param apellido El apellido de los clientes a buscar.
     * @return Una lista de clientes que tienen el apellido dado.
     */
    List<Cliente> findByApellido(String apellido);

    /**
     * Encuentra clientes por nombre y apellido.
     *
     * @param nombre   El nombre de los clientes a buscar.
     * @param apellido El apellido de los clientes a buscar.
     * @return Una lista de clientes que tienen el nombre y apellido dados.
     */
    List<Cliente> findByNombreAndApellido(String nombre, String apellido);


    /**
     * Encuentra clientes por rango de fechas de nacimiento.
     *
     * @param inicio La fecha de inicio del rango de búsqueda.
     * @param fin    La fecha de fin del rango de búsqueda.
     * @return Una lista de clientes que nacieron entre las fechas dadas.
     */
    List<Cliente> findByFechaNacimientoBetween(Date inicio, Date fin);


    /**
     * Encuentra clientes que no tienen préstamos asignados.
     *
     * @return Una lista de clientes que actualmente no tienen préstamos.
     */
    @Query("select c from Cliente c where c.prestamos is empty")
    List<Cliente> findClientesSinPrestamos();


    BigDecimal findTotalPrestamosByClienteId(Long clienteId);

}
