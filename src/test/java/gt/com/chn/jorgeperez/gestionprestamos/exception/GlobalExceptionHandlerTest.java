package gt.com.chn.jorgeperez.gestionprestamos.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Test Message");
    }

    @Test
    void handleClienteNotFoundException() {
        CustomExceptions.ClienteNotFoundException ex = new CustomExceptions.ClienteNotFoundException("Not found");
        ResponseEntity<Object> response = exceptionHandler.handleClienteNotFoundException(ex);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ApiError apiError = (ApiError) response.getBody();
        assertNotNull(apiError);
        assertEquals("Test Message", apiError.getMessage());
    }

    // Aquí puedes añadir más pruebas para otros métodos de manejo de excepciones
}
