# Fullstack Products Inventory

## Descripción
Aplicación full stack compuesta por una API para la gestión de productos e inventarios desarrollada con Spring Boot, y un frontend web en Angular para la administración de datos.  
Permite crear, actualizar, eliminar y consultar productos e inventarios, aplicando buenas prácticas de arquitectura, resiliencia y mantenibilidad.

---

## Arquitectura y Solución Técnica
El sistema está dividido en dos microservicios backend y una aplicación frontend desacoplada:

- **products-service:** gestiona los datos de productos (CRUD completo).  
- **inventory-service:** administra el stock de cada producto.  
- **angular-app:** interfaz web para operar sobre los datos.

Cada microservicio backend aplica una arquitectura hexagonal (Ports & Adapters), separando las capas de dominio, aplicación e infraestructura.  
Este diseño permite desacoplar la lógica de negocio de frameworks o bases de datos y facilita pruebas unitarias e integración.

### Patrones aplicados
- Arquitectura Hexagonal  
- DTOs y mapeadores  
- Repositorios y entidades de dominio  
- Inyección de dependencias  
- Validación con Bean Validation  

### Beneficios
- Desacoplamiento entre capas  
- Mayor mantenibilidad y escalabilidad  
- Resiliencia ante fallos  
- Facilidad de despliegue y pruebas  

---

## Tecnologías principales
### Backend
- Java 17 / Spring Boot 3.x  
- Spring Data JPA / PostgreSQL  
- Resilience4j / OpenAPI / Micrometer  
- JUnit 5 / Testcontainers  

### Frontend
- Angular 18+  
- TypeScript / RxJS / SCSS  
- Angular Material (UI)  
- Karma / Jasmine  

### Infraestructura
- Docker / Docker Compose

---

## Estructura de carpetas

### Products Service
```text
products-service/
├── src/
│   ├── main/
│   │   ├── java/com/store/products/
│   │   │   ├── application/       # Casos de uso, DTOs, servicios
│   │   │   ├── config/            # Configuración (Swagger, DB)
│   │   │   ├── domain/            # Entidades y repositorios
│   │   │   ├── infrastructure/    # Implementaciones técnicas
│   │   │   ├── interface_/        # Controladores REST
│   │   │   └── ProductsServiceApp.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/
│   └── test/
│       └── java/com/store/products/
├── Dockerfile
├── pom.xml
```

### Inventory Service
```text
inventory-service/
├── src/
│   ├── main/
│   │   ├── java/com/store/inventory/
│   │   │   ├── application/
│   │   │   ├── config/
│   │   │   ├── domain/
│   │   │   ├── infrastructure/
│   │   │   ├── interface_/
│   │   │   └── InventoryServiceApp.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/
│   └── test/
│       └── java/com/store/inventory/
├── Dockerfile
├── pom.xml
```

### Frontend
```text
angular-app/
├── src/
│   ├── app/
│   │   ├── modules/
│   │   ├── components/
│   │   ├── services/
│   │   ├── models/
│   │   └── app.module.ts
│   ├── assets/
│   └── environments/
├── angular.json
├── package.json
└── Dockerfile
```

---

## Ejecución del proyecto

### Requisitos
- Docker y Docker Compose instalados.  
- Archivo `.env` configurado (ver `infra/.env.example`).

### Pasos
1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/NicolasChaves93/fullstack-products-inventory.git
   cd fullstack-products-inventory
   ```

2. **Construir y levantar los servicios**
   ```bash
   docker-compose up --build
   ```

3. **Acceder a las aplicaciones**
   - Frontend: [http://localhost:4200](http://localhost:4200)  
   - Products API: [http://localhost:8080/api/products](http://localhost:8080/api/products)  
   - Inventory API: [http://localhost:8081/api/inventories](http://localhost:8081/api/inventories)  
   - Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

4. **Ejecutar pruebas unitarias**
   ```bash
   cd backend/products-service
   ./mvnw test
   ```

---

## Endpoints principales
**ProductsController (/api/v1/products)**  
- `GET /api/v1/products` — Listar todos los productos  
- `GET /api/v1/products/{id}` — Obtener producto por ID  
- `POST /api/v1/products` — Crear un nuevo producto  
- `PATCH /api/v1/products/{id}` — Actualizar parcialmente un producto  
- `DELETE /api/v1/products/{id}` — Eliminar un producto  

**InventoryController (/api/v1/inventories)**  
- `GET /api/v1/inventories` — Listar inventarios  
- `GET /api/v1/inventories/{id}` — Obtener inventario por ID  
- `PATCH /api/v1/inventories/{id}` — Actualizar cantidad  
- `POST /api/v1/inventories/increase` — Aumentar stock  
- `POST /api/v1/inventories/decrease` — Reducir stock  

**HealthController**  
- `GET /health` — Verificar salud del servicio  

---

## API Reference

### Products API

#### Obtener todos los productos
```http
GET /api/v1/products
```
| Parámetro | Tipo | Descripción |
| :--------- | :--- | :---------- |
| `page[number]` | `integer` | Número de página (opcional) |
| `page[size]` | `integer` | Tamaño de página (opcional) |

#### Obtener producto por ID
```http
GET /api/v1/products/{id}
```
| Parámetro | Tipo | Descripción |
| :--------- | :--- | :---------- |
| `id` | `string` | **Requerido.** Identificador del producto |

#### Crear nuevo producto
```http
POST /api/v1/products
```
**Body**
```json
{
  "name": "Laptop Lenovo",
  "price": 4500000,
  "description": "Laptop Core i7 16GB RAM"
}
```

#### Actualizar parcialmente un producto
```http
PATCH /api/v1/products/{id}
```
**Body**
```json
{
  "price": 4800000,
  "description": "Actualización de precio y descripción"
}
```

#### Eliminar producto
```http
DELETE /api/v1/products/{id}
```

---

### Inventory API

#### Obtener todos los inventarios
```http
GET /api/v1/inventories
```

#### Obtener inventario por ID
```http
GET /api/v1/inventories/{id}
```
| Parámetro | Tipo | Descripción |
| :--------- | :--- | :---------- |
| `id` | `string` | **Requerido.** Identificador del inventario |

#### Aumentar stock
```http
POST /api/v1/inventories/increase
```
**Body**
```json
{
  "productId": "1",
  "amount": 10
}
```

#### Reducir stock
```http
POST /api/v1/inventories/decrease
```
**Body**
```json
{
  "productId": "1",
  "amount": 5
}
```

#### Actualizar cantidad (PATCH)
```http
PATCH /api/v1/inventories/{id}
```
**Body**
```json
{
  "quantity": 25
}
```

---

### Health API

#### Verificar estado del servicio
```http
GET /health
```
**Respuesta**
```json
{
  "status": "UP"
}
```

---

## Flujo de trabajo recomendado
### Estructura base
- `master`: Rama estable (producción).  
- `staging`: Pruebas previas a producción.  
- `develop`: Desarrollo de nuevas funcionalidades.

### Nomenclatura de ramas adicionales
- `feature/nueva-funcionalidad`: Nuevas características.  
- `fix/bug-descripcion`: Corrección de errores.  
- `improvement/mejora-x`: Mejoras no funcionales.

---

## Librerías y componentes utilizados
| Componente / Librería | Función principal | Razón de uso |
|------------------------|------------------|--------------|
| **Spring Boot** | Framework backend | Configuración mínima y desarrollo ágil. |
| **Spring Data JPA** | Persistencia | Simplifica el acceso a datos. |
| **PostgreSQL** | Base de datos | Fiabilidad y soporte relacional. |
| **Resilience4j** | Resiliencia | Circuit breaker, retry y timeout. |
| **Micrometer + Actuator** | Monitoreo | Métricas y endpoints de salud. |
| **OpenAPI / Swagger** | Documentación | Exploración y pruebas de endpoints. |
| **JUnit 5 + Testcontainers** | Testing | Pruebas reproducibles. |
| **Angular 18+** | Frontend SPA | Modularidad y escalabilidad. |
| **TypeScript + ESLint + Prettier** | Frontend | Código consistente y seguro. |
| **Docker Compose** | Infraestructura | Orquestación local sencilla. |
| **Google Java Format** | Formateador Java | Mantiene consistencia de estilo. |

---

## Apuntes adicionales
- Arquitectura basada en **Hexagonal Architecture (Ports & Adapters)**.  
- El dominio se mantiene libre de dependencias externas.    
- Configuración centralizada en `application.yml`.  
- Pruebas unitarias e integración en `src/test/java`.  
- Documentación de la API disponible en `/swagger-ui.html` o `/swagger-ui/`.

---

## Documentación

La documentación técnica y los diagramas del proyecto están en la carpeta `docs/`. Puedes navegar a estos archivos directamente desde GitHub o desde tu copia local.

Enlaces rápidos (relativos):

- Arquitectura técnica — [docs/01_Arquitectura_Tecnica.md](docs/01_Arquitectura_Tecnica.md)
- Flujo funcional — [docs/02_Flujo_Funcional.md](docs/02_Flujo_Funcional.md)
- Modelo de datos — [docs/03_Modelo_Datos.md](docs/03_Modelo_Datos.md)
- Diagramas (draw.io) — [docs/Diagramas.drawio](docs/Diagramas.drawio)
