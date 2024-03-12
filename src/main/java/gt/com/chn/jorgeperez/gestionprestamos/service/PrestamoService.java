package gt.com.chn.jorgeperez.gestionprestamos.service;

import gt.com.chn.jorgeperez.gestionprestamos.dto.RespuestaAprobacionDTO;
import gt.com.chn.jorgeperez.gestionprestamos.dto.ResumenPrestamoDTO;
import gt.com.chn.jorgeperez.gestionprestamos.dto.SolicitudPrestamoDTO;
import gt.com.chn.jorgeperez.gestionprestamos.model.Pago;
import gt.com.chn.jorgeperez.gestionprestamos.model.Prestamo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PrestamoService {

    // Métodos CRUD
    Prestamo guardarPrestamo(SolicitudPrestamoDTO solicitudPrestamo);

    Optional<Prestamo> obtenerPrestamoPorId(Long id);

    List<Prestamo> listarTodosLosPrestamos();

    Prestamo actualizarPrestamo(Prestamo prestamo);

    void eliminarPrestamo(Long id);

    // Métodos específicos para la gestión de la solicitud de préstamos
    List<Prestamo> buscarPrestamosPorEstado(String estado);

    RespuestaAprobacionDTO aprobarPrestamo(Long id);

    void rechazarPrestamo(Long id, String motivoRechazo);

    List<Prestamo> buscarPrestamosConPagosPendientes();

    List<Prestamo> obtenerSolicitudesPrestamosPendientes();

    BigDecimal calcularTotalMontoPrestadoPorEstado(String estado);

    // Método para registrar detalles específicos de la solicitud al momento de su creación o actualización
    Prestamo registrarDetallesSolicitudPrestamo(Long prestamoId, String detalles);

    List<Pago> obtenerPagosPorPrestamoId(Long prestamoId);

    List<ResumenPrestamoDTO> obtenerResumenPrestamosPorClienteId(Long clienteId);

    // Obtener solicitudes de préstamos pendientes por clienteId
    List<Prestamo> obtenerSolicitudesPrestamosPendientesPorClienteId(Long clienteId);
}
