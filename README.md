# Mi Negocio – Servicio de Clientes

Sistema de gestión de clientes y sus sucursales (direcciones) para _Mi Negocio_, el módulo contable/facturación de **Alquimiasoft**.  
Permite **crear, buscar, editar y eliminar** clientes, así como registrar **múltiples direcciones** asegurando que exista **una sola matriz**.

| Tecnologías                 | Versión |
| --------------------------- | ------- |
| Java                        | 17 +    |
| Spring Boot                 | 3.x     |
| Spring Data JPA (Hibernate) | 6.x     |
| PostgreSQL                  | 14 +    |
| Lombok                      | 1.18 +  |
| Liquibase                   | 4.x     |
| JUnit 5 · Mockito           | Tests   |

---

## 1. Arquitectura

```
mi-negocio-clientes/
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java/ec/telconet/minegocio
    │   │   ├── configuracion/
    │   │   ├── controlador/
    │   │   ├── dto/
    │   │   ├── entidad/
    │   │   ├── excepcion/
    │   │   ├── repositorio/
    │   │   └── servicio/
    │   └── resources
    │       ├── application.yml
    │       ├── application-dev.yml
    │       ├── application-prod.yml
    │       └── db/changelog/db.changelog-master.xml
    └── test/java/…
```

_Estilo Clean/Hexagonal:_ `controlador → servicio → repositorio → BD`, DTOs y mapeo con MapStruct, validaciones Bean Validation y especificaciones para búsquedas.

---

## 2. Configuración de la base de datos (Render)

### 2.1 Propiedades

Crea **`src/main/resources/application-prod.yml`** con las credenciales de tu instancia **Render**.

> **¡No subas contraseñas reales a Git!** Ponlas como variables de entorno o usa un `.env` en tu servidor CI/CD.

```yaml
spring:
  application:
    name: MiNegocio
  datasource:
    url: ${DB_URL:jdbc:postgresql://dpg-d07nttk9c44c73a5uodg-a.oregon-postgres.render.com:5432/alquimiasoft}
    username: ${DB_USER:anderson}
    password: ${DB_PASS}
    hikari:
      maximum-pool-size: 6
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
  liquibase:
    change-log: classpath:db/changelog/db.changelog-master.xml
```

En **Render Dashboard → Environment → Environment Variables** define:

| KEY       | VALUE                |
| --------- | -------------------- |
| `DB_URL`  | (la URL JDBC)        |
| `DB_USER` | anderson             |
| `DB_PASS` | tu contraseña segura |

### 2.2 Perfil activo

Ejecuta la app con:

```bash
java -jar target/mi-negocio-clientes.jar --spring.profiles.active=prod
```

o configura la variable `SPRING_PROFILES_ACTIVE=prod` en Render.

---

## 3. Puesta en marcha local

### 3.1 Requisitos

- **Java 17**
- **Maven 3.9 +**
- Docker (opcional, para levantar PostgreSQL local)

### 3.2 Pasos rápidos

```bash
# 1. Clonar
git clone https://github.com/<tu-usuario>/mi-negocio-clientes.git
cd mi-negocio-clientes

# 2. Arrancar PostgreSQL local (opcional)
docker compose up -d db

# 3. Compilar y lanzar
mvn clean verify
mvn spring-boot:run --spring.profiles.active=dev
```

---

## 4. API REST

| Método   | Endpoint                               | Descripción                                                          |
| -------- | -------------------------------------- | -------------------------------------------------------------------- |
| `GET`    | `/api/clientes/buscar?termino={texto}` | Listar clientes que coincidan por nombre o identificación (paginado) |
| `POST`   | `/api/clientes`                        | Crear cliente **+** dirección matriz                                 |
| `PUT`    | `/api/clientes/{id}`                   | Editar datos de cliente                                              |
| `DELETE` | `/api/clientes/{id}`                   | Eliminar cliente                                                     |
| `POST`   | `/api/clientes/{id}/direcciones`       | Agregar dirección (sucursal)                                         |
| `GET`    | `/api/clientes/{id}/direcciones`       | Listar todas las direcciones de un cliente                           |

### 4.1 Ejemplos `curl`

```bash
curl -X POST http://localhost:8080/api/clientes      -H "Content-Type: application/json"      -d '{
           "cliente":{
             "tipoIdentificacion":"RUC",
             "numeroIdentificacion":"0999999999001",
             "nombreCompleto":"Telconet S.A.",
             "correo":"info@telconet.ec",
             "celular":"0987654321"
           },
           "matriz":{
             "provincia":"Pichincha",
             "ciudad":"Quito",
             "calleDetalle":"Av. 6 de Diciembre y Eloy Alfaro",
             "matriz":true
           }
         }'
```

---

## 5. Pruebas

```bash
mvn test
```

Se incluyen pruebas unitarias con **JUnit 5 + Mockito** que cubren reglas de negocio.

---

## 6. Migraciones Liquibase

El changelog principal está en `src/main/resources/db/changelog/db.changelog-master.xml`.

---

## 7. Docker Compose (opcional)

```yaml
version: "3.9"
services:
  app:
    build: .
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    ports:
      - "8080:8080"
    depends_on:
      - db
  db:
    image: postgres:14
    environment:
      - POSTGRES_DB=mi_negocio
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
    ports:
      - "5432:5432"
```

---

## 8. Buenas prácticas implementadas

- Principios **SOLID**
- Capas limpias y DTOs
- Validaciones con **Bean Validation**
- Manejador global de errores
- TDD con JUnit + Mockito

---

## 9. Licencia

MIT.
