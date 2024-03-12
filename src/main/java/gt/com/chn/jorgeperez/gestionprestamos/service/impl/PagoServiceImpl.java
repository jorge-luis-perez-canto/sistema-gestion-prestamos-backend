package gt.com.chn.jorgeperez.gestionprestamos.service.impl;

import gt.com.chn.jorgeperez.gestionprestamos.dto.ResumenPrestamoDTO;
import gt.com.chn.jorgeperez.gestionprestamos.dto.SolicitudPagoDTO;
import gt.com.chn.jorgeperez.gestionprestamos.exception.CustomExceptions;
import gt.com.chn.jorgeperez.gestionprestamos.model.Pago;
import gt.com.chn.jorgeperez.gestionprestamos.model.Prestamo;
import gt.com.chn.jorgeperez.gestionprestamos.repository.PagoRepository;
import gt.com.chn.jorgeperez.gestionprestamos.repository.PrestamoRepository;
import gt.com.chn.jorgeperez.gestionprestamos.service.PagoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    public PagoServiceImpl(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }


    @Override
    public Pago guardarPago(Pago pago) {
        try {
            return pagoRepository.save(pago);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomExceptions.PagoProcessingException("No se puede procesar el pago: violación de integridad.", ex);
        }
    }

    @Override
    public Optional<Pago> obtenerPagoPorId(Long id) {
        return pagoRepository.findById(id);
    }

    @Override
    public List<Pago> listarTodosLosPagos() {
        return pagoRepository.findAll();
    }

    @Override
    public Pago actualizarPago(Pago pago) {
        if (pago == null || pago.getPagoId() == null) {
            throw new IllegalArgumentException("El pago y su ID no pueden ser nulos.");
        }

        // Verificar si el pago existe
        Pago pagoExistente = pagoRepository.findById(pago.getPagoId()).orElseThrow(() -> new EntityNotFoundException("Pago no encontrado con ID: " + pago.getPagoId()));

        // Realizar la actualización. Esto podría incluir validaciones adicionales o transformaciones antes de guardar.
        return pagoRepository.save(pago);
    }

    @Override
    public void eliminarPago(Long id) {
        try {
            pagoRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new CustomExceptions.PagoProcessingException("Error al eliminar el pago", e);
        }
    }

    @Override
    public List<Pago> buscarPagosPorPrestamoId(Long prestamoId) {
        try {
            return pagoRepository.findByPrestamoPrestamoId(prestamoId);
        } catch (DataAccessException e) {
            throw new CustomExceptions.PagoProcessingException("Error al buscar pagos por ID de préstamo", e);
        }
    }

    @Override
    public Date obtenerFechaUltimoPagoPorPrestamoId(Long prestamoId) {
        return pagoRepository.findFirstByPrestamoPrestamoIdOrderByFechaPagoDesc(prestamoId).map(Pago::getFechaPago).orElse(null);
    }


    @Override
    public Pago registrarPago(SolicitudPagoDTO solicitudPagoDTO) {
        Prestamo prestamo = prestamoRepository.findById(solicitudPagoDTO.getPrestamoId()).orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado con ID: " + solicitudPagoDTO.getPrestamoId()));

        BigDecimal saldoAnterior = prestamo.getSaldoActual() == null ? prestamo.getMontoSolicitado() : prestamo.getSaldoActual();
        BigDecimal saldoActual = saldoAnterior.subtract(solicitudPagoDTO.getMontoPago());

        Pago nuevoPago = new Pago();
        nuevoPago.setPrestamo(prestamo);
        nuevoPago.setMontoPago(solicitudPagoDTO.getMontoPago());
        nuevoPago.setFechaPago(solicitudPagoDTO.getFechaPago());
        nuevoPago.setSaldoAnterior(saldoAnterior);
        nuevoPago.setSaldoActual(saldoActual);

        prestamo.setSaldoActual(saldoActual);
        return pagoRepository.save(nuevoPago);
    }

    @Override
    public BigDecimal consultarSaldoPendiente(Long prestamoId) {
        Optional<Prestamo> prestamoOptional = prestamoRepository.findById(prestamoId);
        if (prestamoOptional.isEmpty()) {
            throw new IllegalArgumentException("Préstamo no encontrado");
        }
        Prestamo prestamo = prestamoOptional.get();
        return prestamo.getSaldoActual();
    }

    @Override
    public void finalizarPrestamo(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId).orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
        //BigDecimal saldoPendiente = this.consultarSaldoPendiente(prestamoId);
        BigDecimal saldoPendiente = calcularSaldoPendiente(prestamoId);
        if (saldoPendiente.compareTo(BigDecimal.ZERO) <= 0) {
            prestamo.setEstado("Finalizado");
            prestamo.setFechaFinalizacion(new Date());
            prestamoRepository.save(prestamo);
        } else {
            throw new RuntimeException("El préstamo no se puede finalizar porque aún tiene saldo pendiente.");
        }
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

    @Override
    public List<ResumenPrestamoDTO> listarPrestamosAprobadosPorCliente(Long clienteId) {
        // Filtrar solo los préstamos aprobados del cliente
        List<Prestamo> prestamos = prestamoRepository.findByClienteClienteIdAndEstado(clienteId, "Aprobado");
        return prestamos.stream().map(prestamo -> {
            ResumenPrestamoDTO dto = new ResumenPrestamoDTO();
            dto.setPrestamoId(prestamo.getPrestamoId());
            dto.setMontoTotalPrestado(prestamo.getMontoSolicitado());
            dto.setSaldoPendiente(prestamo.getSaldoActual());
            dto.setEstado(prestamo.getEstado());

            // Obtener la fecha del último pago
            List<Pago> pagosDelPrestamo = pagoRepository.findByPrestamoPrestamoIdOrderByFechaPagoDesc(prestamo.getPrestamoId());
            if (!pagosDelPrestamo.isEmpty()) {
                dto.setFechaUltimoPago(pagosDelPrestamo.get(0).getFechaPago());
            }

            return dto;
        }).collect(Collectors.toList());
    }

    public BigDecimal calcularSaldoPendiente(Long prestamoId) {
        // Obtén todos los pagos realizados para este préstamo
        List<Pago> pagos = pagoRepository.findByPrestamoPrestamoId(prestamoId);
        BigDecimal totalPagado = pagos.stream().map(Pago::getMontoPago).reduce(BigDecimal.ZERO, BigDecimal::add);
        // Encuentra el monto original del préstamo
        Prestamo prestamo = prestamoRepository.findById(prestamoId).orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));
        BigDecimal montoSolicitado = prestamo.getMontoSolicitado();

        // Calcula el saldo pendiente
        return montoSolicitado.subtract(totalPagado);
    }

    @Override
    public List<Prestamo> obtenerSolicitudesPorClienteId(Long clienteId) {
        return prestamoRepository.findByClienteClienteId(clienteId);
    }

}
