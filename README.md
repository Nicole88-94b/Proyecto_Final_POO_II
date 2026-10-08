# Sistema de Gestión de Biblioteca Escolar

Aplicación de escritorio desarrollada en Java para apoyar la administración de una biblioteca escolar. El sistema permite gestionar libros y estudiantes, registrar préstamos y devoluciones, consultar historiales y mantener la información almacenada en una base de datos MySQL.

## Objetivo

El proyecto integra los principales contenidos trabajados durante el bimestre: programación orientada a objetos, herencia, polimorfismo, colecciones, manejo de excepciones, interfaces gráficas con Swing, acceso a datos mediante JDBC, operaciones CRUD y uso básico de hilos.

## Funcionalidades

### Bibliotecario

- Iniciar sesión con una cuenta de bibliotecario.
- Registrar, consultar, modificar y eliminar libros.
- Registrar, consultar, modificar y eliminar estudiantes junto con sus cuentas de acceso.
- Registrar préstamos mediante el RUT del estudiante y el ISBN del libro.
- Registrar devoluciones y restituir el stock correspondiente.
- Consultar todos los préstamos, los préstamos activos y el historial de un estudiante.
- Identificar préstamos activos, atrasados y devueltos.
- Consultar el libro o los libros con mayor cantidad de préstamos.

### Estudiante

- Iniciar sesión con una cuenta de estudiante.
- Consultar el catálogo de libros en modalidad de solo lectura.
- Registrar un préstamo para su propia cuenta.
- Consultar su historial y sus préstamos activos.
- Registrar la devolución de uno de sus préstamos.

## Reglas principales

- Cada préstamo se registra con la fecha actual y un plazo de devolución de siete días.
- El préstamo sólo se concreta si el estudiante y el libro existen y hay stock disponible.
- Al prestar un libro se descuenta una unidad del stock; al devolverlo, se restituye.
- Un préstamo pendiente cuya fecha límite ya pasó se muestra como `ATRASADO`.
- Los libros y estudiantes con historial asociado no se eliminan, para conservar la integridad de los registros.
- El RUT debe ingresarse sin puntos y con guion.
- Los campos obligatorios, formatos numéricos, ISBN, RUT y relaciones entre entidades se validan antes de guardar.

## Arquitectura del proyecto

El sistema separa sus responsabilidades en las siguientes capas:

- **Modelo:** representa las entidades `Persona`, `Usuario`, `Estudiante`, `Libro`, `Categoria` y `Prestamo`.
- **Vista:** contiene las ventanas Swing y muestra mensajes comprensibles al usuario.
- **Controlador:** coordina las reglas del sistema y comunica las vistas con el acceso a datos.
- **DAO:** define y ejecuta las operaciones SQL para cada entidad mediante JDBC.
- **Conexión:** `DatabaseConnection` utiliza el patrón Singleton para compartir una conexión con MySQL.

La clase abstracta `Persona` reúne los datos comunes de usuarios y estudiantes. Las interfaces DAO permiten que los controladores trabajen con contratos comunes, mientras que sus implementaciones se encargan directamente de la persistencia.

## Concurrencia

El registro de préstamos se ejecuta en un hilo secundario para evitar que la interfaz quede bloqueada durante el acceso a la base de datos. Los cambios visuales regresan al hilo de eventos de Swing mediante `SwingUtilities.invokeLater`.

Además, las operaciones que registran préstamos y devoluciones están sincronizadas. Esto protege la actualización del stock cuando se procesan solicitudes simultáneas. La prueba realizada utilizó tres solicitudes concurrentes para un libro con dos ejemplares: se aceptaron dos préstamos, se rechazó el tercero y el stock no quedó en un valor negativo.

## Base de datos

La aplicación utiliza la base de datos MySQL `biblioteca` y las tablas:

- `usuarios`
- `estudiantes`
- `categorias`
- `libros`
- `prestamos`

Los scripts se encuentran en:

```text
src/main/resources/database/
├── PRY2203_EFT_S9_Script_crea_tablas_biblioteca.sql
└── PRY2203_EFT_S9_Script_poblado_tablas_biblioteca.sql
```

Primero se debe ejecutar el script de creación y luego el script de poblado.

## Tecnologías utilizadas

- Java 17 o superior
- Maven
- Java Swing
- MySQL 8
- JDBC y MySQL Connector/J 9.7.0
- IntelliJ IDEA

## Estructura

```text
src/main/
├── java/
│   ├── controlador/
│   ├── dao/
│   │   └── interfaces/
│   ├── main/
│   ├── modelo/
│   └── vista/
└── resources/
    ├── database/
    └── images/
```

## Configuración y ejecución

1. Iniciar el servicio de MySQL.
2. Ejecutar los dos scripts SQL incluidos en `src/main/resources/database`, respetando su orden.
3. Abrir el proyecto como proyecto Maven en IntelliJ IDEA.
4. Configurar un JDK 17 o superior.
5. Crear una configuración de ejecución para `main.Main`.
6. Agregar la variable de entorno `BIBLIOTECA_DB_PASSWORD` con la contraseña local del usuario `root` de MySQL.
7. Ejecutar la clase `Main`.

La contraseña de la base de datos no se guarda dentro del código ni se incluye en Git.

### Ejecución del archivo JAR

Para ejecutar la versión empaquetada en otro computador, primero se debe iniciar MySQL y ejecutar los scripts de creación y poblamiento de la base de datos. Como la contraseña del usuario `root` puede ser diferente en cada equipo, debe configurarse antes de iniciar la aplicación.

En PowerShell, abre una terminal dentro de la carpeta que contiene el JAR y ejecuta:

```powershell
$env:BIBLIOTECA_DB_PASSWORD='SU_CONTRASEÑA_DE_MYSQL'
java -jar ".\biblioteca-escolar-eft-1.0-SNAPSHOT-ejecutable.jar"
```

La variable se mantiene solamente durante esa sesión de terminal. Si no se configura o la contraseña es incorrecta, la aplicación podrá abrirse, pero no podrá consultar la información almacenada en MySQL.

## Usuarios de prueba

El script de poblado incluye, entre otras, las siguientes cuentas:

| Tipo de usuario | RUT | Contraseña |
|---|---|---|
| Bibliotecario | `12345678-9` | `clave123` |
| Estudiante | `98765432-1` | `clave123` |

Estas credenciales se incluyen únicamente con fines académicos y de prueba.

## Posibles mejoras futuras

- Reemplazar la eliminación de estudiantes con historial por un estado activo o inactivo.
- Incorporar una confirmación del bibliotecario para las devoluciones solicitadas por estudiantes.
- Proteger las contraseñas mediante un mecanismo seguro de hash.
- Ampliar los reportes y opciones de búsqueda del catálogo.

## Autora

Nicole Ortega
