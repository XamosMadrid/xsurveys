# xsurveys
Trabajo de Fin de Grado Sergio Palacios Castaño - UAX.

## Pasos para levantar el servicio
0. Prerrequisitos:
* Java
* Maven (opcional)
* Docker desktop (opcional)

1. Este paso es necesario únicamente si no existe ningún servidor de MySQL donde podamos instalar la base de datos. 
Ejecutar docker-compose dentro de la carpeta support:
`docker-compose up`
Este comando levantará un contenedor de MariaDB en el puerto 3306 de la máquina.

2. Conectarse vía UI (o de la manera que se desee) a localhost:3306 con el siguiente usuario y contraseña:
`admin / adminpass`
Y ejecutar el script sql que está dentro de la carpeta support: schema_mariadb.sql

Por ejemplo, usar DBeaver como UI o MySQL Workbench.

3. Para arrancar el proyecto:
`./mvnw spring-boot:run` o si prefieres usar maven de tu equipo `mvn spring-boot:run`

4. Abrir cualquier navegador y escribir: http://localhost:8085
