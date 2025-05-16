# 📚 Sistema de Gestión Bibliotecaria - BibliotecaMVC

## Tecnologías Utilizadas
- **Java 11**
- **Swing** (Interfaz gráfica)
- **MySQL Connector/J** (Conexión a BD)
- **Maven** (Gestión de dependencias)

## Funcionalidades Principales
✅ CRUD completo para:
- Libros (ISBN, páginas, editorial)
- Revistas (Edición, categoría)
- DVDs (Duración, género)

✅ Búsqueda avanzada por:
- Título
- Género/categoría
- Tipo de elemento

✅ Validación de datos integrada

## Configuración Inicial
1. Importar proyecto en IntelliJ IDEA como proyecto Maven
2. Configurar conexión a BD en `DatabaseConnection.java`
3. Ejecutar `Main.java`

## Requisitos
- MySQL 8.0+ (crear base de datos según esquema en `resources/`)
- JDK 11+

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Swing](https://img.shields.io/badge/GUI-Swing-orange)
![MySQL](https://img.shields.io/badge/DB-MySQL-4479A1)

## 🔍 Descripción
Aplicación de escritorio para gestión completa de bibliotecas con capacidad para administrar libros, revistas y DVDs. Desarrollada en Java con interfaz Swing y conexión a MySQL.

## 🛠️ Requisitos Técnicos
- **Java JDK 17+**
- **MySQL Server 8.0+**
- **Maven 3.8+**
- **1GB RAM mínimo**
- **200MB espacio en disco**

## 🚀 Instalación Rápida

### 1. Clonar y configurar
```bash
git clone https://github.com/fergmoon1/BibliotecaMVC_FG.git
cd BibliotecaMVC_FG


CREATE DATABASE biblioteca;
USE biblioteca;
-- Ejecutar el archivo src/main/resources/schema.sql

*Use archivo sql adjunto para generar la bd*

private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
private static final String USER = "root"; // Cambiar por tu usuario
private static final String PASSWORD = "password"; // Cambiar por tu contraseña (actualmente sin contraseña)

# 📚 Sistema de Gestión Bibliotecaria

## Descripción
Aplicación de escritorio para administrar libros, revistas y DVDs en una biblioteca, desarrollada en Java con interfaz gráfica Swing.

## Estructura del Proyecto

BibliotecaMVC/
├── .idea/ # Configuración de IntelliJ IDEA
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/
│ │ │ └── biblioteca/
│ │ │ ├── controller/ # Lógica de controladores
│ │ │ │ └── BibliotecaController.java
│ │ │ ├── dao/ # Acceso a datos
│ │ │ │ ├── ElementoBibliotecaDAO.java
│ │ │ │ └── ElementoBibliotecaDAOImpl.java
│ │ │ ├── model/ # Entidades
│ │ │ │ ├── DVD.java
│ │ │ │ ├── ElementoBiblioteca.java
│ │ │ │ ├── Libro.java
│ │ │ │ └── Revista.java
│ │ │ ├── util/ # Utilidades
│ │ │ │ ├── BibliotecaException.java
│ │ │ │ ├── DatabaseConnection.java
│ │ │ │ ├── Logger.java
│ │ │ │ └── UIConfig.java
│ │ │ ├── view/ # Interfaces gráficas
│ │ │ │ ├── BibliotecaView.java
│ │ │ │ └── InputForm.java
│ │ │ └── Main.java # Punto de entrada
│ │ └── resources/ # Recursos (configuraciones, imágenes)
│ └── test/ # Pruebas unitarias
├── target/ # Artefactos de compilación
├── .gitignore # Archivos excluidos de Git
├── biblioteca.log # Archivo de logs
└── pom.xml # Configuración de Maven


## Funcionalidades Principales
- **Gestión de Libros**:
  - Agregar nuevos libros con ISBN, autor, editorial
  - Buscar por título o género
  - Editar y eliminar registros

- **Gestión de Revistas**:
  - Registrar revistas con número de edición
  - Filtrar por categoría
  - Actualizar información

- **Gestión de DVDs**:
  - Añadir DVDs con duración y género
  - Búsqueda avanzada
  - Gestión completa


🚀 Cómo Ejecutar la Aplicación

    Desde el IDE:

        Abrir Main.java en src/main/java/com/biblioteca/

        Click en el botón ▶️ "Run"

    Desde terminal:
    bash

    mvn clean compile exec:java -Dexec.mainClass="com.biblioteca.Main"

🏛️ Estructura del Código
Carpeta	Contenido
controller/	Lógica de negocio (BibliotecaController)
dao/	Patrón Data Access Object (ElementoBibliotecaDAO)
model/	Entidades: Libro, Revista


## Cómo Usar la Aplicación
1. **Iniciar la aplicación**:
   - Ejecutar `Main.java` desde tu IDE
   - O usar: `mvn clean compile exec:java`

2. **Interfaz principal**:
   - Barra superior con pestañas para cada tipo de material
   - Botones para Agregar/Editar/Eliminar
   - Campo de búsqueda con filtros

3. **Agregar nuevo ítem**:
   - Click en botón "Agregar"
   - Seleccionar tipo (Libro/Revista/DVD)
   - Completar formulario
   - Guardar cambios

4. **Buscar elementos**:
   - Escribir término en campo de búsqueda
   - Usar filtros para refinar resultados
   - Doble-click en resultado para ver detalles

## Requisitos
- Java JDK 17 o superior
- Maven para gestión de dependencias
- Resolución de pantalla mínima: 1024x768

## Solución de Problemas Comunes
- **Error al iniciar**: Verificar que Java 17+ esté instalado
- **Datos no visibles**: Revisar conexión a la base de datos
- **Botones deshabilitados**: Seleccionar un registro primero

## Contacto
Para soporte técnico o reporte de errores:
- Email: soporte@bibliotecaapp.com
- Issues en GitHub

---

> ℹ️ **Nota**: La primera ejecución puede tardar unos segundos mientras se establecen las conexiones iniciales.