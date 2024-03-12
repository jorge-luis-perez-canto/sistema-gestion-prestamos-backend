package gt.com.chn.jorgeperez.gestionprestamos.service.impl;

import gt.com.chn.jorgeperez.gestionprestamos.exception.CustomExceptions;
import gt.com.chn.jorgeperez.gestionprestamos.model.Cliente;
import gt.com.chn.jorgeperez.gestionprestamos.repository.ClienteRepository;
import gt.com.chn.jorgeperez.gestionprestamos.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ClienteServiceImpl implements ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final ClienteRepository clienteRepository;

    /**
     * Constructor que inyecta el repositorio de clientes para interactuar con la base de datos.
     */
    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Guarda un nuevo cliente en la base de datos.
     *
     * @param cliente El cliente a guardar.
     * @return El cliente guardado.
     * @throws CustomExceptions.DatabaseOperationException Si ocurre un error relacionado con la base de datos.
     */
    @Override
    public Cliente guardarCliente(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomExceptions.DatabaseOperationException("Error al guardar el cliente debido a restricciones de la base de datos", ex);
        }
    }

    /**
     * Busca un cliente por su ID.
     *
     * @param id El ID del cliente a buscar.
     * @return Un Optional que puede o no contener el cliente buscado.
     * @throws ServiceException Si ocurre un error al buscar el cliente.
     */
    @Override
    public Optional<Cliente> obtenerClientePorId(Long id) {
        try {
            return Optional.ofNullable(clienteRepository.findById(id).orElseThrow(() -> new CustomExceptions.ClienteNotFoundException("Cliente no encontrado con ID: " + id)));
        } catch (DataAccessException e) {
            throw new ServiceException("Error al obtener el cliente por ID", e);
        }
    }

    /**
     * Lista todos los clientes registrados en la base de datos.
     *
     * @return Una lista de todos los clientes.
     */
    @Override
    public List<Cliente> listarTodosLosClientes() {
        return clienteRepository.findAll();
    }

    /**
     * Actualiza la información de un cliente existente.
     *
     * @param cliente El cliente con la información actualizada.
     * @return El cliente actualizado.
     * @throws ServiceException Si ocurre un error al actualizar el cliente.
     */
    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar el cliente", e);
        }
    }

    /**
     * Elimina un cliente de la base de datos por su ID.
     *
     * @param id El ID del cliente a eliminar.
     * @throws ServiceException Si ocurre un error al eliminar el cliente.
     */

    @Override
    public void eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID: " + id));

        boolean tieneDeudas = cliente.getPrestamos().stream().anyMatch(prestamo -> !"Finalizado".equals(prestamo.getEstado()));

        if (tieneDeudas) {
            // Marcar el cliente como inactivo
            cliente.setActivo(false);
            clienteRepository.save(cliente);
            // Lanzar una excepción personalizada indicando que el cliente no puede ser eliminado debido a deudas
            throw new CustomExceptions.ClienteConDeudasException("No se puede eliminar el cliente porque tiene deudas pendientes.");
        } else {
            // Eliminar físicamente
            clienteRepository.deleteById(id);
            throw new CustomExceptions.ClienteEliminadoExitosamenteException("Cliente eliminado con éxito");
        }
    }

    // Implementa el método existeCliente aquí
    public boolean existeCliente(Long clienteId) {
        return clienteRepository.existsById(clienteId);
    }

    /**
     * Clase de excepción para manejar errores de servicio.
     */
    public static class ServiceException extends RuntimeException {
        public ServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
