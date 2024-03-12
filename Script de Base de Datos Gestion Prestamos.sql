CREATE DATABASE IF NOT EXISTS gestion_prestamos;
USE gestion_prestamos;

CREATE TABLE IF NOT EXISTS cliente (
    cliente_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    numero_identificacion VARCHAR(20) NOT NULL UNIQUE,
    fecha_nacimiento DATE NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(50) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS prestamo (
    prestamo_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    monto_solicitado DECIMAL(18, 2) NOT NULL,
    plazo INT NOT NULL,
    estado VARCHAR(20) NOT NULL,
    fecha_solicitud DATE NOT NULL,
    tasa_interes DECIMAL(5, 2) NOT NULL,
    saldo_actual DECIMAL(18, 2),
    fecha_finalizacion DATE,
    detalles VARCHAR(500),
    FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS pago (
    pago_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    prestamo_id BIGINT NOT NULL,
    monto_pago DECIMAL(18, 2) NOT NULL,
    fecha_pago DATE NOT NULL,
    saldo_anterior DECIMAL(18, 2) NOT NULL,
    saldo_actual DECIMAL(18, 2) NOT NULL,
    FOREIGN KEY (prestamo_id) REFERENCES prestamo(prestamo_id)
) ENGINE=InnoDB;
