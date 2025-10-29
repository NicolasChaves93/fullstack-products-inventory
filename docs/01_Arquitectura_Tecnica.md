## 01_Arquitectura_Tecnica

### 1. Descripción general

El sistema **Fullstack Products Inventory** está compuesto por tres aplicaciones principales:

* **Frontend (Angular 18+):** interfaz SPA (Single Page Application) para la gestión de productos e inventarios.
* **Products Service (Spring Boot):** microservicio responsable del CRUD de productos.
* **Inventory Service (Spring Boot):** microservicio encargado de la administración del inventario.

Ambos servicios backend se comunican a través de llamadas REST y comparten la base de datos PostgreSQL, orquestados mediante Docker Compose.

---

### 2. Visión general de arquitectura

El siguiente diagrama muestra una vista general de la relación entre frontend, servicios backend, base de datos y usuarios.

![Diagrama de arquitectura](Diagrama%20de%20arquitectura.png)

Descripción

* El usuario interactúa con Angular, que consume los *endpoints* de ambos servicios.
* Los microservicios están desacoplados y cada uno gestiona su propio contexto.
* La base de datos central almacena productos e inventarios con integridad relacional.

---

### 3. Arquitectura interna de los microservicios

Cada microservicio sigue una Arquitectura Hexagonal (Ports & Adapters), estructurada en tres paquetes principales: `application`, `domain` e `infrastructure`.
Esta organización permite mantener el dominio aislado, una capa de aplicación limpia y una infraestructura fácilmente reemplazable.

![Diagrama interno de microservicios](Diagrama%20arquitectura%20backend.png)

**Descripción**
- Controller REST (en infrastructure) recibe las peticiones HTTPS y delega en la capa application.
- Service / Casos de Uso orquesta la lógica de negocio y utiliza Ports para comunicarse con los adaptadores.
- Domain encapsula las reglas del negocio y define interfaces de repositorio.
  Adaptadores de Infraestructura (JPA, REST, Configuración) implementan los puertos definidos en application.

**Ventajas**
- Desacoplamiento entre lógica de negocio y frameworks.
- Mayor facilidad de pruebas unitarias.
- Sustituibilidad de infraestructura (JPA, Mongo, API externa, etc.) sin alterar el dominio.

**Estructura de carpetas sugerida**
```text
    src/main/java/com/example/products/
    ├── application/
    │   ├── dto/
    │   ├── exception/
    │   ├── mapper/
    │   ├── port/
    │   │   ├── in/ (Puertos de entrada, p.ej. UseCase)
    │   │   └── out/ (Puertos de salida, p.ej. Repositorios externos)
    │   └── service/
    │       └── impl/
    ├── domain/
    │   ├── model/
    │   ├── event/
    │   └── repository/
    └── infrastructure/
        ├── config/
        ├── persistence/ (Implementación JPA/Hibernate)
        ├── web/
        │   └── controller/
        └── integration/ (Adaptadores a otros servicios)
```

---

### 4. Diagramas de secuencia

**Comunicación entre microservicios**
Los servicios se comunican vía HTTP REST. El diagrama de secuencia muestra el flujo completo para actualizar el inventario, incluyendo la validación del producto y la persistencia en la base de datos compartida.
![Diagrama de secuencia entre microservicios](Diagrama%20Secuencia%20entre%20microservicios.png)

**Flujo del Products Service: Creación de Producto**
El Products Service es responsable de su propio recurso. Este diagrama ilustra el flujo de creación de un producto nuevo.
![Diagrama de secuencia creacion de producto](Diagrama%20Secuencia%20creación%20de%20producto.png)

---

### 5. Despliegue con Docker Compose

Cada servicio se ejecuta como un contenedor independiente, orquestado en una red interna definida por Docker Compose. El siguiente diagrama muestra la relación de red y puertos expuestos.

![Diagrama de despliegue](Diagrama%20de%20despliege.png)

**Puertos predeterminados**

| Servicio | Puerto | Descripción |
|---|---:|---|
| Frontend | 4200 | Aplicación Angular |
| Products Service | 8080 | API de productos |
| Inventory Service | 8081 | API de inventarios |
| PostgreSQL | 5432 | Base de datos relacional |

---

### 6. Componentes clave

| Componente | Descripción | Tecnología |
|---|---|---|
| Frontend SPA | Interfaz web dinámica que consume APIs REST | Angular 18+, TypeScript |
| Products Service | Gestión de productos | Spring Boot, JPA |
| Inventory Service | Gestión de inventarios | Spring Boot, JPA |
| Base de datos | Almacenamiento relacional compartido | PostgreSQL |
| Infraestructura | Orquestación y ejecución de contenedores | Docker Compose |
| Documentación | Documentación y pruebas interactivas | OpenAPI (Swagger UI) |
