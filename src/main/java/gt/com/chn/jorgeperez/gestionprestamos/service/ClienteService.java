package gt.com.chn.jorgeperez.gestionprestamos.service;

import gt.com.chn.jorgeperez.gestionprestamos.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    // Métodos CRUD
    Cliente guardarCliente(Cliente cliente);

    Optional<Cliente> obtenerClientePorId(Long id);

    List<Cliente> listarTodosLosClientes();

    Cliente actualizarCliente(Cliente cliente);

    void eliminarCliente(Long id);

    boolean existeCliente(Long clienteId);

}
