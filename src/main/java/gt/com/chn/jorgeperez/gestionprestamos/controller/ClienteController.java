package gt.com.chn.jorgeperez.gestionprestamos.controller;

import gt.com.chn.jorgeperez.gestionprestamos.model.Cliente;
import gt.com.chn.jorgeperez.gestionprestamos.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> agregarCliente(@RequestBody Cliente cliente) {
        log.info("Agregando cliente: {}", cliente);
        Cliente clienteGuardado = clienteService.guardarCliente(cliente);
        return new ResponseEntity<>(clienteGuardado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodosLosClientes() {
        log.info("Listando todos los clientes");
        System.out.println("Listando todos los clientes");
        List<Cliente> clientes = clienteService.listarTodosLosClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        log.info("Obteniendo cliente por ID: {}", id);
        return clienteService.obtenerClientePorId(id).map(cliente -> new ResponseEntity<>(cliente, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        log.info("Actualizando cliente con ID: {}", id);
        return clienteService.obtenerClientePorId(id).map(cliente -> {
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setApellido(clienteActualizado.getApellido());
            cliente.setNumeroIdentificacion(clienteActualizado.getNumeroIdentificacion());
            cliente.setFechaNacimiento(clienteActualizado.getFechaNacimiento());
            cliente.setDireccion(clienteActualizado.getDireccion());
            cliente.setCorreoElectronico(clienteActualizado.getCorreoElectronico());
            cliente.setTelefono(clienteActualizado.getTelefono());
            Cliente clienteGuardado = clienteService.actualizarCliente(cliente);
            return new ResponseEntity<>(clienteGuardado, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        log.info("Eliminando cliente con ID: {}", id);
        clienteService.eliminarCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
