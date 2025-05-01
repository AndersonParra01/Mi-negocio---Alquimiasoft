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
├── postman
    ├── MiNegocio-Postman-Collection.json
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java/AlquimiaSoft/
    │   │   ├── controllers/
    │   │   ├── dtos/
    │   │   ├── exception/
    │   │   ├── models/
    │   │   ├── repositories/
    │   │   └── services/
    │   └── resources
    │       └── application.properties
    └── test/java/AlquimiaSoft
```

_Estilo Clean/Hexagonal:_ `controlador → servicio → repositorio → BD`, DTOs y mapeo con MapStruct, validaciones Bean Validation y especificaciones para búsquedas.

---

## 2. Configuración de la base de datos (Render)

### 2.1 Propiedades

Crea **`src/main/resources/application.properties`** con las credenciales de tu instancia **Render**.

> **¡No subas contraseñas reales a Git!** Ponlas como variables de entorno o usa un `.env` en tu servidor CI/CD.

```.properties
server.port=8081
spring.application.name=Mi Negocio
spring.datasource.url=jdbc:postgresql://dpg-d07nttk9c44c73a5uodg-a.oregon-postgres.render.com:5432/alquimiasoft
spring.datasource.username=anderson
spring.datasource.password=gbZrKQrJgrwoM1dHKqw8BUrtwQlS0fUW
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# --- Configuración de Liquibase ---
spring.liquibase.enabled=true
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml
 logging.level.liquibase=INFO
logging.level.org.springframework.boot.autoconfigure.liquibase=DEBUG

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
mvn spring-boot:run
```

## 3. Puesta en marcha local

### 3.1 Requisitos

- **Java 17**
- **Maven 3.9 +**
- Docker (opcional, para levantar PostgreSQL local)

### 3.2 Pasos rápidos

```bash
# 1. Clonar
git clone https://github.com/<tu-usuario>/mi-negocio-clientes.git
cd mi-negocio-AlquimiaSoft

# 2. Arrancar PostgreSQL local (opcional)
docker compose up -d db

# 3. Compilar y lanzar
mvn clean install -U
mvn spring-boot:run
```

---

## 4. API REST

- Puerto: http://localhost:8081

| Método   | Endpoint                                 | Descripción                                                          |
| -------- | ---------------------------------------- | -------------------------------------------------------------------- |
| `GET`    | `/api/clientes/listar`                   | Lista todos los clientes                                             |
| `GET`    | `/api/clientes/buscar?termino={texto}`   | Listar clientes que coincidan por nombre o identificación (paginado) |
| `POST`   | `/api/clientes/crear`                    | Crear cliente **+** dirección matriz                                 |
| `PUT`    | `/api/clientes/actualizar/{id}`          | Editar datos de cliente                                              |
| `DELETE` | `/api/clientes/eliminar/{id}`            | Eliminar cliente                                                     |
| `POST`   | `/api/clientes/agregar-direcciones/{id}` | Agregar dirección (sucursal)                                         |
| `GET`    | `/api/clientes/direcciones/{id}`         | Listar todas las direcciones de un cliente                           |

### 4.1 Ejemplos `curl`

```bash
curl --location 'http://localhost:8081/api/clientes/crear' \
--header 'Content-Type: application/json' \
--data-raw '{
  "tipoIdentificacion": "C\u00e9dula",
  "numeroIdentificacion": "1234567890",
  "nombres": "Juan P\u00e9rez",
  "correo": "juan@mail.com",
  "celular": "0999999999",
  "direccionMatriz": {
    "provincia": "Pichincha",
    "ciudad": "Quito",
    "direccion": "Av. Siempre Viva 123",
    "esMatriz": true
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

## 7. Buenas prácticas implementadas

- Principios **SOLID**
- Capas limpias y DTOs
- Validaciones con **Bean Validation**
- Manejador global de errores
- TDD con JUnit + Mockito
