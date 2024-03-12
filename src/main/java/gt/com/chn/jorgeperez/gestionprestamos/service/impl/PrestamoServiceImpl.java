package gt.com.chn.jorgeperez.gestionprestamos.service.impl;

import gt.com.chn.jorgeperez.gestionprestamos.dto.RespuestaAprobacionDTO;
import gt.com.chn.jorgeperez.gestionprestamos.dto.ResumenPrestamoDTO;
import gt.com.chn.jorgeperez.gestionprestamos.dto.SolicitudPrestamoDTO;
import gt.com.chn.jorgeperez.gestionprestamos.exception.CustomExceptions;
import gt.com.chn.jorgeperez.gestionprestamos.model.Cliente;
import gt.com.chn.jorgeperez.gestionprestamos.model.Pago;
import gt.com.chn.jorgeperez.gestionprestamos.model.Prestamo;
import gt.com.chn.jorgeperez.gestionprestamos.repository.ClienteRepository;
import gt.com.chn.jorgeperez.gestionprestamos.repository.PagoRepository;
import gt.com.chn.jorgeperez.gestionprestamos.repository.PrestamoRepository;
import gt.com.chn.jorgeperez.gestionprestamos.service.PrestamoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Prestamo guardarPrestamo(SolicitudPrestamoDTO solicitud) {
        Cliente cliente = clienteRepository.findById(solicitud.getClienteId()).orElseThrow(() -> new CustomExceptions.ClienteNotFoundException("El cliente con ID: " + solicitud.getClienteId() + " no existe."));

        Prestamo prestamo = new Prestamo();
        prestamo.setCliente(cliente);
        prestamo.setMontoSolicitado(solicitud.getMontoSolicitado());
        prestamo.setPlazo(solicitud.getPlazo());
        prestamo.setEstado(solicitud.getEstado());
        prestamo.setFechaSolicitud(solicitud.getFechaSolicitud());
        prestamo.setTasaInteres(solicitud.getTasaInteres());

        return prestamoRepository.save(prestamo);
    }

    @Override
    public Optional<Prestamo> obtenerPrestamoPorId(Long id) {
        // Implementación para obtener un préstamo por su ID
        return prestamoRepository.findById(id);
    }

    @Override
    public List<Prestamo> listarTodosLosPrestamos() {
        // Implementación para listar todos los préstamos
        return prestamoRepository.findAll();
    }

    @Override
    public Prestamo actualizarPrestamo(Prestamo prestamo) {
        // Verificar si el préstamo y su ID no son nulos
        if (prestamo == null || prestamo.getPrestamoId() == null) {
            throw new IllegalArgumentException("El préstamo o el ID del préstamo no pueden ser nulos");
        }

        // Verificar si el préstamo existe
        Prestamo existente = prestamoRepository.findById(prestamo.getPrestamoId()).orElseThrow(() -> new EntityNotFoundException("El préstamo con ID " + prestamo.getPrestamoId() + " no existe"));

        // Validación del estado del préstamo
        if (!prestamo.getEstado().matches("Aprobado|Rechazado|En Proceso")) {
            throw new IllegalArgumentException("Estado del préstamo no válido");
        }

        // Validación del monto solicitado
        if (prestamo.getMontoSolicitado().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto solicitado debe ser mayor que cero");
        }

        // Si todas las validaciones pasan, actualizar el préstamo
        return prestamoRepository.save(prestamo);
    }

    @Override
    public void eliminarPrestamo(Long id) {
        // Primero, puedes verificar si el préstamo existe antes de intentar eliminarlo.
        Prestamo prestamo = prestamoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado con ID: " + id));

        // Si el préstamo existe, procedemos a eliminarlo.
        prestamoRepository.deleteById(id);
    }

    @Override
    public RespuestaAprobacionDTO aprobarPrestamo(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id).orElseThrow(() -> new CustomExceptions.PrestamoNotFoundException(id));

        if ("Aprobado".equals(prestamo.getEstado())) {
            throw new CustomExceptions.PrestamoYaAprobadoException(id);
        }

        prestamo.setEstado("Aprobado");
        Prestamo prestamoActualizado = prestamoRepository.save(prestamo);
        return new RespuestaAprobacionDTO(String.format("La solicitud de préstamo con ID %d ha sido aprobada exitosamente.", id), prestamoActualizado.getPrestamoId(), prestamoActualizado.getEstado());
    }


    @Override
    public void rechazarPrestamo(Long id, String motivoRechazo) {
        Prestamo prestamo = prestamoRepository.findById(id).orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
        prestamo.setEstado("Rechazado");
        prestamoRepository.save(prestamo);
    }

    // Implementación de los demás métodos necesarios

    @Override
    public List<Prestamo> buscarPrestamosPorEstado(String estado) {
        return prestamoRepository.findByEstado(estado);
    }

    public List<Prestamo> buscarPrestamosPorMontoMayorA(BigDecimal monto) {
        return prestamoRepository.findByMontoSolicitadoGreaterThan(monto);
    }

    @Override
    public BigDecimal calcularTotalMontoPrestadoPorEstado(String estado) {
        return prestamoRepository.findTotalMontoPrestadoByEstado(estado);
    }

    @Override
    public List<Prestamo> buscarPrestamosConPagosPendientes() {
        return prestamoRepository.findPrestamosConPagosPendientes();
    }

    @Override
    public List<Prestamo> obtenerSolicitudesPrestamosPendientes() {
        return prestamoRepository.findByEstado("En Proceso");
    }


    @Override
    public Prestamo registrarDetallesSolicitudPrestamo(Long prestamoId, String detalles) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId).orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
        prestamo.setDetalles(detalles);
        return prestamoRepository.save(prestamo);
    }

    @Override
    public List<Pago> obtenerPagosPorPrestamoId(Long prestamoId) {
        return pagoRepository.findByPrestamoPrestamoId(prestamoId);
    }


    public BigDecimal calcularSaldoPendiente(Long prestamoId) {
        // Todos los pagos realizados para este préstamo
        List<Pago> pagos = pagoRepository.findByPrestamoPrestamoId(prestamoId);
        BigDecimal totalPagado = pagos.stream().map(Pago::getMontoPago).reduce(BigDecimal.ZERO, BigDecimal::add);
        // Monto original del préstamo
        Prestamo prestamo = prestamoRepository.findById(prestamoId).orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));
        BigDecimal montoSolicitado = prestamo.getMontoSolicitado();

        // Calcular saldo pendiente
        return montoSolicitado.subtract(totalPagado);
    }

    @Override
    public List<ResumenPrestamoDTO> obtenerResumenPrestamosPorClienteId(Long clienteId) {
        List<Prestamo> prestamos = prestamoRepository.findByClienteClienteId(clienteId);
        return prestamos.stream().map(prestamo -> {
            ResumenPrestamoDTO dto = new ResumenPrestamoDTO();
            dto.setPrestamoId(prestamo.getPrestamoId());
            dto.setMontoTotalPrestado(prestamo.getMontoSolicitado());
            dto.setSaldoPendiente(prestamo.getSaldoActual());
            dto.setEstado(prestamo.getEstado());
            // Obtener la fecha del último pago
            List<Pago> pagosDelPrestamo = pagoRepository.findByPrestamoPrestamoIdOrderByFechaPagoDesc(prestamo.getPrestamoId());
            if (!pagosDelPrestamo.isEmpty()) {
                // El primer elemento es el último pago debido al orden descendente
                dto.setFechaUltimoPago(pagosDelPrestamo.get(0).getFechaPago());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    public List<Prestamo> obtenerSolicitudesPrestamosPendientesPorClienteId(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new CustomExceptions.ClienteNotFoundException("El cliente con ID " + clienteId + " no existe.");
        }

        List<Prestamo> prestamosPendientes = prestamoRepository.findByClienteClienteIdAndEstado(clienteId, "En Proceso");

        if (prestamosPendientes.isEmpty()) {
            throw new CustomExceptions.PrestamosPendientesNotFoundException("El cliente con ID " + clienteId + " no tiene préstamos pendientes.");
        }

        return prestamosPendientes;
    }
}
