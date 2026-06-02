# Proyecto final de 1ºDAM - AcedemyManager

AcedemyManager es una aplicación web para la gestión de academias, cursos y ofertas. Permite registrar usuarios, iniciar sesión, crear academias, consultar academias existentes, gestionar cursos de una academia y publicar ofertas para alumnos o profesores.

El proyecto está desarrollado con Spring Boot y usa vistas Thymeleaf para la interfaz web.

## Tecnologías

- Java 21
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Thymeleaf
- H2 Database
- Maven
- Lombok

## Funcionalidades principales

- Registro e inicio de sesión de usuarios.
- Perfil de usuario con academias asociadas y ofertas aplicadas.
- Creación y edición de academias.
- Búsqueda y visualización de academias.
- Creación, edición y borrado de cursos.
- Gestión de alumnos y profesores dentro de un curso.
- Creación, búsqueda, edición y borrado de ofertas.
- Aplicación de usuarios a ofertas.
- Revisión de candidatos por parte del director de una academia.

## Estructura del proyecto

```text
src/
  main/
    java/com/salesianostriana/dam/academymanager/
      controllers/    Controladores MVC
      exceptions/     Excepciones propias de la aplicación
      modules/        Entidades JPA
      repositories/   Repositorios Spring Data
      security/       Configuración de seguridad
      services/       Lógica de negocio
    resources/
      templates/      Vistas Thymeleaf
      static/         Recursos estáticos
      application.properties
  test/
    java/             Pruebas del proyecto
```

## Requisitos previos

- Java 21 instalado.
- Maven instalado, o usar el wrapper incluido en el proyecto.

Para comprobar la versión de Java:

```bash
java -version
```

## Instalación y ejecución

Clona el repositorio:

```bash
git clone https://github.com/ruizcajua25/AcedemyManager.git
cd AcedemyManager
```

Ejecuta la aplicación con Maven Wrapper:

```bash
./mvnw spring-boot:run
```

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Cuando la aplicación esté arrancada, abre:

```text
http://localhost:8080
```

## Usuarios de prueba

El proyecto carga datos iniciales al arrancar mediante `DataLoader`. Estos usuarios se pueden usar para probar la aplicación:

| Usuario | Contraseña | Perfil de prueba |
| --- | --- | --- |
| `directora` | `1234` | Directora de Academia Triana |
| `alumno` | `1234` | Alumno de Academia Triana |
| `profesora` | `1234` | Profesora de Academia Triana |
| `alumno2` | `1234` | Usuario candidato en ofertas |

## Rutas principales

| Ruta | Descripción |
| --- | --- |
| `/` | Página de inicio |
| `/login` | Inicio de sesión |
| `/registro` | Registro de usuarios |
| `/perfil` | Perfil del usuario autenticado |
| `/academia/create` | Crear una academia |
| `/academias/find` | Buscar academias |
| `/academias/mi` | Ver mis academias |
| `/academias/{id}` | Detalle de una academia |
| `/academias/{id}/editar` | Editar una academia |
| `/academias/{academiaId}/cursos/crear` | Crear un curso |
| `/academias/{academiaId}/cursos/{id}` | Detalle de un curso |
| `/ofertas/buscar` | Buscar ofertas activas |
| `/ofertas/crear?academiaId={id}` | Crear una oferta |
| `/ofertas/{id}` | Detalle de una oferta |

## Base de datos

La aplicación usa H2 como base de datos en memoria. Al arrancar, Spring Boot crea el contexto de la aplicación y `DataLoader` inserta datos iniciales si no existen usuarios guardados.

La configuración principal se encuentra en:

```text
src/main/resources/application.properties
```

## Pruebas

Para ejecutar las pruebas automatizadas:

```bash
./mvnw test
```

En Windows:

```powershell
.\mvnw.cmd test
```

Actualmente el proyecto incluye una prueba básica de carga de contexto en:

```text
src/test/java/com/salesianostriana/dam/academymanager/AcedemyManagerApplicationTests.java
```

También existe una carpeta `testing/` con documentación para pruebas manuales.

## Notas

- El nombre del proyecto y del artefacto Maven aparece como `AcedemyManager`.
- Algunas rutas de edición y gestión requieren iniciar sesión.
- La aplicación se ejecuta por defecto en el puerto `8080`.
