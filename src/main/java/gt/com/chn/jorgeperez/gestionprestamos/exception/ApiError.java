package gt.com.chn.jorgeperez.gestionprestamos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una estructura de error para respuestas de la API.
 * Contiene información sobre el estado HTTP, marca de tiempo, mensaje de error,
 * mensaje de depuración y suberrores asociados.
 */

public class ApiError {

    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;


    /**
     * Constructor por defecto que inicializa la marca de tiempo.
     */
    public ApiError() {
        timestamp = LocalDateTime.now();
    }

    /**
     * Constructor que establece el estado HTTP y utiliza el constructor por defecto para inicializar la marca de tiempo.
     *
     * @param status Estado HTTP asociado al error.
     */
    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    /**
     * Constructor que establece el estado HTTP y mensaje de error,
     * utilizando el constructor por defecto para inicializar la marca de tiempo.
     *
     * @param status  Estado HTTP asociado al error.
     * @param message Mensaje descriptivo del error.
     */
    public ApiError(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    /**
     * Constructor que establece el estado HTTP, mensaje de error y mensaje de depuración,
     * utilizando el constructor por defecto para inicializar la marca de tiempo.
     *
     * @param status       Estado HTTP asociado al error.
     * @param message      Mensaje descriptivo del error.
     * @param debugMessage Mensaje de depuración del error (opcional).
     */
    public ApiError(HttpStatus status, String message, String debugMessage) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = debugMessage;
    }

    /**
     * Agrega un suberror a la lista de suberrores.
     *
     * @param subError Suberror a agregar.
     */
    protected void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    /**
     * Agrega un error de validación a la lista de suberrores.
     *
     * @param object        Nombre del objeto asociado al error.
     * @param field         Nombre del campo asociado al error.
     * @param rejectedValue Valor rechazado que causó el error.
     * @param message       Mensaje descriptivo del error.
     */
    public void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ApiValidationError(object, field, rejectedValue, message));
    }

    /**
     * Agrega un error de validación a la lista de suberrores.
     *
     * @param object  Nombre del objeto asociado al error.
     * @param message Mensaje descriptivo del error.
     */
    public void addValidationError(String object, String message) {
        addSubError(new ApiValidationError(object, message));
    }

    /**
     * Agrega una lista de errores de validación a la lista de suberrores.
     *
     * @param fieldErrors Lista de errores de validación.
     */
    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    /**
     * Agrega un error de validación a la lista de suberrores.
     *
     * @param fieldError Error de validación a agregar.
     */
    private void addValidationError(FieldError fieldError) {
        this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
    }

    /**
     * Agrega una lista de errores globales a la lista de suberrores.
     *
     * @param globalErrors Lista de errores globales.
     */
    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Agrega un error global a la lista de suberrores.
     *
     * @param objectError Error global a agregar.
     */
    private void addValidationError(ObjectError objectError) {
        this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public List<ApiSubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<ApiSubError> subErrors) {
        this.subErrors = subErrors;
    }

    @Override
    public String toString() {
        return "ApiError{" + "status=" + status + ", timestamp=" + timestamp + ", message='" + message + '\'' + ", debugMessage='" + debugMessage + '\'' + ", subErrors=" + subErrors + '}';
    }
}
