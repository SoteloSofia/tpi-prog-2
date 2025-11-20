# Trabajo Final Integrador - Programación 2 

## Sistema de Gestión de Empresas y Domicilios Fiscales

### Alumna: Sofía Sotelo
### Materia: Programación II
### Tecnicatura Universitaria en Programación — UTN
### Año: 2025

El presente proyecto implementa un sistema de gestión desarrollado en Java que implementa el dominio **Empresa → DomicilioFiscal**, modelando una relación 1-a-1 unidireccional.
La aplicación utiliza persistencia con JDBC puro (sin ORM), realiza operaciones CRUD completas y manejo de transacciones manuales (commit/rollback) para garantizar la integridad de los datos.

---
## 🧱 Arquitectura del Proyecto

Se utilizó una arquitectura en capas, siguiendo el modelo recomendado por la cátedra:

src/
 ├── config/        → conexión a BD y manejo de transacciones
 ├── dao/           → acceso a datos con JDBC (CRUD)
 ├── entities/      → clases de dominio (Empresa y DomicilioFiscal)
 ├── service/       → reglas de negocio + transacciones
 └── main/          → menú de consola y arranque de la app

---

## 🛠️ Requisitos Técnicos

* **Lenguaje:** Java 17 o superior
* **IDE:** Apache NetBeans (o un IDE compatible)
* **Base de Datos:** MySQL 8.0+
* **Persistencia:** JDBC (con `mysql-connector-j-8.0.33.jar`)
* **Driver JDBC:** MySQL Connector/J 8.0.33+
  
---

## 🚀 Instalación y Ejecución

Para ejecutar este proyecto, se deben seguir los siguientes pasos:


### 1. Configuración de la Base de Datos

El script SQL necesario está en la raíz de este repositorio.

1.  Abrir el cliente de MySQL (MySQL Workbench, DBeaver, etc.).
2.  Abrir el archivo **`script_unico.sql`** que se encuentra en este proyecto.
3.  **Ejecutar el script completo.**

Este script creará la base de datos `tpi_empresa_domicilio`, las tablas `empresa` y `domicilio_fiscal` con sus relaciones, y cargará dos empresas de prueba.

### 2. Configuración del Proyecto 

Este proyecto es `Java with Ant` y requiere que se agregue el "driver" de MySQL manualmente.

1.  **Descargar el Driver:** Buscar y descargar "MySQL Connector/J" (archivo `.jar`).
2.  **Abrir el Proyecto:** Abrir el proyecto en NetBeans.
3.  **Agregar el Driver:**
    * En el panel "Projects", hacer clic derecho sobre la carpeta **`Libraries`**.
    * Seleccionar **`Add JAR/Folder...`**.
    * Buscar y seleccionar el archivo `.jar` que se descargó (ej. `mysql-connector-j-8.0.33.jar`).
    * Hacer clic en "Open".

### 3. Credenciales de Conexión

La aplicación utiliza un usuario dedicado creado por el script SQL:

* **URL:** `jdbc:mysql://localhost:3306/tpi_empresa_domicilio`
* **Usuario:** `tpi`
* **Contraseña:** `1234`

> *Nota: El script `script_unico.sql` se encarga de crear este usuario y asignarle los permisos automáticamente.*


### 4. Ejecución

Para iniciar el programa:

* Hacer clic derecho en el proyecto en NetBeans.
* Seleccionar **`Run`** (Ejecutar).
  
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
* **6) Listar Domicilios Fiscales:**
   *Muestra un listado de todos los domicilios fiscales activos (no eliminados) en el sistema.
* **7) Ver Domicilio Fiscal por ID:**
   *Permite buscar y visualizar el detalle completo de un domicilio específico ingresando su identificador único.
* **8) Actualizar Domicilio Fiscal:**
   *Permite modificar los atributos de un domicilio (calle, número, ciudad, etc.). Implementa una mejora de UX: si se deja un campo vacío al editar, se mantiene el valor actual.
* **9) Eliminar (lógicamente) Domicilio Fiscal:**
    *Realiza una baja lógica (soft delete) de un domicilio específico, marcándolo como eliminado en la base de datos sin borrar el registro físico.
---

## 🎥 Video de Presentación 

Aquí se encuentra el enlace al video de la presentación del TFI, donde se muestra el flujo de uso, la arquitectura y la demostración del `rollback` transaccional.

**ENLACE DE LA PRESENTACIÓN**
