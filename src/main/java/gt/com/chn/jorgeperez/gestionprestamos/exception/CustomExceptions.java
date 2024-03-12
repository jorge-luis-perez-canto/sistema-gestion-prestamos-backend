package gt.com.chn.jorgeperez.gestionprestamos.exception;

import org.springframework.dao.DataAccessException;

/**
 * Contiene las definiciones de las excepciones personalizadas utilizadas en la aplicación.
 * Estas excepciones ayudan a manejar situaciones específicas del negocio y proporcionan mensajes
 * claros y descriptivos que pueden ser utilizados para informar al usuario o para la depuración.
 */
public class CustomExceptions {

    /**
     * Excepción para manejar errores específicos de autenticación, como credenciales incorrectas
     * o intentos de acceso no autorizado.
     */
    public static class AuthenticationException extends ServiceException {
        public AuthenticationException(String message) {
            super("Error de autenticación: " + message);
        }
    }

    /**
     * Excepción general para errores de negocio, utilizada para representar condiciones de error
     * específicas del dominio de la aplicación.
     */
    public static class BusinessException extends ServiceException {
        public BusinessException(String message) {
            super(message);
        }

        public BusinessException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Excepción lanzada cuando se detecta que un cliente tiene deudas pendientes, impidiendo algunas operaciones.
     */
    public static class ClienteConDeudasException extends RuntimeException {
        public ClienteConDeudasException(String message) {
            super(message);
        }
    }

    /**
     * Excepción indicativa de la eliminación exitosa de un cliente del sistema.
     */
    public static class ClienteEliminadoExitosamenteException extends RuntimeException {
        public ClienteEliminadoExitosamenteException(String message) {
            super(message);
        }
    }

    /**
     * Excepción lanzada cuando no se encuentra un cliente esperado en la base de datos.
     */
    public static class ClienteNotFoundException extends ServiceException {
        public ClienteNotFoundException(String message) {
            super(message);
        }
    }

    /**
     * Excepción lanzada cuando un cliente intenta exceder su límite de crédito.
     */
    public static class CreditLimitExceededException extends ServiceException {
        public CreditLimitExceededException(String message) {
            super("El límite de crédito ha sido excedido: " + message);
        }
    }

    /**
     * Excepción lanzada en caso de un error al acceder a la base de datos, incluyendo problemas de conectividad
     * o fallos en las operaciones de consulta.
     */
    public static class DatabaseAccessException extends ServiceException {
        public DatabaseAccessException(String message, Throwable cause) {
            super("Error al acceder a la base de datos: " + message, cause);
        }
    }

    /**
     * Excepción utilizada para errores específicos de operaciones en la base de datos, como violaciones
     * de restricciones de integridad.
     */
    public static class DatabaseOperationException extends ServiceException {
        public DatabaseOperationException(String message, Throwable cause) {
            super("Error de operación en la base de datos: " + message, cause);
        }
    }

    /**
     * Excepción que indica una solicitud de préstamo duplicada, ayudando a evitar la creación de registros redundantes.
     */
    public static class DuplicateLoanRequestException extends ServiceException {
        public DuplicateLoanRequestException(String message) {
            super("Solicitud de préstamo duplicada: " + message);
        }
    }

    /**
     * Excepción para errores relacionados con los pagos, como fallos en el procesamiento o validación de pagos.
     */
    public static class PagoException extends ServiceException {
        public PagoException(String message) {
            super("Error en el pago: " + message);
        }

        public PagoException(String message, Throwable cause) {
            super("Error en el pago: " + message, cause);
        }
    }

    /**
     * Excepción específica para errores durante el procesamiento de pagos, como problemas al registrar
     * o confirmar pagos en la base de datos.
     */
    public static class PagoProcessingException extends ServiceException {
        public PagoProcessingException(String message, DataAccessException e) {
            super(message);
        }
    }

    /**
     * Excepción lanzada cuando no se encuentra un préstamo esperado en la base de datos.
     */
    public static class PrestamoNotFoundException extends RuntimeException {
        private Long prestamoId;

        public PrestamoNotFoundException(Long prestamoId) {
            super(String.format("Préstamo con ID: %s no encontrado.", prestamoId));
            this.prestamoId = prestamoId;
        }

        public Long getPrestamoId() {
            return prestamoId;
        }
    }

    /**
     * Excepción lanzada para manejar el intento de aprobación de un préstamo que ya ha sido aprobado, evitando
     * cambios de estado indebidos.
     */
    public static class PrestamoYaAprobadoException extends RuntimeException {
        private final Long prestamoId;

        public PrestamoYaAprobadoException(Long prestamoId) {
            super(String.format("El préstamo con ID: %s ya está aprobado.", prestamoId));
            this.prestamoId = prestamoId;
        }

        public Long getPrestamoId() {
            return prestamoId;
        }
    }

    /**
     * Excepción lanzada cuando no se encuentran préstamos pendientes para un cliente que se esperaba tuviera.
     * Indica que el cliente no tiene solicitudes de préstamo en proceso o pendientes de aprobación.
     */
    public static class PrestamosPendientesNotFoundException extends RuntimeException {
        public PrestamosPendientesNotFoundException(String mensaje) {
            super(mensaje);
        }
    }

    /**
     * Excepción lanzada cuando se intenta realizar una operación no permitida en el contexto actual,
     * como modificar un préstamo ya finalizado o pagar un préstamo no existente.
     */
    public static class InvalidOperationException extends ServiceException {
        public InvalidOperationException(String message) {
            super(message);
        }

        public InvalidOperationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Excepción lanzada cuando se detecta un intento de acceso o manipulación de recursos sin las credenciales
     * o permisos adecuados. Utilizada para manejar casos de acceso no autorizado o violaciones de seguridad.
     */
    public static class UnauthorizedAccessException extends ServiceException {
        public UnauthorizedAccessException(String message) {
            super("Acceso no autorizado: " + message);
        }

        public UnauthorizedAccessException(String message, Throwable cause) {
            super("Acceso no autorizado: " + message, cause);
        }
    }

    /**
     * Excepción lanzada cuando se intenta realizar una operación en un recurso que ya se encuentra en el estado deseado,
     * lo que indica que la operación es innecesaria o redundante.
     */
    public static class ResourceAlreadyInDesiredStateException extends ServiceException {
        public ResourceAlreadyInDesiredStateException(String message) {
            super(message);
        }
    }

    /**
     * Excepción lanzada cuando se produce un error general en la aplicación que no se ajusta a ninguna de las categorías
     * específicas de excepciones definidas. Utilizada como una excepción genérica para errores inesperados.
     */
    public static class GeneralApplicationException extends ServiceException {
        public GeneralApplicationException(String message) {
            super("Error general de la aplicación: " + message);
        }

        public GeneralApplicationException(String message, Throwable cause) {
            super("Error general de la aplicación: " + message, cause);
        }
    }

    /**
     * Excepción lanzada cuando se intenta realizar una operación sobre un recurso que requiere un estado específico
     * que actualmente no cumple, como intentar pagar un préstamo no aprobado.
     */
    public static class InvalidResourceStateException extends ServiceException {
        public InvalidResourceStateException(String message) {
            super("Estado inválido del recurso: " + message);
        }
    }

    /**
     * Excepción lanzada cuando se solicita un recurso, como un préstamo o cliente, que no existe en el sistema,
     * indicando posiblemente un error de identificador o una eliminación previa del recurso.
     */
    public static class ResourceNotFoundException extends ServiceException {
        public ResourceNotFoundException(String message) {
            super("Recurso no encontrado: " + message);
        }
    }

    /**
     * Excepción utilizada para representar violaciones de las reglas de negocio o condiciones de validación
     * específicas del dominio de la aplicación que no se han cumplido.
     */
    public static class BusinessRuleViolationException extends ServiceException {
        public BusinessRuleViolationException(String message) {
            super("Violación de regla de negocio: " + message);
        }
    }
}