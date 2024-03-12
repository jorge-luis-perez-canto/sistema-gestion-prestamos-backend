package gt.com.chn.jorgeperez.gestionprestamos.exception;

/**
 * Clase que representa un error de validación en la estructura de error de la API.
 * Esta clase hereda de ApiSubError y proporciona atributos adicionales para describir los errores de validación
 * asociados con campos específicos de objetos en las solicitudes de la API.
 * Puede contener información sobre el objeto afectado, el campo, el valor rechazado y un mensaje de error descriptivo.
 */

public class ApiValidationError extends ApiSubError {
    private String object; // Nombre del objeto relacionado con el error
    private String field;  // Campo específico del objeto que generó el error
    private Object rejectedValue; // Valor rechazado que causó el error
    private String message; // Mensaje descriptivo del error

    /**
     * Constructor para errores de validación con un mensaje de error general.
     *
     * @param object  El nombre del objeto relacionado con el error.
     * @param message El mensaje de error descriptivo.
     */
    public ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    /**
     * Constructor para errores de validación con detalles específicos del campo y el valor rechazado.
     *
     * @param object        El nombre del objeto relacionado con el error.
     * @param field         El campo específico del objeto que generó el error.
     * @param rejectedValue El valor rechazado que causó el error.
     * @param message       El mensaje de error descriptivo.
     */
    public ApiValidationError(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
