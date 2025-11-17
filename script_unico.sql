-- ==========================================================
--  SCRIPT ÚNICO - TPI Programación 2
--  Empresa → Domicilio Fiscal (Relación 1..1 unidireccional)
--  Crea BD + Tablas + Restricciones + Datos de Prueba
-- ==========================================================


-- 1) Crear Base de Datos
DROP DATABASE IF EXISTS tpi_empresa_domicilio;
CREATE DATABASE tpi_empresa_domicilio;
USE tpi_empresa_domicilio;


-- 2)  Crear tabla A: EMPRESA

CREATE TABLE empresa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,

    razonSocial VARCHAR(120) NOT NULL,
    cuit VARCHAR(13) NOT NULL,
    actividadPrincipal VARCHAR(80),
    email VARCHAR(120),
    
    UNIQUE KEY uk_empresa_cuit (cuit)
);


-- 3) Crear tabla B: DOMICILIO FISCAL

CREATE TABLE domicilio_fiscal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,

    calle VARCHAR(100) NOT NULL,
    numero INT,
    ciudad VARCHAR(80) NOT NULL,
    provincia VARCHAR(80) NOT NULL,
    codigoPostal VARCHAR(10),
    pais VARCHAR(80) NOT NULL,

    empresa_id BIGINT NOT NULL,

    UNIQUE KEY uk_domicilio_empresa (empresa_id),

    CONSTRAINT fk_domicilio_empresa
        FOREIGN KEY (empresa_id)
        REFERENCES empresa(id)
        ON DELETE CASCADE
);


-- 4) Índices recomendados

CREATE INDEX idx_empresa_eliminado
    ON empresa (eliminado);

CREATE INDEX idx_domicilio_eliminado
    ON domicilio_fiscal (eliminado);


-- 5) DATOS DE PRUEBA (Inserts)

INSERT INTO empresa (razonSocial, cuit, actividadPrincipal, email)
VALUES
('Ceibo S.A.', '30-71234567-8', 'Impresión 3D', 'contacto@ceibo.com'),
('Pueblo encanto SRL', '30-70987654-1', 'Turismo', 'info@puebloencanto.com');

INSERT INTO domicilio_fiscal (calle, numero, ciudad, provincia, codigoPostal, pais, empresa_id)
VALUES
('Av. Siempre Viva', 742, 'CABA', 'Buenos Aires', '1000', 'Argentina', 1),
('San Martín', 123, 'Capilla del Monte', 'Córdoba', '5184', 'Argentina', 2);

