# Trabajo Final Integrador - Programación 2 (UTN)

## Sistema de Gestión de Empresas y Domicilios Fiscales

Este proyecto es el Trabajo Final Integrador (TFI) de la materia Programación 2. Es una aplicación de consola (CRUD) desarrollada en Java que implementa el dominio **Empresa → DomicilioFiscal**, modelando una relación 1-a-1 unidireccional.

La aplicación utiliza una arquitectura por capas (DAO, Service, Main), persistencia con JDBC puro (sin ORM) y manejo de transacciones manuales (commit/rollback) para para garantizar la integridad de los datos.

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 17+
* **IDE:** Apache NetBeans (proyecto `Java with Ant`)
* **Base de Datos:** MySQL 8.0+
* **Persistencia:** JDBC (con `mysql-connector-j-8.0.33.jar`)
* **Arquitectura:** 4 Capas (Entities, DAO, Service, Main), más el paquete de infraestructura config.

---

## 🚀 Instalación y Ejecución

Para ejecutar este proyecto, sigue estos 3 pasos:

### 1. Requisitos Previos

* Tener instalado **Java** (JDK 17 o superior).
* Tener un servidor **MySQL** corriendo (ej. XAMPP, MySQL Workbench).
* Tener **Apache NetBeans** (o un IDE compatible).

### 2. Configuración de la Base de Datos

El script SQL necesario está en la raíz de este repositorio.

1.  Abre tu cliente de MySQL (MySQL Workbench, DBeaver, etc.).
2.  Abre el archivo **`script_unico.sql`** que se encuentra en este proyecto.
3.  **Ejecuta el script completo.**

Este script creará la base de datos `tpi_empresa_domicilio`, las tablas `empresa` y `domicilio_fiscal` con sus relaciones, y cargará dos empresas de prueba.

### 3. Configuración del Proyecto 

Este proyecto es `Java with Ant` y requiere que se agregue el "driver" de MySQL manualmente.

1.  **Descarga el Driver:** Busca y descarga "MySQL Connector/J" (archivo `.jar`).
2.  **Abre el Proyecto:** Abre el proyecto en NetBeans.
3.  **Agrega el Driver:**
    * En el panel "Projects", haz clic derecho sobre la carpeta **`Libraries`**.
    * Selecciona **`Add JAR/Folder...`**.
    * Busca y selecciona el archivo `.jar` que descargaste (ej. `mysql-connector-j-8.0.33.jar`).
    * Haz clic en "Open".

### 4. Ejecución

¡Listo! Para iniciar el programa:

* Haz clic derecho en el proyecto en NetBeans.
* Selecciona **`Run`** (Ejecutar).
* O bien, abre la clase `main/Main.java` y ejecútala.

---

## 📋 Flujo de Uso

El programa presenta un menú de consola con 9 opciones para administrar las entidades:

* **1) Alta de Empresa (con domicilio fiscal):**
    * Esta es la operación transaccional principal.
    * Pide los datos de la Empresa (A) y luego los del Domicilio (B).
    * Inserta ambas entidades en una sola transacción. Si algo falla (ej. CUIT duplicado), se hace `rollback` y no se guarda nada.
* **2) Listar todas las Empresas:**
    * Muestra todas las empresas (no eliminadas) y sus domicilios asociados (usando `LEFT JOIN`).
* **3) Buscar Empresa por CUIT:**
    * Permite encontrar una empresa por su CUIT.
* **4) Actualizar datos de una Empresa:**
    * Permite modificar Razón Social, CUIT, etc.
* **5) Eliminar (lógico) una Empresa:**
    * Realiza una baja lógica (soft delete) de la empresa y su domicilio asociado.
* **6-9) CRUD de Domicilios:**
    * Permite listar, ver, actualizar y eliminar domicilios de forma independiente.

---

## 🎥 Video de Presentación 

Aquí se encuentra el enlace al video de la presentación del TFI, donde se muestra el flujo de uso, la arquitectura y la demostración del `rollback` transaccional.

**[AQUÍ VA EL ENLACE A TU VIDEO EN YOUTUBE O DRIVE]**
