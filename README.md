# 🛡️ Proyecto Java con Spring Security y Docker

Este proyecto fue desarrollado en **Java** utilizando **Spring Boot** y **Spring Security**, con persistencia en una base de datos que corre dentro de un contenedor **Docker**.  
Se implementaron pruebas unitarias para alcanzar un **100% de cobertura de ramas (BRANCH)** utilizando **JaCoCo**.  
El resultado se puede apreciar en la siguiente imagen:

<p align="center">
  <img src="https://i.ibb.co/r2rcKm7D/Captura-de-pantalla-280.png" alt="Imagen Coverage 100%" style="max-width: 100%; height: auto;" />
</p>

<p align="center">
  <em>✅ Cobertura de pruebas al 100% con JaCoCo</em>
</p>


## 🚀 Cómo levantar el proyecto con Docker

```bash
# 1. Construir la imagen del backend
docker build -t sumbackend2 .

# 2. Ejecutar el contenedor junto a la base de datos
docker run -d --name mysql-letras -p 3306:3306

# 3. Ejecutar proyecto desde IDE

