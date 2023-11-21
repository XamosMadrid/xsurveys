# xsurveys
Trabajo de Fin de Grado Sergio Palacios Castaño - UAX.

## Pasos para levantar el servicio

1. Ejecutar docker-compose dentro de la carpeta support:
`support:\> docker-compose up`

2. Conectarse vía UI a localhost:3306 con el siguiente usuario y contraseña:
`admin / adminpass`
Y ejecutar el script sql que está dentro de la carpeta support: schema_mariadb.sql

3. Para arrancar el proyecto:
`./mvnw spring-boot:run`

## Mejoras para futuras versiones:
* Añadir un gestor de logs.
* Añadir más tests.
* Paginación de resultados.
* Seguridad.
* Añadir más opciones a cada pregunta y diferentes tipos de pregunta.
