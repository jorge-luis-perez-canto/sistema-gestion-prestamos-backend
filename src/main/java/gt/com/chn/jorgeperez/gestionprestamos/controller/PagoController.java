package gt.com.chn.jorgeperez.gestionprestamos.controller;

import gt.com.chn.jorgeperez.gestionprestamos.dto.ResumenPrestamoDTO;
import gt.com.chn.jorgeperez.gestionprestamos.model.Pago;
import gt.com.chn.jorgeperez.gestionprestamos.service.PagoService;
import gt.com.chn.jorgeperez.gestionprestamos.service.PrestamoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PrestamoService prestamoService;

    // Listar préstamos aprobados por cliente
    @GetMapping("/prestamos-aprobados/{clienteId}")
    public ResponseEntity<List<ResumenPrestamoDTO>> listarPrestamosAprobadosPorCliente(@PathVariable Long clienteId) {
        log.info("Listando préstamos aprobados para el cliente con ID: {}", clienteId);
        List<ResumenPrestamoDTO> prestamos = pagoService.listarPrestamosAprobadosPorCliente(clienteId);
        return ResponseEntity.ok(prestamos);
    }

    // Registrar pago
    @PostMapping("/registrar")
    public ResponseEntity<Pago> registrarPago(@RequestBody Pago pago) {
        log.info("Registrando pago: {}", pago);
        Pago pagoGuardado = pagoService.guardarPago(pago);
        return new ResponseEntity<>(pagoGuardado, HttpStatus.CREATED);
    }

    // Consultar saldo pendiente
    @GetMapping("/saldo-pendiente/{prestamoId}")
    public ResponseEntity<BigDecimal> consultarSaldoPendiente(@PathVariable Long prestamoId) {
        log.info("Consultando saldo pendiente para el préstamo con ID: {}", prestamoId);
        BigDecimal saldoPendiente = pagoService.consultarSaldoPendiente(prestamoId);
        return ResponseEntity.ok(saldoPendiente);
    }

    // Actualizar estado de préstamo al finiquitar deuda
    @PutMapping("/finalizar-prestamo/{prestamoId}")
    public ResponseEntity<?> finalizarPrestamo(@PathVariable Long prestamoId) {
        try {
            log.info("Finalizando préstamo con ID: {}", prestamoId);
            pagoService.finalizarPrestamo(prestamoId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
