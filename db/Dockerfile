FROM mariadb
ENV MARIADB_ROOT_PASSWORD 1111
ENV MARIADB_ROOT_HOST root
ENV MARIADB_DATABASE fireseaTemp

COPY ./Config/setDB.sql /docker-entrypoint-initdb.d/setDB.sql

# mySQL은 무조건 3306 포트를 쓴다!!! 정해져있어서 못 바꾼다!
#EXPOSE 3307

RUN echo lower_case_table_names=1 >> /etc/mysql/conf.d/docker.cnf
RUN echo default-time-zone='+9:00' >> /etc/mysql/conf.d/docker.cnf
RUN echo collation-server = utf8mb4_unicode_ci >> /etc/mysql/conf.d/docker.cnf
RUN echo collation-server = utf8mb4_0900_ai_ci >> /etc/mysql/conf.d/docker.cnf
RUN echo character-set-server = utf8mb4 >> /etc/mysql/conf.d/docker.cnf
RUN echo skip-character-set-client-handshake >> /etc/mysql/conf.d/docker.cnf