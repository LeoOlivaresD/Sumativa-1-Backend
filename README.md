=========================================================


# 📚 Letras & Papeles - Backend API

**Proyecto académico - Evaluación Final Transversal (EFT)**  
**Asignatura:** Desarrollo Backend II (PBY2202) – Duoc UC  
**Período:** Bimestre 7, 2025  
**Equipo:** [Jaime Barraza - Leonardo Olivares]  
**Repositorio:** https://github.com/LeoOlivaresD/Sumativa-1-Backend

---

## 📌 Descripción del proyecto

Este backend corresponde al sistema informático de **Letras & Papeles**, una librería y papelería chilena con más de 50 años de historia, que busca digitalizar su operación.  
El proyecto implementa una **API RESTful moderna** utilizando **Java 17, Spring Boot 3, Spring Security con JWT**, y cuenta con persistencia en **MySQL** usando contenedores Docker.  

La solución aborda los siguientes módulos clave:

- Gestión de inventario distribuido por sucursal
- Sistema de ventas y reservas en línea
- Gestión de usuarios con autenticación y roles
- Seguridad robusta con JWT + autorización basada en roles
- Documentación con Swagger (OAS3) y respuestas HATEOAS
- Pruebas unitarias con cobertura del 100% de ramas (JaCoCo)

---

## 🧱 Arquitectura y stack tecnológico

| Componente           | Tecnología              |
|----------------------|-------------------------|
| Lenguaje             | Java 17                 |
| Framework            | Spring Boot 3.2.x       |
| Seguridad            | Spring Security + JWT   |
| Persistencia         | Spring Data JPA         |
| Base de datos        | MySQL 8 (Dockerizado)   |
| API Docs             | OpenAPI 3 + Swagger UI  |
| HATEOAS              | Spring HATEOAS          |
| Pruebas              | JUnit 5 + JaCoCo        |
| Contenedores         | Docker                  |
| Control de versiones | Git + GitHub            |

---

## 📦 Instalación local

### 🔧 Requisitos

- Java 17
- Maven 3.8+
- Docker
- Git

### ⚙️ Levantar entorno con Docker

```bash
# 1. Clonar repositorio
git clone https://github.com/LeoOlivaresD/Sumativa-1-Backend.git
cd Sumativa-1-Backend

# 2. Iniciar base de datos MySQL con Docker
docker run -d --name mysql-letras -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=admin \
  -e MYSQL_DATABASE=letrasdb \
  mysql:8

# 3. Ejecutar aplicación desde tu IDE (IntelliJ, VS Code, etc.)
# o con Maven:
mvn spring-boot:run

🔐 Seguridad y autenticación
La API cuenta con seguridad basada en:

🔐 Autenticación JWT sin estado

👤 Autorización por roles (ROLE_USER, ROLE_ADMIN, ROLE_EMPLOYEE)

🔒 Protección de endpoints sensibles mediante filtros

🔑 Ejemplo de Login
POST /auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}


Respuesta:
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}


📘 Documentación de la API
🔎 Swagger UI: http://localhost:8080/swagger-ui/index.html

📄 OpenAPI JSON: http://localhost:8080/v3/api-docs

🔗 Respuestas HATEOAS: disponibles en endpoints como /products, /users, etc.



🧪 Pruebas y cobertura
El sistema implementa pruebas unitarias exhaustivas, logrando un 100% de cobertura de ramas (branch coverage) gracias a JaCoCo.


📄 Licencia
Proyecto con fines educativos para Duoc UC. No se autoriza su reproducción o distribución sin consentimiento de los autores.



✉️ Contacto
Para dudas académicas o técnicas, contactarse vía canal oficial AVA Duoc o GitHub Issues del repositorio.