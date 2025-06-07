FROM mysql/mysql-server:latest



ENV MYSQL_ROOT_PASSWORD=password

ENV MYSQL_DATABASE=letrasypapeles

ENV MYSQL_USER=myuser

ENV MYSQL_PASSWORD=password

COPY ./create.sql /docker-entrypoint-initdb-d/