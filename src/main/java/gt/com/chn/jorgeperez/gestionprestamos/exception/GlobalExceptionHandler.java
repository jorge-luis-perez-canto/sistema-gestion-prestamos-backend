package gt.com.chn.jorgeperez.gestionprestamos.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Clase para manejar excepciones globales en la aplicación.
 * Captura varias excepciones comunes y personalizadas, proporcionando respuestas de error coherentes y localizadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Logger para registrar información y errores en el sistema.
     */
    //private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Fuente de mensajes para internacionalización y localización de mensajes de error.
     */
    private final MessageSource messageSource;

    /**
     * Constructor que inicializa la clase GlobalExceptionHandler con la MessageSource proporcionada.
     *
     * @param messageSource La fuente de mensajes para la internacionalización y localización de mensajes de error.
     */
    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(ServiceException ex) {
        logError(ex);
        String userMessage = messageSource.getMessage("service.exception", null, LocaleContextHolder.getLocale());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, userMessage, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Maneja las excepciones de clientes no encontrados.
     */
    @ExceptionHandler(CustomExceptions.ClienteNotFoundException.class)
    public ResponseEntity<Object> handleClienteNotFoundException(CustomExceptions.ClienteNotFoundException ex) {
        logError(ex);
        String message = messageSource.getMessage("cliente.not.found", null, LocaleContextHolder.getLocale());
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, message, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(CustomExceptions.PrestamosPendientesNotFoundException.class)
    public ResponseEntity<Object> handlePrestamosPendientesNotFoundException(CustomExceptions.PrestamosPendientesNotFoundException ex) {
        logError(ex);
        String message = messageSource.getMessage("cliente.sin.prestamos.pendientes", null, LocaleContextHolder.getLocale());
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, message, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Maneja las excepciones de operaciones de base de datos.
     */
    @ExceptionHandler(CustomExceptions.DatabaseOperationException.class)
    public ResponseEntity<Object> handleDatabaseOperationException(CustomExceptions.DatabaseOperationException ex) {
        logError(ex);
        String message = messageSource.getMessage("error.operacion.bd", null, LocaleContextHolder.getLocale());
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message, ex.getMessage());
        return buildResponseEntity(apiError);
    }


    @ExceptionHandler(CustomExceptions.PrestamoNotFoundException.class)
    public ResponseEntity<Object> handlePrestamoNotFoundException(CustomExceptions.PrestamoNotFoundException ex, Locale locale) {
        log.error("Error: {}", ex.getMessage(), ex);
        // Ahora este método debe estar disponible
        String message = messageSource.getMessage("prestamo.not.found", new Object[]{ex.getPrestamoId()}, locale);
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, message, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(CustomExceptions.PrestamoYaAprobadoException.class)
    public ResponseEntity<Object> handlePrestamoYaAprobadoException(CustomExceptions.PrestamoYaAprobadoException ex) {
        log.error("Error: {}", ex.getMessage(), ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), "El préstamo ya está aprobado.");
        return buildResponseEntity(apiError);
    }


    /**
     * Maneja las excepciones de límite de crédito excedido.
     */
    @ExceptionHandler(CustomExceptions.CreditLimitExceededException.class)
    public ResponseEntity<Object> handleCreditLimitExceeded(CustomExceptions.CreditLimitExceededException ex) {
        logError(ex);
        String message = messageSource.getMessage("limite.credito.excedido", null, LocaleContextHolder.getLocale());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, message, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Maneja las excepciones de solicitudes de préstamo duplicadas.
     */
    @ExceptionHandler(CustomExceptions.DuplicateLoanRequestException.class)
    public ResponseEntity<Object> handleDuplicateLoanRequest(CustomExceptions.DuplicateLoanRequestException ex) {
        logError(ex);
        String message = messageSource.getMessage("solicitud.prestamo.duplicada", null, LocaleContextHolder.getLocale());
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, message, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Maneja las excepciones relacionadas con los pagos.
     */
    @ExceptionHandler(CustomExceptions.PagoException.class)
    public ResponseEntity<Object> handlePagoException(CustomExceptions.PagoException ex) {
        logError(ex);
        String message = messageSource.getMessage("error.pago", null, LocaleContextHolder.getLocale());
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(CustomExceptions.ClienteEliminadoExitosamenteException.class)
    public ResponseEntity<Object> handleClienteEliminadoExitosamente(CustomExceptions.ClienteEliminadoExitosamenteException ex) {
        ApiError apiError = new ApiError(HttpStatus.OK, ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(CustomExceptions.ClienteConDeudasException.class)
    public ResponseEntity<Object> handleClienteConDeudas(CustomExceptions.ClienteConDeudasException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


    /**
     * Maneja las excepciones generales de acceso a datos.
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex) {
        logError(ex);
        String message = messageSource.getMessage("error.acceso.bd", null, LocaleContextHolder.getLocale());
        ApiError apiError = new ApiError(HttpStatus.SERVICE_UNAVAILABLE, message, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Maneja las excepciones de violación de integridad de datos.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        logError(ex);
        // Mensaje general para el usuario sin especificar la entidad
        String userMessage = messageSource.getMessage("data.integrity.violation.general", null, LocaleContextHolder.getLocale());
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, userMessage, "Se encontró un problema con la integridad de los datos.");

        // Intentar extraer información útil del mensaje de error detallado
        String detailedMessage = Optional.ofNullable(ex.getRootCause()).map(Throwable::getMessage).orElse(ex.getMessage());

        // Ejemplo de cómo podrías intentar encontrar información útil sin especificar la entidad
        Pattern pattern = Pattern.compile("Column '(.*?)' cannot be null");
        Matcher matcher = pattern.matcher(detailedMessage);
        if (matcher.find()) {
            String fieldName = matcher.group(1);
            String errorMessage = "El campo '" + fieldName + "' no puede ser nulo";
            // En este caso, no especificamos la entidad ya que nuestro enfoque es genérico
            apiError.addValidationError("EntidadDesconocida", fieldName, null, errorMessage);
        }

        // Ajustar el mensaje de debugMessage para que sea más breve y menos técnico
        apiError.setDebugMessage("Error de integridad de datos relacionado con restricciones de base de datos");

        // Asegúrate de que el mensaje de error sea útil pero no revele detalles internos potencialmente sensibles
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }


    /**
     * Maneja las excepciones de entidades no encontradas.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        logError(ex);
        String message = messageSource.getMessage("entidad.no.encontrada", null, LocaleContextHolder.getLocale());
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, message, ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> handleIllegalStateException(IllegalStateException ex) {
        logError(ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Operación no permitida", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    /**
     * Maneja las excepciones de argumentos de método no válidos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        logError(ex);
        // String message = messageSource.getMessage("error.validacion", null, LocaleContextHolder.getLocale());
        String userMessage = messageSource.getMessage("validacion.campo.error", null, LocaleContextHolder.getLocale());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, userMessage, "Validation failed");

        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError fieldError) {
                String objectName = fieldError.getObjectName();
                apiError.addValidationError(objectName.substring(objectName.lastIndexOf(".") + 1), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
            } else if (error instanceof ObjectError) {
                ObjectError objectError = error;
                String objectName = objectError.getObjectName();
                apiError.addValidationError(objectName.substring(objectName.lastIndexOf(".") + 1), objectError.getDefaultMessage());
            }
        });

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        logError(ex);
        String userMessage = messageSource.getMessage("validacion.metodo.error", null, LocaleContextHolder.getLocale());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, userMessage, "Validation failed");

        ex.getConstraintViolations().forEach(violation -> {
            String path = violation.getPropertyPath().toString();
            String message = violation.getMessage();

            boolean isFieldError = path.contains(".");
            if (isFieldError) {
                String fieldName = path.substring(path.lastIndexOf(".") + 1);
                String objectName = violation.getRootBeanClass().getSimpleName();
                apiError.addValidationError(objectName, fieldName, violation.getInvalidValue(), message);
            } else {
                String objectName = violation.getRootBeanClass().getSimpleName();
                apiError.addValidationError(objectName, message);
            }
        });

        return buildResponseEntity(apiError);
    }


    /**
     * Maneja todas las demás excepciones no capturadas específicamente por otros manejadores de excepciones.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex) {
        logError(ex);
        String message = messageSource.getMessage("error.inesperado", null, LocaleContextHolder.getLocale());
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message, "Se ha producido un error inesperado");
        apiError.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Construye la entidad de respuesta basada en el objeto ApiError.
     */
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        log.info(apiError.toString());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    /**
     * Registra errores en el log del servidor para facilitar el diagnóstico.
     */
    private void logError(Exception ex) {
        log.error("Excepción manejada: {}", ex.getMessage(), ex);
    }
}
