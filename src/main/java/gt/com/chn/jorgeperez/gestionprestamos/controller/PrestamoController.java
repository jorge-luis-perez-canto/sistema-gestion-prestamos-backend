package gt.com.chn.jorgeperez.gestionprestamos.controller;

import gt.com.chn.jorgeperez.gestionprestamos.dto.RespuestaAprobacionDTO;
import gt.com.chn.jorgeperez.gestionprestamos.dto.ResumenPrestamoDTO;
import gt.com.chn.jorgeperez.gestionprestamos.dto.SolicitudPrestamoDTO;
import gt.com.chn.jorgeperez.gestionprestamos.model.Prestamo;
import gt.com.chn.jorgeperez.gestionprestamos.service.ClienteService;
import gt.com.chn.jorgeperez.gestionprestamos.service.PrestamoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private ClienteService clienteService;

    // Permitir a un cliente solicitar un nuevo préstamo bancario
    @PostMapping
    public ResponseEntity<Prestamo> solicitarPrestamo(@RequestBody SolicitudPrestamoDTO solicitud) {
        log.info("Solicitando nuevo préstamo: {}", solicitud.toString());
        Prestamo nuevoPrestamo = prestamoService.guardarPrestamo(solicitud);
        return new ResponseEntity<>(nuevoPrestamo, HttpStatus.CREATED);
    }


    // Ver la lista de solicitudes de préstamos pendientes para cada cliente, incluyendo su estado actual
    @GetMapping("/pendientes/cliente/{clienteId}")
    public ResponseEntity<List<Prestamo>> solicitudesPendientesPorCliente(@PathVariable Long clienteId) {
        log.info("Consultando solicitudes de préstamos pendientes para el cliente con ID: {}", clienteId);
        List<Prestamo> prestamosPendientes = prestamoService.obtenerSolicitudesPrestamosPendientesPorClienteId(clienteId);
        return new ResponseEntity<>(prestamosPendientes, HttpStatus.OK);
    }

    // Aprobar una solicitud de préstamo
    @PostMapping("/aprobar/{id}")
    public ResponseEntity<RespuestaAprobacionDTO> aprobarPrestamo(@PathVariable Long id) {
        log.info("Aprobando solicitud de préstamo con ID: {}", id);
        RespuestaAprobacionDTO respuesta = prestamoService.aprobarPrestamo(id);
        return ResponseEntity.ok(respuesta);
    }


    // Rechazar una solicitud de préstamo
    @PostMapping("/rechazar/{prestamoId}")
    public ResponseEntity<Void> rechazarPrestamo(@PathVariable Long prestamoId, @RequestBody String motivoRechazo) {
        log.info("Rechazando solicitud de préstamo con ID: {}. Motivo: {}", prestamoId, motivoRechazo);
        prestamoService.rechazarPrestamo(prestamoId, motivoRechazo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Listar todos los préstamos de un cliente con su estado
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ResumenPrestamoDTO>> listarPrestamosPorCliente(@PathVariable Long clienteId) {
        log.info("Listando préstamos del cliente con ID: {}", clienteId);
        List<ResumenPrestamoDTO> prestamos = prestamoService.obtenerResumenPrestamosPorClienteId(clienteId);
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }


}
