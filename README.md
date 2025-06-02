# Microservices App - Catalog & Inventory

Este proyecto consiste en dos microservicios independientes construidos con **Spring Boot** y **Docker**, que se comunican entre sí utilizando **Feign Client**.

## Microservicios

### 📦 Catalog Service
- Puerto: `8081`
- Funcionalidad: Gestiona los productos.
- Endpoints: `/api/products`, `/api/products/{id}`
- Documentación: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

### 📦 Inventory Service
- Puerto: `8080`
- Funcionalidad: Gestiona el stock de los productos.
- Endpoints: `/api/stock`, `/api/stock/{productId}/increase`, `/api/stock/{productId}/decrease`
- Documentación: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 🐳 Docker

Ambos servicios están contenidos y orquestados con Docker Compose.

### Comandos principales:

```bash
# Construir e iniciar los contenedores
docker-compose up --build

# Detener los contenedores
docker-compose down --remove-orphans
```

## ✅ Requisitos

- Docker y Docker Compose instalados.
- JDK 17+
- Maven

## 🧪 Tests

Cada microservicio contiene pruebas unitarias de lógica de negocio y controladores (`service` y `controller`).

## 🔗 Comunicación entre microservicios

Inventory-service valida la existencia de productos llamando al endpoint del catalog-service a través de **Feign Client**.

## 👩‍💻 Autor

Gina Rodríguez
