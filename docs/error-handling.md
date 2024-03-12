
### Documentación de Manejo de Errores en la API

Nuestra API utiliza una estructura de respuesta de error consistente para todos los endpoints.   
A continuación, se describen los códigos de estado HTTP que pueden devolverse y la estructura de los mensajes de error.

#### Códigos de Estado HTTP:
- **400 Bad Request:** La solicitud no pudo ser entendida por el servidor debido a una sintaxis malformada.
- **401 Unauthorized:** La solicitud requiere autenticación y no ha sido proporcionada.
- **403 Forbidden:** El servidor entendió la solicitud, pero se niega a autorizarla.
- **404 Not Found:** El recurso solicitado no se pudo encontrar pero podría estar disponible en el futuro.
- **409 Conflict:** La solicitud no pudo ser completada debido a un conflicto con el estado actual del recurso.
- **500 Internal Server Error**: Ha ocurrido un error interno en el servidor y no pudo completar la solicitud.
- **503 Service Unavailable:** El servidor no está listo para manejar la solicitud, generalmente por mantenimiento o sobrecarga.

#### Estructura de Mensaje de Error:
La respuesta de error está en formato JSON y sigue la siguiente estructura:

```json
 {
   "status": "HTTP status code",    
   "timestamp": "timestamp",        
   "message": "message",            
   "debugMessage": "debug message", 
   "subErrors": [                   
     {
       "object": "object name",     
       "field": "field name",       
       "rejectedValue": "value",    
       "message": "error message"   
     }
   ]
 }
```

- **status:** Código de estado HTTP de la respuesta.
- **timestamp:** Marca de tiempo cuando ocurre el error.
- **message:** Mensaje descriptivo del error para el usuario final.
- **debugMessage:** Mensaje de depuración con detalles adicionales, útil durante el desarrollo (opcional).
- **subErrors:** Lista de errores específicos relacionados con la validación de campos (opcional).
- **object:** Nombre del objeto relacionado con el error.
- **field:** Campo específico del objeto que generó el error.
- **rejectedValue:** Valor rechazado que causó el error.
- **message:** Mensaje descriptivo del error específico.

###
#### Ejemplo de Mensaje de Error:
En caso de un error de validación en una solicitud de creación de cliente, la respuesta podría ser:
```json 
{
   "status": 400,
   "timestamp": "2023-03-10T14:15:30",
   "message": "Error de validación",
   "debugMessage": "La solicitud contiene campos inválidos",
   "subErrors": [
     {
       "object": "cliente",
       "field": "correoElectronico",
       "rejectedValue": "correo no válido",
       "message": "El correo electrónico no tiene un formato válido"
     }
   ]
 }
```

Este sistema de manejo de errores está diseñado para proporcionar claridad y ayudar en la rápida identificación y resolución de problemas, mejorando así la experiencia de desarrollo y consumo de nuestra API.

### Excepciones Capturadas por GlobalExceptionHandler y excepciones personalizadas:

**ClienteNotFoundException:** Se devuelve cuando se solicita un cliente que no existe en la base de datos.

HttpStatus: 404 Not Found  
Ejemplo de respuesta:
```json
{
"status": 500,
"timestamp": "2023-03-10T14:15:30",
"message": "Error de operación en la base de datos",
"debugMessage": "Violación de clave única al insertar en la tabla clientes"
}
```

**CreditLimitExceededException:** Lanzada cuando un cliente excede su límite de crédito.

HttpStatus: 400 Bad Request  
Ejemplo de respuesta:

```json
{
  "status": 400,
  "timestamp": "2023-03-10T14:15:30",
  "message": "Límite de crédito excedido",
  "debugMessage": "El cliente con ID: 123 ha excedido el límite de crédito permitido."
}
``` 

**DuplicateLoanRequestException:** Ocurre cuando se detecta una solicitud de préstamo duplicada.

HttpStatus: 409 Conflict  
Ejemplo de respuesta:
```json
{
  "status": 409,
  "timestamp": "2023-03-10T14:15:30",
  "message": "Solicitud de préstamo duplicada",
  "debugMessage": "Ya existe una solicitud de préstamo idéntica en el sistema."
}
```