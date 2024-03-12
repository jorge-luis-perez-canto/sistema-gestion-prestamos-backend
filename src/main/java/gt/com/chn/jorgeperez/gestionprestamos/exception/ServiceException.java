package gt.com.chn.jorgeperez.gestionprestamos.exception;

/**
 * Excepción lanzada cuando ocurre un error genérico relacionado con el servicio.
 * Esta excepción extiende RuntimeException y se utiliza para representar cualquier situación inesperada
 * o error relacionado con los servicios en la aplicación. Puede contener un mensaje descriptivo
 * para identificar la causa del error, así como una causa subyacente.
 */
public class ServiceException extends RuntimeException {

    /**
     * Constructor de la excepción con un mensaje descriptivo.
     *
     * @param message El mensaje de error que describe la causa del error del servicio.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructor de la excepción con un mensaje descriptivo y una causa.
     *
     * @param message El mensaje de error que describe la causa del error del servicio.
     * @param cause   La causa subyacente de esta excepción.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
