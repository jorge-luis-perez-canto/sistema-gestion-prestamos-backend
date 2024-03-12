package gt.com.chn.jorgeperez.gestionprestamos.service;

import gt.com.chn.jorgeperez.gestionprestamos.dto.ResumenPrestamoDTO;
import gt.com.chn.jorgeperez.gestionprestamos.dto.SolicitudPagoDTO;
import gt.com.chn.jorgeperez.gestionprestamos.model.Pago;
import gt.com.chn.jorgeperez.gestionprestamos.model.Prestamo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones de servicio para gestionar pagos.
 * Define métodos para realizar operaciones CRUD y consultas específicas relacionadas con los pagos.
 */
public interface PagoService {

    Pago guardarPago(Pago pago);

    Optional<Pago> obtenerPagoPorId(Long id); // Método agregado

    List<Pago> listarTodosLosPagos();

    Pago actualizarPago(Pago pago);

    void eliminarPago(Long id);


    List<Pago> buscarPagosPorPrestamoId(Long prestamoId);


    // GestionPrestamosAprobadosYPagosService

    List<Prestamo> obtenerSolicitudesPorClienteId(Long clienteId);

    //void registrarPagoPrestamo(Long prestamoId, BigDecimal montoPago, Date fechaPago);
    Pago registrarPago(SolicitudPagoDTO solicitudPagoDTO);

    BigDecimal consultarSaldoPendiente(Long prestamoId);

    void finalizarPrestamo(Long prestamoId);

    List<ResumenPrestamoDTO> listarPrestamosAprobadosPorCliente(Long clienteId);


    Date obtenerFechaUltimoPagoPorPrestamoId(Long prestamoId);


    // Método para obtener un resumen de los préstamos por cliente
    List<ResumenPrestamoDTO> obtenerResumenPrestamosPorClienteId(Long clienteId);

}
