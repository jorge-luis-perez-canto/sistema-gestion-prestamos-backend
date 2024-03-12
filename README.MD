# Sistema de Gestión de Préstamos - Jorge Pérez

Este proyecto consiste en una API REST desarrollada para gestionar préstamos bancarios. Implementada con Spring Boot, la API ofrece una serie de servicios RESTful que permiten realizar operaciones tales como la solicitud de préstamos, administración de pagos y consulta de saldos, entre otras. Está diseñada para ser consumida por aplicaciones frontend o clientes que requieran interactuar con el sistema bancario de forma programática.

## Características

-   **Gestión de Clientes:** Registro, actualización y eliminación de clientes.

#### Gestión de Clientes:
-   Registro de nuevos clientes con detalles como nombre, apellido, identificación y contacto.
-   Listado completo de todos los clientes registrados en el sistema.
-   Recuperación de información detallada de un cliente específico a través de su ID.
-   Actualización de datos de clientes existentes, como su información personal y de contacto.
-   Eliminación de un cliente del sistema por su ID.

#### Solicitudes de Préstamo:
-   Permite a los clientes solicitar nuevos préstamos bancarios especificando detalles como el monto, plazo y fecha de solicitud.
-   Consulta de solicitudes de préstamos pendientes por cliente, incluyendo el estado actual de cada solicitud.
-   Aprobación de solicitudes de préstamo, cambiando su estado a aprobado y registrando la decisión en el sistema.
-   Rechazo de solicitudes de préstamo con motivo, registrando la razón del rechazo y actualizando el estado de la solicitud.

#### Gestión de Préstamos Aprobados y Pagos:
-   Listado de todos los préstamos aprobados para un cliente con detalles como el monto total prestado y el saldo pendiente.
-   Registro de pagos de préstamos con información del monto y fecha de pago, así como la actualización del saldo.
-   Consulta de saldos pendientes de préstamos específicos, proporcionando visibilidad sobre las obligaciones financieras restantes.
-   Actualización del estado del préstamo para marcarlo como finalizado una vez que se paga la deuda en su totalidad.

## Tecnologías Utilizadas

-   **Tipo de Proyecto:** Maven Project
-   **Lenguaje de Programación:** Java
-   **Versión de Spring Boot:** 3.2.3
-   **Empaquetado:** JAR
-   **Versión de Java:** 17

### Backend
-   **Spring Boot:** Framework para el desarrollo de aplicaciones y microservicios con Java.
-   **Spring Data JPA:** Para persistencia de datos en SQL stores utilizando Java Persistence API.
-   **MySQL:** Sistema de gestión de bases de datos relacional.
-   **Spring Data REST:** Facilita la creación de interfaces RESTful basadas en repositorios Spring Data.

### Herramientas de Desarrollo

-   **IntelliJ IDEA**: Este proyecto fue desarrollado utilizando IntelliJ IDEA, un entorno de desarrollo integrado (IDE) para el desarrollo de software en Java.

## Configuración y Ejecución

### Backend

Para ejecutar el backend, necesitarás Maven y Java 17. Ubicado en el directorio `backend/`, ejecuta:


`mvn spring-boot:run`


# Pruebas de los endpoints de la API

Para probar los endpoints de la API en Postman, necesitarás configurar cada solicitud con los detalles de método HTTP, URL, cabeceras y el cuerpo de la petición si es necesario. A continuación, te proporciono un ejemplo de cómo podrías configurar las llamadas a la API para las operaciones básicas:

## Gestión de Clientes:

### Agregar un nuevo cliente

-   Método: POST
-   URL: `http://localhost:8080/clientes`
-   Headers:
    -   Content-Type: application/json

```json
    {
      "nombre": "Nombre del Cliente",
      "apellido": "Apellido del Cliente",
      "numeroIdentificacion": "123456789",
      "fechaNacimiento": "1990-01-01",
      "direccion": "Direccion del cliente",
      "correoElectronico": "cliente@example.com",
      "telefono": "12345678"
    }
```
### Listar todos los clientes

-   Método: GET
-   URL: `http://localhost:8080/clientes`

### Obtener un cliente por ID

-   Método: GET
-   URL: `http://localhost:8080/clientes/{id}`

Reemplaza `{id}` con el ID real del cliente.

### Actualizar un cliente

-   Método: PUT
-   URL: `http://localhost:8080/clientes/{id}`
-   Headers:
    -   Content-Type: application/json
-   Body (raw JSON):

```json
    {
      "nombre": "Nuevo Nombre",
      "apellido": "Nuevo Apellido",
      "numeroIdentificacion": "1234567890",
      "fechaNacimiento": "1990-01-01",
      "direccion": "Nueva Direccion",
      "correoElectronico": "nuevocliente@example.com",
      "telefono": "87654321"
    }
```
Reemplaza `{id}` con el ID real del cliente.

### Eliminar un cliente

-   Método: DELETE
-   URL: `http://localhost:8080/clientes/{id}`

Reemplaza `{id}` con el ID real del cliente.


## Solicitud de Préstamos:

### 1. Solicitar un nuevo préstamo bancario

-   **Método HTTP:** POST
-   **URL:** http://localhost:8080/api/prestamos
-   **Cabeceras:** Content-Type: application/json
-   **Cuerpo de la petición (raw JSON):**


```json
    {
      "clienteId": 1,
      "montoSolicitado": 10000,
      "plazo": 12,
      "estado": "En Proceso",
      "fechaSolicitud": "2023-01-01",
      "tasaInteres": 5.5
    }
```

### 2. Consultar solicitudes de préstamos pendientes por cliente

-   **Método HTTP:** GET
-   **URL:** http://localhost:8080/api/prestamos/pendientes/cliente/{id}
-   **Cabeceras:** No se requieren cabeceras específicas para esta solicitud.
    Reemplaza `{id}` con el ID real del cliente.

### 3. Aprobar una solicitud de préstamo

-   **Método HTTP:** POST
-   **URL:** http://localhost:8080/api/prestamos/aprobar/{id}
-   **Cabeceras:** Content-Type: application/json
-   **Cuerpo de la petición:** Este endpoint no requiere un cuerpo de Reemplaza `{id}` con el ID real del cliente.

### 4. Rechazar una solicitud de préstamo

-   **Método HTTP:** POST
-   **URL:** http://localhost:8080/api/prestamos/rechazar/{id}
-   **Cabeceras:** Content-Type: application/json
-   **Cuerpo de la petición (raw JSON):**
    `"El cliente no cumple con los requisitos mínimos de crédito."`

Reemplaza `{id}` con el ID real del cliente.

Para este caso, asegúrate de que el cuerpo sea un texto plano o ajusta el endpoint para recibir un objeto JSON si es necesario.

### 5. Listar todos los préstamos de un cliente

-   **Método HTTP:** GET
-   **URL:** http://localhost:8080/api/prestamos/cliente/{id}
-   **Cabeceras:** No se requieren cabeceras específicas para esta solicitud.
- Reemplaza `{id}` con el ID real del cliente.


## Gestión de Préstamos Aprobados y Pagos:

### 1. Listar préstamos aprobados por cliente

-   **Método HTTP:** GET
-   **URL:** http://localhost:8080/api/pagos/prestamos-aprobados/{clienteId}
-   **Cabeceras:** Content-Type: application/json
-   **Cuerpo de la petición:** No aplica.
-   **Variables de ruta:** `{clienteId}` con el ID del cliente para el cual se quieren listar los préstamos aprobados.

### 2. Registrar pago

-   **Método HTTP:** POST
-   **URL:** http://localhost:8080/api/pagos/registrar
-   **Cabeceras:** Content-Type: application/json
-   **Cuerpo de la petición:** JSON con los detalles del pago. Ejemplo:


    ```json
    {
      "prestamoId": 1,
      "montoPago": 100.0,
      "fechaPago": "2024-01-01",
      "saldoAnterior": 500.0,
      "saldoActual": 400.0
    }
    ```

### 3. Consultar saldo pendiente

-   **Método HTTP:** GET
-   **URL:** http://localhost:8080/api/pagos/saldo-pendiente/{prestamoId}
-   **Cabeceras:** Content-Type: application/json
-   **Cuerpo de la petición:** No aplica.
-   **Variables de ruta:** `{prestamoId}` con el ID del préstamo para el cual se quiere consultar el saldo pendiente.

### 4. Actualizar estado de préstamo al finiquitar deuda

-   **Método HTTP:** PUT
-   **URL:** http://localhost:8080/api/pagos/finalizar-prestamo/{prestamoId}
-   **Cabeceras:** Content-Type: application/json
-   **Cuerpo de la petición:** No aplica.
-   **Variables de ruta:** `{prestamoId}` con el ID del préstamo que se desea finalizar.



## Documentación de Manejo de Errores en la API

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



## Contenerización con Docker

Este proyecto está preparado para ser contenerizado y ejecutado en contenedores Docker, lo que facilita la implementación y el despliegue independientemente del entorno local.

### Requisitos

- Docker instalado en tu máquina. [Instrucciones de instalación](https://docs.docker.com/get-docker/)

### Construir y Ejecutar el Contenedor del Backend

Para construir la imagen del contenedor de tu aplicación backend y ejecutarla, sigue estos pasos:

1. **Construir la Imagen Docker**

   Navega al directorio `backend/` donde se encuentra el `Dockerfile` y ejecuta el siguiente comando en tu terminal:

   ```sh
   docker build -t sistema-gestion-prestamos-backend .

   
Esto construirá la imagen de Docker para el backend basándose en las instrucciones definidas en el Dockerfile.

### Ejecutar el Contenedor

Una vez construida la imagen, puedes ejecutar un contenedor basado en esa imagen con el siguiente comando:
   ```sh
docker run -p 8080:8080 sistema-gestion-prestamos-backend
   ```
Este comando inicia un contenedor que ejecuta tu aplicación backend, mapeando el puerto 8080 del contenedor al puerto 8080 de tu máquina local.

Verificar la Aplicación
Una vez que el contenedor está en ejecución, la API del backend estará accesible en http://localhost:8080. Puedes usar herramientas como Postman para probar los endpoints de la API como se describe en las secciones anteriores de este documento.

Recuerda revisar y modificar los comandos según las necesidades específicas de tu proyecto, como el nombre de la imagen Docker o los puertos utilizados.


Asegúrate de adaptar los nombres de las imágenes y los puertos según lo que has configurado en tu proyecto. Este ejemplo asume que el `Dockerfile` está correctamente configurado en el directorio `backend/` y que la aplicación escucha en el puerto 8080.




# Despliegue con Docker
Este proyecto incluye configuración preparada para Docker, lo que permite un despliegue y una ejecución consistentes en cualquier entorno que soporte Docker.

## Requisitos para Docker
- Docker y Docker Compose instalados en tu máquina. 
- Dockerfile: Instrucciones para construir la imagen Docker del backend.
- docker-compose.yml: Configuración para desplegar tanto la aplicación backend como la base de datos MySQL en contenedores Docker.
- initdb: Directorio que contiene scripts SQL que se ejecutarán automáticamente para inicializar la base de datos cuando el contenedor MySQL se inicie por primera vez.

## Uso de docker-compose
Para desplegar la aplicación y la base de datos con Docker Compose, sigue los siguientes pasos:

### Inicio del Servicio
En el directorio donde se encuentra el docker-compose.yml, ejecuta el siguiente comando:
```sh
docker-compose up -d --build
```
Este comando construye la imagen Docker para el backend y la base de datos si es necesario, y luego inicia los contenedores en modo "detached" (en segundo plano).

### Verificación de Contenedores
Verifica que los contenedores estén corriendo utilizando:
```sh
docker ps
```

### Acceso a la Aplicación
Una vez que los contenedores están ejecutándose, la API estará accesible en http://localhost:8080. Utiliza herramientas como Postman para interactuar con la API como se describe en las secciones anteriores de este documento.

### Detención y Limpieza
Para detener y eliminar los contenedores, redes y la imagen construida por Docker Compose, ejecuta:
```sh
docker-compose down
```

Si deseas también eliminar los datos persistidos en la base de datos, agrega la opción -v:
```sh
docker-compose down -v
```

### Reiniciar los Servicios
Si necesitas reiniciar los servicios, puedes hacerlo con:

```sh
docker-compose restart
```

### Logs de los Servicios
Para ver los logs de tus servicios ejecutándose en contenedores, usa el comando:
```sh
docker-compose logs
```

Con estos pasos y herramientas, el despliegue de la aplicación es sencillo y no depende de la configuración del entorno local, facilitando así el proceso de desarrollo, pruebas y producción.



## Autor

**Jorge Luis Pérez Canto**
